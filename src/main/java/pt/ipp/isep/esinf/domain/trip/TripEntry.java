package pt.ipp.isep.esinf.domain;

import java.util.Comparator;
import java.util.Objects;

public class TripEntry implements Comparable<TripEntry> {
    private TimeCoordenates coordenates;

    private TripFuelExpendures tripFuelExpendures;
    private BatteryUsage batteryUsage;

    private FuelTrimBank fuelBank;

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
        return Long.compare(Long.getLong(coordenates.getTimeStamp()), Long.getLong(o.coordenates.getTimeStamp()));
    }
}
