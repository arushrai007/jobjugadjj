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
                git url: 'https://github.com/arushrai007/jobjugadjj.git'
            }
        }

        stage('Build Maven Project') {
            steps {
                bat '''
                docker run --rm ^
                -v "%cd%":/workspace ^
                -w /workspace ^
                maven:3.9.9-eclipse-temurin-21 ^
                mvn -B -f backend/pom.xml clean package -DskipTests
                '''
            }
        }

        stage('Build Docker Image') {
            steps {
                bat 'docker build --no-cache -t jj-pipeline-backend .'
            }
        }

        stage('Deploy Containers') {
            steps {
                bat '''
                docker compose down --remove-orphans
                docker compose up --build -d
                '''
            }
        }
    }

    post {

        success {
            echo 'Deployment successful!'
        }

        failure {
            echo 'Build failed.'
        }
    }
}