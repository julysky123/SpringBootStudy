package com.example.study.repository;

import com.example.study.model.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Test
    public void create(){
        Category category =
                Category.builder()
                        .createdAt(LocalDateTime.now())
                        .createdBy("AdminServer")
                        .title("컴퓨터")
                        .type("컴퓨터")
                        .build();
        Category newCategory = categoryRepository.save(category);
    }
}