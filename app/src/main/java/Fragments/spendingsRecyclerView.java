package Fragments;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.jeff.personalbanker.R;

import RecyclerViewMine.DividerItemDecoration;
import RecyclerViewMine.RecyclerAdapter;
import sqlite.SqlDAO;


public class spendingsRecyclerView extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerViewMine.RecyclerAdapter recyclerAdapter;
    private SqlDAO sqlDAO;
    private Button addEntry;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlDAO = new SqlDAO(getActivity());
        recyclerAdapter = new RecyclerAdapter(getActivity(), sqlDAO);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences sp = getActivity().getSharedPreferences("goal", Context.MODE_MULTI_PROCESS);
        Toast.makeText(getActivity(), "RV " + sp.getInt("Food", 0), Toast.LENGTH_SHORT).show();
        View layout = inflater.inflate(R.layout.fragment_spendings_recycler_view, container, false);
        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        addEntry = (Button) layout.findViewById(R.id.addEntryButton);
        addEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment replacementFragment = new FragmentCalculate();
                android.support.v4.app.FragmentManager fm = getFragmentManager();
                android.support.v4.app.FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.container, replacementFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return layout;
    }


}
