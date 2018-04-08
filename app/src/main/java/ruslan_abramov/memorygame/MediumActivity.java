package ruslan_abramov.memorygame;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MediumActivity extends AppCompatActivity {

    private Intent passedParam;
    private String userName;
    private int userAge;
    private TextView timeLeft;
    private CountDownTimer timer; //creating a countDownTimer
    private long milliSeconds = 1000;
    private int timeFactor = 45;

    private int pressedCardID = 0;
    private int myCardsId = 0;
    private int cardsLeft = 16;
    private boolean isPressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medium);
        getUserInfo();
        TextView userInfoTV = findViewById(R.id.mUserInfoTextView);
        userInfoTV.setText(userName);
        timeLeft = findViewById(R.id.mTimerTextView);
        timer = new CountDownTimer(milliSeconds * timeFactor, milliSeconds){

            @Override
            public void onTick(long l) {
                String outMsg = getString(R.string.secondsLeft1)
                        +" " +l/ milliSeconds +" " + getString(R.string.secondsLeft2);
                timeLeft.setText(outMsg);
                if(l/ milliSeconds <= 10)
                    timeLeft.setTextColor(Color.RED);
                didIWon();
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), getString(R.string.youLose), Toast.LENGTH_SHORT).show();
                Intent goBack = new Intent(MediumActivity.this , SelectDifficulty.class);

                goBack.putExtra("userName" , userName);
                goBack.putExtra("userAge" , userAge);
                startActivity(goBack);
            }
        };
        timer.start();
        play();

    }

    protected void getUserInfo(){
        passedParam = getIntent();
        userName = passedParam.getStringExtra("userName");
        userAge = passedParam.getIntExtra("userAge", 0);
    }

    protected void didIWon()
    {
        if(cardsLeft == 0)
        {
            timer.cancel();
            Toast.makeText(getApplicationContext(), getString(R.string.youWon), Toast.LENGTH_SHORT).show();
        }
    }

    ImageButton image1;
    ImageButton image2;
    ImageButton image3;
    ImageButton image4;
    ImageButton image5;
    ImageButton image6;
    ImageButton image7;
    ImageButton image8;
    ImageButton image9;
    ImageButton image10;
    ImageButton image11;
    ImageButton image12;
    ImageButton image13;
    ImageButton image14;
    ImageButton image15;
    ImageButton image16;

    protected void play(){

        image1 = findViewById(R.id.mImageButton1);
        image2 = findViewById(R.id.mImageButton2);
        image3 = findViewById(R.id.mImageButton3);
        image4 = findViewById(R.id.mImageButton4);
        image5 = findViewById(R.id.mImageButton5);
        image6 = findViewById(R.id.mImageButton6);
        image7 = findViewById(R.id.mImageButton7);
        image8 = findViewById(R.id.mImageButton8);
        image9 = findViewById(R.id.mImageButton9);
        image10 = findViewById(R.id.mImageButton10);
        image11 = findViewById(R.id.mImageButton11);
        image12 = findViewById(R.id.mImageButton12);
        image13 = findViewById(R.id.mImageButton13);
        image14 = findViewById(R.id.mImageButton14);
        image15 = findViewById(R.id.mImageButton15);
        image16 = findViewById(R.id.mImageButton16);

        image1.setOnClickListener(imageListener);
        image2.setOnClickListener(imageListener);
        image3.setOnClickListener(imageListener);
        image4.setOnClickListener(imageListener);
        image5.setOnClickListener(imageListener);
        image6.setOnClickListener(imageListener);
        image7.setOnClickListener(imageListener);
        image8.setOnClickListener(imageListener);
        image9.setOnClickListener(imageListener);
        image10.setOnClickListener(imageListener);
        image11.setOnClickListener(imageListener);
        image12.setOnClickListener(imageListener);
        image13.setOnClickListener(imageListener);
        image14.setOnClickListener(imageListener);
        image15.setOnClickListener(imageListener);
        image16.setOnClickListener(imageListener);

    }
    private final Handler handler = new Handler();
    private View.OnClickListener imageListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            myCardsId = view.getId();
            ImageButton ib = findViewById(myCardsId);
            switch (myCardsId){
                case R.id.mImageButton1:
                case R.id.mImageButton4:
                    ib.setImageResource(R.drawable.nin);
                    break;
                case R.id.mImageButton2:
                case R.id.mImageButton11:
                    ib.setImageResource(R.drawable.bear);
                    break;
                case R.id.mImageButton3:
                case R.id.mImageButton7:
                    ib.setImageResource(R.drawable.dolphin);
                    break;
                case R.id.mImageButton5:
                case R.id.mImageButton16:
                    ib.setImageResource(R.drawable.dude);
                    break;
                case R.id.mImageButton6:
                case R.id.mImageButton13:
                    ib.setImageResource(R.drawable.giraffe);
                    break;
                case R.id.mImageButton8:
                case R.id.mImageButton9:
                    ib.setImageResource(R.drawable.dog);
                    break;
                case R.id.mImageButton10:
                case R.id.mImageButton15:
                    ib.setImageResource(R.drawable.oink);
                    break;
                default:
                    ib.setImageResource(R.drawable.ninja);
                    break;
            }
            if(!isPressed) {
                pressedCardID = ib.getId();
                isPressed = true;
            }
            else
            {
                ImageButton pressedIb = findViewById(pressedCardID);
                if(pressedIb.getDrawable().getConstantState().equals(ib.getDrawable().getConstantState()))
                {
                    cardsLeft -= 2;
                    pressedIb.setClickable(false);
                    ib.setClickable(false);
                }
                else{

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            // Do something after 2s = 2000ms
                            ImageButton firstIB = findViewById(pressedCardID);
                            firstIB.setImageResource(R.mipmap.ic_launcher);
                            ImageButton secondIB = findViewById(myCardsId);
                            secondIB.setImageResource(R.mipmap.ic_launcher);
                        }
                    }, 2000);
                }
                isPressed = false;
            }

        }
    };
}