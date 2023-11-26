package com.example.myspring.Dao.impl;

import com.example.myspring.Dao.OrderDao;
import com.example.myspring.Dao.ProductDao;
import com.example.myspring.Model.Order;
import com.example.myspring.Model.OrderItem;
import com.example.myspring.Model.Product;
import com.example.myspring.Util.Page;
import com.example.myspring.rowmapper.OrderItemRowMapper;
import com.example.myspring.rowmapper.OrderRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class OrderDaoImpl implements OrderDao {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public List<Order> getOrdersByUserId(Integer userId, Integer limit, Integer offset) {
        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date FROM `order` WHERE 1=1 " +
                "AND user_id = :userId";

        // 依創建日期排序
        sql += " ORDER BY created_date DESC";

        sql += " LIMIT :limit OFFSET :offset";

        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("limit", limit);
        map.put("offset", offset);

        List<Order> orders = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if (orders.size() > 0) {
            return orders;
        } else {
            return null;
        }
    }

    @Override
    public Order getOrderByOrderId(int orderId) {

        String sql = "SELECT order_id, user_id, total_amount, created_date, last_modified_date " +
                "FROM `order` WHERE order_id = :orderId";

        Map<String, Object> map = new HashMap<>();

        map.put("orderId", orderId);

        List<Order> orders = namedParameterJdbcTemplate.query(sql, map, new OrderRowMapper());

        if (orders.size() > 0) {
            return orders.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<OrderItem> getOrderItemById(int orderId) {

        String sql = "SELECT o.order_item_id, o.order_id, o.product_id, o.quantity, o.amount, p.product_name, p.image_url " +
                "FROM order_item AS o LEFT JOIN product AS p " +
                "ON o.product_id = p.product_id " +
                "WHERE o.order_id = :orderId";

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);

        List<OrderItem> orderItems = namedParameterJdbcTemplate.query(sql, map, new OrderItemRowMapper());

        if (orderItems.size() > 0) {
            return orderItems;
        } else {
            return null;
        }
    }

    @Override
    public Order createOrder(int userId, int totalAmount) {

        String sql = "INSERT INTO `order`(user_id, total_amount, created_date, last_modified_date) " +
                "VALUES(:userId, :totalAmount, :createdDate, :lastModifiedDate)";

        Map<String, Object> map = new HashMap<>();

        map.put("userId", userId);
        map.put("totalAmount", totalAmount);
        Date date = new Date();
        map.put("createdDate", date);
        map.put("lastModifiedDate", date);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        Order order = this.getOrderByOrderId(keyHolder.getKey().intValue());

        return order;
    }

    @Override
    public void createOrderItems(int orderId, Product product, int quantity) {

        String sql = "INSERT INTO order_item(order_id, product_id, quantity, amount) " +
                "VALUES(:orderId, :productId, :quantity, :amount)";

        int amount = product.getPrice() * quantity;

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", orderId);
        map.put("productId", product.getProductId());
        map.put("quantity", quantity);
        map.put("amount", amount);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
    }
}
