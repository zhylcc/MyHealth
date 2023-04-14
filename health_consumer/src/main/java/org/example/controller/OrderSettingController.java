package org.example.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.example.constant.MessageConstant;
import org.example.entity.Result;
import org.example.pojo.OrderSetting;
import org.example.service.OrderSettingService;
import org.example.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {

    @Reference
    private OrderSettingService orderSettingService;

    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile")MultipartFile excelFile) {
        try {
            List<String[]> rows = POIUtils.readExcel(excelFile);
            if (rows.size() > 0) {
                List<OrderSetting> orderSettingList = new ArrayList<>();
                for (String[] cells: rows) {
                    orderSettingList.add(
                            new OrderSetting(new Date(cells[0]), Integer.parseInt(cells[1])));
                }
                orderSettingService.add(orderSettingList);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
        return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
    }
}
