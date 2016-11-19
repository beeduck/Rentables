package com.rent.api.util;

import com.rent.data.dataaccess.api.entities.listing.UserPost;
import com.rent.utility.filters.UserPostFilter;

import java.util.*;

/**
 * Created by Asad on 11/17/2016.
 */
public class RelevanceEngine {

    public static List<UserPost> sortByRelevance(List<UserPost> postList, UserPostFilter filter) {
        Map<UserPost,Double> hashmap = new HashMap<>();
        Map<String,Double> idfScores = calculateIDFScore(filter.getKeywords(),postList);
        for(UserPost e : postList) {
            List<String> titleSplit = Arrays.asList(e.getTitle().toLowerCase().split("[\\s@&.?$+-,]+"));
            List<String> descriptionSplit = Arrays.asList(e.getDescription().toLowerCase().split("[\\s@&.?$+-,]+"));
            double score = 0;
            score += relevanceRank(filter, titleSplit, score);
            score += relevanceRank(filter, descriptionSplit, score);
            for(String s : idfScores.keySet()) {
                if(titleSplit.contains(s) || descriptionSplit.contains(s)) {
                    score += idfScores.get(s);
                }
            }
            hashmap.put(e,score);
        }
        return sortByRelevanceRank(hashmap);
    }

    //this calculates the term frequency scores
    private static double relevanceRank(UserPostFilter filter, List<String> splitStr, double score) {
        for(int i=0; i<filter.getKeywords().length; i++) {
            double count = 0;
            for(String s : splitStr) {
                if(s.equalsIgnoreCase(filter.getKeywords()[i])) {
                    count++;
                }
            }
            double relative = Math.sqrt(count);
            score += relative;
        }
        return score;
    }

    //calculates idf scores for all the terms in the query
    private static Map<String,Double> calculateIDFScore(String[] keywords, List<UserPost> postList) {
        Map<String,Double> idfScoreMap = new HashMap<>();
        for(String s : keywords) {
            double docCount = 0;
            for(UserPost e : postList) {
                List<String> titleSplit = Arrays.asList(e.getTitle().toLowerCase().split("[\\s@&.?$+-,]+"));
                List<String> descriptionSplit = Arrays.asList(e.getDescription().toLowerCase().split("[\\s@&.?$+-,]+"));
                if(titleSplit.contains(s.toLowerCase()) || descriptionSplit.contains(s.toLowerCase())) {
                    docCount++;
                }
            }
            double score = 1 + Math.log(postList.size()/(docCount+1));
            idfScoreMap.put(s,score);
        }
        return idfScoreMap;
    }

    //determine coordination factor
    private static double calculateCoordinationFactor(UserPost userPost, String[] keywords) {
        List<String> titleSplit = Arrays.asList(userPost.getTitle().toLowerCase().split("[\\s@&.?$+-,]+"));
        List<String> descriptionSplit = Arrays.asList(userPost.getDescription().toLowerCase().split("[\\s@&.?$+-,]+"));
        double count = 0;
        for(String s : keywords) {
            if(titleSplit.contains(s.toLowerCase()) || descriptionSplit.contains(s.toLowerCase())) {
                count++;
            }
        }

        return count;
    }

    private static List<UserPost> sortByRelevanceRank(Map<UserPost,Double> hashmap) {
        Set<Map.Entry<UserPost, Double>> set = hashmap.entrySet();
        List<Map.Entry<UserPost, Double>> list = new ArrayList<>(set);
        Collections.sort(list,(o1,o2)->Double.compare(o2.getValue(),o1.getValue()));
        List<UserPost> postList = new ArrayList<>();
        for(Map.Entry<UserPost, Double> e : list) {
            System.out.println(e.getValue());
            postList.add(e.getKey());
        }
        return postList;
    }
}
