package com.material.components.mine;

import com.atech.staggedrv.model.StaggedModel;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * 模拟数据模型
 * created by desong
 * 2020 3.29
 */
public class DataVisualizationModel implements StaggedModel {

    
    private int resourceId;

    private String title;
    private String value;
    private String change;

    private ArrayList<Float> scores;

    public DataVisualizationModel(String title, String value, String change, ArrayList<Float> scores, int resourceId){
        this.title = title;
        this.value = value;
        this.change = change;
        this.scores = scores;
        this.resourceId = resourceId;
    }

    public ArrayList<Float> getScores() {
        return scores;
    }

    public void setScores(ArrayList<Float> scores) {
        this.scores = scores;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public int localResorce() {
        return resourceId;
    }
}
