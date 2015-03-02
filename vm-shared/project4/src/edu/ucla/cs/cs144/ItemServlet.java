package edu.ucla.cs.cs144;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;
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

        try {
            Document doc = stringToDom(xml);
            Element item = doc.getDocumentElement();
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
            
        } catch (Exception e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            xml = errors.toString();
        }

        request.setAttribute("xml", xml);

        request.getRequestDispatcher("/getItem.jsp").forward(request,response);
        
    }
}
