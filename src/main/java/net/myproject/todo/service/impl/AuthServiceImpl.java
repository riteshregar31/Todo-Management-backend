package net.myproject.todo.service.impl;

import lombok.AllArgsConstructor;
import net.myproject.todo.dto.LoginDto;
import net.myproject.todo.dto.RegisterDto;
import net.myproject.todo.entity.Role;
import net.myproject.todo.entity.User;
import net.myproject.todo.exception.TodoAPIException;
import net.myproject.todo.repository.RoleRepository;
import net.myproject.todo.repository.UserRepository;
import net.myproject.todo.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor

public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private AuthenticationManager authenticationManager;
    @Override
    public String register (RegisterDto registerDto){

        if(userRepository.existsByUsername(registerDto.getUsername())){
            throw  new TodoAPIException(HttpStatus.BAD_REQUEST,"username already exists");
        }

        if(userRepository.existsByEmail(registerDto.getEmail())){
            throw  new TodoAPIException(HttpStatus.BAD_REQUEST,"email is already exists");
        }
        User user =new User();
        user.setEmail(registerDto.getEmail());
       user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsername(registerDto.getUsername());
        user.setName(registerDto.getName());

        Set<Role>roles=new HashSet<>();
        Role userRole = roleRepository.findByName("ROLE_USER");
        roles.add(userRole);

        user.setRoles(roles);
        userRepository.save(user);


        return "user registered successfully";
    }

    @Override
    public String login(LoginDto loginDto) {
     Authentication authentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
             loginDto.getUsernameOrEmail(),
             loginDto.getPassword()
     ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
     return "user logged-in successfully" ;
    }
}
