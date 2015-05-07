package Fragments;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import main.example.jeff.personalbanker.MainActivity;
import main.example.jeff.personalbanker.R;

import java.util.Calendar;

import Entries.DateC;
import sqlite.SqlDAO;

/**
 * Created by jeff on 2014-12-25.
 */
public class FragmentCalculate extends Fragment implements View.OnClickListener, TextView.OnEditorActionListener {
    private Button b;
    private EditText titleEditer;
    private EditText amountEditer;
    private EditText dateEditer;
    private String titleText = "";
    private Spinner categoriesSpinner;
    private double amount = 0.0;
    private Calendar cal = Calendar.getInstance();
    private int day = cal.get(Calendar.DATE);
    private int month = cal.get(Calendar.MONTH) + 1;
    private int year = cal.get(Calendar.YEAR);
    private DateC d;
    private SqlDAO sqlDAO;
    private String date = "";
    private String category;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private NotificationCompat.Builder notif;

    private TextWatcher dateTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            String working = s.toString();
            if (working.length() == 2 && before == 0){
                if(Integer.parseInt(working) < 1 || Integer.parseInt(working) > cal.getActualMaximum(Calendar.DAY_OF_MONTH)){
                    dateEditer.setError("Please enter a valid date in the format DD-MM-YYYY");
                }else{
                    day = Integer.parseInt(s.subSequence(0, 2).toString());
                    working += "/";
                    dateEditer.setText(working);
                    dateEditer.setSelection(working.length());
                }
            } else if(working.length() == 5 && before == 0){
                String monthWorking = working.substring(3);
                if(Integer.parseInt(monthWorking) < 1 || Integer.parseInt(monthWorking) > 12){
                    dateEditer.setError("Please enter a valid month in the format DD-MM-YYYY");
                } else {
                    month = Integer.parseInt(s.subSequence(3,5).toString());
                    working += "/";
                    dateEditer.setText(working);
                    dateEditer.setSelection(working.length());
                }
            } else if(working.length() == 10 && before == 0){
                year = Integer.parseInt(s.subSequence(6, 10).toString());
                String yearWorking = working.substring(6);
                if(Integer.parseInt(yearWorking) <1995 || Integer.parseInt(yearWorking) > cal.get(Calendar.YEAR)){
                    dateEditer.setError("Please enter a valid year in the format DD-MM-YYYY");
                }
            }
            date = working;

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
        }
    };

    private TextWatcher amountTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub
            String watcher = s.toString();
            if(watcher.length() >0){
                amount = Double.parseDouble(watcher);
            }else{
                amount = 0.0;
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
           // amount = Double.parseDouble(s.toString());
        }
    };

    private TextWatcher titleTextWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // TODO Auto-generated method stub

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            // TODO Auto-generated method stub

        }

        @Override
        public void afterTextChanged(Editable s) {
            // TODO Auto-generated method stub
            titleText = s.toString();
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        sharedPreferences = getActivity().getSharedPreferences("goal", Context.MODE_MULTI_PROCESS);
        editor = sharedPreferences.edit();
        sqlDAO = new SqlDAO(getActivity());
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calculate_activity, container, false);
        b = (Button) view.findViewById(R.id.add_entry);
        titleEditer = (EditText) view.findViewById(R.id.title_entry);
        titleEditer.setOnEditorActionListener(this);
        titleEditer.addTextChangedListener(titleTextWatcher);

        final ArrayAdapter<CharSequence> categoriesAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.categories_array, android.R.layout.simple_spinner_dropdown_item);
        categoriesSpinner = (Spinner) view.findViewById(R.id.categories_spinner);
        categoriesSpinner.setAdapter(categoriesAdapter);
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                titleText = categoriesAdapter.getItem(position).toString();
                category = categoriesAdapter.getItem(position).toString();
                if(titleEditer.getText().toString().length() == 0){
                    titleEditer.setText(titleText);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                category = "Other";
            }
        });

        amountEditer = (EditText) view.findViewById(R.id.amount_entry);
        amountEditer.addTextChangedListener(amountTextWatcher);

        dateEditer = (EditText) view.findViewById(R.id.date_entry);
        dateEditer.addTextChangedListener(dateTextWatcher);

        b.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        d = new DateC(day,month,year);

        sqlDAO.createSpending(titleText, amount, category, day, month, year);
        float prevVal = sharedPreferences.getFloat(category, 0.0f);
        editor.putFloat(category, prevVal + (float)amount).commit();
        editor.putInt("TotalSpendings", sharedPreferences.getInt("TotalSpendings", 0) + (int) amount).apply();
        float goal = sharedPreferences.getFloat("goalAmount", 0.0f);
        if(sharedPreferences.getInt("TotalSpendings", 0) >= goal){
            //buildNotification((int) goal);
            buildNotificationWear();
        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment newfragment = new spendingsRecyclerView();
        transaction.replace(R.id.container, newfragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void buildNotification(int goal){
        Notification.Builder notification = new Notification.Builder(getActivity())
                .setSmallIcon(R.drawable.ic_launcher3)
                .setContentTitle("Spending Goal Reached")
                .setContentText("You have reached your spending goal of " + goal);
        Intent resultIntent = new Intent(getActivity(), MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        notification.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, notification.build());

    }

    public void buildNotificationWear(){
        Bitmap notificationLargeIconBitmap = BitmapFactory.decodeResource(
                getActivity().getResources(),
                R.drawable.exclamationmark);

        notif = new NotificationCompat.Builder(getActivity().getApplication())
                .setSmallIcon(R.drawable.ic_launcher3)
                .setLargeIcon(notificationLargeIconBitmap)
                .setContentTitle("Spending Goal Reached")
                .setContentText("You have reached your spending goal!")
                .extend(new NotificationCompat.WearableExtender().setHintShowBackgroundOnly(true));

        Intent resultIntent = new Intent(getActivity(), MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getActivity());
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        notif.setContentIntent(resultPendingIntent);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity().getApplication());
        int notificationId = 1;
        notificationManager.notify(notificationId, notif.build());
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event){
        if (event != null&& (event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
            InputMethodManager in = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            in.hideSoftInputFromWindow(titleEditer.getApplicationWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }
}
