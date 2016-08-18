package com.app.phr.peru.peruphr_app.JAVA;

import java.util.ArrayList;
import java.util.Date;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by omop on 2016-08-17.
 */
public class PHR {
    private String allergy_d, allergy_v;

//    public PHR() {
//        this.allergy_d = null;
//        this.allergy_v = null;
//    }

    public void setAllergy_d(String allergy_d) {this.allergy_d = allergy_d;}
    public void setAllergy_v(String allergy_v) {this.allergy_v = allergy_v;}
    public String getAllergy_d() {return this.allergy_d;}
    public String getAllergy_v() {return this.allergy_v;}


}