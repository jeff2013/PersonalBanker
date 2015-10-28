package Fragments;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.FrameLayout;

import main.example.jeff.personalbanker.R;

import RecyclerViewMine.DividerItemDecoration;
import RecyclerViewMine.RecyclerAdapter;
import sqlite.SqlDAO;


public class spendingsRecyclerView extends Fragment implements View.OnTouchListener {
    private RecyclerView recyclerView;
    private RecyclerViewMine.RecyclerAdapter recyclerAdapter;
    private SqlDAO sqlDAO;
    private Button addEntry;
    private FrameLayout popUpFrameContainer;
    private float mLastPosY;
    private float actionDown;
    private float actionUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sqlDAO = new SqlDAO(getActivity());
        recyclerAdapter = new RecyclerAdapter(getActivity(), sqlDAO);
        actionDown = 0.0f;
        actionUp = 0.0f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPreferences sp = getActivity().getSharedPreferences("goal", Context.MODE_MULTI_PROCESS);
        View layout = inflater.inflate(R.layout.fragment_spendings_recycler_view, container, false);
        popUpFrameContainer = (FrameLayout) layout.findViewById(R.id.FragmentPopUpContainer);
        android.support.v4.app.FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(popUpFrameContainer.getId(), new SpendingsPullUp(), "Fragment Pull Up");
        transaction.commit();

        recyclerView = (RecyclerView) layout.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                Log.d("Listener Called", "called");
                super.onChanged();
                popUpFrameContainer.refreshDrawableState();
            }
        });
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

        popUpFrameContainer.setOnTouchListener(this);
        hide_keyboard(getActivity());
        return layout;
    }

    @Override
    public void onResume() {
        hide_keyboard(getActivity());
        super.onResume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Interpolator interpolator = new OvershootInterpolator();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                mLastPosY = event.getY();
                actionDown = event.getY();
                return true;
            case MotionEvent.ACTION_UP:
                actionUp = event.getY();
               if(actionUp <0 && actionDown>actionUp) {
                    v.animate().setInterpolator(interpolator).translationY(200).setDuration(500);
                }
                if(actionUp>0 && actionDown<actionUp ) {
                    final float scale = getActivity().getResources().getDisplayMetrics().density;
                    int pixels = (int) (350 * scale + 0.5f);
                    v.animate().setInterpolator(interpolator).translationY(pixels).setDuration(500);
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                return true;
        }


        return false;
    }

    public static void hide_keyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if(view == null) {
            view = new View(activity);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
