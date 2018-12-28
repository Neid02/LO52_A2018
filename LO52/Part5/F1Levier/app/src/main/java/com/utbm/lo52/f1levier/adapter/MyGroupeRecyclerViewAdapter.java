package com.utbm.lo52.f1levier.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.utbm.lo52.f1levier.R;
import com.utbm.lo52.f1levier.entity.Groupe;
import com.utbm.lo52.f1levier.fragment.GroupesFragment.OnListFragmentInteractionListener;
import com.utbm.lo52.f1levier.model.GroupeIhm;

import java.util.List;

public class MyGroupeRecyclerViewAdapter extends RecyclerView.Adapter<MyGroupeRecyclerViewAdapter.ViewHolder> {

    private List<GroupeIhm> groupesIhm;
    private final OnListFragmentInteractionListener mListener;

    public MyGroupeRecyclerViewAdapter(List<GroupeIhm> groupesIhm, OnListFragmentInteractionListener listener) {
        this.groupesIhm = groupesIhm;
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_groupe, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.groupeIhm = groupesIhm.get(position);
        holder.mIdView.setText(Integer.toString(holder.groupeIhm.getId()));
        holder.mNomGroupeView.setText(holder.groupeIhm.getNomGroupe());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.groupeIhm);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return groupesIhm.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mNomGroupeView;
        public GroupeIhm groupeIhm;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mNomGroupeView = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mNomGroupeView.getText() + "'";
        }
    }
}
