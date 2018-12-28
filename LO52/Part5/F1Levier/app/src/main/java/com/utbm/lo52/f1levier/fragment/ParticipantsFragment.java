package com.utbm.lo52.f1levier.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utbm.lo52.f1levier.R;
import com.utbm.lo52.f1levier.adapter.MyParticipantsRecyclerViewAdapter;
import com.utbm.lo52.f1levier.dao.AppDatabase;
import com.utbm.lo52.f1levier.dao.ParticipantDAO;
import com.utbm.lo52.f1levier.entity.Participant;
import com.utbm.lo52.f1levier.viewmodel.ParticipantListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ParticipantsFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    private List<Participant> participants = new ArrayList<>();
    private ParticipantListViewModel participantListViewModel;

    public ParticipantsFragment() {
    }

    public static ParticipantsFragment newInstance() {
        return new ParticipantsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        participants.clear();

        participantListViewModel = ViewModelProviders.of(this).get(ParticipantListViewModel.class);
        participantListViewModel.deleteAll();

        List<Participant> newParticipants = new ArrayList<>();
        newParticipants.add(newParticipant("RIFFLART", "Florian", 76));
        newParticipants.add(newParticipant("PANASSIM", "Nadège", 60));
        participantListViewModel.insertAll(newParticipants);

        participants = participantListViewModel.getAllParticipants();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_participants_list, container, false);

        recyclerView = view.findViewById(R.id.listParticipants);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new MyParticipantsRecyclerViewAdapter(participants, mListener));

        return view;
    }

    private Participant newParticipant(String nom, String prenom, int poids) {
        Participant participant = new Participant();
        participant.setPrenom(prenom);
        participant.setNom(nom);
        participant.setPoids(poids);

        return participant;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
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
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Participant participant);
    }

}
