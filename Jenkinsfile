pipeline {
    agent any

    stages {

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                bat 'docker run --rm -v "%cd%":/workspace -w /workspace maven:3.9.9-eclipse-temurin-21 mvn -B -f backend/pom.xml clean package'
            }
        }

        stage('Run Tests') {
            steps {
                bat 'docker run --rm -v "%cd%":/workspace -w /workspace maven:3.9.9-eclipse-temurin-21 mvn -B -f backend/pom.xml test'
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build -t job-jugad:latest .'
            }
        }

        stage('Deploy Container') {
    steps {
        bat '''
        docker compose down
        docker system prune -f
        docker compose up --build -d
        '''
    }
}

    post {

        success {
            echo 'Deployment successful!'
        }

        failure {
            echo 'Build failed. Please inspect logs.'
        }
    }
}