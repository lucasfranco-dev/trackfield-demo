package com.trackfield.todolist.repositories;

import com.trackfield.todolist.models.Tasks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Tasks, Long> {

    List<Tasks> findByUserCpf(String userCpf);
}
