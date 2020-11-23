package com.baldede.postman.solver.score;

//import com.baldede.postman.domain.Domicile;
import com.baldede.postman.domain.Domicile;
import com.baldede.postman.domain.Standstill;
import com.baldede.postman.domain.Stop;
import com.baldede.postman.domain.TourSolution;
import org.optaplanner.core.api.score.buildin.simplelong.SimpleLongScore;
import org.optaplanner.core.impl.score.director.easy.EasyScoreCalculator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TourOptimizingEasyScoreCalculator implements EasyScoreCalculator<TourSolution> {

    @Override
    public SimpleLongScore calculateScore(TourSolution tourSolution) {
        List<Stop> visitList = tourSolution.getStopList();
        Set<Stop> tailVisitSet = new HashSet<>(visitList);
        long score = 0L;
        for (Stop visit : visitList) {
            Standstill previousStandstill = visit.getPreviousStandstill();
            if (previousStandstill != null) {
                score -= visit.getDistanceFromPreviousStandstill();
                if (previousStandstill instanceof Stop) {
                    tailVisitSet.remove(previousStandstill);
                }
            }
        }
        Domicile domicile = tourSolution.getDomicile();
        for (Stop tailVisit : tailVisitSet) {
            if (tailVisit.getPreviousStandstill() != null) {
                score -= tailVisit.getDistanceTo(domicile);
            }
        }
        return SimpleLongScore.of(score);
    }
}
