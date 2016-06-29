package com.mcmanus.fs.validator;

import com.mcmanus.fs.model.jpa.AbstractEntity;
import com.mcmanus.fs.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Validator;

import java.lang.reflect.ParameterizedType;

public abstract class EntityValidator<T extends AbstractEntity> implements Validator {

    @Autowired
    protected EntityService<T> srv;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }
}
