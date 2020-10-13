package de.jensk.optaPlannerHsOsna;

import java.util.List;

public class StudyGroup {
    private int specialId;
    private int specialCatId;
    private int cohortId;


    public StudyGroup() {
    }

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

    @Override
    public String toString() {
        return "StudyGroup{" +
                "specialId=" + specialId +
                ", specialCatId=" + specialCatId +
                ", cohortId=" + cohortId +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof StudyGroup){
            StudyGroup other = (StudyGroup) obj;
            if(other.cohortId == cohortId && other.specialCatId == specialCatId && other.specialId == specialId){
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    //this costructor is only used in JUnit tests
    public StudyGroup(int specialId, int specialCatId, int cohortId) {
        this.specialId = specialId;
        this.specialCatId = specialCatId;
        this.cohortId = cohortId;
    }
}
