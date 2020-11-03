package com.nihon.aki2.control;

import android.widget.Button;

/**
 * Created by kstanoev on 1/14/2015.
 */
public class Exam {
    private String title, testinfo, jointime,testtime,countday;
    int more;

    public Exam(String title,String testinfo,String jointime,String testtime,String countday,int more)
    {
        this.setTitle(title);
        this.setTestinfo(testinfo);
        this.setJointime(jointime);
        this.setTesttime(testtime);
        this.setCountday(countday);
        this.setMore(more);
       
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTestinfo() {
        return testinfo;
    }

    public void setTestinfo(String testinfo) {
        this.testinfo = testinfo;
    }

    public String getJointime() {
        return jointime;
    }

    public void setJointime(String jointime) {
        this.jointime = jointime;
    }

    public String getTesttime() {
        return testtime;
    }

    public void setTesttime(String testtime) {
        this.testtime = testtime;
    }

    public String getCountday() {
        return countday;
    }

    public void setCountday(String countday) {
        this.countday = countday;
    }

    public int getMore() {
        return more;
    }

    public void setMore(int more) {
        this.more = more;
    }
}
