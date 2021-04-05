package com.example.study.model.network.response;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOrderInfoApiResponse {

    //강의에서 편의상 이렇게 한다고함. 원래는 어케함 ㅡㅡ
    private UserApiResponse userApiResponse;
}
