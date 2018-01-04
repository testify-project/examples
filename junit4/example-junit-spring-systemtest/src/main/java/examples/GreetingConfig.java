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

import static javax.persistence.Persistence.createEntityManagerFactory;

import static org.hibernate.cfg.AvailableSettings.DATASOURCE;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.convention.NamingConventions;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import examples.resource.repository.RepositoryPackage;

/**
 * Greeter Application Spring MVC Java Config.
 *
 * @author saden
 */
@Configuration
@EnableWebMvc
@ComponentScan
@EnableJpaRepositories(basePackageClasses = RepositoryPackage.class)
public class GreetingConfig {

    /**
     * Adds supports common Java annotations out of the box. Including JPA annotations.
     *
     * @return common annotation processor instance.
     */
    @Bean
    static CommonAnnotationBeanPostProcessor annotationBeanPostProcessor() {
        return new CommonAnnotationBeanPostProcessor();
    }

    /**
     * An in-memory H2 database data source.
     *
     * @return the data source
     */
    @Bean
    DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("production.acme.com");
        dataSource.setPortNumber(5432);
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
    @Bean
    EntityManagerFactory entityManagerFactory(DataSource dataSource) {
        Map<String, Object> properties = new HashMap<>();
        properties.put(DATASOURCE, dataSource);

        return createEntityManagerFactory("example.greetings", properties);
    }

    /**
     * Provides JPA based Spring transaction manager.
     *
     * @param entityManagerFactory the entity manager factory
     * @return jpa transaction manager
     */
    @Bean
    PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager(
                entityManagerFactory);

        return transactionManager;
    }

    /**
     * Provides a singleton model mapper instance.
     *
     * @return a model mapper instance
     */
    @Bean
    ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        org.modelmapper.config.Configuration configuration = mapper.getConfiguration();
        configuration.setMatchingStrategy(MatchingStrategies.STRICT);
        configuration.setFieldAccessLevel(
                org.modelmapper.config.Configuration.AccessLevel.PUBLIC);
        configuration.setMethodAccessLevel(
                org.modelmapper.config.Configuration.AccessLevel.PUBLIC);
        configuration.setAmbiguityIgnored(false);
        configuration.setDestinationNamingConvention(NamingConventions.JAVABEANS_MUTATOR);
        configuration.setSourceNamingConvention(NamingConventions.JAVABEANS_ACCESSOR);

        return mapper;
    }

}
