package fcweb.backend.data.entity;
// Generated 31-ott-2018 12.15.29 by Hibernate Tools 5.1.7.Final

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * FcClassifica generated by hbm2java
 */
@Entity
@Table(name = "fc_classifica" )
public class FcClassifica implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private FcClassificaId id;
	private FcAttore fcAttore;
	private FcCampionato fcCampionato;
	private int dr;
	private int fmMercato;
	private int gf;
	private int gs;
	private int idPosiz;
	private int idPosizFinal;
	private int pari;
	private int perse;
	private int punti;
	private int totFm;
	private Double totPunti;
	private Double totPuntiOld;
	private Double totPuntiRosa;
	private int vinte;

	public FcClassifica() {
	}

	public FcClassifica(FcClassificaId id, FcAttore fcAttore,
			FcCampionato fcCampionato, int dr, int fmMercato, int gf, int gs,
			int idPosiz, int idPosizFinal, int pari, int perse, int punti,
			int totFm, int vinte) {
		this.id = id;
		this.fcAttore = fcAttore;
		this.fcCampionato = fcCampionato;
		this.dr = dr;
		this.fmMercato = fmMercato;
		this.gf = gf;
		this.gs = gs;
		this.idPosiz = idPosiz;
		this.idPosizFinal = idPosizFinal;
		this.pari = pari;
		this.perse = perse;
		this.punti = punti;
		this.totFm = totFm;
		this.vinte = vinte;
	}

	public FcClassifica(FcClassificaId id, FcAttore fcAttore,
			FcCampionato fcCampionato, int dr, int fmMercato, int gf, int gs,
			int idPosiz, int idPosizFinal, int pari, int perse, int punti,
			int totFm, Double totPunti, Double totPuntiOld, Double totPuntiRosa,
			int vinte) {
		this.id = id;
		this.fcAttore = fcAttore;
		this.fcCampionato = fcCampionato;
		this.dr = dr;
		this.fmMercato = fmMercato;
		this.gf = gf;
		this.gs = gs;
		this.idPosiz = idPosiz;
		this.idPosizFinal = idPosizFinal;
		this.pari = pari;
		this.perse = perse;
		this.punti = punti;
		this.totFm = totFm;
		this.totPunti = totPunti;
		this.totPuntiOld = totPuntiOld;
		this.totPuntiRosa = totPuntiRosa;
		this.vinte = vinte;
	}

	@EmbeddedId

	@AttributeOverrides({ @AttributeOverride(name = "idAttore", column = @Column(name = "id_attore", nullable = false)), @AttributeOverride(name = "idCampionato", column = @Column(name = "id_campionato", nullable = false)) })
	public FcClassificaId getId() {
		return this.id;
	}

	public void setId(FcClassificaId id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_attore", nullable = false, insertable = false, updatable = false)
	public FcAttore getFcAttore() {
		return this.fcAttore;
	}

	public void setFcAttore(FcAttore fcAttore) {
		this.fcAttore = fcAttore;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_campionato", nullable = false, insertable = false, updatable = false)
	public FcCampionato getFcCampionato() {
		return this.fcCampionato;
	}

	public void setFcCampionato(FcCampionato fcCampionato) {
		this.fcCampionato = fcCampionato;
	}

	@Column(name = "dr", nullable = false)
	public int getDr() {
		return this.dr;
	}

	public void setDr(int dr) {
		this.dr = dr;
	}

	@Column(name = "fm_mercato", nullable = false)
	public int getFmMercato() {
		return this.fmMercato;
	}

	public void setFmMercato(int fmMercato) {
		this.fmMercato = fmMercato;
	}

	@Column(name = "gf", nullable = false)
	public int getGf() {
		return this.gf;
	}

	public void setGf(int gf) {
		this.gf = gf;
	}

	@Column(name = "gs", nullable = false)
	public int getGs() {
		return this.gs;
	}

	public void setGs(int gs) {
		this.gs = gs;
	}

	@Column(name = "id_posiz", nullable = false)
	public int getIdPosiz() {
		return this.idPosiz;
	}

	public void setIdPosiz(int idPosiz) {
		this.idPosiz = idPosiz;
	}

	@Column(name = "id_posiz_final", nullable = false)
	public int getIdPosizFinal() {
		return this.idPosizFinal;
	}

	public void setIdPosizFinal(int idPosizFinal) {
		this.idPosizFinal = idPosizFinal;
	}

	@Column(name = "pari", nullable = false)
	public int getPari() {
		return this.pari;
	}

	public void setPari(int pari) {
		this.pari = pari;
	}

	@Column(name = "perse", nullable = false)
	public int getPerse() {
		return this.perse;
	}

	public void setPerse(int perse) {
		this.perse = perse;
	}

	@Column(name = "punti", nullable = false)
	public int getPunti() {
		return this.punti;
	}

	public void setPunti(int punti) {
		this.punti = punti;
	}

	@Column(name = "tot_fm", nullable = false)
	public int getTotFm() {
		return this.totFm;
	}

	public void setTotFm(int totFm) {
		this.totFm = totFm;
	}

	@Column(name = "tot_punti", precision = 22, scale = 0)
	public Double getTotPunti() {
		return this.totPunti;
	}

	public void setTotPunti(Double totPunti) {
		this.totPunti = totPunti;
	}

	@Column(name = "tot_punti_old", precision = 22, scale = 0)
	public Double getTotPuntiOld() {
		return this.totPuntiOld;
	}

	public void setTotPuntiOld(Double totPuntiOld) {
		this.totPuntiOld = totPuntiOld;
	}

	@Column(name = "tot_punti_rosa", precision = 22, scale = 0)
	public Double getTotPuntiRosa() {
		return this.totPuntiRosa;
	}

	public void setTotPuntiRosa(Double totPuntiRosa) {
		this.totPuntiRosa = totPuntiRosa;
	}

	@Column(name = "vinte", nullable = false)
	public int getVinte() {
		return this.vinte;
	}

	public void setVinte(int vinte) {
		this.vinte = vinte;
	}

}
