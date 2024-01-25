package application;

public class Employee {
	private String NumeCol;
	private String PrenumeCol;
	private String CNPCol;
	private String OrasCol;
	private String JudetCol;
	private String SalariuCol;
	private String FilialaCol;
	private String DepartamentCol;
	private String SexCol;
	private String StradaCol;
	private String NrCol;
	private String MailCol;
    
    public String getNumeCol() {
		return NumeCol;
	}

	public void setNumeCol(String numeCol) {
		NumeCol = numeCol;
	}

	public String getPrenumeCol() {
		return PrenumeCol;
	}

	public void setPrenumeCol(String prenumeCol) {
		PrenumeCol = prenumeCol;
	}

	public String getCNPCol() {
		return CNPCol;
	}

	public void setCNPCol(String cNPCol) {
		CNPCol = cNPCol;
	}

	public String getOrasCol() {
		return OrasCol;
	}

	public void setOrasCol(String orasCol) {
		OrasCol = orasCol;
	}

	public String getJudetCol() {
		return JudetCol;
	}

	public void setJudetCol(String judetCol) {
		JudetCol = judetCol;
	}

	public String getSalariuCol() {
		return SalariuCol;
	}

	public void setSalariuCol(String salariuCol) {
		SalariuCol = salariuCol;
	}

	public String getFilialaCol() {
		return FilialaCol;
	}

	public void setFilialaCol(String filialaCol) {
		FilialaCol = filialaCol;
	}

	public String getDepartamentCol() {
		return DepartamentCol;
	}

	public void setDepartamentCol(String departamentCol) {
		DepartamentCol = departamentCol;
	}

	public String getSexCol() {
		return SexCol;
	}

	public void setSexCol(String sexCol) {
		SexCol = sexCol;
	}

	public String getStradaCol() {
		return StradaCol;
	}

	public void setStradaCol(String stradaCol) {
		StradaCol = stradaCol;
	}

	public String getNrCol() {
		return NrCol;
	}

	public void setNrCol(String nrCol) {
		NrCol = nrCol;
	}

	public String getMailCol() {
		return MailCol;
	}

	public void setMailCol(String mailCol) {
		MailCol = mailCol;
	}

	public Employee () {
    	this.NumeCol = null;
    	this.PrenumeCol = null;
    	this.CNPCol = null;
    	this.OrasCol = null;
    	this.JudetCol = null;
    	this.SalariuCol = null;
    	this.FilialaCol = null;
    	this.DepartamentCol = null;
    	this.SexCol = null;
    	this.StradaCol = null;
    	this.NrCol = null;
    	this.MailCol = null;
    }
    
    public Employee (String Nume, String Prenume, String CNP, String Oras, String Judet, String Salariu, String Filiala, String Departament, String Sex, String Strada, String Nr, String Mail) {
    	this.NumeCol = Nume;
    	this.PrenumeCol = Prenume;
    	this.CNPCol = CNP;
    	this.OrasCol = Oras;
    	this.JudetCol = Judet;
    	this.SalariuCol = Salariu;
    	this.FilialaCol = Filiala;
    	this.DepartamentCol = Departament;
    	this.SexCol = Sex;
    	this.StradaCol = Strada;
    	this.NrCol = Nr;
    	this.MailCol = Mail;
    }
}
