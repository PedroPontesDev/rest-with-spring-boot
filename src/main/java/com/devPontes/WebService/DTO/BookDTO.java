package com.devPontes.WebService.DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder(value = { "id", "author", "price", "title", "launchDate" })
public class BookDTO extends RepresentationModel<BookDTO> implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long id;
	private String author;
	private Double price;
	private String title;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm:ss")
	private Date launchDate;

	public BookDTO(Long id, String author, Double price, String title, Date launchDate) {
		this.id = id;
		this.author = author;
		this.price = price;
		this.title = title;
		this.launchDate = launchDate;
	}
	
	public BookDTO() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(Date launchDate) {
		this.launchDate = launchDate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(id);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookDTO other = (BookDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "BookDTO [id=" + id + ", author=" + author + ", price=" + price + ", title=" + title + ", launchDate="
				+ launchDate + "]";
	}

}
