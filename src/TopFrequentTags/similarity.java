package TopFrequentTags;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;


public class similarity {
    private String dataSetName;
    String[] tagsList;
    LinkedHashMap<String,HashSet<Integer>> tag_QIdList = new LinkedHashMap<>();
    double[][] similarityMatrix;

    public similarity(String dataSetName,String postFileAddress, Set<String> topFrequentTagsList) throws IOException {
        setParameters(dataSetName,topFrequentTagsList);
        calculateQIdListForEachTag(postFileAddress);
    }
    
    private void setParameters(String dataSetName, Set<String> topFrequentTagsList){
        setTagList(topFrequentTagsList);
        this.dataSetName = dataSetName;
        similarityMatrix = new double[topFrequentTagsList.size()][topFrequentTagsList.size()];
    }
    
    
    private void setTagList(Set<String> topFrequentTagsList){
        tagsList = new String[topFrequentTagsList.size()];
        int i = 0;
        for(String tag : topFrequentTagsList){
            tagsList[i] = tag;
            i++;
        }
    }
    
    public void getSimilarity(String writePath) throws FileNotFoundException {
        calculateSimilarity();
        printSimilarityMatrix(writePath);
    }

    private void calculateQIdListForEachTag(String postFileAddress) throws FileNotFoundException, IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(postFileAddress)));
        String line = "";
        int count = 24120523;
        while((line=reader.readLine())!=null){
            if(ExistExpression(line, "Id")){
                addQIdByAConditions(line);
                count--;
                if(count%100000==0)
                    System.out.println(count);
            }
        }
        System.out.println("finished!");
    }
    
    private void addQIdByAConditions(String line) {
        int PostTypeId = Integer.parseInt(FindExpression(line, "PostTypeId"));
        if (PostTypeId == 1)//is_Questions
        {
            String[] Tags = ConvertHtmlToPlainTxt(FindExpression(line, "Tags"));
            if (ExistDataSetTag(Tags) && ExistInTagList(Tags)){
                int QId = Integer.parseInt(FindExpression(line, "Id"));
                addQId(QId,Tags);
            }
        }
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
            if (Arrays.asList(tagsList).contains(tag)){
                exist = true;
                break;
            }
        }
        return exist;
    }
    
    private void addQId(int QId, String []tags){
        for(String tag : tags){
            if (Arrays.asList(tagsList).contains(tag)){
                if(tag_QIdList.containsKey(tag)){
                    HashSet<Integer> QIdList = tag_QIdList.get(tag);
                    QIdList.add(QId);
                    tag_QIdList.replace(tag, QIdList);
                }else{
                    HashSet<Integer> QIdList = new HashSet<>();
                    QIdList.add(QId);
                    tag_QIdList.put(tag, QIdList);
                }
            }
        }
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

    private void calculateSimilarity() {
        for (int i = 0; i < tagsList.length; i++){
            for (int j = 0; j <  tagsList.length; j++){
                 CalculateSimilarityBasedOnIAndJ(i, j);
            }
        }
    }

    private void CalculateSimilarityBasedOnIAndJ(int i, int j) {
        if (i == j)
            similarityMatrix[i][j] = 1;
        else if (i > j)
            similarityMatrix[i][j] = similarityMatrix[j][i];
        else
            similarityMatrix[i][j] = CalculateSimilarityBetweenTwoTags(tagsList[i], tagsList[j]);
    }
       
    private double CalculateSimilarityBetweenTwoTags(String tag1, String tag2) {
        HashSet<Integer> QIdList1 = tag_QIdList.get(tag1);
        HashSet<Integer> QIdList2 = tag_QIdList.get(tag2);
        //Intersect
        HashSet<Integer> Intersect= (HashSet<Integer>) QIdList1.clone();
        HashSet<Integer> QIdList2TempForIntersect = (HashSet<Integer>) QIdList2.clone();
        Intersect.retainAll(QIdList2TempForIntersect);
        //Union
        HashSet<Integer> Union= (HashSet<Integer>) QIdList1.clone();
        HashSet<Integer> QIdList2TempForUnion = (HashSet<Integer>) QIdList2.clone();
        Union.addAll(QIdList2TempForUnion);
        //Similarity
        double similarity =(double) Intersect.size() /(double) Union.size();
        return similarity;
    }
        
    private void printSimilarityMatrix(String writePath) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(writePath);
        for (int i = 0; i < similarityMatrix.length; i++){
            for (int j = 0; j < similarityMatrix[i].length; j++){
                double similarity = similarityMatrix[i][j];
                if(j==0)
                    writer.print(similarity);
                else
                    writer.print("," + similarity);
            }
            writer.println();
        }
        writer.close();
    }
    
}
