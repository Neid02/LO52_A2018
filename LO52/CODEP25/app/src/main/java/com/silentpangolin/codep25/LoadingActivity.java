package com.silentpangolin.codep25;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.silentpangolin.codep25.DataBase.ORM.DBCoureur;
import com.silentpangolin.codep25.DataBase.ORM.DBEquipe;
import com.silentpangolin.codep25.DataBase.ORM.DBTemps;
import com.silentpangolin.codep25.Objects.Coureur;

import java.util.ArrayList;

public class LoadingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        new CreateTeams(this).execute();
    }

    private class CreateTeams extends AsyncTask<String, Void, String>{
        private Context context;

        public CreateTeams(Context context){
            this.context = context;
        }
        @Override
        protected String doInBackground(String... params){
            try {
                String[] nameTeams = {"Zenithar", "Kynareth", "Asari", "Krogan", "Galarien", "Volus", "Geth", "Turien", "Mara", "Orsimer", "Nordique", "Julianos", "Dibella", "Argonien", "Dunmer", "Elcor", "Dragon", "Falmer", "Dovakhiin", "Bosmer", "Septim", "Humain", "Prothéen", "Butarien", "Hanari", "Altmer", "Rougegarde", "Bréton", "Promo 16 !", "Prince Daedra", "Khajiit", "Maormer", "Akatosh", "Stendarr", "Thorien", "Impériaux", "Dwemer", "Rachni", "Veilleurs", "Moissonneur", "Quarien", "Arkay"};
                int rand = (int) ((Math.random() * 100) % 42);
                DBCoureur dbCoureur = new DBCoureur(getApplicationContext());
                dbCoureur.open();
                ArrayList<Coureur> all = dbCoureur.getAllCoureur();
                dbCoureur.close();

                int gap = 1, count, average = 0;
                ArrayList<String> insertTeams = new ArrayList<>();

                for (Coureur c : all) average += c.getEchelon_crr();
                average /= all.size();
                average *= 3;
                boolean[] isInTeam = new boolean[all.size()];
                boolean valid = true;

                while (valid) {
                    count = 0;
                    insertTeams = new ArrayList<>();
                    for (int i = 0; i < all.size(); ++i) {
                        isInTeam[i] = false;
                    }
                    for (Coureur c : new ArrayList<>(all)) {
                        for (Coureur b : new ArrayList<>(all)) {
                            for (Coureur a : new ArrayList<>(all)) {
                                if ((c.getId_crr() != b.getId_crr()) && (b.getId_crr() != a.getId_crr()) && (c.getId_crr() != a.getId_crr())
                                        && !(isInTeam[all.indexOf(c)]) && !(isInTeam[all.indexOf(b)]) && !(isInTeam[all.indexOf(a)])) {
                                    if (((c.getEchelon_crr() + b.getEchelon_crr() + a.getEchelon_crr()) < (average + gap))
                                            && ((c.getEchelon_crr() + b.getEchelon_crr() + a.getEchelon_crr()) > (average - gap))) {
                                        isInTeam[all.indexOf(c)] = true;
                                        isInTeam[all.indexOf(b)] = true;
                                        isInTeam[all.indexOf(a)] = true;
                                        c.setId_equ_crr(count);
                                        b.setId_equ_crr(count);
                                        a.setId_equ_crr(count);
                                        c.setOrdrepassage_crr(1);
                                        b.setOrdrepassage_crr(2);
                                        a.setOrdrepassage_crr(3);
                                        insertTeams.add("INSERT INTO EQUIPE(id_equ, name_equ) VALUES (" + count + ", '" + nameTeams[rand] + "');");
                                        rand = (++rand) % 42;
                                        ++count;
                                    }
                                }
                            }
                            if ((c.getId_crr() != b.getId_crr()) && !(isInTeam[all.indexOf(c)]) && !(isInTeam[all.indexOf(b)])) {
                                if (((c.getEchelon_crr() + b.getEchelon_crr()) < (average + gap)) && ((c.getEchelon_crr() + b.getEchelon_crr()) > (average - gap))) {
                                    isInTeam[all.indexOf(c)] = true;
                                    isInTeam[all.indexOf(b)] = true;
                                    c.setId_equ_crr(count);
                                    b.setId_equ_crr(count);
                                    c.setOrdrepassage_crr(1);
                                    b.setOrdrepassage_crr(2);
                                    insertTeams.add("INSERT INTO EQUIPE(id_equ, name_equ) VALUES (" + count + ", '" + nameTeams[rand] + "');");
                                    rand = (++rand) % 42;
                                    ++count;
                                }
                            }
                        }
                    }
                    valid = false;
                    for (int i = 0; i < all.size(); ++i) {
                        if (!(isInTeam[i])) {
                            valid = true;
                        }
                    }
                    ++gap;
                }
                DBEquipe dbEquipe = new DBEquipe(getApplicationContext());
                dbEquipe.open();
                dbEquipe.deleteAll();
                dbEquipe.intertTeams(insertTeams);
                dbEquipe.close();
                dbCoureur.open();
                dbCoureur.updateAllCoureurs(all);
                dbCoureur.close();

                DBTemps dbTemps = new DBTemps(getApplicationContext());
                dbTemps.open();
                dbTemps.deleteIDTeam();
                dbTemps.close();

            } catch (Exception e) {
                e.printStackTrace();
                Log.wtf("SQL", e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String params){
            ((Activity)context).finish();

        }
    }

}
