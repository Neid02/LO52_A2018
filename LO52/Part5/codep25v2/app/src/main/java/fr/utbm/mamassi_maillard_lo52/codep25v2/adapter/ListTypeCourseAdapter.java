package fr.utbm.mamassi_maillard_lo52.codep25v2.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import fr.utbm.mamassi_maillard_lo52.codep25v2.activities.ParametresTours;
import fr.utbm.mamassi_maillard_lo52.codep25v2.dao.TypeCourseDAO;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeCourse;

public class ListTypeCourseAdapter extends BaseAdapter {

    public static final String TAG = "ListTypeCourseAdapter";

    private List<TypeCourse> mItems;
    private LayoutInflater mInflater;
    private TypeCourseDAO mTypeCourseDao;
    Context mContext;

    public ListTypeCourseAdapter(Context context, List<TypeCourse> listTypeCourse) {
        this.setItems(listTypeCourse);
        this.mInflater = LayoutInflater.from(context);
        mContext = context;
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public TypeCourse getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getmId() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
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
        final TypeCourse currentItem = getItem(position);
        if(currentItem != null) {
            holder.txt.setText(currentItem.getmTitre());
        }

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ParametresTours.class);
                Bundle b = new Bundle();
                b.putLong(ParametresTours.EXTRA_SELECTED_TYPECOURSE_ID, currentItem.getmId());
                intent.putExtras(b);
                mContext.startActivity(intent);
            }
        });

        holder.buttonTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteDialogConfirmation(currentItem);
            }
        });



        return v;
    }

    public List<TypeCourse> getItems() {
        return mItems;
    }

    public void setItems(List<TypeCourse> mItems) {
        this.mItems = mItems;
    }

    private final void showDeleteDialogConfirmation(final TypeCourse clicked) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        alertDialogBuilder.setTitle("Suppression");
        alertDialogBuilder.setMessage("Voulez vous supprimer le type de course : "+clicked.getmTitre()+"?");

        // set positive button YES message
        alertDialogBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // delete the company and refresh the list
                mTypeCourseDao = new TypeCourseDAO(mContext);
                if(mTypeCourseDao != null) {
                    mTypeCourseDao.deleteTypeCourse(clicked);
                    mItems.remove(clicked);

                    notifyDataSetChanged();
                    Toast.makeText(mContext,clicked.getmTitre() + " : Suppression validé",Toast.LENGTH_SHORT).show();
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
