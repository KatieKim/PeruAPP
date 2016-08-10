package com.app.phr.peru.peruphr_app.JAVA;

/**
 * Created by chiyo on 2016-08-04.
 */
public class PHR {

    // after get data
    private double height;
    private double weight;
    private int age;
    private String blood;
    private String sideEffect;
    private String allergy;
    public PHR(double height, double weight, int age, String blood, String sideEffect, String allergy){
        this.height = height;
        this.weight = weight;
        this.age = age;
        this.blood = blood;
        this.sideEffect = sideEffect;
        this.allergy = allergy;
    }
    public void setHeight(double height){this.height = height;}
    public void setWeight(double weight){this.weight = weight;}
    public void setAge (int age){this.age = age;}
    public void setBlood(String blood){this.blood = blood;}
    public void setSideEffect(String sideEffect){this.sideEffect = sideEffect;}
    public void setAllergy(String allergy){this.allergy = allergy;}
    public double getHeight(){return this.height;}
    public double getWeight(){return this.weight;}
    public String getBlood(){return this.blood;}
    public String getSideEffect(){return this.sideEffect;}
    public String getAllergy(){return this.allergy;}

}
