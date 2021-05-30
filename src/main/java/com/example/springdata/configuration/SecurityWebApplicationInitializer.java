package com.example.springdata.configuration;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.stereotype.Component;

@Component // necessarily to inherit from AbstractSecurityWebApplicationInitializer to get secured
public class SecurityWebApplicationInitializer extends AbstractSecurityWebApplicationInitializer {
}
