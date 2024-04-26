package com.marcos.demoparkapi.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "customers")
@EntityListeners(AuditingEntityListener.class)
public class Cliente implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "cpf", nullable = false, unique = true, length = 11)
	private String cpf;

	@OneToOne // Chave estrangeira de um para um
	@JoinColumn(name = "id_user", nullable = false) // Join na tabela user
	private User user;

	@CreatedDate
	@Column(name = "creation_date")
	private LocalDateTime creation_date;

	@LastModifiedDate
	@Column(name = "modification_date")
	private LocalDateTime dmodification_date;

	@CreatedBy
	@Column(name = "created_by")
	private String created_by;

	@LastModifiedBy
	@Column(name = "modified_by")
	private String modified_by;

	public Cliente() {
		// TODO Auto-generated constructor stub
	}

	public Cliente(Long id, String name, String cpf) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
	}

	public Cliente(Long id, String name, String cpf, User user, LocalDateTime creation_date,
			LocalDateTime dmodification_date, String created_by, String modified_by) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.user = user;
		this.creation_date = creation_date;
		this.dmodification_date = dmodification_date;
		this.created_by = created_by;
		this.modified_by = modified_by;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	public LocalDateTime getDmodification_date() {
		return dmodification_date;
	}

	public void setDmodification_date(LocalDateTime dmodification_date) {
		this.dmodification_date = dmodification_date;
	}

	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id);
	}

	
	
}