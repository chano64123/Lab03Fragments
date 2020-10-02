package com.example.lab03fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VoteFragment extends Fragment {

    private static final int MOVIE1 = 0;
    private static final int MOVIE2 = 1;
    private static final int NONE = 2;
    public int mRadioButtonChoice = NONE;
    private static final String CHOICE = "choice";

    OnFragmentInteractionListener mListener;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VoteFragment() {
        // Required empty public constructor
    }

    interface OnFragmentInteractionListener {
        void onRadioButtonChoice(int choice);
        void onButtonClick(int choice);
    }


    // TODO: Rename and change types and number of parameters
    public static VoteFragment newInstance(int choice) {
        VoteFragment fragment = new VoteFragment();
        Bundle args = new Bundle();
        args.putInt(CHOICE, choice);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener)context;
            } else {
                throw new ClassCastException(context.toString() + getResources().getString(R.string.exception_message));
            }
        } catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_vote, container, false);
        final RadioGroup radioGroup = rootView.findViewById(R.id.radio_group);
        final Button btnVoto = rootView.findViewById(R.id.button_vote);

        if (getArguments().containsKey(CHOICE)){
            mRadioButtonChoice = getArguments().getInt(CHOICE);
            if (mRadioButtonChoice!=NONE){
                radioGroup.check(radioGroup.getChildAt(mRadioButtonChoice).getId());
            }
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                View radioButton = radioGroup.findViewById(i);
                int index = radioGroup.indexOfChild(radioButton);
                switch(index){
                    case MOVIE1:
                        mListener.onRadioButtonChoice(MOVIE1);
                        mRadioButtonChoice = MOVIE1;
                        break;
                    case MOVIE2:
                        mListener.onRadioButtonChoice(MOVIE2);
                        mRadioButtonChoice = MOVIE2;
                        break;
                    default:
                        mRadioButtonChoice = NONE;
                        mListener.onRadioButtonChoice(NONE);
                        break;
                }
            }
        });

        btnVoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onButtonClick(mRadioButtonChoice);
            }
        });

        return rootView;

    }



}