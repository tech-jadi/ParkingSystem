package model;

import java.time.LocalDateTime;

public record Slot(String vehicleType, String slotCode, boolean isOccupied, LocalDateTime startingTime) {

  public Slot {
    if (vehicleType == null || slotCode == null || startingTime == null) {
      throw new IllegalArgumentException("Vehicle type, parking slot, and entry time cannot be null");
    }
  }

  @Override
  public String toString() {
    return "Slot{" +
        "vehicleType='" + vehicleType + '\'' +
        ", parkingSlot='" + slotCode + '\'' +
        ", entryTime=" + startingTime +
        '}';
  }

}
