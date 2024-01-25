package application;

public class Material {
	String NumeCol;
	String CuloareCol;
	String PretCol;
	String CalitateCol;
	
	public Material() {
		NumeCol = null;
		CuloareCol = null;
		PretCol = null;
		CalitateCol = null;
	}
	
	public Material(String numeCol, String culoareCol, String pretCol, String calitateCol) {
		NumeCol = numeCol;
		CuloareCol = culoareCol;
		PretCol = pretCol;
		CalitateCol = calitateCol;
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

	public String getPretCol() {
		return PretCol;
	}

	public void setPretCol(String pretCol) {
		PretCol = pretCol;
	}

	public String getCalitateCol() {
		return CalitateCol;
	}

	public void setCalitateCol(String calitateCol) {
		CalitateCol = calitateCol;
	}
}
