package pt.ipp.isep.esinf.struct.simple;

import pt.ipp.isep.esinf.domain.trip.TimeCoordenates;
import pt.ipp.isep.esinf.domain.trip.Trip;

import java.util.*;

public class Trip2DTree extends TwoDTree<Trip> {


    private class ClosestToStartAndEndComparator implements Comparator<Trip> {


        private class DebugEntry {
            private Trip trip;
            private double val;

            public DebugEntry(Trip trip, double val) {
                this.trip = trip;
                this.val = val;
            }

            @Override
            public String toString() {
                return "DebugEntry{" +
                        "trip=" + trip.getId() +
                        ", val=" + val +
                        '}';
            }
        }

        private TimeCoordenates start;
        private TimeCoordenates end;

        private Set<DebugEntry> debugMap = new TreeSet<>(new Comparator<DebugEntry>() {
            @Override
            public int compare(DebugEntry o1, DebugEntry o2) {
                return Double.compare(o1.val,o2.val);
            }
        });

        public ClosestToStartAndEndComparator(TimeCoordenates start, TimeCoordenates end) {
            this.start = start;
            this.end = end;
        }


        private double totalDistance(Trip trip) {
            double d = start.distance(trip.getEntries().first().getCoordenates()) + end.distance(trip.getEntries().last().getCoordenates());
            debugMap.add(new DebugEntry(trip, d));
            return d;
        }

        @Override
        public int compare(Trip o1, Trip o2) {
            return Double.compare(totalDistance(o1), totalDistance(o2));
        }

        public Set<DebugEntry> getDebugMap() {
            return debugMap;
        }
    }


    public Trip obtainClosestCoordenatesToOriginDestination(
            TimeCoordenates start, TimeCoordenates end) {
        Set<Trip> trips = new HashSet<>();
        topNLongestTripsBetweenInArea(start, end, getRoot(), trips);
        ClosestToStartAndEndComparator comparator = new ClosestToStartAndEndComparator(start, end);
        TreeSet<Trip> result = new TreeSet<>(comparator);
        result.addAll(trips);
        Set<ClosestToStartAndEndComparator.DebugEntry> debugSet = comparator.getDebugMap();
        return result.first();
    }


    public Set<Trip> topNLongestTripsBetweenInArea(TimeCoordenates start, TimeCoordenates end, int n) {
        Set<Trip> tmp = new HashSet<>();
        Set<Trip> result = new TreeSet<>(new Trip.TripRealDistanceComparator());
        topNLongestTripsBetweenInArea(start, end, getRoot(), tmp);
        for (Trip trip : tmp) {
            if (n == 0) {
                break;
            }
            result.add(trip);
            n--;
        }
        return result;
    }


    private void topNLongestTripsBetweenInArea(
            TimeCoordenates start, TimeCoordenates end, TwoDNode<Trip> root, Set<Trip> result) {

        if (root == null) {
            return;
        }

        if (isInRange(start, end, root)) {
            result.add(root.getElement());
        }

        topNLongestTripsBetweenInArea(start, end, root.getLeft(), result);
        topNLongestTripsBetweenInArea(start, end, root.getRight(), result);
    }


    private boolean isInRange(TimeCoordenates start, TimeCoordenates end, TwoDNode<Trip> root) {
        double x = root.getX();
        double y = root.getY();
        double x1 = Math.min(Double.parseDouble(start.getLatitude()), Double.parseDouble(end.getLatitude()));
        double x2 = Math.max(Double.parseDouble(start.getLatitude()), Double.parseDouble(end.getLatitude()));
        double y1 = Math.min(Double.parseDouble(start.getLongitude()), Double.parseDouble(end.getLongitude()));
        double y2 = Math.max(Double.parseDouble(start.getLongitude()), Double.parseDouble(end.getLongitude()));
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }


}
