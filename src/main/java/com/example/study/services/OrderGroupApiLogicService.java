package com.example.study.services;

import com.example.study.model.entity.OrderGroup;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.OrderGroupApiRequest;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderGroupApiLogicService extends BaseService<OrderGroupApiRequest, OrderGroupApiResponse, OrderGroup> {

    @Autowired
    UserRepository userRepository;

    @Override
    public Header<OrderGroupApiResponse> create(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest body = request.getData();
        OrderGroup orderGroup = OrderGroup.builder()
                .status(body.getStatus())
                .orderType(body.getOrderType())
                .revAddress(body.getRevAddress())
                .revName(body.getRevName())
                .paymentType(body.getPaymentType())
                .totalPrice(body.getTotalPrice())
                .totalQuantity(body.getTotalQuantity())
                .orderAt(LocalDateTime.now())
                .arrivalDate(body.getArrivalDate())
                .user(userRepository.getOne(body.getUserId()))
                .build();

        OrderGroup newOrderGroup = baseRepository.save(orderGroup);

        return Header.OK(response(newOrderGroup));
    }

    @Override
    public Header<OrderGroupApiResponse> read(Long id) {
        Optional<OrderGroup> optional = baseRepository.findById(id);
        return optional.map(this::response) //alt enter 쳐서 추천받은 람다 method
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("there is no OrderGroup allocated id :"+id));
    }

    @Override
    public Header<OrderGroupApiResponse> update(Header<OrderGroupApiRequest> request) {
        OrderGroupApiRequest body = request.getData();
        Long id = body.getId();
        Optional<OrderGroup> optional = baseRepository.findById(id);
        return optional
                .map(orderGroup -> {
                    orderGroup
                            .setStatus(body.getStatus())
                            .setOrderType(body.getOrderType())
                            .setRevAddress(body.getRevAddress())
                            .setRevName(body.getRevName())
                            .setPaymentType(body.getPaymentType())
                            .setTotalPrice(body.getTotalPrice())
                            .setTotalQuantity(body.getTotalQuantity())
                            .setOrderAt(body.getOrderAt())
                            .setArrivalDate(body.getArrivalDate())
                            .setUser(userRepository.getOne(body.getUserId()));
                    return baseRepository.save(orderGroup);
                })
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("there is no OrderGroup allocated id :"+id));
    }

    @Override
    public Header delete(Long id) {
        Optional<OrderGroup> optional = baseRepository.findById(id);

        return optional.map(orderGroup ->{
            baseRepository.delete(orderGroup);
            return Header.OK();
        }).orElseGet(()->Header.ERROR("there is no OrderGroup allocated id :"+id));
    }

    public OrderGroupApiResponse response(OrderGroup orderGroup){
        OrderGroupApiResponse body = OrderGroupApiResponse.builder()
                .id(orderGroup.getId())
                .status(orderGroup.getStatus())
                .orderType(orderGroup.getOrderType())
                .revAddress(orderGroup.getRevAddress())
                .revName(orderGroup.getRevName())
                .paymentType(orderGroup.getPaymentType())
                .totalPrice(orderGroup.getTotalPrice())
                .totalQuantity(orderGroup.getTotalQuantity())
                .arrivalDate(orderGroup.getArrivalDate())
                .userId(orderGroup.getUser().getId())
                .build();
        return body;
    }

    @Override
    public Header<List<OrderGroupApiResponse>> search(Pageable pageable) {
        Page<OrderGroup> orderGroups = baseRepository.findAll(pageable);

        List<OrderGroupApiResponse> orderGroupApiResponseList = orderGroups.stream()
                .map(orderGroup -> response(orderGroup))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(orderGroups.getTotalPages())
                .totalElements(orderGroups.getTotalElements())
                .currentPage(orderGroups.getNumber())
                .currentElements(orderGroups.getNumberOfElements())      //현재 페이지의 요소 수.
                .build();

        return Header.OK(orderGroupApiResponseList, pagination);
    }
}
