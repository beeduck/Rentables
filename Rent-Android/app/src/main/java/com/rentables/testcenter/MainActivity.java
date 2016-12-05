package com.rentables.testcenter;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;

import java.util.ArrayList;

import dataobject.Listing;
import dataobject.Listings;
import dataobject.LoginUser;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class MainActivity extends AppCompatActivity implements ThreadListener {

    Thread loginThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         //Setting up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Rentables");

        //Adding onKeyListener for password EditText
        onKeyListenerForPassword();

        //Resetting weird password typeface
        resetPasswordTypeface();

        testServerConnection();
    }

    public void testServerConnection(){

        Listings listings = new Listings();
        listings.setKeywords("test");

        ServerConnection<Listings> test = new ServerConnection<>(listings);
        test.addListener(this);

        Thread thread = new Thread(test);
        thread.start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.overflow_menu, menu);

        return true;
    }

    @Override
    public void notifyOfThreadCompletion(final NotifyingThread notifyingThread){

        if(loginThread != null) {
            ArrayList<String> errors = notifyingThread.getErrors();
            final EditText username = (EditText) findViewById(R.id.username_edit_text);
            final EditText password = (EditText) findViewById(R.id.password_edit_text);

            if (errors != null) {

                if (errors.get(0).contains("Bad credentials")) {

                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            password.setError("Username or password is incorrect!");
                        }
                    });

                } else if (errors.get(0).contains("User is disabled")) {

                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            //TODO Probably display a dialog fragment to inform the user of authorizing their account.
                            username.setError("Authentication required!");
                        }
                    });
                }

            } else if (errors == null) {

                Intent loginIntent = new Intent();
                loginIntent.setClass(this, HomeActivity.class);
                startActivity(loginIntent);
            }
        }

        loginThread = null;
    }

    public void userLogin(View view){

        EditText userName = (EditText) findViewById(R.id.username_edit_text);
        EditText password = (EditText) findViewById(R.id.password_edit_text);
        boolean complete = true;

        if(userName.getText().toString().trim().equals("")){

            userName.setError("Username Required");
            complete = false;
        }

        if(password.getText().toString().trim().equals("")){

            password.setError("Password Required");
            complete = false;
        }

        if(complete){

            LoginUser user = new LoginUser();
            user.setUsername(userName.getText().toString().trim());
            user.setPassword(password.getText().toString().trim());

            ServerConnection<LoginUser> connection = new ServerConnection<>(user);
            connection.addListener(this);

            loginThread = new Thread(connection);
            loginThread.start();
        }
    }

    public void forgotPassword(View view){

        FragmentManager fm = getSupportFragmentManager();
        ForgotPasswordDialog forgotPass = new ForgotPasswordDialog();
        forgotPass.show(fm, "forgot_password");
    }


    public void startRegisterUserActivity(View view){

        Intent registerIntent = new Intent(this, RegisterUserActivity.class);
        startActivity(registerIntent);

    }

    public void resetPasswordTypeface(){

        EditText password = (EditText) findViewById(R.id.password_edit_text);
        password.setTypeface(Typeface.DEFAULT);
        password.setTransformationMethod(new PasswordTransformationMethod());
    }

    public void onKeyListenerForPassword(){

        final EditText passText = (EditText) findViewById(R.id.password_edit_text);

        passText.setOnKeyListener(new OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event){

                if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP){

                    userLogin(passText);
                    return true;
                }

                return false;
            }
        });
    }
}
