package com.example.notificationservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmailLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String recipient;
    private String subject;

    @Lob
    private String content;

    private boolean success;

    @Column(length = 1000)
    private String errorMessage;

    private LocalDateTime sentAt;
}