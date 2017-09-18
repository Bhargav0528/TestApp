package com.example.bhargav.testapp;

import android.util.Log;

/**
 * Created by BhargavBV on 31-08-2017.
 */

public class weekquestion {

    String name,description,answer,hint,status;


    public String getPointflag() {
        return pointflag;
    }

    public void setPointflag(String pointflag) {
        this.pointflag = pointflag;
    }

    String pointflag;

    public String getHintflag() {
        return hintflag;
    }

    public void setHintflag(String hintflag) {
        this.hintflag = hintflag;
    }

    String hintflag;



    public weekquestion(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
