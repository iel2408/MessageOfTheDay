package app.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy =  GenerationType.IDENTITY)
	@Column
	private Long pk;
	
	@Column
	private String name;
	
	@Column
	private String cellphoneNumber;

	public Long getPk() {
		return pk;
	}

	public void setPk(Long pk) {
		this.pk = pk;
	}
	
	public String getName() {
		return name;
	}

	public String setName(String name) {
		this.name = name;
	}

	public String getCellphoneNumber() {
		return cellphoneNumber;
	}

	public void setCellphoneNUmber(String cellphoneNumber) {
		this.cellphoneNumber = cellphoneNumber;
	}
}
