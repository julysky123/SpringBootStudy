package com.example.study.model.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class Pagination {

    //아래 정보를 제공해주면 프론트에서 페이지 번호를 구성하는데에 무리가 없다.
    private Integer totalPages;         //총 페이지 수
    private Long totalElements;         //총 요소 수
    private Integer currentPage;        //현재 페이지 수
    private Integer currentElements;    //현재 요소 수
}
