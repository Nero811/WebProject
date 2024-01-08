package com.example.myspring.Service;

import java.util.List;

import com.example.myspring.Dto.OrderRequest;
import com.example.myspring.Model.Order;
import com.example.myspring.Util.Pages;

public interface OrderService {
    Order createOrder(Integer userId, List<OrderRequest> buyItemList);
    Pages<Order> getOrders(Integer userId, Integer limit, Integer offset);
}
