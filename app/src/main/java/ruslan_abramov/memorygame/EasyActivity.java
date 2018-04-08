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

public class EasyActivity extends AppCompatActivity {

    private Intent passedParam;
    private String userName;
    private int userAge;
    private  TextView timeLeft;
    private CountDownTimer timer; //creating a countDownTimer
    private long milliSeconds = 1000;
    private int timeFactor = 30;

    private int pressedCardID = 0;
    private int myCardsId = 0;
    private int cardsLeft = 4;
    private boolean isPressed = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_easy);
        getUserInfo();
        TextView userInfoTV = findViewById(R.id.userNameTextView);
        userInfoTV.setText(userName);
        timeLeft = findViewById(R.id.timeLeftEasy);
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
                Intent goBack = new Intent(EasyActivity.this , SelectDifficulty.class);

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

    protected void play(){

        image1 = findViewById(R.id.imageButton1);
        image2 = findViewById(R.id.imageButton2);
        image3 = findViewById(R.id.imageButton3);
        image4 = findViewById(R.id.imageButton4);

        image1.setOnClickListener(imageListener);
        image2.setOnClickListener(imageListener);
        image3.setOnClickListener(imageListener);
        image4.setOnClickListener(imageListener);

    }
    private final Handler handler = new Handler();
    private View.OnClickListener imageListener = new View.OnClickListener(){

        @Override
        public void onClick(View view) {
            myCardsId = view.getId();
            ImageButton ib = findViewById(myCardsId);
            switch (myCardsId){
                case R.id.imageButton1:
                case R.id.imageButton4:
                    ib.setImageResource(R.drawable.nin);
                    break;
                default:
                    ib.setImageResource(R.drawable.dude);
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
