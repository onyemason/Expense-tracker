package com.codeElevate.ExpenseTracker.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExpenseDTO {
    private  Long Id;

    private String Title;

    private String description;

    private  String category;

    private LocalDate date;

    private Integer amount;


}

