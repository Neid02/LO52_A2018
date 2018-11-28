public class Tour {

	private int id_Tour;
	private String typeTour ;
	private double tempsTo;
	
	//constructeur
	public Tour(int id, String typeTour,double tempsTo) {
	this.id_Tour=id;
	this.typeTour=typeTour;
	this.tempsTo=tempsTo;
	}
	public int getId_Tour(){
		return id_Tour;		
	}
	public void setId_Tour (int id_Tour){
		this.id_Tour=id_Tour;
	}
	
	public String getTypeTour(){
		return typeTour;		
	}
	public void setTypeTour(String typeTour){
		this.typeTour=typeTour;
	}
	public double getTempsTo(){
		return tempsTo;		
	}
	public void setNom(double tempsTo){
		this.tempsTo=tempsTo;
	
	
}