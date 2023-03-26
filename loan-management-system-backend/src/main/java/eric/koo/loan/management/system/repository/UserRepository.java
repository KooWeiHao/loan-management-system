package eric.koo.loan.management.system.repository;

import eric.koo.loan.management.system.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> getByUsername(String username);
}
