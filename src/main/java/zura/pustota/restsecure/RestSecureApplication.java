package zura.pustota.restsecure;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import zura.pustota.restsecure.model.AppUser;
import zura.pustota.restsecure.model.Role;
import zura.pustota.restsecure.service.AppUserService;

@SpringBootApplication
public class RestSecureApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestSecureApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(AppUserService userService){
        return args -> {
            userService.saveUser(new AppUser(null, "john", "1234", "john", "doe", Role.USER));
            userService.saveUser(new AppUser(null, "Arnold", "1234", "arnold", "terminator", Role.ADMIN));
        };
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }
}
