package com.example.study.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
//JPA에 대해 감시하겠다는 설정.
@EnableJpaAuditing
public class JpaConfig {

}
