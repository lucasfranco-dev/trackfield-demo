package com.trackfield.todolist.models;

import com.trackfield.todolist.Services.UserService;
import com.trackfield.todolist.dtos.TaskDTO;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = Tasks.TABLE_NAME)
@Entity(name = Tasks.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Tasks {
    public static final String TABLE_NAME = "tarefas";

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    private String description;
}
