stage 'Build'
node {
        git url: 'https://github.com/kmahmood-2015/simple-servlet.git'
        def mvnHome = tool 'M3'
        sh "${mvnHome}/bin/mvn -B verify"
        docker.build 'kmtest:v1'
}

stage 'Test'
node {
        docker.image('kmtest:v1').withRun('-p 8585:8080') {c ->
            sh "sleep 3"
            sh "curl 'http://192.168.56.103:8585/my-web-app/simple?a=4&b=3' | grep 'The sum of 4 + 3 = 7'"
            input message: "Does http://192.168.56.103:8585/my-web-app/simple?a=5&b=6 look good?"
        }
 }

stage 'Docker push'
node {
        docker.withRegistry("https://253814188284.dkr.ecr.eu-west-1.amazonaws.com", "ecr:kmtest1") {
            docker.image("kmtest:v1").push('v1a')
        }

}
