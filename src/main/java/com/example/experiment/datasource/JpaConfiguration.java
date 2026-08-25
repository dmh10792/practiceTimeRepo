package com.example.experiment.datasource;

import com.example.experiment.character.CharacterBaseInterface;
import com.example.experiment.todo.TodoBaseInterface;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackageClasses = {CharacterBaseInterface.class, TodoBaseInterface.class},
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
public class JpaConfiguration {

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            DataSource dataSource, EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.physical_naming_strategy", "org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy");

        return entityManagerFactoryBuilder
                .dataSource(dataSource)
                .properties(properties)
                .packages(CharacterBaseInterface.class, TodoBaseInterface.class)
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager transactionManager(
            @Qualifier("entityManagerFactory") LocalContainerEntityManagerFactoryBean awsRdsEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(awsRdsEntityManagerFactory.getObject()));
    }

}
