package com.example.library.client;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor

public class ConfirmationToken {

    @Id
    @SequenceGenerator(
            name="student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1

    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    private Integer id;
    private String token;
    private LocalDateTime expiredAt;
    private LocalDateTime createdAt;
    private LocalDateTime confirmedAt;

    public ConfirmationToken(String token,
                             LocalDateTime expiredAt,
                             LocalDateTime createdAt,
                             LocalDateTime confirmedAt) {
        this.token = token;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
        this.confirmedAt = confirmedAt;
    }
}
