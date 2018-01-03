package Index;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import javax.swing.text.BadLocationException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class QuestionsIndexer {
    String dataSetName;
    IndexWriter writer;
    HashSet<Integer> QIdHashSet = new HashSet<>();
    
    public QuestionsIndexer(Path indexDirectoryPath) throws IOException{   
        Directory indexDirectory = FSDirectory.open(indexDirectoryPath);
        Analyzer analyzer = new StandardAnalyzer();
        IndexWriterConfig cfg = new IndexWriterConfig(analyzer);
        cfg.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        writer = new IndexWriter(indexDirectory, cfg);
    }
    
    public int CreateQuestionIndex(String srcFilePath, String dataSetName) throws IOException, BadLocationException{
        SetParameters(dataSetName);
        IndexQuestionFile(srcFilePath);
        return writer.numDocs();
    }
    
    private void SetParameters(String dataSetName){
        this.dataSetName = dataSetName;
    }
    
    private void IndexQuestionFile(String srcFilePath) throws IOException {
        LineNumberReader reader = new LineNumberReader(new FileReader(new File(srcFilePath)));
        int count = 24120523;
        String line = "";
        while ((line = reader.readLine()) != null){
            if (ExistExpression(line, "Id")){
                AddQuestionDocument(line);
                count--;
                printCounter(count);
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

    private void printCounter(int count) {
        if(count%100000==0)
            System.out.println(count);
    }

    private void AddQuestionDocument(String Row) throws IOException {
        int PostTypeId = Integer.parseInt(FindExpression(Row, "PostTypeId"));
        if (PostTypeId == 1){
            String[] tags = ConvertHtmlToPlainTxt(FindExpression(Row, "Tags"));
            if (ExistDataSetTag(tags)){
                int QId = Integer.parseInt(FindExpression(Row, "Id"));
                QIdHashSet.add(QId);
                Document doc = getQuestionDocument(Row);
                writer.addDocument(doc);
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

    private Document getQuestionDocument(String Row) {
        Document doc = new Document();
        doc.add(findIdField(Row, "Id"));
        doc.add(findIdField(Row, "AcceptedAnswerId"));
        doc.add(findDateField(Row, "CreationDate"));
        doc.add(findIdField(Row, "Score"));
        doc.add(findIdField(Row, "ViewCount"));
        doc.add(findTextField(Row, "Body"));
        doc.add(findTextField(Row, "Title"));
        doc.add(findIdField(Row, "OwnerUserId"));
        doc.add(findIdField(Row, "AnswerCount"));
        doc.add(findIdField(Row, "CommentCount"));
        doc.add(findIdField(Row, "FavoriteCount"));
        doc.add(findDateField(Row, "CommunityOwnedDate"));
        doc.add(findDateField(Row, "LastActivityDate"));
        for(Field TagItem : findTagField(Row, "Tags")){
            doc.add(TagItem);
        }
        return doc;
    }

    private Field findIdField(String Row, String fieldName) {
        String Id = Integer.toString(intTryParse(Row, fieldName));
        Field RowField = new StringField(fieldName, Id, Field.Store.YES);
        return RowField;
    }

    private int intTryParse(String line, String field){
        int n;
        String fieldStr = FindExpression(line, field);
        try {
            n = Integer.parseInt(fieldStr);
        } catch (NumberFormatException e) {
            n = -1;
        }
        return n;
    }
    
    private Field findDateField(String Row, String fieldName) {
        String date = FindExpression(Row, fieldName);
        String YMD = date.split("T")[0].replace("-", "");
        Field RowField = new StringField(fieldName, YMD, Field.Store.YES);
        return RowField;
    }

    private Field findTextField(String Row, String fieldName) {
        String TextField = FindExpression(Row, fieldName);
        String CorrectedTextField = findCorrectedText(TextField);
        FieldType FT = SetFieldType();
        Field RowField = new Field(fieldName, CorrectedTextField, FT);
        return RowField;
    }

    private String findCorrectedText(String text){
        text = CorrectText(text, "c#", "csharp");
        text = CorrectText(text, ".net", "dotnet");
        text = CorrectText(text, "c++", "cplusplus");
        text = CorrectText(text, ".htaccess", "dothtaccess");
        text = CorrectText(text, "three.js", "threedotjs");
        text = CorrectText(text, "socket.io", "socketdotio");
        text = CorrectText(text, "node.js", "nodedotjs");
        text = CorrectText(text, "knockout.js", "knockoutdotjs");
        text = CorrectText(text, "d3.js", "d3dotjs");
        text = CorrectText(text, "backbone.js", "backbonedotjs");
        text = CorrectText(text, "underscore.js", "underscoredotjs");
        text = CorrectText(text, "ember.js", "emberdotjs");
        text = CorrectText(text, "handlebars.js", "handlebarsdotjs");
        return text;
    }
    
    private String CorrectText(String text, String oldWord, String newWord){
        String CorrectedText = text.toLowerCase();
        if (text.toLowerCase().contains(oldWord))
            CorrectedText = text.toLowerCase().replace(oldWord, newWord);
        return CorrectedText;
    }
    
    private FieldType SetFieldType(){
        FieldType FT = new FieldType();
        FT.setIndexOptions(IndexOptions.DOCS_AND_FREQS);
        FT.setStoreTermVectors(true);
        FT.setTokenized(true);
        return FT;
    }
    
    private Field[] findTagField(String Row, String fieldName) {
        String TagExpression = FindExpression(Row, fieldName);
        String[] Tags = ConvertHtmlToPlainTxt(TagExpression);
        Field []RowFields = new Field[Tags.length];
        for (int i = 0; i < Tags.length; i++){
            RowFields[i] = new StringField(fieldName, Tags[i], Field.Store.YES);
        }
        return RowFields;
    }
    
    public void close() throws IOException{
        writer.close();
    }
    
    public HashSet<Integer> getQIdHashSet(){
        return QIdHashSet;
    }
}
