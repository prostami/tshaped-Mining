
package Approaches;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class NDCG {
    private HashSet<String> SAList = new HashSet<>();
    private LinkedHashMap<String, LinkedHashMap<String, Integer>> realData = new LinkedHashMap<>();
    private TreeMap<String, Double> NDCGList = new TreeMap<>();
    private double NDCGAvg = 0;
    
    public NDCG(String approachPath, String tagsOfSkillAreaPath, String skillShapesXMLPath) throws IOException{
        ReadClusteringFile(tagsOfSkillAreaPath);
        CalculateRealData(skillShapesXMLPath);
        calculateAvgNDCG(approachPath);
        PrintNDCG(approachPath);
    }

    private void ReadClusteringFile(String tagsOfSkillAreaPath) throws FileNotFoundException, IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(tagsOfSkillAreaPath)));
        String line = "";
        while((line = reader.readLine()) != null){
            if(!line.equals("SkillArea,Tag")){
                String skillArea = line.split(",")[0];
                if(!SAList.contains(skillArea))
                    SAList.add(skillArea);
            }
        }
        reader.close();
    }
    
    private void CalculateRealData(String skillShapesXMLPath) throws FileNotFoundException, IOException{
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(skillShapesXMLPath)));
        String line = "";
        while ((line = reader.readLine()) != null){
            if (ExistExpression(line, "UserId")){
                for(String SkillArea : SAList){
                    String shape = CalculateShapeType(line, SkillArea);
                    AddShapeCountToRealData(SkillArea, shape);
                }
            }
        }
        reader.close();
    }
    
    private String CalculateShapeType(String line, String skillArea) throws FileNotFoundException, IOException{
        String shape = FindExpression(line, "Shape");
        boolean advancedLevel = ExistSkillAreaName(line, "Advanced", skillArea);
        if (advancedLevel)
            return shape;
        else
            return "NonExpert";
    }
    
    private boolean ExistSkillAreaName(String line, String levelName, String SA) {
        boolean existExpectedLevel = ExistExpression(line, levelName);
        if (existExpectedLevel)
            return Arrays.asList(FindExpression(line, levelName).split(",")).contains(SA);
        else
            return false;
    }
    
    private void AddShapeCountToRealData(String skillArea, String shape){
        if(realData.containsKey(skillArea)){
            AddShapeCountToRealData_isExistSkillArea(skillArea, shape);
        }else{
            AddShapeCountToRealData_isNotExistSkillArea(skillArea, shape);            
        }
    }
    
    private void AddShapeCountToRealData_isExistSkillArea(String skillArea, String shape){
        if(realData.get(skillArea).containsKey(shape)){
            LinkedHashMap<String, Integer> shape_count = realData.get(skillArea);
            int Count = shape_count.get(shape);
            Count++;
            shape_count.replace(shape, Count);
            realData.replace(skillArea, shape_count);
        }else{
            LinkedHashMap<String, Integer> shape_count = realData.get(skillArea);
            shape_count.put(shape, 1);
            realData.replace(skillArea, shape_count);
        }
    }
    
    private void AddShapeCountToRealData_isNotExistSkillArea(String skillArea, String shape){
        LinkedHashMap<String, Integer> shape_count = new LinkedHashMap<>();
        shape_count.put(shape, 1);
        realData.put(skillArea, shape_count);
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
    
    public void calculateAvgNDCG(String approachPath) throws FileNotFoundException, IOException{
        double NDCGSum = 0;
        for(String skillArea : SAList){
            double NDCG = calculateNDCG(skillArea, approachPath);
            NDCGList.put(skillArea, NDCG);
            NDCGSum += NDCG;
        }
        NDCGAvg = NDCGSum / SAList.size();     
    }

    private double calculateNDCG(String skillArea, String approachPath) throws FileNotFoundException, IOException {
        String resultPath = approachPath+skillArea+"Users.csv";
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(resultPath)));
        String line = "";
        double DCG = 0;
        int i = 1;
        while((line = reader.readLine()) != null){
            if(!line.equals("UserId,Shape")){
                String shape = line.split(",")[1];
                DCG += CalculateDCGInner(i, shape);
                i++;
            }     
        }
        reader.close();
        double IDCG = CalculateIDCG(skillArea);
        double NDCG = DCG / IDCG;
        return NDCG;
    }
    
    private double CalculateIDCG(String SkillArea){
        LinkedHashMap<String, Integer> shape_count = realData.get(SkillArea);
        int TCount = shape_count.get("T");
        int CCount = shape_count.get("C");
        int NonExpertCount = shape_count.get("NonExpert");
        double IDCGForTs = CalculateIDCGForShape("T",1,TCount);
        double IDCGForCs = CalculateIDCGForShape("C",TCount+1,TCount+CCount);
        double IDCGForNonExperts = CalculateIDCGForShape("NonExpert",TCount+CCount+1,TCount+CCount+NonExpertCount);
        return IDCGForTs + IDCGForCs + IDCGForNonExperts;      
    }
    
    private double CalculateIDCGForShape(String shape, int begin, int end){
        double sum = 0;
        for(int i = begin; i<=end; i++){
            sum += CalculateDCGInner(i,shape);
        }
        return sum;
    }
    
    private double CalculateDCGInner(int i, String shape){
        double rel_i = (double)(CalculateRel_i(shape));
        double oneDCG = (Math.pow(2, rel_i) - 1) / (Math.log10(i + 1) / Math.log10(2));
        return oneDCG;
    }
    
    private int CalculateRel_i(String shape) {
        switch (shape) {
            case "T":
                return 2;
            case "C":
                return 1;
            default:
                return 0;
        }
    }
     
    public void PrintNDCG(String approachPath) throws FileNotFoundException, IOException{
        PrintWriter writer = new PrintWriter(approachPath+"NDCG.csv");
        writer.println("skillArea,NDCG");
        for(Map.Entry<String, Double> SkillAreaItem : NDCGList.entrySet()){
            String skillArea = SkillAreaItem.getKey();
            double NDCG = SkillAreaItem.getValue();
            writer.println(skillArea +","+ NDCG);
        }
        writer.println("All,"+NDCGAvg);
        writer.close();
    }
}
