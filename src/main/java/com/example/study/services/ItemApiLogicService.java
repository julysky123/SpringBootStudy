package com.example.study.services;

import com.example.study.model.entity.Item;
import com.example.study.model.network.Header;
import com.example.study.model.network.Pagination;
import com.example.study.model.network.request.ItemApiRequest;
import com.example.study.model.network.response.ItemApiResponse;
import com.example.study.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemApiLogicService extends BaseService<ItemApiRequest, ItemApiResponse, Item> {

    @Autowired
    PartnerRepository partnerRepository;

    @Override
    public Header<ItemApiResponse> create(Header<ItemApiRequest> request) {
        ItemApiRequest body = request.getData();

        Item item = baseRepository.save(Item.builder()
                    .status(body.getStatus())
                    .name(body.getName())
                    .title(body.getTitle())
                    .content(body.getContent())
                    .price(body.getPrice())
                    .brandName(body.getBrandName())
                    .partner(partnerRepository.getOne(body.getPartnerId()))
                    .registeredAt(LocalDateTime.now())
                    .build());
        Item newItem = baseRepository.save(item);
        return Header.OK(response(newItem));
    }

    @Override
    public Header<ItemApiResponse> read(Long id) {

        return baseRepository.findById(id)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("there is no Item allocated id :"+id));

    }

    @Override
    public Header<ItemApiResponse> update(Header<ItemApiRequest> request) {
        ItemApiRequest body = request.getData();
        Long id = body.getId();
        Optional<Item> optional = baseRepository.findById(id);
        return optional
                .map(item -> {
                    item
                            .setStatus(body.getStatus())
                            .setName(body.getName())
                            .setTitle(body.getTitle())
                            .setContent(body.getContent())
                            .setPrice(body.getPrice())
                            .setBrandName(body.getBrandName())
                            .setRegisteredAt(body.getRegisteredAt())
                            .setUnregisteredAt(body.getUnregisteredAt())
                            .setPartner(partnerRepository.getOne(body.getPartnerId()));
                    return item;
                })
                .map(baseRepository::save)
                .map(this::response)
                .map(Header::OK)
                .orElseGet(()->Header.ERROR("there is no Item allocated id :"+id));
    }

    @Override
    public Header delete(Long id) {

        Optional<Item> optional = baseRepository.findById(id);

        return optional.map(item ->{
            baseRepository.delete(item);
            return Header.OK();

        }).orElseGet(()->Header.ERROR("there is no Item allocated id :"+id));
    }

    public ItemApiResponse response(Item item){
        //user -> userApiResponse 만들어서 리턴

        //아래와 같이 작성하고 response 의 status를 String 으로 바꾼뒤 status의 내용을 출력하게 할 수도 있다.
        //String statusTitle = item.getStatus().getTitle();

        ItemApiResponse body= ItemApiResponse.builder()
                .id(item.getId())
                .status(item.getStatus())
                .name(item.getName())
                .title(item.getTitle())
                .content(item.getContent())
                .price(item.getPrice())
                .brandName(item.getBrandName())
                .registeredAt(item.getRegisteredAt())
                .unregisteredAt(item.getUnregisteredAt())
                .partnerId(item.getPartner().getId())
                .build();

        // Header + data
        return body;
    }

    @Override
    public Header<List<ItemApiResponse>> search(Pageable pageable) {

        Page<Item> items = baseRepository.findAll(pageable);

        List<ItemApiResponse> itemApiResponseList = items.stream()
                .map(item -> response(item))
                .collect(Collectors.toList());

        Pagination pagination = Pagination.builder()
                .totalPages(items.getTotalPages())
                .totalElements(items.getTotalElements())
                .currentPage(items.getNumber())
                .currentElements(items.getNumberOfElements())      //현재 페이지의 요소 수.
                .build();

        return Header.OK(itemApiResponseList, pagination);

    }
}
