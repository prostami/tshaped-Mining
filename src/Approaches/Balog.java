package Approaches;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;

public class Balog {
    private Path   answerPath;
    private String lambdaType;
    private double lambda;
    private String dataSetName;
    private ArrayList<String> tagsOfSkillArea;
    private LinkedHashMap<Integer, Double> UId_probilitySAGivenExpert = new LinkedHashMap<>();
    private LinkedHashMap<Integer, Double> SortedUId_probilitySAGivenExpert = new LinkedHashMap<>();
    
    public Balog(Path answerPath, ArrayList<String> tagsOfSkillArea, String dataSetName, String lambdaType, double lambda) throws IOException, ParseException{
        setParameters(answerPath, tagsOfSkillArea, dataSetName, lambdaType, lambda);
        CalculateProbilitySAGivenExpert();
        SortUsersProbility();
    }
    
    private void setParameters(Path answerPath, ArrayList<String> tagsOfSkillArea,String dataSetName, String lambdaType, double lambda){
        this.answerPath = answerPath;
        this.tagsOfSkillArea = tagsOfSkillArea;;
        this.dataSetName = dataSetName;
        setLambda(lambdaType, lambda);
    }
  
    private void setLambda(String lambdaType, double lambda) {
        this.lambdaType = lambdaType;
        if (lambdaType.equals("constant"))
            this.lambda = lambda;
    }

    private void CalculateProbilitySAGivenExpert() throws IOException, ParseException {
        Searcher searcher = new Searcher(answerPath, "Body");
        TopDocs hits = searcher.querySearch("*:*", Integer.MAX_VALUE);
        IndexReader indexReader = CreateIndexReader(answerPath);
        int count = getAnswersCount();
        for (ScoreDoc scoreDoc : hits.scoreDocs){
            Document doc = searcher.getDocument(scoreDoc);
            int userId = Integer.parseInt(doc.getField("OwnerUserId").stringValue());
            int docNum = scoreDoc.doc;
            count--;
            printCounter(count);
            if (userId != -1)         
                AddDocProbilityForEachUser(indexReader, userId, docNum);
        }
        indexReader.close();
    }

    private int getAnswersCount(){
        switch (dataSetName) {
            case "c#":
                return 1453649;
            case "java":
                return 1510812;
            case "android":
                return 917924;
            default:
                return 0;
        }
    }
    
    private void printCounter(int count){
        if(count%10000==0)
            System.out.println(count);
    }
    
    private IndexReader CreateIndexReader(Path indexDirectoryPath) throws IOException {
        Directory indexDirectory = FSDirectory.open(indexDirectoryPath);
        IndexReader indexReader = DirectoryReader.open(indexDirectory);
        return indexReader;
    }
    
    private void AddDocProbilityForEachUser(IndexReader indexReader, int userId, int docNum) throws IOException {
        double currentProbilityForEachDoc = CalculateProbilityForEachDoc(indexReader, docNum);
        if (UId_probilitySAGivenExpert.containsKey(userId)){
            double previousProbility = UId_probilitySAGivenExpert.get(userId);
            double newProbility = currentProbilityForEachDoc + previousProbility;
            UId_probilitySAGivenExpert.replace(userId, newProbility);
        }
        else
            UId_probilitySAGivenExpert.put(userId, currentProbilityForEachDoc);
    }

    private double CalculateProbilityForEachDoc(IndexReader indexReader, int docNum) throws IOException {
        double probilityDocGivenExpert = 1;
        double probilitySAGivenDocAndExpert = CalculateProbilitySAGivenDocAndExpert(indexReader, docNum);
        double probilityForEachDoc = probilityDocGivenExpert * probilitySAGivenDocAndExpert;
        return probilityForEachDoc;     
    }

    private double CalculateProbilitySAGivenDocAndExpert(IndexReader indexReader, int docNum) throws IOException {
        double productProbility = 1;
        for(String tag : tagsOfSkillArea){
            productProbility *= CalculateProbilityTagGivenThetaDoc(indexReader, tag.split("-"), docNum);
        }
        return productProbility;
    }
    
    private double CalculateProbilityTagGivenThetaDoc(IndexReader indexReader, String []terms, int docNum) throws IOException{
        double productProbility = 1;
        for (String termItem : terms){
            if(!IsExistStopWord(termItem))
                productProbility *= CalculateProbilityTermGivenThetaDoc(indexReader,"Body", CorrectTermText(termItem.trim()), docNum);
        }
        return productProbility;   
    }
    
    private boolean IsExistStopWord(String term){
        switch (term) {
            case "in":
                return true;
            case "on":
                return true;
            case "of":
                return true;
            case "at":
                return true;
            default:
                return false;
        }
    }
    
