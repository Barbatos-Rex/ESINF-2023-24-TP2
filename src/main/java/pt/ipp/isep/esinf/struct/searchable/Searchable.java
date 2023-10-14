package pt.ipp.isep.esinf.struct;

import java.util.Optional;

public interface Serchable<S, I extends Comparable<I>> {


    Optional<S> search(I id);


}
