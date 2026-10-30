package Services;

import Models.Delivery;
import Repository.DeliveryRepository;
import java.util.List;

public class DeliveryService {

    private DeliveryRepository deliveryRepository;

    // Constructor Injection
    public DeliveryService(DeliveryRepository deliveryRepository) {
        this.deliveryRepository = deliveryRepository;
    }

    // Add Delivery
    public void addDelivery(int deliveryId, int orderId,
            String address, String partner) {

        if (deliveryId <= 0 || orderId <= 0) {
            System.out.println("Invalid ID!");
            return;
        }

        if (address == null || address.trim().isEmpty()
                || partner == null || partner.trim().isEmpty()) {
            System.out.println("Address and partner are required!");
            return;
        }

        Delivery delivery = new Delivery(
                deliveryId,
                orderId,
                address,
                partner,
                "Processing"
        );

        boolean added = deliveryRepository.addDelivery(delivery);

        if (added) {
            System.out.println("Delivery Added Successfully!");
        } else {
            System.out.println("Delivery ID already exists!");
        }
    }

    // View All Deliveries
    public void viewDeliveries() {

        List<Delivery> deliveries
                = deliveryRepository.getAllDeliveries();

        if (deliveries.isEmpty()) {
            System.out.println("No Deliveries Found!");
            return;
        }

        for (Delivery delivery : deliveries) {
            delivery.displayDelivery();
            System.out.println("-----------------------");
        }
    }

    // Search Delivery
    public void searchDelivery(int deliveryId) {

        Delivery delivery
                = deliveryRepository.getDeliveryById(deliveryId);

        if (delivery != null) {
            delivery.displayDelivery();
        } else {
            System.out.println("Delivery Not Found!");
        }
    }

    // Update Delivery Status
    public void updateStatus(int deliveryId, String status) {

        if (status == null
                || !(status.equalsIgnoreCase("Processing")
                || status.equalsIgnoreCase("Shipped")
                || status.equalsIgnoreCase("Out for Delivery")
                || status.equalsIgnoreCase("Delivered"))) {

            System.out.println("Invalid Delivery Status!");
            return;
        }

        boolean updated
                = deliveryRepository.updateDeliveryStatus(
                        deliveryId, status
                );

        if (updated) {
            System.out.println("Delivery Status Updated!");
        } else {
            System.out.println("Delivery Not Found!");
        }
    }
}
