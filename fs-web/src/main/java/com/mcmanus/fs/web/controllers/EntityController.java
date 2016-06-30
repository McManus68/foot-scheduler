package com.mcmanus.fs.web.controllers;

import com.mcmanus.fs.model.exceptions.InputException;
import com.mcmanus.fs.model.jpa.AbstractEntity;
import com.mcmanus.fs.services.EntityService;
import com.mcmanus.fs.validator.EntityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public abstract class EntityController<T extends AbstractEntity> {

    @Autowired
    protected EntityService<T> srv;

    @Autowired(required = false)
    protected EntityValidator<T> validator;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<T> getAll() {
        return srv.getAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    @ModelAttribute
    public T get(@PathVariable Long id) {
        return srv.get(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ModelAttribute
    public Long add(@Valid @RequestBody T entity, BindingResult result) throws InputException {
        if(result.hasErrors()) {
            throw new InputException(result.getGlobalError().getCode());
        }
        return srv.save(entity);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ModelAttribute
    public Long update(@Valid @RequestBody T entity , BindingResult result) throws InputException {
        if(result.hasErrors()) {
            throw new InputException(result.getGlobalError().getCode());
        }
        return srv.update(entity);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable Long id) {
        srv.delete(id);
    }

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }
}
