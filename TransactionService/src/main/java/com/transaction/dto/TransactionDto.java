package com.transaction.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private int amount;

    private String description;

    private String userName;

    private int accountNumber;

    private String userEmail;

    private boolean sucess;

    private LocalDate userCreatedDate;
}