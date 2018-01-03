package Approaches;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.TreeSet;

public class GoldenUsersShapes {
    LinkedHashMap<String,LinkedHashMap<Integer,String>> SA__UId_Shape = new LinkedHashMap<>();
    TreeSet<String> SAList = new TreeSet<>();
    
    public GoldenUsersShapes(String SkillAreasPath,String SkillShapesXMLPath) throws FileNotFoundException, IOException{
        getSAList(SkillAreasPath);
        getGoldenUsersShapes(SkillShapesXMLPath);
    }
    
    private void getSAList(String SkillAreasPath) throws IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(SkillAreasPath)));
        String line = "";
        while((line = reader.readLine())!= null){
            if(!line.equals("SkillArea,Tag")){
                String skillArea = line.split(",")[0];
                if(!SAList.contains(skillArea))
                    SAList.add(skillArea);
            }
        }
        reader.close();
    }
        
    private void getGoldenUsersShapes(String SkillShapesXMLPath) throws FileNotFoundException, IOException{
        for(String skillArea : SAList){
            LineNumberReader reader = new LineNumberReader(new FileReader(new File(SkillShapesXMLPath)));
            String line = "";
            while((line = reader.readLine()) != null){
                if(ExistExpression(line, "UserId")){
                    int userId = Integer.parseInt(FindExpression(line, "UserId"));
                    String shape = FindExpression(line, "Shape");
                    boolean advancedLevel = ExistSkillAreaName(line, "Advanced", skillArea);
                    if (advancedLevel)
                        addUserAndItsShape(skillArea,userId,shape);
                    else
                        addUserAndItsShape(skillArea,userId,"NonExpert");
                }
            }
            reader.close();
        }
    }
    
    private static String FindExpression(String Str, String expression) {
	String result = "";
	int startBodyx = Str.indexOf(expression + "=\"");
	if(startBodyx != (-1)){
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
    
    private boolean ExistSkillAreaName(String line, String levelName, String SA) {
	boolean existExpectedLevel = ExistExpression(line, levelName);
	if (existExpectedLevel)
            return Arrays.asList(FindExpression(line, levelName).split(",")).contains(SA);
	else
            return false;
    }
    
    private void addUserAndItsShape(String skillArea, int userId, String shape) {
        if(SA__UId_Shape.containsKey(skillArea)){
            LinkedHashMap<Integer,String> UId_Shape = SA__UId_Shape.get(skillArea);
            UId_Shape.put(userId, shape);
            SA__UId_Shape.replace(skillArea, UId_Shape);
        }else{
            LinkedHashMap<Integer,String> UId_Shape = new LinkedHashMap<>();
            UId_Shape.put(userId, shape);
            SA__UId_Shape.put(skillArea, UId_Shape);
        }
    }
    
    public LinkedHashMap<String,LinkedHashMap<Integer,String>> getGoldenUsersShapesList(){
        return SA__UId_Shape;
    }

}
