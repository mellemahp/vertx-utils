load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# import libraries from maven
RULES_JVM_EXTERNAL_TAG = "2.8"

RULES_JVM_EXTERNAL_SHA = "79c9850690d7614ecdb72d68394f994fef7534b292c4867ce5e7dec0aa7bdfad"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

load("@rules_jvm_external//:defs.bzl", "maven_install")

MAVEN_REPOS = ["https://repo1.maven.org/maven2"]

maven_install(
    name = "deps",
    artifacts = [
        # jackson
        "com.fasterxml.jackson.core:jackson-databind:2.12.4",
        "com.fasterxml.jackson.core:jackson-core:2.12.4",
    ],
    repositories = MAVEN_REPOS
)

###################
# JAVA BUILD DEPS
###################
maven_install(
    name = "build_deps",
    artifacts = [
        # dagger dependencies
        "com.google.dagger:dagger:2.32",
        "com.google.dagger:dagger-compiler:2.32",
        "javax.inject:javax.inject:1",
        # openapi build
        "org.junit.jupiter:junit-jupiter-api:5.8.2",
        "org.junit.jupiter:junit-jupiter-engine:5.8.2",
        # nullaway
        "com.google.code.findbugs:jsr305:3.0.2",
        "com.uber.nullaway:nullaway:0.3.4",
        # lombok
        "org.projectlombok:lombok:1.18.22",
        # vertx codegen
        "io.vertx:vertx-codegen:4.2.4",
        "io.vertx:vertx-service-proxy:4.2.4"
    ],
    repositories = MAVEN_REPOS,
)

###################
# LOGGING DEPS
###################
maven_install(
    name = "logging_deps",
    artifacts = [
        # AWS Lambda logging for logback
        "org.jlib:jlib-awslambda-logback:1.0.0",
        # SLF4J logging facade api
        "org.slf4j:slf4j-api:1.8.0-beta4",
        # Logback base logging
        "ch.qos.logback:logback-classic:1.3.0-alpha4",
        # structured logging
        "net.logstash.logback:logstash-logback-encoder:6.4",
        # contextual logging
        "io.reactiverse:reactiverse-contextual-logging:1.1.2",
    ],
    repositories = MAVEN_REPOS,
)

#######################
# Metrics Dependencies
#######################
maven_install(
    name = "metrics_deps",
    artifacts = [
        # vertx metrics adapter
        "io.vertx:vertx-micrometer-metrics:4.2.3",
        "io.micrometer:micrometer-registry-prometheus:1.8.1",
        "io.micrometer:micrometer-core:1.8.2",
        # prometheus metrics libraries
        "io.prometheus:simpleclient_vertx:0.14.1",
        "io.prometheus:simpleclient:0.14.1",
    ],
    repositories = MAVEN_REPOS,
)

###########################
# Vertx Core Dependencies
###########################
maven_install(
    name = "vertx_core",
    artifacts = [
        "io.vertx:vertx-web-openapi:4.2.1",
        "io.vertx:vertx-core:4.2.1",
        "io.vertx:vertx-web-validation:4.2.1",
        "io.vertx:vertx-web:4.2.1",
        "io.vertx:vertx-json-schema:4.2.1",
    ],
    repositories = MAVEN_REPOS,
)