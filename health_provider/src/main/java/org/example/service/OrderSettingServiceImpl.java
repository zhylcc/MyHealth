package org.example.service;

import com.alibaba.dubbo.config.annotation.Service;
import org.example.dao.OrderSettingDao;
import org.example.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService{

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    public void add(List<OrderSetting> orderSettingList) {
        if (orderSettingList != null && orderSettingList.size() > 0) {
            for (OrderSetting orderSetting: orderSettingList) {
                if (orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate()) > 0) {
                    orderSettingDao.editByOrderDate(orderSetting);
                } else {
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }
}
