package TopFrequentTags;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class FrequentTags {
    private String dataSetName;
    private int tagsCount;
    LinkedHashMap<String,Integer> tagsFrequentList = new LinkedHashMap<>();
    
    public FrequentTags(String postFileAddress, String dataSetName, int tagsCount) throws IOException, FileNotFoundException{
        setParameters(dataSetName,tagsCount);
        calculateTagsFrequentCountOnPostFile(postFileAddress);
    }
    
    private void setParameters(String dataSetName, int tagsCount) {
        this.dataSetName = dataSetName;
        this.tagsCount   = tagsCount;
    }
        
    private void calculateTagsFrequentCountOnPostFile(String postFileAddress) throws FileNotFoundException, IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(postFileAddress)));
        String line = "";
        int count = 24120523;
        while((line=reader.readLine())!=null){
            if(ExistExpression(line, "Id")){
                CalculateFrequentTagsInTheLine(line);
                count--;
                if(count%100000==0)
                    System.out.println(count);
            }
        }
        System.out.println("finished!");
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

    private void CalculateFrequentTagsInTheLine(String line) {
        int PostTypeId = Integer.parseInt(FindExpression(line, "PostTypeId"));
        if (PostTypeId == 1)//is_Questions
        {
            String[] Tags = ConvertHtmlToPlainTxt(FindExpression(line, "Tags"));
            if (ExistDataSetTag(Tags))//is_tag_equals_with_dataSetTag
                IncreaseTheTagsCount(Tags);
        }
    }
    
    private String[] ConvertHtmlToPlainTxt(String line){
        String []tags = line.substring(4, line.length()-4).split("&gt;&lt;");
        return tags;
    }
    
    private boolean ExistDataSetTag(String[] tags) {
        return Arrays.asList(tags).contains(dataSetName.toLowerCase());
    }

    private void IncreaseTheTagsCount(String[] TagsList) {
        for(String tag : TagsList){
            if (!tag.equals(dataSetName)){
                if (tagsFrequentList.containsKey(tag)){
                    int frequentCount = tagsFrequentList.get(tag);
                    frequentCount++;
                    tagsFrequentList.replace(tag, frequentCount);
                }else
                    tagsFrequentList.put(tag, 1);
            }
        }
    }

    public LinkedHashMap<String,Integer> getSortedTopFrequentTagsList() {
        LinkedHashMap<String,Integer> TopSortedFrequentTagsList = TopElements.getTopElements(Sort.getSortedObjects(tagsFrequentList, "DESC"), tagsCount);
        return TopSortedFrequentTagsList;
    }
}
