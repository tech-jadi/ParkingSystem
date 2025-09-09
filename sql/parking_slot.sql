CREATE DATABASE parking_lot;

USE parking_lot;

CREATE TABLE parking_slot (
	id INT AUTO_INCREMENT PRIMARY KEY,
    slot_code VARCHAR(4) NOT NULL UNIQUE,
    vehicle_type ENUM("4-Wheeler", "2-Wheeler"),
    is_occupied ENUM("TRUE", "FALSE") DEFAULT "FALSE",
    starting_time DATETIME DEFAULT NULL
);

# Data for 2 Wheeler
INSERT INTO parking_slot (slot_code, vehicle_type)
VALUE 
	("F1A1", "2-Wheeler"),
    ("F1A2", "2-Wheeler"),
    ("F1A3", "2-Wheeler"),
    ("F1A4", "2-Wheeler"),
    ("F1H1", "2-Wheeler"),
    ("F1H2", "2-Wheeler"),
    ("F1H3", "2-Wheeler"),
    ("F1H4", "2-Wheeler"),
    ("F1H5", "2-Wheeler"),
    ("F2A1", "2-Wheeler"),
    ("F2A2", "2-Wheeler"),
    ("F2A3", "2-Wheeler"),
    ("F2A4", "2-Wheeler"),
    ("F2H1", "2-Wheeler"),
    ("F2H2", "2-Wheeler"),
    ("F2H3", "2-Wheeler"),
    ("F2H4", "2-Wheeler"),
    ("F2H5", "2-Wheeler");

# For 4-Wheeler
INSERT INTO parking_slot(slot_code, vehicle_type)
VALUE 
	("F1B1", "4-Wheeler"),
    ("F1B2", "4-Wheeler"),
    ("F1B3", "4-Wheeler"),
    ("F1B4", "4-Wheeler"),
    ("F1B5", "4-Wheeler"),
    ("F1C1", "4-Wheeler"),
    ("F1C2", "4-Wheeler"),
    ("F1C3", "4-Wheeler"),
    ("F1C4", "4-Wheeler"),
    ("F1C5", "4-Wheeler"),
    ("F1D1", "4-Wheeler"),
    ("F1D2", "4-Wheeler"),
    ("F1D3", "4-Wheeler"),
    ("F1D4", "4-Wheeler"),
    ("F1D5", "4-Wheeler"),
    ("F1E1", "4-Wheeler"),
    ("F1E2", "4-Wheeler"),
    ("F1E3", "4-Wheeler"),
    ("F1E4", "4-Wheeler"),
    ("F1E5", "4-Wheeler"),
    ("F1F1", "4-Wheeler"),
    ("F1F2", "4-Wheeler"),
    ("F1F3", "4-Wheeler"),
    ("F1F4", "4-Wheeler"),
    ("F1F5", "4-Wheeler"),
    ("F1G1", "4-Wheeler"),
    ("F1G2", "4-Wheeler"),
    ("F1G3", "4-Wheeler"),
    ("F1G4", "4-Wheeler"),
    ("F1G5", "4-Wheeler"),
    ("F2B1", "4-Wheeler"),
    ("F2B2", "4-Wheeler"),
    ("F2B3", "4-Wheeler"),
    ("F2B4", "4-Wheeler"),
    ("F2B5", "4-Wheeler"),
    ("F2C1", "4-Wheeler"),
    ("F2C2", "4-Wheeler"),
    ("F2C3", "4-Wheeler"),
    ("F2C4", "4-Wheeler"),
    ("F2C5", "4-Wheeler"),
    ("F2D1", "4-Wheeler"),
    ("F2D2", "4-Wheeler"),
    ("F2D3", "4-Wheeler"),
    ("F2D4", "4-Wheeler"),
    ("F2D5", "4-Wheeler"),
    ("F2E1", "4-Wheeler"),
    ("F2E2", "4-Wheeler"),
    ("F2E3", "4-Wheeler"),
    ("F2E4", "4-Wheeler"),
    ("F2E5", "4-Wheeler"),
    ("F2F1", "4-Wheeler"),
    ("F2F2", "4-Wheeler"),
    ("F2F3", "4-Wheeler"),
    ("F2F4", "4-Wheeler"),
    ("F2F5", "4-Wheeler"),
    ("F2G1", "4-Wheeler"),
    ("F2G2", "4-Wheeler"),
    ("F2G3", "4-Wheeler"),
    ("F2G4", "4-Wheeler"),
    ("F2G5", "4-Wheeler");
    
SELECT * FROM parking_slot;

UPDATE parking_slot
SET
  is_occupied = "FALSE",
  starting_time = NULL;
  
desc parking_slot;