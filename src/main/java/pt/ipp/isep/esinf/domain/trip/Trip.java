package pt.ipp.isep.esinf.domain.trip;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Trip {
    private TripId id;

    private Set<TripEntry> entries;

    public Trip(TripId id, Set<TripEntry> entries) {
        this.id = id;
        this.entries = entries;
    }

    public Trip(TripId id) {
        this.id = id;
        entries = new TreeSet<>();
    }

    public TripId getId() {
        return id;
    }

    public Set<TripEntry> getEntries() {
        return entries;
    }

    public void addEntry(TripEntry entry) {
        entries.add(entry);
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
}
