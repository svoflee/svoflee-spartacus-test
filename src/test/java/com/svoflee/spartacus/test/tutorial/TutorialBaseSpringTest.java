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

package com.svoflee.spartacus.test.tutorial;

import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import com.svoflee.spartacus.test.BaseSpringTest;

/**
 * TutorialBaseSpringTest 是BaseSpringTest的例子，使用需要Spring配置文件
 * 
 * @author <a href="mailto:svoflee@gmail.com">svoflee@gmail.com</a>
 * @since 1.0.0
 */
@ContextConfiguration(locations = { BaseSpringTest.DEFAULT_SPRING_CONFIG_PATH })
public class TutorialBaseSpringTest extends BaseSpringTest {

    @Test
    public void testTime() {
        log.debug("testTime");
        Assert.isTrue(true);
    }

    @Mock
    private DemoService demoService;

    @Mock
    private DemoDao demoDao;

    /**
     * 使用Mock进行单元测试的例子
     */
    @Test
    public void testMock() {
        List<Model> list = new ArrayList<Model>();
        Model model = new Model("id", "name");
        list.add(model);

        // 先设置预期
        Mockito.when(demoDao.getModel("id")).thenReturn(model);

        Mockito.when(demoService.getModel("id")).thenReturn(model);

        Model m = demoService.getModel("id");

        assertSame(m, model);

    }

    public class Model {

        private String id;

        private String name;

        public Model(String id, String name) {
            super();
            this.id = id;
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public interface DemoDao {

        Model getModel(String id);
    }

    public interface DemoService {

        Model getModel(String id);
    }
}
