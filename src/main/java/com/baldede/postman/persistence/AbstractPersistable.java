package com.baldede.postman.persistence;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.optaplanner.core.api.domain.lookup.PlanningId;
import org.optaplanner.core.api.score.constraint.ConstraintMatch;

import java.io.Serializable;

public abstract class AbstractPersistable implements Serializable, Comparable<AbstractPersistable> {

    protected Long id;

    protected AbstractPersistable() {
    }

    protected AbstractPersistable(long id) {
        this.id = id;
    }

    @PlanningId
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Used by the GUI to sort the {@link ConstraintMatch} list
     * by {@link ConstraintMatch#getJustificationList()}.
     * @param other never null
     * @return comparison
     */
    @Override
    public int compareTo(AbstractPersistable other) {
        return new CompareToBuilder()
                .append(getClass().getName(), other.getClass().getName())
                .append(id, other.id)
                .toComparison();
    }

    @Override
    public String toString() {
        return getClass().getName().replaceAll(".*\\.", "") + "-" + id;
    }
}
