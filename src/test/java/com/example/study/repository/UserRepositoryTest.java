package com.example.study.repository;

import com.example.study.model.entity.User;
import com.example.study.model.enumclass.UserStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void create(){
        User user = User.builder()
                .account("testuser02")
                .password("@123qwe")
                .email("testuser02@gmail.com")
                .status(UserStatus.REGISTERED)
                .phoneNumber("010-5678-8246")
                .build();
        User newUser = userRepository.save(user);
        System.out.println(newUser);
    }

    @Test
    @Transactional
    public void read(){
        Optional<User> user= userRepository.findById(1L);
        user.ifPresent(selectUser ->{
            System.out.println("user : "+selectUser);
        });
    }

    @Test
    @Transactional
    public void readByQuery(){
        Optional<User> user = userRepository.findFirstByPhoneNumberOrderByIdDesc("010-1234-5678");

        user.ifPresent(selectUser ->{
            selectUser.getOrderGroupList().stream().forEach(orderGroup -> {
                System.out.println("--------주문묶음--------");
                System.out.println("수령인: " + orderGroup.getRevName());
                System.out.println("수령지: " + orderGroup.getRevAddress());
                System.out.println("총금액: " + orderGroup.getTotalPrice());
                System.out.println("총수량 :" + orderGroup.getTotalQuantity());
                System.out.println("--------주문상세--------");
                orderGroup.getOrderDetailList().forEach(orderDetail -> {
                    System.out.println("파트너사 이름 : "+orderDetail.getItem().getPartner().getName());
                    System.out.println("파트너사 카테고리 : "+orderDetail.getItem().getPartner().getCategory().getTitle());
                    System.out.println("주문 상품 : " + orderDetail.getItem().getName());
                    System.out.println("고객센터 번호 : "+ orderDetail.getItem().getPartner().getCallCenter());
                    System.out.println("주문의 상태: "+orderDetail.getStatus());
                    System.out.println("도착 예정일자 : "+orderDetail.getArrivalDate());
                });
            });
        });
    }

    @Test
    public void update(){
        Optional<User> user= userRepository.findById(2L);

        user.ifPresent(selectUser ->{
            selectUser.setAccount("HACKING");
            selectUser.setUpdatedAt(LocalDateTime.now());
            selectUser.setUpdatedBy("hacker");

            //ID가 들어있는 값을 세이브하면 업데이트.
            userRepository.save(selectUser);
        });
    }

    @Test
    //@Transactional 을 하면 데이터를 수정하는 동작을 테스트한뒤 롤백해줌.
    @Transactional
    public void delete(){
        Optional<User> user= userRepository.findById(3L);

        assertTrue(user.isPresent());   //있는거 지우게.

        user.ifPresent(selectUser ->{
            userRepository.delete(selectUser);
        });

        Optional<User> deletedUser= userRepository.findById(3L);

        assertFalse(deletedUser.isPresent());
    }
}