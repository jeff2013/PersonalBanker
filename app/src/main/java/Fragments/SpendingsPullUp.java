package Fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.NumberFormat;

import Entries.Entry;
import main.example.jeff.personalbanker.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SpendingsPullUp#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SpendingsPullUp extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private TextView progressTextView;
    private TextView spentTextView;
    private TextView goalTextView;
    private SharedPreferences sharedPreferences;
    private boolean overSpent;
    private ProgressBar progressBar;
    private RelativeLayout peekView;
    private Typeface tp;
    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SpendingsPullUp.
     */
    // TODO: Rename and change types and number of parameters
    public static SpendingsPullUp newInstance(String param1, String param2) {
        SpendingsPullUp fragment = new SpendingsPullUp();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SpendingsPullUp() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sharedPreferences = getActivity().getSharedPreferences("goal", Context.MODE_MULTI_PROCESS);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                updateColor();
                Log.d("crea Listener initiated", "jklh");
                spentTextView.setText(currencyString(sharedPreferences.getInt("TotalSpendings", 0))+"/"+ Integer.toString(sharedPreferences.getInt("goalAmount",0)));

            }
        };
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
        overSpent = false;
        tp = Typeface.createFromAsset(getActivity().getAssets(), "molot.otf");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spendings_pull_up, container, false);
        progressBar = (ProgressBar) view.findViewById(R.id.pullUp_progress_bar);
        progressTextView = (TextView) view.findViewById(R.id.pullUp_progress);
        peekView = (RelativeLayout) view.findViewById(R.id.peekView);
        TextView percentage =  (TextView)view.findViewById(R.id.pullup_percentage);
        spentTextView = (TextView) view.findViewById(R.id.textView_AmountSpent);
        spentTextView.setText(currencyString(sharedPreferences.getInt("TotalSpendings", 0))+"/"+ Integer.toString(sharedPreferences.getInt("goalAmount",0)));
        progressTextView.setTypeface(tp);
        percentage.setTypeface(tp);
        progressBar.setProgress(calculateProgress());
        updateColor();
        return view;
    }

    @Override
    public void onResume() {
        Log.d("OnResume", "called");
        super.onResume();
    }

    private void updateColor(){
        progressBar.setProgress(calculateProgress());
        if(overSpent){
            peekView.setBackgroundColor(getActivity().getResources().getColor(R.color.red_peek));
        }else{
            peekView.setBackgroundColor(getActivity().getResources().getColor(R.color.hangout_green));
        }
    }

    public void updateText(int percentage){
        if(overSpent){
            progressTextView.setText(String.valueOf(percentage));
        }else{
            progressTextView.setText(String.valueOf(percentage));
        }

    }

    public int calculateProgress(){
        int goal = sharedPreferences.getInt("goalAmount", 0);
        if(goal == 0){
            updateText(0);
            return 0;
        } else {
            int spendings = sharedPreferences.getInt("TotalSpendings", 0)/100;
            if(spendings>goal){
                overSpent = true;
                updateText(100);
                return 100;
            } else {
                overSpent = false;
                double temp  = ((spendings*100)/goal);
                updateText((int)temp);
                return (int) Math.ceil(temp);
            }
        }
    }

    private String currencyString(int num){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        return formatter.format((double)num/100);
    }


}
