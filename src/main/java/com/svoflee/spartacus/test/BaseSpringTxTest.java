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

import javax.sql.DataSource;

import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.context.transaction.AfterTransaction;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Transactional;

/**
 * BaseSpringTxTest 是支持Spring事务管理的测试基类,
 * 移植至 {@link AbstractTransactionalJUnit4SpringContextTests} 测试时，
 * 需要注入 {@link DataSource},如果应用有不止一个DataSource，
 * 需要使用{@link Qualifier}指定your_datasource_name *
 * 
 * @author <a href="mailto:svoflee@gmail.com">svoflee@gmail.com</a>
 * @since 1.0.0
 * @version 1.0.0
 */
@TestExecutionListeners({ TransactionalTestExecutionListener.class })
@Transactional
public abstract class BaseSpringTxTest extends BaseSpringTest {

    protected DataSource dataSource;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @BeforeTransaction
    public void beforeTransaction() {
        log.debug("BeforeTransaction");
    }

    @AfterTransaction
    public void afterTransaction() {
        log.debug("AfterTransaction");
    }
}
