package zura.pustota.restsecure.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zura.pustota.restsecure.model.AppUser;
import zura.pustota.restsecure.repositiry.AppUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AppUserServiceImpl implements AppUserService, UserDetailsService {
    private final AppUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AppUser> appUser = userRepository.findByUsername(username);
        if(!appUser.isPresent()){
            log.error("User with username: {} not found", username);
            throw new UsernameNotFoundException("User not found");
        }
        else {
            AppUser user = appUser.get();
            Set<SimpleGrantedAuthority> authorities = user.getRole().getAuthorities();
            return new User(
                    user.getUsername(),
                    user.getPassword(),
                    authorities
            );
        }
    }

    @Override
    public List<AppUser> findALl() {
        return userRepository.findAll();
    }

    @Override
    public AppUser findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()->new RuntimeException(("User not found")));
    }

    @Override
    public AppUser findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(()->new RuntimeException("User not found"));
    }

    @Override
    public AppUser saveUser(AppUser user) {
        user.setId(0L);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("User added: {}", user.getUsername());
        return userRepository.save(user);

    }

    @Override
    public void deleteUser(Long id) {
        Optional<AppUser> byId = userRepository.findById(id);
        if(byId.isPresent()){
        userRepository.delete(byId.get());
        log.info("User deleted: {}", byId.get().getId()); }
        else {
            log.error("User with id: {} not found", id);
            throw new RuntimeException("User Not found");}
    }


}
