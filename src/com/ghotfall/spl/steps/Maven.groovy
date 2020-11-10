package com.ghotfall.spl.steps

def build() {
    stage('Build') {
        echo 'Build:'
        bat 'mvn -B -DskipTests clean package'
    }
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
//        try {
            def (major, minor, patch) = pom.getVersion().tokenize('.')
            minor++
            pom.setVersion("${major}.${minor}.${patch}")
            writeMavenPom model: pom
            echo "New version is ${pom.getVersion()}"

            echo 'Writing changes to repository'
            git status
            git add 'pom.xml'
            git commit -m "Jenkins CI - Project version increment"
            git push origin
//        } catch(Exception ignored) {
//            echo 'Unknown version format, skipping version increment'
//        }
    }
}

return this