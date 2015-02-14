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
import java.sql.PreparedStatement;

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
            String sqlQuery = "SELECT ItemID FROM ItemSpatial WHERE Contains(GeomFromText(' "+ poly +"'), Coordinates);";
            //System.out.println(sqlQuery);
            Statement queryStatement = dbConnection.createStatement();
            ResultSet validLocationResults = queryStatement.executeQuery(sqlQuery);
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
            e.printStackTrace();
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
		
        String result = "";
        try{
            Connection dbConnection = DbManager.getConnection(true);
        
            //modularize query string generation into method?
            String itemSqlQuery = "SELECT * FROM Item WHERE ItemID = ? ;";
            String userSqlQuery = "SELECT * FROM User WHERE UserID = " + itemId + " ;";
            String bidSqlQuery = "SELECT * FROM Bid WHERE ItemID = " + itemId + " ;";
            String categorySqlQuery = "SELECT * FROM Category WHERE ItemID = " + itemId+ " ;";

            //TODO: Use prepared statements for better perf?            
            PreparedStatement itemQueryStatement = dbConnection.prepareStatement("SELECT * FROM Item WHERE ItemID = ?");
            itemQueryStatement.setString(1, itemId);
            ResultSet itemResult = itemQueryStatement.executeQuery();
            
            PreparedStatement userQueryStatement = dbConnection.prepareStatement(userSqlQuery);
            ResultSet userResult = userQueryStatement.executeQuery();
            PreparedStatement bidQueryStatement = dbConnection.prepareStatement(bidSqlQuery);
            ResultSet bidResult = bidQueryStatement.executeQuery();
            
            PreparedStatement categoryQueryStatement = dbConnection.prepareStatement(categorySqlQuery);
            ResultSet categoryResult = categoryQueryStatement.executeQuery();

            //itemID doesn't exist
            if(!itemResult.isBeforeFirst()){
                dbConnection.close();    
                return result;
            }  
            else{
                itemResult.next();
            } 
            //make function for enclosing with correct tag?
            result += "<Item ItemID=\"" + itemId + "\">\n";
            result += "<Name>" + itemResult.getString("Name") + "</Name>\n";
            
            if(categoryResult.isBeforeFirst()){ 
                String categories = "";
                try{
                    while(categoryResult.next()){
                        categories += "<Category>" + escapeString(categoryResult.getString("Category")) + "</Category>\n";
                    }
                }
                catch(SQLException e){ //don't print anything 
                }    
                result += categories;
            }
            try{ 
                String currently = String.format("$%.2f",itemResult.getFloat("Currently"));
                result += "<Currently>" + currently + "</Currently>\n";
            }catch(SQLException e){}
            
            String buyPrice = String.format("$%.2f", itemResult.getFloat("Buy_Price"));
            if(!buyPrice.equals("$0.00"))
                result+="<Buy_Price>"+buyPrice+"</Buy_Price>\n";
            String firstBid = String.format("$%.2f", itemResult.getFloat("First_Bid"));
            result += "<First_Bid>"+firstBid+"</First_Bid>\n";
           
            //Bid data strings 
            PreparedStatement countBidStatement = dbConnection.prepareStatement("SELECT COUNT(*) FROM Bid WHERE ItemID = " + itemId + ";");
            ResultSet countBidResult = countBidStatement.executeQuery();
            int numOfBids = 0;
            if(countBidResult.next()){
                numOfBids = countBidResult.getInt("COUNT(*)");
                result += "<Number_of_Bids>"+numOfBids+"</Number_of_Bids>\n";    
            }          
        
            String bidString = "";
            if(numOfBids != 0){
                if(!bidResult.isBeforeFirst()){
                    bidString += "<Bids />\n";
                }
                else{
                    bidString += "<Bids>\n";
                    while(bidResult.next()){
                        bidString += "<Bid>\n";
                        
                        String bidderID = bidResult.getString("BidderID");
                        PreparedStatement bidderStatement = dbConnection.prepareStatement("SELECT * FROM User WHERE UserID = " + bidderID + ";");
                        ResultSet bidderResult = bidderStatement.executeQuery();
                        
                        if(bidderResult.next()){
                            bidString += "<Bidder Rating=\"" + bidderResult.getString("Rating") + "\" UserID=\"" + bidderID + "\">\n";
                           try{
                                bidString += "<Location>" + bidderResult.getString("Location")+"</Location>\n";
                            } catch(Exception e){}
                           try{
                                bidString += "<Country>" + bidderResult.getString("Country")+"</Country>\n";
                            } catch(Exception e){}

                            bidString += "</Bidder>\n";
                        }

                        String time = convertToXMLTimeString(bidderResult.getTimestamp("Time").toString());
                        bidString += "<Time>" + time + "</Time>\n";
                        
                        bidString += "<Country>"+ String.format("$%.2f",bidResult.getFloat("Amount")) + "</Amount>\n";
                        bidString += "</Bid>\n";
                        
                    }
                    bidString += "</Bids>\n";
                } 
            }
            
            String locationString = "";
            try{
                locationString += "<Location";
                String latitude = escapeString(itemResult.getString("Latitide"));
                if(!latitude.equals("0.0000000"))
                    locationString += " Latitude=\"" + latitude + "\"";
                String longitude = escapeString (itemResult.getString("Longitude"));
                if(!longitude.equals("0.0000000"))
                    locationString += " Longitude=\"" + longitude + "\"";
            }catch(SQLException e){ locationString+= ">"; }
            finally{
                locationString+=  escapeString(itemResult.getString("Location"))+"</Location>\n";
            }
            result += locationString; 
           
            result += "<Country>" + escapeString(itemResult.getString("Country")) + "</Country>\n";
            result += "<Started>"+ convertToXMLTimeString(itemResult.getTimestamp("Started").toString()) + "</Started>\n";
            result += "<Ends>" + convertToXMLTimeString(itemResult.getTimestamp("Ends").toString()) + "</Ends>\n";
            
            dbConnection.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        finally{
            return result;
        }
	}

    private String convertToXMLTimeString(String sqlTime){
        String xmlTime = "";
        SimpleDateFormat input = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        try{
            Date newDateTime = input.parse(sqlTime);
            xmlTime += output.format(newDateTime);
        }catch(Exception e){}
        return xmlTime;
    }    
    public String escapeString(String input){
        String escapedString = input;
        String[] charsToReplace = { "<", ">", "&", "\"", "\'" };
        String[] charsToReplaceWith = { "&lt;", "&gt;", "&amp;", "&quot;", "&apos;" };
        //Hashmap would be better
        for(int i=0; i<charsToReplace.length; i++){
            escapedString.replaceAll(charsToReplace[i], charsToReplaceWith[i]);
        }
        return escapedString;
    }	
	public String echo(String message) {
		return message;
	}

}
