/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication12;

import java.util.Date;

/**
 *
 * @author Maks_
 */
public class Version_file {
    private int number;
    private String date;
    private String time;

    
    Version_file(){}
    Version_file(int number, String date, String time){
        this.number =  number;
        this.date = date;
        this.time = time;
    }
    
    public int getNumber(){ return this.number; }
    public String getDate(){ return this.date; }
    public String getTime(){ return this.time; }
    
    public void setNumber(int number){ this.number = number;}
    public void setDate(String date){ this.date = date; }
    public void setTime(String time){ this.time = time; }
}
