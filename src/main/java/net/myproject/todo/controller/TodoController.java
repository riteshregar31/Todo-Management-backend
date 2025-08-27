package net.myproject.todo.controller;

import lombok.AllArgsConstructor;
import net.myproject.todo.dto.TodoDto;
import net.myproject.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
 public class TodoController {

private TodoService todoService;

//add todo rest api
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        TodoDto savedTodo =todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }


    //get todo rest api
     @GetMapping("{id}")
    public  ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId){
        TodoDto todoDto = todoService.getTodo(todoId);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);

    }
}
