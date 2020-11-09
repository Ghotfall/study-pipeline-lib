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
    assert branch != null: "Branch data not found"

    switch (branch) {
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
    node(this.nodeLabel) {
        Common.preBuild()
        Common.getRepo()
        Maven.build()
        Maven.test(true)
    }
}

def bFeature() {
    node(this.nodeLabel) {
        Common.preBuild()
        Common.getRepo()
        Maven.build()
    }
}

return this