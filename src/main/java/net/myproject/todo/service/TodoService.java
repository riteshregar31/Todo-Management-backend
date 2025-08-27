package net.myproject.todo.service;

import net.myproject.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto addTodo(TodoDto todoDto);
    TodoDto getTodo(Long id);

    List<TodoDto> getAllTodos();
}
