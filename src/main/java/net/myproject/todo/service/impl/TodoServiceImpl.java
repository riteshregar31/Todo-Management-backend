package net.myproject.todo.service.impl;

import lombok.AllArgsConstructor;
import net.myproject.todo.dto.TodoDto;
import net.myproject.todo.entity.Todo;
import net.myproject.todo.entity.User;
import net.myproject.todo.entity.UserTodo;
import net.myproject.todo.exception.ResourceNotFoundException;
import net.myproject.todo.repository.TodoRepository;
import net.myproject.todo.repository.UserRepository;
import net.myproject.todo.repository.UserTodoRepository;
import net.myproject.todo.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;
    private UserRepository userRepository;
    private UserTodoRepository userTodoRepository;
    private ModelMapper modelMapper;

    //  Admin Only
    @Override
    public TodoDto addTodo(TodoDto todoDto) {
        Todo todo = modelMapper.map(todoDto, Todo.class);
        Todo savedTodo = todoRepository.save(todo);
        return modelMapper.map(savedTodo, TodoDto.class);
    }

    @Override
    public TodoDto getTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));
        return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
        List<Todo> todos = todoRepository.findAll();
        return todos.stream()
                .map(todo -> modelMapper.map(todo, TodoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(TodoDto todoDto, Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + id));
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        Todo updatedTodo = todoRepository.save(todo);
        return modelMapper.map(updatedTodo, TodoDto.class);
    }

    @Override
    public void deleteTodo(Long id) {
        Todo todo = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not exist with id " + id));

        // Delete all userTodo entries referencing this todo first
        userTodoRepository.deleteAllByTodo(todo);

        // Now delete the todo
        todoRepository.delete(todo);
    }

    //  User Specific
    @Override
    public List<TodoDto> getTodosForUser(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));

        List<Todo> todos = todoRepository.findAll();

        return todos.stream().map(todo -> {
            TodoDto dto = modelMapper.map(todo, TodoDto.class);
            userTodoRepository.findByUserAndTodo(user, todo)
                    .ifPresent(userTodo -> dto.setCompleted(userTodo.isCompleted()));
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void updateTodoCompletion(String username, Long todoId, boolean completed) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username " + username));
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id " + todoId));

        UserTodo userTodo = userTodoRepository.findByUserAndTodo(user, todo)
                .orElse(new UserTodo(user, todo, completed));

        userTodo.setCompleted(completed);
        userTodoRepository.save(userTodo);
    }

    //  Old behavior: complete/incomplete
    @Override
    public TodoDto completeTodo(Long todoId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found: " + todoId));

        UserTodo userTodo = userTodoRepository.findByUserAndTodo(user, todo)
                .orElse(new UserTodo(user, todo, true));

        userTodo.setCompleted(true);
        userTodoRepository.save(userTodo);

        TodoDto dto = modelMapper.map(todo, TodoDto.class);
        dto.setCompleted(true);
        return dto;
    }

    @Override
    public TodoDto inCompleteTodo(Long todoId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + username));
        Todo todo = todoRepository.findById(todoId)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found: " + todoId));

        UserTodo userTodo = userTodoRepository.findByUserAndTodo(user, todo)
                .orElse(new UserTodo(user, todo, false));

        userTodo.setCompleted(false);
        userTodoRepository.save(userTodo);

        TodoDto dto = modelMapper.map(todo, TodoDto.class);
        dto.setCompleted(false);
        return dto;
    }
}
