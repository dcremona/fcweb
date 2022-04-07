package fcweb.backend.data.entity;
// Generated 31-ott-2018 12.15.29 by Hibernate Tools 5.1.7.Final

import java.sql.Blob;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * FcGiocatore generated by hbm2java
 */
@Entity
@Table(name = "fc_giocatore" )
public class FcGiocatore implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int idGiocatore;
	private FcRuolo fcRuolo;
	private FcSquadra fcSquadra;
	private String cognGiocatore;
	private boolean flagAttivo;
	private String nomeGiocatore;
	private String nomeImg;
	private Integer quotazione;
	private Blob img;
	private Blob imgSmall;
	private Set<FcPagelle> fcPagelles = new HashSet<FcPagelle>(0);
	private Set<FcFormazione> fcFormaziones = new HashSet<FcFormazione>(0);
	private Set<FcGiornataDett> fcGiornataDetts = new HashSet<FcGiornataDett>(0);
	private FcStatistiche fcStatistiche;
	private Set<FcMercatoDett> fcMercatoDettsForIdGiocVen = new HashSet<FcMercatoDett>(0);
	private Set<FcMercatoDett> fcMercatoDettsForIdGiocAcq = new HashSet<FcMercatoDett>(0);

	public FcGiocatore() {
	}

	public FcGiocatore(int idGiocatore, String cognGiocatore) {
		this.idGiocatore = idGiocatore;
		this.cognGiocatore = cognGiocatore;
	}

	public FcGiocatore(int idGiocatore, FcRuolo fcRuolo, FcSquadra fcSquadra,
			String cognGiocatore, boolean flagAttivo, String nomeGiocatore,
			String nomeImg, Integer quotazione, Set<FcPagelle> fcPagelles,
			Set<FcFormazione> fcFormaziones,
			Set<FcGiornataDett> fcGiornataDetts, FcStatistiche fcStatistiche,
			Set<FcMercatoDett> fcMercatoDettsForIdGiocVen,
			Set<FcMercatoDett> fcMercatoDettsForIdGiocAcq) {
		this.idGiocatore = idGiocatore;
		this.fcRuolo = fcRuolo;
		this.fcSquadra = fcSquadra;
		this.cognGiocatore = cognGiocatore;
		this.flagAttivo = flagAttivo;
		this.nomeGiocatore = nomeGiocatore;
		this.nomeImg = nomeImg;
		this.quotazione = quotazione;
		this.fcPagelles = fcPagelles;
		this.fcFormaziones = fcFormaziones;
		this.fcGiornataDetts = fcGiornataDetts;
		this.fcStatistiche = fcStatistiche;
		this.fcMercatoDettsForIdGiocVen = fcMercatoDettsForIdGiocVen;
		this.fcMercatoDettsForIdGiocAcq = fcMercatoDettsForIdGiocAcq;
	}

	@Id

	@Column(name = "id_giocatore", unique = true, nullable = false)
	public int getIdGiocatore() {
		return this.idGiocatore;
	}

	public void setIdGiocatore(int idGiocatore) {
		this.idGiocatore = idGiocatore;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_ruolo")
	public FcRuolo getFcRuolo() {
		return this.fcRuolo;
	}

	public void setFcRuolo(FcRuolo fcRuolo) {
		this.fcRuolo = fcRuolo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_squadra")
	public FcSquadra getFcSquadra() {
		return this.fcSquadra;
	}

	public void setFcSquadra(FcSquadra fcSquadra) {
		this.fcSquadra = fcSquadra;
	}

	@Column(name = "cogn_giocatore", nullable = false)
	public String getCognGiocatore() {
		return this.cognGiocatore;
	}

	public void setCognGiocatore(String cognGiocatore) {
		this.cognGiocatore = cognGiocatore;
	}

	@Column(name = "flag_attivo")
	public boolean isFlagAttivo() {
		return this.flagAttivo;
	}

	public void setFlagAttivo(boolean flagAttivo) {
		this.flagAttivo = flagAttivo;
	}

	@Column(name = "nome_giocatore")
	public String getNomeGiocatore() {
		return this.nomeGiocatore;
	}

	public void setNomeGiocatore(String nomeGiocatore) {
		this.nomeGiocatore = nomeGiocatore;
	}

	@Column(name = "nome_img")
	public String getNomeImg() {
		return this.nomeImg;
	}

	public void setNomeImg(String nomeImg) {
		this.nomeImg = nomeImg;
	}

	@Column(name = "quotazione")
	public Integer getQuotazione() {
		return this.quotazione;
	}

	public void setQuotazione(Integer quotazione) {
		this.quotazione = quotazione;
	}

	@Column(name = "img")
	public Blob getImg() {
		return this.img;
	}

	public void setImg(Blob img) {
		this.img = img;
	}

	@Column(name = "img_small")
	public Blob getImgSmall() {
		return this.imgSmall;
	}

	public void setImgSmall(Blob imgSmall) {
		this.imgSmall = imgSmall;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fcGiocatore")
	public Set<FcPagelle> getFcPagelles() {
		return this.fcPagelles;
	}

	public void setFcPagelles(Set<FcPagelle> fcPagelles) {
		this.fcPagelles = fcPagelles;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fcGiocatore")
	public Set<FcFormazione> getFcFormaziones() {
		return this.fcFormaziones;
	}

	public void setFcFormaziones(Set<FcFormazione> fcFormaziones) {
		this.fcFormaziones = fcFormaziones;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fcGiocatore")
	public Set<FcGiornataDett> getFcGiornataDetts() {
		return this.fcGiornataDetts;
	}

	public void setFcGiornataDetts(Set<FcGiornataDett> fcGiornataDetts) {
		this.fcGiornataDetts = fcGiornataDetts;
	}

	@OneToOne(fetch = FetchType.LAZY, mappedBy = "fcGiocatore")
	public FcStatistiche getFcStatistiche() {
		return this.fcStatistiche;
	}

	public void setFcStatistiche(FcStatistiche fcStatistiche) {
		this.fcStatistiche = fcStatistiche;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fcGiocatoreByIdGiocVen")
	public Set<FcMercatoDett> getFcMercatoDettsForIdGiocVen() {
		return this.fcMercatoDettsForIdGiocVen;
	}

	public void setFcMercatoDettsForIdGiocVen(
			Set<FcMercatoDett> fcMercatoDettsForIdGiocVen) {
		this.fcMercatoDettsForIdGiocVen = fcMercatoDettsForIdGiocVen;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "fcGiocatoreByIdGiocAcq")
	public Set<FcMercatoDett> getFcMercatoDettsForIdGiocAcq() {
		return this.fcMercatoDettsForIdGiocAcq;
	}

	public void setFcMercatoDettsForIdGiocAcq(
			Set<FcMercatoDett> fcMercatoDettsForIdGiocAcq) {
		this.fcMercatoDettsForIdGiocAcq = fcMercatoDettsForIdGiocAcq;
	}
	
	
	@Column(name = "data", nullable = false)
	private LocalDateTime data;

	
	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}


}
