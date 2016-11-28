package com.rentables.testcenter;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View.OnKeyListener;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Iterator;

import dataobject.CreateUser;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class RegisterUserActivity extends AppCompatActivity implements ThreadListener {

    private boolean passwordsMatch = false;
    private CreateUser newUser = new CreateUser();
    Thread connectionThread;

    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_register_user);

        //Setting up toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);

        //Resetting password typeface for both password EditTexts.
        resetPasswordTypeface();

        //Adding listeners to the confirm password EditText
        passwordChangeTextListener();
        confirmPasswordListener();
        confirmPasswordChangeTextListener();

        //Set Focus to Email Edit Text
        setFocusOfEditText(R.id.register_username);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.overflow_menu, menu);
        return true;
    }

    @Override
    public void notifyOfThreadCompletion(NotifyingThread notifyThread) {

        //Thread completion!!

        finalizeRegistration(notifyThread);

        System.out.println("The connection has been made and the thread has finished.");
    }

    public void finalizeRegistration(NotifyingThread notifyThread){

        //TODO This will most likely change some time in the future. The way the errors are handled
        //If the user was able to register then create a dialog box prompting the user with
        //"A confirmation email has been sent your way" then have a button to send the user
        //back to the login screen. Also, respond to errors here.

        final EditText username = (EditText) findViewById(R.id.register_username);
        final EditText password = (EditText) findViewById(R.id.register_password);

        ArrayList errors = notifyThread.getErrors();

        if(errors != null){
            if(notifyThread.getErrorAt(0).equals("could not execute statement")){

                this.runOnUiThread(new Runnable(){
                    @Override
                    public void run(){username.setError("Email already in use!");}
                });

            }else if(notifyThread.getErrorAt(0).equals("{\"password\":\"Password must contain at least seven characters with one number and one letter.\"}")) {

                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        password.setError("Must contain at least seven characters with one number and one letter");
                    }
                });
            }else if(notifyThread.getErrorAt(0).equals("{\"username\":\"User name must be a valid email address.\"}")){

                this.runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        username.setError("Not a valid email!");
                    }
                });
            }else{

                Iterator<String> iterator = errors.iterator();

                while(iterator.hasNext()){

                    System.out.println(iterator.next());
                }
            }
        }else if(errors == null){

            showRegistrationDialog();
            System.out.println("Woohoo! User has been registered successfully!");
        }
    }

    public void registerUser(View view){

        /*
        Before user can register make sure that the two passwords match. Also, make sure that
        all fields in the form are filled in completely.
         */

        if(!isFormCompleted() || !passwordsMatch) {

            //Checking to see if forms have been filled out completely and
            //that the passwords match correctly.
            return;

        }else{

            initializeNewUser();

            //Creating a ServerConnection with a CreateUser object. The ServerConnection object will
            //decide what kind of connection to make based off the object passed to it.
            ServerConnection<CreateUser> createUserConnection = new ServerConnection<>(newUser);
            createUserConnection.addListener(this);

            //Starting the thread which connects to the server
            connectionThread = new Thread(createUserConnection);
            connectionThread.start();
        }
    }

    public void showRegistrationDialog(){

        FragmentManager manager = getSupportFragmentManager();
        RegistrationSuccessDialog successDialog = new RegistrationSuccessDialog();
        successDialog.show(manager, "registration_successful_dialog");
    }

    public void onContinue(View view){

        this.finish();
    }

    public void initializeNewUser(){

        EditText username = (EditText) findViewById(R.id.register_username);
        EditText firstName = (EditText) findViewById(R.id.register_firstname);
        EditText lastName = (EditText) findViewById(R.id.register_lastname);
        EditText password = (EditText) findViewById(R.id.register_password);

        newUser.setUsername(username.getText().toString());
        newUser.setFirstName(firstName.getText().toString());
        newUser.setLastName(lastName.getText().toString());
        newUser.setPassword(password.getText().toString());
    }

    public void setFocusOfEditText(int viewId){

        EditText viewToFocus = (EditText) findViewById(viewId);
        viewToFocus.requestFocus();
    }

    public void confirmPasswordListener(){

        //A listener to check to see if the fields in the form are completely filled out.

        final EditText confirmPassText = (EditText) findViewById(R.id.register_password_confirm);

        confirmPassText.setOnKeyListener(new OnKeyListener(){

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){

                   registerUser(confirmPassText);
                }

                return false;
            }
        });
    }

    public void passwordChangeTextListener(){

        final EditText passText = (EditText) findViewById(R.id.register_password);

        passText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                confirmPassword();
            }
        });
    }

    public void confirmPasswordChangeTextListener(){

        //On text changed listener for the register_password_confirm EditText.
        //Used to confirm that both password EditTexts match.

        final EditText confirmPassText = (EditText) findViewById(R.id.register_password_confirm);

        confirmPassText.addTextChangedListener(new TextWatcher(){

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                confirmPassword();
            }
        });
    }

    public void confirmPassword(){

        EditText passwordEditText = (EditText) findViewById(R.id.register_password);
        EditText confirmPasswordEditText = (EditText) findViewById(R.id.register_password_confirm);

        String password = passwordEditText.getText().toString();
        String confirmPassword = confirmPasswordEditText.getText().toString();

        if(confirmPassword.equals(password)){

            passwordsMatch = true;
            confirmPasswordEditText.setError(null);

        }else{

            passwordsMatch = false;
            confirmPasswordEditText.setError("Passwords must match!");

        }
    }

    public boolean isFormCompleted(){

        EditText username = (EditText) findViewById(R.id.register_username);
        EditText firstName = (EditText) findViewById(R.id.register_firstname);
        EditText lastName = (EditText) findViewById(R.id.register_lastname);
        EditText password = (EditText) findViewById(R.id.register_password);
        EditText confirmPassword = (EditText) findViewById(R.id.register_password_confirm);

        boolean formCompleted = true;

        if(username.getText().toString().trim().equals("")){

            formCompleted = false;
            username.setError("Username Required");
        }

        if(firstName.getText().toString().trim().equals("")){

            formCompleted = false;
            firstName.setError("First Name Required");
        }

        if(lastName.getText().toString().trim().equals("")){

            formCompleted = false;
            lastName.setError("Last Name Required");
        }

        if(password.getText().toString().trim().equals("")){

            formCompleted = false;
            password.setError("Password Required");
        }

        if(confirmPassword.getText().toString().trim().equals("")){

            formCompleted = false;
        }

        return formCompleted;
    }

    public void resetPasswordTypeface(){

        EditText password = (EditText) findViewById(R.id.register_password);
        EditText confirmPassword = (EditText) findViewById(R.id.register_password_confirm);

        password.setTypeface(Typeface.DEFAULT);
        confirmPassword.setTypeface(Typeface.DEFAULT);

        password.setTransformationMethod(new PasswordTransformationMethod());
        confirmPassword.setTransformationMethod((new PasswordTransformationMethod()));
    }
}
