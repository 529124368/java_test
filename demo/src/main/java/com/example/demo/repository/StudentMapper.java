package com.example.demo.repository;

import java.util.List;

import com.example.demo.model.StudentModel;

public interface StudentMapper {
    List<StudentModel> search();
}
