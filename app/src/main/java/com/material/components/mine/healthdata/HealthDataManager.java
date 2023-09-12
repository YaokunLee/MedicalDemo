package com.material.components.mine.healthdata;

import com.chunxia.mmkv.KVUtils;

import java.util.ArrayList;

public class HealthDataManager {

    // 写一个单例
    private static HealthDataManager instance = null;

    private HealthDataManager() {
    }

    public static HealthDataManager getInstance() {
        if (instance == null) {
            instance = new HealthDataManager();
        }
        return instance;
    }

    private static final String KEY_SELF_ASSESSMENT_HEALTH_DATA = "KEY_SELF_ASSESSMENT_HEALTH_DATA";

    public void setSelfAssessmentHealthData(SelfAssessmentHealthData data) {
        KVUtils.get().encodeParcelable(KEY_SELF_ASSESSMENT_HEALTH_DATA, data);
    }

    public SelfAssessmentHealthData getSelfAssessmentHealthData() {
        SelfAssessmentHealthData data =  KVUtils.get().decodeParcelable(KEY_SELF_ASSESSMENT_HEALTH_DATA, SelfAssessmentHealthData.class);
        if (data == null) {
            data = new SelfAssessmentHealthData();
        }
        return data;
    }


    public static SelfAssessmentHealthData calculateAverageScores(ArrayList<SurveyQuestionData> dataList) {
        SelfAssessmentHealthData healthData = new SelfAssessmentHealthData();

        // Calculate average score for each type
        healthData.setSleepScore(calculateAverageScoreForType(dataList, SurveyQuestionProvider.TYPE_SLEEP));
        healthData.setEmotionScore(calculateAverageScoreForType(dataList,  SurveyQuestionProvider.TYPE_EMOTION));
        healthData.setAppetiteScore(calculateAverageScoreForType(dataList, SurveyQuestionProvider.TYPE_APPETITE));
        healthData.setCognitionScore(calculateAverageScoreForType(dataList, SurveyQuestionProvider.TYPE_COGNITION));
        healthData.setActivityScore(calculateAverageScoreForType(dataList, SurveyQuestionProvider.TYPE_INTEREST_ACTIVITIES));
        healthData.setSelfWorthScore(calculateAverageScoreForType(dataList, SurveyQuestionProvider.TYPE_SELF_WORTH));
        healthData.setLifeAttitudeScore(calculateAverageScoreForType(dataList, SurveyQuestionProvider.TYPE_LIFE_ATTITUDE));
        healthData.setInterpersonalRelationScore(calculateAverageScoreForType(dataList, SurveyQuestionProvider.TYPE_INTERPERSONAL_RELATIONS));

        return healthData;
    }

    private static float calculateAverageScoreForType(ArrayList<SurveyQuestionData> dataList, String type) {
        int sum = 0;
        int count = 0;
        for (SurveyQuestionData data : dataList) {
            if (data.getType().equals(type) && data.getScore() != -1) {
                sum += data.getScore();
                count++;
            }
        }
        if (count == 0) return 0; // avoid division by zero
        return (float) sum / count;
    }

}
