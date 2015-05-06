package RecyclerViewMine;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeff.personalbanker.R;

import java.util.List;

import Entries.Entry;
import sqlite.SqlDAO;

/**
 * Created by jeff on 2015-04-06.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.myViewHolder> {
    private LayoutInflater inflater;
    private List<Entry> entries;
    SqlDAO sqlDAO;
    private enum Categories{
        Other, Food, Electronics, Gasoline, Clothing, Vehicular, Housing, Tuition
    }
    private Categories categories;
    private Context c;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    public RecyclerAdapter(Context context, SqlDAO data){
        inflater = LayoutInflater.from(context);
        sqlDAO = data;
        entries = data.getEntries();
        c = context;
        sharedPreferences = c.getSharedPreferences("goal", Context.MODE_MULTI_PROCESS);
        editor = sharedPreferences.edit();

    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listview_layout, parent, false);
        return new myViewHolder(view);
    }


    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        Entry entryData = entries.get(position);
        String entryCategory = entryData.getCategory();
        holder.title.setText(entryData.getName());
        holder.amount.setText(Double.toString(entryData.getAmount()));
        holder.date.setText(entryData.getDate().toStringProper());
        Drawable icon = c.getResources().getDrawable(R.drawable.o);
        holder.icon.setImageDrawable(icon);
        String title = entryData.getName();
        //TODO

        try{
            categories = Categories.valueOf(entryCategory);
                    //Categories.valueOf(title);
            switch(categories){
                case Other:
                    icon = c.getResources().getDrawable(R.drawable.o);
                    holder.icon.setImageDrawable(icon);
                    break;
                case Food:
                    icon = c.getResources().getDrawable(R.drawable.f);
                    holder.icon.setImageDrawable(icon);
                    break;
                case Clothing:
                    icon = c.getResources().getDrawable(R.drawable.c);
                    holder.icon.setImageDrawable(icon);
                    break;
                case Electronics:
                    icon = c.getResources().getDrawable(R.drawable.e);
                    holder.icon.setImageDrawable(icon);
                    break;
                case Gasoline:
                    icon = c.getResources().getDrawable(R.drawable.g);
                    holder.icon.setImageDrawable(icon);
                    break;
                case Vehicular:
                    icon = c.getResources().getDrawable(R.drawable.v);
                    holder.icon.setImageDrawable(icon);
                    break;
                case Housing:
                    icon = c.getResources().getDrawable(R.drawable.h);
                    holder.icon.setImageDrawable(icon);
                    break;
                case Tuition:
                    icon = c.getResources().getDrawable(R.drawable.t);
                    holder.icon.setImageDrawable(icon);
                    break;
                default:
                    icon = c.getResources().getDrawable(R.drawable.o);
                    holder.icon.setImageDrawable(icon);
                    break;

            }
        }catch (IllegalArgumentException ex){
            icon = c.getResources().getDrawable(R.drawable.o);
            holder.icon.setImageDrawable(icon);
        }
        //editor.putInt(entryCategory, sharedPreferences.getInt(entryCategory, 0) + ((int) entryData.getAmount()));
        //editor.apply();

    }

    public void delete(int position){
        notifyItemRemoved(position);
        Entry entryDelete = entries.get(position);
        String entryDeleteCategory = entryDelete.getCategory();
        //Toast.makeText(c, "before delete " + entryDeleteCategory + sharedPreferences.getInt(entryDeleteCategory, 0), Toast.LENGTH_SHORT).show();
        editor.putInt(entryDeleteCategory, (int) (sharedPreferences.getInt(entryDeleteCategory, 0) - entryDelete.getAmount()));
        editor.apply();
        //Toast.makeText(c, "after delete " + entryDeleteCategory + sharedPreferences.getInt(entryDeleteCategory, 0), Toast.LENGTH_SHORT).show();
        sqlDAO.delete(entryDelete.getId());
        entries = sqlDAO.getEntries();

    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icon;
        TextView amount;
        TextView date;

        public myViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.TextView_Title);
            icon = (ImageView) itemView.findViewById(R.id.icon);
            amount = (TextView) itemView.findViewById(R.id.TextView_Amount);
            date = (TextView) itemView.findViewById(R.id.TextView_Date);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            delete(getPosition());
        }
    }
}
