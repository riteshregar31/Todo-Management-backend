package net.myproject.todo.service;

import net.myproject.todo.dto.LoginDto;
import net.myproject.todo.dto.RegisterDto;

public interface AuthService {
    String register(RegisterDto registerDto);
    String login(LoginDto loginDto);
}
