package com.sandey.children.app.qrcode.entities;

import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class Children {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private int id;
	private String name;
	private int age;
	private String gender;
	@OneToOne(mappedBy = "children",cascade = CascadeType.ALL)
	private QrcodeStatus qrcodeStatus;

	public QrcodeStatus getQrcodeStatus() {
		return qrcodeStatus;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setQrcodeStatus(QrcodeStatus qrcodeStatus) {
		this.qrcodeStatus = qrcodeStatus;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, gender, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Children other = (Children) obj;
		return age == other.age && Objects.equals(gender, other.gender) && id == other.id
				&& Objects.equals(name, other.name);
	}

	public Children(int id, String name, int age, String gender) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.gender = gender;
	}

	public Children() {
	}

}
