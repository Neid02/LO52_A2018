package com.utbm.lo52.f1levier.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Spinner;

import com.utbm.lo52.f1levier.R;
import com.utbm.lo52.f1levier.model.GroupeIhm;
import com.utbm.lo52.f1levier.viewmodel.GroupListViewModel;
import com.utbm.lo52.f1levier.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements OnItemSelectedListener, OnClickListener {

    private OnFragmentInteractionListener mListener;

    private Chronometer chronometer;
    private Button chronometerButton;

    private Spinner spinnerGroupe;

    private long pauseOffset;
    private boolean running;

    private static final int SPRINT1 = 1;
    private static final int FRACTION1 = 2;
    private static final int PIT_STOP = 3;
    private static final int SPRINT2 = 4;
    private static final int FRACTION2 = 5;

    private int etape;
    private int participant_index;

    List<String> nomsGroupes = new ArrayList<>();
    private GroupListViewModel groupListViewModel;

    private HomeViewModel homeViewModel;

    public HomeFragment() {

    }

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        etape = 0;
        participant_index = 0;

        groupListViewModel = ViewModelProviders.of(this).get(GroupListViewModel.class);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);

        nomsGroupes = groupListViewModel.getAllGroupNames();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        chronometer = view.findViewById(R.id.chronometer);

        chronometerButton = view.findViewById(R.id.start);
        chronometerButton.setOnClickListener(this);

        spinnerGroupe = view.findViewById(R.id.spinner);
        spinnerGroupe.setOnItemSelectedListener(this);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, nomsGroupes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerGroupe.setAdapter(adapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public void OnChronometerButtonClick() {

        System.out.println(homeViewModel.getSelectedGroup().getParticipants().size());
        if(participant_index <= homeViewModel.getSelectedGroup().getParticipants().size()) {
            switch (etape) {
                case 0:
                    startChronometer();
                    chronometerButton.setText("Sprint 1");
                    break;
                case SPRINT1:
                    chronometerButton.setText("Fraction 1");
                    break;
                case FRACTION1:
                    chronometerButton.setText("Pit stop");
                    break;
                case PIT_STOP:
                    chronometerButton.setText("Sprint 2");
                    break;
                case SPRINT2:
                    chronometerButton.setText("Fraction 2");
                    break;
                case FRACTION2:
                    chronometerButton.setText("Start");
                    pauseChronometer();
                    resetChronometer();
                    etape = 0;
                    participant_index++;
                    break;
                default:
                    break;
            }
            etape++;
        }
        else {
            chronometerButton.setText("Terminé");
            chronometerButton.setEnabled(false);
        }
    }

    public void startChronometer() {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }

    public void pauseChronometer() {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }
    }

    public void resetChronometer() {
        chronometer.stop();
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String nomGroupe = parent.getItemAtPosition(position).toString();
        homeViewModel.setSelectedItem(nomGroupe);
        homeViewModel.setSelectedGroup(groupListViewModel.getGroupIhmByName(nomGroupe));
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.start:
                OnChronometerButtonClick();
                break;
            default:
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction();
    }

}
