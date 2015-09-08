package com.twocater.diamond.core.server;

import com.twocater.diamond.api.annotation.AFilter;
import com.twocater.diamond.api.annotation.AListener;
import com.twocater.diamond.api.annotation.AServiceMapper;
import com.twocater.diamond.api.service.Filter;
import com.twocater.diamond.api.service.Service;
import com.twocater.diamond.core.server.parse.ContextFilterConfig;
import com.twocater.diamond.core.server.parse.ContextListenerConfig;
import com.twocater.diamond.core.server.parse.ContextServiceConfig;
import com.twocater.diamond.core.server.parse.SpringXmlServerFactory;

import java.util.*;

/**
 * Created by chenzhiwei on 15-9-8.
 */
public class AnnotationSupport {

    /**
     * 从注解中获取ContextService配置
     *
     * @return
     */
    public static List<ContextServiceConfig> getServiceConfigFromAnnotation() {
        String[] beanNamesForAnnotation = SpringXmlServerFactory.getApplicationContext().getBeanNamesForAnnotation(AServiceMapper.class);

        List<ContextServiceConfig> configs = new ArrayList();

        for (String name : beanNamesForAnnotation) {
            Object bean = SpringXmlServerFactory.getApplicationContext().getBean(name);
            // 符合以下条件的类，认为是合格的Service
            // 1、被注解为@Service，@AServiceMapper
            // 2、com.twocater.diamond.api.service.Service的子类
            // 3、没有@Deprecated标记
            if (bean instanceof Service && bean.getClass().getAnnotation(Deprecated.class) == null) {
                AServiceMapper annotation = bean.getClass().getAnnotation(AServiceMapper.class);

                ContextServiceConfig config = new ContextServiceConfig();
                config.setServiceName(name);
                config.setServiceClass(bean.getClass().getName());
                config.setOrder(annotation.order());
                config.setServicePaths(Arrays.asList(annotation.paths()));

                if (annotation.params() != null && annotation.params().length > 0) {
                    Map<String, String> map = new HashMap<>();
                    for (String s : annotation.params()) {
                        String[] split = s.split("=");
                        if (split.length == 2) {
                            map.put(split[0], split[1]);
                        }
                    }
                    config.setParams(map);
                }

                configs.add(config);
            }
        }
        return configs;
    }

    public static List<ContextFilterConfig> getFilterConfigFromAnnotation() {
        String[] beanNamesForAnnotation = SpringXmlServerFactory.getApplicationContext().getBeanNamesForAnnotation(AFilter.class);

        List<ContextFilterConfig> configs = new ArrayList();
        for (String name : beanNamesForAnnotation) {
            Object bean = SpringXmlServerFactory.getApplicationContext().getBean(name);


            // 符合以下条件的类，认为是合格的Filter
            // 1、被注解为@Component，@AFilter
            // 2、com.twocater.diamond.api.service.Filter的子类
            // 3、没有@Deprecated标记
            if (bean instanceof Filter && bean.getClass().getAnnotation(Deprecated.class) == null) {
                AFilter annotation = bean.getClass().getAnnotation(AFilter.class);

                ContextFilterConfig config = new ContextFilterConfig();
                config.setFilterName(name);
                config.setFilterClass(bean.getClass().getName());
                config.setFilterPaths(Arrays.asList(annotation.paths()));
                config.setOrder(annotation.order());

                if (annotation.params() != null && annotation.params().length > 0) {
                    Map<String, String> map = new HashMap<>();
                    for (String s : annotation.params()) {
                        String[] split = s.split("=");
                        if (split.length == 2) {
                            map.put(split[0], split[1]);
                        }
                    }
                    config.setParams(map);
                }

                configs.add(config);
            }
        }
        return configs;
    }

    public static List<ContextListenerConfig> getListenerConfigFromAnnotation() {
        String[] beanNamesForAnnotation = SpringXmlServerFactory.getApplicationContext().getBeanNamesForAnnotation(AListener.class);

        List<ContextListenerConfig> configs = new ArrayList();
        for (String name : beanNamesForAnnotation) {
            Object bean = SpringXmlServerFactory.getApplicationContext().getBean(name);
            // 符合以下条件的类，认为是合格的Listener
            // 1、被注解为@Component，@AListener
            // 2、java.util.EventListener的子类
            // 3、没有@Deprecated标记
            if (bean instanceof EventListener && bean.getClass().getAnnotation(Deprecated.class) == null) {
                AListener annotation = bean.getClass().getAnnotation(AListener.class);

                ContextListenerConfig config = new ContextListenerConfig();
                config.setListenerClass(bean.getClass().getName());
                config.setOrder(annotation.order());

                configs.add(config);
            }
        }
        return configs;
    }
}
