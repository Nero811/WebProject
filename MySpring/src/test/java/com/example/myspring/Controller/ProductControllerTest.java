package com.example.myspring.Controller;

import com.example.myspring.Constant.ProductCategory;
import com.example.myspring.Dto.ProductRequest;
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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void getProductById_success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/{productId}", 1);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.productName", equalTo("蘋果（澳洲）")))
                .andExpect(jsonPath("$.category", equalTo("FOOD")))
                .andExpect(jsonPath("$.imageUrl", notNullValue()))
                .andExpect(jsonPath("$.price", notNullValue()))
                .andExpect(jsonPath("$.stock", notNullValue()))
                .andExpect(jsonPath("$.description", notNullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        System.out.println("response body: " + body);
    }

    @Test
    public void getProductById_notFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/product/{productId}", 2000);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404));
    }

    @Transactional
    @Test
    public void createProduct_success() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("AE86");
        productRequest.setCategory(ProductCategory.CAR);
        productRequest.setImageUrl("http://test.com");
        productRequest.setPrice(100);
        productRequest.setStock(10);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().is(201))
                .andExpect(jsonPath("$.productName", equalTo("AE86")))
                .andExpect(jsonPath("$.category", equalTo("CAR")))
                .andExpect(jsonPath("$.imageUrl", equalTo("http://test.com")))
                .andExpect(jsonPath("$.price", equalTo(100)))
                .andExpect(jsonPath("$.stock", equalTo(10)))
                .andExpect(jsonPath("$.description", nullValue()))
                .andExpect(jsonPath("$.createdDate", notNullValue()))
                .andExpect(jsonPath("$.lastModifiedDate", notNullValue()))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        System.out.println(body);
    }

    @Test
    public void createProduct_illegalArgument() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("test for illegalArgument");
        String json = objectMapper.writeValueAsString(productRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().is(400))
                .andReturn();
    }

    @Transactional
    @Test
    public void updateProductById_success() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("AE86");
        productRequest.setCategory(ProductCategory.CAR);
        productRequest.setImageUrl("http://test.com");
        productRequest.setDescription("test");
        productRequest.setPrice(50);
        productRequest.setStock(30);
        String json = objectMapper.writeValueAsString(productRequest);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/{productId}", 3)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.productName", equalTo("AE86")))
                .andExpect(jsonPath("$.category", equalTo("CAR")))
                .andExpect(jsonPath("$.imageUrl", equalTo("http://test.com")))
                .andExpect(jsonPath("$.description", equalTo("test")))
                .andExpect(jsonPath("$.price", equalTo(50)))
                .andExpect(jsonPath("$.stock", equalTo(30)))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        System.out.println(body);
    }

    @Transactional
    @Test
    public void updateProductById_notFound() throws Exception {
        ProductRequest productRequest = new ProductRequest();
        productRequest.setProductName("AE86");
        productRequest.setCategory(ProductCategory.CAR);
        productRequest.setImageUrl("http://test.com");
        productRequest.setDescription("test");
        productRequest.setPrice(50);
        productRequest.setStock(30);

        String json = objectMapper.writeValueAsString(productRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/products/{productId}", 20)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(requestBuilder)
                .andExpect(status().is(404))
                .andReturn();
    }

    @Transactional
    @Test
    public void deleteProductById() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/{productId}", 3);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204))
                .andReturn();
    }

    @Transactional
    @Test
    public void deleteProductById_notFound() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/products/{productId}", 2000);
        mockMvc.perform(requestBuilder)
                .andExpect(status().is(204))
                .andReturn();
    }

    @Test
    public void getProducts() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products");
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void getProducts_filter() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products")
                .param("category", "CAR")
                .param("search", "B");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.total", equalTo(2)))
                .andReturn();
    }

    @Test
    public void getProducts_sort() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/products")
                .param("order", "price")
                .param("sort", "DESC");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(5)))
                .andExpect(jsonPath("$.result[0].productId", equalTo(6)))
                .andExpect(jsonPath("$.result[1].productId", equalTo(5)))
                .andExpect(jsonPath("$.result[2].productId", equalTo(7)))
                .andReturn();
    }

    @Test
    public void getProducts_page() throws Exception {
        RequestBuilder requestBuilder =MockMvcRequestBuilders.get("/products")
                .param("limit", "5")
                .param("offset", "5");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result", hasSize(2)))
                .andExpect(jsonPath("$.result[0].productId", equalTo(2)))
                .andExpect(jsonPath("$.result[1].productId", equalTo(1)))
                .andReturn();
    }
}
