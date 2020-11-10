package com.ghotfall.spl

import com.ghotfall.spl.steps.Common
import com.ghotfall.spl.steps.Maven
import groovy.transform.Field

@Field
String nodeLabel

@Field
def steps

def simpleBuild(String nodeLabel, steps) {
    this.steps = steps
    this.nodeLabel = nodeLabel

    Common.steps = this.steps

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

def bMaster() {
    def stepsMaven = new Maven()
    node(this.nodeLabel) {
        Common.preBuild()
        Common.getRepo()
        stepsMaven.build()
        stepsMaven.test(true)
    }
}

def bFeature() {
    def stepsMaven = new Maven()
    node(this.nodeLabel) {
        Common.preBuild()
        Common.getRepo()
        stepsMaven.build()
    }
}

return this