    private String CorrectTermText(String text){
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

    private String CorrectText(String text, String oldWord, String newWord) {
        String CorrectedText = text.toLowerCase();
        if (text.toLowerCase().contains(oldWord))
            CorrectedText = text.toLowerCase().replace(oldWord, newWord);
        return CorrectedText;
    }
    
    private double CalculateProbilityTermGivenThetaDoc(IndexReader indexReader, String field, String term, int docNum) throws IOException{
        double probilityTermGivenDoc = CalculateProbilityTermGivenDoc(indexReader, field, term, docNum);
        double probilityTerm = CalculateProbilityTerm(indexReader, field, term);
        double lambdaD = CalculateLambda(indexReader, field, docNum);
        double probilityTermGivenThetaDoc = ((1 - lambdaD) * probilityTermGivenDoc) + (lambdaD * probilityTerm);
        return probilityTermGivenThetaDoc;
    }

    private double CalculateProbilityTermGivenDoc(IndexReader indexReader, String field, String term, int docNum) throws IOException {
        double termFreq = (double)(CalculateTermFreq(indexReader, field, term, docNum));
        double docLength = (double)(CalculateDocLength(indexReader, field, docNum));
        double probilityTermGivenDoc = termFreq / docLength;
        return probilityTermGivenDoc;
    }

    private long CalculateTermFreq(IndexReader indexReader, String field, String termOfQuery, int docNum) throws IOException {
        Terms termVector = indexReader.getTermVector(docNum, field);
        TermsEnum itr = termVector.iterator();
        long termFreq = SearchTermFreqInDoc(itr, termOfQuery);
        return termFreq;
    }
    
    private long SearchTermFreqInDoc(TermsEnum itr, String termOfQuery) throws IOException {
        BytesRef term = null;
        long termFreq = 0;
        while ((term = itr.next()) != null){
            String termName = term.utf8ToString();
            if (termName.equals(termOfQuery)){
                termFreq = itr.totalTermFreq();
                break;
            }
        }
        return termFreq;
    }
    
    private long CalculateDocLength(IndexReader indexReader, String field, int docNum) throws IOException {
        Terms termVector = indexReader.getTermVector(docNum, field);
        TermsEnum itr = termVector.iterator();
        long docLength = SumDocTermsFreq(itr);
        return docLength;
    }
    
    private long SumDocTermsFreq(TermsEnum itr) throws IOException {
        BytesRef term = null;
        long sumTermsFreq = 0;
        while ((term = itr.next()) != null){
            sumTermsFreq += itr.totalTermFreq();
        }
        return sumTermsFreq;
    }
    
    private double CalculateProbilityTerm(IndexReader indexReader, String field, String term) throws IOException {
        double totalTermFreq = (double)(CalculateTotalTermFreq(indexReader, field, term));
        double collectionLength = (double)(CalculateCollectionLength(indexReader, field));
        double probilityTerm = totalTermFreq / collectionLength;
        return probilityTerm;
    }

    private long CalculateTotalTermFreq(IndexReader indexReader, String field, String term) throws IOException{
        long totalTermFreq = indexReader.totalTermFreq(new Term(field, term));
        return totalTermFreq;
    }
    
    private long CalculateCollectionLength(IndexReader indexReader, String field) throws IOException{
        long collectionLength = indexReader.getSumTotalTermFreq(field);
        return collectionLength;
    }
    
    private double CalculateLambda(IndexReader indexReader, String field, int docNum) throws IOException {
        if (lambdaType.equals("constant"))
            return lambda;
        else
            return LambdaIsNoConstant(indexReader, field, docNum);
    }

    private double LambdaIsNoConstant(IndexReader indexReader, String field, int docNum) throws IOException {
        double Beta = AvgDocLengthInCollection(indexReader, field);
        double documentLength = (double)(CalculateDocLength(indexReader, field, docNum));
        double lambdaD = Beta / (Beta + documentLength);
        return lambdaD;
    }

    private double AvgDocLengthInCollection(IndexReader indexReader, String field) throws IOException {
        long collectionLength = CalculateCollectionLength(indexReader, field);
        long numDoc = indexReader.numDocs();
        double documentLengthAvg = (double)collectionLength / (double)numDoc;
        return documentLengthAvg;
    }

    private void SortUsersProbility() {
        TreeMap<Double, ArrayList<Integer>> probility_UserList = getUserListGroupBySortedProbility();
        for (Map.Entry<Double, ArrayList<Integer>> item : probility_UserList.entrySet()){
            double probilityValue = item.getKey();
            ArrayList<Integer> userList = item.getValue();
            for (int user : userList){
                SortedUId_probilitySAGivenExpert.put(user, probilityValue);
            }
        }
        UId_probilitySAGivenExpert.clear();
    }
    
    private TreeMap<Double, ArrayList<Integer>> getUserListGroupBySortedProbility(){
        TreeMap<Double, ArrayList<Integer>> probility_UserList = new TreeMap<>(Collections.reverseOrder());
        for (Map.Entry<Integer, Double> user_ProbilityItem : UId_probilitySAGivenExpert.entrySet()){
            int userId = user_ProbilityItem.getKey();
            double probilityValue = user_ProbilityItem.getValue();
            if (probility_UserList.containsKey(probilityValue)){
                ArrayList<Integer> UsersIdList = probility_UserList.get(probilityValue);
                UsersIdList.add(userId);
                probility_UserList.replace(probilityValue, UsersIdList);
            }
            else{
                ArrayList<Integer> UsersIdList = new ArrayList<>();
                UsersIdList.add(userId);
                probility_UserList.put(probilityValue, UsersIdList);
            }
        }
        return probility_UserList;
    }
    
    public LinkedHashMap<Integer,Double> getSortedUId_probilitySAGivenExpert(){
        return SortedUId_probilitySAGivenExpert;
    }
}
