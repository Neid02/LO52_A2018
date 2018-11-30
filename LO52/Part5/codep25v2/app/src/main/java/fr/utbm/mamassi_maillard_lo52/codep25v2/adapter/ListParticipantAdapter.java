package fr.utbm.mamassi_maillard_lo52.codep25v2.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.utbm.mamassi_maillard_lo52.codep25v2.R;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.ParticipantDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.Participant;

public class ListParticipantAdapter extends BaseAdapter {

    public static final String TAG = "ListParticipantAdapter";

    private List<Participant> mItems;
    private LayoutInflater mInflater;
    private ParticipantDAO mDAO;
    Context mContext;

    public ListParticipantAdapter(Context context, List<Participant> list) {
        this.setItems(list);
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Participant getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getmId() : position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(v == null) {
            v = mInflater.inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder();
            holder.txt = (TextView) v.findViewById(R.id.txtview);
            holder.buttonTrash = (ImageButton) v.findViewById(R.id.buttonTrash);
            holder.parentLayout = (RelativeLayout)  v.findViewById(R.id.parent_layout_list_item);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Participant currentItem = getItem(position);
        if(currentItem != null) {
            holder.txt.setText(currentItem.getmNom() + " " + currentItem.getmPrenom() + " : " + Integer.toString(currentItem.getmEchelon()));
        }

        holder.buttonTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialogConfirmation(mItems.get(position));
            }
        });



        return v;
    }

    public List<Participant> getItems() {
        return mItems;
    }

    public void setItems(List<Participant> mItems) {
        this.mItems = mItems;
    }

    private final void showDeleteDialogConfirmation(final Participant clicked) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        alertDialogBuilder.setTitle("Suppression");
        alertDialogBuilder.setMessage("Voulez vous supprimer le Participant "+clicked.getmNom()+ " " + clicked.getmPrenom() + "?");

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete the company and refresh the list
                mDAO = new ParticipantDAO(mContext);
                if(mDAO != null) {
                    mDAO.deleteParticipant(clicked);
                    mItems.remove(clicked);

                    notifyDataSetChanged();
                    Toast.makeText(mContext,"Participant n°" + clicked.getmId() + " : Suppression validé",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(mContext," Echec de la suppression",Toast.LENGTH_SHORT).show();

                dialog.dismiss();
            }
        });


        // set neutral button OK
        alertDialogBuilder.setNeutralButton(android.R.string.no, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

    class ViewHolder {
        TextView txt;
        RelativeLayout parentLayout;
        ImageButton buttonTrash;
    }

}


