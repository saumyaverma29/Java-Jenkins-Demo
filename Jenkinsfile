pipeline {
  // If you have a dedicated Windows agent, set its label here (recommended).
  // Otherwise keep 'agent any' but ensure the job runs on a Windows node.
  agent any

  parameters {
    string(name: 'GIT_BRANCH', defaultValue: 'main', description: 'Branch to build (e.g., main, test)')
  }

  tools {
    jdk 'jdk25'
    maven 'M3911'
  }

  options { timestamps() }

  stages {
    stage('Checkout') {
      steps {
        checkout([
          $class: 'GitSCM',
          branches: [[name: "*/${params.GIT_BRANCH}"]],
          userRemoteConfigs: [[url: 'https://github.com/saumyaverma29/Java-Jenkins-Demo.git']]
        ])
      }
    }

    stage('Build & Test') {
      steps {
        bat 'mvn -B clean test'
      }
      post {
        always {
          junit 'target\\surefire-reports\\*.xml'
        }
      }
    }

    stage('Package') {
      steps {
        bat 'mvn -B package'
      }
      post {
        success {
          archiveArtifacts artifacts: 'target\\*.jar', fingerprint: true
        }
      }
    }
  }
}
