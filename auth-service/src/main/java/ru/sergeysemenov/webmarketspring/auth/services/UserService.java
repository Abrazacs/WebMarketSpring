package ru.sergeysemenov.webmarketspring.auth.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergeysemenov.webmarketspring.api.RegisterUserDto;
import ru.sergeysemenov.webmarketspring.auth.entities.Role;
import ru.sergeysemenov.webmarketspring.auth.entities.User;
import ru.sergeysemenov.webmarketspring.auth.exceptions.RegistrationException;
import ru.sergeysemenov.webmarketspring.auth.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final BCryptPasswordEncoder passwordEncoder;

    public Optional<User> findByUsername (String username){
        return userRepository.findByUsername(username);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(()->
                new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public void tryToSignInNewUser(RegisterUserDto registerUserDto) throws RegistrationException {
        if (userRepository.findByUsername(registerUserDto.getUsername()).isPresent()){
            throw new RegistrationException("Логин занят");
        }
        if(userRepository.findByEmail(registerUserDto.getEmail()).isPresent()){
            throw new RegistrationException("Почта занята");
        }
        User user = createNewUser(registerUserDto);
        userRepository.save(user);

    }

    private User createNewUser(RegisterUserDto registerUserDto) {
        User user = new User();
        user.setUsername(registerUserDto.getUsername());
        user.setRoles(generateRolesCollection());
        user.setEmail(registerUserDto.getEmail());
        user.setPassword(convertPassword(registerUserDto.getPassword()));
        return user;
    }

    private Collection<Role> generateRolesCollection() {
        Collection<Role> roles = new ArrayList<>();
        roles.add(roleService.getUserRole());
        return roles;
    }

    private String convertPassword(String password){
        return passwordEncoder.encode(password);
    }
}
