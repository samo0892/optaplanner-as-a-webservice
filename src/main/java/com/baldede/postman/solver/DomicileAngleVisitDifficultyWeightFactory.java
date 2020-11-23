package com.baldede.postman.solver;

import com.baldede.postman.domain.Domicile;
import com.baldede.postman.domain.TourSolution;
import com.baldede.postman.domain.Stop;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.impl.heuristic.selector.common.decorator.SelectionSorterWeightFactory;

/**
 * On large datasets, the constructed solution looks like pizza slices.
 */
public class DomicileAngleVisitDifficultyWeightFactory implements SelectionSorterWeightFactory<TourSolution, Stop> {

    @Override
    public DomicileAngleVisitDifficultyWeight createSorterWeight(TourSolution vehicleRoutingSolution, Stop visit) {
        Domicile domicile = vehicleRoutingSolution.getDomicile();
        return new DomicileAngleVisitDifficultyWeight(visit,
                visit.getLocation().getAngle(domicile.getLocation()),
                visit.getLocation().getDistanceTo(domicile.getLocation())
                        + domicile.getLocation().getDistanceTo(visit.getLocation()));
    }

    public static class DomicileAngleVisitDifficultyWeight
            implements Comparable<DomicileAngleVisitDifficultyWeight> {

        private final Stop visit;
        private final double domicileAngle;
        private final long domicileRoundTripDistance;

        public DomicileAngleVisitDifficultyWeight(Stop visit,
                                                  double domicileAngle, long domicileRoundTripDistance) {
            this.visit = visit;
            this.domicileAngle = domicileAngle;
            this.domicileRoundTripDistance = domicileRoundTripDistance;
        }

        @Override
        public int compareTo(DomicileAngleVisitDifficultyWeight other) {
            return new CompareToBuilder()
                    .append(domicileAngle, other.domicileAngle)
                    .append(domicileRoundTripDistance, other.domicileRoundTripDistance) // Ascending (further from the depot are more difficult)
                    .append(visit.getId(), other.visit.getId())
                    .toComparison();
        }

    }

}
