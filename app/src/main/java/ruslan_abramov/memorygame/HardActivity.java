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

public class HardActivity extends AppCompatActivity {

    private Intent passedParam;
    private String userName;
    private int userAge;
    private TextView timeLeft;
    private CountDownTimer timer; //creating a countDownTimer
    private long milliSeconds = 1000;
    private int timeFactor = 60;

    private int pressedCardID = 0;
    private int myCardsId = 0;
    private int cardsLeft = 24;
    private boolean isPressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hard);
        getUserInfo();
        TextView userInfoTV = findViewById(R.id.hUserInfoTextView);
        userInfoTV.setText(userName);
        timeLeft = findViewById(R.id.hTimerTextView);
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
                Intent goBack = new Intent(HardActivity.this , SelectDifficulty.class);

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
    ImageButton image17;
    ImageButton image18;
    ImageButton image19;
    ImageButton image20;
    ImageButton image21;
    ImageButton image22;
    ImageButton image23;
    ImageButton image24;


    protected void play(){

        image1 = findViewById(R.id.hImageButton1);
        image2 = findViewById(R.id.hImageButton2);
        image3 = findViewById(R.id.hImageButton3);
        image4 = findViewById(R.id.hImageButton4);
        image5 = findViewById(R.id.hImageButton5);
        image6 = findViewById(R.id.hImageButton6);
        image7 = findViewById(R.id.hImageButton7);
        image8 = findViewById(R.id.hImageButton8);
        image9 = findViewById(R.id.hImageButton9);
        image10 = findViewById(R.id.hImageButton10);
        image11 = findViewById(R.id.hImageButton11);
        image12 = findViewById(R.id.hImageButton12);
        image13 = findViewById(R.id.hImageButton13);
        image14 = findViewById(R.id.hImageButton14);
        image15 = findViewById(R.id.hImageButton15);
        image16 = findViewById(R.id.hImageButton16);
        image17 = findViewById(R.id.hImageButton17);
        image18 = findViewById(R.id.hImageButton18);
        image19 = findViewById(R.id.hImageButton19);
        image20 = findViewById(R.id.hImageButton20);
        image21 = findViewById(R.id.hImageButton21);
        image22 = findViewById(R.id.hImageButton22);
        image23 = findViewById(R.id.hImageButton23);
        image24 = findViewById(R.id.hImageButton24);

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
        image17.setOnClickListener(imageListener);
        image18.setOnClickListener(imageListener);
        image19.setOnClickListener(imageListener);
        image20.setOnClickListener(imageListener);
        image21.setOnClickListener(imageListener);
        image22.setOnClickListener(imageListener);
        image23.setOnClickListener(imageListener);
        image24.setOnClickListener(imageListener);

    }
    private final Handler handler = new Handler();
    private View.OnClickListener imageListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            myCardsId = view.getId();
            ImageButton ib = findViewById(myCardsId);
            switch (myCardsId){
                case R.id.hImageButton1:
                case R.id.hImageButton4:
                    ib.setImageResource(R.drawable.nin);
                    break;
                case R.id.hImageButton2:
                case R.id.hImageButton11:
                    ib.setImageResource(R.drawable.bear);
                    break;
                case R.id.hImageButton3:
                case R.id.hImageButton7:
                    ib.setImageResource(R.drawable.dolphin);
                    break;
                case R.id.hImageButton5:
                case R.id.hImageButton16:
                    ib.setImageResource(R.drawable.dude);
                    break;
                case R.id.hImageButton6:
                case R.id.hImageButton13:
                    ib.setImageResource(R.drawable.giraffe);
                    break;
                case R.id.hImageButton8:
                case R.id.hImageButton9:
                    ib.setImageResource(R.drawable.dog);
                    break;
                case R.id.hImageButton17:
                case R.id.hImageButton24:
                    ib.setImageResource(R.drawable.kangaroo);
                    break;
                case R.id.hImageButton18:
                case R.id.hImageButton22:
                    ib.setImageResource(R.drawable.lion);
                    break;
                case R.id.hImageButton19:
                case R.id.hImageButton21:
                    ib.setImageResource(R.drawable.oink);
                    break;
                case R.id.hImageButton12:
                case R.id.hImageButton14:
                    ib.setImageResource(R.drawable.panda);
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
