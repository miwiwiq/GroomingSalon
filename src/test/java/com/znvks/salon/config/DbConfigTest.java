package com.znvks.salon.config;

import com.znvks.salon.model.config.DbConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DbConfig.class)
public class DbConfigTest {
}
