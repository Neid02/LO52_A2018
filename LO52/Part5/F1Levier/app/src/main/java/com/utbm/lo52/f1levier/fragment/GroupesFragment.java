package com.utbm.lo52.f1levier.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.utbm.lo52.f1levier.R;
import com.utbm.lo52.f1levier.adapter.MyGroupeRecyclerViewAdapter;
import com.utbm.lo52.f1levier.entity.Groupe;
import com.utbm.lo52.f1levier.entity.Participant;
import com.utbm.lo52.f1levier.model.GroupeIhm;
import com.utbm.lo52.f1levier.viewmodel.GroupListViewModel;
import com.utbm.lo52.f1levier.viewmodel.ParticipantListViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroupesFragment extends Fragment {

    private OnListFragmentInteractionListener mListener;
    private RecyclerView recyclerView;

    private List<GroupeIhm> groupesIhm = new ArrayList<>();
    private GroupListViewModel groupListViewModel;
    private ParticipantListViewModel participantListViewModel;

    public GroupesFragment() {
    }

    public static GroupesFragment newInstance() {
        return new GroupesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        groupesIhm.clear();

        groupListViewModel = ViewModelProviders.of(this).get(GroupListViewModel.class);
        participantListViewModel = ViewModelProviders.of(this).get(ParticipantListViewModel.class);
        groupListViewModel.deleteAll();
        for (int i=0; i<10; i++) {
            groupListViewModel.insert(newGroupeIhm());
        }

        groupesIhm = groupListViewModel.getAllGroupsIhm();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groupe_list, container, false);

        recyclerView = view.findViewById(R.id.listGroupes);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerView.setAdapter(new MyGroupeRecyclerViewAdapter(groupesIhm, mListener));

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        return view;
    }

    private GroupeIhm newGroupeIhm() {
        GroupeIhm groupeIhm = new GroupeIhm();

        List<Participant> participants = new ArrayList<>();
        participants.add(participantListViewModel.getParticipantByName("Florian", "RIFFLART"));
        participants.add(participantListViewModel.getParticipantByName("Nadège", "PANASSIM"));

        groupeIhm.setNomGroupe("Groupe N+F");
        groupeIhm.setParticipants(participants);

        return groupeIhm;
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(GroupeIhm groupeIhm);
    }

}
