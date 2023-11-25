package com.picsart.intership.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Accessors(chain = true)
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    @Column(unique = true, updatable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    private ERole role;
    private String password;

    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-mm-dd HH:mm:ss")
    @Column(updatable = false)
    private LocalDate dateJoined;
    private LocalDateTime lastLogin;
    private String profilePicture;
    private String bio;

    private boolean active;
}
