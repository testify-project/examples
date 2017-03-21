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
package examples;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import static org.hibernate.cfg.AvailableSettings.DATASOURCE;
import org.modelmapper.ModelMapper;
import org.modelmapper.config.Configuration;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

/**
 * Greeting Spring Boot Application.
 *
 * @author saden
 */
@SpringBootApplication
public class GreetingApplication {

    public static void main(String[] args) throws Exception {
        GreetingApplication application = new GreetingApplication();

        application.run(args);
    }

    public void run(String[] args) {
        SpringApplication.run(GreetingApplication.class, args);
    }

    @Bean
    DataSource productionDataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("production.acme.com");
        dataSource.setPortNumber(5432);
        dataSource.setDatabaseName("postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("mysecretpassword");

        return dataSource;
    }

    @Bean
    LocalContainerEntityManagerFactoryBean entityManagerFactory(
            EntityManagerFactoryBuilder builder, DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(DATASOURCE, dataSource);

        return builder.dataSource(dataSource)
                .persistenceUnit("example.greeter")
                .properties(properties)
                .build();
    }

    @Bean
    ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        Configuration configuration = mapper.getConfiguration();
        configuration.setMatchingStrategy(MatchingStrategies.STRICT);
        configuration.setFieldAccessLevel(Configuration.AccessLevel.PUBLIC);
        configuration.setMethodAccessLevel(Configuration.AccessLevel.PUBLIC);
        configuration.setAmbiguityIgnored(false);
        configuration.setDestinationNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
        configuration.setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR);

        return mapper;
    }
}
