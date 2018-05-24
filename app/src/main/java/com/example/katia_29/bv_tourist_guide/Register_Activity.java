package com.example.katia_29.bv_tourist_guide;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Register_Activity extends AppCompatActivity {
    TextView bv, already;
    EditText Email, Password, confirm_password;
    Button Register;
    String Email_string, Password_string, confirm_password_string;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();

        Log.e("Register", "onCreate: "+uid);

        //Save data to shared Preferences

        SharedPreferences.Editor editor = getSharedPreferences("USER_INFORMATION", MODE_PRIVATE).edit();
        editor.putString("uid", uid);
        editor.apply();


        if (user != null) {
            // User is signed in
            Intent i = new Intent(Register_Activity.this, Profile.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        } else {
            // User is signed out
            Log.d("RegisterActivity", "onAuthStateChanged:signed_out");
        }



        setContentView(R.layout.activity_register_);

        auth = FirebaseAuth.getInstance();
        already = (TextView) findViewById(R.id.already);
        already.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Register_Activity.this, Login.class);
                startActivity(i);
            }
        });

        Email = (EditText) findViewById(R.id.Email);


        Password = (EditText) findViewById(R.id.Password);


        confirm_password = (EditText) findViewById(R.id.confirm_password);
        confirm_password_string = confirm_password.getText().toString().trim();


        Register = (Button) findViewById(R.id.Register);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Email_string = Email.getText().toString().trim();
                Password_string = Password.getText().toString().trim();

                if (TextUtils.isEmpty(Email_string)) {
                    Toast.makeText(Register_Activity.this, "Please Enter Email", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(Password_string)) {


                    Toast.makeText(Register_Activity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();

                } else if (TextUtils.isEmpty(confirm_password_string)) {
                    Toast.makeText(Register_Activity.this, "Please Enter Confirm Password", Toast.LENGTH_SHORT).show();
                }

                if(TextUtils.isEmpty(Email_string)||TextUtils.isEmpty(Password_string)){

                    Toast.makeText(Register_Activity.this, "Email or password is blank", Toast.LENGTH_SHORT).show();
                }
                else {
                    auth.createUserWithEmailAndPassword(Email_string, Password_string).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Register_Activity.this, "Success", Toast.LENGTH_SHORT).show();
                                Email.setText("");
                                confirm_password.setText("");
                                Password.setText("");
                            } else {
                                Toast.makeText(Register_Activity.this, "fail", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });
                }
            }
        });
    }
}
