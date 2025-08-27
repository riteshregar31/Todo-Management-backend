package net.myproject.todo.service;

import net.myproject.todo.dto.TodoDto;

public interface TodoService {
    TodoDto addTodo(TodoDto todoDto);
    TodoDto getTodo(Long id);
}
