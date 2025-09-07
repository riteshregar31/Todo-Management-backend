package net.myproject.todo.repository;

import net.myproject.todo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role,Long> {
Role findByName(String name);
}
