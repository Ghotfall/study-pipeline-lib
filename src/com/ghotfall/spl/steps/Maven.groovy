package com.ghotfall.spl.steps

static def build() {
    stage('Build') {
        echo 'Build:'
        bat 'mvn -B -DskipTests clean package'
    }
}

static def test(Boolean junitArchive = false) {
    stage('Test') {
        if (junitArchive) {
            echo 'Test:'
            bat 'mvn test'
            junit 'target/surefire-reports/*.xml'
        } else {
            echo 'Tests were skipped!'
        }
    }
}

return this