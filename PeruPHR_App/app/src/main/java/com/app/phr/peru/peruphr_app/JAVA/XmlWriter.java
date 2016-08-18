package com.app.phr.peru.peruphr_app.JAVA;

import android.content.SharedPreferences;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by chiyo on 2016-08-05.
 */
public class XmlWriter {
    private String id;
    private String pw;
    private String newPw;
    private String key;
    private Document doc;
    private String type;
    public void getKeyCd(String key){
        this.key = key;
    }
    public XmlWriter() {
        this.id = "";
        this.pw = "";
        this.key = "";
    }

    public Document getForPhr(String id, String key) {
        this.type = "PHR";
        this.id = id;
        this.key = key;
        makeXMLforPHR();
        return doc;
    }

    public Document getLoginXml(String id, String pw) {
        this.id = id;
        this.pw = pw;
        this.type = "Login";
        makeXMLforLogin();
        return doc;

    }
    public Document getXmlForChange(String id, String key, String oldPw, String newPw) {
        this.id = id;
        this.pw = oldPw;
        this.key = key;
        this.newPw = newPw;
        this.type = "ChangePassword";
        makeXMLforChangePW();
        return doc;

    }

    private void makeXMLforLogin() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            Element root = doc.createElement("Request");
            doc.appendChild(root);
            Element typeElement = doc.createElement("Type");
            typeElement.appendChild(doc.createTextNode(type));
            root.appendChild(typeElement);
            Element idElement = doc.createElement("PatientID");
            idElement.appendChild(doc.createTextNode(id));
            root.appendChild(idElement);
            Element pwElement = doc.createElement("Password");
            pwElement.appendChild(doc.createTextNode(pw));
            root.appendChild(pwElement);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    private void makeXMLforChangePW() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            Element root = doc.createElement("Request");
            doc.appendChild(root);
            Element typeElement = doc.createElement("Type");
            typeElement.appendChild(doc.createTextNode(type));
            root.appendChild(typeElement);
            Element keyElement = doc.createElement("KeyCD");
            keyElement.appendChild(doc.createTextNode(id));
            root.appendChild(keyElement);
            Element idElement = doc.createElement("PatientID");
            idElement.appendChild(doc.createTextNode(pw));
            Element currentPwElement = doc.createElement("OldPW");
            currentPwElement.appendChild(doc.createTextNode(pw));
            Element newPwElement = doc.createElement("NewPW");
            newPwElement.appendChild(doc.createTextNode(newPw));

            root.appendChild(idElement);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
    private void makeXMLforPHR() {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
            Element root = doc.createElement("Request");
            doc.appendChild(root);
            Element typeElement = doc.createElement("Type");
            typeElement.appendChild(doc.createTextNode(type));
            root.appendChild(typeElement);
            Element keyElement = doc.createElement("KeyCD");
            keyElement.appendChild(doc.createTextNode(key));
            root.appendChild(keyElement);
            Element idElement = doc.createElement("PatientID");
            idElement.appendChild(doc.createTextNode(id));
            root.appendChild(idElement);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
