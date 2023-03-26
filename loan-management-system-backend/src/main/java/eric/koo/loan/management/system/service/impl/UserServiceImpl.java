package eric.koo.loan.management.system.service.impl;

import eric.koo.loan.management.system.entity.UserEntity;
import eric.koo.loan.management.system.repository.UserRepository;
import eric.koo.loan.management.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserEntity> getByUsername(String username) {
        return userRepository.getByUsername(username);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<UserEntity> getByUsernameAndPassword(String username, String password) {
        return userRepository.getByUsername(username)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
    }

    @Transactional
    @Override
    public UserEntity createUser(String username, String password) {
        var newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));

        return userRepository.save(newUser);
    }
}
