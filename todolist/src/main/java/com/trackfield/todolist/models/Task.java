package com.trackfield.todolist.models;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

@Table(name = Task.TABLE_NAME)
@Entity(name = Task.TABLE_NAME)
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
public class Task {
    public static final String TABLE_NAME = "tasks";

    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private String title;

    private String description;

    private Boolean finished = false;


}
