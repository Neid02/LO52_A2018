package com.utbm.lo52.f1levier.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utbm.lo52.f1levier.R;
import com.utbm.lo52.f1levier.entity.Participant;

import java.util.ArrayList;
import java.util.List;


public class ParticipantsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;

    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    private List<Participant> participants = new ArrayList<>();

    public ParticipantsFragment() {
    }

    public static ParticipantsFragment newInstance(int columnCount) {
        ParticipantsFragment fragment = new ParticipantsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        participants.clear();
        for(int i=0; i<15; i++) {
            addParticipants("RIFFLART", "Florian", 76);
            addParticipants("PANASSIM", "Nadège", 60);
        }

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_participants_list, container, false);
        recyclerView = view.findViewById(R.id.list);

        Context context = view.getContext();

        if (mColumnCount <= 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }

        recyclerView.setAdapter(new MyParticipantsRecyclerViewAdapter(participants, mListener));

        return view;
    }

    private void addParticipants(String nom, String prenom, int poids) {
        Participant participant = new Participant();
        participant.setId(participants.size() + 1);
        participant.setPrenom(prenom);
        participant.setNom(nom);
        participant.setPoids(poids);

        participants.add(participant);
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
