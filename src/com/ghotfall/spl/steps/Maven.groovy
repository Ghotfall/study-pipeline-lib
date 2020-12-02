package com.ghotfall.spl.steps

def build() {
    stage('Build') {
        echo 'Build:'
        bat 'mvn -B -DskipTests clean package'
    }
}

def getInfo() {
    def pom = readMavenPom file: 'pom.xml'

    def data = [:]
    data['id'] = pom.getArtifactId()
    data['group'] = pom.getGroupId()
    data['version'] = pom.getVersion()

    return data
}

def test(Boolean junitArchive = false) {
    stage('Test') {
        if (junitArchive) {
            echo 'Test:'
            bat 'mvn test'
            junit 'target/surefire-reports/*.xml'
        } else {
            echo 'Tests were skipped!'
        }
    }
}

def updateVersion() {
    stage('Version update') {
        echo('Increasing project version...')

        def pom = readMavenPom file: 'pom.xml'
        echo "Current version is ${pom.getVersion()}"
            def (major, minor, patch) = pom.getVersion().tokenize('.')
            if (minor != null) {
                minor++
                String newVersion = "${major}.${minor}.${patch}"
                pom.setVersion(newVersion)
                writeMavenPom model: pom
                echo "New version is $newVersion"
                writePOMToRepo(newVersion)
            } else {
                echo 'Unknown version format, skipping'
            }
    }
}

private def writePOMToRepo(String version) {
    echo 'Writing changes to repository'

    bat """\
git status
git add pom.xml
git commit -m "Jenkins CI - New version: ${version}"
git push --set-upstream origin ${env.BRANCH_NAME}
"""
}

return this