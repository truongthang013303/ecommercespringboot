package com.tbt.ecommerce.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class PaymentDetails {
//    private PaymentMethod paymentMethod;
//    private PaymentStatus paymentStatus;
    private String paymentMethod;
    private String status;
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReferenceId;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId;
}
