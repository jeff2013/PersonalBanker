package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import main.example.jeff.personalbanker.R;

import java.util.List;

import Adapter.EntryAdapter;
import Entries.Entry;
import sqlite.SqlDAO;

/**
 * Created by jeff on 2014-12-25.
 */
public class FragmentSpendingsListView extends Fragment {
    SqlDAO sqldao;
    ListView listview;
    LinearLayout linearLayout;
    EntryAdapter entryAdapter;
    TextView totalAmountTextView;
    TextView remainingSpendingsTextView;
    double amount;
    int j=0;
    double goal;
    double remainingGoal;
    AdapterView.OnItemClickListener itemClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        this.sqldao = new SqlDAO(getActivity());
        entryAdapter = new EntryAdapter(getActivity(), this.getDatabase());
        amount=0.0;
        itemClickListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                entryAdapter = new EntryAdapter(getActivity(), getDatabase());
                Entry e = (Entry) entryAdapter.getItem(position-1);
                Double d = e.getAmount();
                Toast.makeText(getActivity(), d.toString(), Toast.LENGTH_SHORT).show();
                Toast.makeText(getActivity(), getTotalSpendings().toString(), Toast.LENGTH_SHORT).show();
                sqldao.delete(e.getId());
                updateSpendings(getTotalSpendings());
                updateRemainingSpendings();
                updateColor();
                listview.setAdapter(new EntryAdapter(getActivity(), getDatabase()));
                Toast.makeText(getActivity(), "Spending deleted!", Toast.LENGTH_SHORT).show();
            }
        };
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = (View) inflater.inflate(R.layout.fragment_spendingsv2, container, false);
        listview = (ListView) view.findViewById(R.id.listView1);
        listview.setAdapter(entryAdapter);
        listview.setOnItemClickListener(itemClickListener);
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.listview_header, listview, false);
        listview.addHeaderView(header, null, false);

        linearLayout = (LinearLayout) view.findViewById(R.id.linearLayoutTest);
        final View totalSpendings = inflater.inflate(R.layout.spendingslistview_otalspendings, null);
        final View remainingSpendings = inflater.inflate(R.layout.spendingslistview_goal, null);

        this.remainingSpendingsTextView = (TextView) remainingSpendings.findViewById(R.id.textview_remainingGoal);
        goalInitialization(inflater);

        linearLayout.addView(totalSpendings);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(j == 2){
                    j = 0;
                }
                if (j%2==0){
                    linearLayout.removeAllViews();
                    linearLayout.addView(remainingSpendings);
                    updateRemainingSpendings();
                } else {
                    linearLayout.removeAllViews();
                    linearLayout.addView(totalSpendings);
                }
                j++;
                Toast.makeText(getActivity(), "increment number" + j, Toast.LENGTH_SHORT).show();
            }
        });

        totalAmountTextView = (TextView) linearLayout.findViewById(R.id.totalSpendings);
        totalAmountTextView.setText(getTotalSpendings().toString());



        return view;
    }

    public List<Entry> getDatabase(){
        return sqldao.getEntries();
    }

    public Double getTotalSpendings(){
        Double amount=0.0;
        for (int i = 0; i< getDatabase().size(); i++){
                amount += getDatabase().get(i).getAmount();
        }
        return amount;
    }

    public void updateSpendings(Double d){
        totalAmountTextView.setText(d.toString());
    }

    public void updateGoal(Double d){
        remainingSpendingsTextView.setText(d.toString());
        updateColor();
    }

    //updates the amount of spendings remaining
    public void updateRemainingSpendings(){
        Double goal = (this.goal - getTotalSpendings());
        remainingSpendingsTextView.setText(goal.toString());
        updateColor();
    }

    //initializes the goal textview and sets the basevalue of he goal set by the user.
    public void goalInitialization(LayoutInflater inflater){
        View v = inflater.inflate(R.layout.settingslistview_goal, null);
        //SharedPreferences preference = getActivity().getSharedPreferences("goal", Context.MODE_PRIVATE);
        //this.goal = (double) preference.getInt("goalAmount", 100);
        //remainingGoal = goal - getTotalSpendings();
        Toast.makeText(getActivity(), Double.toString(remainingGoal),Toast.LENGTH_SHORT ).show();
        //updateGoal(remainingGoal);
    }

    public void updateColor(){
        remainingGoal = goal - getTotalSpendings();
        if(remainingGoal < 0){
            remainingSpendingsTextView.setTextColor(getResources().getColor(R.color.red));
        } else {
            remainingSpendingsTextView.setTextColor(getResources().getColor(R.color.Black));
        }
    }
}
