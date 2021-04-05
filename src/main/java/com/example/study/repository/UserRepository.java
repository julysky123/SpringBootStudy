package com.example.study.repository;

import com.example.study.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    //자동으로 findBy 까지 보고 뒤에 올 항목명을 보고 그걸로 쿼리 검색해줌.
//    Optional<User> findByAccount(String account);
//    Optional<User> findByEmail(String Email);
    //대문자로 구분.
//    Optional<User> findByAccountAndEmail(String account, String email);

    Optional<User> findFirstByPhoneNumberOrderByIdDesc(String phoneNumber);
}
