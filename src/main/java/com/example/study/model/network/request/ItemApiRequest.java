package com.example.study.model.network.request;

import com.example.study.model.enumclass.ItemStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemApiRequest {

    private Long id;

    private ItemStatus status;

    private String name;

    private String title;

    private String content;

    private BigDecimal price;      //todo 소숫점 4자리.

    private String brandName;

    private Long partnerId;

    private LocalDateTime registeredAt;

    private LocalDateTime unregisteredAt;
}
