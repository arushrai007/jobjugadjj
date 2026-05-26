pipeline {
    agent any

    stages {

        stage('Clean Workspace') {
            steps {
                deleteDir()
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'main', url: 'YOUR_GITHUB_REPO'
            }
        }

        stage('Build') {
            steps {
                bat 'docker run --rm -v "%cd%":/workspace -w /workspace maven:3.9.9-eclipse-temurin-21 mvn -B -f backend/pom.xml clean package'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker compose down'
                bat 'docker build --no-cache -t job-jugad:latest .'
            }
        }

        stage('Deploy') {
            steps {
                bat 'docker compose up --build -d'
            }
        }
    }
}