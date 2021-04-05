package com.example.study.model.enumclass;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PartnerStatus {
    REGISTERED(0,"등록","등록된 파트너"),
    UNREGISTERED(1, "등록해제", "등록해제된 파트너"),
    WAITING(2, "등록중","등록중인 파트너");

    public Integer id;

    public String title;

    public String description;

}
