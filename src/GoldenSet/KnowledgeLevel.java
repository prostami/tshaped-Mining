package GoldenSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

class KnowledgeLevel {

    private String dataSetName;
    private int BinsCount;
    private double alpha;
    private int AdvancedMeter;
    private int IntermediateMeter;
    private LinkedHashMap<String, Integer> SkillArea_AnswerCount = new LinkedHashMap<>();
    private LinkedHashMap<String, Integer> SkillArea_AcceptedAnswerCount = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap<Integer, Integer>> SkillArea__UId_AnswerCount = new LinkedHashMap<>();
    private LinkedHashMap<String, LinkedHashMap<Integer, Integer>> SkillArea__UId_AcceptedAnswerCount = new LinkedHashMap<>();
    private TreeMap<Integer,TreeMap<String, String>> UId__KnowledgeLevel_SkillAreas = new TreeMap<>();
    private LinkedHashMap<String, String> UIdSA_properties = new LinkedHashMap<>();

    public KnowledgeLevel(String dataSetName, String AnswerDataSetPath, String userPerformanceXMLPath
                        , String expertiseShapesXMLPath, String expertiseShapeCSVPath, String skillAreasCSVPath
                        , String AdvancedLevelCSVPath, String IntermediateLevelCSVPath,String BeginnerLevelCSVPath
                        , int BinsCount, double alpha, int AdvancedMeter, int IntermediateMeter) throws IOException {
        setParameters(dataSetName, BinsCount, alpha, AdvancedMeter, IntermediateMeter);
        calculateAcceptedAnswersCount(AnswerDataSetPath);
        System.out.println("1");
        calculateKnowledgeLevel();
        System.out.println("2");
        generateFmeasureAndOtherProperties(userPerformanceXMLPath);
        System.out.println("3");
        generateExpertiseShapes(expertiseShapesXMLPath);
        System.out.println("4");
        generateCSVFile(expertiseShapesXMLPath, skillAreasCSVPath, expertiseShapeCSVPath, AdvancedLevelCSVPath, IntermediateLevelCSVPath, BeginnerLevelCSVPath);
        System.out.println("5");
    }

    private void setParameters(String dataSetName, int BinsCount, double alpha, int AdvancedMeter, int IntermediateMeter) {
        this.dataSetName = dataSetName;
        this.BinsCount = BinsCount;
        this.alpha = alpha;
        this.AdvancedMeter = AdvancedMeter;
        this.IntermediateMeter = IntermediateMeter;
    }

