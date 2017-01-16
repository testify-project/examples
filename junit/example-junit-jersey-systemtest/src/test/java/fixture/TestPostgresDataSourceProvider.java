/*
 * Copyright 2016-2017 Sharmarke Aden.
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
package fixture;

import org.testify.ContainerInstance;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.sql.DataSource;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.Rank;
import org.postgresql.ds.PGSimpleDataSource;

/**
 * A provider of a JDBC PostgreSQL test DataSource. Note that we do not annotate
 * this class with @Service because we don't want it to be discovered and used
 * every time.
 *
 * @author saden
 */
@Rank(Integer.MAX_VALUE)
public class TestPostgresDataSourceProvider implements Factory<DataSource> {

    private final ContainerInstance instance;

    @Inject
    TestPostgresDataSourceProvider(@Named("postgres") ContainerInstance instance) {
        this.instance = instance;
    }

    @Singleton
    @Override
    public DataSource provide() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(instance.getHost());
        dataSource.setPortNumber(instance.findFirstPort().get());
        //Default postgres image database name, user and postword
        dataSource.setDatabaseName("postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("mysecretpassword");

        return dataSource;
    }

    @Override
    public void dispose(DataSource instance) {
    }

}
