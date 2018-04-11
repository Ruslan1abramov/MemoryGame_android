/**
* created by
* ruslan abramov 306847393
*/
package ruslan_abramov.memorygame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Vibrator;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class GameActivity extends AppCompatActivity implements GameValues {

    private Intent passedParam;
    private String userName;
    private int userAge;
    private  TextView timeLeft;
    private ConstraintLayout board;
    private ProgressBar pb;
    private long milliSeconds = MILLI_SECONDS;

    private Vibrator v;
    SoundPool sp;
    int[] sounds = new int[3];

    /* Game related */
    private  ViewFlipper vf; //used to pick which card board to show
    //list of active buttons
    private ArrayList<ImageButton> buttons = new  ArrayList();
    //map of each button to his image
    private HashMap<ImageButton ,Integer> buttonToImage = new HashMap();
    //list that holds the available cards
    private ArrayList<Integer> cardsDrawableID= new ArrayList();
    //list of cards we need to display
    private ArrayList<Integer> cardsToDisplay = new  ArrayList();

    private CountDownTimer timer; //creating a countDownTimer
    private final Handler handler = new Handler();//handler for making a delay

    private boolean isPressed = false;
    private boolean isDelayed = false;
    private int pressedCardID = 0;
    /*difficulty related*/
    private int timeFactor;
    private int difficulty;
    private int cardsLeft;
    /*action Listener related*/
    private int myCardsId = 0;

    private boolean userAborted = false;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //stopping the timer when going back from the game
        timer.cancel();
        userAborted = true;
        sp.release();
        this.finish();
    }
    @Override
    public void onPause() {
        super.onPause();
        //stopping the timer when going back from the game
        timer.cancel();
        userAborted = true;
        sp.release();
        this.finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        addSounds();
        addCards();
        getUserInfo();
        getGameInfo();
        displayUserName();
        initTimer();
        play();

    }
    protected void displayUserName(){
        TextView userInfoTV = findViewById(R.id.userNameTextView);
        userInfoTV.setText(getString(R.string.helloTO) + " " +userName);
    }

    protected void initTimer(){
        timeLeft = findViewById(R.id.timeLeftEasy);
        timer = new CountDownTimer(MILLI_SECONDS * timeFactor, MILLI_SECONDS / 4) {
            Intent goBack ;
            @Override
            public void onTick(long l) {
                String outMsg = getString(R.string.secondsLeft1)
                        + " " + l / milliSeconds + " " + getString(R.string.secondsLeft2);
                timeLeft.setText(outMsg);
                if (l / milliSeconds <= 10)
                    timeLeft.setTextColor(Color.RED);
                didIWin();
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), getString(R.string.youLose), Toast.LENGTH_SHORT).show();
                goBackToSelectDifficulty();
            }

            protected void goBackToSelectDifficulty(){
                goBack = new Intent(GameActivity.this, SelectDifficulty.class);
                goBack.putExtra("userName", userName);
                goBack.putExtra("userAge", userAge);
                goBack.putExtra("winOrLose", cardsLeft == 0 ? 1 : 0);
                goBack.putExtra("difficulty", difficulty);
                sp.release();

                startActivity(goBack);
            }
            /*checking if we won the game*/
            protected void didIWin() {
                if (cardsLeft == 0) {
                    //stopping the timer
                    timer.cancel();
                    playSound(2);
                    Toast.makeText(getApplicationContext(), getString(R.string.youWon), Toast.LENGTH_LONG).show();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //checking if the user is still in this activity
                            if(userAborted != true) {
                                goBackToSelectDifficulty();
                            }
                        }
                    }, 5*MILLI_SECONDS);

                }
            }
        };
    }

    protected void addSounds(){
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sp = new SoundPool(3, AudioManager.STREAM_NOTIFICATION,0);
        sounds[0] = sp.load(getApplicationContext(), R.raw.cancel , 0);
        sounds[1] = sp.load(getApplicationContext(), R.raw.twinkle , 1);
        sounds[2] = sp.load(getApplicationContext(), R.raw.bulletin , 2);
    }

    protected void playSound(int sound) {

        sp.play(sounds[sound], 1, 1, 1, 0, 1f);
    }

    /*creating a list of all available cards*/
    protected void addCards(){
        cardsDrawableID.add(R.drawable.nin);
        cardsDrawableID.add(R.drawable.bear);
        cardsDrawableID.add(R.drawable.dolphin);
        cardsDrawableID.add(R.drawable.dude);
        cardsDrawableID.add(R.drawable.ninja);
        cardsDrawableID.add(R.drawable.giraffe);
        cardsDrawableID.add(R.drawable.dog);
        cardsDrawableID.add(R.drawable.kangaroo);
        cardsDrawableID.add(R.drawable.lion);
        cardsDrawableID.add(R.drawable.oink);
        cardsDrawableID.add(R.drawable.panda);
        cardsDrawableID.add(R.drawable.sheep);

        Collections.shuffle(cardsDrawableID);
    }
    /*creating a list of the cards picked for the specific game*/
    protected void randomCardArray() {
        for (int counter = 0; counter < cardsLeft / 2; counter++) {
            int nextID = cardsDrawableID.remove(cardsDrawableID.size() - 1);
            //each card is displayed twice
            cardsToDisplay.add(nextID);
            cardsToDisplay.add(nextID);
        }
        Collections.shuffle(cardsToDisplay);
    }

    /*gathering user info from previous activity*/
    protected void getUserInfo() {
        passedParam = getIntent();
        userName = passedParam.getStringExtra("userName");
        userAge = passedParam.getIntExtra("userAge", 0);
    }

    /*gathering game info from previous activity*/
    protected void getGameInfo() {
        passedParam = getIntent();
        difficulty = passedParam.getIntExtra("difficulty", EASY_ID);
        timeFactor = passedParam.getIntExtra("TIME_FACTOR", timeFactor);

        vf = findViewById( R.id.view_flipper );
        switch (difficulty) {
            case HARD_ID: //if user wants to play hard difficulty
                cardsLeft = HARD_CARDS;
                board = findViewById(R.id.constraintLayoutHARD);
                vf.setDisplayedChild(vf.indexOfChild(findViewById(R.id.constraintLayoutHARD)));

                break;
            case MEDIUM_ID: //if user wants to play medium difficulty
                cardsLeft = MEDIUM_CARDS;
                board = findViewById(R.id.constraintLayoutMedium);
                vf.setDisplayedChild(vf.indexOfChild(findViewById(R.id.constraintLayoutMedium)));

                break;
            default: //if user wants to play easy difficulty
                cardsLeft = EASY_CARDS;
                board = findViewById(R.id.constraintLayoutEasy);
                vf.setDisplayedChild(vf.indexOfChild(findViewById(R.id.constraintLayoutEasy)));

        }
        //picking cards for the game
        randomCardArray();


    }



    /*starting the game*/
    protected void play() {
        pb = findViewById( R.id.progressBar );
        //adding the image buttons from our board
        //in case we play hard mode the last card is not taken into an account because we have only 12 pairs
        for (int counter = 0; counter < board.getChildCount() && counter < HARD_CARDS; counter++)
            buttons.add((ImageButton) board.getChildAt(counter));
        //mapping each button to a picture
        for (ImageButton ib : buttons)
            buttonToImage.put(ib, cardsToDisplay.remove(cardsToDisplay.size() - 1));
        //adding on click listener
        for (ImageButton ib : buttons)
            ib.setOnClickListener(imageListener);

        timer.start();
    }

    /*the listener used upon clicking an image*/
    private View.OnClickListener imageListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            //if we are delayed we do nothing
            if (!isDelayed) {
                myCardsId = view.getId();
                ImageButton ib = findViewById(myCardsId);
                ib.setImageResource(buttonToImage.get(ib));
                gameLogic(ib);
            }

        }
    };

    /*this method is responsible for the game behavior*/
    public void gameLogic(ImageButton ib) {
        //if no card is flipped
        if (!isPressed) {
            //the flipped card is the card that was pressed
            pressedCardID = ib.getId();
            isPressed = true;
            //if we click the same card twice
        } else if (ib.getId() == pressedCardID) {
            //do nothing
        } else { //if a card was flipped
            ImageButton pressedIb = findViewById(pressedCardID);
            //both cards are the same
            if (pressedIb.getDrawable().getConstantState().equals(ib.getDrawable().getConstantState())) {
                cardsLeft -= 2;
                pressedIb.setClickable(false);
                ib.setClickable(false);
                playSound(1);
                v.vibrate(200);
                float startingCards = difficulty == EASY_ID ? EASY_CARDS :
                        difficulty == MEDIUM_ID ? MEDIUM_CARDS : HARD_CARDS;
                pb.setProgress((int)((1-cardsLeft/startingCards) * 100));
            } else { // both cards are different
                isDelayed = true;
                playSound(0);
                //starting 2 seconds delay
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Do something after 2s = 2000ms
                        ImageButton firstIB = findViewById(pressedCardID);
                        firstIB.setImageResource(R.mipmap.ic_launcher);
                        ImageButton secondIB = findViewById(myCardsId);
                        secondIB.setImageResource(R.mipmap.ic_launcher);
                        isDelayed = false;
                    }
                }, 2000);
            }
            isPressed = false;
        }
    }

}
