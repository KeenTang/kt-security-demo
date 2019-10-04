package com.k.security.demo.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.k.security.core.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created with IntelliJ IDEA.
 * Desc:
 *
 * @author: keen
 * Date: 2019-10-02
 * Time: 16:28
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @JsonView(value = User.UserSampleView.class)
    public List<User> users(User user, Pageable pageable) {
        System.out.println(user);
        System.out.println(ReflectionToStringBuilder.toString(pageable, ToStringStyle.MULTI_LINE_STYLE));
        int size = 3;
        ArrayList<User> users = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            users.add(new User(1, "name" + i,"p"));
        }
        return users;
    }

    /**
     * 参数中\\d+是一个正则表达式，代表id只能接收整数
     */
    @RequestMapping(value = "/{id:\\d+}", method = RequestMethod.GET)
    @JsonView(value = User.UserDetailView.class)
    public User getUser(@PathVariable String id) {
        System.out.println("id=" + id);
        User user = new User();
        user.setUserName("name1");
        return user;
    }

    @GetMapping("/upload")
    public void upload(MultipartFile file){
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());
    }

    @GetMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response)throws Exception{
        try(InputStream is=new FileInputStream("D:\\面试题.txt");
            OutputStream os=response.getOutputStream()){
            response.setContentType("application/x-download");
            response.setHeader("Content-Disposition","attachment;filename=test.txt");
            IOUtils.copy(is,os);
            os.flush();
        }
    }

    @GetMapping("/callableTest")
    public Callable<User> callableTest(){
        log.info("主线程执行");
        Callable callable = new Callable<User>() {
            @Override
            public User call() throws Exception {
                log.info("副线程执行");
                User user = new User();
                user.setUserName("Abc");
                Thread.sleep(10000);
                log.info("副线程执行完毕");
                return user;
            }
        };
        log.info("主线程完毕");
        return callable;
    }
}
