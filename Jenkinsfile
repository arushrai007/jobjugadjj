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
        sh 'mvn -B -f backend/pom.xml clean package'
      }
    }
    stage('Run Tests') {
      steps {
        sh 'mvn -B -f backend/pom.xml test'
      }
    }
    stage('Build Docker Image') {
      steps {
        sh 'docker build -t job-jugad:latest .'
      }
    }
  }
  post {
    success {
      echo 'Build succeeded and Docker image created.'
    }
    failure {
      echo 'Build failed. Please inspect logs.'
    }
  }
}
