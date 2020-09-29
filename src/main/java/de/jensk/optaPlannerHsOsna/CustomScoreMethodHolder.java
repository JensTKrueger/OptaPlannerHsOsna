package de.jensk.optaPlannerHsOsna;

import java.util.List;


public class CustomScoreMethodHolder {

    public boolean checkForCommonGroup(Event event1, Event event2, Event event3){
        List<StudyGroup> groupList1 = event1.getStudyGroups();
        List<StudyGroup> groupList2 = event2.getStudyGroups();
        List<StudyGroup> groupList3 = event3.getStudyGroups();
        for(StudyGroup group1: groupList1){
            for(StudyGroup group2: groupList2){
                for(StudyGroup group3: groupList3){
                    if(group1.getSpecialId() == group2.getSpecialId() || (group1.getCohortId() == group2.getCohortId() && group1.getSpecialCatId() != group2.getSpecialCatId())){
                        if(group1.getSpecialId() == group3.getSpecialId() || (group1.getCohortId() == group3.getCohortId() && group1.getSpecialCatId() != group3.getSpecialCatId())){
                            return true;
                        }

                    }
                }
            }
        }
        return false;
    }

 public boolean test(Event event){
        System.out.println("TTTTTTTTTTTTT");
        return true;
 }
}
