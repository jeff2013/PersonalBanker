package Adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jeff on 2014-12-31.
 */
public class CategoriesAdapter extends BaseAdapter {

    List<String> listofCategories;

    public CategoriesAdapter(){
        listofCategories = new ArrayList<String>();
        listofCategories.add("Food");
        listofCategories.add("Clothing");
        listofCategories.add("Gasoline");
        listofCategories.add("Electronics");
        listofCategories.add("Vehicular expenses");
        listofCategories.add("Housing expenses");
        listofCategories.add("Tuition");
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
        return null;
    }
}
