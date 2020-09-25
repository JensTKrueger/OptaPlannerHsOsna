package de.jensk.optaPlannerHsOsna;

import java.util.List;

public class StudyGroup {
    private int specialId;
    private int specialCatId;
    private int cohortId;

    public int getSpecialId() {
        return specialId;
    }

    public void setSpecialId(int specialId) {
        this.specialId = specialId;
    }

    public int getSpecialCatId() {
        return specialCatId;
    }

    public void setSpecialCatId(int specialCatId) {
        this.specialCatId = specialCatId;
    }

    public int getCohortId() {
        return cohortId;
    }

    public void setCohortId(int cohortId) {
        this.cohortId = cohortId;
    }
}
