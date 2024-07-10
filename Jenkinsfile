pipeline {
    agent any

    environment {
            GITHUB_TOKEN = credentials('github-token')
            }

    options {
        // Set log rotation to keep the last 10 builds and discard older ones
        buildDiscarder(logRotator(numToKeepStr: '1', daysToKeepStr: '1'))
    }

    tools {
        // Ensure this matches the Maven tool name configured in Jenkins
        maven '3.6.3'
    }

    stages {
        stage('Checkout') {
            steps {
                git url: "https://klapertart:${GITHUB_TOKEN}@github.com/klapertart/sample-library.git", branch: 'master'
            }
        }
        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }
        stage('Check for Changes') {
            steps {
                script {
                    // Execute git diff command to check for changes
                    def gitChanges = sh(returnStdout: true, script: 'git diff --exit-code')
                    if (gitChanges.trim().isEmpty()) {
                        echo "No changes in the working directory."
                    } else {
                        echo "Changes detected in the working directory."
                        echo "Changes:\n${gitChanges}"
                        sh 'git add .'
                        sh 'git commit -m "update"'
                    }
                }
            }
        }

        stage('Get Project Version') {
            steps {
                script {
                    def projectVersion = sh(script: "mvn help:evaluate -Dexpression=project.version -q -DforceStdout", returnStdout: true).trim()
                    echo "Project version: ${projectVersion}"
                    def splitVersion = projectVersion.split('-')
                    env.TAG_TO_CHECK = splitVersion[0] + env.APP_VERSION
                }
            }
        }

        stage('Check Tag Existence') {
            steps {
                script {
                    def tagExists = sh(script: "git tag -l ${TAG_TO_CHECK}", returnStdout: true).trim()
                    if (tagExists) {
                        echo "Tag ${TAG_TO_CHECK} exists."
                        // You can set an environment variable or take other actions here
                        env.TAG_EXISTS = 'true'
                    } else {
                        echo "Tag ${TAG_TO_CHECK} does not exist."
                        env.TAG_EXISTS = 'false'
                    }
                }
            }
        }

        stage('Prepare Release') {
            when {
                expression { env.TAG_EXISTS == 'false' }
            }
            steps {
                script {
                    withCredentials([string(credentialsId: 'github-token', variable: 'GITHUB_TOKEN')]) {
                        sh '''
                            mvn release:clean release:prepare -Dusername=klapertart -Dpassword=${GITHUB_TOKEN}
                        '''
                    }
                }
            }
        }
        stage('Perform Release') {
            when {
                expression { env.TAG_EXISTS == 'false' }
            }
            steps {
                sh 'mvn release:perform'
            }
        }
        stage('Genereate Changelog') {
            when {
                expression { env.TAG_EXISTS == 'false' }
            }
            steps {
                script {
                    withCredentials([string(credentialsId: 'github-token', variable: 'GITHUB_TOKEN')]) {
                        sh '''
                            mvn generate-resources
                            git add .
                            git commit -m "docs: update changelog"
                            git push origin prod
                        '''
                    }
                }
            }
        }
    }
}