package adury_csanchez.utbm.f1levier.adapters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import adury_csanchez.utbm.f1levier.R;
import adury_csanchez.utbm.f1levier.model.Runner;

public class ListRunnersAdapter extends BaseAdapter {

    public static final String TAG = "ListRunnersAdapter";

    private List<Runner> mItems;
    private LayoutInflater mInflater;

    private ArrayList<Boolean> mSelectedItemsArray; // todo

    public ListRunnersAdapter(Context context, List<Runner> listRunner) {
        this.setItems(listRunner);
        this.mInflater = LayoutInflater.from(context);

        this.mSelectedItemsArray = new ArrayList<>(Arrays.asList(new Boolean[listRunner.size()]));
        Collections.fill(this.mSelectedItemsArray, false);
    }

    @Override
    public int getCount() {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().size() : 0 ;
    }

    @Override
    public Runner getItem(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position) : null ;
    }

    @Override
    public long getItemId(int position) {
        return (getItems() != null && !getItems().isEmpty()) ? getItems().get(position).getId() : position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        ViewHolder holder;
        if(v == null) {
            v = mInflater.inflate(R.layout.list_item_runner, parent, false);
            holder = new ViewHolder();
            holder.txtFirstName = (TextView) v.findViewById(R.id.txt_runner_first_name);
            holder.txtLastName = (TextView) v.findViewById(R.id.txt_runner_last_name);
            holder.txtWeight = (TextView) v.findViewById(R.id.txt_runner_weight);
            holder.checkBoxSelected = (CheckBox) v.findViewById(R.id.checkbox_runner_selected);
            v.setTag(holder);
        }
        else {
            holder = (ViewHolder) v.getTag();
        }

        // fill row data
        Runner currentItem = getItem(position);
        if(currentItem != null) {
            holder.txtFirstName.setText(currentItem.getFistName());
            holder.txtLastName.setText(currentItem.getLastName());
            holder.txtWeight.setText(Integer.toString(currentItem.getWeight()));
        }

        if(position==mSelectedItemsArray.size())
            mSelectedItemsArray.add(false);

        holder.checkBoxSelected.setTag(position);
        holder.checkBoxSelected.setChecked(isChecked(position));

        CompoundButton.OnCheckedChangeListener mListener = new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mSelectedItemsArray.set(((Integer) buttonView.getTag()).intValue(), isChecked);
            }
        };

        holder.checkBoxSelected.setOnCheckedChangeListener(mListener);

        return v;
    }

    // TODO : to improve
    public void removeSelected(int position)
    {
        mSelectedItemsArray.remove(position);
    }

    public boolean isChecked(int position)
    {
        return mSelectedItemsArray.get(position);
    }

    public void setChecked(int position, boolean isChecked)
    {
        mSelectedItemsArray.set(position, isChecked);
    }

    public void toggle(int position)
    {
        mSelectedItemsArray.set(position, !mSelectedItemsArray.get(position));
    }

    public List<Runner> getItems() {
        return mItems;
    }

    public void setItems(List<Runner> mItems) {
        this.mItems = mItems;
    }

    class ViewHolder {
        TextView txtFirstName;
        TextView txtLastName;
        TextView txtWeight;
        CheckBox checkBoxSelected;
    }
}
