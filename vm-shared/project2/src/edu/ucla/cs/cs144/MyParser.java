/* CS144
 *
 * Parser skeleton for processing item-???.xml files. Must be compiled in
 * JDK 1.5 or above.
 *
 * Instructions:
 *
 * This program processes all files passed on the command line (to parse
 * an entire diectory, type "java MyParser myFiles/*.xml" at the shell).
 *
 * At the point noted below, an individual XML file has been parsed into a
 * DOM Document node. You should fill in code to process the node. Java's
 * interface for the Document Object Model (DOM) is in package
 * org.w3c.dom. The documentation is available online at
 *
 * http://java.sun.com/j2se/1.5.0/docs/api/index.html
 *
 * A tutorial of Java's XML Parsing can be found at:
 *
 * http://java.sun.com/webservices/jaxp/
 *
 * Some auxiliary methods have been written for you. You may find them
 * useful.
 */

package edu.ucla.cs.cs144;

import java.io.*;
import java.text.*;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.ErrorHandler;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

class MyParser {
    
    static final String columnSeparator = "|*|";
    static DocumentBuilder builder;
    
    static final String[] typeName = {
	"none",
	"Element",
	"Attr",
	"Text",
	"CDATA",
	"EntityRef",
	"Entity",
	"ProcInstr",
	"Comment",
	"Document",
	"DocType",
	"DocFragment",
	"Notation",
    };
    
    static class MyErrorHandler implements ErrorHandler {
        
        public void warning(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void error(SAXParseException exception)
        throws SAXException {
            fatalError(exception);
        }
        
        public void fatalError(SAXParseException exception)
        throws SAXException {
            exception.printStackTrace();
            System.out.println("There should be no errors " +
                               "in the supplied XML files.");
            System.exit(3);
        }
        
    }
    
    /* Non-recursive (NR) version of Node.getElementsByTagName(...)
     */
    static Element[] getElementsByTagNameNR(Element e, String tagName) {
        Vector< Element > elements = new Vector< Element >();
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
            {
                elements.add( (Element)child );
            }
            child = child.getNextSibling();
        }
        Element[] result = new Element[elements.size()];
        elements.copyInto(result);
        return result;
    }
    
