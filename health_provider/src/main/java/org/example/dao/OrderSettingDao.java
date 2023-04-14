package org.example.dao;

import org.example.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingDao {
    public void add(OrderSetting orderSetting);
    public void editByOrderDate(OrderSetting orderSetting);
    public long findCountByOrderDate(Date orderDate);

    public void editReservationsByOrderDate(OrderSetting orderSetting);
    public OrderSetting findByOrderDate(Date orderDate);
}
