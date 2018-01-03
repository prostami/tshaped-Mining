package Approaches;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;

public class EBA {

    private String EBAFolderPath;
    private TreeMap<String, ArrayList<String>> SA_TagsList = new TreeMap<>();
    private LinkedHashMap<String, String> tag_SA = new LinkedHashMap<>();
    private LinkedHashMap<Integer, ArrayList<String>> QId_SAList = new LinkedHashMap<>();
    private LinkedHashMap<Integer, LinkedHashMap<String, Integer>> UId_DeAndDsaCount = new LinkedHashMap<>();
    private LinkedHashMap<Integer, Integer> UId_DeCount = new LinkedHashMap<>();
    private LinkedHashMap<Integer, LinkedHashMap<String, Double>> UId__SA_PsaAnde = new LinkedHashMap<>();
    private LinkedHashMap<Integer, Double> UId_He = new LinkedHashMap<>();
    private LinkedHashMap<Integer, Double> UId_ProbilityTGivenExpert = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap<Integer,Double>> SA__UId_ProbilityTandRGivenExpertAndSA = new LinkedHashMap<>();

    public EBA(String EBAFolderPath, LinkedHashMap<String, LinkedHashMap<Integer, Double>> SA__UId_ProbilityRGivenExpertAndSA,
            LinkedHashMap<String, LinkedHashMap<Integer, String>> SA__UId_GoldenShapes, String tagsOfSkillAreaPath, 
            String QuestionsIndexPath, String AnswersIndexPath) throws IOException, ParseException {

        setParameters(EBAFolderPath);
        calculateProbilityTGivenExpert(tagsOfSkillAreaPath, QuestionsIndexPath, AnswersIndexPath);
        calculateProbilityTandRGivenExpertAndSA(SA__UId_ProbilityRGivenExpertAndSA);
        generateExpertiseShapeLabelForSortedUsers(SA__UId_GoldenShapes);
    }

    private void setParameters(String EBAFolderPath) {
        this.EBAFolderPath = EBAFolderPath;
    }

    private void calculateProbilityTGivenExpert(String tagsOfSkillAreaPath, String QuestionsIndexPath, String AnswersIndexPath) throws IOException, ParseException {
        getTagsOfSkillArea(tagsOfSkillAreaPath);
        findSAsOfQuestions(new File(QuestionsIndexPath).toPath());
        CalculateDsaAnde(new File(AnswersIndexPath).toPath());
        calculateDe();
        calculatePsaAnde();
        calculateHe();
        calculateNormalizedProbilityTGivenExpert();
    }

