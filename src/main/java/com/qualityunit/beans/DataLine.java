package com.qualityunit.beans;


/**
 * Created by Maksim Ovcharenko
 * 09.07.2018
 */

public abstract class DataLine {
    private int service_id;
    private int variation_id;
    private int question_type_id;
    private int category_id;
    private int sub_category_id;
    private boolean firstAnswer;


    public int getService_id() {
        return service_id;
    }

    public void setService_id(int service_id) {
        this.service_id = service_id;
    }

    public int getVariation_id() {
        return variation_id;
    }

    public void setVariation_id(int variation_id) {
        this.variation_id = variation_id;
    }

    public int getQuestion_type_id() {
        return question_type_id;
    }

    public void setQuestion_type_id(int question_type_id) {
        this.question_type_id = question_type_id;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getSub_category_id() {
        return sub_category_id;
    }

    public void setSub_category_id(int sub_category_id) {
        this.sub_category_id = sub_category_id;
    }

    public boolean isFirstAnswer() {
        return firstAnswer;
    }

    public void setFirstAnswer(boolean firstAnswer) {
        this.firstAnswer = firstAnswer;
    }


}
