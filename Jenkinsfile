pipeline {
    agent any

    tools {
        jdk 'JDK_17'           // Your configured JDK 17
        maven 'Maven_3.9'   // Your Maven 3.9.11 tool
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out projects branch...'
                git branch: 'projects', url: 'https://github.com/arunkumaria/MyGitApps.git'
            }
        }

        stage('Build') {
            steps {
                echo 'Building PayPal Payment App with Maven...'
                sh '''
                cd paypal-payment-app
                mvn clean install -DskipTests
                '''
            }
        }

        stage('List Artifacts') {
            steps {
                echo 'Listing built artifacts...'
                sh 'ls -l paypal-payment-app/target'
            }
        }
    }

    post {
        success {
            echo 'Build succeeded!'
        }
        failure {
            echo 'Build failed. Check console output.'
        }
    }
}
