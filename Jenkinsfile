def VERSION_TAG="v3"

stage 'Build'
node {
        git url: 'https://github.com/kmahmood-2015/simple-servlet.git'
        def mvnHome = tool 'M3'
        sh "${mvnHome}/bin/mvn -B verify"
        docker.build 'kmtest:${VERSION_TAG}'
}

stage 'Test'
node {
        docker.image('kmtest:${VERSION_TAG}').withRun('-p 8585:8080') {c ->
            sh "sleep 3"
            sh "curl 'http://192.168.56.103:8585/my-web-app/simple?a=4&b=3' | grep 'The sum of 4 + 3 = 7'"
            input message: "Does http://192.168.56.103:8585/my-web-app/simple?a=5&b=6 look good?"
        }
 }

stage 'Docker push'
node {
        docker.withRegistry("https://253814188284.dkr.ecr.eu-west-1.amazonaws.com", "ecr:kmtest1") {
            docker.image("kmtest:${VERSION_TAG}").push('${VERSION_TAG}')
        }

}

stage 'Deploy to Prod'

node {
  sshagent (credentials: ['e7c2cb8b-0be7-44d6-a483-7639b7b53fd5']) {
    sh 'ssh -o StrictHostKeyChecking=no -l ec2-user 54.229.105.31 sudo docker login -u AWS -p $(aws ecr get-authorization-token --region eu-west-1 --output text --query authorizationData[].authorizationToken | base64 -d | cut -d: -f2) -e none https://253814188284.dkr.ecr.eu-west-1.amazonaws.com'
    sh 'ssh -o StrictHostKeyChecking=no -l ec2-user 54.229.105.31 sudo docker service update --image 253814188284.dkr.ecr.eu-west-1.amazonaws.com/kmtest:${VERSION_TAG} --registry-auth mywebapp'
  }
}


