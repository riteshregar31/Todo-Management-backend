package net.myproject.todo.service.impl;

import lombok.AllArgsConstructor;
import net.myproject.todo.dto.TodoDto;
import net.myproject.todo.entity.Todo;
import net.myproject.todo.exception.ResourceNotFoundException;
import net.myproject.todo.repository.TodoRepository;
import net.myproject.todo.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;

    private ModelMapper modelMapper;

    @Override
    public TodoDto addTodo(TodoDto todoDto) {

       //todoDto to todo jpa entity
        Todo todo=modelMapper.map(todoDto,Todo.class);

        //jpa entity
        Todo savedTodo = todoRepository.save(todo);

        //convert saved entity to dto object
        TodoDto savedTodoDto=modelMapper.map(savedTodo,TodoDto.class);

        return savedTodoDto;


    }

    @Override
    public TodoDto getTodo(Long id) {
      Todo todo=todoRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("todo not found with given id"+id));
      return modelMapper.map(todo, TodoDto.class);
    }

    @Override
    public List<TodoDto> getAllTodos() {
       List<Todo>todos =todoRepository.findAll();
       return todos.stream().map((todo)->modelMapper.map(todo,TodoDto.class)).collect(Collectors.toList());
    }
}
