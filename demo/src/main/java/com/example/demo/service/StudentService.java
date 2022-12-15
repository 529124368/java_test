package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.model.StudentModel;
import com.example.demo.repository.StudentMapper;

@Service
public class StudentService {
    @Autowired
    private StudentMapper studentMapper;

    public List<StudentModel> getAll() {
        return studentMapper.search();
    }
}
