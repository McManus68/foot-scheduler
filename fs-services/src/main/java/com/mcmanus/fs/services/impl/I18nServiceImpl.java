package com.mcmanus.fs.services.impl;

import com.mcmanus.fs.services.I18nService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class I18nServiceImpl implements I18nService {

    @Autowired
    private MessageSource i18n;

    @Override
    public String translate(String code) {
        return  i18n.getMessage(code, null, null);
    }

    @Override
    public String translate(String code, Object[] args) {
        return  i18n.getMessage(code, args, null);
    }
}