    /* Returns the first subelement of e matching the given tagName, or
     * null if one does not exist. NR means Non-Recursive.
     */
    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    /* Returns the text associated with the given element (which must have
     * type #PCDATA) as child, or "" if it contains no text.
     */
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }
    
    /* Returns the text (#PCDATA) associated with the first subelement X
     * of e with the given tagName. If no such X exists or X contains no
     * text, "" is returned. NR means Non-Recursive.
     */
    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }
    
    /* Returns the amount (in XXXXX.xx format) denoted by a money-string
     * like $3,453.23. Returns the input if the input is an empty string.
     */
    static String strip(String money) {
        if (money.equals(""))
            return money;
        else {
            double am = 0.0;
            NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.US);
            try { am = nf.parse(money).doubleValue(); }
            catch (ParseException e) {
                System.out.println("This method should work for all " +
                                   "money values you find in our data.");
                System.exit(20);
            }
            nf.setGroupingUsed(false);
            return nf.format(am).substring(1);
        }
    }
    
    static String toMySQLtimestamp(String in) {
        String out = "";
        SimpleDateFormat inFormat = new SimpleDateFormat("MMM-dd-yy HH:mm:ss");
        try {
            Date parsed = inFormat.parse(in);
            DateFormat outFormat = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
            out = outFormat.format(parsed);
        } catch (ParseException pe) {
            System.out.println("ERROR: Cannot parse \"" + in + "\"");
        }
        return out;
    }

    static String formatForLoad(String... args){
        String result = "";
        
        int size = args.length, i=0;
        for(;i<size-1; ++i){
            if(args[i].length() != 0){
                result = result + args[i] + columnSeparator;
            }
            else{
                result = result + columnSeparator;
            }
            //System.out.println(result);
            //result = result + args[i] + columnSeparator;
        }
        result = result + args[i] ;

        return result;
    }
    /* Process one items-???.xml file.
     */
    static void processFile(File xmlFile) {
        Document doc = null;
        try {
            doc = builder.parse(xmlFile);
        }
        catch (IOException e) {
            e.printStackTrace();
            System.exit(3);
        }
        catch (SAXException e) {
            System.out.println("Parsing error on file " + xmlFile);
            System.out.println("  (not supposed to happen with supplied XML files)");
            e.printStackTrace();
            System.exit(3);
        }
        
        /* At this point 'doc' contains a DOM representation of an 'Items' XML
         * file. Use doc.getDocumentElement() to get the root Element. */
        System.out.println("Successfully parsed - " + xmlFile);
        
        /* Fill in code here (you will probably need to write auxiliary
            methods). */
        // for each item, create a text line for the LOAD file
        Element root = doc.getDocumentElement();
        Element[] items = getElementsByTagNameNR(root,"Item");
       
        BufferedWriter itemWriter = null , userWriter = null, bidWriter = null, categoryWriter = null;
        try{
            //Create outlets to write to files
            itemWriter = new BufferedWriter(new FileWriter("item.del",true));
            userWriter = new BufferedWriter(new FileWriter("user.del", true));
            bidWriter = new BufferedWriter(new FileWriter("bid.del", true));
            categoryWriter = new BufferedWriter(new FileWriter("category.del", true));

            for (int i = 0; i < items.length; ++i) {
            
                Element item = items[i];

                //Get all strings needed
                String ItemID = item.getAttribute("ItemID");
                String Name = getElementTextByTagNameNR(item, "Name");
                String First_Bid = strip(getElementTextByTagNameNR(item,"First_Bid"));
                String Started = toMySQLtimestamp(getElementTextByTagNameNR(item, "Started"));
                String Ends = toMySQLtimestamp(getElementTextByTagNameNR(item, "Ends"));
                String Currently = strip(getElementTextByTagNameNR(item, "Currently"));
                String Buy_Price = strip(getElementTextByTagNameNR(item, "Buy_Price"));
                if(Buy_Price == "") 
                    Buy_Price = " ";
                String descriptionFullText = getElementTextByTagNameNR(item, "Description");
                String Description = descriptionFullText.substring(0, Math.min(descriptionFullText.length(), 4000));
                Element[] categoryList = getElementsByTagNameNR(item, "Category");
                Element seller = getElementByTagNameNR(item, "Seller");
                String sellerID = seller.getAttribute("UserID");
                String userID = sellerID;
                String rating = seller.getAttribute("Rating");
                String Country = getElementText(getElementByTagNameNR(item, "Country"));
                Element Location_Element = getElementByTagNameNR(item, "Location");
                String Location = getElementText(Location_Element);
                String Latitude = Location_Element.getAttribute("Latitude");
                String Longitude = Location_Element.getAttribute("Longitude");   
                if(Latitude == "")  Latitude = " ";
                if(Longitude == "") Longitude = " ";
                Element bids = getElementByTagNameNR(item, "Bids");
                Element[] bidList = getElementsByTagNameNR(bids, "Bid");

                //Construct rows and write to load files
                String itemRow = formatForLoad(ItemID, Name, Buy_Price, First_Bid, Started, Ends,sellerID, Description, Location, Country, Latitude, Longitude);
                itemWriter.write(itemRow+"\n");
                
                String userRow = formatForLoad(userID, rating, Location, Country);
                userWriter.write(userRow+"\n");
            
                for(int k=0; k<bidList.length ;k++){
                    Element bid = bidList[k];
                    Element bidder = getElementByTagNameNR(bid, "Bidder");
                    String BidderID = bidder.getAttribute("UserID");
                    String Bidder_Rating = bidder.getAttribute("Rating");
                    String Bidder_Location = getElementTextByTagNameNR(bidder, "Location");
                    String Bidder_Country = getElementTextByTagNameNR(bidder, "Country");
                    String bidderUserRow = formatForLoad(BidderID, Bidder_Rating, Bidder_Location, Bidder_Country);
                    userWriter.write(bidderUserRow+"\n");

                    String Time = toMySQLtimestamp(getElementTextByTagNameNR(bid, "Time"));
                    String Amount = getElementTextByTagNameNR(bid, "Amount");
                    String bidRow = formatForLoad(BidderID, ItemID, Time, Amount);
                    bidWriter.write(bidRow + "\n");
                }    
                for(int j=0; j<categoryList.length; j++){
                    Element category = categoryList[j];
                    String categoryRow = formatForLoad(getElementText(category), ItemID);
                    categoryWriter.write(categoryRow+"\n");
                }
                //return;
            }
        } 
        catch(IOException e){
            e.printStackTrace();
        } finally {
            if(itemWriter != null) try{itemWriter.close();} catch(IOException ignore) {} 
            if(categoryWriter != null) try{categoryWriter.close();} catch(IOException ignore){}
            if(userWriter != null) try{userWriter.close();} catch(IOException ignore){}
            if(bidWriter != null) try{bidWriter.close();} catch(IOException ignore){}
        }
        // for each Item in the XML document

        
    }
    
    public static void main (String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java MyParser [file] [file] ...");
            System.exit(1);
        }
        
        /* Initialize parser. */
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setValidating(false);
            factory.setIgnoringElementContentWhitespace(true);      
            builder = factory.newDocumentBuilder();
            builder.setErrorHandler(new MyErrorHandler());
        }
        catch (FactoryConfigurationError e) {
            System.out.println("unable to get a document builder factory");
            System.exit(2);
        } 
        catch (ParserConfigurationException e) {
            System.out.println("parser was unable to be configured");
            System.exit(2);
        }
        
        /* Process all files listed on command line. */
        for (int i = 0; i < args.length; i++) {
            File currentFile = new File(args[i]);
            processFile(currentFile);
        }
    }
}
