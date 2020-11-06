package com.ghotfall.spl

def simpleBuild(String nodeLabel, Boolean junitArchive = false) {
    node(nodeLabel) {
        preBuild()
        getRepo()
        build()
        test(junitArchive)
    }
}

def preBuild() {
    stage('Pre Build') {
        echo 'Cleaning workspace:'
        git clean -xdf
    }
}

def getRepo() {
    stage('Checkout') {
        echo 'Checkout:'
        checkout scm
    }
}

def build() {
    stage('Build') {
        echo 'Build:'
        bat 'mvn -B -DskipTests clean package'
    }
}

def test(Boolean junitArchive) {
    stage('Test') {
        echo 'Test:'
        bat 'mvn test'
        if (junitArchive) {
            junit 'target/surefire-reports/*.xml'
        }
    }
}

return this