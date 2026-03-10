pipeline {
  agent any

  tools {
    jdk 'jdk25'
    maven 'M3911'
  }

  options {
    timestamps()
    skipDefaultCheckout(true)   // important when we do our own checkout
  }

  stages {
    stage('Checkout (Selected Branch)') {
      steps {
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
          def raw = (params.SLEEP_TIME ?: '0s').toString().trim()   // e.g. "10s"
          int seconds = raw.replace('s','') as int                  // -> 10
          echo "Sleeping for ${seconds}s"
          sleep time: seconds, unit: 'SECONDS'
        }
      }
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
