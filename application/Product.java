package application;

import java.util.List;

public class Product {
	private String NumeCol;
	private String CuloareCol;
	private String MarimeCol;
	private String NrBucatiCol;
	private String CodProdusCol;
	private String PretCol;
	private String MaterialeFolositeCol;
	
	public Product() {
		NumeCol = null;
		CuloareCol = null;
		MarimeCol = null;
		NrBucatiCol = null;
		CodProdusCol = null;
		PretCol = null;
		MaterialeFolositeCol = null;
	}
	
	public Product(String nume, String culoare, String marime, String nrBucati, String codProdus, String pret, String materiale) {
		this.NumeCol = nume;
		this.CuloareCol = culoare;
		this.MarimeCol = marime;
		this.NrBucatiCol = nrBucati;
		this.CodProdusCol = codProdus;
		this.PretCol = pret;
		this.MaterialeFolositeCol = materiale;
	}

	public String getNumeCol() {
		return NumeCol;
	}

	public void setNumeCol(String numeCol) {
		NumeCol = numeCol;
	}

	public String getCuloareCol() {
		return CuloareCol;
	}

	public void setCuloareCol(String culoareCol) {
		CuloareCol = culoareCol;
	}

	public String getMarimeCol() {
		return MarimeCol;
	}

	public void setMarimeCol(String marimeCol) {
		MarimeCol = marimeCol;
	}

	public String getNrBucatiCol() {
		return NrBucatiCol;
	}

	public void setNrBucatiCol(String nrBucatiCol) {
		NrBucatiCol = nrBucatiCol;
	}

	public String getCodProdusCol() {
		return CodProdusCol;
	}

	public void setCodProdusCol(String codProdusCol) {
		CodProdusCol = codProdusCol;
	}

	public String getPretCol() {
		return PretCol;
	}

	public void setPretCol(String pretCol) {
		PretCol = pretCol;
	}

	public String getMaterialeFolositeCol() {
		return MaterialeFolositeCol;
	}

	public void setMaterialeFolositeCol(String materialeFolositeCol) {
		MaterialeFolositeCol = materialeFolositeCol;
	}
}
