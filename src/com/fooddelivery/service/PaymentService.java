package com.fooddelivery.service;

import com.fooddelivery.model.Payment;
import com.fooddelivery.repository.PaymentRepository;
import java.util.List;

public class PaymentService {

    private PaymentRepository paymentRepository;

    public PaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // Make Payment
    public void makePayment(int paymentId, int orderId,
            double amount, String method) {

        if (amount <= 0) {
            System.out.println("Invalid payment amount!");
            return;
        }

        String status = "Success";

        Payment payment = new Payment(
                paymentId, orderId, amount, method, status
        );

        paymentRepository.addPayment(payment);
        System.out.println("Payment completed successfully!");
    }

    // View All Payments
    public void viewPayments() {
        List<Payment> payments = paymentRepository.getAllPayments();

        if (payments.isEmpty()) {
            System.out.println("No payments found!");
            return;
        }

        for (Payment payment : payments) {
            payment.displayPayment();
            System.out.println("------------------");
        }
    }

    // Search Payment
    public void searchPayment(int paymentId) {
        Payment payment = paymentRepository.getPaymentById(paymentId);

        if (payment != null) {
            payment.displayPayment();
        } else {
            System.out.println("Payment not found!");
        }
    }

    // Update Payment Status
    public void updateStatus(int paymentId, String status) {
        boolean updated = paymentRepository.updatePaymentStatus(paymentId, status);

        if (updated) {
            System.out.println("Payment status updated!");
        } else {
            System.out.println("Payment not found!");
        }
    }
}
