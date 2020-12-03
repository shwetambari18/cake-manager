package com.waracle.cakemanager.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Currency;


@Entity
@Table(name = "CAKE")
public class CakeEntity implements Serializable{

	private static final long serialVersionUID = 6218681867192538239L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	
	@Column(name = "name")
	@NotEmpty
	@Size(min = 3)
	private String name;
	
	@Column(name = "flavour")
	@NotEmpty
	private String flavour;
	
	@Column(name = "size")
	@NotEmpty
	private String size;
	
	@Column(name = "icingtype")
	@NotEmpty
	private String icingType;
	
	@Column(name = "price")
	@DecimalMax(value = "9999.99")
	private BigDecimal price;
	
	
	protected CakeEntity() {
		
	}

	public CakeEntity(Long id, String name, String flavour, String size, String icingType, BigDecimal price) {
		super();
		Id = id;
		this.name = name;
		this.flavour = flavour;
		this.size = size;
		this.icingType = icingType;
		this.price = price;
	}


	public Long getId() {
		return Id;
	}


	public void setId(Long id) {
		Id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getFlavour() {
		return flavour;
	}


	public void setFlavour(String flavour) {
		this.flavour = flavour;
	}


	public String getSize() {
		return size;
	}


	public void setSize(String size) {
		this.size = size;
	}


	public String getIcingType() {
		return icingType;
	}


	public void setIcingType(String icingType) {
		this.icingType = icingType;
	}


	public BigDecimal getPrice() {
		return price;
	}


	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Cake [Id=" + Id + ", name=" + name + ", flavour=" + flavour + ", size=" + size + ", icingType="
				+ icingType + ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		return super.hashCode() + 32;
	}

	@Override
	public boolean equals(Object obj) {
		
		if(this == obj)
			return true;
		if(this == null || this.getClass() != obj.getClass())
			return false;
		CakeEntity cakeObj = (CakeEntity)obj;
		return this.name.equals(cakeObj.name) && this.flavour.equals(cakeObj.flavour) && this.icingType.equals(cakeObj.icingType)
				&& this.size.equals(cakeObj.size) && (this.price.equals(cakeObj.price));
	}
	
	
	
}
