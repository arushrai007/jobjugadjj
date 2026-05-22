package com.jobjugad.service;

import com.jobjugad.model.ResumeScoreResponse;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ResumeScorerService {
    private static final Set<String> STOP_WORDS = Set.of(
            "and", "or", "the", "a", "an", "with", "to", "of", "for", "in", "on", "at",
            "by", "from", "as", "is", "are", "this", "that", "will", "you", "your", "our"
    );

    public ResumeScoreResponse score(String resumeText, String jobDescription) {
        Set<String> jobKeywords = extractKeywords(jobDescription);
        Set<String> resumeKeywords = extractKeywords(resumeText);
        Set<String> matchedKeywords = jobKeywords.stream()
                .filter(resumeKeywords::contains)
                .collect(Collectors.toCollection(LinkedHashSet::new));

        Set<String> missingKeywords = jobKeywords.stream()
                .filter(keyword -> !matchedKeywords.contains(keyword))
                .collect(Collectors.toCollection(LinkedHashSet::new));

        int score = jobKeywords.isEmpty() ? 0 : Math.toIntExact(Math.round(matchedKeywords.size() * 100.0 / jobKeywords.size()));
        List<String> improvementAreas = missingKeywords.stream()
                .limit(10)
                .collect(Collectors.toList());

        String summary = String.format("Your resume matches %d%% of the job keywords. Add more details around %s.",
                score,
                improvementAreas.isEmpty() ? "general role keywords" : String.join(", ", improvementAreas));

        return new ResumeScoreResponse(score, List.copyOf(matchedKeywords), List.copyOf(missingKeywords), improvementAreas, summary);
    }

    private Set<String> extractKeywords(String text) {
        if (text == null) {
            return Set.of();
        }

        return Arrays.stream(text.toLowerCase().split("[^a-z0-9]+"))
                .map(String::trim)
                .filter(token -> token.length() > 2)
                .filter(token -> !STOP_WORDS.contains(token))
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}
