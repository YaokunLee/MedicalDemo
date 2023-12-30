package com.material.components.mine.healthdata;

import android.app.Activity;
import android.util.Log;

import com.chunxia.mmkv.KVUtils;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptionsExtension;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResponse;
import com.google.android.gms.tasks.Task;
import com.material.components.R;
import com.material.components.mine.DataVisualizationModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HealthDataManager {

    private static final int GOOGLE_FIT_PERMISSIONS_REQUEST_CODE = 113232;
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
    private static final String KEY_HEALTH_DATA = "KEY_HEALTH_DATA";

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

    public HealthData getHealthData() {
        HealthData data =  KVUtils.get().decodeParcelable(KEY_HEALTH_DATA, HealthData.class);
        if (data == null) {
            data = new HealthData();
        }
        return data;
    }


    public void setHealthData(HealthData data) {
        KVUtils.get().encodeParcelable(KEY_HEALTH_DATA, data);
    }

    public ArrayList<DataVisualizationModel>  getData() {
        ArrayList<DataVisualizationModel> datas = new ArrayList<>();

//        HealthData healthData = HealthDataManager.getInstance().getHealthData();
//        long heartRate = healthData.getHeartRate();
//        long distance = healthData.getDistance();
//        long step = healthData.getStepCount();

        datas.add(new DataVisualizationModel("Heart Rate",  "69", "+12%" , null, R.drawable.image_1));
        datas.add(new DataVisualizationModel ("Resting Heart Rate",  "72", "-5%" , null,  R.drawable.image_5));
        datas.add(new DataVisualizationModel ("Distance",  "2.4km", "+126.4%" , new ArrayList<>(Arrays.asList(6245f, 5045f, 7543f, 9875f, 12348f, 7353f, 5000f)),   R.drawable.image_2));
        datas.add(new DataVisualizationModel ("Move Minutes",  "53min", "+2%" , null, R.drawable.image_3));
        datas.add(new DataVisualizationModel ("Speed",  "3.13km/h", "" , null, R.drawable.image_4));
        datas.add(new DataVisualizationModel ("Steps", "3453" , "-52%" , new ArrayList<>(Arrays.asList(1245f, 8045f, 7543f, 9875f, 2348f, 2353f, 13000f)), R.drawable.image_6));
        datas.add(new DataVisualizationModel ("Blood Pressure",  "83", "+12%" , null, R.drawable.image_7));
        datas.add(new DataVisualizationModel ("Blood glucose",  "83", "" , null, R.drawable.image_8));

        SelfAssessmentHealthData selfAssessmentHealthData = HealthDataManager.getInstance().getSelfAssessmentHealthData();

        datas.add(new DataVisualizationModel ("Self-assessment - Sleep",  "Score:" + selfAssessmentHealthData.getSleepScore(), "" , null, R.drawable.image_9));
        datas.add(new DataVisualizationModel ("Self-assessment - Appetite",  "Score:" + selfAssessmentHealthData.getAppetiteScore(), "" , null, R.drawable.image_10));
        datas.add(new DataVisualizationModel ("Self-assessment - emotion",  "Score:" + selfAssessmentHealthData.getEmotionScore(), "" , null, R.drawable.image_10));
        datas.add(new DataVisualizationModel ("Self-assessment - cognition",  "Score:" +selfAssessmentHealthData.getCognitionScore(), "" , null, R.drawable.image_10));
        datas.add(new DataVisualizationModel ("Self-assessment - activity",  "Score:" + selfAssessmentHealthData.getActivityScore(), "" , null, R.drawable.image_10));
        datas.add(new DataVisualizationModel ("Self-assessment - self worth",  "Score:" + selfAssessmentHealthData.getSelfWorthScore(), "" , null, R.drawable.image_10));
        datas.add(new DataVisualizationModel ("Self-assessment - interpersonal relations",  "Score:" + selfAssessmentHealthData.getInterpersonalRelationScore(), "" , null, R.drawable.image_10));
        datas.add(new DataVisualizationModel ("Self-assessment - life attitude",  "Score:" + selfAssessmentHealthData.getLifeAttitudeScore(), "" , null, R.drawable.image_10));

        return datas;
    }

    private void processHeartRateData(DataSet dataSet, HealthData healthData) {
        for (DataPoint dp : dataSet.getDataPoints()) {
            for (Field field : dp.getDataType().getFields()) {
                int heartRate = dp.getValue(field).asInt();
                healthData.setHeartRate(heartRate);
                setHealthData(healthData);
                Log.i(TAG, "Heart rate: " + heartRate);
            }
        }
    }

    private static final String TAG = "HealthDataManager";
    public void getFitnessPermission(Activity activity, long startTime, long endTime) {
        GoogleSignInOptionsExtension fitnessOptions =
                FitnessOptions.builder()
                        .addDataType(DataType.TYPE_HEART_RATE_BPM, FitnessOptions.ACCESS_READ)
                        .build();

        GoogleSignInAccount account = GoogleSignIn.getAccountForExtension(activity, fitnessOptions);

        if (!GoogleSignIn.hasPermissions(account, fitnessOptions)) {
            GoogleSignIn.requestPermissions(
                    activity, // your activity
                    GOOGLE_FIT_PERMISSIONS_REQUEST_CODE, // e.g., 1
                    account,
                    fitnessOptions);
        } else {
            Task<DataReadResponse> response = Fitness.getHistoryClient(activity, GoogleSignIn.getAccountForExtension(activity, fitnessOptions))
                    .readData(new DataReadRequest.Builder()
                            .read(DataType.TYPE_HEART_RATE_BPM)
                            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
                            .build());

            Log.i(TAG, "startTime: " + startTime + " endTime: " + endTime);
            response.addOnSuccessListener(dataReadResponse -> {
                HealthData healthData = getHealthData();
                Log.i(TAG, "SUCCESS : " + startTime + " endTime: " + endTime);
                if (dataReadResponse.getBuckets().size() > 0) {
                    for (Bucket bucket : dataReadResponse.getBuckets()) {
                        for (DataSet dataSet : bucket.getDataSets()) {
                            processHeartRateData(dataSet, healthData);
                        }
                    }
                } else {
                    for (DataSet dataSet : dataReadResponse.getDataSets()) {
                        processHeartRateData(dataSet, healthData);
                    }
                }
            }).addOnFailureListener(e -> {
                // 处理错误
                Log.i(TAG, "FAILURE startTime: " + startTime + " endTime: " + endTime);
            });

//            Task<DataReadResponse> response2 = Fitness.getHistoryClient(activity, GoogleSignIn.getAccountForExtension(activity, fitnessOptions))
//                    .readData(new DataReadRequest.Builder()
//                            .read(DataType.TYPE_DISTANCE_DELTA)
//                            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
//                            .build());
//
//            response2.addOnSuccessListener(dataReadResponse -> {
//                HealthData healthData = getHealthData();
//                if (dataReadResponse.getBuckets().size() > 0) {
//                    for (Bucket bucket : dataReadResponse.getBuckets()) {
//                        for (DataSet dataSet : bucket.getDataSets()) {
//                            processHeartRateData(dataSet, healthData);
//                        }
//                    }
//                } else {
//                    for (DataSet dataSet : dataReadResponse.getDataSets()) {
//                        processHeartRateData(dataSet, healthData);
//                    }
//                }
//            }).addOnFailureListener(e -> {
//                // 处理错误
//            });
//
//
//            Task<DataReadResponse> response3 = Fitness.getHistoryClient(activity, GoogleSignIn.getAccountForExtension(activity, fitnessOptions))
//                    .readData(new DataReadRequest.Builder()
//                            .read(DataType.TYPE_STEP_COUNT_CADENCE)
//                            .setTimeRange(startTime, endTime, TimeUnit.MILLISECONDS)
//                            .build());
//
//            response3.addOnSuccessListener(dataReadResponse -> {
//                HealthData healthData = getHealthData();
//                if (dataReadResponse.getBuckets().size() > 0) {
//                    for (Bucket bucket : dataReadResponse.getBuckets()) {
//                        for (DataSet dataSet : bucket.getDataSets()) {
//                            processHeartRateData(dataSet, healthData);
//                        }
//                    }
//                } else {
//                    for (DataSet dataSet : dataReadResponse.getDataSets()) {
//                        processHeartRateData(dataSet, healthData);
//                    }
//                }
//            }).addOnFailureListener(e -> {
//                // 处理错误
//            });


        }

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
