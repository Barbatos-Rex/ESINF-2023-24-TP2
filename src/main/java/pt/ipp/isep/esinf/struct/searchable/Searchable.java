package pt.ipp.isep.esinf.struct.searchable;

import java.util.Optional;

public interface Searchable<S, I extends Comparable<I>> {


    Optional<S> search(I id);


}
