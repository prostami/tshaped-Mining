package Path;

public class FilePaths {
    private static String basePath = "C:\\TShape-Mining\\files\\";
    
    public static String getFilePaths(String dataSetName, String fileName){
        switch (fileName) {
            case "Posts":
                return basePath+"Posts.xml";
            case "IndexQuestions":
                return IndexPath(dataSetName)+"Questions";
            case "IndexAnswers":
                return IndexPath(dataSetName)+"Answers";
            case "Clustering":
                return GoldenPath(dataSetName)+dataSetName+"Cluster.csv";
            case "SkillShapesXML":
                return GoldenPath(dataSetName)+dataSetName+"SkillShapes.xml";
            case "QuestionDataSet":
                return GoldenPath(dataSetName)+dataSetName+"Questions.xml";
            case "AnswerDataSet":
                return GoldenPath(dataSetName)+dataSetName+"Answers.xml";
            case "userPerformance":
                return GoldenPath(dataSetName)+dataSetName+"UserPerformance.xml";
            case "skillAreasProperty":
                return GoldenPath(dataSetName)+dataSetName+"SkillArea.csv";
            case "AdvancedLevel":
                return GoldenPath(dataSetName)+dataSetName+"AdvancedLevel.csv";
            case "IntermediateLevel":
                return GoldenPath(dataSetName)+dataSetName+"IntermediateLevel.csv";
            case "BeginnerLevel":
                return GoldenPath(dataSetName)+dataSetName+"BeginnerLevel.csv";
            case "Shapes":
                return GoldenPath(dataSetName)+dataSetName+"Shapes.csv";
            case "topFrequntTags":
                return GoldenPath(dataSetName)+"topFrequntTags.txt";
            case "Similarity":
                return GoldenPath(dataSetName)+dataSetName+"Similarity.csv";
            case "DBA":
                return ResultPath(dataSetName)+"DBA\\";
            case "EBA":
                return ResultPath(dataSetName)+"EBA\\";
            case "XEBA":
                return ResultPath(dataSetName)+"XEBA\\";
            default:
                return "";
        }
    }
    
    private static String GoldenPath(String dataSetName){
        return basePath+"Golden\\"+dataSetName+"\\";
    }
    
    private static String IndexPath(String dataSetName){
        return basePath+"Index\\"+dataSetName+"\\";
    }
    
    private static String ResultPath(String dataSetName){
        return basePath+"Result\\"+dataSetName+"\\";
    }
    
}
