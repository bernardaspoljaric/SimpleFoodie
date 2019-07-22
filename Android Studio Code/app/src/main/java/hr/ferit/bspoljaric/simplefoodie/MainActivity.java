package hr.ferit.bspoljaric.simplefoodie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button loginButton, signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginButton= findViewById(R.id.loginButton);
        signUpButton= findViewById(R.id.signUpButton);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUp = new Intent(MainActivity.this, SignUp.class);
                startActivity(signUp);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                Intent logIn = new Intent(MainActivity.this, Login.class);
                startActivity(logIn);

            }
        });
    }
}
