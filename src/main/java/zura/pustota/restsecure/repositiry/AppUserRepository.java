package zura.pustota.restsecure.repositiry;

import org.springframework.data.jpa.repository.JpaRepository;
import zura.pustota.restsecure.model.AppUser;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
}
