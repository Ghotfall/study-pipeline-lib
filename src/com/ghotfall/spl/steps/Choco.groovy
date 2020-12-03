package com.ghotfall.spl.steps

def pack(map) {
    stage('Build package') {
        bat "choco new ${map['id']} -t nssmjar --version ${map['version']} --maintainer ${map['group']}"
        bat "copy .\\target\\${map['id']}-${map['version']}-shaded.jar .\\${map['id']}\\tools\\${map['id']}.jar"
        dir(map['id']) {
            bat 'choco pack'
        }
    }
}

def install(packageName) {
    stage('Install package') {
        dir(packageName) {
            bat "choco install ${packageName} -s . -y"
        }
    }
}

return this