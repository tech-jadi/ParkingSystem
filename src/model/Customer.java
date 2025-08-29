package model;

import java.time.LocalDateTime;

/**
 * A record representing a customer in the parking system.
 * 
 * @param vehicleType    the type of vehicle (e.g., 4-Wheeler, 2-Wheeler).
 * @param discountType   the type of discount applied (e.g., Regular, PWD,
 *                       senior, Student).
 * @param slotCode       the code of the parking slot assigned to the customer.
 * @param entryTime      the time the customer entered the parking lot.
 * @param exitTime       the time the customer exited the parking lot.
 * @param subtotal       the subtotal amount before discounts.
 * @param discountAmount the amount of discount applied.
 * @param cash           the amount of cash paid by the customer.
 * @throws IllegalArgumentException if any of the parameters are invalid (e.g.,
 *                                  null values, negative amounts, or cash less
 *                                  than total due).
 */
public record Customer(String vehicleType, String discountType, String slotCode, LocalDateTime entryTime,
    LocalDateTime exitTime,
    double subtotal, double discountAmount, double cash) {

  /**
   * Instantiate a new Customer record with validation.
   * 
   * @param vehicleType    the type of vehicle (e.g., 4-Wheeler, 2-Wheeler).
   * @param discountType   the type of discount applied (e.g., Regular, PWD,
   *                       senior, Student).
   * @param slotCode       the code of the parking slot assigned to the customer.
   * @param entryTime      the time the customer entered the parking lot.
   * @param exitTime       the time the customer exited the parking lot.
   * @param subtotal       the subtotal amount before discounts.
   * @param discountAmount the amount of discount applied.
   * @param cash           the amount of cash paid by the customer.
   * @throws IllegalArgumentException if any of the parameters are invalid (e.g.,
   *                                  null values, negative amounts, or cash less
   *                                  than total due).
   */
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