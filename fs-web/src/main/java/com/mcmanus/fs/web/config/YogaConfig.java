package com.mcmanus.fs.web.config;

import org.skyscreamer.yoga.configuration.DefaultEntityConfigurationRegistry;
import org.skyscreamer.yoga.configuration.YogaEntityConfiguration;
import org.skyscreamer.yoga.listener.RenderingListenerRegistry;
import org.skyscreamer.yoga.selector.CoreSelector;
import org.skyscreamer.yoga.selector.SelectorResolver;
import org.skyscreamer.yoga.selector.parser.AliasSelectorResolver;
import org.skyscreamer.yoga.selector.parser.DynamicPropertyResolver;
import org.skyscreamer.yoga.selector.parser.GDataSelectorParser;
import org.skyscreamer.yoga.selector.parser.SelectorParser;
import org.skyscreamer.yoga.springmvc.view.YogaSpringView;
import org.skyscreamer.yoga.view.JsonSelectorView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class YogaConfig {

    private static final Logger LOG = LoggerFactory.getLogger(YogaConfig.class);

    @Autowired
    List<YogaEntityConfiguration<?>> entitiesConfig;

    @Bean
    public RenderingListenerRegistry renderingListenerRegistry() {
        return new RenderingListenerRegistry();
    }

    @Bean
    public DefaultEntityConfigurationRegistry entityConfigurationRegistry() {
        return new DefaultEntityConfigurationRegistry(entitiesConfig);
    }

    @Bean
    public AliasSelectorResolver aliasSelectorResolver() {
        DynamicPropertyResolver dynamicPropertyResolver = new DynamicPropertyResolver();
        try {
            dynamicPropertyResolver.setPropertyFile(new ClassPathResource("yoga-aliases.properties").getInputStream());
        } catch (IOException e) {
            LOG.error("Cant load yoga aliases configuration file", e);
        }
        return dynamicPropertyResolver;
    }

    @Bean
    public SelectorParser selectorParser() {
        GDataSelectorParser parser = new GDataSelectorParser();
        parser.setAliasSelectorResolver(aliasSelectorResolver());
        parser.setDisableExplicitSelectors(true);
        return parser;
    }

    @Bean
    public CoreSelector coreSelector() {
        CoreSelector selector = new CoreSelector();
        selector.setEntityConfigurationRegistry(entityConfigurationRegistry());
        return selector;
    }

    @Bean
    public SelectorResolver selectorResolver() {
        SelectorResolver resolver = new SelectorResolver();
        resolver.setStarResolvesToAll(true);
        resolver.setSelectorParameterName("view");
        resolver.setBaseSelector(coreSelector());
        resolver.setSelectorParser(selectorParser());
        return resolver;
    }

    @Bean
    public JsonSelectorView jsonView() {
        JsonSelectorView view = new JsonSelectorView();
        view.setRegistry(renderingListenerRegistry());
        view.setSelectorResolver(selectorResolver());
        return view;
    }

    @Bean
    public ContentNegotiatingViewResolver viewResolver() {
        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
        resolver.setOrder(1);
        List<View> defaultViews = new ArrayList<>();
        YogaSpringView yogaView = new YogaSpringView();
        yogaView.setYogaView(jsonView());
        defaultViews.add(yogaView);
        resolver.setDefaultViews(defaultViews);
        return resolver;
    }
}

