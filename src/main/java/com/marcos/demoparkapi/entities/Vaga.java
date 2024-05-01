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
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vagas")
@EntityListeners(AuditingEntityListener.class)
public class Vaga implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "code", nullable = false, unique = true, length = 4)
	private String code;

	@Column(name = "status", nullable = false, length = 4)
	@Enumerated(EnumType.STRING)
	private StatusVacancy status;

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

	public enum StatusVacancy {
		FREE, BUSY
	}
	
	public Vaga() {
		// TODO Auto-generated constructor stub
	}

	public Vaga(Long id, String code, StatusVacancy status, LocalDateTime creation_date,
			LocalDateTime dmodification_date, String created_by, String modified_by) {
		super();
		this.id = id;
		this.code = code;
		this.status = status;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public StatusVacancy getStatus() {
		return status;
	}

	public void setStatus(StatusVacancy status) {
		this.status = status;
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
		Vaga other = (Vaga) obj;
		return Objects.equals(id, other.id);
	}

}
