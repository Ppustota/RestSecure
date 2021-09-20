package zura.pustota.restsecure.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import zura.pustota.restsecure.model.AppUser;
import zura.pustota.restsecure.service.AppUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AppUserService userService;

    @GetMapping("/users/{id}")
    public ResponseEntity<AppUser> getByUsername(@PathVariable Long id){
        return ResponseEntity.ok().body(userService.findById(id));
    }
}
