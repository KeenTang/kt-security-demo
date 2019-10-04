package com.k.security.demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-02
 * Time: 20:05
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void queryUserTest() throws Exception {
        String content = mockMvc.perform(MockMvcRequestBuilders.get("/users") //发送GET请求访问user服务
                .param("id", "1")
                .param("size", "10")
                .param("page", "2")
                .contentType(MediaType.APPLICATION_JSON_UTF8))// 设置content-type
                .andExpect(MockMvcResultMatchers.status().isOk())//期望的服务返回状态码是200
                //期望的服务返回的数据长度是3
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(3))
                .andReturn().getResponse().getContentAsString();
        System.out.println("content==" +  content);
    }

    @Test
    public void getUserTest() throws Exception {
        String content = mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))// 设置content-type
                .andExpect(MockMvcResultMatchers.status().isOk())//期望的服务返回状态码是200
                //期望的服务返回的用户名称是3
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("name1"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(content);

    }
}