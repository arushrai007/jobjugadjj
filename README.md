# Job Jugad (Full stack app Based on Devops tools)

Job Jugad is a Java + Maven project that aggregates remote job listings from free public APIs and provides a resume scoring endpoint to compare a resume against a job description.

## Architecture

- Java Spring Boot backend
- REST APIs for job search and resume scoring
- Static frontend served from Spring Boot (`index.html`, `app.js`, `styles.css`)
- Docker container build
- Docker Compose for local backend + Prometheus + Grafana
- Jenkins pipeline and GitHub Actions CI
- Kubernetes manifests for deployment

## Features

- `/api/jobs/search?query=...` returns jobs from Remotive
- `/api/resume/score` evaluates how well a resume matches a job description
- Built-in Prometheus metrics via `/actuator/prometheus`
- Grafana dashboard configuration for monitoring

## Quick start

### Build locally

```bash
mvn -B -f backend/pom.xml clean package
```

### Run in Docker

```bash
docker build -t job-jugad:latest .
docker run --rm -p 8080:8080 job-jugad:latest
```

### Run with Docker Compose

```bash
docker-compose up --build
```

Then open:
- `http://localhost:8080` for the app
- `http://localhost:9090` for Prometheus
- `http://localhost:3000` for Grafana (admin/admin)

### Kubernetes deployment

```bash
kubectl apply -f k8s/
```

## CI/CD

- `Jenkinsfile` provides a basic Maven + Docker pipeline
- `.github/workflows/ci.yml` runs Maven build/tests and builds Docker image on push / pull request

## Notes

- Resume scoring is keyword-based and returns missing keywords and improvement suggestions
- Job scraping uses Remotive's public API, with a fallback sample dataset
- Grafana provisioning is included under `monitoring/grafana`
