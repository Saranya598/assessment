package com.multichoice.test.core.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;

import com.multichoice.test.core.entity.Constants;

@Configuration
public class UserAuthorization extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws ServletException, IOException {
		if (request.getHeader("Authorization") != null) {
			String authHeader = request.getHeader("Authorization").split(" ")[1];
			byte[] decoded = Base64.getDecoder().decode(authHeader);
			String auth = new String(decoded, StandardCharsets.UTF_8);
			if (!(auth.split(":")[0].equals(Constants.USERNAME) && auth.split(":")[1].equals(Constants.PASSWORD))) {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED,Constants.INVALID_AUTH);
				return false;
			}
		} else {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,Constants.INVALID_AUTH);
			return false;
		}
		return true;
	}

	/**
	 * Create a bean of the RequestInterceptor for creating the
	 * {@link MappedInterceptor}
	 * 
	 * @return an instance of {@link RequestInterceptor}
	 */
	@Bean
	public UserAuthorization interceptor() {
		return new UserAuthorization();
	}

	/**
	 * Create a bean of MappedInterceptor for delegating calls to
	 * {@link HandlerInterceptor} which is used to intercept the incoming requests
	 * that match the includePatterns
	 * 
	 * @return an instance {@link MappedInterceptor}
	 */
	@Bean
	public MappedInterceptor mapper() {
		return new MappedInterceptor(null, null, interceptor());
	}
}
