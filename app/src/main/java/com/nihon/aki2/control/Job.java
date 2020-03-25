package com.nihon.aki2.control;

import android.widget.Button;

/**
 * Created by kstanoev on 1/14/2015.
 */
public class Job {
    private String name, place,price;

    public Job(String name, String place,  String price)
    {
        this.setName(name);
       
        this.setPlace(place);
        this.setPrice(price);
    }
   
    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }

  
    public String getPlace() {
      return place;
    }

    public void setPlace(String place) {
      this.place = place;
    }

    public String getPrice() {
      return price;
    }

    public void setPrice(String price) {
      this.price = price;
    }


}
