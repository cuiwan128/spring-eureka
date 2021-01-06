package org.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/excel")
public class ExcelController {
    @RequestMapping("/export")
    public ModelAndView export(){
        List<List<String>> sheet1 = Arrays.asList(
                Arrays.asList("1", "11", "111", "1111"),
                Arrays.asList("2", "22", "222", "2222"),
                Arrays.asList("3", "33", "333", "3333")
        );
        List<List<String>> sheet2 = Arrays.asList(
                Arrays.asList("4", "44", "444", "4444"),
                Arrays.asList("5", "55", "555", "5555"),
                Arrays.asList("6", "66", "666", "6666")
        );
        List<List<List<String>>> sheets = Arrays.asList(sheet1, sheet2);
        Map<String, Object> map = new HashMap<>();
        map.put("ExcelSheetSetting", ExcelSheetSettingEnum.REPORT_TEST);
        map.put("data", sheets);
        ExcelView excelView = new ExcelView();
        return new ModelAndView(excelView, map);
    }
}
