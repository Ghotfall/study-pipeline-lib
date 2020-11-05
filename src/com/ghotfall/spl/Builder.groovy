package com.ghotfall.spl

void simpleBuild(Boolean junitArchive = false) {
    preBuild()
    getRepo()
    build()
    test(junitArchive)
}

void preBuild() {
    stage('Pre Build') {
        echo 'Cleaning workspace:'
        deleteDir
    }
}

void getRepo() {
    stage('Checkout') {
        echo 'Checkout:'
        checkout scm
    }
}

void build() {
    stage('Build') {
        echo 'Build:'
        bat 'mvn -B -DskipTests clean package'
    }
}

void test(Boolean junitArchive) {
    stage('Test') {
        echo 'Test:'
        bat 'mvn test'
        if (junitArchive) {
            junit 'target/surefire-reports/*.xml'
        }
    }
}