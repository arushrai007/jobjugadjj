package com.jobjugad.controller;

import com.jobjugad.model.ResumeScoreResponse;
import com.jobjugad.service.ResumeScorerService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResumeController {
    private final ResumeScorerService resumeScorerService;

    public ResumeController(ResumeScorerService resumeScorerService) {
        this.resumeScorerService = resumeScorerService;
    }

    @PostMapping(value = "/api/resume/score", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResumeScoreResponse scoreResume(@RequestBody ResumeScoreRequest request) {
        return resumeScorerService.score(request.getResumeText(), request.getJobDescription());
    }

    public static class ResumeScoreRequest {
        private String resumeText;
        private String jobDescription;

        public String getResumeText() {
            return resumeText;
        }

        public void setResumeText(String resumeText) {
            this.resumeText = resumeText;
        }

        public String getJobDescription() {
            return jobDescription;
        }

        public void setJobDescription(String jobDescription) {
            this.jobDescription = jobDescription;
        }
    }
}
