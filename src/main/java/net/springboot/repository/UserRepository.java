package net.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import net.springboot.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}