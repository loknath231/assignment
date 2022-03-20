package com.program.practice.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "transaction")
public class Transaction {
	@Id
	@Column(name = "identification")
	private long identification;
	@Column(name = "bsb")
	private long bsb;
	@Column(name = "balance_date")
	private String balanceDate;
	@Column(name = "balance_amount")
	private double balance;

}
