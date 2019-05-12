package com.example.giftsandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.giftsandroid.Database.DatabaseLoginHalper;
import com.example.giftsandroid.R;


public class ForgetPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
    }

    public void changePassword(View view) {
        EditText emailText = (EditText) findViewById(R.id.forget_password_email);
        String email = emailText.getText().toString();

        EditText newPasswordText = (EditText) findViewById(R.id.forget_password_new_password);
        String newPassword = newPasswordText.getText().toString();

        EditText confirmNewPasswordText = (EditText) findViewById(R.id.forget_password_confirm_new_password);
        String confirmNewPassword = confirmNewPasswordText.getText().toString();

        if (newPassword.equals(confirmNewPassword)) {
            DatabaseLoginHalper databaseLoginHalper = new DatabaseLoginHalper(this);
            if (databaseLoginHalper.checkUser(email)) {
                databaseLoginHalper.changePassword(email, newPassword);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast noThatUser = Toast.makeText(this, "Пользователя с таким email не существует", Toast.LENGTH_SHORT);
                noThatUser.show();
            }
        }
        else {
            Toast difPassword = Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT);
            difPassword.show();
        }
    }
}
