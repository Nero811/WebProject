package com.example.myspring.Service.impl;

import com.example.myspring.Dto.OrderRequest;
import com.example.myspring.Model.Order;
import com.example.myspring.Model.OrderItem;
import com.example.myspring.Model.Product;
import com.example.myspring.Service.OrderService;
import com.example.myspring.Util.Pages;
import com.example.myspring.Dao.OrderDao;
import com.example.myspring.Dao.ProductDao;
import com.example.myspring.Dao.UserDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderServiceImpl implements OrderService {

    @Autowired
    ProductDao productDao;

    @Autowired
    UserDao userDao;

    @Autowired
    OrderDao orderDao;

    private final static Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Transactional
    @Override
    public Order createOrder(Integer userId, List<OrderRequest> buyItemList) {
        int totalAmount = 0;

        List<Product> productList = new ArrayList<>();

        for (int i = 0 ; i < buyItemList.size() ; i++) {
            Product product = productDao.getProductById(buyItemList.get(i).getProductId());

            if (product == null) {
                log.warn("商品 {} 不存在", buyItemList.get(i).getProductId());
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            int quantity = buyItemList.get(i).getQuantity();
            int stock = product.getStock();

            // 檢查商品庫存機制
            if (stock == 0) {
                log.warn(product.getProductName() + " 商品庫存為0 ， 不可購買商品");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }
            else if (stock < quantity) {
                log.warn(product.getProductName() + " 商品實際庫存為 " + stock + " 小於購買數量，無法購買商品");
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            }

            // 確認 user 是否存在
            if (userDao.getUserById(userId) != null) {
                // 更新Product Table 商品庫存
                int newStock = stock - quantity;
                productDao.updateProductStock(newStock, product.getProductId());
            } else {
                log.warn("使用者不存在");
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            // 計算總價
            totalAmount += (product.getPrice() * quantity);
            productList.add(product);
        }

        Order order = orderDao.createOrder(userId, totalAmount);

        // 使用 forloop 效率較低，可以使用batchUpdate()效率較高
        for (int i = 0 ; i < productList.size() ; i++) {
             orderDao.createOrderItems(order.getOrderId(), productList.get(i), buyItemList.get(i).getQuantity());
        }

        // 串建orderItemList
        List<OrderItem> orderItemLists = orderDao.getOrderItemById(order.getOrderId());

        order.setOrderItemList(orderItemLists);

        return order;
    }

    @Override
    public Pages<Order> getOrders(Integer userId, Integer limit, Integer offset) {

        // 確認 user 是否存在
        if (userDao.getUserById(userId) == null) {
            log.warn("使用者不存在");
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        Pages<Order> page = new Pages<>();
        // 取得所有訂單
        List<Order> orders = orderDao.getOrdersByUserId(userId, limit, offset);

        page.setResult(orders);
        // 創建orderItemLists
        if (orders.size() > 0) {
            for (Order order : page.getResult()) {
                List<OrderItem> orderItemLists = orderDao.getOrderItemById(order.getOrderId());
                order.setOrderItemList(orderItemLists);
            }
        }

        page.setLimit(limit);
        page.setOffset(offset);
        page.setTotal(0);
        if (orders != null) {
        	page.setTotal(orders.size());
        } 

        return page;
    }
}
