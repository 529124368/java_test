package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.StudentModel;
import com.example.demo.repository.StudentMapper;

@RestController
public class StudentController {
    @Autowired
    private StudentMapper stu;

    @GetMapping("/test")
    public List<StudentModel> helloWorld() {
        List<StudentModel> s = stu.search();
        return s;

    }
}
