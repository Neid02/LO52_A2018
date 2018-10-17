 package com.lo52.codep25.modules.main;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.lo52.codep25.R;
import com.lo52.codep25.dao.EquipeDao;
import com.lo52.codep25.dao.GroupeDao;
import com.lo52.codep25.dao.ParticipantDao;
import com.lo52.codep25.fragments.MainFragment;
import com.lo52.codep25.fragments.ParticipantFragment;
import com.lo52.codep25.models.Equipe;
import com.lo52.codep25.models.Groupe;
import com.lo52.codep25.models.Participant;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

 public class MainActivity extends AppCompatActivity {
     FrameLayout frameLayout;
     GroupeDao groupeDaoImplementation;
     EquipeDao equipeDao;
    ParticipantDao participantDao;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         groupeDaoImplementation = new GroupeDao(getApplicationContext());
         equipeDao = new EquipeDao(getApplicationContext());
         participantDao = new ParticipantDao(getApplicationContext());
         Groupe groupe = new Groupe();
         groupe.setTitle("Integrale");
         groupeDaoImplementation.addGroupe(groupe);

         Equipe equipe = new Equipe();
         equipe.setName("Leopards");
          equipeDao.addEquipe(equipe);


         Participant participant = new Participant();
         participant.setNom("Integrale");
         participant.setPrenom("Loho");
         //participant.setEquipe(equipe);
         //participant.setGroupe(groupe);
         participant.setEchelon(25);
         participantDao.addParticipant(participant);
         frameLayout = (FrameLayout) findViewById(R.id.framelayout);


         BottomBar bottomBar = (BottomBar) findViewById(R.id.bottombar);
         for (int i = 0; i < bottomBar.getTabCount(); i++) {
             bottomBar.getTabAtPosition(i).setGravity(Gravity.CENTER_VERTICAL);
         }



         /*roughike bottombar library code is here*/

         bottomBar.setOnTabSelectListener(new OnTabSelectListener() {

             @Override
             public void onTabSelected(@IdRes int tabId) {
                 Fragment fragment = null;
                 switch (tabId) {
                     case R.id.clean:
                         replace_fragment(new MainFragment());
                         break;
                     case R.id.wifi:
                         replace_fragment(new ParticipantFragment());
                         break;
                     case R.id.more:
                         replace_fragment(new MainFragment());
                         break;


                 }


             }
         });
         Log.d("List", groupeDaoImplementation.findAll().toString());
    }
     public void replace_fragment(Fragment fragment) {
         FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
         transaction.replace(R.id.framelayout, fragment);
         transaction.commit();
     }
}
