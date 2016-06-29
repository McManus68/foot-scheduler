package com.mcmanus.fs.services;

public interface I18nService {

    String translate(String code);

    String translate(String code, Object[] args);
}
