package com.example.myspring.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "product")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	Integer productId;
	
	@Column(name = "product_name")
	String productName;
	
	@Column(name = "category")
	String category;
	
	@Column(name = "image_url")
	String imageUrl;
	
	@Column(name = "price")
	Integer price;
	
	@Column(name = "stock")
	Integer stock;
	
	@Column(name = "description")
	String description;
	
	@Column(name = "created_date")
	Date createDate;
	
	@Column(name = "last_modified_date")
	Date lastModifiedDate;
	
	public ProductEntity(String productName,
						 String category,
						 String imageUrl,
						 Integer price,
						 Integer stock,
						 String description){
		this.productName = productName;
		this.category = category;
		this.imageUrl = imageUrl;
		this.price = price;
		this.stock = stock;
		this.description = description;
	}
	
	public ProductEntity(Integer productId,
						 String productName,
						 String category,
						 String imageUrl,
						 Integer price,
						 Integer stock,
						 String description){
		this.productId = productId;
		this.productName = productName;
		this.category = category;
		this.imageUrl = imageUrl;
		this.price = price;
		this.stock = stock;
		this.description = description;
	}
	
	public ProductEntity(Integer productId, Integer stock){
		this.productId = productId;
		this.stock = stock;
	}
	
	public ProductEntity(Integer productId){
		this.productId = productId;

	}
}
