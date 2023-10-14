package pt.ipp.isep.esinf.domain.trip;

import java.util.Objects;

public class TripFuelExpendures {
    private String speed;

    private String maf;

    private String rpm;

    private String load;
    private String oat;

    private String fuelRate;

    public TripFuelExpendures(String speed, String maf, String rpm, String load, String oat, String fuelRate) {
        this.speed = speed;
        this.maf = maf;
        this.rpm = rpm;
        this.load = load;
        this.oat = oat;
        this.fuelRate = fuelRate;
    }

    public String getSpeed() {
        return speed;
    }

    public String getMaf() {
        return maf;
    }

    public String getRpm() {
        return rpm;
    }

    public String getLoad() {
        return load;
    }

    public String getOat() {
        return oat;
    }

    public String getFuelRate() {
        return fuelRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TripFuelExpendures that = (TripFuelExpendures) o;
        return Objects.equals(speed, that.speed) && Objects.equals(maf, that.maf) && Objects.equals(rpm, that.rpm) && Objects.equals(load, that.load) && Objects.equals(oat, that.oat) && Objects.equals(fuelRate, that.fuelRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(speed, maf, rpm, load, oat, fuelRate);
    }
}
