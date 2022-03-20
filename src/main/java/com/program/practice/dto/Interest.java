package com.program.practice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Interest {
	private long bsb;
	private long identification;
	private String openingDate;
	private double balance;
	private double interestAmount;
	private double totalBal;
}
