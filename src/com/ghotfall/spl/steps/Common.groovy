package com.ghotfall.spl.steps

static def preBuild() {
    stage('Pre Build') {
        echo 'Cleaning workspace:'
        deleteDir()
    }
}

static def getRepo() {
    stage('Checkout') {
        echo 'Checkout:'
        checkout scm
    }
}

return this