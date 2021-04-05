package com.example.study.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Header<T> {

    //항상 통신시 들어가는 정보들 작성.

    //api 통신시간
    //아래와 같이 작성하면 json으로 만들때 키를 transaction_time 으로 만들어줌
    //그런데 일일히 하기 번거로우니 application.properties에서 설정
    //@JsonProperty("transaction_time")
    private LocalDateTime transactionTime;

    //api 응답코드

    private String resultCode;

    //api 부가설명
    private String description;

    //data 는 제네릭으로 구성.

    private T data;

    private Pagination pagination;


    //OK
    public static <T> Header<T> OK(){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .build();
    }

    //DATA OK
    public static <T> Header<T> OK(T data){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .build();
    }

    //DATA OK & PAGINATION
    public static <T> Header<T> OK(T data, Pagination pagination ){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("OK")
                .description("OK")
                .data(data)
                .pagination(pagination)
                .build();
    }

    //ERROR
    public static <T> Header<T> ERROR(String description){
        return (Header<T>) Header.builder()
                .transactionTime(LocalDateTime.now())
                .resultCode("ERROR")
                .description(description)
                .build();
    }

}
