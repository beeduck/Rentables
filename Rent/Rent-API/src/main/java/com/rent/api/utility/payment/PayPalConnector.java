package com.rent.api.utility.payment;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Asad on 2/28/2017.
 */


public class PayPalConnector {
    static APIContext context = new APIContext(System.getenv("PAYPAL_CLIENT_ID"), System.getenv("PAYPAL_CLIENT_SECRET"), "sandbox");

    public static String processPayPalPayment() {
        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://www.cnn.com");
        redirectUrls.setReturnUrl("http://www.facebook.com");

        Details details = new Details();
        details.setSubtotal("1");

        Amount amount = new Amount();
        amount.setCurrency("USD");

        amount.setTotal("1");
        amount.setDetails(details);

        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setDescription("Hi Kris");

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setRedirectUrls(redirectUrls);
        payment.setTransactions(transactions);

        try {
            Payment createdPayment = payment.create(context);
            Iterator links = createdPayment.getLinks().iterator();
            while(links.hasNext()) {
                Links link = (Links)links.next();
                if (link.getRel().equalsIgnoreCase("approval_url")) {
                    // REDIRECT USER TO link.getHref()
                    return link.getHref();
                }
            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getDetails());
        }
        return "failed";
    }
}