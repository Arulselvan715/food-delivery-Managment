package Repository;

import java.util.ArrayList;
import java.util.List;
import Models.Delivery;

public class DeliveryRepository {

    private List<Delivery> deliveries = new ArrayList<>();

    // Add Delivery
    public boolean addDelivery(Delivery delivery) {

        if (getDeliveryById(delivery.getDeliveryId()) != null) {
            return false;
        }

        deliveries.add(delivery);
        return true;
    }

    // View All Deliveries
    public List<Delivery> getAllDeliveries() {
        return deliveries;
    }

    // Search Delivery
    public Delivery getDeliveryById(int deliveryId) {

        for (Delivery delivery : deliveries) {

            if (delivery.getDeliveryId() == deliveryId) {
                return delivery;
            }
        }

        return null;
    }

    // Update Delivery Status
    public boolean updateDeliveryStatus(int deliveryId, String status) {

        Delivery delivery = getDeliveryById(deliveryId);

        if (delivery != null) {
            delivery.setDeliveryStatus(status);
            return true;
        }

        return false;
    }
}
