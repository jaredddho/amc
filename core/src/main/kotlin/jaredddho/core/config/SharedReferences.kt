package jaredddho.core.config

import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan(value = ["jaredddho"])
@EntityScan(value = ["jaredddho"])
class SharedReferences