pipeline {
  agent { label 'windows' }  // optional but recommended if you have mixed agents

  tools {
    jdk 'jdk25'
    maven 'M3911'
  }

  options { timestamps() }

  stages {
    stage('Checkout') {
      steps { checkout scm }
    }

    stage('Build & Test') {
      steps { bat 'mvn -B clean test' }
      post { always { junit 'target\\surefire-reports\\*.xml' } }
    }

    stage('Package') {
      steps { bat 'mvn -B package' }
      post { success { archiveArtifacts artifacts: 'target\\*.jar', fingerprint: true } }
    }
  }
}
