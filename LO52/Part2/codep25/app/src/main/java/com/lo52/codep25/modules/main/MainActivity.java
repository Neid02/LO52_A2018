 package com.lo52.codep25.modules.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.lo52.codep25.R;
import com.lo52.codep25.dao.GroupeDao;
import com.lo52.codep25.models.Groupe;

 public class MainActivity extends AppCompatActivity {

     GroupeDao groupeDaoImplementation;

     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         groupeDaoImplementation = new GroupeDao(getApplicationContext());
         Groupe groupe = new Groupe();
         groupe.setTitle("Integrale");
         groupeDaoImplementation.addGroupe(groupe);
         Log.d("List", groupeDaoImplementation.findAll().toString());
    }
}
