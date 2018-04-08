package ruslan_abramov.memorygame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SelectDifficulty extends AppCompatActivity {
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

                nextActivity.putExtra("userName" , userName);
                nextActivity.putExtra("userAge" , userAge);

                startActivity(nextActivity);
            }
        });

    }

    protected void getUserInfo(){
        passedParam = getIntent();
        userName = passedParam.getStringExtra("userName");
        userAge = passedParam.getIntExtra("userAge", 0);
        userInfoString = userName + " " + userAge;
    }

    protected void setNextActivity( int checkedButtonID ){
        switch (checkedButtonID){
            case R.id.easyRB:
                nextActivity = new Intent(SelectDifficulty.this, EasyActivity.class);
                break;
            case R.id.mediumRB:
                nextActivity = new Intent(SelectDifficulty.this, MediumActivity.class);
                break;
            case R.id.hardRB:
                nextActivity = new Intent(SelectDifficulty.this, HardActivity.class);
                break;
            default:
                nextActivity = new Intent(SelectDifficulty.this, EasyActivity.class);
                break;
        }
    }
}
