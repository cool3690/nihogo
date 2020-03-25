package com.nihon.aki2.control;

import java.io.Serializable;

@SuppressWarnings("serial")
public class RssNews implements Serializable

{

    private String title = null;

    private String link = null;

   // private String pubDate = null;



    public void setTitle(String title)

    {

        this.title = title;

    }



    public String getTitle()

    {

        return title;

    }



    public void setLink(String link)

    {

        this.link = link;

    }



    public String getLink()

    {

        return link;

    }





    @Override

    public String toString()

    {

        return "RssNews [title=" + title + ", link=" + link  + "]";

    }

}