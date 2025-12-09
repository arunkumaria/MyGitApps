pipeline {
    agent any

    tools {
        maven 'Maven_3.9'  // Must match your Jenkins Maven configuration
        jdk 'JDK_17'       // Updated to JDK 17
    }

    environment {
        PROJECT_DIR = 'paypal-payment-app'
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                echo "Checking out projects branch..."
                git branch: 'projects', url: 'https://github.com/arunkumaria/MyGitApps.git'
            }
        }

        stage('Build') {
            steps {
                echo "Building project with Maven..."
                withMaven(maven: 'Maven_3.9') {
                    sh "mvn -f ${env.PROJECT_DIR}/pom.xml clean install"
                }
            }
        }

        stage('List Artifacts') {
            steps {
                echo "Listing built artifacts..."
                sh "ls -l ${env.PROJECT_DIR}/target"
            }
        }

        stage('Archive Artifacts') {
            steps {
                echo "Archiving JAR files..."
                archiveArtifacts artifacts: "${env.PROJECT_DIR}/target/*.jar", fingerprint: true
            }
        }

        stage('Publish Test Results') {
            steps {
                echo "Publishing JUnit test results..."
                junit "${env.PROJECT_DIR}/target/surefire-reports/*.xml"
            }
        }
    }

    post {
        success {
            echo "Build completed successfully!"
        }
        failure {
            echo "Build failed. Check console output."
        }
    }
}

