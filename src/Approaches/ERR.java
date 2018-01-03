package Approaches;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class ERR {
    private HashSet<String> SAList = new HashSet<>();
    private LinkedHashMap<String,ArrayList<String>> SA_usersShapes = new LinkedHashMap<>();
    
    public ERR(String approachPath, String tagsOfSkillAreaPath) throws IOException{
        ReadClusteringFile(tagsOfSkillAreaPath);
        SaveUserShape(approachPath);
        calculateERR(approachPath);
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
        System.gc();
    }
    
    private void SaveUserShape(String approachPath) throws IOException{
        for(String SAItem: SAList){
            SaveUserShapeForTheSA(SAItem, approachPath);
        }
    }
    
    private void SaveUserShapeForTheSA(String skillArea, String approachPath) throws FileNotFoundException, IOException{
        String resultPath = approachPath+skillArea+"Users.csv";
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(resultPath)));
        ArrayList<String> shapesList = new ArrayList<>();
        String line = "";
        while((line = reader.readLine()) != null){
            if(!line.equals("UserId,Shape")){
                String shape = line.split(",")[1];
                shapesList.add(shape);
            }     
        }
        SA_usersShapes.put(skillArea,shapesList);
        reader.close();       
    }
    
    private void calculateERR(String approachPath) throws FileNotFoundException{
        PrintWriter writer = new PrintWriter(approachPath+"ERR.csv");
        writer.println("skillArea,ERR");
        int skillAreaCount = SAList.size();
        double sum = 0;
        for(String skillArea : SAList){
            double ERR = calculateERRForTheSA(skillArea);
            writer.println(skillArea + "," + ERR);
            sum += ERR;
        }
        double avgERR = sum / (double)skillAreaCount;
        writer.println("All,"+avgERR);
        writer.close();
    }
    
    private double calculateERRForTheSA(String skillArea){
        ArrayList<String> shapes = SA_usersShapes.get(skillArea);
        int n = shapes.size();
        int gMax = getGradeMax();
        double sum = 0;
        for(int r=1; r<=n; r++){
            if(shapes.get(r-1).equals("NonExpert"))
                continue;
            double MRR = (double)1 / (double)r;
            double production = calculateProductInERR(shapes,r,gMax);
            double Rr = calculateRi(getGrade(shapes.get(r-1)),gMax);
            sum += (MRR * production * Rr);
        }
        return sum;
    }
    
    private int getGradeMax(){
        String[] shapeTypes = {"T","C","NonExpert"};
        int max = -1;
        for(String shapeTypeItem: shapeTypes){
            int grade = getGrade(shapeTypeItem);
            if(grade > max)
                max = grade;
        }
        return max;
    }
        
    private int getGrade(String shape) {
        switch (shape) {
            case "T":
                return 2;
            case "C":
                return 1;
            default:
                return 0;
        }
    }
    
    private double calculateRi(int g, int gMax){
        double Ri = (Math.pow(2,g)-(double)1) / (Math.pow(2,gMax));
        return Ri;
    }
        
    private double calculateProductInERR(ArrayList<String> shapes, int r, int gMax){
        double production = 1;
        for(int i=1; i<=r-1; i++){
            int g = getGrade(shapes.get(i-1));
            double Ri = calculateRi(g,gMax);
            production *= (double)1 - Ri;
        }
        return production;
    }
}
