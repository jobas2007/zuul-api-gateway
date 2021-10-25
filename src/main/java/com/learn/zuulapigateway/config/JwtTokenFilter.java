package com.learn.zuulapigateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("JwtTokenFilter - doFilterInternal() --------");
        // very first time, username/password will be passed to return token
        // then subsequently token will be passed
        String token = request.getHeader("Authorization");
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                log.info("....... token validation passed .......");
                SecurityContextHolder.getContext().setAuthentication(jwtTokenProvider.getAuthentication(token));
            }
        } catch (RuntimeException e) {
            try {
                log.info("token validation failed.................");
                SecurityContextHolder.clearContext();
                response.setContentType("application/json");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().println(new JSONObject().put("exception", "expired or invalid token " + e.getMessage()));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        filterChain.doFilter(request, response);
    }
}
