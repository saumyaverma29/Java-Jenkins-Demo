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
    stage('Checkout (Selected Branch)') {
      steps {
        // IMPORTANT: Use BRANCH_NAME parameter (falls back to main if empty)
        checkout([
          $class: 'GitSCM',
          branches: [[name: "*/${params.BRANCH_NAME ?: 'main'}"]],
          userRemoteConfigs: [[url: 'https://github.com/saumyaverma29/Java-Jenkins-Demo.git']]
        ])
      }
    }

    stage('Sleep (Demo)') {
      steps {
        script {
          // Convert "5s" -> 5; default to 0 if blank
          def raw = (params.SLEEP_TIME ?: '0s').toString().trim()
          int seconds = raw.replace('s','') as int
          echo "Sleeping for ${seconds} seconds (SLEEP_TIME=${raw})"
          sleep time: seconds, unit: 'SECONDS'
        }
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
      archiveArtifacts artifacts: 'pom.xml', onlyIfSuccessful: false
    }
  }
}
