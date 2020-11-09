package com.ghotfall.spl.steps

def preBuild() {
    stage('Pre Build') {
        echo 'Cleaning workspace:'
        deleteDir()
    }
}

def getRepo() {
    stage('Checkout') {
        echo 'Checkout:'
        checkout scm
    }
}

return this