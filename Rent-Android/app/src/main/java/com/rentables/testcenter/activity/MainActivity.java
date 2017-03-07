package com.rentables.testcenter.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;

import com.rentables.testcenter.dialog.ErrorDialog;
import com.rentables.testcenter.dialog.ForgotPasswordDialog;
import com.rentables.testcenter.R;

import java.io.IOException;
import java.util.ArrayList;

import dataobject.LoginUser;
import dataobject.User;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class MainActivity extends AppCompatActivity implements ThreadListener {

    public static User CURRENT_USER;

    Thread loginThread = null;
    private ProgressDialog loginProgress;
    private DialogFragment currentDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Adding onKeyListener for password EditText
        onKeyListenerForPassword();

        //  Resetting weird password typeface
        resetPasswordTypeface();

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
                } else if(errors.get(0).contains(java.net.UnknownHostException.class.toString())){

                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showUnknownHostError();
                        }
                    });
                }

            } else if (errors == null) {

                loginProgress.dismiss();
                Intent loginIntent = new Intent();
                loginIntent.setClass(this, HomeActivity.class);
                startActivity(loginIntent);
            }
        }

        loginProgress.dismiss();
        loginThread = null;
    }

    public void userLogin(View view){

        //TODO Remove this eventually
        //This is a side step of the login

        EditText userAdmin = (EditText) findViewById(R.id.username_edit_text);
        EditText passwordAdmin = (EditText) findViewById(R.id.password_edit_text);

        if(userAdmin.getText().toString().trim().equals("1") && passwordAdmin.getText().toString().trim().equals("1")){

            Intent adminIntent = new Intent();
            adminIntent.setClass(this, HomeActivity.class);
            startActivity(adminIntent);
            return;

        }

        if (loginThread == null) {

            EditText userName = (EditText) findViewById(R.id.username_edit_text);
            EditText password = (EditText) findViewById(R.id.password_edit_text);
            boolean complete = true;

            if (userName.getText().toString().trim().equals("")) {

                userName.setError("Username Required");
                complete = false;
            }

            if (password.getText().toString().trim().equals("")) {

                password.setError("Password Required");
                complete = false;
            }

            if (complete) {

                loginProgress = new ProgressDialog(MainActivity.this, ProgressDialog.STYLE_SPINNER);
                loginProgress.setTitle("Logging In...");
                loginProgress.setMessage("please wait");
                loginProgress.show();

                LoginUser user = new LoginUser();
                user.setUsername(userName.getText().toString().trim());
                user.setPassword(password.getText().toString().trim());

                ServerConnection<LoginUser> connection = new ServerConnection<>(user);
                connection.addListener(this);

                loginThread = new Thread(connection);
                loginThread.start();

            }
        }
    }

    public void forgotPassword(View view){

        FragmentManager fm = getSupportFragmentManager();
        ForgotPasswordDialog forgotPass = new ForgotPasswordDialog();
        forgotPass.show(fm, "forgot_password");
    }

    public void showUnknownHostError(){

        Bundle bundle = new Bundle();
        bundle.putString("error", "Please try again. We were unable to connect to the server.");
        currentDialog = new ErrorDialog();
        currentDialog.setArguments(bundle);

        FragmentManager fm = getSupportFragmentManager();
        currentDialog.show(fm, "error_dialog");

    }

    public void dismissUnknownHostError(View view){

        if(currentDialog != null){

            currentDialog.dismiss();
            currentDialog = null;
        }
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
