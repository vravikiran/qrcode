package com.sandey.children.app.qrcode.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
public class QrcodeStatus {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	boolean isactive;
	@OneToOne
	@PrimaryKeyJoinColumn
	private Children children;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isActive() {
		return isactive;
	}

	public void setActive(boolean isActive) {
		this.isactive = isActive;
	}

	public QrcodeStatus() {
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, isactive);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QrcodeStatus other = (QrcodeStatus) obj;
		return id == other.id && isactive == other.isactive;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

}
