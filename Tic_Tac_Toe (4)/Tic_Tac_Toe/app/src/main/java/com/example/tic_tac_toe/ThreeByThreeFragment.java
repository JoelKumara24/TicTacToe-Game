package com.example.tic_tac_toe;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThreeByThreeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThreeByThreeFragment extends Fragment {

    private static Handler handler = new Handler();


    static Tracker trackedData =  new Tracker();

    private static final int SIZE = 3;

    static HashMap<ImageView, Integer> PlayerMap = new HashMap<>();
    static PlayerProfile player1 = new PlayerProfile();

    static Tracker tracker = new Tracker();

    ThreeGridGamePlay game1 = new ThreeGridGamePlay();

    static int[][] board = new int[SIZE][SIZE];

    static ImageView[][] checkIm = new ImageView[SIZE][SIZE];

    PlayerProfile curUser = new PlayerProfile();
    static List<ImageView> unclickedImageViews = new ArrayList<>();
    private int count = 0;
    boolean multiFlag;

    static TextView countView;

    static TextView timerView;

    ImageView undoFunc;
    ImageView resetFunc;
    ImageView one_of_one;
    ImageView two_of_one;
    ImageView three_of_one;
    ImageView one_of_two;
    ImageView two_of_two;
    ImageView three_of_two;
    ImageView one_of_three ;
    ImageView two_of_three;
    ImageView three_of_three;

    private MainActivityData viewModel;


    private ArrayList<ImageView> undoMoves = new ArrayList<>();




    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ThreeByThreeFragment() {
        // Required empty public constructor

       handler = new Handler();


       PlayerMap = new HashMap<>();
        player1 = new PlayerProfile();

       tracker = new Tracker();

         game1 = new ThreeGridGamePlay();

      board = new int[SIZE][SIZE];

      checkIm = new ImageView[SIZE][SIZE];


         curUser = new PlayerProfile();
        unclickedImageViews = new ArrayList<>();

        trackedData.setMovesLeftCount(5);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThreeByThreeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThreeByThreeFragment newInstance(String param1, String param2) {
        ThreeByThreeFragment fragment = new ThreeByThreeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = new ViewModelProvider(getActivity()).get(MainActivityData.class);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_three_by_three, container, false);
        // Inflate the layout for this fragmen
        //
        int screenOrientation = getResources().getConfiguration().orientation;



        multiFlag = viewModel.isMultiplayerFlag();

         undoFunc = view.findViewById(R.id.undo);

        resetFunc = view.findViewById(R.id.reset);

        countView = view.findViewById(R.id.cView);

        timerView = view.findViewById(R.id.tView);

        ConstraintSet set2 = new ConstraintSet();
        ConstraintSet set = new ConstraintSet();



        int heightInDp = 75;
        int heightInPixels = (int) (heightInDp * getResources().getDisplayMetrics().density); // Convert dp to pixels



        viewModel.getOrientationLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {


            ConstraintLayout constraintLayout = view.findViewById(R.id.consLayout1);


            @Override
            public void onChanged(Integer newOrientation) {
                // Adjust constraints based on the newOrientation (portrait or landscape)


             //   ConstraintSet set = new ConstraintSet();



                set.clone(constraintLayout);







                if (newOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                    // Adjust constraints for landscape orientation

                    set.clear(resetFunc.getId());




                    set.constrainHeight(resetFunc.getId(), heightInPixels);
                    set.constrainWidth(resetFunc.getId(), heightInPixels);


                   set.connect(resetFunc.getId(), ConstraintSet.BOTTOM, R.id.grid, ConstraintSet.BOTTOM, 0);
                    set.connect(resetFunc.getId(), ConstraintSet.END, R.id.consLayout1, ConstraintSet.END, 0);
                    set.connect(resetFunc.getId(), ConstraintSet.START, R.id.grid, ConstraintSet.END, 0);





                } else if (newOrientation == Configuration.ORIENTATION_PORTRAIT){

                    set.clear(resetFunc.getId());

                    set.constrainHeight(resetFunc.getId(), heightInPixels);
                    set.constrainWidth(resetFunc.getId(), heightInPixels);
                     set.connect(resetFunc.getId(), ConstraintSet.BOTTOM, R.id.consLayout1, ConstraintSet.BOTTOM, 0);
                     set.connect(resetFunc.getId(), ConstraintSet.TOP, R.id.grid, ConstraintSet.BOTTOM, 0);
                    set.connect(resetFunc.getId(), ConstraintSet.END, R.id.consLayout1, ConstraintSet.END, 0);

                }

                set.applyTo(constraintLayout);


            }
        });


        viewModel.getOrientationLiveData().observe(getViewLifecycleOwner(), new Observer<Integer>() {


            ConstraintLayout constraintLayout = view.findViewById(R.id.consLayout1);


            @Override
            public void onChanged(Integer newOrientation) {
                // Adjust constraints based on the newOrientation (portrait or landscape)


                //   ConstraintSet set = new ConstraintSet();




                set2.clone(constraintLayout);



                if (newOrientation == Configuration.ORIENTATION_LANDSCAPE) {

                     set2.clear(undoFunc.getId());
                    set2.constrainHeight(undoFunc.getId(), heightInPixels);
                    set2.constrainWidth(undoFunc.getId(), heightInPixels);
                    set2.connect(undoFunc.getId(), ConstraintSet.BOTTOM, R.id.consLayout1, ConstraintSet.BOTTOM, 0);
                    set2.connect(undoFunc.getId(), ConstraintSet.START, R.id.consLayout1, ConstraintSet.START, 0);
                    set2.connect(undoFunc.getId(), ConstraintSet.END, R.id.grid, ConstraintSet.START, 0);




                } else if (newOrientation == Configuration.ORIENTATION_PORTRAIT){


                    set2.clear(undoFunc.getId());

                    set2.constrainHeight(undoFunc.getId(), heightInPixels);
                    set2.constrainWidth(undoFunc.getId(), heightInPixels);
                     set2.connect(undoFunc.getId(), ConstraintSet.TOP, R.id.grid, ConstraintSet.BOTTOM, 0);
                    set2.connect(undoFunc.getId(), ConstraintSet.START, R.id.consLayout1, ConstraintSet.START, 0);



                }

                set2.applyTo(constraintLayout);

            }
        });



        one_of_one = view.findViewById(R.id.position11);
         two_of_one =view.findViewById(R.id.position12);
         three_of_one = view.findViewById(R.id.position13);
         one_of_two = view.findViewById(R.id.position21);
         two_of_two =view.findViewById(R.id.position22);
         three_of_two = view.findViewById(R.id.position23);
         one_of_three = view.findViewById(R.id.position31);
         two_of_three =view.findViewById(R.id.position32);
         three_of_three = view.findViewById(R.id.position33);

        PlayerMap.put(one_of_one, 0);
        PlayerMap.put(two_of_one, 0);
        PlayerMap.put(three_of_one, 0);
        PlayerMap.put(one_of_two, 0);
        PlayerMap.put(two_of_two, 0);
        PlayerMap.put(three_of_two, 0);
        PlayerMap.put(one_of_three, 0);
        PlayerMap.put(two_of_three, 0);
        PlayerMap.put(three_of_three, 0);

        checkIm[0][0] = one_of_one;
        checkIm[0][1] = two_of_one;
        checkIm[0][2] = three_of_one;
        checkIm[1][0] = one_of_two;
        checkIm[1][1] = two_of_two;
        checkIm[1][2] = three_of_two;
        checkIm[2][0] = one_of_three;
        checkIm[2][1] = two_of_three;
        checkIm[2][2] = three_of_three;


        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 3;
            }
        }






        one_of_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(one_of_one,board,0,0);
            }
        });

        two_of_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(two_of_one,board,0,1);
            }
        });

        three_of_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(three_of_one,board,0,2);
            }
        });

        one_of_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(one_of_two,board,1,0);
            }
        });

        two_of_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(two_of_two,board,1,1);
            }
        });

        three_of_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(three_of_two,board,1,2);
            }
        });

        one_of_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(one_of_three,board,2,0);
            }
        });

        two_of_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(two_of_three,board,2,1);
            }
        });

        three_of_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleImageViewClick(three_of_three,board,2,2);
            }
        });

        undoFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                undoMove();
            }
        });


        resetFunc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });



        return view;
    }

    private void undoMove()
    {
        trackedData.getPrevBoard();
        trackedData.undoMove();
        countView.setText("Moves left\n" + String.valueOf(trackedData.getMovesLeftCount()));

        int lastIndex = undoMoves.size() - 1;
        if (lastIndex >= 0) {

            undoMoves.get(lastIndex).setImageResource(R.drawable.white);
            undoMoves.get(lastIndex-1).setImageResource(R.drawable.white);
            PlayerMap.put(undoMoves.get(lastIndex-1),0);
            PlayerMap.put(undoMoves.get(lastIndex),0);

            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {

                    if(checkIm[i][j] == undoMoves.get(lastIndex))
                    {
                           board[i][j] = 3;
                    } else if (checkIm[i][j] == undoMoves.get(lastIndex-1))
                    {
                        board[i][j] = 3;
                    }

                }
            }


            undoMoves.remove(lastIndex);
            undoMoves.remove(lastIndex-1);





        }

    }

    public void handleImageViewClick(ImageView imageView,int[][] board,int row,int col) {


        trackedData.move();
        countView.setText("Moves left\n" + String.valueOf(trackedData.getMovesLeftCount()));
        undoMoves.add(imageView);

        final Context context = getContext();

        trackedData.startPlayerTurnTimer(30000, new Tracker.OnTimerTickListener() {
            @Override
            public void onTick(long millisUntilFinished) {
                // Update the timerView with the remaining time
                long secondsRemaining = millisUntilFinished/1000;
                timerView.setText("Time left\n" + secondsRemaining +"s");
            }

            @Override
            public void onFinish() {
                // Handle timer expiration (player's turn is over)
                timerView.setText("Time's up!");
                Notification.showTimeUp(getContext(),0 );

                // You can take appropriate action here, like ending the player's turn
            }
        });
        if(multiFlag)
        {   countView.setText("");
            undoMoves.add(imageView);
            if (PlayerMap.get(imageView) == 0) {
                if (player1.getPlayerRole() == 0) {
                    imageView.setImageResource(R.drawable.cross);
                    player1.setPlayerRole(1);
                    for (int i = 0; i < SIZE; i++) {
                        for (int j = 0; j < SIZE; j++) {
                            board[row][col] = 1;
                        }
                    }
                } else if (player1.getPlayerRole() == 1) {
                    imageView.setImageResource(R.drawable.circle);
                    player1.setPlayerRole(0);
                    for (int i = 0; i < SIZE; i++) {
                        for (int j = 0; j < SIZE; j++) {
                            board[row][col] = 0;
                        }
                    }
                }
                PlayerMap.put(imageView, 1);
                if (ThreeGridGamePlay.checkWin(board, 1)) {

                    Notification.showWin1(getContext(),0);
                    trackedData.cancelPlayerTimer();
                    timerView.setText("");
                }
                else if (ThreeGridGamePlay.checkWin(board, 0)) {

                    Notification.showWin2(getContext(),0);
                    trackedData.cancelPlayerTimer();
                    timerView.setText("");
                }
                else if(ThreeGridGamePlay.checkDraw(board) ) {
                    Notification.showDraw(getContext(),0 );
                    trackedData.cancelPlayerTimer();
                    timerView.setText("");
                }
            }
        }
        else
        {

            if (PlayerMap.get(imageView) == 0)
            {
                undoMoves.add(imageView);
                imageView.setImageResource(R.drawable.cross);

                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        board[row][col] = 1;

                    }
                }
                PlayerMap.put(imageView, 1);
                if (ThreeGridGamePlay.checkWin(board, 1)) {

                    Notification.showWin(getContext(),0);
                    trackedData.cancelPlayerTimer();
                    timerView.setText("");
                }else if (ThreeGridGamePlay.checkDraw(board) ) {
                    Notification.showDraw(getContext() ,0);
                    trackedData.cancelPlayerTimer();
                    timerView.setText("");
                }else
                {
                    unclickedImageViews.clear();
                    for (Map.Entry<ImageView, Integer> entry : PlayerMap.entrySet()) {
                        ImageView imageView1 = entry.getKey();
                        int value = entry.getValue();

                        if (value == 0) {
                            unclickedImageViews.add(imageView1);
                        }
                    }
                    if (!unclickedImageViews.isEmpty()) {



                        tracker.startPlayerTurnTimer(1000, new Tracker.OnTimerTickListener() {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                // Update the timerView with the remaining time
                                long secondsRemaining = millisUntilFinished / 1000;
                            }

                            @Override
                            public void onFinish() {
                                // Handle timer expiration (player's turn is over)
                                //timerView.setText("Time's up!");
                                // You can take appropriate action here, like ending the player's turn
                                Random random = new Random();
                                int randomIndex = random.nextInt(unclickedImageViews.size());

                                // Get the random unclicked ImageView
                                ImageView randomUnclickedImageView = unclickedImageViews.get(randomIndex);
                                for (int i = 0; i < SIZE; i++) {
                                    for (int j = 0; j < SIZE; j++) {
                                        if (checkIm[i][j] == randomUnclickedImageView) {
                                            board[i][j] = 0;
                                        }
                                    }
                                }

                                randomUnclickedImageView.setImageResource(R.drawable.circle);
                                undoMoves.add(randomUnclickedImageView);

                                PlayerMap.put(randomUnclickedImageView, 1);

                                if (ThreeGridGamePlay.checkWin(board, 0)) {

                                    Notification.showLoss(getContext(), 0);
                                    trackedData.cancelPlayerTimer();
                                    timerView.setText("");
                                } else if (ThreeGridGamePlay.checkDraw(board)) {
                                    Notification.showDraw(getContext(), 0);
                                    trackedData.cancelPlayerTimer();
                                    timerView.setText("");
                                }
                            }
                        });


                    }
                }

            }
        }


    }

    static void resetGameWithDelay() {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Clear the game board, reset player roles, and update image views
                resetGame();
            }
        }, 3000); // 3000 milliseconds = 3 seconds
        trackedData.setMovesLeftCount(5);
        countView.setText("Moves left\n" + String.valueOf(trackedData.getMovesLeftCount()));


    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the note and title data       // Save the game board and other data to the ViewModel
             viewModel.setMultiplayerFlag(multiFlag);

    }


    @SuppressLint("ResourceType")
    private static void resetGame() {
        // Clear the game board
        trackedData.cancelPlayerTimer();
        timerView.setText("");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                board[i][j] = 3;
            }
        }

        // Reset the player's roles and image views
        player1.setPlayerRole(0);
        for (ImageView imageView : PlayerMap.keySet()) {
            imageView.setImageResource(R.drawable.white);
            PlayerMap.put(imageView, 0);
        }

        trackedData.setMovesLeftCount(5);
        countView.setText("Moves left\n" + String.valueOf(trackedData.getMovesLeftCount()));


    }



}
