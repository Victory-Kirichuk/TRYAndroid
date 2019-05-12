package com.example.giftsandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.giftsandroid.Database.DatabaseLoginHalper;
import com.example.giftsandroid.R;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login (View view) {
        EditText emailText = (EditText) findViewById(R.id.email);
        String email = emailText.getText().toString();

        EditText passwordText = (EditText) findViewById(R.id.password);
        String password = passwordText.getText().toString();


        DatabaseLoginHalper databaseLoginHalper = new DatabaseLoginHalper(this);

        if (databaseLoginHalper.checkUser(email, password)){
            String username = databaseLoginHalper.findUsername(email);
            Intent accountIntent = new Intent(this, MainActivity.class);
            Toast login = Toast.makeText(this, "Здравствуйте, " + username, Toast.LENGTH_SHORT);
            startActivity(accountIntent);
            login.show();
        }
        else {
            Toast toast = Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT);
            toast.show();
        }

     }

     public void goToRegister(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
     }

     public void forgetPassword(View view) {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
     }
}
