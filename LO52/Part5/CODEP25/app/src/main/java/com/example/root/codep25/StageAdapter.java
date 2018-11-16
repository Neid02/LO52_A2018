package com.example.root.codep25;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class StageAdapter extends ArrayAdapter<Stage> {


    public StageAdapter(Context context, List<Stage> stages) {
            super(context, 0, stages);
        }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_stage,parent, false);
        }

        StageViewHolder viewHolder = (StageViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new StageViewHolder();
            viewHolder.pseudo = (TextView) convertView.findViewById(R.id.pseudo);
            viewHolder.text = (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Stage stage = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.pseudo.setText(stage.getPseudo());
        viewHolder.text.setText(stage.getText());

        return convertView;
    }

    private class StageViewHolder{
        public TextView pseudo;
        public TextView text;
    }
}
