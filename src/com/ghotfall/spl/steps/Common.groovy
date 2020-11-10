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
//        checkout scm
//        checkout([$class: 'GitSCM', extensions: [[$class: 'LocalBranch', localBranch: "**"]]])
        checkout([
                $class: 'GitSCM',
                branches: scm.branches,
                doGenerateSubmoduleConfigurations: scm.doGenerateSubmoduleConfigurations,
                extensions: scm.extensions + [[$class: 'LocalBranch', localBranch: "**"]],
                userRemoteConfigs: scm.userRemoteConfigs
        ])
    }
}

return this