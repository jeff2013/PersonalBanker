package Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import main.example.jeff.personalbanker.R;

import java.util.List;

import Entries.Entry;
import sqlite.SqlDAO;

/**
 * Created by jeff on 2014-12-25.
 */
public class FragmentSpendings extends Fragment {

    private TableLayout tableLayout;
    private TableRow tableRow;
    private List<Entry> listOfEntries;
    private TextView totalSpendingTextView;
    private ListView spendingsListView;
    private Double totalSpendings = 0.0;
    public Button button;
    private SqlDAO sqlDAO;
    private TableRow tr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        sqlDAO = new  SqlDAO(getActivity());
        this.listOfEntries = sqlDAO.getEntries();
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = (View) inflater.inflate(R.layout.fragment_spendings, container, false);
        tableLayout = (TableLayout) view.findViewById(R.id.table_layout);
        tableRow = (TableRow) view.findViewById(R.id.tableRow1);
        button = (Button) view.findViewById(R.id.clear);
        totalSpendingTextView = (TextView) view.findViewById(R.id.totalSpendings);
        TextView tv = new TextView(getActivity());
        tv.setText("test");
        tableRow.addView(tv);
        onClick();

        createTables();
        return view;
    }
    //gets called before the fragment is about to be destroyed.
    @Override
    public void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
    }


    public void createTables(){
        if(listOfEntries != null){
            for(int i = 1; i<listOfEntries.size(); i++){
                final String tvTitleName = listOfEntries.get(i).getName();


                LinearLayout LL = new LinearLayout(getActivity());
                LL.setOrientation(LinearLayout.VERTICAL);
                TableRow.LayoutParams LLParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                LLParams.setLayoutDirection(LinearLayout.VERTICAL);
                LL.setLayoutParams(LLParams);

                TableRow.LayoutParams params = new TableRow.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, Gravity.END);
                // params.setLayoutDirection(0);
                params.weight = 1;




                tr = new TableRow(getActivity());
                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
                TextView tvTitle = new TextView(getActivity());
                tvTitle.setLayoutParams(new TableRow.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                tvTitle.setText(tvTitleName);
                tvTitle.setTextColor(getResources().getColor(R.color.White));
                tvTitle.setTextSize(15);


                TextView tvAmount = new TextView(getActivity());
                tvAmount.setGravity(Gravity.END);

                TextView tvDate = new TextView(getActivity());



                tvAmount.setTextColor(getResources().getColor(R.color.White));
                tvDate.setTextColor(getResources().getColor(R.color.White));
                tvAmount.setText(Double.toString(listOfEntries.get(i).getAmount()));
                tvAmount.setTextSize(25);
                //
                tvAmount.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1));
                tvDate.setText(listOfEntries.get(i).getDate().toString());
                //dateString(listOfEntries.get(i).getDate())
                tvDate.setTextSize(8);
                //tr.addView(tvTitle);
                //tr.addView(tvAmount);
                //tr.addView(tvDate);

                //LL.addView(tvDate);
                tr.addView(LL);
                LL.addView(tvTitle);
                tr.addView(tvAmount);
                LL.addView(tvDate);
                tableLayout.addView(tr);
                //listOfEntries.get(i).getAmount();



                tr.setClickable(true);

            }

            totalSpendingTextView.setText(Double.toString(totalSpendings()));
        }


    }
    public void onClick(){
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                sqlDAO.removeAllEntries();
                Toast.makeText(getActivity(), "Database deleted!", Toast.LENGTH_SHORT).show();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                FragmentSpendings refresh = new FragmentSpendings();
                transaction.replace(R.id.container, refresh);
                transaction.commit();


                // TODO Auto-generated method stub

            }
        });
    }

    public double totalSpendings(){
        for(int i = 1; i<listOfEntries.size(); i++){
            Entry e = listOfEntries.get(i);

            totalSpendings += e.getAmount();
        }
        return totalSpendings;
    }


    public List<Entry> getListOfEntries() {
        return listOfEntries;
    }

    public void setListOfEntries(List<Entry> listOfEntries) {
        this.listOfEntries = listOfEntries;
    }


}
