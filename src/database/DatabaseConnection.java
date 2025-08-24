package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Slot;

public class DatabaseConnection {
  private static Connection getConnection() {
    try {
      return DriverManager.getConnection(
          "jdbc:mysql://localhost:3306/parking_lot",
          "root",
          "root");
    } catch (SQLException e) {
      System.out.println("Connection Failed! Check output console");
      e.printStackTrace();
      return null;
    }
  }

  public static void setOccupied(int slotNumber, String vehicleType) {

    try (Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement("""
            UPDATE parking_slot
            SET
              is_occupied = "TRUE",
              starting_time = CURRENT_TIMESTAMP
            WHERE id = ? AND vehicle_type = ?""");) {
      statement.setInt(1, slotNumber);
      statement.setString(2, vehicleType);

      int rowsUpdated = statement.executeUpdate();
      assert rowsUpdated == 1 : "SQL Statement error. Refactor it.";
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static List<Integer> getIdOfAvailableSlots() {
    List<Integer> availableSlots = new ArrayList<>();

    try (Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement("""
            SELECT id FROM parking_slot
            WHERE is_occupied = "FALSE"
            ORDER BY id ASC""")) {
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        availableSlots.add(resultSet.getInt("id"));
      }

      return availableSlots;
    } catch (SQLException e) {
      e.printStackTrace();
      return availableSlots;
    }
  }

  public static Slot getLatestEntrySlot() {
    try (Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement("""
            SELECT * FROM PARKING_SLOT
            WHERE
              is_occupied = "TRUE"
            ORDER BY starting_time DESC
            LIMIT 1;
            """)) {
      ResultSet resultSet = statement.executeQuery();
      Slot latestSlot = null;

      if (resultSet.next()) {
        String vehicleType = resultSet.getString("vehicle_type");
        String slotCode = resultSet.getString("slot_code");
        boolean isOccupied = resultSet.getBoolean("is_occupied");
        LocalDateTime entryTime = resultSet.getTimestamp("starting_time").toLocalDateTime();

        latestSlot = new Slot(vehicleType, slotCode, isOccupied, entryTime);
      }
      return latestSlot;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static List<String> getOccupiedSlotCodes() {
    List<String> occupiedSlotCodes = new ArrayList<>();

    try (Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement("""
            SELECT slot_code FROM parking_slot
            WHERE is_occupied = "TRUE"
            ORDER BY id ASC""")) {
      ResultSet resultSet = statement.executeQuery();

      while (resultSet.next()) {
        occupiedSlotCodes.add(resultSet.getString("slot_code"));
      }

      return occupiedSlotCodes;
    } catch (SQLException e) {
      e.printStackTrace();
      return occupiedSlotCodes;
    }
  }

  public static Slot getSlotByCode(String slotCode) {
    Slot selectedSlot = null;

    try (Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement("""
              SELECT * FROM parking_slot
              WHERE slot_code = ?
            """)) {
      statement.setString(1, slotCode);
      ResultSet resultSet = statement.executeQuery();

      if (resultSet.next()) {
        String _slotCode = resultSet.getString("slot_code");
        String vehicleType = resultSet.getString("vehicle_type");
        String isOccupied = resultSet.getString("is_occupied");
        LocalDateTime startingTime = resultSet.getTimestamp("starting_time").toLocalDateTime();

        selectedSlot = new Slot(vehicleType, _slotCode, Boolean.parseBoolean(isOccupied), startingTime);
      }

      return selectedSlot;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  public static void setUnoccupied(String slotCode) {
    try (Connection conn = getConnection();
        PreparedStatement statement = conn.prepareStatement("""
            UPDATE parking_slot
            SET
              is_occupied = "FALSE",
              starting_time = NULL
            WHERE slot_code = ? AND is_occupied = "TRUE" """)) {
      statement.setString(1, slotCode);

      int rowsUpdated = statement.executeUpdate();
      assert rowsUpdated == 1 : "SQL Statement error. Refactor it.";
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public static Map<String, Integer> getOccupiedParkingLot() {
    Map<String, Integer> occupiedParkingLotMap = new HashMap<>();

    try (Connection conn = getConnection()) {
      String[] vehicleTypes = { "2-Wheeler", "4-Wheeler" };
      for (int i = 0; i < 2; i++) {
        PreparedStatement statement = conn.prepareStatement("""
            SELECT count(*) AS total FROM parking_slot
            WHERE is_occupied = "TRUE" AND vehicle_type = ?
            """);
        statement.setString(1, vehicleTypes[i]);
        ResultSet result = statement.executeQuery();

        if (result.next()) {
          occupiedParkingLotMap.put(vehicleTypes[i], result.getInt("total"));
        }
      }

      assert occupiedParkingLotMap.size() == 2 : "SQL Statement Error. Refactor it";
      return occupiedParkingLotMap;
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }
}
