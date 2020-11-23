package com.baldede.postman.solver;

import com.baldede.postman.domain.Domicile;
import com.baldede.postman.domain.TourSolution;
import com.baldede.postman.domain.Standstill;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;

public class DomicileDistanceStandstillStrengthWeightFactory implements SelectionSorterWeightFactory<TourSolution, Standstill> {

    @Override
    public DomicileDistanceStandstillStrengthWeight createSorterWeight(TourSolution tspSolution, Standstill standstill) {
        Domicile domicile = tspSolution.getDomicile();
        long domicileRoundTripDistance = domicile.getDistanceTo(standstill) + standstill.getDistanceTo(domicile);
        return new DomicileDistanceStandstillStrengthWeight(standstill, domicileRoundTripDistance);
    }

    public static class DomicileDistanceStandstillStrengthWeight implements Comparable<DomicileDistanceStandstillStrengthWeight> {

        private final Standstill standstill;
        private final long domicileRoundTripDistance;

        public DomicileDistanceStandstillStrengthWeight(Standstill standstill, long domicileRoundTripDistance) {
            this.standstill = standstill;
            this.domicileRoundTripDistance = domicileRoundTripDistance;
        }

        @Override
        public int compareTo(DomicileDistanceStandstillStrengthWeight other) {
            return new CompareToBuilder()
                    .append(other.domicileRoundTripDistance, domicileRoundTripDistance) // Decreasing: closer to depot is stronger
                    .append(standstill.getLocation().getLatitude(), other.standstill.getLocation().getLatitude())
                    .append(standstill.getLocation().getLongitude(), other.standstill.getLocation().getLongitude())
                    .toComparison();
        }

    }
}


