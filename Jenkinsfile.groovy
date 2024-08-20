pipeline {
    agent any
    tools {
        maven 'Maven 3.9.9'
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building the code...'
            }
        }
        stage('Unit and Integration Tests') {
            steps {
                echo 'Running unit and integration tests...'
            }
            post {
                always {
                    emailext(
                        to: 'mirandalpena@gmail.com',
                        subject: "Unit and Integration Tests - ${currentBuild.currentResult}",
                        body: "The unit and integration tests have ${currentBuild.currentResult}. Please check the logs.",
                        attachmentsPattern: '**/target/surefire-reports/*.xml'
                    )
                }
            }
        }
        stage('Code Analysis') {
            steps {
                echo 'Analyzing code quality...'
            }
        }
        stage('Security Scan') {
            steps {
                echo 'Performing security scan...'
                sh 'zap-cli quick-scan --self-contained http://your-app-url'
            }
            post {
                always {
                    emailext(
                        to: 'mirandalpena@gmail.com',
                        subject: "Security Scan - ${currentBuild.currentResult}",
                        body: "The security scan has ${currentBuild.currentResult}. Please check the logs.",
                        attachmentsPattern: '**/zap-reports/*.html'
                    )
                }
            }
        }
        stage('Deploy to Staging') {
            steps {
                echo 'Deploying to Staging environment...'
            }
        }
        stage('Integration Tests on Staging') {
            steps {
                echo 'Running integration tests on Staging environment...'
            }
        }
        stage('Deploy to Production') {
            steps {
                echo 'Deploying to Production environment...'
            }
        }
    }
}