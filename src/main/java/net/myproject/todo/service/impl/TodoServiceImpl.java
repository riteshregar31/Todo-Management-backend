package net.myproject.todo.service.impl;

import lombok.AllArgsConstructor;
import net.myproject.todo.dto.TodoDto;
import net.myproject.todo.entity.Todo;
import net.myproject.todo.repository.TodoRepository;
import net.myproject.todo.service.TodoService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private TodoRepository todoRepository;


    @Override
    public TodoDto addTodo(TodoDto todoDto) {

       //todoDto to todo jpa entity
        Todo todo =new Todo();
        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        //jpa entity
        Todo savedTodo = todoRepository.save(todo);

        //convert saved entity to dto object

        TodoDto savedTodoDto =new TodoDto();
        savedTodoDto.setId(savedTodo.getId());
        savedTodoDto.setTitle(savedTodo.getTitle());
        savedTodoDto.setDescription(savedTodo.getDescription());
        savedTodoDto.setCompleted(savedTodo.isCompleted());

        return savedTodoDto;


    }
}
