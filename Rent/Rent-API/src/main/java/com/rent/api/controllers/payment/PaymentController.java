package com.rent.api.controllers.payment;

import com.rent.api.dto.payment.PaymentDTO;
import com.rent.api.utility.payment.PayPalConnector;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Asad on 3/2/2017.
 */

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    public String payWithPayPal() {
        return PayPalConnector.processPayPalPayment();
    }

    @RequestMapping(value = "/redirect", method = RequestMethod.GET)
    public String executePayment(PaymentDTO paymentDTO) {
        return PayPalConnector.executePayment(paymentDTO);
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.GET)
    public String cancelPayment() {
        return "payment cancelled";
    }
}
