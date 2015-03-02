package edu.ucla.cs.cs144;

import java.io.IOException;
import java.net.HttpURLConnection;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.io.StringWriter;
import java.io.PrintWriter;

public class ProxyServlet extends HttpServlet implements Servlet {
       
    public ProxyServlet() {}

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        // your codes here
        try{
            String q = request.getParameter("q");
            String baseURL = "http://google.com/complete/search?output=toolbar&q=";
            
            String encodedQ = URLEncoder.encode(q,"UTF-8");
            URL url = new URL(baseURL + encodedQ);
            response.setContentType("text/xml");
            
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.connect(); 
        
            InputStreamReader in = new InputStreamReader(connection.getInputStream());
            BufferedReader inReader = new BufferedReader(in);
            String inputString = "";
            String suggestionString = "";
            while((inputString = inReader.readLine()) != null){
                suggestionString += inputString;
            }

            //cleanup
            inReader.close();
            connection.disconnect();

            //output
            response.getWriter().println(suggestionString);
        }
        catch(Exception e){
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            response.getWriter().println(errors.toString());

            //TODO: In production, have a fluffy error message. 
            //Don't print stackTrace.
        }
        
            
    }
}
