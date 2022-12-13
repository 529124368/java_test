package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.StudentModel;
import org.springframework.stereotype.Component;

@Component("StudentMapper")
public interface StudentMapper {
    public List<StudentModel> search();
}
