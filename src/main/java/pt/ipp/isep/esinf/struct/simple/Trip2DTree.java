package pt.ipp.isep.esinf.struct.simple;

import pt.ipp.isep.esinf.domain.trip.TimeCoordenates;
import pt.ipp.isep.esinf.domain.trip.Trip;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Trip2DTree extends TwoDTree<Trip> {


    /**
     * Complexity: O(1) + O(n) + O(1) + O(n) * O(log(n)) = O(nlog(n))
     * Complexity in debug mode: O(nlog^2(n))
     *
     * @param start
     * @param end
     * @return
     */
    public Trip obtainClosestCoordenatesToOriginDestination(
            TimeCoordenates start, TimeCoordenates end) {
        //O(1)
        Set<Trip> trips = new HashSet<>();
        //O(n)
        topNLongestTripsBetweenInArea(start, end, getRoot(), trips);
        //O(1)
        ClosestToStartAndEndComparator comparator = new ClosestToStartAndEndComparator(start, end);
        TreeSet<Trip> result = new TreeSet<>(comparator);
        //O(n) * O(log(n))
        result.addAll(trips);
        if (Boolean.parseBoolean(System.getProperty("Debug", "false"))) {
            Set<ClosestToStartAndEndComparator.DebugEntry> debugSet = comparator.getDebugMap();
            System.out.println("DEBUG SET:");
            debugSet.forEach(System.out::println);
            System.out.println();
        }
        return result.first();
    }

    /**
     * Complexity: O(1) + O(n) + O(n)*O(log(n)) = O(nlog(n))
     *
     * @param start
     * @param end
     * @param n
     * @return
     */
    public Set<Trip> topNLongestTripsBetweenInArea(TimeCoordenates start, TimeCoordenates end, int n) {
        //O(1)
        Set<Trip> tmp = new HashSet<>();
        Set<Trip> result = new TreeSet<>(new Trip.TripRealDistanceComparator());
        //O(n)
        topNLongestTripsBetweenInArea(start, end, getRoot(), tmp);
        for (Trip trip : tmp) {//O(n)
            if (n == 0) {
                break;
            }
            //O(log(n))
            result.add(trip);
            n--;
        }
        return result;
    }

    //O(n) * (O(1) + O(1) * O(1)) = O(n)
    private void topNLongestTripsBetweenInArea(
            TimeCoordenates start, TimeCoordenates end, TwoDNode<Trip> root, Set<Trip> result) {

        //O(1)
        if (root == null) {
            return;
        }
        //O(1) * O(1)
        if (isInRange(start, end, root)) {
            result.add(root.getElement());
        }

        //O(n) call
        topNLongestTripsBetweenInArea(start, end, root.getLeft(), result);
        topNLongestTripsBetweenInArea(start, end, root.getRight(), result);
    }

    //O(1)
    private boolean isInRange(TimeCoordenates start, TimeCoordenates end, TwoDNode<Trip> root) {
        double x = root.getX();
        double y = root.getY();
        double x1 = Math.min(Double.parseDouble(start.getLatitude()), Double.parseDouble(end.getLatitude()));
        double x2 = Math.max(Double.parseDouble(start.getLatitude()), Double.parseDouble(end.getLatitude()));
        double y1 = Math.min(Double.parseDouble(start.getLongitude()), Double.parseDouble(end.getLongitude()));
        double y2 = Math.max(Double.parseDouble(start.getLongitude()), Double.parseDouble(end.getLongitude()));
        return x >= x1 && x <= x2 && y >= y1 && y <= y2;
    }

    private class ClosestToStartAndEndComparator implements Comparator<Trip> {


        private final TimeCoordenates start;
        private final TimeCoordenates end;
        private final Set<DebugEntry> debugMap = new TreeSet<>(new Comparator<DebugEntry>() {
            @Override
            public int compare(DebugEntry o1, DebugEntry o2) {
                return Double.compare(o1.val, o2.val);
            }
        });

        public ClosestToStartAndEndComparator(TimeCoordenates start, TimeCoordenates end) {
            this.start = start;
            this.end = end;
        }

        private double totalDistance(Trip trip) {
            double d = start.distance(trip.getEntries().first().getCoordenates()) + end.distance(trip.getEntries().last().getCoordenates());
            if (Boolean.parseBoolean(System.getProperty("Debug", "false"))) {
                debugMap.add(new DebugEntry(trip, d));
            }
            return d;
        }

        @Override
        public int compare(Trip o1, Trip o2) {
            return Double.compare(totalDistance(o1), totalDistance(o2));
        }

        public Set<DebugEntry> getDebugMap() {
            return debugMap;
        }

        private class DebugEntry {
            private final Trip trip;
            private final double val;

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
    }


}
