/*
 * Copyright (c) http://www.svoflee.com All rights reserved.
 **************************************************************************
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *  
 *      http://www.apache.org/licenses/LICENSE-2.0
 *  
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 **************************************************************************      
 */

package com.svoflee.spartacus.test;

import static org.mockito.Mockito.mock;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mockito.Mock;
import org.springframework.aop.framework.Advised;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

/**
 * MockitoDependencyInjectionTestExecutionListener 是加入Mockito的支持的测试
 * 
 * @author <a href="mailto:svoflee@gmail.com">svoflee@gmail.com</a>
 * @since 1.0.0
 * @version 1.0.0
 */
public class MockitoDependencyInjectionTestExecutionListener extends DependencyInjectionTestExecutionListener {

    private static final Map<String, MockObject> mockObject = new HashMap<String, MockObject>();

    private static final List<Field> injectFields = new ArrayList<Field>();

    @Override
    protected void injectDependencies(final TestContext testContext) throws Exception {
        super.injectDependencies(testContext);
        init(testContext);
    }

    protected void injectMock(final TestContext testContext) throws IllegalArgumentException, IllegalAccessException,
            InvocationTargetException {
        AutowireCapableBeanFactory beanFactory = testContext.getApplicationContext().getAutowireCapableBeanFactory();
        for (Field field : injectFields) {
            Object o = beanFactory.getBean(field.getName(), field.getType());
            if (null != o) {
                Method[] methods = o.getClass().getDeclaredMethods();
                for (Method method : methods) {
                    if (method.getName().startsWith("set")) {
                        for (Object element : mockObject.keySet()) {
                            String key = (String) element;
                            if (method.getName().equalsIgnoreCase("set" + key)) {
                                method.invoke(o, mockObject.get(key).getObj());
                                break;
                            }
                        }
                    }
                }
            }
        }
    }

    private void init(final TestContext testContext) throws Exception {
        Object bean = testContext.getTestInstance();
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation aAnnotation : annotations) {
                if (aAnnotation instanceof Mock) {
                    // 注入mock实例
                    MockObject obj = new MockObject();
                    obj.setType(field.getType());
                    obj.setObj(mock(field.getType()));
                    field.setAccessible(true);
                    field.set(bean, obj.getObj());
                    mockObject.put(field.getName(), obj);
                }
                else if (aAnnotation instanceof Autowired) {
                    // 只对autowire重新注入
                    injectFields.add(field);
                }
            }
        }
        for (Field field : injectFields) {
            field.setAccessible(true);
            Object object = field.get(bean);
            if (object instanceof Proxy) {
                // 如果是代理的话，找到真正的对象
                Class targetClass = AopUtils.getTargetClass(object);
                if (targetClass == null) {
                    // 可能是远程实现
                    return;
                }
                Field[] targetFields = targetClass.getDeclaredFields();
                for (Field targetField : targetFields) {
                    // 针对每个需要重新注入的字段
                    for (Map.Entry<String, MockObject> entry : mockObject.entrySet()) {
                        // 针对每个mock的字段
                        if (targetField.getName().equals(entry.getKey())) {
                            targetField.setAccessible(true);
                            targetField.set(this.getTargetObject(object, entry.getValue().getType()), entry.getValue()
                                    .getObj());
                        }
                    }
                }
            }
            else {
                injectMock(testContext);
            }
        }
    }

    protected <T> T getTargetObject(Object proxy, Class<T> targetClass) throws Exception {
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return (T) ((Advised) proxy).getTargetSource().getTarget();
        }
        else {
            return (T) proxy; // expected to be cglib proxy then, which is simply a specialized class
        }
    }

    public static class MockObject {

        private Object obj;

        private Class<?> type;

        public MockObject() {
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        public Class<?> getType() {
            return type;
        }

        public void setType(Class<?> type) {
            this.type = type;
        }
    }

    public static Map<String, MockObject> getMockobject() {
        return mockObject;
    }

    public static List<Field> getInjectfields() {
        return injectFields;
    }
}
