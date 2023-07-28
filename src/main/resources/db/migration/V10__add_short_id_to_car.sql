ALTER TABLE car_rentals.cars
    ADD COLUMN short_id VARCHAR(8) GENERATED ALWAYS AS (LEFT(id, 8)) STORED,
    ADD UNIQUE INDEX idx_short_id (short_id);
