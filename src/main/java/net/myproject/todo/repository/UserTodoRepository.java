package net.myproject.todo.repository;

import net.myproject.todo.entity.User;
import net.myproject.todo.entity.Todo;
import net.myproject.todo.entity.UserTodo;
import net.myproject.todo.entity.UserTodoId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTodoRepository extends JpaRepository<UserTodo, UserTodoId> {

    // Get all todos assigned to a user
    List<UserTodo> findByUser(User user);

    // Find one mapping for a given user and todo
    Optional<UserTodo> findByUserAndTodo(User user, Todo todo);
    void deleteAllByTodo(Todo todo);

}
