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

maven_install(
    artifacts = [
        # dagger dependencies
        "com.google.dagger:dagger:2.32",
        "com.google.dagger:dagger-compiler:2.32",
        "javax.inject:javax.inject:1",
        # vert.x
        "io.vertx:vertx-web-openapi:4.2.1",
        "io.vertx:vertx-core:4.2.1",
        "io.vertx:vertx-web-validation:4.2.1",
        "io.vertx:vertx-web:4.2.1",
        "io.vertx:vertx-json-schema:4.2.1",
        # nullaway
        "com.google.code.findbugs:jsr305:3.0.2",
        "com.uber.nullaway:nullaway:0.3.4",
        # lombok
        "org.projectlombok:lombok:1.18.22",
        # contextual logging
        "io.reactiverse:reactiverse-contextual-logging:1.1.2",
        # jackson
        "com.fasterxml.jackson.core:jackson-databind:2.12.4",
        "com.fasterxml.jackson.core:jackson-core:2.12.4",
        # logging
        "org.slf4j:slf4j-api:1.8.0-beta4",
    ],
    repositories = [
        "https://repo1.maven.org/maven2",
    ],
)