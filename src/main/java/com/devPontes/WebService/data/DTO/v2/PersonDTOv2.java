package com.devPontes.WebService.data.DTO.v2;


import java.util.Date;
import java.util.Objects;

public class PersonDTOv2 {

	private Long id;
	private String firstName;
	private String lastName;
	private String address;
	private String gender;
	private Date birthDate;
	

	public PersonDTOv2(Long id, String firstName, String lastName, String address, String gender) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.gender = gender;
		this.id = id;
	}

	public PersonDTOv2() {
	}

	
	
	public Long getId() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
 
	
	
	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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
		PersonDTOv2 other = (PersonDTOv2) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "PersonDTO [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", address=" + address
				+ ", gender=" + gender + "]";
	}

	public void setId(long longValue) {
		this.id = longValue;
	}

	
	
	
}
