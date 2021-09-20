package zura.pustota.restsecure.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zura.pustota.restsecure.jwtutil.JwtBuilder;
import zura.pustota.restsecure.model.AppUser;
import zura.pustota.restsecure.service.AppUserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
        private final AppUserService userService;
        private final JwtBuilder jwtBuilder;

        @GetMapping("/refresh")
        public void refresh(HttpServletRequest request, HttpServletResponse response) throws IOException {
                String authorizationHeader = request.getHeader(AUTHORIZATION);
                if(authorizationHeader!=null && authorizationHeader.startsWith("Bearer ")){
                        try {
                                String refreshToken = authorizationHeader.substring("Bearer ".length());
                                String username = jwtBuilder.getUsernameFromJwt(refreshToken);
                                AppUser user = userService.findByUsername(username);
                                String accessToken = jwtBuilder.createToken(user, request, 10);
                                Map<String, String> tokens = new HashMap<>();
                                tokens.put("access_token", accessToken);
                                tokens.put("refresh_token", refreshToken);
                                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
                        } catch (Exception e){
                                response.setHeader("error", e.getMessage());
                                response.setStatus(FORBIDDEN.value());
                                Map<String, String> error =new HashMap<>();
                                error.put("error_message", e.getMessage());
                                response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                                new ObjectMapper().writeValue(response.getOutputStream(), error);
                        }

                } else throw new RemoteException("Token is missing");
        }
}
