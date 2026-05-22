package com.jobjugad.model;

import java.util.List;

public class ResumeScoreResponse {
    private int score;
    private List<String> matchedKeywords;
    private List<String> missingKeywords;
    private List<String> improvementAreas;
    private String summary;

    public ResumeScoreResponse() {
    }

    public ResumeScoreResponse(int score, List<String> matchedKeywords, List<String> missingKeywords, List<String> improvementAreas, String summary) {
        this.score = score;
        this.matchedKeywords = matchedKeywords;
        this.missingKeywords = missingKeywords;
        this.improvementAreas = improvementAreas;
        this.summary = summary;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public List<String> getMatchedKeywords() {
        return matchedKeywords;
    }

    public void setMatchedKeywords(List<String> matchedKeywords) {
        this.matchedKeywords = matchedKeywords;
    }

    public List<String> getMissingKeywords() {
        return missingKeywords;
    }

    public void setMissingKeywords(List<String> missingKeywords) {
        this.missingKeywords = missingKeywords;
    }

    public List<String> getImprovementAreas() {
        return improvementAreas;
    }

    public void setImprovementAreas(List<String> improvementAreas) {
        this.improvementAreas = improvementAreas;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
