package Repository;

import java.util.ArrayList;
import java.util.List;
import Models.Payment;

public class PaymentRepository {

    private List<Payment> payments = new ArrayList<>();

    // Add Payment
    public void addPayment(Payment payment) {
        payments.add(payment);
        System.out.println("Payment added successfully!");
    }

    // View All Payments
    public List<Payment> getAllPayments() {
        return payments;
    }

    // Find Payment by ID
    public Payment getPaymentById(int paymentId) {

        for (Payment payment : payments) {
            if (payment.getPaymentId() == paymentId) {
                return payment;
            }
        }

        return null;
    }

    // Update Payment Status
    public boolean updatePaymentStatus(int paymentId, String status) {

        Payment payment = getPaymentById(paymentId);

        if (payment != null) {
            payment.setPaymentStatus(status);
            return true;
        }

        return false;
    }
}
