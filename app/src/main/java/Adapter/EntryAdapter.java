package Adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import main.example.jeff.personalbanker.R;

import java.util.List;

import Entries.Entry;

/**
 * Created by jeff on 2014-12-25.
 */
public class EntryAdapter extends BaseAdapter {

    List<Entry> listOfEntries;
    LayoutInflater layoutInflater;
    Context c;
    enum Categories{
        Other, Food, Electronics, Gasoline, Clothing, Vehicular, Housing, Tuition
    }
    Categories categories;

    public EntryAdapter(Context c, List<Entry> entries){
        this.listOfEntries = entries;
        this.layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.c = c;
    }

    @Override
    public int getCount() {
        System.out.print("hi");
        return listOfEntries.size();
    }

    @Override
    public Object getItem(int position) {
        return listOfEntries.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.listview_layout, parent, false);

        TextView textViewTitle = (TextView) convertView.findViewById(R.id.TextView_Title);
        TextView textViewDate = (TextView) convertView.findViewById(R.id.TextView_Date);
        TextView textViewAmount = (TextView) convertView.findViewById(R.id.TextView_Amount);

        textViewTitle.setText(listOfEntries.get(position).getName());
        textViewDate.setText(listOfEntries.get(position).getDate().toStringProper());
        textViewAmount.setText(Double.toString(listOfEntries.get(position).getAmount()));

        ImageView imageView = (ImageView) convertView.findViewById(R.id.icon);
        Drawable icon = c.getResources().getDrawable(R.drawable.o);
        imageView.setImageDrawable(icon);
        String title = listOfEntries.get(position).getName();
        try{
            categories = Categories.valueOf(title);
            switch(categories){
                case Other:
                    icon = c.getResources().getDrawable(R.drawable.o);
                    imageView.setImageDrawable(icon);
                    break;
                case Food:
                    icon = c.getResources().getDrawable(R.drawable.f);
                    imageView.setImageDrawable(icon);
                    break;
                case Clothing:
                    icon = c.getResources().getDrawable(R.drawable.c);
                    imageView.setImageDrawable(icon);
                    break;
                case Electronics:
                    icon = c.getResources().getDrawable(R.drawable.e);
                    imageView.setImageDrawable(icon);
                    break;
                case Gasoline:
                    icon = c.getResources().getDrawable(R.drawable.g);
                    imageView.setImageDrawable(icon);
                    break;
                case Vehicular:
                    icon = c.getResources().getDrawable(R.drawable.v);
                    imageView.setImageDrawable(icon);
                    break;
                case Housing:
                    icon = c.getResources().getDrawable(R.drawable.h);
                    imageView.setImageDrawable(icon);
                    break;
                case Tuition:
                    icon = c.getResources().getDrawable(R.drawable.t);
                    imageView.setImageDrawable(icon);
                    break;
                default:
                    icon = c.getResources().getDrawable(R.drawable.o);
                    imageView.setImageDrawable(icon);
                    break;

            }
        }catch (IllegalArgumentException ex){
            icon = c.getResources().getDrawable(R.drawable.o);
            imageView.setImageDrawable(icon);
        }
        return convertView;
    }
}
