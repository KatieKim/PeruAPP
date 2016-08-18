package com.app.phr.peru.peruphr_app.JAVA;

import android.os.AsyncTask;
import android.util.Log;

import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * Created by chiyo on 2016-08-04.
 */
public class HTTPClient {
    private HttpURLConnection connection;
    private URL url;
    private BufferedReader reader;
    private Document mDoc;
    private boolean success;
    private String request;
    final static String LINK ="http://52.78.19.78/insert.php";
            //"http://52.78.19.78/insert.php";
            //"http://ucare.gilhospital.com/Peru/gateway.aspx";
    public HTTPClient() {
      request = "";
    }
    public void setDoc(Document doc){
        mDoc = doc;
        request = DocumentToString(mDoc);
        Log.d("xml",request);
    }
    public String connect() {
        String result ="";
        try {
            URL url = new URL(LINK);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000);

            conn.setDoOutput(true);
            conn.setDoInput(true);
            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            Log.d("http send",request);
            wr.write(request);
            wr.flush();
            wr.close();
            Log.d("http check","check1");

            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));


            StringBuilder sb = new StringBuilder();
            String line = "";
            Log.d("http check","check2");
            // Read Server Response
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                Log.d("http get",line);
                result += line;
            }
            result = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                    "<Response statusCode='100'>\n" + "<KeyCD>1234512345</KeyCD>\n" +"<PatientName>Daniel</PatientName>\n" +
                    "</Response>\n";
            Log.d("result",result);
            Log.d("xml","result : "+sb.toString());
        }catch (FileNotFoundException e){
            Log.d("err",e.toString());
        }
        catch (MalformedURLException e) {
            Log.d("err",e.toString());
            return "connection error";
        } catch (IOException e) {
            e.printStackTrace();
            return "connection error";
        } finally {
            if (connection != null)
                connection.disconnect();
            try {
                if(reader != null)
                   reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public static String DocumentToString(Document doc) {
        try {
            StringWriter clsOutput = new StringWriter();
            Transformer clsTrans = TransformerFactory.newInstance().newTransformer();
            clsTrans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
            clsTrans.setOutputProperty(OutputKeys.METHOD, "xml");
            clsTrans.setOutputProperty(OutputKeys.INDENT, "yes");
            clsTrans.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            clsTrans.transform(new DOMSource(doc), new StreamResult(clsOutput));
            return clsOutput.toString();
        } catch (Exception ex) {
            return "";
        }
    }
}
