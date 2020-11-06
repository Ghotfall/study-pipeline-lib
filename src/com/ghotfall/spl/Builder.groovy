package com.ghotfall.spl

import groovy.transform.Field

@Field
String nodeLabel

def simpleBuild(String nodeLabel, Boolean junitArchive = false) {
    this.nodeLabel = nodeLabel

    preBuild()
    getRepo()
    build()
    test(junitArchive)
}

def preBuild() {
    node(nodeLabel) {
        stage('Pre Build') {
            echo 'Cleaning workspace:'
//            if (fileExists('.git'))
//                git clean -gdf
//            else
                deleteDir()
        }
    }
}

def getRepo() {
    node(nodeLabel) {
        stage('Checkout') {
            echo 'Checkout:'
            checkout scm
        }
    }
}

def build() {
    node(nodeLabel) {
        stage('Build') {
            echo 'Build:'
            bat 'mvn -B -DskipTests clean package'
        }
    }
}

def test(Boolean junitArchive) {
    node(nodeLabel) {
        stage('Test') {
            echo 'Test:'
            bat 'mvn test'
            if (junitArchive) {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }
}

return this