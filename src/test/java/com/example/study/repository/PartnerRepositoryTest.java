package com.example.study.repository;

import com.example.study.model.entity.Partner;
import com.example.study.model.enumclass.PartnerStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PartnerRepositoryTest {


    @Autowired
    PartnerRepository partnerRepository;

    @Test
    public void create(){
        Partner partner =
                Partner.builder()
                        .name("Partner01")
                        .status(PartnerStatus.REGISTERED)
                        .address("서울시 강남구")
                        .callCenter("070-1111-2222")
                        .partnerNumber("010-1111-2222")
                        .businessNumber("1234567890123")
                        .ceoName("홍길동")
                        .registeredAt(LocalDateTime.now())
                        .createdAt(LocalDateTime.now())
                        .createdBy("AdminServer")
               //         .categoryId(1L)
                        .build();
        Partner newPartner = partnerRepository.save(partner);
        assertNotNull(newPartner);
        assertEquals(newPartner.getName(),"Partner01");
    }
}