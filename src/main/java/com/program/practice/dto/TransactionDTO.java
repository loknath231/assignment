package com.program.practice.dto;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDTO implements Serializable {
	private static final long serialVersionUID = 1L;
//@Id
	private long bsb;
	private long identification;
	private double balance;
	private LocalDate openingDate;

}
