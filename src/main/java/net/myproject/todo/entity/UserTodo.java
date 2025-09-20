package net.myproject.todo.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user_todos")
public class UserTodo {

    @EmbeddedId
    private UserTodoId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("todoId")
    @JoinColumn(name = "todo_id")
    private Todo todo;

    private boolean completed;

    public UserTodo(User user, Todo todo, boolean completed) {
        this.user = user;
        this.todo = todo;
        this.completed = completed;
        this.id = new UserTodoId(user.getId(), todo.getId());
    }
}
