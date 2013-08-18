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
 * Author:   svoflee@gmail.com 
 */

package com.svoflee.spartacus.test.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.ext.h2.H2Connection;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import com.svoflee.spartacus.core.log.Logger;

/**
 * DbUnitUtils 是使用DBUnit初始化测试用H2嵌入式数据库数据的工具类.
 * 
 * @author <a href="mailto:svoflee@gmail.com">svoflee@gmail.com</a>
 * @since 1.0.0
 * @version 1.0.0
 */
public class DbUnitUtils {

    private static Logger logger = Logger.getLogger(DbUnitUtils.class);

    private static final ResourceLoader resourceLoader = new DefaultResourceLoader();

    /**
     * 清除并插入XML数据文件到H2数据库.
     * XML数据文件中涉及的表在插入数据前会先进行清除.
     * 
     * @param xmlFilePaths 符合Spring Resource路径格式的文件列表.
     */
    public static void loadData(DataSource h2DataSource, String... xmlFilePaths) throws Exception {
        execute(DatabaseOperation.CLEAN_INSERT, h2DataSource, xmlFilePaths);
    }

    /**
     * 插入XML数据文件到H2数据库.
     */
    public static void appendData(DataSource h2DataSource, String... xmlFilePaths) throws Exception {
        execute(DatabaseOperation.INSERT, h2DataSource, xmlFilePaths);
    }

    /**
     * 在H2数据库中删除XML数据文件中涉及的表的数据.
     */
    public static void removeData(DataSource h2DataSource, String... xmlFilePaths) throws Exception {
        execute(DatabaseOperation.DELETE_ALL, h2DataSource, xmlFilePaths);
    }

    /**
     * 按DBUnit Operation执行XML数据文件的数据.
     * 
     * @param xmlFilePaths
     *            符合Spring Resource路径格式的文件列表.
     */
    private static void execute(DatabaseOperation operation, DataSource h2DataSource, String... xmlFilePaths)
            throws DatabaseUnitException, SQLException {
        IDatabaseConnection connection = new H2Connection(h2DataSource.getConnection(), "");
        for (String xmlPath : xmlFilePaths) {
            try {
                InputStream input = resourceLoader.getResource(xmlPath).getInputStream();
                IDataSet dataSet = new FlatXmlDataSetBuilder().setColumnSensing(true).build(input);
                operation.execute(connection, dataSet);
            }
            catch (IOException e) {
                logger.warn(xmlPath + " file not found", e);
            }
        }
    }
}
