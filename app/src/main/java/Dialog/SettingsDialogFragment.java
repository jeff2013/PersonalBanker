package Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import main.example.jeff.personalbanker.R;


/**
 * Created by jeff on 2015-01-12.
 */
public class SettingsDialogFragment extends DialogFragment {
    int goal;

    private Button goalBtn;
    TextView goalT;

    public SettingsDialogFragment newInstance(TextView goalT){
        SettingsDialogFragment fragment = new SettingsDialogFragment();
        this.goalT = goalT;
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = View.inflate(getActivity(), R.layout.goaldialog, null);
        goalBtn =  (Button) v.findViewById(R.id.btn_changeGoal);

        goalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "goalBtn", Toast.LENGTH_SHORT).show();
                goalT.setText("Test");
            }
        });

        TextWatcher goalWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String watcher = s.toString();
                if(watcher.length()>0){
                    goal = Integer.parseInt(watcher);
                } else {
                    goal = 0;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };


        builder.setView(v);
        builder.setMessage("Set Goal")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        final SharedPreferences sharedPreferences = getActivity().getSharedPreferences("goal", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("goalAmount", goal);
                        editor.apply();

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        return builder.create();
    }



}