    private void calculateAcceptedAnswersCount(String AnswerDataSetPath) throws FileNotFoundException, IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(AnswerDataSetPath)));
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (ExistExpression(line, "Id")) 
                calculateAcceptedAnswerCountFromEachLine(line);
        }
        reader.close();
    }

    private static String FindExpression(String Str, String expression) {
        String result = "";
        int startBodyx = Str.indexOf(expression + "=\"");
        if (startBodyx != (-1)) {
            int startBody = startBodyx + (expression.length() + 2);
            int endBody = Str.substring(startBody).indexOf("\"");

            result = Str.substring(startBody, startBody + endBody);
        }
        return result;
    }

    private static boolean ExistExpression(String Str, String expression) {
        int startBodyx = Str.indexOf(expression + "=\"");
        return startBodyx != (-1);
    }
    
    private void calculateAcceptedAnswerCountFromEachLine(String line){
        int AcceptedAnswer = Integer.parseInt(FindExpression(line, "AcceptedAnswer"));
        int OwnerUserId = Integer.parseInt(FindExpression(line, "OwnerUserId"));
        String SkillAreas = FindExpression(line, "SkillArea");
        for (String skillArea : SkillAreas.split(",")) {
            if (AcceptedAnswer == 1){
                CalculateAcceptedAnswerCountForTheSA(skillArea);
                CalculateAcceptedAnswerCountForTheUserInTheSA(skillArea, OwnerUserId);
            }
            CalculateAnswerCountForTheSA(skillArea);
            CalculateAnswerCountForTheUserInTheSA(skillArea, OwnerUserId);
        }
    }

    private void CalculateAnswerCountForTheSA(String skillArea) {
        if (SkillArea_AnswerCount.containsKey(skillArea)) {
            int answerCount = SkillArea_AnswerCount.get(skillArea);
            answerCount++;
            SkillArea_AnswerCount.replace(skillArea, answerCount);
        } else
            SkillArea_AnswerCount.put(skillArea, 1);
    }

    private void CalculateAcceptedAnswerCountForTheSA(String skillArea) {
        if (SkillArea_AcceptedAnswerCount.containsKey(skillArea)) {
            int accpetedAnswerCount = SkillArea_AcceptedAnswerCount.get(skillArea);
            accpetedAnswerCount++;
            SkillArea_AcceptedAnswerCount.replace(skillArea, accpetedAnswerCount);
        }else 
            SkillArea_AcceptedAnswerCount.put(skillArea, 1);
    }

    private void CalculateAnswerCountForTheUserInTheSA(String skillArea, int OwnerUserId) {
        if (SkillArea__UId_AnswerCount.containsKey(skillArea)) {
            LinkedHashMap<Integer, Integer> UId_AnswerCount = SkillArea__UId_AnswerCount.get(skillArea);
            if (UId_AnswerCount.containsKey(OwnerUserId)) {
                int AnswerCount = UId_AnswerCount.get(OwnerUserId);
                AnswerCount++;
                UId_AnswerCount.replace(OwnerUserId, AnswerCount);
            }else 
                UId_AnswerCount.put(OwnerUserId, 1);
            SkillArea__UId_AnswerCount.replace(skillArea, UId_AnswerCount);
        }else{
            LinkedHashMap<Integer, Integer> UId_AnswerCount = new LinkedHashMap<>();
            UId_AnswerCount.put(OwnerUserId, 1);
            SkillArea__UId_AnswerCount.put(skillArea, UId_AnswerCount);
        }
    }

    private void CalculateAcceptedAnswerCountForTheUserInTheSA(String skillArea, int OwnerUserId) {
        if (SkillArea__UId_AcceptedAnswerCount.containsKey(skillArea)) {
            LinkedHashMap<Integer, Integer> UId_AcceptedAnswerCount = SkillArea__UId_AcceptedAnswerCount.get(skillArea);
            if (UId_AcceptedAnswerCount.containsKey(OwnerUserId)) {
                int AcceptedAnswerCount = UId_AcceptedAnswerCount.get(OwnerUserId);
                AcceptedAnswerCount++;
                UId_AcceptedAnswerCount.replace(OwnerUserId, AcceptedAnswerCount);
            } else 
                UId_AcceptedAnswerCount.put(OwnerUserId, 1);
            SkillArea__UId_AcceptedAnswerCount.replace(skillArea, UId_AcceptedAnswerCount);
        } else {
            LinkedHashMap<Integer, Integer> UId_AcceptedAnswerCount = new LinkedHashMap<>();
            UId_AcceptedAnswerCount.put(OwnerUserId, 1);
            SkillArea__UId_AcceptedAnswerCount.put(skillArea, UId_AcceptedAnswerCount);
        }
    }
    
    private void calculateKnowledgeLevel() {
        for (String skillArea : SkillArea_AcceptedAnswerCount.keySet()) {
            LinkedHashMap<Integer, String> userKnowLedgeLevelInTheSA = calculateKnowledgeLevelInTheSA(skillArea);
            for (Map.Entry<Integer, String> userItem : userKnowLedgeLevelInTheSA.entrySet()) {
                int userId = userItem.getKey();
                String knowledgeLevel = userItem.getValue();
                addKnowledgeLevelForTheUserOfTheSA(skillArea,userId,knowledgeLevel);
            }
        }
    }

    private void addKnowledgeLevelForTheUserOfTheSA(String skillArea,int userId,String knowledgeLevel){
        if (UId__KnowledgeLevel_SkillAreas.containsKey(userId)) {
            TreeMap<String, String> knowledgeLevel_skillAreas = UId__KnowledgeLevel_SkillAreas.get(userId);
            if (knowledgeLevel_skillAreas.containsKey(knowledgeLevel)) {
                String PreviousSkillAreas = knowledgeLevel_skillAreas.get(knowledgeLevel);
                knowledgeLevel_skillAreas.replace(knowledgeLevel, PreviousSkillAreas + "," + skillArea);
            }else 
                knowledgeLevel_skillAreas.put(knowledgeLevel, skillArea);
            UId__KnowledgeLevel_SkillAreas.replace(userId, knowledgeLevel_skillAreas);
        }else{
            TreeMap<String, String> knowledgeLevel_skillAreas = new TreeMap<>();
            knowledgeLevel_skillAreas.put(knowledgeLevel, skillArea);
            UId__KnowledgeLevel_SkillAreas.put(userId, knowledgeLevel_skillAreas);
        }
    }
    
    private LinkedHashMap<Integer, String> calculateKnowledgeLevelInTheSA(String skillArea) {
        LinkedHashMap<Integer, String> User_KnowledgeLevel = new LinkedHashMap<>();
        LinkedHashMap<Integer, HashSet<Integer>> BinNumber_UserList = CreateUserBins(skillArea);
        for (Map.Entry<Integer,HashSet<Integer>> item : BinNumber_UserList.entrySet()) {
            int Bin = item.getKey();
            HashSet<Integer> UIdList = item.getValue();
            if (Bin <= AdvancedMeter)
                for (int userId : UIdList) {
                    User_KnowledgeLevel.put(userId, "Advanced");
                }
            else if (Bin <= IntermediateMeter)
                for (int userId : UIdList) {
                    User_KnowledgeLevel.put(userId, "Intermediate");
                }
            else
                for (int userId : UIdList) {
                    User_KnowledgeLevel.put(userId, "Beginner");
                }
        }
        return User_KnowledgeLevel;
    }

    private LinkedHashMap<Integer, HashSet<Integer>> CreateUserBins(String skillArea) {
        LinkedHashMap<Integer, HashSet<Integer>> BinNumber_UserList = new LinkedHashMap<>();
        LinkedHashMap<Integer, Double> UId_fmeasure = getUsersBasedOnSortedFmeasure(skillArea);
        int count = 1, Bin = 1;
        int ListSize = UId_fmeasure.size();
        int BinSize = (int) (Math.floor((double) ListSize / (double) BinsCount));
        for (Map.Entry<Integer, Double> UserItem : UId_fmeasure.entrySet()) {
            int userId = UserItem.getKey();
            int BinRand = (Bin == BinsCount ? ListSize : Bin * BinSize);
            if (count <= BinRand) {
                if (BinNumber_UserList.containsKey(Bin)) {
                    HashSet<Integer> usersList = BinNumber_UserList.get(Bin);
                    usersList.add(userId);
                    BinNumber_UserList.replace(Bin, usersList);
                } else {
                    HashSet<Integer> usersList = new HashSet<>();
                    usersList.add(userId);
                    BinNumber_UserList.put(Bin, usersList);
                }
                count++;
            } else
                Bin++;
        }
        return BinNumber_UserList;
    }

    private LinkedHashMap<Integer, Double> getUsersBasedOnSortedFmeasure(String skillArea) {
        LinkedHashMap<Integer, Double> UId_Fmeasure = new LinkedHashMap<>();
        TreeMap<Double, HashSet<Integer>> Fmeasure_UIdList = getSortedFmeasureForUsersList(skillArea);
        for (Map.Entry<Double, HashSet<Integer>> fmeasureItem : Fmeasure_UIdList.entrySet()) {
            double Fmeasure = fmeasureItem.getKey();
            HashSet<Integer> UIdList = fmeasureItem.getValue();
            for (int UId : UIdList) {
                UId_Fmeasure.put(UId, Fmeasure);
            }
        }
        return UId_Fmeasure;
    }

    private TreeMap<Double, HashSet<Integer>> getSortedFmeasureForUsersList(String skillArea) {
        TreeMap<Double, HashSet<Integer>> Fmeasure_UIdList = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Integer, Integer> UserItem : SkillArea__UId_AcceptedAnswerCount.get(skillArea).entrySet()) {
            int userId = UserItem.getKey();
            int acceptedAnswerCount = UserItem.getValue();
            int answerCount = SkillArea__UId_AcceptedAnswerCount.get(skillArea).get(userId);
            int SAAcceptedAnswerCount = SkillArea_AcceptedAnswerCount.get(skillArea);
            double precision = (double) acceptedAnswerCount / (double) answerCount;
            double recall = (double) acceptedAnswerCount / (double) SAAcceptedAnswerCount;
            double NormalizedRecall = recall / getMaxRecall(skillArea);
            double fmeasure = (double)1 / ((alpha / precision) + (((double)1 - alpha) / NormalizedRecall));
            if (Fmeasure_UIdList.containsKey(fmeasure)) {
                HashSet<Integer> UIdList = Fmeasure_UIdList.get(fmeasure);
                UIdList.add(userId);
                Fmeasure_UIdList.replace(fmeasure, UIdList);
            } else {
                HashSet<Integer> UIdList = new HashSet<>();
                UIdList.add(userId);
                Fmeasure_UIdList.put(fmeasure, UIdList);
            }
        }
        return Fmeasure_UIdList;
    }

    private double getMaxRecall(String skillArea) {
        double MaxRecall = -100;
        for (Map.Entry<Integer, Integer> userItem : SkillArea__UId_AcceptedAnswerCount.get(skillArea).entrySet()) {
            int userId = userItem.getKey();
            int AcceptedAnswerCount = userItem.getValue();
            int SAAcceptedAnswerCount = SkillArea_AcceptedAnswerCount.get(skillArea);
            double Recall = (double) AcceptedAnswerCount / (double) SAAcceptedAnswerCount;
            if (Recall > MaxRecall) {
                MaxRecall = Recall;
            }
        }
        return MaxRecall;
    }

    private void generateFmeasureAndOtherProperties(String userPerformancePath) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(userPerformancePath);
        writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        writer.println("<posts>");
        LinkedHashMap<String, LinkedHashMap<Integer, Double>> skillArea__UId_Fmeasure = getFmeasureForAllUsersInAllSAs();
        LinkedHashMap<String, Double> skillArea_MaxRecall = GetMaxRecallForAllSAs();
        for(Map.Entry<Integer, TreeMap<String, String>> UserItem : UId__KnowledgeLevel_SkillAreas.entrySet()) {
            int userId = UserItem.getKey();
            TreeMap<String, String> KnowledgeLevel_SkillArea = UserItem.getValue();
            generateFmeasureAndOtherPropertiesForTheUser(writer,userId, KnowledgeLevel_SkillArea.values(),skillArea__UId_Fmeasure,skillArea_MaxRecall);
        }
        writer.println("</posts>");
        writer.close();
    }
    
    private LinkedHashMap<String, LinkedHashMap<Integer, Double>> getFmeasureForAllUsersInAllSAs() {
        LinkedHashMap<String, LinkedHashMap<Integer, Double>> skillArea__UId_Fmeasure = new LinkedHashMap<>();
        for (String skillArea : SkillArea_AcceptedAnswerCount.keySet()) {
            LinkedHashMap<Integer, Double> UId_Fmeasure = getUsersBasedOnSortedFmeasure(skillArea);
            skillArea__UId_Fmeasure.put(skillArea, UId_Fmeasure);
        }
        return skillArea__UId_Fmeasure;
    }

    private LinkedHashMap<String, Double> GetMaxRecallForAllSAs() {
        LinkedHashMap<String, Double> skillArea_MaxRecall = new LinkedHashMap<>();
        for (String skillArea : SkillArea_AcceptedAnswerCount.keySet()) {
            double maxRecall = getMaxRecall(skillArea);
            skillArea_MaxRecall.put(skillArea, maxRecall);
        }
        return skillArea_MaxRecall;
    }

    private void generateFmeasureAndOtherPropertiesForTheUser(PrintWriter writer, int userId, Collection<String> SAListOfEachKnowledgeLevel, LinkedHashMap<String, LinkedHashMap<Integer, Double>> skillArea__UId_Fmeasure, LinkedHashMap<String, Double> skillArea_MaxRecall){
        for (String SAList : SAListOfEachKnowledgeLevel) {
            for (String skillArea : SAList.split(",")) {
                int acceptedAnswerCount = SkillArea__UId_AcceptedAnswerCount.get(skillArea).get(userId);
                int answerCount = SkillArea__UId_AnswerCount.get(skillArea).get(userId);
                int acceptedAnswerCountOfSA = SkillArea_AcceptedAnswerCount.get(skillArea);
                double Fmeasure = skillArea__UId_Fmeasure.get(skillArea).get(userId);
                double precision = (double) acceptedAnswerCount / (double) answerCount;
                double recall = (double) acceptedAnswerCount / (double) acceptedAnswerCountOfSA;
                double normalizedRecall = recall / skillArea_MaxRecall.get(skillArea);
                String FormatedPrecision =(precision==1?"1":String.format("%11.10f", precision));
                String FormatedRecall = (normalizedRecall==1?"1":String.format("%11.10f", normalizedRecall));
                String FormatedFmeasure =(Fmeasure==1?"1":String.format("%11.10f", Fmeasure));
                writer.println("<row UserId=\"" + userId + "\" SkillArea=\"" + skillArea + "\" Fmeasure=\"" + FormatedFmeasure + "\" Precision=\"" + FormatedPrecision + "\" Recall=\"" + FormatedRecall + "\" AnswerCount=\"" + answerCount + "\" AcceptedAnswerCount=\"" + acceptedAnswerCount + "\" >");
                UIdSA_properties.put((userId + "," + skillArea), (FormatedFmeasure + "," + FormatedPrecision + "," + FormatedRecall + "," + answerCount + "," + acceptedAnswerCount));
            }
        }
    }
        
    private void generateExpertiseShapes(String expertiseShapesPath) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(expertiseShapesPath);
        writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        writer.println("<posts>");
        for (Map.Entry<Integer, TreeMap<String, String>> userItem : UId__KnowledgeLevel_SkillAreas.entrySet()) {
            int UserId = userItem.getKey();
            TreeMap<String, String> KnowledgeLevel_skillArea = userItem.getValue();
            int AdvancedSAsCount = getKnowledgeLevelSAsCount(KnowledgeLevel_skillArea, "Advanced");
            String AdvancedSAs = getKnowledgeLevelSAs(KnowledgeLevel_skillArea, "Advanced");
            String IntermediateSAs = getKnowledgeLevelSAs(KnowledgeLevel_skillArea, "Intermediate");
            String BeginnerSAs = getKnowledgeLevelSAs(KnowledgeLevel_skillArea, "Beginner");
            String Shape = recognizeShape(AdvancedSAsCount);
            writeExpertiseShapesForTheUser(writer,UserId,Shape,AdvancedSAs,IntermediateSAs,BeginnerSAs);
        }
        writer.println("</posts>");
        writer.close();
    }
    
    private int getKnowledgeLevelSAsCount(TreeMap<String, String> KnowledgeLevel_SkillArea, String KnowledgeLevelName) {
        int SACount = 0;
        if (KnowledgeLevel_SkillArea.containsKey(KnowledgeLevelName)) {
            SACount = KnowledgeLevel_SkillArea.get(KnowledgeLevelName).split(",").length;
        }
        return SACount;
    }

    private String getKnowledgeLevelSAs(TreeMap<String, String> KnowledgeLevel_SkillArea, String KnowledgeLevelName) {
        String SAs = "";
        if (KnowledgeLevel_SkillArea.containsKey(KnowledgeLevelName)) {
            SAs = KnowledgeLevel_SkillArea.get(KnowledgeLevelName);
        }
        return SAs;
    }

    private String recognizeShape(int AdvancedSAsCount) {
        String shape = "";
        if (AdvancedSAsCount == 0)
            shape = "NonExpert";
        else if (AdvancedSAsCount == 1)
            shape = "T";
        else if (AdvancedSAsCount >= 2)
            shape = "C";
        return shape;
    }

    private void writeExpertiseShapesForTheUser(PrintWriter writer, int UserId, String Shape, String AdvancedSAs, String IntermediateSAs, String BeginnerSAs){
        writer.print("< UserId=\"" + UserId + "\" Shape=\"" + Shape + "\"");
        if (!AdvancedSAs.equals("")) 
            writer.print(" Advanced=\"" + AdvancedSAs + "\"");
        if (!IntermediateSAs.equals(""))
            writer.print(" Intermediate=\"" + IntermediateSAs + "\"");
        if (!BeginnerSAs.equals(""))
            writer.print(" Beginner=\"" + BeginnerSAs + "\"");
        writer.println(" >");
    }
        
    private void generateCSVFile(String expertiseShapesXMLPath, String skillAreasCSVPath, String expertiseShapeCSVPath, String AdvancedLevelCSVPath, String IntermediateLevelCSVPath, String BeginnerLevelCSVPath) throws FileNotFoundException, IOException {
        generateSkillAreasProperties(skillAreasCSVPath);
        generateKnowledgeLevelFiles(expertiseShapesXMLPath, expertiseShapeCSVPath, AdvancedLevelCSVPath, IntermediateLevelCSVPath, BeginnerLevelCSVPath);
    }

    private void generateSkillAreasProperties(String skillAreasCSVPath) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(skillAreasCSVPath);
        writer.println("SkillArea,AnswerCount,AcceptedAnswerCount");
        for (Map.Entry<String, Integer> SAItem : SkillArea_AnswerCount.entrySet()) {
            String SkillArea = SAItem.getKey();
            int AnswerCount = SAItem.getValue();
            int AcceptedAnswerCount = SkillArea_AcceptedAnswerCount.get(SkillArea);
            writer.println(SkillArea + "," + AnswerCount + "," + AcceptedAnswerCount);
        }
        writer.close();
    }

    private void generateKnowledgeLevelFiles(String expertiseShapesXMLPath, String expertiseShapeCSVPath, String AdvancedLevelCSVPath, String IntermediateLevelCSVPath, String BeginnerLevelCSVPath) throws FileNotFoundException, IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(expertiseShapesXMLPath)));
        PrintWriter ShapesWriter = new PrintWriter(expertiseShapeCSVPath);
        PrintWriter AdvancedLevelWriter = new PrintWriter(AdvancedLevelCSVPath);
        PrintWriter IntermediateLevelWriter = new PrintWriter(IntermediateLevelCSVPath);
        PrintWriter BeginnerLevelWriter = new PrintWriter(BeginnerLevelCSVPath);
        WriteFilesTitle(ShapesWriter, AdvancedLevelWriter, IntermediateLevelWriter, BeginnerLevelWriter);
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (ExistExpression(line, "UserId")) {
                writeEachLine(line, ShapesWriter, AdvancedLevelWriter, IntermediateLevelWriter, BeginnerLevelWriter);
            }
        }
        ShapesWriter.close();
        AdvancedLevelWriter.close();
        IntermediateLevelWriter.close();
        BeginnerLevelWriter.close();
    }

    private void WriteFilesTitle(PrintWriter ShapesWriter, PrintWriter AdvancedLevelWriter, PrintWriter IntermediateLevelWriter, PrintWriter BeginnerLevelWriter) {
        ShapesWriter.println("UserID,Shape");
        AdvancedLevelWriter.println("UserId,AdvancedSkillArea,Fmeasure,Precision,Recall,AnswerCount,AcceptedAnswerCount");
        IntermediateLevelWriter.println("UserId,IntermediateSkillArea,Fmeasure,Precision,Recall,AnswerCount,AcceptedAnswerCount");
        BeginnerLevelWriter.println("UserId,BeginnerSkillArea,Fmeasure,Precision,Recall,AnswerCount,AcceptedAnswerCount");
    }

    private void writeEachLine(String line, PrintWriter ShapesWriter, PrintWriter AdvancedLevelWriter, PrintWriter IntermediateLevelWriter, PrintWriter BeginnerLevelWriter) {
        writeShapeForTheUser(line, ShapesWriter);
        writeKnowledgeLevelForTheUser(line, "Advanced", AdvancedLevelWriter);
        writeKnowledgeLevelForTheUser(line, "Intermediate", IntermediateLevelWriter);
        writeKnowledgeLevelForTheUser(line, "Beginner", BeginnerLevelWriter);
    }

    private void writeShapeForTheUser(String line, PrintWriter ShapesWriter) {
        int UserId = Integer.parseInt(FindExpression(line, "UserId"));
        String Shape = FindExpression(line, "Shape");
        ShapesWriter.println(UserId + "," + Shape);
    }

    private void writeKnowledgeLevelForTheUser(String line, String KnowledgeLevelName, PrintWriter KnowledgeLevelWriter) {
        if (ExistExpression(line, KnowledgeLevelName)) {
            int UserId = Integer.parseInt(FindExpression(line, "UserId"));
            String KnowledgeLevel = FindExpression(line, KnowledgeLevelName);
            String[] KnowledgeLevelSAList = KnowledgeLevel.split(",");
            for (String skillArea : KnowledgeLevelSAList) {
                String[] Performance = UIdSA_properties.get(UserId + "," + skillArea).split(",");
                KnowledgeLevelWriter.println(UserId + "," + skillArea + "," + Performance[0] + "," + Performance[1]+","+ Performance[2] + "," + Performance[3]+","+Performance[4]);
            }
        }
    }
}
