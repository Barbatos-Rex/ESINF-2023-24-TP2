package pt.ipp.isep.esinf.domain.trip;

import java.util.Objects;

public class TripEntry implements Comparable<TripEntry> {
    private final TimeCoordenates coordenates;

    private final TripFuelExpendures tripFuelExpendures;
    private final BatteryUsage batteryUsage;

    private final FuelTrimBank fuelBank;

    public TripEntry(TimeCoordenates coordenates, TripFuelExpendures tripFuelExpendures, BatteryUsage batteryUsage, FuelTrimBank fuelBank) {
        this.coordenates = coordenates;
        this.tripFuelExpendures = tripFuelExpendures;
        this.batteryUsage = batteryUsage;
        this.fuelBank = fuelBank;
    }

    public TimeCoordenates getCoordenates() {
        return coordenates;
    }

    public TripFuelExpendures getTripFuelExpendures() {
        return tripFuelExpendures;
    }

    public BatteryUsage getBatteryUsage() {
        return batteryUsage;
    }

    public FuelTrimBank getFuelBank() {
        return fuelBank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripEntry tripEntry = (TripEntry) o;
        return Objects.equals(coordenates, tripEntry.coordenates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordenates);
    }

    @Override
    public int compareTo(TripEntry o) {
        return Long.compare(Long.parseLong(coordenates.getTimeStamp()), Long.parseLong(o.coordenates.getTimeStamp()));
    }
}
