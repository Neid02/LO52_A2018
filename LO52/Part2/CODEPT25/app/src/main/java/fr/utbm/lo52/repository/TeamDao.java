package fr.utbm.lo52.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import fr.utbm.lo52.domain.Runner;
import fr.utbm.lo52.domain.Team;

public class TeamDao extends BaseDao {

    public static final String TABLE_NAME = "Team";
    public static final String TEAM_KEY = "idTeam";
    public static final String RACE_KEY = "idRace";
    public static final String NAME = "name";
    public static final String TABLE_RACE = "Race";
    public static final String RACE_ID = "id";



    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    RACE_KEY + " INTEGER NOT NULL, " +
                    TEAM_KEY + " INTEGER NOT NULL, " +
                    NAME + " TEXT, " +
                    "PRIMARY KEY (" +TEAM_KEY+ "," +RACE_KEY+" ),"+
                    "FOREIGN KEY("+RACE_KEY+") REFERENCES "+TABLE_RACE+"("+RACE_ID+"));";

    public static final String TABLE_DROP =  "DROP TABLE IF EXISTS " + TABLE_NAME + ";";

    public TeamDao(Context pContext) {
        super(pContext);
    }

    /**
     * @param t l'équipe à ajouter à la base
     */
    public boolean create(Team t) {
        // CODE
        SQLiteDatabase db = this.open();
        ContentValues contentValues= new ContentValues();
        contentValues.put(RACE_KEY, t.getIdRace());
        contentValues.put(TEAM_KEY, t.getId());
        contentValues.put(NAME, t.getName());
        long result= db.insert(TABLE_NAME,null,contentValues);
        if (result==-1)
            return false;
        else
            return true;
    }


    public Cursor allTeam() {
        // CODE
        SQLiteDatabase db = this.open();
        Cursor result= db.rawQuery("select * from " +TABLE_NAME,null);
        return result;
    }


    /**
     * @param idRace and idTeam l'identifiant de l'équipe à supprimer
     */
    public void delete(long idRace, long idTeam) {
        // CODE
        SQLiteDatabase db = this.open();
        db.delete(TABLE_NAME, RACE_KEY + " = ? AND" +TEAM_KEY + " = ?"  , new String[] {String.valueOf(idRace),String.valueOf(idTeam)});
    }

    /**
     * @param t l'equipe à modifier
     */
    public void update(Team t) {
        // CODE
        SQLiteDatabase db = this.open();
        ContentValues value = new ContentValues();
        value.put(RACE_KEY, t.getIdRace());
        value.put(TEAM_KEY, t.getId());
        value.put(NAME, t.getName());
        db.update(TABLE_NAME, value, RACE_KEY  + " = ? AND" +TEAM_KEY + " = ?", new String[] {String.valueOf(t.getIdRace()),String.valueOf(t.getId())});

    }

    /**
     * @param idRace and idTeam l'identifiant de la personne à récupérer
     */
    public Team findRaceById(long idRace, long idTeam) {
        // CODE
        SQLiteDatabase db = this.open();
        Cursor result= db.rawQuery("select * from " +TABLE_NAME + " where idRace =? AND idTeam=? ",new String[]{String.valueOf(idRace),String.valueOf(idTeam)});
        Team team;
        if(result==null){
            team = new Team();
        }
        else {
            long RaceId = result.getLong(0);
            long TeamId = result.getLong(1);
            String name = result.getString(2);
            team = new Team(RaceId, TeamId,name);
        }
        return team;
    }
}


