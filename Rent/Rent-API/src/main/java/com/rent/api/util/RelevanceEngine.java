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
        for(UserPost e : postList) {
            String[] titleSplit = e.getTitle().split("[\\s@&.?$+-,]+");
            String[] descriptionSplit = e.getDescription().split("[\\s@&.?$+-,]+");
            double score = 0;
            score = relevanceRank(filter, titleSplit, score);
            score = relevanceRank(filter, descriptionSplit, score);
            hashmap.put(e,score);
        }
        return sortByRelevanceRank(hashmap);
    }

    private static double relevanceRank(UserPostFilter filter, String[] splitStr, double score) {
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

    private static Map<String, Double> calculateIDFScore(String[] keywords) {
        return null;
    }

    private static List<UserPost> sortByRelevanceRank(Map<UserPost,Double> hashmap) {
        Set<Map.Entry<UserPost, Double>> set = hashmap.entrySet();
        List<Map.Entry<UserPost, Double>> list = new ArrayList<>(set);
        Collections.sort(list,(o1,o2)->Double.compare(o2.getValue(),o1.getValue()));
        List<UserPost> postList = new ArrayList<>();
        for(Map.Entry<UserPost, Double> e : list) {
            postList.add(e.getKey());
        }
        return postList;
    }
}
