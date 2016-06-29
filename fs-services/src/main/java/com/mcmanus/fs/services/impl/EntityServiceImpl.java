package com.mcmanus.fs.services.impl;

import com.mcmanus.fs.model.jpa.AbstractEntity;
import com.mcmanus.fs.security.services.AuthenticationService;
import com.mcmanus.fs.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public abstract class EntityServiceImpl<T extends AbstractEntity> implements EntityService<T> {

    @Autowired
    JpaRepository<T, Long> repo;

    @Autowired
    AuthenticationService authSrv;

    @Override
    public List<T> getAll() {
        return repo.findAll();
    }

    @Override
    public T get(Long id) {
        return repo.findOne(id);
    }

    @Override
    public Long save(T entity) {
        return repo.save(entity).getId();
    }

    @Override
    public Long update(T entity) {
        return repo.save(entity).getId();
    }

    @Override
    public void delete(Long id)  {
        repo.delete(id);
    }
}
