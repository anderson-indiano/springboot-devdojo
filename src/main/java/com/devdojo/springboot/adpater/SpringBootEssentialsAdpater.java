/**
 * 
 */
package com.devdojo.springboot.adpater;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * @author Anderson Indiano on 3 de fev. de 2021
 *
 */

@Configuration
public class SpringBootEssentialsAdpater implements WebMvcConfigurer{
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		PageableHandlerMethodArgumentResolver ex = new PageableHandlerMethodArgumentResolver();
		ex.setFallbackPageable(PageRequest.of(0, 5));
		argumentResolvers.add(ex);
	}
	
}
