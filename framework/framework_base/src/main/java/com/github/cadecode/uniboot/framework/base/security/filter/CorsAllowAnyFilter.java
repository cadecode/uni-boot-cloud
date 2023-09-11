package com.github.cadecode.uniboot.framework.base.security.filter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * Filter, cors support
 *
 * @author Cade Li
 * @since 2023/9/11
 */
public class CorsAllowAnyFilter extends CorsFilter {

    static UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();

    static {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.setAllowCredentials(true);
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);
    }

    public CorsAllowAnyFilter() {
        super(urlBasedCorsConfigurationSource);
    }
}
