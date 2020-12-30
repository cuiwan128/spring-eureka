//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package org.example.interceptor;

import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.SystemClock;
import com.baomidou.mybatisplus.core.toolkit.sql.SqlUtils;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;

@Intercepts({@Signature(
        type = StatementHandler.class,
        method = "query",
        args = {Statement.class, ResultHandler.class}
), @Signature(
        type = StatementHandler.class,
        method = "update",
        args = {Statement.class}
), @Signature(
        type = StatementHandler.class,
        method = "batch",
        args = {Statement.class}
)})
public class SqlTimeInterceptor extends PerformanceInterceptor {
    private static final Log logger = LogFactory.getLog(PerformanceInterceptor.class);
    private static final String DruidPooledPreparedStatement = "com.alibaba.druid.pool.DruidPooledPreparedStatement";
    private static final String T4CPreparedStatement = "oracle.jdbc.driver.T4CPreparedStatement";
    private static final String OraclePreparedStatementWrapper = "oracle.jdbc.driver.OraclePreparedStatementWrapper";
    private long maxTime = 0L;
    private boolean format = false;
    private boolean writeInLog = false;
    private Method oracleGetOriginalSqlMethod;
    private Method druidGetSQLMethod;

    public SqlTimeInterceptor() {
    }
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object firstArg = invocation.getArgs()[0];
        Statement statement;
        if (Proxy.isProxyClass(firstArg.getClass())) {
            statement = (Statement)SystemMetaObject.forObject(firstArg).getValue("h.statement");
        } else {
            statement = (Statement)firstArg;
        }

        MetaObject stmtMetaObj = SystemMetaObject.forObject(statement);

        try {
            statement = (Statement)stmtMetaObj.getValue("stmt.statement");
        } catch (Exception var20) {
        }

        if (stmtMetaObj.hasGetter("delegate")) {
            try {
                statement = (Statement)stmtMetaObj.getValue("delegate");
            } catch (Exception var19) {
            }
        }

        String originalSql = null;
        String stmtClassName = statement.getClass().getName();
        Class clazz;
        Object stmtSql;
        if ("com.alibaba.druid.pool.DruidPooledPreparedStatement".equals(stmtClassName)) {
            try {
                if (this.druidGetSQLMethod == null) {
                    clazz = Class.forName("com.alibaba.druid.pool.DruidPooledPreparedStatement");
                    this.druidGetSQLMethod = clazz.getMethod("getSql");
                }

                stmtSql = this.druidGetSQLMethod.invoke(statement);
                if (stmtSql instanceof String) {
                    originalSql = (String)stmtSql;
                }
            } catch (Exception var18) {
                var18.printStackTrace();
            }
        } else if ("oracle.jdbc.driver.T4CPreparedStatement".equals(stmtClassName) || "oracle.jdbc.driver.OraclePreparedStatementWrapper".equals(stmtClassName)) {
            try {
                if (this.oracleGetOriginalSqlMethod != null) {
                    stmtSql = this.oracleGetOriginalSqlMethod.invoke(statement);
                    if (stmtSql instanceof String) {
                        originalSql = (String)stmtSql;
                    }
                } else {
                    clazz = Class.forName(stmtClassName);
                    this.oracleGetOriginalSqlMethod = this.getMethodRegular(clazz, "getOriginalSql");
                    if (this.oracleGetOriginalSqlMethod != null) {
                        this.oracleGetOriginalSqlMethod.setAccessible(true);
                        if (null != this.oracleGetOriginalSqlMethod) {
                            stmtSql = this.oracleGetOriginalSqlMethod.invoke(statement);
                            if (stmtSql instanceof String) {
                                originalSql = (String)stmtSql;
                            }
                        }
                    }
                }
            } catch (Exception var17) {
            }
        }

        if (originalSql == null) {
            originalSql = statement.toString();
        }

        originalSql = originalSql.replaceAll("[\\s]+", " ");
        int index = this.indexOfSqlStart(originalSql);
        if (index > 0) {
            originalSql = originalSql.substring(index);
        }

        long start = SystemClock.now();
        Object result = invocation.proceed();
        long timing = SystemClock.now() - start;
        Object target = PluginUtils.realTarget(invocation.getTarget());
        MetaObject metaObject = SystemMetaObject.forObject(target);
        MappedStatement ms = (MappedStatement)metaObject.getValue("delegate.mappedStatement");
        StringBuilder formatSql = (new StringBuilder()).append("Execute SQL Time：").append(timing).append(" ms \nmapper ID：").append(ms.getId()).append("\n").append("Execute SQL：").append(SqlUtils.sqlFormat(originalSql, this.format)).append("\n");
        logger.error("sql执行时间连接器类：SqlTimeInterceptor \n"+formatSql.toString());
        return result;
    }



    private int indexOfSqlStart(String sql) {
        String upperCaseSql = sql.toUpperCase();
        Set<Integer> set = new HashSet();
        set.add(upperCaseSql.indexOf("SELECT "));
        set.add(upperCaseSql.indexOf("UPDATE "));
        set.add(upperCaseSql.indexOf("INSERT "));
        set.add(upperCaseSql.indexOf("DELETE "));
        set.remove(-1);
        if (CollectionUtils.isEmpty(set)) {
            return -1;
        } else {
            List<Integer> list = new ArrayList(set);
            list.sort(Comparator.naturalOrder());
            return (Integer)list.get(0);
        }
    }

}
