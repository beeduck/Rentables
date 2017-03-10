package com.rentables.testcenter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.rentables.testcenter.activity.MainActivity;

import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;

import dataobject.Listing;
import dataobject.RentRequest;
import server.NotifyingThread;
import server.ServerConnection;
import server.ThreadListener;

public class PayPalPaymentActivity extends AppCompatActivity implements ThreadListener {

    Thread requestThread = null;
    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK).clientId("AXaDNQpEdrXJltQXlKuzkbbOb4_YNfEcYnlEN78TRYxXmkhsqxGReoihBNoXkyaA3iKfdK4I7o7HjOF6");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paypal_payment);
        setText();
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
        startService(intent);
    }

    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }

    public void onBuyPressed(View pressed) {
        Bundle listingBundle = this.getIntent().getExtras();
        RentRequest rentRequest = new RentRequest();
        rentRequest.setListingId(listingBundle.getInt("id"));
        rentRequest.setRequestingUser(MainActivity.CURRENT_USER.getUserId());
        ServerConnection<RentRequest> serverConnection = new ServerConnection<>(rentRequest);
        serverConnection.addListener(this);
        requestThread = new Thread(serverConnection);
        requestThread.start();
    }

    private void setText() {
        Bundle listingBundle = this.getIntent().getExtras();
        TextView confirm = (TextView) findViewById(R.id.confirm_text);
        confirm.setText("Are you sure you want to rent " + listingBundle.getString("title") +
                " for $" + listingBundle.getString("price") + "?");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK) {
            PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
            if (confirm != null) {
                try {
                    Log.i("paymentExample", confirm.toJSONObject().toString(4));

                } catch (JSONException e) {
                    Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
                }
            }
        }
        else if (resultCode == Activity.RESULT_CANCELED) {
            Log.i("paymentExample", "The user canceled.");
        }
        else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
            Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
        }
    }

    @Override
    public void notifyOfThreadCompletion(final NotifyingThread notifyThread) {
        if(requestThread != null) {
            final TextView requestText = (TextView) findViewById(R.id.request_text);
            runOnUiThread(new Runnable(){
                @Override
                public void run(){
                    ArrayList<String> errors = notifyThread.getErrors();
                    if (errors == null) {
                        requestText.setText("Success");
                    }
                    else {
                        requestText.setText(notifyThread.getErrors().get(0));
                    }
                }
            });
        }
        requestThread = null;
//        Bundle listingBundle = this.getIntent().getExtras();
//        PayPalPayment payment = new PayPalPayment(new BigDecimal(listingBundle.getString("price")),
//                "USD", listingBundle.getString("title"),
//                PayPalPayment.PAYMENT_INTENT_SALE);
//        Intent intent = new Intent(this, PaymentActivity.class);
//        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
//        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
//        startActivityForResult(intent, 0);
    }
}
