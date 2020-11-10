package com.ghotfall.spl.steps

class Common {
    static def steps

    static void preBuild() {
        steps.stage('Pre Build') {
            steps.echo 'Cleaning workspace:'
            steps.deleteDir()
        }
    }

    static void getRepo() {
        steps.stage('Checkout') {
            steps.echo 'Checkout:'
            steps.checkout scm
        }
    }
}