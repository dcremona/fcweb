package fcweb.backend.data.entity;
// Generated 31-ott-2018 12.15.29 by Hibernate Tools 5.1.7.Final

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * FcGiornataInfo generated by hbm2java
 */
@Entity
@Table(name = "fc_giornata_info")
public class FcGiornataInfo implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int codiceGiornata;
	private LocalDateTime dataAnticipo1;
	private LocalDateTime dataAnticipo2;
	private LocalDateTime dataGiornata;
	private LocalDateTime dataPosticipo;
	private String descGiornata;
	private String descGiornataFc;
	private int idGiornataFc;
	private Set<FcGiornataRis> fcGiornataRises = new HashSet<FcGiornataRis>(0);
	private Set<FcGiornataDett> fcGiornataDetts = new HashSet<FcGiornataDett>(0);
	private Set<FcPagelle> fcPagelles = new HashSet<FcPagelle>(0);
	private Set<FcGiornataDettInfo> fcGiornataDettInfos = new HashSet<FcGiornataDettInfo>(0);
	private Set<FcGiornata> fcGiornatas = new HashSet<FcGiornata>(0);
	private Set<FcClassificaTotPt> fcClassificaTotPts = new HashSet<FcClassificaTotPt>(0);
	private Set<FcMercatoDett> fcMercatoDetts = new HashSet<FcMercatoDett>(0);

	public FcGiornataInfo() {
	}

	public FcGiornataInfo(int codiceGiornata, String descGiornata,
			String descGiornataFc, int idGiornataFc) {
		this.codiceGiornata = codiceGiornata;
		this.descGiornata = descGiornata;
		this.descGiornataFc = descGiornataFc;
		this.idGiornataFc = idGiornataFc;
	}

	public FcGiornataInfo(int codiceGiornata, LocalDateTime dataAnticipo1,
			LocalDateTime dataAnticipo2, LocalDateTime dataGiornata,
			LocalDateTime dataPosticipo, String descGiornata,
			String descGiornataFc, int idGiornataFc,
			Set<FcGiornataRis> fcGiornataRises,
			Set<FcGiornataDett> fcGiornataDetts, Set<FcPagelle> fcPagelles,
			Set<FcGiornataDettInfo> fcGiornataDettInfos,
			Set<FcGiornata> fcGiornatas,
			Set<FcClassificaTotPt> fcClassificaTotPts,
			Set<FcMercatoDett> fcMercatoDetts) {
		this.codiceGiornata = codiceGiornata;
		this.dataAnticipo1 = dataAnticipo1;
		this.dataAnticipo2 = dataAnticipo2;
		this.dataGiornata = dataGiornata;
		this.dataPosticipo = dataPosticipo;
		this.descGiornata = descGiornata;
		this.descGiornataFc = descGiornataFc;
		this.idGiornataFc = idGiornataFc;
		this.fcGiornataRises = fcGiornataRises;
		this.fcGiornataDetts = fcGiornataDetts;
		this.fcPagelles = fcPagelles;
		this.fcGiornataDettInfos = fcGiornataDettInfos;
		this.fcGiornatas = fcGiornatas;
		this.fcClassificaTotPts = fcClassificaTotPts;
		this.fcMercatoDetts = fcMercatoDetts;
	}

	@Id

	@Column(name = "codice_giornata", unique = true, nullable = false)
	public int getCodiceGiornata() {
		return this.codiceGiornata;
	}

	public void setCodiceGiornata(int codiceGiornata) {
		this.codiceGiornata = codiceGiornata;
	}

	@Column(name = "data_anticipo1")
	public LocalDateTime getDataAnticipo1() {
		return this.dataAnticipo1;
	}

	public void setDataAnticipo1(LocalDateTime dataAnticipo1) {
		this.dataAnticipo1 = dataAnticipo1;
	}

	@Column(name = "data_anticipo2")
	public LocalDateTime getDataAnticipo2() {
		return this.dataAnticipo2;
	}

	public void setDataAnticipo2(LocalDateTime dataAnticipo2) {
		this.dataAnticipo2 = dataAnticipo2;
	}

	@Column(name = "data_giornata")
	public LocalDateTime getDataGiornata() {
		return this.dataGiornata;
	}

	public void setDataGiornata(LocalDateTime dataGiornata) {
		this.dataGiornata = dataGiornata;
	}

	@Column(name = "data_posticipo")
	public LocalDateTime getDataPosticipo() {
		return this.dataPosticipo;
	}

	public void setDataPosticipo(LocalDateTime dataPosticipo) {
		this.dataPosticipo = dataPosticipo;
	}

	@Column(name = "desc_giornata", nullable = false)
	public String getDescGiornata() {
		return this.descGiornata;
	}

	public void setDescGiornata(String descGiornata) {
		this.descGiornata = descGiornata;
	}

	@Column(name = "desc_giornata_fc", nullable = false)
	public String getDescGiornataFc() {
		return this.descGiornataFc;
	}

	public void setDescGiornataFc(String descGiornataFc) {
		this.descGiornataFc = descGiornataFc;
	}

	@Column(name = "id_giornata_fc", nullable = false)
	public int getIdGiornataFc() {
		return this.idGiornataFc;
	}

	public void setIdGiornataFc(int idGiornataFc) {
		this.idGiornataFc = idGiornataFc;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fcGiornataInfo")
	public Set<FcGiornataRis> getFcGiornataRises() {
		return this.fcGiornataRises;
	}

	public void setFcGiornataRises(Set<FcGiornataRis> fcGiornataRises) {
		this.fcGiornataRises = fcGiornataRises;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fcGiornataInfo")
	public Set<FcGiornataDett> getFcGiornataDetts() {
		return this.fcGiornataDetts;
	}

	public void setFcGiornataDetts(Set<FcGiornataDett> fcGiornataDetts) {
		this.fcGiornataDetts = fcGiornataDetts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fcGiornataInfo")
	public Set<FcPagelle> getFcPagelles() {
		return this.fcPagelles;
	}

	public void setFcPagelles(Set<FcPagelle> fcPagelles) {
		this.fcPagelles = fcPagelles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fcGiornataInfo")
	public Set<FcGiornataDettInfo> getFcGiornataDettInfos() {
		return this.fcGiornataDettInfos;
	}

	public void setFcGiornataDettInfos(
			Set<FcGiornataDettInfo> fcGiornataDettInfos) {
		this.fcGiornataDettInfos = fcGiornataDettInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fcGiornataInfo")
	public Set<FcGiornata> getFcGiornatas() {
		return this.fcGiornatas;
	}

	public void setFcGiornatas(Set<FcGiornata> fcGiornatas) {
		this.fcGiornatas = fcGiornatas;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fcGiornataInfo")
	public Set<FcClassificaTotPt> getFcClassificaTotPts() {
		return this.fcClassificaTotPts;
	}

	public void setFcClassificaTotPts(
			Set<FcClassificaTotPt> fcClassificaTotPts) {
		this.fcClassificaTotPts = fcClassificaTotPts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fcGiornataInfo")
	public Set<FcMercatoDett> getFcMercatoDetts() {
		return this.fcMercatoDetts;
	}

	public void setFcMercatoDetts(Set<FcMercatoDett> fcMercatoDetts) {
		this.fcMercatoDetts = fcMercatoDetts;
	}

}
