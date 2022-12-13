package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.StudentModel;
import com.example.demo.repository.StudentMapper;

@RestController
public class StudentController {
    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private Environment env;

    @GetMapping("/test/{name}/{age}")
    public List<StudentModel> helloWorld(@PathVariable("name") String name, @PathVariable("age") Integer age,
            HttpServletRequest request) {
        String n = request.getParameter("name");
        System.out.println(n);
        System.out.println("name => " + name);
        System.out.println("age is " + age);
        System.out.println(env.getProperty("server.port"));
        logger.error("api be called");
        return studentMapper.search();

    }
}
