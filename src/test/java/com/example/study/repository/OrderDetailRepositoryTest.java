package com.example.study.repository;

import com.example.study.model.entity.OrderDetail;
import com.example.study.model.enumclass.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@SpringBootTest
class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Test
    public void create(){
        OrderDetail orderDetail =
                OrderDetail.builder()
                        .status(OrderStatus.COMPLETE)
                        .arrivalDate(LocalDateTime.now().plusDays(2))
                        .quantity(1)
                        .totalPrice(BigDecimal.valueOf(9000000))
      //                  .itemId(1L)
                        .createdAt(LocalDateTime.now())
                        .createdBy("AdminServer")
                        .build();
        OrderDetail newOrderDetail = orderDetailRepository.save(orderDetail);

    }
}