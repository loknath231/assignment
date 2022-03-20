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
@Table(name = "user")
public class User {
	@Id
	@Column(name = "bsb")
	private Long bsb;
	@Column(name = "identification")
	private String identification;
	@Column(name = "opening_date")
	private String openingDate;
}
