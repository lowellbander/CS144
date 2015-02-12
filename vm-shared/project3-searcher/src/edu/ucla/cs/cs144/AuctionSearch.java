package edu.ucla.cs.cs144;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.Date;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.HashSet;
import java.text.SimpleDateFormat;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.lucene.document.Document;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import edu.ucla.cs.cs144.DbManager;
import edu.ucla.cs.cs144.SearchRegion;
import edu.ucla.cs.cs144.SearchResult;

public class AuctionSearch implements IAuctionSearch {

	/* 
         * You will probably have to use JDBC to access MySQL data
         * Lucene IndexSearcher class to lookup Lucene index.
         * Read the corresponding tutorial to learn about how to use these.
         *
	 * You may create helper functions or classes to simplify writing these
	 * methods. Make sure that your helper functions are not public,
         * so that they are not exposed to outside of this class.
         *
         * Any new classes that you create should be part of
         * edu.ucla.cs.cs144 package and their source files should be
         * placed at src/edu/ucla/cs/cs144.
         *
         */
	
	public SearchResult[] basicSearch(String query, int numResultsToSkip, 
			int numResultsToReturn) {
        SearchResult[] results = new SearchResult[0];
        try {
            SearchEngine se = new SearchEngine();
            TopDocs topDocs = se.performSearch(query, numResultsToSkip + numResultsToReturn);
            ScoreDoc[] hits = topDocs.scoreDocs;
            hits = Arrays.copyOfRange(hits, numResultsToSkip, hits.length);
            results = new SearchResult[hits.length];
            int i = 0;
            for (ScoreDoc hit : hits) {
                Document doc = se.getDocument(hit.doc);
                results[i] = new SearchResult(doc.get("itemID"), doc.get("name"));
                ++i;
            }
        } catch (Exception e) {
            System.out.println("Caught exception!");
        }
		return results;
	}

	public SearchResult[] spatialSearch(String query, SearchRegion region,
			int numResultsToSkip, int numResultsToReturn) {
		// TODO: Your code here!
		
        //Get id of all items which fall within region
        SearchResult[] spatialResult = null;

        try{
            Connection dbConnection = DbManager.getConnection(true);
            //Get location query results
            String poly = "Polygon((" + region.getLx() + " " + region.getLy() + ", " + 
                                        region.getLx() + " " + region.getRy() + ", " +
                                        region.getRx() + " " + region.getRy() + ", " +
                                        region.getRx() + " " + region.getLy() + ", " +
                                        region.getLx() + " " + region.getLy() + ")) "; 
            String sqlQuery = "SELECT ItemID FROM ItemSpatial WHERE MBRContains(GeomFromText(' "+ poly +"'), Coordinates);";
            Statement queryStatment = dbConnection.createStatement();
            ResultSet validLocationResults = queryStatment.executeQuery(sqlQuery);
            
            //basic query results
            SearchResult[] queryResult = basicSearch(query, 0, Integer.MAX_VALUE);

            //put all ResultSet ItemIDs into set for lookup
            HashSet<String> locationResultSet = new HashSet<String>();
                    
    
            while(validLocationResults.next()){
                locationResultSet.add(validLocationResults.getString("ItemID"));
            }    
            
           // if query result is in location result, add to list
            ArrayList<SearchResult> finalList = new ArrayList<SearchResult>();
            for(SearchResult sr : queryResult){
                if(locationResultSet.contains(sr.getItemId()))
                    finalList.add(sr);
            }

            //compute length  
            int length = (numResultsToSkip + numResultsToReturn > finalList.size()) ? (finalList.size() - numResultsToSkip) : numResultsToReturn;
            
            //fill spatialResult
            spatialResult = new SearchResult[length];
            for(int i=numResultsToSkip;i<length; ++i){
                spatialResult[i] = finalList.get(i);
            }   
            dbConnection.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
        finally{
            return spatialResult;
        }    
        // store in resultSet
        // get all results which match query
        // Take intersection of sets
        // return after skipResult arithmetic

	}

	public String getXMLDataForItemId(String itemId) {
		// TODO: Your code here!
		return "";
	}
	
	public String echo(String message) {
		return message;
	}

}
