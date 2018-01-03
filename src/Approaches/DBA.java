package Approaches;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.Map;

public class DBA {
    private String DBAFolderPath;

    public DBA(String DBAFolderPath, LinkedHashMap<String, LinkedHashMap<Integer, Double>> SA__UId_ProbilityExpertGivenSA, LinkedHashMap<String, LinkedHashMap<Integer, String>> SA__UId_GoldenShapes) throws FileNotFoundException {
        setParameters(DBAFolderPath);
        generateExpertiseShapeLabelForSortedUsers(SA__UId_ProbilityExpertGivenSA,SA__UId_GoldenShapes);
    }

    private void setParameters(String DBAFolderPath) {
        this.DBAFolderPath = DBAFolderPath;
    }

    private void generateExpertiseShapeLabelForSortedUsers(LinkedHashMap<String, LinkedHashMap<Integer, Double>> SA__UId_ProbilityExpertGivenSA, LinkedHashMap<String, LinkedHashMap<Integer, String>> SA__UId_GoldenShapes) throws FileNotFoundException {
        for(Map.Entry<String ,LinkedHashMap<Integer,Double>> SAItem : SA__UId_ProbilityExpertGivenSA.entrySet()){
            String SkillArea = SAItem.getKey();
            PrintWriter writer = new PrintWriter(DBAFolderPath+SkillArea+"Users.csv");
            writer.println("UserId,Shape");
            LinkedHashMap<Integer,Double> UId_Probility = SAItem.getValue();
            for(Map.Entry<Integer,Double> UserItem : UId_Probility.entrySet()){
                int userId = UserItem.getKey();
                String Shape = SA__UId_GoldenShapes.get(SkillArea).containsKey(userId)?SA__UId_GoldenShapes.get(SkillArea).get(userId):"NonExpert";
                writer.println(userId + "," + Shape);
            }
            writer.close();
        }
    }
    
}
