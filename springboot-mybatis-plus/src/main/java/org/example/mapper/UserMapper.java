package org.example.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.example.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

// 在对应的Mapper上面继承基本的类 BaseMapper
// 代表持久层
@Repository
public interface UserMapper extends BaseMapper<User> {
    // 所有的CRUD操作都已经编写完成了

    List<User> selectAll(@Param(Constants.WRAPPER) QueryWrapper<User> wrapper);

    List<User> selectOrderUser(@Param("orderId") Long orderId);

    List<User> selectUserOrders(@Param("orderId") Long orderId);

    List<User> findUserAndItemsRstMap(@Param("orderId") Long orderId);
 }