    private void getTagsOfSkillArea(String tagsOfSkillAreaPath) throws FileNotFoundException, IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(tagsOfSkillAreaPath)));
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (!line.equals("SkillArea,Tag")) {
                String skillArea = line.split(",")[0];
                String tag = line.split(",")[1];
                tag_SA.put(tag, skillArea);
                addTagToSA(skillArea, tag);
            }
        }
        reader.close();
    }

    private void addTagToSA(String skillArea, String tag) {
        if (SA_TagsList.containsKey(skillArea)) {
            ArrayList<String> tagsList = SA_TagsList.get(skillArea);
            tagsList.add(tag);
            SA_TagsList.replace(skillArea, tagsList);
        } else {
            ArrayList<String> tagsList = new ArrayList<>();
            tagsList.add(tag);
            SA_TagsList.put(skillArea, tagsList);
        }
    }

    private void findSAsOfQuestions(Path QuestionsIndexPath) throws IOException, ParseException {
        Searcher searcher = new Searcher(QuestionsIndexPath, "Tags");
        TopDocs hits = searcher.querySearch("*:*", Integer.MAX_VALUE);
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            int QId = Integer.parseInt(doc.getField("Id").stringValue());
            String[] tags = doc.getValues("Tags");
            ArrayList<String> skillAreaList = getSAsFromTags(tags);
            if (!skillAreaList.isEmpty()) {
                QId_SAList.put(QId, skillAreaList);
            }
        }
    }

    private ArrayList<String> getSAsFromTags(String[] tags) {
        ArrayList<String> SAList = new ArrayList<>();
        for (String tag : tags) {
            if (tag_SA.containsKey(tag)) {
                String SA = tag_SA.get(tag);
                if (!SAList.contains(SA)) {
                    SAList.add(SA);
                }
            }
        }
        return SAList;
    }

    private void CalculateDsaAnde(Path answersIndexPath) throws IOException, ParseException {
        Searcher searcher = new Searcher(answersIndexPath, "Id");
        TopDocs hits = searcher.querySearch("*:*", Integer.MAX_VALUE);
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = searcher.getDocument(scoreDoc);
            int parantId = Integer.parseInt(doc.getField("ParentId").stringValue());
            int userId = Integer.parseInt(doc.getField("OwnerUserId").stringValue());
            if (QId_SAList.containsKey(parantId) && userId != -1) {
                addToDsaAndeList(userId, QId_SAList.get(parantId));
            }
        }
    }

    private void addToDsaAndeList(int userId, ArrayList<String> SAList) {
        for (String skillArea : SAList) {
            if (UId_DeAndDsaCount.containsKey(userId)) {
                addToDsaAndeList_isExistTheUser(userId, skillArea);
            } else {
                addToDsaAndeList_isNotExistTheUser(userId, skillArea);
            }
        }
    }

    private void addToDsaAndeList_isExistTheUser(int userId, String skillArea) {
        LinkedHashMap<String, Integer> skillArea_AnswerCount = UId_DeAndDsaCount.get(userId);
        if (skillArea_AnswerCount.containsKey(skillArea)) {
            int answerCount = skillArea_AnswerCount.get(skillArea);
            answerCount++;
            skillArea_AnswerCount.replace(skillArea, answerCount);
        } else {
            skillArea_AnswerCount.put(skillArea, 1);
        }
        UId_DeAndDsaCount.replace(userId, skillArea_AnswerCount);
    }

    private void addToDsaAndeList_isNotExistTheUser(int userId, String skillArea) {
        LinkedHashMap<String, Integer> skillArea_AnswerCount = new LinkedHashMap<>();
        skillArea_AnswerCount.put(skillArea, 1);
        UId_DeAndDsaCount.put(userId, skillArea_AnswerCount);
    }

    private void calculateDe() {
        for (Map.Entry<Integer, LinkedHashMap<String, Integer>> userItem : UId_DeAndDsaCount.entrySet()) {
            int userId = userItem.getKey();
            int sumAnswerCount = 0;
            LinkedHashMap<String, Integer> SkillArea_AnswerCount = userItem.getValue();
            for (Map.Entry<String, Integer> SAItem : SkillArea_AnswerCount.entrySet()) {
                int count = SAItem.getValue();
                sumAnswerCount += count;
            }
            UId_DeCount.put(userId, sumAnswerCount);
        }
    }

    private void calculatePsaAnde() {
        for (Map.Entry<Integer, LinkedHashMap<String, Integer>> userItem : UId_DeAndDsaCount.entrySet()) {
            LinkedHashMap<String, Double> SA_PsaAnde = new LinkedHashMap<>();
            int userId = userItem.getKey();
            LinkedHashMap<String, Integer> SA_AnswerCount = userItem.getValue();
            for (Map.Entry<String, Integer> SAItem : SA_AnswerCount.entrySet()) {
                String skillArea = SAItem.getKey();
                int count = SAItem.getValue();
                double PsaAnde = (double) count / (double) UId_DeCount.get(userId);
                SA_PsaAnde.put(skillArea, PsaAnde);
            }
            UId__SA_PsaAnde.put(userId, SA_PsaAnde);
        }
        UId_DeAndDsaCount.clear();
    }

    private void calculateHe() {
        for (Map.Entry<Integer, LinkedHashMap<String, Double>> userItem : UId__SA_PsaAnde.entrySet()) {
            int userId = userItem.getKey();
            LinkedHashMap<String, Double> SA_PsaAnde = userItem.getValue();
            double sumHe = 0;
            for (Map.Entry<String, Double> SAItem : SA_PsaAnde.entrySet()) {
                String skillArea = SAItem.getKey();
                double PsaAnde = SAItem.getValue();
                double uncertainty = -PsaAnde * ((Math.log10(PsaAnde)) / (Math.log10(2)));
                sumHe += uncertainty;
            }
            UId_He.put(userId, sumHe);
        }
    }

    private void calculateNormalizedProbilityTGivenExpert() {
        double maxProbilityTGivenExpert = calculateProbilityTGivenExpert();
        for (Map.Entry<Integer, Double> userItem : UId_ProbilityTGivenExpert.entrySet()) {
            int userId = userItem.getKey();
            double ProbilityTGivenExpert = userItem.getValue();
            double NormalizedProbilityTGivenExpert = ProbilityTGivenExpert / maxProbilityTGivenExpert;
            UId_ProbilityTGivenExpert.replace(userId, NormalizedProbilityTGivenExpert);
        }
    }

    private double calculateProbilityTGivenExpert() {
        double max = -1000;
        for (Map.Entry<Integer, Double> userItem : UId_He.entrySet()) {
            int userId = userItem.getKey();
            double He = userItem.getValue();
            double De = UId_DeCount.get(userId);
            double logDe = Math.log10(De) / Math.log10(2);
            double ProbilityTGivenExpert = logDe / (He + 0.01);
            if (ProbilityTGivenExpert > max) {
                max = ProbilityTGivenExpert;
            }
            UId_ProbilityTGivenExpert.put(userId, ProbilityTGivenExpert);
        }
        return max;
    }

    private void calculateProbilityTandRGivenExpertAndSA(LinkedHashMap<String, LinkedHashMap<Integer, Double>> SA__UId_ProbilityRGivenExpertAndSA) {
        for(Map.Entry<String, LinkedHashMap<Integer,Double>> SAItem: SA__UId_ProbilityRGivenExpertAndSA.entrySet()){
            String skillArea = SAItem.getKey();
            LinkedHashMap<Integer,Double> userId_ProbilityRGivenExpertAndSA = SAItem.getValue();
            LinkedHashMap<Integer,Double> userId_ProbilityTandRGivenExpertAndSA = new LinkedHashMap<>();
            for(Map.Entry<Integer,Double> userItem :userId_ProbilityRGivenExpertAndSA.entrySet()){
                int userId = userItem.getKey();
                double probilityRGivenExpertAndSA = userItem.getValue();
                double probilityTandRGivenExpertAndSA =  probilityRGivenExpertAndSA * 
                                                        (UId_ProbilityTGivenExpert.containsKey(userId)? 
                                                         UId_ProbilityTGivenExpert.get(userId):0.001);
                userId_ProbilityTandRGivenExpertAndSA.put(userId, probilityTandRGivenExpertAndSA);
            }
            LinkedHashMap<Integer, Double> SortedUId_ProbilityTandRGivenExpertAndSA = sortHashMap(userId_ProbilityTandRGivenExpertAndSA);
            SA__UId_ProbilityTandRGivenExpertAndSA.put(skillArea, SortedUId_ProbilityTandRGivenExpertAndSA);
        }
       SA__UId_ProbilityRGivenExpertAndSA.clear();
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
        for(Map.Entry<String ,LinkedHashMap<Integer,Double>> SAItem : SA__UId_ProbilityTandRGivenExpertAndSA.entrySet()){
            String SkillArea = SAItem.getKey();
            PrintWriter writer = new PrintWriter(EBAFolderPath+SkillArea+"Users.csv");
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
        return SA__UId_ProbilityTandRGivenExpertAndSA;
    }

    public LinkedHashMap<Integer, LinkedHashMap<String, Double>> getPsaAndeList(){
        return UId__SA_PsaAnde;
    }
}
