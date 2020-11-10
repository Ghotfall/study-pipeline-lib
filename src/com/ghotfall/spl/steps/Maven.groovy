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
        String[] version = pom.getVersion().split('.')
        try {
            int minorVersion = Integer.parseInt(version[1])
            version[1] = ++minorVersion
            pom.setVersion(version.join('.'))
            writeMavenPom model: pom
            echo "New version is ${pom.getVersion()}"

            echo 'Writing changes to repository'
            git status
            git add 'pom.xml'
            git commit -m "Jenkins CI - Project version increment"
            git push origin
        } catch(NumberFormatException ignored) {
            echo 'Unknown version format, skipping version increment'
        }
    }
}

return this