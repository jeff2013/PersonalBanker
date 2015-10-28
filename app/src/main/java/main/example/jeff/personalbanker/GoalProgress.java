package main.example.jeff.personalbanker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import Entries.Entry;
import RecyclerViewMine.RecyclerAdapterStats;
import sqlite.SqlDAO;


public class GoalProgress extends Fragment {

    private ProgressBar goalProgress;
    private SqlDAO sqlDAO;
    private List<Entry> listOfEntries;
    private TextView percentageTextView;
    private TextView highestSpendingTextView;
    private RecyclerAdapterStats summaryAdapter;
    private RecyclerView summaryStatsRV;
    private boolean overSpent;
    private SharedPreferences sharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlDAO = new SqlDAO(getActivity());
        listOfEntries = sqlDAO.getEntries();
        summaryAdapter = new RecyclerAdapterStats(getActivity(), sqlDAO);
        sharedPreferences = getActivity().getSharedPreferences("goal", Context.MODE_MULTI_PROCESS);
        overSpent = false;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_goal_progress, container, false);
        highestSpendingTextView = (TextView) view.findViewById(R.id.textView_highestSpending);
        summaryStatsRV = (RecyclerView) view.findViewById(R.id.statsRecyclerView);
        summaryAdapter = new RecyclerAdapterStats(getActivity(), sqlDAO);
        summaryStatsRV.setAdapter(summaryAdapter);
        summaryStatsRV.setLayoutManager(new LinearLayoutManager(getActivity()));

        percentageTextView = (TextView) view.findViewById(R.id.percentage_TextView);
        goalProgress = (ProgressBar) view.findViewById(R.id.circle_progress_bar);
        return view;
    }

    public void updateText(int percentage){
        if(overSpent){
            percentage +=25;
            percentageTextView.setText(String.valueOf(percentage) + "%");
        }else{
            percentageTextView.setText(String.valueOf(percentage) + "%");
        }

        Entry otherEntry = new Entry("Other", sharedPreferences.getInt("Other", 0));
        Entry foodEntry = new Entry("Food", sharedPreferences.getInt("Food", 0));
        Entry clothingEntry = new Entry("Clothing", sharedPreferences.getInt("Clothing", 0));
        Entry electronicsEntry = new Entry("Electronics", sharedPreferences.getInt("Electronics", 0));
        highestSpendingTextView.setText(maxFunc(otherEntry, foodEntry, clothingEntry, electronicsEntry));
    }

    public String maxFunc(Entry e1, Entry e2, Entry e3, Entry e4){
        int e1A = e1.getAmount();
        int e2A = e2.getAmount();
        int e3A = e3.getAmount();
        int e4A =  e4.getAmount();

        if(e1A>e2A && e1A>e3A && e1A >e4A) return e1.getCategory();
        if(e2A>e1A && e2A>e3A && e2A >e4A) return e2.getCategory();
        if(e3A>e1A && e3A>e2A && e3A >e4A) return e3.getCategory();
        return e4.getCategory();

    }

    public int calculateProgress(){
        int goal = sharedPreferences.getInt("goalAmount", 0);
        if(goal == 0){
            updateText(-25);
            return 0;
        } else {
            int spendings = sharedPreferences.getInt("TotalSpendings", 0)/100;
            if(spendings>goal){
                overSpent = true;
                updateText(75);
                return 75;
            } else {
                overSpent = false;
                double temp  = ((spendings)*100/goal);
                updateText((int)temp);
                return (int) Math.ceil(temp* 0.75);
            }
        }

    }

    @Override
    public void onResume() {
        goalProgress.setProgress(calculateProgress());
        super.onResume();
    }
}
