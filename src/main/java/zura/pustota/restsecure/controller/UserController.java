package zura.pustota.restsecure.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import zura.pustota.restsecure.model.AppUser;
import zura.pustota.restsecure.service.AppUserService;

import java.net.URI;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final AppUserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> findAllUsers(){
        return ResponseEntity.ok().body(userService.findALl());
    }
    @PostMapping("/users")
    public ResponseEntity<AppUser> addUser(@RequestBody AppUser user){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/users").toUriString());
        return ResponseEntity.created(uri).body(userService.saveUser(user));
    }
    @GetMapping("/users/username/{username}")
    public ResponseEntity<AppUser> getByUsername(@PathVariable String username){
        return ResponseEntity.ok().body(userService.findByUsername(username));
    }


    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
    }
}
