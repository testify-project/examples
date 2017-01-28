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

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManagerFactory;
import static javax.persistence.Persistence.createEntityManagerFactory;
import javax.sql.DataSource;
import static org.hibernate.cfg.AvailableSettings.DATASOURCE;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.testify.ContainerInstance;
import org.testify.annotation.Fixture;

/**
 * Test fixture module that defines the datasource of a postgreSQL running
 * inside of a container.
 *
 * @author saden
 */
@Fixture
@Configuration
public class TestModule {

    @Primary
    @Bean
    DataSource testDataSource(@Qualifier("postgres") ContainerInstance instance) {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName(instance.getHost());
        dataSource.setPortNumber(instance.findFirstPort().get());
        //Default postgres image database name, user and postword
        dataSource.setDatabaseName("postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("mysecretpassword");

        return dataSource;
    }

    /**
     * Provides an entity manager factory based on the data source.
     *
     * @param ds the data source
     * @return the entity manager factory
     */
    @Primary
    @Bean
    EntityManagerFactory testEntityManagerFactory(DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(DATASOURCE, dataSource);

        return createEntityManagerFactory("test.example.greeter", properties);
    }
}
