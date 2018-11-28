package com.TO52.dao.implement;

public class ParticipantDAO extends DAO<Participant> {
  public ParticipantDAO(Connection conn) {
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
   
  public Participant find(int id) {
    Participant participant = new Participant();      
      
    try {
      ResultSet result = this.connect.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Participant WHERE id_participant = " + id);
      if(result.first())
        participant = new Participant(
          id,
          result.getString("echelon")          
        ));         
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return participant;
  }
}