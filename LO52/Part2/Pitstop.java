public class Pitstop {

	private int id_pitstop;
	private String nom;
	private double tempsPi;
	
	//constructeur
	public Pitstop(int id, String nom,double tempsPi) {
	this.id_pitstop=id;
	this.nom=nom;
	this.tempsPi=tempsPi;
	}
	public int getId_pitstop(){
		return id_pitstop;		
	}
	public void setId_pitstop (int id_pitstop){
		this.id_pitstop=id_pitstop;
	}
	
	public String getNom(){
		return nom;		
	}
	public void setNom(String nom){
		this.nom=nom;
	}
	public double tempsPi(){
		return tempsPi;		
	}
	public void setNom(double tempsPi){
		this.tempsPi=tempsPi;
	
	
}