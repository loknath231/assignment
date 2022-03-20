package com.program.practice.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DailyTransactionDTO {
	private String balanceDate;
	private ArrayList<TransactionDTO> transaction;

}
