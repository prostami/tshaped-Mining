package Approaches;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.apache.lucene.queryparser.classic.ParseException;

public class AllRelevanceProbility {
    private String lambda;
    private String dataSetName;
    private Path answersIndexPathFormat;
    private TreeMap<String, ArrayList<String>> SkillArea_TagsList = new TreeMap<>();
    private LinkedHashMap<String,LinkedHashMap<Integer,Double>> SA__UId_ProbilityExpertGivenSA = new LinkedHashMap<>();
    
    public AllRelevanceProbility(String answersIndexPath, String dataSetName, String lambda, String skillAreasPath) throws IOException, ParseException{
        setParameters(answersIndexPath,dataSetName,lambda);
        getSkillAreaTagsFile(skillAreasPath);
        CalculateProbilityExpertGivenSAForAll();
    }

    private void setParameters(String answersIndexPath, String dataSetName, String lambda) {
        this.lambda = lambda;
        this.dataSetName = dataSetName;
        this.answersIndexPathFormat = new File(answersIndexPath).toPath();
    }

    private void getSkillAreaTagsFile(String skillAreasPath) throws FileNotFoundException, IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(skillAreasPath)));
        String line = "";
        while((line = reader.readLine())!= null){
            if(!line.equals("SkillArea,Tag")){
                String skillArea = line.split(",")[0];
                String tag = line.split(",")[1];
                addSkillAreaTagsToList(skillArea, tag);
            }
        }
        reader.close();
    }

    private void addSkillAreaTagsToList(String skillArea, String tag) {
        if(SkillArea_TagsList.containsKey(skillArea)){
            ArrayList<String> tagsList = SkillArea_TagsList.get(skillArea);
            tagsList.add(tag);
            SkillArea_TagsList.replace(skillArea, tagsList);
        }else{
            ArrayList<String> tagsList = new ArrayList<>();
            tagsList.add(tag);
            SkillArea_TagsList.put(skillArea, tagsList);
        }
    }

    private void CalculateProbilityExpertGivenSAForAll() throws IOException, ParseException {
        for(Map.Entry<String, ArrayList<String>> item : SkillArea_TagsList.entrySet()){
            String skillArea = item.getKey();
            ArrayList<String> tags = item.getValue();
            Balog B = new Balog(answersIndexPathFormat, tags, dataSetName, findLambdaType(), findLambdaValue());
            SA__UId_ProbilityExpertGivenSA.put(skillArea, B.getSortedUId_probilitySAGivenExpert());
            System.out.println("*******"+skillArea+"*******");
            System.gc();
        }
    }
    
    private String findLambdaType(){
        return (!lambda.equals(""))?"constant":"nonConstant";
    }
    
    private double findLambdaValue(){
        return (findLambdaType().equals("constant"))? (Double.parseDouble(lambda)):-1;
    }
    
    public LinkedHashMap<String,LinkedHashMap<Integer,Double>> getProbilitiesList(){
        return SA__UId_ProbilityExpertGivenSA;
    }
}
