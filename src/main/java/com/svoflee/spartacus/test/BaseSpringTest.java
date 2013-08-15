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

import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;

/**
 * BaseSpringTest 是采用Spring配置作为测试时使用的测试基类,
 * 移植至 {@link AbstractJUnit4SpringContextTests}，
 * 使用BaseSpringTest需要设置配置文件,比如：
 * <p>
 * <code>  @ContextConfiguration(locations = {
 "classpath*:/config/approot-config.xml" }</code>
 * </p>
 * 默认的SpringContext配置文件存放于classpath*:/config/approot-config.xml
 * 
 * @author <a href="mailto:svoflee@gmail.com">svoflee@gmail.com</a>
 * @since 1.0.0
 * @version 1.0.0
 * @see AbstractJUnit4SpringContextTests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DirtiesContextTestExecutionListener.class })
@ActiveProfiles(BaseSpringTest.DEFAULT_TEST_PROFILE_NAME)
@ContextConfiguration(locations = { BaseSpringTest.DEFAULT_SPRING_CONFIG_PATH })
public abstract class BaseSpringTest extends BaseRootTest {

    /**
     * 默认的应用配置位置，由该配置引入框架配置文件及应用相关的配置
     */
    public static final String DEFAULT_SPRING_CONFIG_PATH = "classpath*:/config/approot-config.xml";

    /**
     * 单元测试的Spring Profile名称，默认为test,在Spring的配置文件中，使用以下定义名称定义单元测试的相关配置
     */
    public static final String DEFAULT_TEST_PROFILE_NAME = "test";

    /**
     * The {@link ApplicationContext} that was injected into this test instance
     * via {@link #setApplicationContext(ApplicationContext)}.
     */
    protected ApplicationContext applicationContext;

    /**
     * Set the {@link ApplicationContext} to be used by this test instance,
     * provided via {@link ApplicationContextAware} semantics.
     */
    public final void setApplicationContext(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
