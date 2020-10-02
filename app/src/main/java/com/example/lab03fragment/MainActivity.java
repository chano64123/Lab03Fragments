package com.example.lab03fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.IllegalFormatCodePointException;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements VoteFragment.OnFragmentInteractionListener {

    private Button btn;
    private boolean isFragmentDisplayed = false;
    private int numberVotesMovie1 = 0;
    private int numberVotesMovie2 = 0;
    static final String FRAGMENT_STATE = "state_of_Fragment";
    static final String VOTES_FOR_MOVIE1 = "votes_for_movie1";
    static final String VOTES_FOR_MOVIE2 = "votes_for_movie2";
    private int mRadioButtonChoice = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(FRAGMENT_STATE);
            numberVotesMovie1 = savedInstanceState.getInt(VOTES_FOR_MOVIE1);
            numberVotesMovie2 = savedInstanceState.getInt(VOTES_FOR_MOVIE2);
            if (isFragmentDisplayed){
                btn = findViewById(R.id.btnVote);
                btn.setText(R.string.cancel_vote);
            }
        }

        btn = findViewById(R.id.btnVote);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isFragmentDisplayed){
                    closeFragment();
                }else{
                    openFragment();
                }
            }
        });
    }

    private void openFragment() {
        VoteFragment simpleFragment = VoteFragment.newInstance(mRadioButtonChoice);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container,simpleFragment).addToBackStack(null).commit();
        btn.setText(R.string.cancel_vote);
        isFragmentDisplayed = true;
    }

    private void closeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        VoteFragment simpleFragment = (VoteFragment)fragmentManager.findFragmentById(R.id.fragment_container);
        if (simpleFragment != null){
            fragmentManager.beginTransaction().remove(simpleFragment).commit();
            btn.setText(R.string.go_vote);
            isFragmentDisplayed = false;
        }
    }

    //para guardar los datos al momento de sufrir una modificacion de configuracion
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(FRAGMENT_STATE,isFragmentDisplayed);
        savedInstanceState.putInt(VOTES_FOR_MOVIE1,numberVotesMovie1);
        savedInstanceState.putInt(VOTES_FOR_MOVIE2,numberVotesMovie2);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRadioButtonChoice(int choice) {
        mRadioButtonChoice = choice;
    }



    @Override
    public void onButtonClick(int choice) {
        String message = "";
        String title = "";
        switch (choice){
            case 0:
                numberVotesMovie1++;
                message = getResources().getString(R.string.you_select)+ getResources().getString(R.string.first_movie) + "\n\n\n" +
                          getResources().getString(R.string.first_movie) + getResources().getString(R.string.two_points) + numberVotesMovie1 + "\n\n" +
                          getResources().getString(R.string.second_movie) + getResources().getString(R.string.two_points) + numberVotesMovie2;
                title = getResources().getString(R.string.result_title);
                break;
            case 1:
                numberVotesMovie2++;
                message = getResources().getString(R.string.you_select)+ getResources().getString(R.string.second_movie) + "\n\n\n" +
                        getResources().getString(R.string.first_movie) + getResources().getString(R.string.two_points) + numberVotesMovie1 + "\n\n" +
                        getResources().getString(R.string.second_movie) + getResources().getString(R.string.two_points) + numberVotesMovie2;
                title = getResources().getString(R.string.result_title);
                break;
            default:
                message = getResources().getString(R.string.alert_message);
                title = getResources().getString(R.string.alert_title);
                break;
        }
        showMessage(message,title);
    }

    private void showMessage(String message, String title) {
        // cuztomizando al mensaje
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setCancelable(false);

        // agregando un boton al mensaje
        alert.setPositiveButton(R.string.ok, null);

        // create and show the alert dialog
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}