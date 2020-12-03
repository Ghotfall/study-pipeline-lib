package com.ghotfall.spl

import com.ghotfall.spl.steps.Choco
import com.ghotfall.spl.steps.Common
import com.ghotfall.spl.steps.Maven
import groovy.transform.Field

@Field
String nodeLabel

def simpleBuild(String nodeLabel, boolean deploy = false) {
    this.nodeLabel = nodeLabel
    echo 'Started new build'

    def branch = env.BRANCH_NAME

    switch (branch) {
        case null:
            throw new IllegalArgumentException("Can't define repository branch")
            break

        case 'master':
            echo 'Building "master" branch...'
            bMaster(deploy)
            break

        default:
            echo "Building \"$branch\" branch..."
            bFeature()
            break
    }
}


def bMaster(boolean deploy = false) {
    def stepsCommon = new Common()
    def stepsMaven = new Maven()
    def stepsChoco = new Choco()
    node(this.nodeLabel) {
        stepsCommon.preBuild()
        stepsCommon.getRepo()
        stepsMaven.updateVersion()
        stepsMaven.build()
        stepsMaven.test(true)

        pomData = stepsMaven.getInfo()
        stepsChoco.pack(pomData)
        if (deploy) {
            stepsChoco.install(pomData['id'])
        }
    }
}

def bFeature() {
    def stepsCommon = new Common()
    def stepsMaven = new Maven()
    node(this.nodeLabel) {
        stepsCommon.preBuild()
        stepsCommon.getRepo()
        stepsMaven.build()
    }
}

return this