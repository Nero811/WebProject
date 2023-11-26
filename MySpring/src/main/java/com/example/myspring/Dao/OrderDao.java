package com.example.myspring.Dao;

import com.example.myspring.Dto.OrderRequest;
import com.example.myspring.Model.Order;
import com.example.myspring.Model.OrderItem;
import com.example.myspring.Model.Product;
import com.example.myspring.Util.Page;

import java.util.List;

public interface OrderDao {
    Order getOrderByOrderId(int orderId);
    List<OrderItem> getOrderItemById(int orderId);
    List<Order> getOrdersByUserId(Integer userId, Integer limit, Integer offset);
    Order createOrder(int userId, int totalAmount);
    void createOrderItems(int orderId, Product product, int quantity);
}
