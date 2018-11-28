public class Participant {

	private int id_participant;
	private String echelon;
	
	//constructeur
	public Participant(int id, String echelon) {
	this.id_participant=id;
	this.echelon=echelon;
	}
	public int getId_participant(){
		return id_participant;		
	}
	public void setId_participant(int id_participant){
		this.id_participant=id_participant
	}
	
	public int getEchelon(){
		return echelon;		
	}
	public void setEchelon(String echelon){
		this.echelon=echelon;
	}
	
	
}