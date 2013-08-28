/*
 * Copyright (c)  http://www.svoflee.com All rights reserved.
 *  
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
 *       
 * Product:  svoflee-spartacus
 * Creator:  svoflee@gmail.com 
 */

package com.svoflee.spartacus.test;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;

import com.svoflee.spartacus.core.log.Logger;

/**
 * BaseRootTest 是所有单元测试的基类
 * <p>
 * 用法:
 * <p>
 * <code>public class YourTest extends BaseRootTest{...}</code>
 * 
 * @author <a href="mailto:svoflee@gmail.com">svoflee@gmail.com</a>
 * @since 1.0.0
 * @version 1.0.0
 */
public abstract class BaseRootTest {

    /**
     * 测试的log4j-test.xml默认位置,注意命名规则
     */
    private static final String DEFAULT_LOG4J_CONFIG_PATH = "src/test/resources/log4j-test.xml";

    protected final Logger log = Logger.getLogger(this.getClass());

    private volatile long begin;

    protected void p(Object o) {
        System.out.println(o);
    }

    @BeforeClass
    public static void beforeClass() {
        try {
            DOMConfigurator.configure(DEFAULT_LOG4J_CONFIG_PATH);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Before
    public void beforeTest() {
        begin = System.nanoTime();
        log.debug("测试开始>>>>>>");
    }

    @After
    public void afterTest() {
        long end = System.nanoTime();
        log.debug("测试结束>>>>>>");

        float dur = (end - begin) / (1000f * 1000f);
        log.debug("执行耗时[ {} 毫秒] ,[ {} 秒]", dur, dur / 1000f);

    }

    @AfterClass
    public static void afterClass() {
    }
}
