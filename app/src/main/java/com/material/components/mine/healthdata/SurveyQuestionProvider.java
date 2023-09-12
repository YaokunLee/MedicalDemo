package com.material.components.mine.healthdata;

import java.util.ArrayList;
import java.util.Random;

public class SurveyQuestionProvider {

    // 睡眠部分
    private static  String description_sleep_array[] = {
            "I struggled to fall asleep at night",
            "I woke up several times during the night.",
            "I felt exhausted even after a full night's sleep.",
            "I experienced nightmares or distressing dreams.",
            "I found myself oversleeping more often than usual.",
            "My sleep pattern was irregular.",
            "I felt restless even when I tried to relax in bed.",
            "I often woke up feeling unrefreshed.",
            "I had difficulty getting out of bed in the morning.",
    };

    // 食欲部分
    private static  String description_shiyu_array[] = {
            "I frequently skipped meals without realizing it.",
            "The taste of food seemed bland or unappealing to me.",
            "Even when I was hungry, I didn't have the energy to eat.",
            "I often had no appetite.",
            "I neglected proper nutrition and often went for easy or junk foods."
    };

    // emotion
    private static  String description_emotion_array[] = {
            "I felt sad or down most of the time.",
            "I felt hopeless about the future.",
            "I often feel like crying.",
            "I often felt worthless or guilty.",
            "I was easily frustrated or angered by trivial things.",
            "I felt a sense of impending doom or anxiety for no reason.",
            "I felt like I was on an emotional roller coaster, with rapid mood swings.",
            "I felt a persistent sense of guilt or self-blame.",
            "I had a hard time feeling positive emotions, even when good things happened.",
            "I felt a deep sense of isolation, even when surrounded by people.",
            "I found it hard to connect emotionally with others.",
    };


    // 认知
    private static  String description_coginion_array[] = {
            "I had trouble concentrating or making decisions.",
            "I frequently forgot things or became easily distracted.",
            "My thoughts were often negative or self-critical.",
            "Tasks that required thinking felt more challenging than before.",
            "I found it hard to organize my thoughts or express myself clearly.",
            "It took me longer to process information or understand things.",
            "I felt indecisive, even about simple choices.",
            "It was difficult for me to stay focused on a single task.",
            "I often lost my train of thought in the middle of conversations.",
            "I felt overwhelmed by complex tasks or multiple instructions."
    };

    private static  String description_interest_activities_array[] = {
            "I lost interest in activities I once enjoyed.",
            "I felt too tired or unmotivated to participate in hobbies or pastimes.",
            "I withdrew from social activities or gatherings.",
            "Activities that used to bring joy felt meaningless or burdensome.",
            "I avoided or postponed tasks, even if they were important.",
            "I found it hard to initiate or complete projects.",
            "Even simple activities felt draining or overwhelming.",
            "I felt detached or uninvolved when participating in activities."
    };

    private static  String description_self_worth_array[] = {
            "I felt like I was a burden to others.",
            "I doubted my worth or abilities frequently.",
            "I constantly criticized myself or focused on my flaws.",
            "I felt unimportant or invisible to others.",
            "I believed I was not good enough, regardless of my achievements.",
            "Negative self-talk dominated my thoughts.",
            "I struggled to recognize or appreciate my strengths.",
            "I felt undeserving of love, kindness, or success."
    };

    private static  String description_interpersonal_relations_array[] = {
            "I felt distant or disconnected from loved ones.",
            "I struggled to communicate my feelings or needs.",
            "I often felt misunderstood or alone in groups.",
            "I avoided social interactions or dreaded social events.",
            "I found it hard to trust or open up to others.",
            "I felt more sensitive to perceived rejections or criticisms.",
            "I felt a lack of emotional connection in my relationships.",
            "I isolated myself, even from those who wanted to support me."
    };

    private static  String description_life_attitude_array[] = {
            "I felt indifferent or cynical about my future.",
            "I struggled to find meaning or purpose in life.",
            "Thoughts of self-harm or suicide occasionally crossed my mind.",
            "I believed things would never get better for me.",
            "I had a constant sense of hopelessness or despair.",
            "I felt trapped or stuck in my current situation.",
            "I frequently thought about escaping or disappearing.",
            "I saw little point in planning for the future or setting goals."
    };

    public static final String TYPE_SLEEP = "sleep";
    public static final String TYPE_APPETITE = "appetite";
    public static final String TYPE_EMOTION = "emotion";
    public static final String TYPE_COGNITION = "cognition";
    public static final String TYPE_INTEREST_ACTIVITIES = "interest_activities";
    public static final String TYPE_SELF_WORTH = "self_worth";
    public static final String TYPE_INTERPERSONAL_RELATIONS = "interpersonal_relations";
    public static final String TYPE_LIFE_ATTITUDE = "life_attitude";

    private static final String[] types = {
            TYPE_SLEEP,
            TYPE_APPETITE,
            TYPE_EMOTION,
            TYPE_COGNITION,
            TYPE_INTEREST_ACTIVITIES,
            TYPE_SELF_WORTH,
            TYPE_INTERPERSONAL_RELATIONS,
            TYPE_LIFE_ATTITUDE
    };


    public static ArrayList<SurveyQuestionData> getSampleQuestions() {
        ArrayList<SurveyQuestionData> resultList = new ArrayList<>();

        // 将所有数组和它们的类型放入一个数组中，方便迭代
        String[][] questionArrays = {
                description_sleep_array, description_shiyu_array, description_emotion_array, description_coginion_array,
                description_interest_activities_array, description_self_worth_array, description_interpersonal_relations_array,
                description_life_attitude_array
        };

        Random random = new Random(System.currentTimeMillis());
        for(int i = 0; i < questionArrays.length; i++) {
            for(int j = 0; j < 2; j++) {
                int randomIndex = random.nextInt(questionArrays[i].length);
                resultList.add(new SurveyQuestionData(questionArrays[i][randomIndex], types[i]));

                // Ensure unique questions are picked from the array
                String temp = questionArrays[i][randomIndex];
                questionArrays[i][randomIndex] = questionArrays[i][questionArrays[i].length - 1];
                questionArrays[i][questionArrays[i].length - 1] = temp;
            }
        }

        return resultList;
    }

}
