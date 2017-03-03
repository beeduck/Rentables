package com.rentables.testcenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.paypal.android.sdk.payments.PayPalConfiguration;

public class PaymentActivity extends AppCompatActivity {

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK).clientId("")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
    }
}
