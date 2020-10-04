package com.example.lab03fragment;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import java.util.IllegalFormatCodePointException;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity implements VoteFragment.OnFragmentInteractionListener {

    private Button btn;
    private boolean isFragmentDisplayed = false;
    private boolean isAlertDisplayed = false;
    private int numberVotesMovie1 = 0;
    private int numberVotesMovie2 = 0;
    private String messageAlert = "";
    private String titleAlert = "";
    private String resultVote = "";
    static final String FRAGMENT_STATE = "state_of_Fragment";
    static final String ALERT_STATE = "state_of_Alert";
    static final String VOTES_FOR_MOVIE1 = "votes_for_movie1";
    static final String VOTES_FOR_MOVIE2 = "votes_for_movie2";
    static final String MESSAGE_ALERT = "message_alert";
    static final String TITLE_ALERT = "title_alert";
    static final String RESULT_VOTE = "result_vote";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            isFragmentDisplayed = savedInstanceState.getBoolean(FRAGMENT_STATE);
            isAlertDisplayed = savedInstanceState.getBoolean(ALERT_STATE);
            numberVotesMovie1 = savedInstanceState.getInt(VOTES_FOR_MOVIE1);
            numberVotesMovie2 = savedInstanceState.getInt(VOTES_FOR_MOVIE2);
            messageAlert = savedInstanceState.getString(MESSAGE_ALERT);
            titleAlert = savedInstanceState.getString(TITLE_ALERT);
            resultVote = savedInstanceState.getString(RESULT_VOTE);
            if (isFragmentDisplayed){
                btn = findViewById(R.id.btnVote);
                btn.setText(R.string.cancel_vote);
            }
            if (isAlertDisplayed){
                showMessage(messageAlert,titleAlert);
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
        VoteFragment simpleFragment = VoteFragment.newInstance();
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
        savedInstanceState.putBoolean(ALERT_STATE,isAlertDisplayed);
        savedInstanceState.putInt(VOTES_FOR_MOVIE1,numberVotesMovie1);
        savedInstanceState.putInt(VOTES_FOR_MOVIE2,numberVotesMovie2);
        savedInstanceState.putString(MESSAGE_ALERT,messageAlert);
        savedInstanceState.putString(TITLE_ALERT,titleAlert);
        savedInstanceState.putString(RESULT_VOTE,resultVote);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onButtonClick(int choice) {
        String result ="";
        switch (choice){
            case 0:
                numberVotesMovie1++;
                result = (numberVotesMovie1==numberVotesMovie2)?getResources().getString(R.string.equals):(numberVotesMovie2>numberVotesMovie1)?getResources().getString(R.string.winner) + getResources().getString(R.string.second_movie) + getResources().getString(R.string.with) + numberVotesMovie2 + getResources().getString(R.string.votes):getResources().getString(R.string.winner) + getResources().getString(R.string.first_movie) + getResources().getString(R.string.with) + numberVotesMovie1 + getResources().getString(R.string.votes);
                messageAlert = getResources().getString(R.string.you_select)+ getResources().getString(R.string.first_movie) + "\n\n\n" +
                          getResources().getString(R.string.first_movie) + getResources().getString(R.string.two_points) + numberVotesMovie1 + "\n" +
                          getResources().getString(R.string.second_movie) + getResources().getString(R.string.two_points) + numberVotesMovie2 + "\n\n" +
                          result;
                titleAlert = getResources().getString(R.string.result_title);
                closeFragment();
                break;
            case 1:
                numberVotesMovie2++;
                result = (numberVotesMovie1==numberVotesMovie2)?getResources().getString(R.string.equals):(numberVotesMovie2>numberVotesMovie1)?getResources().getString(R.string.winner) + getResources().getString(R.string.second_movie) + getResources().getString(R.string.with) + numberVotesMovie2 + getResources().getString(R.string.votes):getResources().getString(R.string.winner) + getResources().getString(R.string.first_movie) + getResources().getString(R.string.with) + numberVotesMovie1 + getResources().getString(R.string.votes);
                messageAlert = getResources().getString(R.string.you_select)+ getResources().getString(R.string.second_movie) + "\n\n\n" +
                        getResources().getString(R.string.first_movie) + getResources().getString(R.string.two_points) + numberVotesMovie1 + "\n" +
                        getResources().getString(R.string.second_movie) + getResources().getString(R.string.two_points) + numberVotesMovie2 + "\n\n" +
                        result;
                titleAlert = getResources().getString(R.string.result_title);
                closeFragment();
                break;
            default:
                messageAlert = getResources().getString(R.string.alert_message);
                titleAlert = getResources().getString(R.string.alert_title);
                break;
        }
        showMessage(messageAlert,titleAlert);
        isAlertDisplayed = true;
    }

    private void showMessage(String message, String title) {
        // cuztomizando al mensaje
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.setCancelable(false);

        // agregando un boton al mensaje
        alert.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                isAlertDisplayed=false;
            }
        });

        // create and show the alert dialog
        AlertDialog dialog = alert.create();
        dialog.show();
    }
}