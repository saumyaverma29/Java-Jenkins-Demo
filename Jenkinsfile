pipeline {
  agent any

  tools {
    jdk 'jdk25'
    maven 'M3911'
  }

  options {
    timestamps()
    skipDefaultCheckout(true)
  }

  environment {
    // Keeps Maven repo under the workspace to avoid user-profile permission issues on agents
    MAVEN_OPTS = "-Dmaven.repo.local=.m2\\repository"
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Environment Info') {
      steps {
        bat '''
          echo === WHOAMI ===
          whoami
          echo === JAVA ===
          java -version
          echo === MAVEN ===
          mvn -version
        '''
      }
    }

    stage('Clean') {
      steps {
        bat 'mvn -B clean'
      }
    }

    stage('Compile') {
      steps {
        bat 'mvn -B -DskipTests=true compile'
      }
    }

    stage('Unit Tests') {
      steps {
        bat 'mvn -B test'
      }
      post {
        always {
          // Produces test result trend graph
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

  post {
    always {
      // Helps debugging when something fails
      archiveArtifacts artifacts: 'pom.xml', onlyIfSuccessful: false
    }
  }
}
