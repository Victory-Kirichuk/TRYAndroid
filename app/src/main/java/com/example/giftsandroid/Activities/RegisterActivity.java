package com.example.giftsandroid.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.giftsandroid.Database.DatabaseLoginHalper;
import com.example.giftsandroid.R;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {

        EditText usernameText = (EditText) findViewById(R.id.register_username);
        String username = usernameText.getText().toString();

        EditText emailText = (EditText) findViewById(R.id.register_email);
        String email = emailText.getText().toString();

        EditText passwordText = (EditText) findViewById(R.id.register_password);
        String password = passwordText.getText().toString();

        EditText passwordConfirmText = (EditText) findViewById(R.id.register_confirm_password);
        String passwordConfirm = passwordConfirmText.getText().toString();

        if (!email.equals("")) {
            if (!password.equals("")) {
                if (password.equals(passwordConfirm)) {

                    DatabaseLoginHalper databaseLoginHalper = new DatabaseLoginHalper(this);

                    if (!databaseLoginHalper.checkUser(email)) {
                        databaseLoginHalper.createUser(username, email, password);
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast exist = Toast.makeText(this, "Пользователь с таким email уже существует", Toast.LENGTH_SHORT);
                        exist.show();
                    }

                } else {
                    Toast difPassword = Toast.makeText(this, "Пароли не совпадают", Toast.LENGTH_SHORT);
                    difPassword.show();
                }

            } else {
                Toast noPassword = Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT);
                noPassword.show();
            }

        } else {
            Toast noEmail = Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT);
            noEmail.show();
        }
    }
}
