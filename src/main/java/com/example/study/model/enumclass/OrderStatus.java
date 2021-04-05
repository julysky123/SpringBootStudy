package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {
    HOLDING(0,"준비준비","주문 품목 체크 중"),
    ONGOING(1,"배송중","고객님께 찾아가는 중"),
    COMPLETE(2, "배송완료","고객님께 전달 완료");

    private Integer id;
    private String title;
    private String description;
}
