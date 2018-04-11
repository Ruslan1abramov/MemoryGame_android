/**
* created by
* ruslan abramov 306847393
*/
package ruslan_abramov.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SelectDifficulty extends AppCompatActivity implements GameValues {
    private Intent passedParam;
    private Intent nextActivity;
    private String userInfoString;
    private String userName;
    private int userAge;
    private TextView userInfo;
    private RadioGroup rg;
    private Button continueButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_difficulty);
        getUserInfo();
        userInfo = findViewById(R.id.userInfoTextView);
        userInfo.setText(userInfoString);//getting user info from previous activity

        rg = findViewById(R.id.RGroup);

        continueButton = findViewById(R.id.buttonFinished2);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedButtonID = rg.getCheckedRadioButtonId();
                setNextActivity(checkedButtonID);//setting the next activity
                startActivity(nextActivity);//going to next activity
            }
        });

    }
    /*gathering user data from previous activity*/
    protected void getUserInfo(){
        passedParam = getIntent();
        userName = passedParam.getStringExtra("userName");
        userAge = passedParam.getIntExtra("userAge", 0);
        userInfoString = userName + " " + userAge;
    }
    /*gathering the data we need to send to our next activity*/
    protected void setNextActivity(int checkedButtonID) {
        nextActivity = new Intent(SelectDifficulty.this, GameActivity.class);
        switch (checkedButtonID) {
            case R.id.mediumRB:
                nextActivity.putExtra("TIME_FACTOR", MEDIUM_TIME_FACTOR);
                nextActivity.putExtra("difficulty", MEDIUM_ID);
                break;
            case R.id.hardRB:
                nextActivity.putExtra("TIME_FACTOR", HARD_TIME_FACTOR);
                nextActivity.putExtra("difficulty", HARD_ID);
                break;
            case R.id.easyRB:
            default:
                nextActivity.putExtra("TIME_FACTOR", EASY_TIME_FACTOR);
                nextActivity.putExtra("difficulty", EASY_ID);
                break;
        }
        nextActivity.putExtra("userName", userName);
        nextActivity.putExtra("userAge", userAge);
    }
}
