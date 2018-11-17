package com.lo52.codep25.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.lo52.codep25.R;
import com.lo52.codep25.models.Equipe;

import java.util.ArrayList;
import java.util.List;

public class EquipeAdapter extends RecyclerView.Adapter {
    private Context context;
    private List<Equipe> litEquipes;

    public EquipeAdapter(Context context, ArrayList<Equipe> a) {

        this.context = context;
        litEquipes = a;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.equipe_item, null);
        return new EquipeAdapter.ViewHolderEquipe(itemLayoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolderEquipe) holder).nomequipe.setText(String.valueOf(litEquipes.get(position).getName()));
        ((ViewHolderEquipe) holder).equipeID.setText(litEquipes.get(position).getId());

        ColorGenerator generator = ColorGenerator.MATERIAL;
        int color_randon = generator.getRandomColor();

        TextDrawable drawable = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRoundRect(String.valueOf(litEquipes.get(position).getName().charAt(0))+String.valueOf(position+1), color_randon, 1);
        ((ViewHolderEquipe)holder).photo.setImageDrawable(drawable);
    }

    @Override
    public int getItemCount() {
        return litEquipes.size();
    }

    public static class ViewHolderEquipe extends RecyclerView.ViewHolder {

        TextView nomequipe, equipeID;
        ImageView photo;

        ViewHolderEquipe(View itemLayoutView) {
            super(itemLayoutView);

            this.nomequipe = itemLayoutView.findViewById(R.id.nomequipe);
            this.equipeID =  itemLayoutView.findViewById(R.id.equipeID);
            this.photo = itemLayoutView.findViewById(R.id.team_tof);


        }
    }
}
