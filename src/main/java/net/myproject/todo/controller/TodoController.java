package net.myproject.todo.controller;

import lombok.AllArgsConstructor;
import net.myproject.todo.dto.TodoDto;
import net.myproject.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("api/todos")
@AllArgsConstructor
 public class TodoController {

private TodoService todoService;

//add todo rest api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<TodoDto> addTodo(@RequestBody TodoDto todoDto){
        TodoDto savedTodo =todoService.addTodo(todoDto);
        return new ResponseEntity<>(savedTodo, HttpStatus.CREATED);
    }


    //get todo rest api
     @GetMapping("{id}")
     @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public  ResponseEntity<TodoDto> getTodo(@PathVariable("id") Long todoId){
        TodoDto todoDto = todoService.getTodo(todoId);
        return new ResponseEntity<>(todoDto, HttpStatus.OK);

    }

    //get all todos rest api
    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<TodoDto>>getAllTodos(){
        List<TodoDto>todos=todoService.getAllTodos();
        return  ResponseEntity.ok(todos);
    }

    //update todo rest api
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TodoDto> updateTodo(@RequestBody TodoDto todoDto,@PathVariable("id") Long id){
        TodoDto updatedTodo = todoService.updateTodo(todoDto, id);
        return ResponseEntity.ok(updatedTodo);
    }

    //Build delete Todo rest api
@DeleteMapping("{id}")
@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteTodo(@PathVariable("id") Long id){
        todoService.deleteTodo(id);
        return ResponseEntity.ok("todo deleted successfully");
    }

    //complete todo rest api
    @PatchMapping("{id}/complete")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable("id") Long id){
        TodoDto updatedDto = todoService.completeTodo(id);
        return ResponseEntity.ok(updatedDto);
    }

    //incomplete todo rest api
    @PatchMapping("{id}/in-complete")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable Long id){
        TodoDto updatedDto=todoService.inCompleteTodo(id);
        return  ResponseEntity.ok(updatedDto);
    }
}
