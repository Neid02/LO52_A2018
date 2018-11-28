package com.TO52.dao.implement;

public class PitstopDAO extends DAO<Pitstop> {
  public PitstopDAO(Connection conn) {
    super(conn);
  }

  public boolean create(Participant obj) {
    return false;
  }

  public boolean delete(Participant obj) {
    return false;
  }
   
  public boolean update(Participant obj) {
    return false;
  }
   
  public Pitstop find(int id) {
    Pitstop pitstop = new Pitstop();      
      
    try {
      ResultSet result = this.connect.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Pitstop WHERE id_pitstop = " + id);
      if(result.first())
        pitstop = new Pitstop(
          id,
          result.getString("nom"),
		  result.getString("tempsPi"),       
        ));         
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return participant;
  }
}