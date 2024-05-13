package dataApp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class User implements Serializable{
    private String name;
    private int age;
    private LocalDate DOB;
    private boolean maritalStat;
    private boolean isGPS;
    private String address;


    public User(String name, int age, LocalDate DOB, boolean maritalStat, boolean isGPS, String address) {
        this.name = name;
        this.age = age;
        this.DOB = DOB;
        this.isGPS = isGPS;
        this.maritalStat = maritalStat;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public LocalDate getDOB() {
        return DOB;
    }

    public boolean getGPS(){
        return isGPS;
    }

    public boolean getMarital(){
        return maritalStat;
    }
    public String getAddress() {
        return address;
    }

    public void setName(String name) {
    	this.name = name;
    }
    public void setAge(int age) {
    	this.age = age;
    }
    public void setMarital(boolean status) {
    	this.maritalStat = status;
    }

    public void setgps(boolean gps) {
    	this.isGPS = gps;
    }

	public void setAddress(String address2) {
		this.address = address2;
		
	}

	public void setdob(LocalDate d1) {
		// TODO Auto-generated method stub
		this.DOB = d1;
	}

}

