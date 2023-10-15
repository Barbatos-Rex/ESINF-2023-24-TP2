package pt.ipp.isep.esinf.struct.auxiliary;

import pt.ipp.isep.esinf.domain.trip.TimeCoordenates;
import pt.ipp.isep.esinf.domain.trip.Trip;

public class TripStartEnd {
    private Trip trip;


    public TripStartEnd(Trip trip) {
        this.trip = trip;
    }

    public Trip getTrip() {
        return trip;
    }


    public Pair<TimeCoordenates, TimeCoordenates> obtainStartAndEnd() {
        return new Pair<>(trip.getEntries().getFirst().getCoordenates(), trip.getEntries().last().getCoordenates());
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Trip: ").append(trip.getId().getId()).append("\n");
        Pair<TimeCoordenates, TimeCoordenates> coords = obtainStartAndEnd();
        sb.append("Start Coordenates: ").append(coords.getFirst()).append("\n");
        sb.append("End Coordenates: ").append(coords.getSecond());
        return sb.toString();
    }
}
