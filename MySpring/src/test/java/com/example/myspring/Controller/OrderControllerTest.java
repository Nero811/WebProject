package com.example.myspring.Controller;

import com.example.myspring.Dto.CreateOrderRequest;
import com.example.myspring.Dto.OrderRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper objectMapper = new ObjectMapper();
    //創建訂單功能
    @Transactional
    @Test
    public void createOrder_Success() throws Exception {
        List<OrderRequest> buyItemList = new ArrayList<>();
        OrderRequest orderRequest1 = new OrderRequest();
        OrderRequest orderRequest2 = new OrderRequest();
        orderRequest1.setProductId(1);
        orderRequest1.setQuantity(2);
        orderRequest2.setProductId(2);
        orderRequest2.setQuantity(2);
        buyItemList.add(orderRequest1);
        buyItemList.add(orderRequest2);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBuyItemList(buyItemList);

        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderId", equalTo(1)))
                .andExpect(jsonPath("$.userId", equalTo(1)))
                .andExpect(jsonPath("$.totalAmount", equalTo(660)))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        System.out.println(body);
    }

    @Transactional
    @Test
    public void createOrder_userNotFound() throws Exception {
        List<OrderRequest> buyItemList = new ArrayList<>();
        OrderRequest orderRequest1 = new OrderRequest();
        OrderRequest orderRequest2 = new OrderRequest();
        orderRequest1.setProductId(1);
        orderRequest1.setQuantity(2);
        orderRequest2.setProductId(2);
        orderRequest2.setQuantity(2);
        buyItemList.add(orderRequest1);
        buyItemList.add(orderRequest2);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBuyItemList(buyItemList);

        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/100/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Transactional
    @Test
    public void createOrder_productNotFound() throws Exception {
        List<OrderRequest> buyItemList = new ArrayList<>();
        OrderRequest orderRequest1 = new OrderRequest();
        OrderRequest orderRequest2 = new OrderRequest();
        orderRequest1.setProductId(100);
        orderRequest1.setQuantity(2);
        orderRequest2.setProductId(150);
        orderRequest2.setQuantity(2);
        buyItemList.add(orderRequest1);
        buyItemList.add(orderRequest2);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBuyItemList(buyItemList);

        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Transactional
    @Test
    public void createOrder_stockIsEmpty() throws Exception {
        List<OrderRequest> buyItemList = new ArrayList<>();
        OrderRequest orderRequest1 = new OrderRequest();
        orderRequest1.setProductId(6);
        orderRequest1.setQuantity(2);
        buyItemList.add(orderRequest1);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBuyItemList(buyItemList);

        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Transactional
    @Test
    public void createOrder_stockLessThanQuantity() throws Exception {
        List<OrderRequest> buyItemList = new ArrayList<>();
        OrderRequest orderRequest1 = new OrderRequest();
        orderRequest1.setProductId(7);
        orderRequest1.setQuantity(10);
        buyItemList.add(orderRequest1);

        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBuyItemList(buyItemList);

        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();
    }
    // 商品查詢功能
    @Transactional
    @Test
    public void getOrders_Success() throws Exception {
        List<OrderRequest> buyItemList = new ArrayList<>();
        OrderRequest orderRequest1 = new OrderRequest();
        OrderRequest orderRequest2 = new OrderRequest();
        orderRequest1.setProductId(1);
        orderRequest1.setQuantity(2);
        buyItemList.add(orderRequest1);

        // 創建第一筆訂單
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBuyItemList(buyItemList);

        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder).andReturn();

        buyItemList = new ArrayList<>();
        orderRequest2.setProductId(2);
        orderRequest2.setQuantity(2);
        buyItemList.add(orderRequest2);
        // 創建第二筆訂單
        createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBuyItemList(buyItemList);

        json = objectMapper.writeValueAsString(createOrderRequest);

        requestBuilder = MockMvcRequestBuilders.post("/users/1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder).andReturn();


        requestBuilder = MockMvcRequestBuilders.get("/users/1/orders?limit=10")
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", equalTo(10)))
                .andExpect(jsonPath("$.offset", equalTo(0)))
                .andExpect(jsonPath("$.total", equalTo(2)))
                .andExpect(jsonPath("$.result[0].orderId", equalTo(2)))
                .andExpect(jsonPath("$.result[0].userId", equalTo(1)))
                .andExpect(jsonPath("$.result[0].totalAmount", equalTo(600)))
                .andExpect(jsonPath("$.result[0].createdDate", notNullValue()))
                .andExpect(jsonPath("$.result[0].lastModifiedDate", notNullValue()))
                .andExpect(jsonPath("$.result[1].orderId", equalTo(1)))
                .andExpect(jsonPath("$.result[1].userId", equalTo(1)))
                .andExpect(jsonPath("$.result[1].totalAmount", equalTo(60)))
                .andExpect(jsonPath("$.result[0].createdDate", notNullValue()))
                .andExpect(jsonPath("$.result[0].lastModifiedDate", notNullValue()))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();

        System.out.println(body);
    }

    @Transactional
    @Test
    public void getOrders_userNotFound() throws Exception {
        List<OrderRequest> buyItemList = new ArrayList<>();
        OrderRequest orderRequest1 = new OrderRequest();
        OrderRequest orderRequest2 = new OrderRequest();
        orderRequest1.setProductId(1);
        orderRequest1.setQuantity(2);
        buyItemList.add(orderRequest1);

        // 創建第一筆訂單
        CreateOrderRequest createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBuyItemList(buyItemList);

        String json = objectMapper.writeValueAsString(createOrderRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder).andReturn();

        buyItemList = new ArrayList<>();
        orderRequest2.setProductId(2);
        orderRequest2.setQuantity(2);
        buyItemList.add(orderRequest2);
        // 創建第二筆訂單
        createOrderRequest = new CreateOrderRequest();
        createOrderRequest.setBuyItemList(buyItemList);

        json = objectMapper.writeValueAsString(createOrderRequest);

        requestBuilder = MockMvcRequestBuilders.post("/users/1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder).andReturn();


        requestBuilder = MockMvcRequestBuilders.get("/users/100/orders?limit=10")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Transactional
    @Test
    public void getOrders_userHasNoOrder() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/1/orders");

        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.limit", notNullValue()))
                .andExpect(jsonPath("$.offset", notNullValue()))
                .andExpect(jsonPath("$.total", notNullValue()))
                .andExpect(jsonPath("$.result", hasSize(0)));
    }
}
