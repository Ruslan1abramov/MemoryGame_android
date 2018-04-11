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
import android.widget.Toast;

public class SelectDifficulty extends AppCompatActivity implements GameValues {
    private Intent passedParam;
    private Intent nextActivity;
    private String userInfoString;
    private String userName;
    private int userAge;
    private TextView userInfo;
    private RadioGroup rg;
    private Button continueButton;
    private int selectedDifficulty;
    private int won_lost_didntPlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_difficulty);

        getAndDisplayUserInfo();
        setRadioGroup();
        setButton();

    }
    /*get and display user info*/
    protected void getAndDisplayUserInfo(){
        getUserInfo();
        userInfo = findViewById(R.id.userInfoTextView);
        userInfo.setText(userInfoString);//getting user info from previous activity
    }
    /*set Button*/
    protected void setButton(){
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
    /*set radio group*/
    protected void setRadioGroup()
    {
        rg = findViewById(R.id.RGroup);
        if(won_lost_didntPlay == 1 && selectedDifficulty != HARD_ID)
            Toast.makeText(getApplicationContext(), getString(R.string.youWonTryAHarderLevel), Toast.LENGTH_SHORT).show();
        else if(won_lost_didntPlay == 1 && selectedDifficulty == HARD_ID)
        Toast.makeText(getApplicationContext(), getString(R.string.youAreAMaster), Toast.LENGTH_SHORT).show();
        switch(selectedDifficulty + won_lost_didntPlay) {
            case EASY_ID:
                rg.check(R.id.easyRB);
                break;
            case MEDIUM_ID:
                rg.check(R.id.mediumRB);
                break;
            default:
                rg.check(R.id.hardRB);
                break;
        }
    }
    /*gathering user data from previous activity*/
    protected void getUserInfo(){
        passedParam = getIntent();
        userName = passedParam.getStringExtra("userName");
        userAge = passedParam.getIntExtra("userAge", 0);
        userInfoString = getString(R.string.helloTO) +" " + userName +" " +
                getString(R.string.whoIsAged) + " " + userAge;
        selectedDifficulty = passedParam.getIntExtra("difficulty", EASY_ID);
        won_lost_didntPlay = passedParam.getIntExtra("winOrLose", 0);
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
