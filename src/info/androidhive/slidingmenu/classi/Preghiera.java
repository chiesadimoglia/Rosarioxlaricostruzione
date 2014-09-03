package info.androidhive.slidingmenu.classi;


public class Preghiera {

	private int IDP;
	private String Nome;
	private String Testo;
	private String Tipo;
	private int Importanza;
	
	public Preghiera(){}
	
	public Preghiera(int id, String n, String t, String tipo, int imp){
		IDP=id;
		Nome=n;
		Testo=t;
		Tipo=tipo;
		Importanza=imp;
	}
	
	public int getID(){
		return IDP;
	}
	
	public String getNome(){
		return Nome;
	}
	
	public int getTipo(){
		//TODO Selezionare risorsa in base al tipo della preghiera
		return 1;
	}
	
	public String getTesto(){
		return Testo;
	}
	
	public int getImportanza(){
		return Importanza;
	}
	
	public void setID(int i){
		IDP=i;
	}
	
	public void setNome(String n){
		Nome=n;
	}
	
	public void setTesto(String t){
		Testo=t;
	}
	
	public void setTipo(String t){
		Tipo=t;
	}	
	
	public void setImportanza(int i){
		Importanza=i;
	}
}
