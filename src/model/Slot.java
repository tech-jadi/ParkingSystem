package model;

import java.time.LocalDateTime;

/**
 * Represents a parking slot with details about the vehicle type, slot code,
 * occupancy status, and starting time.
 * 
 * @param vehicleType  the type of vehicle (e.g., 4-Wheeler, 2-Wheeler).
 * @param slotCode     the unique code identifying the parking slot.
 * @param isOccupied   indicates whether the slot is currently occupied.
 * @param startingTime the time when the vehicle was parked.
 * @throws IllegalArgumentException if any of the parameters are null.
 */
public record Slot(String vehicleType, String slotCode, boolean isOccupied, LocalDateTime startingTime) {

  /**
   * Instantiates a new Slot object with the provided parameters, ensuring none
   * are null.
   * 
   * @param vehicleType  the type of vehicle (e.g., 4-Wheeler, 2-Wheeler).
   * @param slotCode     the unique code identifying the parking slot.
   * @param isOccupied   indicates whether the slot is currently occupied.
   * @param startingTime the time when the vehicle was parked.
   * @throws IllegalArgumentException if any of the parameters are null.
   */
  public Slot {
    if (vehicleType == null || slotCode == null || startingTime == null) {
      throw new IllegalArgumentException("Vehicle type, parking slot, and entry time cannot be null");
    }
  }

  /**
   * Returns a string representation of the Slot object, including vehicle type,
   * slot code, and starting time.
   * 
   * @return a string representation of the Slot object.
   */
  @Override
  public String toString() {
    return "Slot{" +
        "vehicleType='" + vehicleType + '\'' +
        ", parkingSlot='" + slotCode + '\'' +
        ", entryTime=" + startingTime +
        '}';
  }

}
