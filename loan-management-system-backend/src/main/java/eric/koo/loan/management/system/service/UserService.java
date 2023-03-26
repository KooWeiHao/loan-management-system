package eric.koo.loan.management.system.service;

import eric.koo.loan.management.system.entity.UserEntity;

import java.util.Optional;

public interface UserService {
    Optional<UserEntity> getByUsername(String username);
    Optional<UserEntity> getByUsernameAndPassword(String username, String password);
    UserEntity createUser(String username, String password);
}
