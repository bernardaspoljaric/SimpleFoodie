package hr.ferit.bspoljaric.simplefoodie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import hr.ferit.bspoljaric.simplefoodie.Common.Common;
import hr.ferit.bspoljaric.simplefoodie.Model.Users;

public class Login extends AppCompatActivity {

    EditText phoneInput, passwordInput;
    Button logButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        phoneInput= findViewById(R.id.phoneInput);
        passwordInput= findViewById(R.id.passwordInput);
        logButton= findViewById(R.id.logButton);

        final FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference table_users = database.getReference("Users");
        logButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final ProgressDialog mDialog = new ProgressDialog(Login.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();
                table_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Check if user exist in database
                        if (dataSnapshot.child(phoneInput.getText().toString()).exists()) {
                            //Get user data
                            mDialog.dismiss();
                            Users users = dataSnapshot.child(phoneInput.getText().toString()).getValue(Users.class);
                            if (users.getPassword().equals(passwordInput.getText().toString())) {
                                Intent homeIntent = new Intent(Login.this, Home.class);
                                Common.currentUser= users;
                                startActivity(homeIntent);
                                finish();

                            } else {
                                Toast.makeText(Login.this, "Log in failed!", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Login.this, "User doesn't exist!", Toast.LENGTH_SHORT).show();
                        }
                    }
                        
                    

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
