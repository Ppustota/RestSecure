package zura.pustota.restsecure.service;

import zura.pustota.restsecure.model.AppUser;

import java.util.List;

public interface AppUserService {
    List<AppUser> findALl();
    AppUser findById(Long id);
    AppUser findByUsername(String username);
    AppUser saveUser(AppUser user);
    void deleteUser(Long id);
}
