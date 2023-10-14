package pt.ipp.isep.esinf.domain.vehicle;

import java.util.Objects;

public class Vehicle {
    private String id;
    private String type;
    private String vClass;
    private String engine;
    private String tranmission;
    private String wheels;

    private String weight;


    public Vehicle(String id, String type, String vClass, String engine, String tranmission, String wheels, String weight) {
        this.id = id;
        this.type = type;
        this.vClass = vClass;
        this.engine = engine;
        this.tranmission = tranmission;
        this.wheels = wheels;
        this.weight = weight;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getvClass() {
        return vClass;
    }

    public String getEngine() {
        return engine;
    }

    public String getTranmission() {
        return tranmission;
    }

    public String getWheels() {
        return wheels;
    }

    public String getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vehicle vehicle = (Vehicle) o;
        return Objects.equals(id, vehicle.id) && Objects.equals(type, vehicle.type) && Objects.equals(vClass, vehicle.vClass) && Objects.equals(engine, vehicle.engine) && Objects.equals(tranmission, vehicle.tranmission) && Objects.equals(wheels, vehicle.wheels) && Objects.equals(weight, vehicle.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, vClass, engine, tranmission, wheels, weight);
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", vClass='" + vClass + '\'' +
                ", engine='" + engine + '\'' +
                ", tranmission='" + tranmission + '\'' +
                ", wheels='" + wheels + '\'' +
                ", weight='" + weight + '\'' +
                '}';
    }
}
