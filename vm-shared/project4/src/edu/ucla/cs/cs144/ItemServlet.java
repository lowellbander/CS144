package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.*;

public class ItemServlet extends HttpServlet implements Servlet {
       
    public ItemServlet() {}

    public static Document stringToDom(String xmlSource) 
            throws SAXException, ParserConfigurationException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xmlSource)));
    }

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

    static Element getElementByTagNameNR(Element e, String tagName) {
        Node child = e.getFirstChild();
        while (child != null) {
            if (child instanceof Element && child.getNodeName().equals(tagName))
                return (Element) child;
            child = child.getNextSibling();
        }
        return null;
    }
    
    static String getElementText(Element e) {
        if (e.getChildNodes().getLength() == 1) {
            Text elementText = (Text) e.getFirstChild();
            return elementText.getNodeValue();
        }
        else
            return "";
    }

    static String getElementTextByTagNameNR(Element e, String tagName) {
        Element elem = getElementByTagNameNR(e, tagName);
        if (elem != null)
            return getElementText(elem);
        else
            return "";
    }


    protected void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException
    {
        AuctionSearchClient client = new AuctionSearchClient();
        String itemID = request.getParameter("ItemID");
        String xml = client.getXMLDataForItemId(itemID);
        if (xml == null) xml = "There was an error receiving data from the server. Sorry!";
        else {
            try {
                Document doc = stringToDom(xml);
                Element item = doc.getDocumentElement();

                // handle simple attributes
                request.setAttribute("itemid", 
                        item.getAttribute("ItemID"));
                request.setAttribute("name", 
                        getElementTextByTagNameNR(item, "Name"));
                request.setAttribute("first bid", 
                        getElementTextByTagNameNR(item, "First_Bid"));
                request.setAttribute("Number of Bids", 
                        getElementTextByTagNameNR(item, "Number_of_Bids"));
                request.setAttribute("Location", 
                        getElementTextByTagNameNR(item, "Location"));
                request.setAttribute("Country", 
                        getElementTextByTagNameNR(item, "Country"));
                request.setAttribute("Started", 
                        getElementTextByTagNameNR(item, "Started"));
                request.setAttribute("Ends", 
                        getElementTextByTagNameNR(item, "Ends"));

                // categories
                String categories = "";
                for (Element category : 
                        getElementsByTagNameNR(item, "Category")) {
                    categories = categories.concat(getElementText(category) 
                                                                    + ", ");
                }
                request.setAttribute("categories", categories);

                // seller 
                Element seller = getElementByTagNameNR(item, "Seller");
                request.setAttribute("seller rating", 
                        seller.getAttribute("Rating"));
                request.setAttribute("seller id", 
                        seller.getAttribute("UserID"));
                request.setAttribute("description",
                        getElementTextByTagNameNR(item, "Description"));

                // bids

                Element[] bids = getElementsByTagNameNR(
                        getElementByTagNameNR(item, "Bids"), "Bid");

                ArrayList times = new ArrayList();
                ArrayList amounts = new ArrayList();
                ArrayList ratings = new ArrayList();
                ArrayList ids = new ArrayList();
                ArrayList locations = new ArrayList();
                ArrayList countries = new ArrayList();
                for (Element bid : bids) {
                    times.add(getElementTextByTagNameNR(bid, "Time"));
                    amounts.add(getElementTextByTagNameNR(bid, "Amount"));

                    Element bidder = getElementByTagNameNR(bid, "Bidder");
                    locations.add(getElementTextByTagNameNR(bidder, 
                                                            "Location"));
                    countries.add(getElementTextByTagNameNR(bidder, 
                                                            "Country"));
                    ratings.add(bidder.getAttribute("Rating"));
                    ids.add(bidder.getAttribute("UserID"));

                }

                request.setAttribute("times", times);
                request.setAttribute("amounts", amounts);
                request.setAttribute("ratings", ratings);
                request.setAttribute("ids", ids);
                request.setAttribute("locations", locations);
                request.setAttribute("countries", countries);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        request.setAttribute("xml", xml);

        request.getRequestDispatcher("/getItem.jsp").forward(request,response);
        
    }
}
