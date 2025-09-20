package net.myproject.todo.service;

import net.myproject.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {

    // Admin only
    TodoDto addTodo(TodoDto todoDto);
    TodoDto getTodo(Long id);
    List<TodoDto> getAllTodos();
    TodoDto updateTodo(TodoDto todoDto, Long id);
    void deleteTodo(Long id);

    // User-specific
    List<TodoDto> getTodosForUser(String username);

    // Update completion status
    void updateTodoCompletion(String username, Long todoId, boolean completed);

    TodoDto completeTodo(Long todoId, String username);
    TodoDto inCompleteTodo(Long todoId, String username);
}
