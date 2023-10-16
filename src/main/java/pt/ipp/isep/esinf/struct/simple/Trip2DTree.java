package pt.ipp.isep.esinf.struct.simple;

import pt.ipp.isep.esinf.domain.trip.TimeCoordenates;
import pt.ipp.isep.esinf.domain.trip.Trip;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Trip2DTree extends TwoDTree<Trip> {
//    public Pair<Pair<Double, Double>, Pair<Double, Double>> obtainClosestCoordenatesToOriginDestination(
//            TimeCoordenates start, TimeCoordenates end) {
//
//
//    }


    public Set<Trip> topNLongestTripsBetweenInArea(TimeCoordenates start, TimeCoordenates end, int n) {
        Set<Trip> tmp = new HashSet<>();
        Set<Trip> result = new TreeSet<>(new Trip.TripRealDistanceComparator());
        topNLongestTripsBetweenInArea(start, end, getRoot(), tmp);
        for (Trip trip : tmp) {
            if (n==0){
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
