package com.ghotfall.spl.steps

def newPackage(map) {
    stage('New package') {
        bat "choco new ${map['id']} -t nssmjar --version ${map['version']} --maintainer ${map['group']}"
        bat "cp .\\target\\${map['id']}-${map['version']}-shaded.jar .\\${map['id']}\\tools\\${map['id']}.jar"
        dir(map['id']) {
            bat 'choco pack'
            bat "choco install ${map['id']} -s ."
        }
    }
}

return this