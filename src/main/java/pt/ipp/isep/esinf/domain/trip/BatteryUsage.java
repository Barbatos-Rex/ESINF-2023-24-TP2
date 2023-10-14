package pt.ipp.isep.esinf.domain.trip;

import java.util.Objects;

public class BatteryUsage {
    private String airPower;

    private String heatPower;

    private String baterryCurrent;

    private String batterySoc;

    private String batteryVoltage;

    public BatteryUsage(String airPower, String heatPower, String baterryCurrent, String batterySoc, String batteryVoltage) {
        this.airPower = airPower;
        this.heatPower = heatPower;
        this.baterryCurrent = baterryCurrent;
        this.batterySoc = batterySoc;
        this.batteryVoltage = batteryVoltage;
    }

    public String getAirPower() {
        return airPower;
    }

    public String getHeatPower() {
        return heatPower;
    }

    public String getBaterryCurrent() {
        return baterryCurrent;
    }

    public String getBatterySoc() {
        return batterySoc;
    }

    public String getBatteryVoltage() {
        return batteryVoltage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BatteryUsage that = (BatteryUsage) o;
        return Objects.equals(airPower, that.airPower) && Objects.equals(heatPower, that.heatPower) && Objects.equals(baterryCurrent, that.baterryCurrent) && Objects.equals(batterySoc, that.batterySoc) && Objects.equals(batteryVoltage, that.batteryVoltage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(airPower, heatPower, baterryCurrent, batterySoc, batteryVoltage);
    }

    @Override
    public String toString() {
        return "batteryUsage: {" +
                "airPower='" + airPower + '\'' +
                ", heatPower='" + heatPower + '\'' +
                ", baterryCurrent='" + baterryCurrent + '\'' +
                ", batterySoc='" + batterySoc + '\'' +
                ", batteryVoltage='" + batteryVoltage + '\'' +
                '}';
    }
}
