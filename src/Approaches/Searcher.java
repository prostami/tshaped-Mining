package Approaches;

import java.io.IOException;
import java.nio.file.Path;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.PhraseQuery;
import org.apache.lucene.search.PhraseQuery.Builder;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Searcher {
    private IndexSearcher indexSearcher;
    private QueryParser queryParser;
    private PhraseQuery phraseQuery;
    private String field;
    
    public Searcher(Path indexDirectoryPath, String field) throws IOException{
        Directory indexDirectory = FSDirectory.open(indexDirectoryPath);
        IndexReader indexReader = DirectoryReader.open(indexDirectory);
        indexSearcher = new IndexSearcher(indexReader);
        Analyzer analyzer = new StandardAnalyzer();
        setQueryParser(field, analyzer);
        setSearchField(field);
    }

    private void setQueryParser(String field, Analyzer analyzer) {
        queryParser = new QueryParser(field, analyzer);
    }

    private void setSearchField(String field) {
        this.field = field;
    }
    
    public TopDocs querySearch(String searchQuery,int max) throws ParseException, IOException{
        Query query = queryParser.parse(searchQuery);
        return indexSearcher.search(query, max);
    }
    
    public TopDocs PhraseSearch(String PhraseSearchQuery, int max) throws IOException {           
        String[] words = PhraseSearchQuery.split(" ");
        Builder phraseQueryBuilder = addTermToPhraseQueryBuilder(words);
        phraseQuery = phraseQueryBuilder.build();
        return indexSearcher.search(phraseQuery, max);
    }

    public TopDocs PhraseSearch(String phraseSearchQuery, String field, int max) throws IOException {
        String[] words = phraseSearchQuery.split(" ");
        Builder phraseQueryBuilder = addTermToPhraseQueryBuilder(field, words);
        phraseQuery = phraseQueryBuilder.build();
        return indexSearcher.search(phraseQuery, max);
    }
    
     private Builder addTermToPhraseQueryBuilder(String[] words){
        Builder phraseQueryBuilder = new Builder();
        for (String word : words)
        {
            phraseQueryBuilder.add(new Term(field, word));
        }
        return phraseQueryBuilder;
    }

    private Builder addTermToPhraseQueryBuilder(String field, String[] words){
        Builder phraseQueryBuilder = new Builder();
        for (String word : words)
        {
            phraseQueryBuilder.add(new Term(field, word));
        }
        return phraseQueryBuilder;
    }
        
    public Document getDocument(ScoreDoc scoreDoc) throws IOException {
        return indexSearcher.doc(scoreDoc.doc);
    }
}
