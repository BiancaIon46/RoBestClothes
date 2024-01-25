package application;

public class Headquarter {
    String OrasCol;
    String CodFilialaCol;
    String NumeDirectorCol;
    String PrenumeCol;
    String CNPCol;
    String FurnizoriCol;
	
    public Headquarter() {
		OrasCol = null;
		CodFilialaCol = null;
		NumeDirectorCol = null;
		PrenumeCol = null;
		CNPCol = null;
		FurnizoriCol = null;
	}
    
    public Headquarter(String orasCol, String codFilialaCol, String numeDirectorCol, String prenumeCol, String cNPCol, String furnizoriCol) {
		OrasCol = orasCol;
		CodFilialaCol = codFilialaCol;
		NumeDirectorCol = numeDirectorCol;
		PrenumeCol = prenumeCol;
		CNPCol = cNPCol;
		FurnizoriCol = furnizoriCol;
	}

	public String getOrasCol() {
		return OrasCol;
	}

	public void setOrasCol(String orasCol) {
		OrasCol = orasCol;
	}

	public String getCodFilialaCol() {
		return CodFilialaCol;
	}

	public void setCodFilialaCol(String codFilialaCol) {
		CodFilialaCol = codFilialaCol;
	}

	public String getNumeDirectorCol() {
		return NumeDirectorCol;
	}

	public void setNumeDirectorCol(String numeDirectorCol) {
		NumeDirectorCol = numeDirectorCol;
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

	public String getFurnizoriCol() {
		return FurnizoriCol;
	}

	public void setFurnizoriCol(String furnizoriCol) {
		FurnizoriCol = furnizoriCol;
	}
}
