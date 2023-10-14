package pt.ipp.isep.esinf.domain;

import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class Trip implements Entity{
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
    public String getIndexKey() {
        return id.getId();
    }

    @Override
    public EntityType type() {
        return EntityType.TRIP;
    }
}
