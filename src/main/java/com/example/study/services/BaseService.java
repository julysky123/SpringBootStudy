package com.example.study.services;

import com.example.study.interfaces.CrudInterface;
import com.example.study.model.network.Header;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

//autowired 받으려면 작성하는 어노테이션)
@Component
public abstract class BaseService<Req,Res,Entity> implements CrudInterface<Req,Res> {

    //항상 받는것은 아님.
    @Autowired (required = false)
    // JpaRepository<Entity,Long> 에 알맞는 Repository를 주입 시켜준다.
    protected JpaRepository<Entity,Long> baseRepository;

    public abstract Header<List<Res>> search(Pageable pageable);
}
