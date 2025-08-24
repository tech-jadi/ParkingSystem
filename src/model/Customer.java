package model;

import java.time.LocalDateTime;

public record Customer(String vehicleType, String discountType, String slotCode, LocalDateTime entryTime,
    LocalDateTime exitTime,
    double subtotal, double discountAmount, double cash) {

  public Customer {
    if (vehicleType == null || discountType == null || slotCode == null || entryTime == null || exitTime == null) {
      throw new IllegalArgumentException(
          "Vehicle type, discount type, parking slot, entry time, and exit time cannot be null");
    } else if (subtotal < 0 || discountAmount < 0 || cash < 0) {
      throw new IllegalArgumentException("Subtotal, discount amount, and cash cannot be negative");
    } else if (cash < (subtotal - discountAmount)) {
      throw new IllegalArgumentException("Cash cannot be less than the total amount due");
    }
  }
}