package com.example.study.repository;

import com.example.study.model.entity.Item;
import com.example.study.model.enumclass.ItemStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ItemRepositoryTest {

    @Autowired
    private ItemRepository itemRepository;

    @Test
    public void create() {
        Item item = Item.builder()
                .status(ItemStatus.REGISTERED)
                .name("노트북")
                .title("삼성 노트북 A100")
                .price(BigDecimal.valueOf(100000))
                .content("삼성 노트북입니다.")
                .brandName("삼성")
                .registeredAt(LocalDateTime.now())
                .createdAt(LocalDateTime.now())
                .createdBy("Partner01")
      //          .partnerId(1L)
                .build();

        Item newItem = itemRepository.save(item);
        assertNotNull(newItem);
    }

    @Test
    public void read(){
        Long id = 1L;

        Optional<Item> item = itemRepository.findById(id);

        assertTrue(item.isPresent());

        item.ifPresent(i->{
            System.out.println(i);
        });
    }
}