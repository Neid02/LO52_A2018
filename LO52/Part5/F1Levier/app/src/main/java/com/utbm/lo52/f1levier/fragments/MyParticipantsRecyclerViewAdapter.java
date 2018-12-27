package com.utbm.lo52.f1levier.fragments;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utbm.lo52.f1levier.R;
import com.utbm.lo52.f1levier.fragments.ParticipantsFragment.OnListFragmentInteractionListener;
import com.utbm.lo52.f1levier.entity.Participant;

import java.util.List;

public class MyParticipantsRecyclerViewAdapter extends RecyclerView.Adapter<MyParticipantsRecyclerViewAdapter.ViewHolder> {

    private final List<Participant> participants;
    private final OnListFragmentInteractionListener mListener;

    public MyParticipantsRecyclerViewAdapter(List<Participant> participants, OnListFragmentInteractionListener listener) {
        this.participants = participants;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_participants, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.participant = participants.get(position);
        holder.mIdView.setText(Integer.toString(holder.participant.getId()));
        holder.mNomView.setText(holder.participant.getNom());
        holder.mPrenomView.setText(holder.participant.getPrenom());
        holder.mPoidsView.setText(Integer.toString(holder.participant.getPoids()));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.participant);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNomView;
        public final TextView mPrenomView;
        public final TextView mPoidsView;
        public Participant participant;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.id);
            mNomView = view.findViewById(R.id.nom);
            mPrenomView = view.findViewById(R.id.prenom);
            mPoidsView = view.findViewById(R.id.poids);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNomView.getText() + "'";
        }
    }
}
