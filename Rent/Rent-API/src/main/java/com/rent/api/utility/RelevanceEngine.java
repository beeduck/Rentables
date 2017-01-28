package com.rent.api.utility;

import com.rent.api.entities.listing.Listing;
import com.rent.api.entities.listing.Listing;

import java.util.*;

/**
 * Created by Asad on 11/17/2016.
 */
public class RelevanceEngine {

    //calls all methods
    public static List<Listing> sortByRelevance(List<Listing> listingList, String[] keywords) {
        Map<Listing,Double> hashmap = new HashMap<>();
        Map<String,Double> idfScores = calculateIDFScore(keywords,listingList);
        for(Listing e : listingList) {
            double score = 0;
            List<String> titleSplit = Arrays.asList(e.getTitle().toLowerCase().split("[\\s@&.?$+-,]+"));
            List<String> descriptionSplit = new ArrayList<>();
            if(e.getDescription() != null) {
                descriptionSplit = Arrays.asList(e.getDescription().toLowerCase().split("[\\s@&.?$+-,]+"));
            }
            score += calculateTermFrequencyScore(keywords, descriptionSplit);
            score += calculateTermFrequencyScore(keywords, titleSplit);
            for(String s : idfScores.keySet()) {
                if(titleSplit.contains(s) || descriptionSplit.contains(s)) {
                    score *= idfScores.get(s);
                }
            }
            hashmap.put(e,score);
        }
        return sortByRelevanceRank(hashmap);
    }

    //TODO: combine tf and idf into BM-25 model and feed to learning machine
    private static double calculateBMScore(Listing post, String[] keywords, Map<String, Double> idfMap) {
        double score = 0;
        for(String s : keywords) {

        }
        return score;
    }

    //this calculates the term frequency scores
    //term frequency is how often a term appears in a listing
    //the frequency is normalized by taking the square root
    private static double calculateTermFrequencyScore(String[] keywords, List<String> splitStr) {
        double score = 0;
        for(String e : keywords) {
            double count = 0;
            for(String s : splitStr) {
                if(s.equalsIgnoreCase(e)) {
                    count++;
                }
            }
            double relative = Math.sqrt(count);
            score += relative;
        }
        return score;
    }
    
    private static double numOfDocumentsWithTerm(String term, List<Listing> listingList) {
        double docCount = 0;
        for(Listing e : listingList) {
            List<String> titleSplit = Arrays.asList(e.getTitle().toLowerCase().split("[\\s@&.?$+-,]+"));
            if(titleSplit.contains(term.toLowerCase())) {
                docCount++;
            }
            else if(e.getDescription() != null) {
                List<String> descriptionSplit = Arrays.asList(e.getDescription().toLowerCase().split("[\\s@&.?$+-,]+"));
                if(descriptionSplit.contains(term.toLowerCase())) {
                    docCount++;
                }
            }
        }
        return docCount;
    }

    //calculates idf scores for all the terms in the query
    //idf score is how often the term appears in all documents
    //if a term appears very often in the description of many posts for example,
    //it will be considered less valuable
    private static Map<String,Double> calculateIDFScore(String[] keywords, List<Listing> listingList) {
        Map<String,Double> idfScoreMap = new HashMap<>();
        for(String s : keywords) {
            double docCount = numOfDocumentsWithTerm(s, listingList);
            double score = 1 + Math.log(listingList.size()/(docCount+1));
            idfScoreMap.put(s,score);
        }
        return idfScoreMap;
    }

    private static List<Listing> sortByRelevanceRank(Map<Listing,Double> hashmap) {
        Set<Map.Entry<Listing, Double>> set = hashmap.entrySet();
        List<Map.Entry<Listing, Double>> list = new ArrayList<>(set);
        Collections.sort(list,(o1,o2)->Double.compare(o2.getValue(),o1.getValue()));
        List<Listing> listingList = new ArrayList<>();
        for(Map.Entry<Listing, Double> e : list) {
            listingList.add(e.getKey());
        }
        return listingList;
    }
}
