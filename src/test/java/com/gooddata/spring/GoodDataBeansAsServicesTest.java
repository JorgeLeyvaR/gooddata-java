/**
 * Copyright (C) 2004-2016, GoodData(R) Corporation. All rights reserved.
 * This source code is licensed under the BSD-style license found in the
 * LICENSE.txt file in the root directory of this source tree.
 */
package com.gooddata.spring;

import com.gooddata.GoodData;
import com.gooddata.project.ProjectService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

import static org.testng.Assert.*;

public class GoodDataBeansAsServicesTest {

    @Test
    public void servicesRegisteredFromXmlUsingAnnotationConfig() throws Exception {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/gd-annotationConfig.xml");
        assertAllServicesPresent(context);
    }

    @Test
    public void servicesRegisteredFromXmlUsingRegistrar() throws Exception {
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/gd-registrar.xml");
        assertAllServicesPresent(context);
    }

    @Test
    public void servicesRegisteredFromJava() throws Exception {
        final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(GoodDataSpringConfig.class);
        assertAllServicesPresent(context);
    }

    private void assertAllServicesPresent(ApplicationContext context) {
        Arrays.stream(GoodData.class.getDeclaredMethods())
                .map(Method::getReturnType)
                .filter(rt -> rt.getName().endsWith("Service"))
                .forEach(rt -> assertNotNull(context.getBean(rt), "Bean of type " + rt + " should be registered"));
    }

    @Configuration
    public static class GoodDataSpringConfig {

        @Bean
        public GoodData goodData() {
            return new GoodData("roman@gooddata.com", "supercoolsecret");
        }


        @Bean
        public BeanDefinitionRegistryPostProcessor goodDataRegistrar() {
            return new GoodDataServicesRegistrar("goodData");
        }
    }

}