package com.rent.api.dto.payment;

import javax.validation.Valid;

/**
 * Created by Asad on 3/2/2017.
 */
public class PaymentDTO {

    @Valid
    private String paymentId;

    @Valid
    private String token;

    @Valid
    private String PayerID;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPayerID() {
        return PayerID;
    }

    public void setPayerID(String payerID) {
        PayerID = payerID;
    }
}
