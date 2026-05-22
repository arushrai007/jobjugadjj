package com.jobjugad.service;

import com.jobjugad.model.JobPosting;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class JobScraperService {
    private final RestTemplate restTemplate;
    private final String remotiveApiUrl;

    public JobScraperService(@Value("${remotive.api.url}") String remotiveApiUrl) {
        this.restTemplate = new RestTemplate();
        this.remotiveApiUrl = remotiveApiUrl;
    }

    public List<JobPosting> searchJobs(String query) {
        String encodedQuery = UriUtils.encodeQueryParam(query == null ? "" : query, StandardCharsets.UTF_8);
        String url = remotiveApiUrl + encodedQuery;

        try {
            Map<?, ?> response = restTemplate.getForObject(url, Map.class);
            if (response == null || !response.containsKey("jobs")) {
                return fallbackJobs(query);
            }

            List<?> rawJobs = (List<?>) response.get("jobs");
            List<JobPosting> jobs = new ArrayList<>();
            for (Object rawJob : rawJobs) {
                if (!(rawJob instanceof Map)) {
                    continue;
                }
                Map<?, ?> jobData = (Map<?, ?>) rawJob;
                String id = stringValue(jobData.get("id"), stringValue(jobData.get("job_id"), ""));
                String title = stringValue(jobData.get("title"), "Unknown title");
                String company = stringValue(jobData.get("company_name"), "Unknown company");
                String location = stringValue(jobData.get("candidate_required_location"), "Remote");
                String urlValue = stringValue(jobData.get("url"), stringValue(jobData.get("job_url"), ""));
                String description = stringValue(jobData.get("description"), "");
                jobs.add(new JobPosting(id, title, company, location, urlValue, "Remotive", description));
            }
            return jobs;
        } catch (Exception ex) {
            return fallbackJobs(query);
        }
    }

    private List<JobPosting> fallbackJobs(String query) {
        List<JobPosting> fallback = new ArrayList<>();
        fallback.add(new JobPosting("fallback-1", "Software Engineer", "Job Jugad Labs", "Remote", "https://remotive.com", "Local", "Build backend services and automation."));
        fallback.add(new JobPosting("fallback-2", "Data Analyst", "Job Jugad Analytics", "Remote", "https://remotive.com", "Local", "Analyze job and resume scoring signals."));
        return fallback;
    }

    private String stringValue(Object value, String defaultValue) {
        if (value == null) {
            return defaultValue;
        }
        return String.valueOf(value);
    }
}
