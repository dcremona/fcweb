package fcweb.backend.data.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fc_accesso")
public class FcAccesso implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_attore", referencedColumnName = "id_attore")
	private FcAttore fcAttore; // immutable

	@Column(name = "data", nullable = false)
	private LocalDateTime data;
	
	@Column(name = "note")
	private String note;

	@ManyToOne
	@JoinColumn(name = "id_campionato", referencedColumnName = "id_campionato")
	private FcCampionato fcCampionato; // immutable

	public FcCampionato getFcCampionato() {
		return fcCampionato;
	}

	public void setFcCampionato(FcCampionato fcCampionato) {
		this.fcCampionato = fcCampionato;
	}

	public FcAttore getFcAttore() {
		return fcAttore;
	}

	public void setFcAttore(FcAttore fcAttore) {
		this.fcAttore = fcAttore;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}