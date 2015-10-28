package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import main.example.jeff.personalbanker.R;

/**
 * Created by jeff on 2015-01-07.
 */
public class TotalSpendingsAdapter extends BaseAdapter {

    LayoutInflater layoutinflater;

    public TotalSpendingsAdapter(Context c){
        this.layoutinflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutinflater.inflate(R.layout.spendingslistview_otalspendings, parent, false);
        return convertView;
    }
}
