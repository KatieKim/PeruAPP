package com.app.phr.peru.peruphr_app.JAVA;

import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by chiyo on 2016-08-05.
 */
public class XmlParser {
    private int responseType; //log in
    private boolean mResponse; //request phr
    private String mData = "";
    private String key;
    private String pName;
    private PHR phr;
    DocumentBuilderFactory factory;
    DocumentBuilder builder;

    public XmlParser() {
        key = "";
        pName = "";
    }

    public Objects xmlParsing() {
        return null;
    }
    public int checkResponse(String data) {
        int response = 0;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            mData = data;

            InputStream istream = new
                    ByteArrayInputStream(mData.getBytes("utf-8"));
            Document doc = builder.parse(istream);
            Node node = doc.getFirstChild();


            NamedNodeMap Attrs = node.getAttributes();
            Node attr = Attrs.item(0);
            response = Integer.valueOf(attr.getNodeValue());
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return response;
    }

    public boolean resForLogin(String data) { //check response for login
        mResponse = false;
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            mData = data;
            mData = "<?xml version='1.0' encoding='UTF-8'?>\n" +
                    "<Response statusCode='100'>\n" +"<KeyCD>1234512345</KeyCD>\n +<PatientName>Daniel</PatientName>\n"+
                    "</Response>\n";

            InputStream istream = new
                    ByteArrayInputStream(mData.getBytes("utf-8"));
            Document doc = builder.parse(istream);
            Node node = doc.getFirstChild();


            NamedNodeMap Attrs = node.getAttributes();
            Node attr = Attrs.item(0);

            if(Integer.valueOf(attr.getNodeValue()) == 100){
                mResponse = true;
            }
            else{
               return false;
            }
            Element order = doc.getDocumentElement();
            Node item = order.getElementsByTagName("KeyCD").item(0);
            key = item.getFirstChild().getNodeValue();
            item = order.getElementsByTagName("PatientName").item(0);
            pName = item.getFirstChild().getNodeValue();

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mResponse;
    }
    public String getKey(){
        return key;
    }
    public String getPName(){
        return pName;
    }
    public PHR getPhrObject(String data){
        return phr;
    }
}


