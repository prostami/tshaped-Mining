package GoldenSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class QuestionsDataSet {
    private String dataSetName;
    private LinkedHashMap<String,String> tag_skillArea = new LinkedHashMap<>();

    QuestionsDataSet(String dataSetName, String postFileAddress, String SkillAreasPath, String QuestionDataSetPath) throws FileNotFoundException, IOException {
        SetParameters(dataSetName,SkillAreasPath);
        WriteQuestionsDataSet(postFileAddress,QuestionDataSetPath);
    }
    
    private void SetParameters(String dataSetName,String SkillAreasPath) throws FileNotFoundException, IOException{
        this.dataSetName = dataSetName;
        setTag_skillAreaList(SkillAreasPath);
    }
    
    private void setTag_skillAreaList(String SkillAreasPath) throws FileNotFoundException, IOException{
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(SkillAreasPath)));
        String line = "";
        while((line=reader.readLine())!=null){
            if(!line.equals("SkillArea,Tag")){
                String tag = line.split(",")[1];
                String skillArea = line.split(",")[0];
                tag_skillArea.put(tag, skillArea);
            }
        }
        reader.close();
    }
    
    private void WriteQuestionsDataSet(String postFileAddress,String QuestionDataSetPath) throws FileNotFoundException, IOException{
        PrintWriter writer = new PrintWriter(QuestionDataSetPath);
        writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        writer.println("<posts>");
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(postFileAddress)));
        String line = "";
        int count = 24120523;
        while((line = reader.readLine())!= null){
            if(ExistExpression(line, "Id")){
                count--;
                printCounter(count);
                int PostTypeId = Integer.parseInt(FindExpression(line, "PostTypeId"));
                if(PostTypeId == 1){//is_Question
                    String[] Tags = ConvertHtmlToPlainTxt(FindExpression(line, "Tags"));
                    if(ExistDataSetTag(Tags) && ExistInTagList(Tags)){
                        int QId = Integer.parseInt(FindExpression(line, "Id"));
                        int AcceptedAnswerId = intTryParse(line, "AcceptedAnswerId");
                        int OwnerUserId = intTryParse(line, "OwnerUserId");
                        String SkillArea = getSkillAreasStringFromQuestion(Tags);
                        writer.println("<row Id=\"" + QId + "\" OwnerUserId=\"" + OwnerUserId + "\" AcceptedAnswerId=\"" + AcceptedAnswerId + "\" SkillArea=\"" + SkillArea + "\" >");
                    }
                }
            }
        }
        writer.println("</posts>");
        writer.close();
        reader.close();
        System.out.println("Successful!");
    }
    
    private void printCounter(int count){
        if(count%100000==0)
            System.out.println(count);
    }
    
    private String[] ConvertHtmlToPlainTxt(String line){
        String []tags = line.substring(4, line.length()-4).split("&gt;&lt;");
        return tags;
    }
    
    private boolean ExistDataSetTag(String[] tags) {
        return Arrays.asList(tags).contains(dataSetName.toLowerCase());
    }
    
    private boolean ExistInTagList(String []tags){
        boolean exist = false;
        for(String tag : tags){
            if (tag_skillArea.containsKey(tag)){
                exist = true;
                break;
            }
        }
        return exist;
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

    private int intTryParse(String line, String field) {
        int n;
        String fieldStr = FindExpression(line, field);
        try {
            n = Integer.parseInt(fieldStr);
        } catch (NumberFormatException e) {
            n = -1;
        }
        return n;
    }

    private String getSkillAreasStringFromQuestion(String[] Tags) {
        HashSet<String> SkillAreaTemp = getSkillAreasListFromQuestion(Tags);
        String SkillAreaFinal = "";
        int i = 0;
        for(String skillArea : SkillAreaTemp){
            if(i == 0)
                SkillAreaFinal += skillArea;
            else
                SkillAreaFinal += "," + skillArea;
            i++;
        }
        return SkillAreaFinal;
    }
    
    private HashSet<String> getSkillAreasListFromQuestion(String[] Tags){
        HashSet<String> SkillAreaTemp = new HashSet<>();
        for(String tag : Tags){
            if(tag_skillArea.containsKey(tag)){
                String SkillArea = tag_skillArea.get(tag);
                if(!SkillAreaTemp.contains(SkillArea))
                    SkillAreaTemp.add(SkillArea);
            }
        }
        return SkillAreaTemp;
    }
    
}
