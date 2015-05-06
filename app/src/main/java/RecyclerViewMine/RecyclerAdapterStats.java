package RecyclerViewMine;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeff.personalbanker.R;

import java.util.List;

import Entries.Entry;
import sqlite.SqlDAO;

/**
 * Created by jeff on 2015-04-30.
 */
public class RecyclerAdapterStats extends RecyclerView.Adapter<RecyclerAdapterStats.myViewHolderSummary> {

    private SqlDAO sqlDAO;
    private LayoutInflater inflater;
    private List<Entry> entries;
    private Context c;
    private int foodAmount;
    private int otherAmount;
    private int electronicsAmount;
    private int clothingAmount;
    private enum Categories{
        Other, Food, Electronics, Gasoline, Clothing, Vehicular, Housing, Tuition
    }
    private SharedPreferences sharedPreferences;

    public RecyclerAdapterStats(Context context, SqlDAO data){
        inflater = LayoutInflater.from(context);
        sqlDAO = data;
        entries = data.getEntries();
        c = context;
        sharedPreferences =  c.getSharedPreferences("goal", Context.MODE_MULTI_PROCESS);

    }


    @Override
    public myViewHolderSummary onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.summary_layout, parent, false);
        myViewHolderSummary holder = new myViewHolderSummary(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final myViewHolderSummary holder, int position) {
        final String category;
        int icon;
        switch(position){
            case 1:
                category = "Food";
                icon = R.drawable.f;
                break;
            case 2:
                category = "Clothing";
                icon = R.drawable.c;
                break;
            case 3:
                category = "Electronics";
                icon = R.drawable.e;
                break;
            case 4:
                category = "Other";
                icon = R.drawable.o;
                break;
            default:
                category = "Other";
                icon = R.drawable.o;
        }
        holder.title.setText(category);
        holder.icon.setImageDrawable(c.getResources().getDrawable(icon));
        //Toast.makeText(c, "Holder percentage " + Integer.toString(sharedPreferences.getInt(category, 0)), Toast.LENGTH_SHORT).show();
        holder.percentage.setText("$" + Integer.toString(sharedPreferences.getInt(category, 0)));
        /*
        SharedPreferences.OnSharedPreferenceChangeListener listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                switch(Categories.valueOf(key)){
                    case Food:
                }
                prefs.getInt(key, 0);
            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
        */
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class myViewHolderSummary extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icon;
        TextView percentage;

        public myViewHolderSummary(View itemView) {
            super(itemView);
            
            percentage = (TextView) itemView.findViewById(R.id.textView_percentage);
            title = (TextView) itemView.findViewById(R.id.textView_Category);
            icon = (ImageView) itemView.findViewById(R.id.summaryRV_icon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }
}