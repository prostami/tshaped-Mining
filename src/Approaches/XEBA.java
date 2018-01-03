package Approaches;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

class XEBA {
    private String XEBAFolderPath;
    private LinkedHashMap<Integer, LinkedHashMap<String,Double>> UId__SA_ProbilitySAGivenExpertAndT = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap<Integer,Double>> SA__UId_ProbilityRandTGivenExpertAndSA = new LinkedHashMap<>();
    
    public XEBA(String XEBAFolderPath, LinkedHashMap<Integer,LinkedHashMap<String,Double>> UId__SA_PsaAnde, LinkedHashMap<String, LinkedHashMap<Integer, String>> SA__UId_GoldenShapes, LinkedHashMap<String,LinkedHashMap<Integer,Double>> SA__UId_EBA) throws FileNotFoundException{
        setParameters(XEBAFolderPath);
        calculateProbilitySAGivenExpertAndT(UId__SA_PsaAnde);
        calculateProbilityTandRGivenExpertAndSA(SA__UId_EBA);
        generateExpertiseShapeLabelForSortedUsers(SA__UId_GoldenShapes);
    }
    
    private void setParameters(String XEBAFolderPath){
        this.XEBAFolderPath = XEBAFolderPath;
    }
           
    private void calculateProbilitySAGivenExpertAndT(LinkedHashMap<Integer,LinkedHashMap<String,Double>> UId__SA_PsaAnde){
        for(Map.Entry<Integer,LinkedHashMap<String,Double>> userItem: UId__SA_PsaAnde.entrySet()){
            int userId = userItem.getKey();
            LinkedHashMap<String,Double> SA_PsaAnde = userItem.getValue();
            LinkedHashMap<String,Double> SA_ProbilitySAGivenExpertAndT = new LinkedHashMap<>();
            double maxPsaAnde = maxPsaAndeForEachUserId(SA_PsaAnde);
            for(Map.Entry<String,Double> SAItem : SA_PsaAnde.entrySet()){
                String skillArea = SAItem.getKey();
                double PsaAnde = SAItem.getValue();
                double ProbilitySAGivenExpertAndT = PsaAnde / maxPsaAnde;
                SA_ProbilitySAGivenExpertAndT.put(skillArea,ProbilitySAGivenExpertAndT);
            }
            UId__SA_ProbilitySAGivenExpertAndT.put(userId, SA_ProbilitySAGivenExpertAndT);
        }
    }
    
    private double maxPsaAndeForEachUserId(LinkedHashMap<String,Double> SA_PsaAnde){
        double max = -1000;
        for(double PsaAnde : SA_PsaAnde.values()){
            if(PsaAnde > max)
                max = PsaAnde;
        }
        return max;
    }

    private void calculateProbilityTandRGivenExpertAndSA(LinkedHashMap<String, LinkedHashMap<Integer, Double>> SA__UId_EBA) {
        for(Map.Entry<String, LinkedHashMap<Integer,Double>> SAItem: SA__UId_EBA.entrySet()){
            String skillArea = SAItem.getKey();
            LinkedHashMap<Integer,Double> userId_EBA= SAItem.getValue();
            LinkedHashMap<Integer,Double> userId_ProbilityTandRGivenExpertAndSA= new LinkedHashMap<>();
            for(Map.Entry<Integer,Double> userItem :userId_EBA.entrySet()){
                int userId = userItem.getKey();
                double EBA = userItem.getValue();
                double ProbilityTandRGivenExpertAndSA =  EBA * ((UId__SA_ProbilitySAGivenExpertAndT.containsKey(userId) && 
                                                                 UId__SA_ProbilitySAGivenExpertAndT.get(userId).containsKey(skillArea))?
                                                                 UId__SA_ProbilitySAGivenExpertAndT.get(userId).get(skillArea) : 0.001);
                userId_ProbilityTandRGivenExpertAndSA.put(userId, ProbilityTandRGivenExpertAndSA);
            }
            LinkedHashMap<Integer, Double> SortedUId_ProbilityTandRGivenExpertAndSA = sortHashMap(userId_ProbilityTandRGivenExpertAndSA);
            SA__UId_ProbilityRandTGivenExpertAndSA.put(skillArea, SortedUId_ProbilityTandRGivenExpertAndSA);
        }
       UId__SA_ProbilitySAGivenExpertAndT.clear();
    }
    
    private LinkedHashMap<Integer, Double> sortHashMap(LinkedHashMap<Integer, Double> UId_ProbilityTandRGivenExpertAndSA) {
        LinkedHashMap<Integer,Double> UId_Probility = new LinkedHashMap<>();
        TreeMap<Double, ArrayList<Integer>> probility_UserList = getUserListGroupBySortedProbility(UId_ProbilityTandRGivenExpertAndSA);
        for (Map.Entry<Double, ArrayList<Integer>> item : probility_UserList.entrySet()){
            double probilityValue = item.getKey();
            ArrayList<Integer> userList = item.getValue();
            for (int userId : userList){
                UId_Probility.put(userId, probilityValue);
            }
        }
        probility_UserList.clear();
        return UId_Probility;
    }
    
    private TreeMap<Double, ArrayList<Integer>> getUserListGroupBySortedProbility(LinkedHashMap<Integer, Double> UId_ProbilityTandRGivenExpertAndSA){
        TreeMap<Double, ArrayList<Integer>> probility_UserList = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Integer, Double> userItem : UId_ProbilityTandRGivenExpertAndSA.entrySet()){
            int userId = userItem.getKey();
            double probilityValue = userItem.getValue();
            if (probility_UserList.containsKey(probilityValue)){
                ArrayList<Integer> UsersIdList = probility_UserList.get(probilityValue);
                UsersIdList.add(userId);
                probility_UserList.replace(probilityValue, UsersIdList);
            }
            else{
                ArrayList<Integer> UsersIdList = new ArrayList<>();
                UsersIdList.add(userId);
                probility_UserList.put(probilityValue, UsersIdList);
            }
        }
        return probility_UserList;
    }

    private void generateExpertiseShapeLabelForSortedUsers(LinkedHashMap<String, LinkedHashMap<Integer, String>> SA__UId_GoldenShapes) throws FileNotFoundException {
        for(Map.Entry<String ,LinkedHashMap<Integer,Double>> SAItem : SA__UId_ProbilityRandTGivenExpertAndSA.entrySet()){
            String SkillArea = SAItem.getKey();
            PrintWriter writer = new PrintWriter(XEBAFolderPath+SkillArea+"Users.csv");
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
    
    public LinkedHashMap<String, LinkedHashMap<Integer, Double>> getProbilityTAndRList(){
        return SA__UId_ProbilityRandTGivenExpertAndSA;
    }
}
