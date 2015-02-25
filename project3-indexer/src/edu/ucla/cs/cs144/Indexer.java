package edu.ucla.cs.cs144;

import java.io.IOException;
import java.io.StringReader;
import java.io.File;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Indexer {
    
    /** Creates a new instance of Indexer */
    public Indexer() {
    }

    private IndexWriter indexWriter = null;

    public IndexWriter getIndexWriter () throws IOException {
        if (indexWriter == null) {
            Directory indexDir = 
                FSDirectory.open(new File("/var/lib/lucene/ebay-index"));
            IndexWriterConfig config = 
                new IndexWriterConfig(Version.LUCENE_4_10_2, new StandardAnalyzer());
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
            indexWriter = new IndexWriter(indexDir, config);
        }
        return indexWriter;
    }

    public void closeIndexWriter() throws IOException {
        if (indexWriter != null) indexWriter.close();
   }
    
 
    public void rebuildIndexes() throws IOException {

        Connection conn = null;

        // create a connection to the database to retrieve Items from MySQL
	try {
	    conn = DbManager.getConnection(true);

        IndexWriter writer = getIndexWriter();
        
        // use JDBC to retrieve information from our database table, 
        
        // statements are kind of like threads, so you need separate statement
        // objects for separate queries: One for the Items, and another for
        // their Categories. If the queries are run on a single thread, the
        // following SQLexception is raised: "Operation not allowed after
        // ResultSet closed."

        PreparedStatement itemsQueryStatement = conn.prepareStatement("SELECT * FROM Item");
        
        ResultSet rs = itemsQueryStatement.executeQuery();
        String name, itemID, description;
        double howMany = 0;
        // for each Item in the database
        while (rs.next()) {
            Document doc = new Document();

            // retrieve the easy attributes
            name = rs.getString("Name");
            itemID = rs.getString("ItemID");
            description = rs.getString("Description");

            doc.add(new StringField("name", name, Field.Store.YES));
            doc.add(new StringField("itemID", itemID, Field.Store.YES));

            // retrieve and add categories to the document
            PreparedStatement c_s = 
                conn.prepareStatement("SELECT Category_Name FROM Category WHERE ItemID = " + itemID);
            ResultSet c_rs = c_s.executeQuery();
            String categories = "";
            while (c_rs.next())
                categories += c_rs.getString("Category_Name") + " ";

            String content = name + " " + description + " " + categories; 
            doc.add(new TextField("content", content, Field.Store.NO));

            writer.addDocument(doc);

            ++howMany;
            double total = 19532;
            double percentComplete = (howMany / total) * 100;
            if (howMany % 1000 == 0)
                System.out.println(howMany + " documents indexed. " 
                        + percentComplete + " % complete");
        }

        closeIndexWriter();

	} catch (SQLException ex) {
	    System.out.println(ex);
	}


	/*
	 * Add your code here to retrieve Items using the connection
	 * and add corresponding entries to your Lucene inverted indexes.
         *
         * You will have to use JDBC API to retrieve MySQL data from Java.
         * Read our tutorial on JDBC if you do not know how to use JDBC.
         *
         * You will also have to use Lucene IndexWriter and Document
         * classes to create an index and populate it with Items data.
         * Read our tutorial on Lucene as well if you don't know how.
         *
         * As part of this development, you may want to add 
         * new methods and create additional Java classes. 
         * If you create new classes, make sure that
         * the classes become part of "edu.ucla.cs.cs144" package
         * and place your class source files at src/edu/ucla/cs/cs144/.
	 * 
	 */


        // close the database connection
	try {
	    conn.close();
	} catch (SQLException ex) {
	    System.out.println(ex);
	}
    }    

    public static void main(String args[]) throws IOException {
        Indexer indexer = new Indexer();
        System.out.println("Rebuilding indexes . . .");
        indexer.rebuildIndexes();
        System.out.println("Done rebuilding indexes.");
    }   
}
