package com.atech.staggedrv.model;

/**
 * 数据模型接口，适用于网络图片
 * created by desong
 * 2020 3.29
 */
public interface StaggedModel {

    //标题,根据自身需要，可以不填
    String getTitle ();
    //网络图片
    //本地图片
    int localResorce();


}
