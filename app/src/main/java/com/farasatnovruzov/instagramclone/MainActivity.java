package com.farasatnovruzov.instagramclone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.farasatnovruzov.instagramclone.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth auth;
//    String email = binding.emailText.getText().toString();
//    String password = binding.passwordText.getText().toString();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

//        email = binding.emailText.getText().toString();
//        password = binding.passwordText.getText().toString();

        auth = FirebaseAuth.getInstance();


        FirebaseUser user = auth.getCurrentUser();
        if(user != null){
            Intent intent  = new Intent(MainActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();
        }

    }

    public void signInClicked(View view){

        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_LONG).show();
        }else if(password.length()<6){
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
        }else {
            auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(MainActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG);
                }
            });
        }
    }

    public void signUpClicked(View view){

        String email = binding.emailText.getText().toString();
        String password = binding.passwordText.getText().toString();

        if(email.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_LONG).show();
        }else if(password.length()<6){
            Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show();
        }else{
            auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Toast.makeText(MainActivity.this, "You have signed up successfully", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull  Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG);
                }
            });
        }

    }
}