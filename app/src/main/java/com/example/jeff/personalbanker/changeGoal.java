package com.example.jeff.personalbanker;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import Fragments.FragmentSettings;


public class changeGoal extends ActionBarActivity {

    private Button cancel;
    private Button Confirm;
    private EditText newGoal;
    private float goalAmount;
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           String watcher = s.toString();

            if(watcher.length() >0){
                goalAmount = Float.parseFloat(s.toString());
            }else{
                goalAmount = 0.0f;
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private SharedPreferences sharedPreferences;

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
                Toast.makeText(changeGoal.this, Float.toString(preference.getFloat("goalAmount", 101.0f)), Toast.LENGTH_SHORT).show();
                changeGoal.this.finish();
            }
        });

        newGoal = (EditText) v.findViewById(R.id.editText);
        newGoal.addTextChangedListener(textWatcher);
        setContentView(v);
        ActionBar actionBar = getSupportActionBar();
        actionBar.show();
    }

}

/* DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        TypedValue tv = new TypedValue();
        this.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true);
        int actionBarHeight = getResources().getDimensionPixelSize(tv.resourceId);

        int height = displayMetrics.heightPixels ;
        int width = displayMetrics.widthPixels;
        BitmapDrawable bmap = (BitmapDrawable) this.getResources().getDrawable(R.drawable.goalbackground);
        float bmapWidth = bmap.getBitmap().getWidth();
        float bmapHeight = bmap.getBitmap().getHeight() + actionBarHeight;

        float wRatio = width / bmapWidth;
        float hRatio = height / bmapHeight;

        float ratioMultiplier = wRatio;
        if (hRatio < wRatio) {
            ratioMultiplier = hRatio;
        }

        int newBmapWidth = (int) (bmapWidth*ratioMultiplier);
        int newBmapHeight = (int) (bmapHeight*ratioMultiplier);

        ImageView backgroundImage = (ImageView) v.findViewById(R.id.goalBackground);
        backgroundImage.setLayoutParams(new RelativeLayout.LayoutParams(newBmapWidth, newBmapHeight));
*/