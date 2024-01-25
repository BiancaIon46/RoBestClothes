package application;

public class Provider {
    String NumeCol;
    String CodFurnizorCol;
    String NrContractCol;
    String DataAchizitieCol;
    String MaterialeFurnizateCol;
    
    public Provider() {
    	NumeCol = null;
		CodFurnizorCol = null;
		NrContractCol = null;
		DataAchizitieCol = null;
		MaterialeFurnizateCol = null;
    }
    
	public Provider(String numeCol, String codFurnizorCol, String nrContractCol, String dataAchizitieCol, String materialeFurnizateCol) {
		NumeCol = numeCol;
		CodFurnizorCol = codFurnizorCol;
		NrContractCol = nrContractCol;
		DataAchizitieCol = dataAchizitieCol;
		MaterialeFurnizateCol = materialeFurnizateCol;
	}
	
	public String getNumeCol() {
		return NumeCol;
	}
	public void setNumeCol(String numeCol) {
		NumeCol = numeCol;
	}
	public String getCodFurnizorCol() {
		return CodFurnizorCol;
	}
	public void setCodFurnizorCol(String codFurnizorCol) {
		CodFurnizorCol = codFurnizorCol;
	}
	public String getNrContractCol() {
		return NrContractCol;
	}
	public void setNrContractCol(String nrContractCol) {
		NrContractCol = nrContractCol;
	}
	public String getDataAchizitieCol() {
		return DataAchizitieCol;
	}
	public void setDataAchizitieCol(String dataAchizitieCol) {
		DataAchizitieCol = dataAchizitieCol;
	}
	public String getMaterialeFurnizateCol() {
		return MaterialeFurnizateCol;
	}
	public void setMaterialeFurnizateCol(String materialeFurnizateCol) {
		MaterialeFurnizateCol = materialeFurnizateCol;
	}
    
    
}
