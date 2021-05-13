package ca.sheriancollege.ghimirsh.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if(auth != null) {
				System.out.println(auth.getName() +
						" was trying to get access to a protected resources!!!"+
						request.getRequestURI());
			}
			response.sendRedirect(request.getContextPath()+"/access-denied");
		}catch(Exception e) {
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
		}
		
	

	}

}
