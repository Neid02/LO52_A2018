package com.TO52.dao.implement;

public class EquipeDAO extends DAO<Equipe> {
  public EquipeDAO(Connection conn) {
    super(conn);
  }

  public boolean create(Equipe obj) {
    return false;
  }

  public boolean delete(Equipe obj) {
    return false;
  }
   
  public boolean update(Equipe obj) {
    return false;
  }
   
  public Equipe find(int id) {
    Equipe equipe = new Equipe();      
      
    try {
      ResultSet result = this.connect.createStatement(
        ResultSet.TYPE_SCROLL_INSENSITIVE,
        ResultSet.CONCUR_READ_ONLY).executeQuery("SELECT * FROM Equipe WHERE id_equipe = " + id);
      if(result.first())
        equipe = new Equipe(
          id,
          result.getString("nomEquipe")          
        ));         
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return equipe;
  }
}