package com.example.myspring.Service;

import com.example.myspring.Dto.OrderRequest;
import com.example.myspring.Model.Order;
import com.example.myspring.Model.OrderItem;
import com.example.myspring.Util.Page;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface OrderService {
    Order createOrder(Integer userId, List<OrderRequest> buyItemList);
    Page<Order> getOrders(Integer userId, Integer limit, Integer offset);
}
