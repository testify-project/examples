#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
/*
 * Copyright 2016-2017 Testify Project.
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
 */
package examples.database;

import javax.inject.Singleton;
import javax.sql.DataSource;
import org.glassfish.hk2.api.Factory;
import org.jvnet.hk2.annotations.Service;
import org.postgresql.ds.PGSimpleDataSource;

/**
 * A factory class that provides production PostgreSQL data source.
 *
 * @author saden
 */
@Service
public class PostgresSourceProvider implements Factory<DataSource> {

    @Singleton
    @Override
    public DataSource provide() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("production.acme.com");
        dataSource.setPortNumber(5432);
        dataSource.setDatabaseName("postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("mysecretpassword");

        return dataSource;
    }

    @Override
    public void dispose(DataSource instance) {
    }
}
