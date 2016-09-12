def VERSION_TAG=16
def SWARM_MASTER_NODE="54.229.63.29"

stage 'Develop'
node {
        git url: 'https://github.com/kmahmood-2015/simple-servlet.git'
        def mvnHome = tool 'M3'
        sh "${mvnHome}/bin/mvn -B verify"
        sh "${mvnHome}/bin/mvn sonar:sonar -Dsonar.host.url=http://sonar:9000 -Dsonar.jdbc.url=\"jdbc:h2:tcp://sonar/sonar\""
        docker.build "kmahmood/kmtest:v${VERSION_TAG}"
}


stage 'Test'
node {
        docker.image("kmahmood/kmtest:v${VERSION_TAG}").withRun('-p 8585:8080') {c ->
            sh "sleep 10"
            sh "curl -v '172.17.0.1:8585/my-web-app/simple?a=4&b=3' | grep 'The sum of 4 + 3 = 7'"
            input message: "Does http://192.168.50.4:8585/my-web-app/simple?a=5&b=6 look good?"
        }
 }

stage 'Package'
node {
            withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId: 'dkrhub',
                            usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
                //available as an env variable, but will be masked if you try to print it out any which way
                sh 'docker login -u $USERNAME -p $PASSWORD'
                docker.image("kmahmood/kmtest:v${VERSION_TAG}").push("v${VERSION_TAG}")
            }
}


stage 'Staging'

node {

        sshagent (credentials: ['swarmkey1']) {

            def staging_env = sh (
                    script: "ssh -o StrictHostKeyChecking=no -l ubuntu ${SWARM_MASTER_NODE} sudo /opt/get_staging_env.sh",
                    returnStdout: true
                ).trim()

            def elb_dnsname = sh (
                    script: "ssh -o StrictHostKeyChecking=no -l ubuntu ${SWARM_MASTER_NODE} sudo /opt/get_elb_dnsname.sh",
                    returnStdout: true
                ).trim()


            sh "ssh -o StrictHostKeyChecking=no -l ubuntu ${SWARM_MASTER_NODE} sudo -i docker service update --image kmahmood/kmtest:v${VERSION_TAG} --with-registry-auth ${staging_env}"
       }

            sh "curl -v 'http://${elb_dnsname}:8080/my-web-app/simple?a=4&b=3' | grep 'The sum of 4 + 3 = 7'"
            input message: "Please check at http://${elb_dnsname}:8080/my-web-app/simple?a=5&b=6 - Do you want to GO-LIVE ?"
}

stage 'Deploy to Production'

node {
        sshagent (credentials: ['swarmkey1']) {
            sh "ssh -o StrictHostKeyChecking=no -l ubuntu ${SWARM_MASTER_NODE} sudo /opt/switch_staging_to_prod.sh"
       }

}

