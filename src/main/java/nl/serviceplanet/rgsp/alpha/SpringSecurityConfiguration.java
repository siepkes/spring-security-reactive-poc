package nl.serviceplanet.rgsp.alpha;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableReactiveMethodSecurity
public class SpringSecurityConfiguration  {




}
