package com.example.demo.repository;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.demo.model.StudentModel;

@Component("StudentMapper")
public interface StudentMapper {
    public List<StudentModel> search();
}
