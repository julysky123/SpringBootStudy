package com.example.study.component;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;



@Component
public class LoginUserAuditorAware implements AuditorAware<String> {

    //CreatedBy , LastModifiedBy 어노테이션 붙은 애들은 전부 이 리턴값으로 들어감.
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of("AdminServer");
    }
}
