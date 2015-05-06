package Fragments;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jeff.personalbanker.R;
import com.example.jeff.personalbanker.changeGoal;

import Adapter.SettingsAdapter;
import Dialog.SettingsDialogFragment;

/**
 * Created by jeff on 2015-01-02.
 */
public class FragmentSettings extends Fragment {

    private TextView goalT;
    private ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        //goalT = (TextView) view.findViewById(R.id.goalAmount);


        listView = (ListView) view.findViewById(R.id.settingsListView);
        SettingsAdapter settingsAdapter = new SettingsAdapter(getActivity());
        View GoalView = settingsAdapter.getView(0, null, null);
        View testView = (TextView) GoalView.findViewById(R.id.goalAmount);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        goalEdit();
                        break;
                    case 1:
                        Toast.makeText(getActivity(), "Testing 1", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getActivity(), "Testing 2", Toast.LENGTH_SHORT).show();
                        break;
                }

            }
        });
        listView.setAdapter(settingsAdapter);
        return view;
    }

    @Override
    public void onStart() {
        Log.d("Fragment Settings", "On Start Called");
        SettingsAdapter settingsAdapter = new SettingsAdapter(getActivity());
        listView.setAdapter(settingsAdapter);
        super.onStart();
    }

    @Override
    public void onResume() {
        SettingsAdapter settingsAdapter = new SettingsAdapter(getActivity());
        listView.setAdapter(settingsAdapter);
        Log.d("Fragment Settings", "OnResume called");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("Fragment Settings", "OnPauseCalled");
        super.onPause();
    }

    public void updateText(){

    }

    public void showDialog(){
        FragmentManager fm = getActivity().getFragmentManager();
        SettingsDialogFragment settingsDialogFragment = new SettingsDialogFragment().newInstance(goalT);
        settingsDialogFragment.show(fm, "Goal");
    }

    public void goalEdit(){
        Intent goal = new Intent(getActivity(), changeGoal.class);
        startActivity(goal);
    }
}
