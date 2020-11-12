package com.ghotfall.spl

import com.ghotfall.spl.steps.Common
import com.ghotfall.spl.steps.Maven
import groovy.transform.Field

@Field
String nodeLabel

def simpleBuild(String nodeLabel) {
    this.nodeLabel = nodeLabel
    echo 'Started new build'

    def branch = env.BRANCH_NAME

    switch (branch) {
        case null:
            throw new IllegalArgumentException("Can't define repository branch")
            break

        case 'master':
            echo 'Building "master" branch...'
            bMaster()
            break

        default:
            echo "Building \"$branch\" branch..."
            bFeature()
            break
    }
}

def dockerAdvancedBuild(String nodeLabel, String image, Closure body) {
    this.nodeLabel = nodeLabel
    node(this.nodeLabel) {
        docker.image(image).inside {
            body()
        }
    }
}

def bMaster() {
    def stepsCommon = new Common()
    def stepsMaven = new Maven()
    node(this.nodeLabel) {
        stepsCommon.preBuild()
        stepsCommon.getRepo()
        stepsMaven.updateVersion()
        stepsMaven.build()
        stepsMaven.test(true)
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