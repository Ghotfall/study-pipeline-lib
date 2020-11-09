package com.ghotfall.spl.steps

static void preBuild() {
    stage('Pre Build') {
        echo 'Cleaning workspace:'
        deleteDir()
    }
}

static void getRepo() {
    stage('Checkout') {
        echo 'Checkout:'
        checkout scm
    }
}

return this