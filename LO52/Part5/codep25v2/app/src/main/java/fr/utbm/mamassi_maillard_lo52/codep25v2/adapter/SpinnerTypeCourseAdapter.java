package fr.utbm.mamassi_maillard_lo52.codep25v2.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import fr.utbm.mamassi_maillard_lo52.codep25v2.R;
import fr.utbm.mamassi_maillard_lo52.codep25v2.model.TypeCourse;

public class SpinnerTypeCourseAdapter extends BaseAdapter {

    public static final String TAG = "SpinnerTypeCourseAdapter";

    private List<TypeCourse> mItems;
    private LayoutInflater mInflater;

    public SpinnerTypeCourseAdapter(Context context, List<TypeCourse> list) {
        this.setItems(list);
        this.mInflater = LayoutInflater.from(context);
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
            v = mInflater.inflate(R.layout.spinner_item_type_course, parent, false);
            holder = new ViewHolder();
            holder.txtTitre = (TextView) v.findViewById(R.id.txt_titre);
            holder.txtNbTour = (TextView) v.findViewById(R.id.txt_nbTour);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        TypeCourse currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtTitre.setText(currentItem.getmTitre());
            holder.txtNbTour.setText(Long.toString(currentItem.getmId())); //TODO a changer pour récupérer le nombre de tour pour ce type de course!
        }

        return v;
    }

    public List<TypeCourse> getItems() {
        return mItems;
    }

    public void setItems(List<TypeCourse> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtTitre;
        TextView txtNbTour;
    }
}
