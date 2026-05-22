const searchJobsBtn = document.getElementById('searchJobsBtn');
const scoreResumeBtn = document.getElementById('scoreResumeBtn');

searchJobsBtn.addEventListener('click', () => {
    const query = document.getElementById('jobQuery').value.trim();
    searchJobs(query);
});

scoreResumeBtn.addEventListener('click', () => {
    const jobDescription = document.getElementById('jobDescription').value.trim();
    const resumeText = document.getElementById('resumeText').value.trim();
    scoreResume(jobDescription, resumeText);
});

async function searchJobs(query) {
    const results = document.getElementById('jobResults');
    results.innerHTML = '<p>Loading jobs…</p>';
    try {
        const response = await fetch(`/api/jobs/search?query=${encodeURIComponent(query)}`);
        const jobs = await response.json();
        if (!Array.isArray(jobs) || jobs.length === 0) {
            results.innerHTML = '<p>No jobs found. Try a different query.</p>';
            return;
        }

        results.innerHTML = jobs.map(job => `
            <article class="job-card">
                <h3>${job.title}</h3>
                <p><strong>${job.company}</strong> · ${job.location} · ${job.source}</p>
                <p>${job.description ? job.description.substring(0, 220) + '...' : ''}</p>
                <a href="${job.url}" target="_blank">View job</a>
            </article>
        `).join('');
    } catch (error) {
        results.innerHTML = '<p class="error">Unable to fetch jobs right now.</p>';
        console.error(error);
    }
}

async function scoreResume(jobDescription, resumeText) {
    const resultContainer = document.getElementById('scoreResult');
    resultContainer.innerHTML = '<p>Scoring resume…</p>';

    try {
        const response = await fetch('/api/resume/score', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ jobDescription, resumeText }),
        });

        const payload = await response.json();
        resultContainer.innerHTML = `
            <div class="score-block">
                <h3>Resume Fit: ${payload.score}%</h3>
                <p>${payload.summary}</p>
                <div class="score-lists">
                    <div><strong>Matched Keywords</strong><br/>${payload.matchedKeywords.length ? payload.matchedKeywords.join(', ') : 'None'}</div>
                    <div><strong>Missing Keywords</strong><br/>${payload.missingKeywords.length ? payload.missingKeywords.join(', ') : 'None'}</div>
                </div>
            </div>
        `;
    } catch (error) {
        resultContainer.innerHTML = '<p class="error">Unable to score resume right now.</p>';
        console.error(error);
    }
}
