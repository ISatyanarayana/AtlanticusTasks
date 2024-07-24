package com.transaction.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int transactionId;

    private String description;

    private int amount;

    private boolean credited;

    @Transient
    private int userID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private LocalDate createdDate;
}
