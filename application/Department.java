package application;

public class Department {
	String NumeCol;
	String NumeManagerCol;
	String PrenumeManagerCol;
	String CNPManagerCol;
	String CodDepartamentCol;
	
	public Department() {
		NumeCol = null;
		NumeManagerCol = null;
		PrenumeManagerCol = null;
		CNPManagerCol = null;
		CodDepartamentCol = null;
	}
	
	public Department(String numeCol, String numeManagerCol, String prenumeManagerCol, String cNPManagerCol, String codDepartamentCol) {
		NumeCol = numeCol;
		NumeManagerCol = numeManagerCol;
		PrenumeManagerCol = prenumeManagerCol;
		CNPManagerCol = cNPManagerCol;
		CodDepartamentCol = codDepartamentCol;
	}

	public String getNumeCol() {
		return NumeCol;
	}

	public void setNumeCol(String numeCol) {
		NumeCol = numeCol;
	}

	public String getNumeManagerCol() {
		return NumeManagerCol;
	}

	public void setNumeManagerCol(String numeManagerCol) {
		NumeManagerCol = numeManagerCol;
	}

	public String getPrenumeManagerCol() {
		return PrenumeManagerCol;
	}

	public void setPrenumeManagerCol(String prenumeManagerCol) {
		PrenumeManagerCol = prenumeManagerCol;
	}

	public String getCNPManagerCol() {
		return CNPManagerCol;
	}

	public void setCNPManagerCol(String cNPManagerCol) {
		CNPManagerCol = cNPManagerCol;
	}

	public String getCodDepartamentCol() {
		return CodDepartamentCol;
	}

	public void setCodDepartamentCol(String codDepartamentCol) {
		CodDepartamentCol = codDepartamentCol;
	}
}
