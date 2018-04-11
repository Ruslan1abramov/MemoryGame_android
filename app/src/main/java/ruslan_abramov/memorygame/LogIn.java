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
import android.widget.EditText;
import android.widget.Toast;

public class LogIn extends AppCompatActivity {

    private EditText userName;
    private EditText userAge;
    private Button continueButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        Toast.makeText(getApplicationContext(), getString(R.string.greetings), Toast.LENGTH_SHORT).show();

        userName = findViewById(R.id.nameEditText);
        userAge = findViewById(R.id.ageEditText);
        continueButton = findViewById(R.id.buttonFinished);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //gathering user info
                String userNameString = userName.getText().toString();  //getting user name
                userNameString = userNameString.isEmpty() ? getString(R.string.defaultName) : userNameString;
                String userAgeString = userAge.getText().toString();
                int age = Integer.parseInt(userAgeString.isEmpty() ? getString(R.string.defaultAge) : userAgeString); //getting user age
                //adding user info to next activity
                Intent intent = new Intent(LogIn.this, SelectDifficulty.class);
                intent.putExtra("userName", userNameString);
                intent.putExtra("userAge", age);
                //going to next activity
                startActivity(intent);

            }
        });

    }
}
