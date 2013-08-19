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

package com.svoflee.spartacus.test.tutorial;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.svoflee.spartacus.test.BaseSpringTest;
import com.svoflee.spartacus.test.BaseSpringTxTest;

/**
 * TutorialBaseSpringTxTest 是BaseSpringTxTest的例子程序
 * 测试时，需要注入{@link DataSource},如果应用有不止一个DataSource，
 * 需要使用{@link Qualifier}指定your_datasource_name
 * 
 * @author <a href="mailto:svoflee@gmail.com">svoflee@gmail.com</a>
 * @since 1.0.0
 */
@ContextConfiguration(locations = { BaseSpringTest.DEFAULT_SPRING_CONFIG_PATH })
@Transactional
public class TutorialBaseSpringTxTest extends BaseSpringTxTest {

    @Override
    @Autowired
    @Qualifier("your_datasource_name")
    public void setDataSource(DataSource dataSource) {
        super.setDataSource(dataSource);
    }

    @Test
    @Rollback(true)
    public void testTxTime() {
        log.debug("testTxTime");
        Assert.isTrue(true);
    }
}
