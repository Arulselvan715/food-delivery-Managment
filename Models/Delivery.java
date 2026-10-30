package Models;

public class Delivery {

    private int deliveryId;
    private int orderId;
    private String deliveryAddress;
    private String deliveryPartner;
    private String deliveryStatus;

    // Constructor
    public Delivery(int deliveryId, int orderId,
            String deliveryAddress,
            String deliveryPartner,
            String deliveryStatus) {

        this.deliveryId = deliveryId;
        this.orderId = orderId;
        this.deliveryAddress = deliveryAddress;
        this.deliveryPartner = deliveryPartner;
        this.deliveryStatus = deliveryStatus;
    }

    // Getters
    public int getDeliveryId() {
        return deliveryId;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public String getDeliveryPartner() {
        return deliveryPartner;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    // Setters
    public void setDeliveryId(int deliveryId) {
        this.deliveryId = deliveryId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public void setDeliveryPartner(String deliveryPartner) {
        this.deliveryPartner = deliveryPartner;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    // Display
    public void displayDelivery() {
        System.out.println("Delivery ID: " + deliveryId);
        System.out.println("Order ID: " + orderId);
        System.out.println("Address: " + deliveryAddress);
        System.out.println("Delivery Partner: " + deliveryPartner);
        System.out.println("Status: " + deliveryStatus);
    }
}
