/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication12;

import java.util.Date;

/**
 *
 * @author user
 */
public class Check {
    private int id;
    private String condition;
    private String result;
    private String date;
    private String responsible;
    
    Check(){}
    Check(int id, String condition, String result, String date, String responsible){
        this.id = id;
        this.condition = condition;
        this.result = result;
        this.date = date;
        this.responsible = responsible;
    }
    
    public int getId(){ return this.id; }
    public String getCondition(){ return this.condition; }
    public String getResult(){ return this.result; }
    public String getDate(){ return this.date; }
    public String getResponsible(){ return this.responsible; }
    
    public void setId(int id){ this.id = id;}
    public void setCondition(String condition){ this.condition = condition; }
    public void setResult(String result){ this.result = result; }
    public void setDate(String date){ this.date = date; }
    public void setResponsible(String condition){ this.condition = condition; }
}
