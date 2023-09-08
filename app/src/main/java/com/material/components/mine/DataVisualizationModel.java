package com.material.components.mine;

import com.atech.staggedrv.model.StaggedModel;

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

    public DataVisualizationModel(String title, String value, String change, int resourceId){
        this.title = title;
        this.value = value;
        this.change = change;
        this.resourceId = resourceId;
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
