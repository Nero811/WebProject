package com.example.myspring.Service;

import com.example.myspring.Dto.OrderRequest;
import com.example.myspring.Model.Order;
import com.example.myspring.Model.OrderItem;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface OrderService {
    Order createOrder(Integer userId, List<OrderRequest> buyItemList);
}
