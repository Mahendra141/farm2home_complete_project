package com.farm2home.util;

import com.farm2home.entity.Order;
import com.farm2home.entity.DeliveryDetails;

public class WhatsAppUtil {

    public static String generateMapsLink(Double lat, Double lng) {
        if (lat == null || lng == null) {
            return "Location not available";
        }
        return "https://www.google.com/maps?q=" + lat + "," + lng;
    }

    public static String buildOrderMessage(Order order) {

        DeliveryDetails delivery = order.getDeliveryDetails();

        String mapLink = "Location not available";

        if (delivery != null) {
            mapLink = generateMapsLink(
                    delivery.getLatitude(),
                    delivery.getLongitude()
            );
        }

        return "🛒 New Farm2Home Order\n\n" +
                "Order ID: " + order.getId() + "\n" +
                "Amount: ₹" + order.getTotalAmount() + "\n" +
                "Status: " + order.getStatus() + "\n\n" +
                "📍 Delivery Location:\n" + mapLink;
    }
}
