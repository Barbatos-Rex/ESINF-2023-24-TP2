package pt.ipp.isep.esinf.domain.trip;

import java.util.Comparator;
import java.util.Objects;
import java.util.TreeSet;

public class Trip implements Comparable<Trip> {


    @Override
    public int compareTo(Trip o) {
        return Integer.compare(id.getId(), o.getId().getId());
    }

    public class TripInfo {
        private boolean upToDate;

        private double speedMax;
        private double speedMin;
        private double speedAvg;

        private double loadMax;
        private double loadMin;
        private double loadAvg;

        private double oatMax;
        private double oatMin;
        private double oatAvg;

        public TripInfo() {
            upToDate = false;
            speedMax = 0;
            speedMin = 0;
            speedAvg = 0;
            loadMax = 0;
            loadMin = 0;
            loadAvg = 0;
            oatMax = 0;
            oatMin = 0;
            oatAvg = 0;
        }

        public boolean isUpToDate() {
            return upToDate;
        }

        public double getSpeedMax() {
            return speedMax;
        }

        public double getSpeedMin() {
            return speedMin;
        }

        public double getSpeedAvg() {
            return speedAvg;
        }

        public double getLoadMax() {
            return loadMax;
        }

        public double getLoadMin() {
            return loadMin;
        }

        public double getLoadAvg() {
            return loadAvg;
        }

        public double getOatMax() {
            return oatMax;
        }

        public double getOatMin() {
            return oatMin;
        }

        public double getOatAvg() {
            return oatAvg;
        }

        public void updateResults() {
            if (upToDate) {
                return;
            }


            speedMax = Double.MIN_VALUE;
            loadMax = Double.MIN_VALUE;
            oatMax = Double.MIN_VALUE;

            speedMin = Double.MIN_VALUE;
            loadMin = Double.MIN_VALUE;
            oatMin = Double.MIN_VALUE;

            double speedCount = 0;
            double loadCount = 0;
            double oatCount = 0;


            for (TripEntry entry : entries) {
                TripFuelExpendures expendures = entry.getTripFuelExpendures();
                try {
                    double speedValue = Double.parseDouble(expendures.getSpeed());
                    speedMax = Math.max(speedValue, speedMax);
                    speedMin = Math.min(speedValue, speedMin);
                    speedCount++;
                    speedAvg += speedValue;
                } catch (NumberFormatException ignored) {
                    //Go to the next parameter
                }
                try {
                    double loadValue = Double.parseDouble(expendures.getLoad());
                    loadMax = Math.max(loadValue, loadMax);
                    loadMin = Math.min(loadValue, loadMin);
                    loadCount++;
                    loadAvg += loadValue;
                } catch (NumberFormatException ignored) {
                    //Go to the next parameter
                }
                try {
                    double oatValue = Double.parseDouble(expendures.getOat());
                    oatMax = Math.max(oatValue, oatMax);
                    oatMin = Math.min(oatValue, oatMin);
                    oatCount++;
                    oatAvg += oatValue;
                } catch (NumberFormatException ignored) {
                    //Go to the next parameter
                }


                try {
                    speedAvg = speedAvg / speedCount;
                } catch (ArithmeticException e) {
                    speedAvg = -1;
                }

                try {
                    loadAvg = loadAvg / loadCount;
                } catch (ArithmeticException e) {
                    loadAvg = -1;
                }
                try {
                    oatAvg = oatAvg / oatCount;
                } catch (ArithmeticException e) {
                    oatAvg = -1;
                }
            }
            upToDate = true;
        }

        public void alterStatus() {
            upToDate = false;
        }
    }


    private TripId id;

    private TripInfo info;

    private TreeSet<TripEntry> entries;

    public Trip(TripId id, TreeSet<TripEntry> entries) {
        info = new TripInfo();
        this.id = id;
        this.entries = entries;
    }

    public Trip(TripId id) {
        this.id = id;
        entries = new TreeSet<>();
        info = new TripInfo();
    }

    public TripId getId() {
        return id;
    }

    public TreeSet<TripEntry> getEntries() {
        return entries;
    }

    public void addEntry(TripEntry entry) {
        entries.add(entry);
        info.alterStatus();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trip trip = (Trip) o;
        return Objects.equals(id, trip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return id.toString();
    }

    public TripInfo getInfo() {
        if (!info.isUpToDate()) {
            info.updateResults();
        }
        return info;
    }

    public double tripDistance() {
        return entries.first().getCoordenates().distance(entries.last().getCoordenates());
    }

    public double tripRealDistance() {
        double distance = 0;
        TimeCoordenates last = null;
        for (TripEntry entry : entries) {
            if (last == null) {
                last = entry.getCoordenates();
                continue;
            }

            distance += last.distance(entry.getCoordenates());
            last = entry.getCoordenates();
        }
        return distance;
    }


    public static class TripDistanceComparator implements Comparator<Trip> {

        @Override
        public int compare(Trip o1, Trip o2) {
            return Double.compare(o1.tripDistance(), o2.tripDistance());
        }
    }


    public static class TripRealDistanceComparator implements Comparator<Trip>{

        @Override
        public int compare(Trip o1, Trip o2) {
            return -Double.compare(o1.tripRealDistance(),o2.tripRealDistance());
        }
    }


}
