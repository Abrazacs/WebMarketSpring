package ru.sergeysemenov.webmarketspring.auth.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sergeysemenov.webmarketspring.api.RegisterUserDto;
import ru.sergeysemenov.webmarketspring.api.StringResponse;
import ru.sergeysemenov.webmarketspring.auth.entities.Role;
import ru.sergeysemenov.webmarketspring.auth.entities.User;
import ru.sergeysemenov.webmarketspring.auth.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
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

    public StringResponse tryToSignInNewUser(RegisterUserDto registerUserDto) {
        StringResponse stringResponse = new StringResponse();
        if(registerUserDto.getPassword() == registerUserDto.getConfirmPassword()){
            if(findByUsername(registerUserDto.getUsername()).isPresent()){
                stringResponse.setValue("Пользователь с таким имене уже существует. Попробуйте сного");
            }else{
                userRepository.save(createNewUser(registerUserDto));
                stringResponse.setValue("Регистрация завершена");
            }
        }else{
            stringResponse.setValue("Пароли должны совпадать");
        }
        return stringResponse;
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
