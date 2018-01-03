package Index;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashSet;
import javax.swing.text.BadLocationException;

public class CreateIndex {
    private String dataSetName;
    private String QuestionPath;
    private String AnswerPath;
    private String postFilePath;
    QuestionsIndexer QIndexer;
    AnswersIndexer AIndexer;
    HashSet<Integer> QIdList;
    
    public CreateIndex(String dataSetName, String postFilePath, String QuestionPath, String AnswerPath) throws IOException, BadLocationException {
        setParameters(dataSetName,postFilePath,QuestionPath,AnswerPath);
        create();
    }

    private void setParameters(String dataSetName, String postFilePath, String QuestionPath, String AnswerPath) {
        this.dataSetName  = dataSetName;
        this.QuestionPath = QuestionPath;
        this.AnswerPath   = AnswerPath;
        this.postFilePath = postFilePath;
    }
    
    private void create() throws IOException, BadLocationException{
        int indexedQuestionsCount = createQuestionIndex(QuestionPath);
        int indexedAnswersCount   = createAnswerIndex(AnswerPath);
        System.out.println(indexedQuestionsCount + "Questions and " + indexedAnswersCount + "Answers added!");
    }
    
    private int createQuestionIndex(String filePath) throws IOException, BadLocationException {
        QIndexer = new QuestionsIndexer(StringToPath(filePath));
        int numIndexed = QIndexer.CreateQuestionIndex(postFilePath, dataSetName);
        QIdList = QIndexer.getQIdHashSet();
        QIndexer.close();
        return numIndexed;
    }
    
    
    private Path StringToPath(String path){
        Path indexDir = new File(path).toPath();
        return indexDir;
    }

    private int createAnswerIndex(String filePath) throws IOException, BadLocationException {
        AIndexer = new AnswersIndexer(StringToPath(filePath));
        int numIndexed = AIndexer.CreateAnswerIndex(postFilePath, dataSetName, QIdList);
        AIndexer.close();
        return numIndexed;
    }
}
