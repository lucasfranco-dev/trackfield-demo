package com.trackfield.todolist.repositories;

import com.trackfield.todolist.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserCpf(String userCpf);

    List<Task> findByUserCpfAndFinished(String userCpf, boolean finished);
}
