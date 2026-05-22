pipeline {
    agent any

    stages {
        stage('Clonar repositorio') {
            steps {
                git branch: 'main',
                    credentialsId: 'TuIDToken',
                    url: 'https://github.com/TuOrganizacion/StoreAPP-TuNombre.git'
            }
        }

        stage('Verificar contenido') {
            steps {
                sh 'echo "Repositorio clonado correctamente"'
                sh 'pwd'
                sh 'ls -la'
                sh 'git branch -a || true'
            }
        }

        stage('Tests con Maven') {
            steps {
                withEnv([
                    "JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64",
                    "PATH+JAVA=/usr/lib/jvm/java-11-openjdk-amd64/bin"
                ]) {
                    sh 'mvn test'
                }
            }
        }

        stage('Package con Maven') {
            steps {
                withEnv([
                    "JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64",
                    "PATH+JAVA=/usr/lib/jvm/java-11-openjdk-amd64/bin"
                ]) {
                    sh 'mvn clean package -DskipTests'
                }
            }
        }

        stage('Construir imagen para Minikube') {
            steps {
                sh '''
                    eval $(minikube -p ansible docker-env)
                    docker build -t store-app:latest .
                    docker images | grep store-app
                '''
            }
        }

        stage('Validar acceso a Kubernetes') {
            steps {
                withKubeConfig(
                    credentialsId: 'minikube-jenkins-secret',
                    serverUrl: 'https://TU_API_SERVER:PUERTO',
                    clusterName: 'minikube',
                    contextName: 'minikube',
                    namespace: 'default',
                    restrictKubeConfigAccess: true
                ) {
                    sh 'kubectl get nodes'
                }
            }
        }

        stage('Desplegar en Kubernetes') {
            steps {
                withKubeConfig(
                    credentialsId: 'minikube-jenkins-secret',
                    serverUrl: 'https://TU_API_SERVER:PUERTO',
                    clusterName: 'minikube',
                    contextName: 'minikube',
                    namespace: 'default',
                    restrictKubeConfigAccess: true
                ) {
                    sh '''
                        kubectl apply -f store-app-k8s.yaml
                        kubectl rollout status deployment/store-app --timeout=120s
                        kubectl get all
                    '''
                }
            }
        }
    }

    post {
        success {
            echo 'Ciclo de vida completado correctamente'
        }
        failure {
            echo 'El ciclo de vida ha fallado'
        }
        always {
            archiveArtifacts artifacts: 'target/*.jar', fingerprint: true, onlyIfSuccessful: false
        }
    }
}