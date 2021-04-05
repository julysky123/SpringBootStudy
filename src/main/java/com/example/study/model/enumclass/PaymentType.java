package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {

    CASH(0,"현금","현금 결제"),
    CARD(1,"카드", "카드 결제"),
    BANK_TRANSFER(2,"입금", "계좌 입금");

    private Integer id;
    private String title;
    private String description;
}
