
package GoldenSet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.LinkedHashMap;

public class AnswersDataSet {
    private String dataSetName;
    private LinkedHashMap<Integer,String[]> QId_Properties = new LinkedHashMap<>();
    public AnswersDataSet(String dataSetName, String postFileAddress, String QuestionDataSetPath, String AnswerDataSetPath) throws FileNotFoundException, IOException {
        SetParameters(dataSetName);
        readQuestionsDataSet(QuestionDataSetPath);
        WriteAnswersDataSet(postFileAddress,AnswerDataSetPath);
        
    }

    private void SetParameters(String dataSetName) {
        this.dataSetName = dataSetName;
    }

    private void readQuestionsDataSet(String QuestionDataSetPath) throws FileNotFoundException, IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(QuestionDataSetPath)));
        String line = "";
        while ((line = reader.readLine()) != null) {
            if (ExistExpression(line, "Id")) {
                int QId = Integer.parseInt(FindExpression(line, "Id"));
                String AcceptedAnswerId = FindExpression(line, "AcceptedAnswerId");
                String SkillAreas = FindExpression(line, "SkillArea");
                String[] properties = { AcceptedAnswerId, SkillAreas };
                QId_Properties.put(QId, properties);
            }
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
    
    private void WriteAnswersDataSet(String postFileAddress, String AnswerDataSetPath) throws FileNotFoundException, IOException {
        PrintWriter writer = new PrintWriter(AnswerDataSetPath);
        writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        writer.println("<posts>");
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(postFileAddress)));
        String line = "";
        int count = 24120523;
        while((line = reader.readLine()) != null){
            if (ExistExpression(line, "Id")){
                count--;
                printCounter(count);
                int PostTypeId = Integer.parseInt(FindExpression(line, "PostTypeId"));
                int OwnerUserId = intTryParse(line, "OwnerUserId");
                if (PostTypeId == 2 && OwnerUserId != -1){//If_Answer_and_OwnerUserId_is_yet_exist_in_SOF                  
                    int parentId = Integer.parseInt(FindExpression(line, "ParentId"));
                    if (QId_Properties.containsKey(parentId)){//if_Answer_be_a_Answer_from_the_Question
                        int AId = Integer.parseInt(FindExpression(line, "Id"));
                        int AcceptedAnswer = BooleanAcceptedAnswer(QId_Properties.get(parentId)[0]);
                        String SkillAreas = QId_Properties.get(parentId)[1];
                        writer.println("<row Id=\"" + AId + "\" OwnerUserId=\"" + OwnerUserId + "\" ParentId=\"" + parentId + "\" AcceptedAnswer=\"" + AcceptedAnswer + "\" SkillArea=\"" + SkillAreas + "\" >");
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
    
    private int BooleanAcceptedAnswer(String Str){
        if (Integer.parseInt(Str) == -1)
            return 0;
        else
            return 1;
    }
}
