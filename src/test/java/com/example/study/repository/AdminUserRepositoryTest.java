package com.example.study.repository;

import com.example.study.model.entity.AdminUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminUserRepositoryTest {

    @Autowired
    AdminUserRepository adminUserRepository;

    @Test
    public void create() {
        AdminUser adminUser =
                AdminUser.builder()
                        .account("Admin2")
                        .password("@123qwe")
                        .status("REGISTERED")
                        .role("Programmer")
                        .build();
        AdminUser newAdminUser = adminUserRepository.save(adminUser);

        assertNotNull(newAdminUser);
    }
}