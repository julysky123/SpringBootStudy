package com.example.study.services;

import com.example.study.model.entity.Item;
import com.example.study.model.entity.OrderDetail;
import com.example.study.model.entity.OrderGroup;
import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.UserApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.model.network.response.OrderGroupApiResponse;
import com.example.study.model.network.response.UserApiResponse;
import com.example.study.model.network.response.UserOrderInfoApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserApiLogicService extends BaseService<UserApiRequest, UserApiResponse, User> {

    // 1. request data
    // 2. user 생성
    // 3. 생성된 데이터 -> UserApiResponse return

    @Autowired
    OrderGroupApiLogicService orderGroupApiLogicService;

    @Autowired
    ItemApiLogicService itemApiLogicService;

    @Override
    public Header<UserApiResponse> create(Header<UserApiRequest> request) {

        UserApiRequest userApiRequest = request.getData();

        User user = User.builder()
                .account(userApiRequest.getAccount())
                .password(userApiRequest.getPassword())
                .status(UserStatus.REGISTERED)
                .phoneNumber(userApiRequest.getPhoneNumber())
                .email(userApiRequest.getEmail())
                .registeredAt(LocalDateTime.now())
                .build();

        User newUser = baseRepository.save(user);

        return Header.OK(response(newUser));
    }

    @Override
    public Header<UserApiResponse> read(Long id) {
        Optional<User> optional = baseRepository.findById(id);

        //optional에 있다면 map으로 데이터 변형해서 리턴.
        //optional이 없다면 orElseGet으로 리턴
        return optional
                .map(user -> response(user))
                .map(Header::OK)
                .orElseGet(
                        ()->Header.ERROR("there is no User allocated id :"+id)
                );
    }

    @Override
    public Header<UserApiResponse> update(Header<UserApiRequest> request) {

        UserApiRequest userApiRequest = request.getData();
        Long id = userApiRequest.getId();

        Optional<User> optional = baseRepository.findById(id);

        return optional.map(user->{


            user.setAccount(userApiRequest.getAccount())
                    .setPassword(userApiRequest.getPassword())
                    .setStatus(userApiRequest.getStatus())
                    .setPhoneNumber(userApiRequest.getPhoneNumber())
                    .setEmail(userApiRequest.getEmail())
                    .setRegisteredAt(userApiRequest.getRegisteredAt())
                    .setUnregisteredAt(userApiRequest.getUnregisteredAt());
            return user;
        })
        .map(user-> baseRepository.save(user))
        .map(user->response(user))
        .map(Header::OK)
        .orElseGet(()->Header.ERROR("there is no User allocated id :"+id));
    }

    @Override
    public Header delete(Long id) {
        Optional<User> optional = baseRepository.findById(id);

        return optional.map(user-> {
            baseRepository.delete(user);
            return Header.OK();
        })
        .orElseGet(()->Header.ERROR("there is no User allocated id :"+id));
    }

    private UserApiResponse response(User user){
        //user -> userApiResponse 만들어서 리턴

        UserApiResponse userApiResponse= UserApiResponse.builder()
                .id(user.getId())
                .account(user.getAccount())
                .password(user.getPassword())   //todo 암호화, 길이
                .status(user.getStatus())
                .phoneNumber(user.getPhoneNumber())
                .email(user.getEmail())
                .registeredAt(user.getRegisteredAt())
                .unregisteredAt(user.getUnregisteredAt())
                .build();

        return userApiResponse;
    }
    @Override
    public Header<List<UserApiResponse>> search(Pageable pageable) {

        Page<User> users = baseRepository.findAll(pageable);

        List<UserApiResponse> userApiResponseList = users.stream()
                .map(user->response(user))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(users.getTotalPages())
                .totalElements(users.getTotalElements())
                .currentPage(users.getNumber())
                .currentElements(users.getNumberOfElements())      //현재 페이지의 요소 수.
                .build();

        return Header.OK(userApiResponseList, pagination);
    }

    public Header<UserOrderInfoApiResponse> orderInfo(Long id) {
        //user
        User user = baseRepository.getOne(id);
        UserApiResponse userApiResponse = response(user);

        //orderGroup
        List<OrderGroup> orderGroupList = user.getOrderGroupList();
        List<OrderGroupApiResponse> orderGroupApiResponseList =orderGroupList
                .stream()
                .map(orderGroup -> {
                    //items
                    List<ItemApiResponse> itemApiResponseList = orderGroup
                            .getOrderDetailList()
                            .stream()
                            .map(orderDetail -> orderDetail.getItem())
                            .map(itemApiLogicService::response)
                            .collect(Collectors.toList());

                    OrderGroupApiResponse orderGroupApiResponse
                            = orderGroupApiLogicService.response(orderGroup);
                    orderGroupApiResponse.setItemApiResponseList(itemApiResponseList);

                    return orderGroupApiResponse;
                })
                .collect(Collectors.toList());

        userApiResponse.setOrderGroupApiResponseList(orderGroupApiResponseList);

        UserOrderInfoApiResponse userOrderInfoApiResponse
                = UserOrderInfoApiResponse.builder()
                .userApiResponse(userApiResponse)
                .build();

        return Header.OK(userOrderInfoApiResponse);
    }
}
