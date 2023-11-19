package com.example.myspring.Controller;

import com.example.myspring.Dao.UserDao;
import com.example.myspring.Dto.UserRequest;
import com.example.myspring.Model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserDao userDao;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void userLogin_success() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test1@gmail.com");
        userRequest.setPassword("123456");

        String json = objectMapper.writeValueAsString(userRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(jsonPath("$.email", equalTo("test1@gmail.com")))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        System.out.println(body);
    }

    @Test
    public void userLogin_wrongPassword() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test1@gmail.com");
        userRequest.setPassword("12345");

        String json = objectMapper.writeValueAsString(userRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().is(404))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        System.out.println(body);
    }

    @Test
    public void userLogin_UserNotFound() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test2@gmail.com");
        userRequest.setPassword("123456");

        String json = objectMapper.writeValueAsString(userRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().is(404))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        System.out.println(body);
    }

    @Test
    public void userLogin_wrongUserFormat() throws Exception {
        UserRequest userRequest = new UserRequest();
        userRequest.setEmail("test1gmail.com");
        userRequest.setPassword("123456");

        String json = objectMapper.writeValueAsString(userRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().is(404))
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();
        System.out.println(body);
    }

    @Transactional
    @Test
    public void userRegistry_success() throws Exception {
        UserRequest userRequest = new UserRequest();

        userRequest.setEmail("test2@gmail.com");
        userRequest.setPassword("123456");

        String json = objectMapper.writeValueAsString(userRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email", equalTo("test2@gmail.com")))
                .andReturn();

        // 驗證密碼有被 MD5
        User newUser = userDao.getUserById(2);
        assertNotNull(newUser.getPassword(), userRequest.getPassword());

        String body = mvcResult.getResponse().getContentAsString();

        System.out.println(body);
    }

    @Transactional
    @Test
    public void userRegistry_userExisted() throws Exception {
        UserRequest userRequest = new UserRequest();

        userRequest.setEmail("test1@gmail.com");
        userRequest.setPassword("123456");

        String json = objectMapper.writeValueAsString(userRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();

        System.out.println(body);
    }

    @Transactional
    @Test
    public void userRegistry_wrongEmailFormat() throws Exception {
        UserRequest userRequest = new UserRequest();

        userRequest.setEmail("test1gmail.com");
        userRequest.setPassword("123456");

        String json = objectMapper.writeValueAsString(userRequest);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(status().isBadRequest())
                .andReturn();

        String body = mvcResult.getResponse().getContentAsString();

        System.out.println(body);
    }
}
