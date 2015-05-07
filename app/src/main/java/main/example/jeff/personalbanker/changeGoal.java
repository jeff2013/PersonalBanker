package main.example.jeff.personalbanker;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class changeGoal extends ActionBarActivity {

    private Button cancel;
    private EditText newGoal;
    private float goalAmount;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String watcher = s.toString();

            if (watcher.length() > 0) {
                goalAmount = Float.parseFloat(s.toString());
            } else {
                goalAmount = 0.0f;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = View.inflate(this, R.layout.activity_change_goal, null);

        cancel = (Button) v.findViewById(R.id.btn_cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeGoal.this.finish();
            }
        });

        Button confirmButton = (Button) v.findViewById(R.id.btn_confirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preference = changeGoal.this.getSharedPreferences("goal", Context.MODE_PRIVATE);
                preference.edit().putFloat("goalAmount", goalAmount).apply();
                changeGoal.this.finish();
            }
        });

        newGoal = (EditText) v.findViewById(R.id.editText);
        newGoal.addTextChangedListener(textWatcher);
        setContentView(v);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.show();
    }

}