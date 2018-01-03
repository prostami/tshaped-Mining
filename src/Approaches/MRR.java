package Approaches;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.HashSet;

public class MRR {
    private HashSet<String> SAList = new HashSet<>();
    
    public MRR(String approachPath, String tagsOfSkillAreaPath) throws IOException{
        ReadClusteringFile(tagsOfSkillAreaPath);
        calculateAndPrintMRR(approachPath);
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
    
    private void calculateAndPrintMRR(String approachPath) throws IOException {
        PrintWriter writer = new PrintWriter(approachPath+"MRR.csv");
        writer.println("skillArea,MRR");
        double sum = 0;
        for(String skillArea : SAList){
            double MRR = calculateMRR(skillArea, approachPath);
            sum += MRR;
            writer.println(skillArea + "," + MRR);
        }
        double avg = sum / (double)SAList.size();
        writer.println("All,"+avg);
        writer.close();
        SAList.clear();
    }
    
    private double calculateMRR(String skillArea, String approachPath) throws IOException{
        String resultPath = approachPath+skillArea+"Users.csv";
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(resultPath)));
        String line = "";
        double MRR = 0;
        int count = 0;
        while((line = reader.readLine()) != null){
            String shape = line.split(",")[1];
            if(shape.equals("T")){
                MRR = 1.0 / (double)count;
                break;
            }
            count++;
        }
        reader.close();
        return MRR;
    }
    
}
