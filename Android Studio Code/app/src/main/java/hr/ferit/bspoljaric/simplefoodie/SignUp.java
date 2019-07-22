package hr.ferit.bspoljaric.simplefoodie;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
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

import hr.ferit.bspoljaric.simplefoodie.Model.Users;

public class SignUp extends AppCompatActivity {

    EditText phoneInput, nameInput, passwordInput;
    Button signButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        phoneInput= findViewById(R.id.phoneInput);
        passwordInput= findViewById(R.id.passwordInput);
        nameInput= findViewById(R.id.nameInput);
        signButton= findViewById(R.id.signButton);

        final FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference table_users = database.getReference("Users");

        signButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignUp.this);
                mDialog.setMessage("Please wait...");
                mDialog.show();

                table_users.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Check if email already exist
                        if (dataSnapshot.child(phoneInput.getText().toString()).exists()){
                            mDialog.dismiss();
                            Toast.makeText(SignUp.this, "Number already exist in database !", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            mDialog.dismiss();
                            Users users = new Users(nameInput.getText().toString(), passwordInput.getText().toString());
                            table_users.child(phoneInput.getText().toString()).setValue(users);
                            Toast.makeText(SignUp.this, "Sign Up seccessfully !", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
