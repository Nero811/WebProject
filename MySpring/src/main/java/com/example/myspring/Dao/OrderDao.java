package com.example.myspring.Dao;

import com.example.myspring.Dto.OrderRequest;
import com.example.myspring.Model.Order;
import com.example.myspring.Model.OrderItem;
import com.example.myspring.Model.Product;

import java.util.List;

public interface OrderDao {
    Order getOrderByOrderId(int orderId);
    OrderItem getOrderItemById(int orderId);
    Order createOrder(int userId, int totalAmount);
    void createOrderItems(int orderId, Product product, int quantity);
}
