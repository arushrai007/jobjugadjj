package com.jobjugad.controller;

import com.jobjugad.model.JobPosting;
import com.jobjugad.service.JobScraperService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class JobController {
    private final JobScraperService jobScraperService;

    public JobController(JobScraperService jobScraperService) {
        this.jobScraperService = jobScraperService;
    }

    @GetMapping("/api/jobs/search")
    public List<JobPosting> searchJobs(@RequestParam(name = "query", defaultValue = "developer") String query) {
        return jobScraperService.searchJobs(query);
    }
}
