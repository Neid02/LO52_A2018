package com.lo52.codep25.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.lo52.codep25.R;
import com.lo52.codep25.models.Participant;

import java.util.ArrayList;
import java.util.List;

public class ParticipantAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Participant> participants;

    public ParticipantAdapter(Context context, ArrayList<Participant> participantdata) {

        this.context = context;
        participants = participantdata;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.participant_item, null);
        return new ViewHolderParticipant(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
       ((ViewHolderParticipant) holder).participantGroupe.setText(String.valueOf(participants.get(position).getEchelon()));
        ((ViewHolderParticipant) holder).partticipantName.setText(participants.get(position).getNom() + " " + participants.get(position).getPrenom());
        ((ViewHolderParticipant) holder).participantID.setText(participants.get(position).getId());
        ((ViewHolderParticipant) holder).ratingBar.setRating(getRating(participants.get(position).getEchelon()));
        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color_randon = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRoundRect(String.valueOf(participants.get(position).getNom().charAt(0))+String.valueOf(participants.get(position).getPrenom().charAt(0)), color_randon, 10);
        ((ViewHolderParticipant) holder).user_tof.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return participants.size();
    }

    public static class ViewHolderParticipant extends RecyclerView.ViewHolder {

        TextView partticipantName, participantID, participantGroupe;
        ImageView user_tof;
        RatingBar ratingBar;

        ViewHolderParticipant(View itemLayoutView) {
            super(itemLayoutView);

            this.partticipantName = itemLayoutView.findViewById(R.id.username);
            this.ratingBar =  itemLayoutView.findViewById(R.id.echelon);
            this.participantID = itemLayoutView.findViewById(R.id.participantID);
            this.participantGroupe = itemLayoutView.findViewById(R.id.userequipe);
            this.user_tof = itemLayoutView.findViewById(R.id.user_tof);


        }
    }
    public static float getRating(int echelon){

        return Float.parseFloat(String.valueOf(echelon*0.05));
    }

}
