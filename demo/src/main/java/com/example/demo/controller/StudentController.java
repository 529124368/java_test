package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.bean.User;
import com.example.demo.model.StudentModel;
import com.example.demo.service.StudentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private Environment env;

    @GetMapping("/test/{name}/{age}")
    public List<StudentModel> helloWorld(@PathVariable("name") String name, @PathVariable("age") Integer age,
            User user) {
        System.out.print(user);
        System.out.println("name => " + name);
        System.out.println("age is " + age);
        System.out.println(env.getProperty("server.port"));
        log.error("api be called");
        return studentService.getAll();

    }
}
