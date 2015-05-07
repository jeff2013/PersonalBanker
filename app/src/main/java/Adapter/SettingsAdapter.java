package Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import main.example.jeff.personalbanker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeff on 2015-01-03.
 */
public class SettingsAdapter extends BaseAdapter{

    List<String> listOfSettings;
    LayoutInflater layoutInflater;
    Context context;

    public SettingsAdapter(Context c){
        this.layoutInflater = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        listOfSettings = new ArrayList<String>();
        listOfSettings.add("Goal");
        listOfSettings.add("Reset");
        listOfSettings.add("About");
        listOfSettings.add("Support");
        this.context = c;
    }

    @Override
    public int getCount() {
        return listOfSettings.size();
    }

    @Override
    public Object getItem(int position) {
        return  listOfSettings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch(position){
            case 0: convertView = layoutInflater.inflate(R.layout.settingslistview_goal, parent, false);
                TextView goalTextView = (TextView)convertView.findViewById(R.id.goalAmount);
                SharedPreferences preferences = context.getSharedPreferences("goal", Context.MODE_PRIVATE);
                if(!preferences.contains("goalAmount")) preferences.edit().putFloat("goalAmount", 104.1f).apply();
                goalTextView.setText(Float.toString(preferences.getFloat("goalAmount", 105.0f)));
                break;
            case 1: convertView = layoutInflater.inflate(R.layout.settingslistview_reset, parent, false);
                break;
            case 2: convertView = layoutInflater.inflate(R.layout.settingslistview_about, parent, false);
                break;
            case 3: convertView = layoutInflater.inflate(R.layout.settingslistview_support, parent, false);
                break;
        }
        return convertView;
    }
}
