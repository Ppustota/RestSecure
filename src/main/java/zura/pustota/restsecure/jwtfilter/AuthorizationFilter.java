package zura.pustota.restsecure.jwtfilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.filter.OncePerRequestFilter;
import zura.pustota.restsecure.jwtutil.JwtBuilder;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;


public class AuthorizationFilter extends OncePerRequestFilter {
    private final JwtBuilder jwtBuilder = new JwtBuilder();
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        if(request.getContextPath().equals("/api/login") || request.getServletPath().equals("/token/refresh/**"))
                filterChain.doFilter(request, response);
        else {
            String authorizationHeader = request.getHeader(AUTHORIZATION);
            if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
                try{
                    String token = authorizationHeader.substring("Bearer ".length());
                    jwtBuilder.authorizationChain(token);
                    filterChain.doFilter(request, response);

                }catch (Exception e){
                    response.setHeader("error", e.getMessage());
                    response.setStatus(FORBIDDEN.value());
                    Map<String, String> error = new HashMap<>();
                    error.put("error", e.getMessage());
                    new ObjectMapper().writeValue(response.getOutputStream(), error);
                }
            } else
                filterChain.doFilter(request, response);

        }

    }
}
