package de.jensk.optaPlannerHsOsna;

/**
 * This class represents a single studygroup. A studygroup is specialisation in a specific
 * specialisation category in a specific cohort.
 */
public class StudyGroup {

    /**
     * The id of the specialisation of this studygroup.
     */
    private int specialId;

    /**
     * The id of the specialisation category of this studygroup.
     */
    private int specialCatId;

    /**
     * The id of the cohort of this studygroup.
     */
    private int cohortId;

    /**
     * This is the default constructor.
     */
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

    /**
     * Compares this studygroup to another studygroup. Because each specialId is unique and
     * cannot exist in multiple specialisation categories or cohorts it is sufficient to only
     * check whether the specialId of both studygroups are equal.
     * @param obj The studygroup object to compare this studygroup to.
     * @return Returns true if the studygroups are the same. Returns false if not.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StudyGroup) {
            StudyGroup other = (StudyGroup) obj;
            return other.specialId == specialId;
        } else {
            return false;
        }
    }


    /**
     * This constructor is only used for testing.
     */
    public StudyGroup(int specialId, int specialCatId, int cohortId) {
        this.specialId = specialId;
        this.specialCatId = specialCatId;
        this.cohortId = cohortId;
    }
}
