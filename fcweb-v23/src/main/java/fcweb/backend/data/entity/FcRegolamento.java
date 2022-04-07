package fcweb.backend.data.entity;

import java.io.Serializable;
import java.sql.Clob;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fc_regolamento")
public class FcRegolamento implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "data", nullable = false)
	private LocalDateTime data;

	@Column(name = "src")
	private Clob src;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public Clob getSrc() {
		return src;
	}

	public void setSrc(Clob src) {
		this.src = src;
	}

}