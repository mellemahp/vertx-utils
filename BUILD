#######################
# Lombok
#######################
java_library(
    name = "lombok",
    exported_plugins = [
        ":lombok_plugin",
    ],
    exports = [
        "@build_deps//:org_projectlombok_lombok",
    ],
)

java_plugin(
    name = "lombok_plugin",
    processor_class = "lombok.launch.AnnotationProcessorHider$AnnotationProcessor",
    deps = [
        ":lombok_jar",
    ],
)

java_import(
    name = "lombok_jar",
    jars = [
        "@build_deps//:v1/https/repo1.maven.org/maven2/org/projectlombok/lombok/1.18.22/lombok-1.18.22.jar",
    ],
)

#######################
# Dagger-2
#######################
java_plugin(
    name = "dagger_plugin",
    generates_api = 1,
    processor_class = "dagger.internal.codegen.ComponentProcessor",
    deps = [
        "@build_deps//:com_google_dagger_dagger_compiler",
    ],
)

java_library(
    name = "dagger_lib",
    exported_plugins = ["dagger_plugin"],
    exports = [
        "@build_deps//:com_google_dagger_dagger",
        "@build_deps//:javax_inject_javax_inject",
    ],
)

#######################
# Vertx Codegen
#######################
java_plugin(
    name = "vertx_codegen",
    generates_api = 1,
    processor_class = "io.vertx.codegen.CodeGenProcessor",
    deps = [
        "@build_deps//:io_vertx_vertx_codegen",
        "@build_deps//:io_vertx_vertx_service_proxy",
    ],
)

#######################
# Nullaway
#######################
java_plugin(
    name = "nullaway",
    deps = [
        "@build_deps//:com_uber_nullaway_nullaway",
    ],
)

#######################
# Base Pre-processors
#######################
# This can be re-used by other libraries that depend on this 
# utils library
java_library(
    name = "preprocessors",
    exports = [
        # NOTE: ORDER OF PLUGINS DOES MATTER
        ":lombok",
        ":vertx_codegen",
        ":dagger_lib",
        ":nullaway",
    ],
    visibility = ["//visibility:public"],
)

#########################
# Logging Deps
#########################
# This can be re-used by other libraries that depend on this 
# utils library
java_library(
    name = "runtime_logging_deps",
    exports = [
        "@logging_deps//:net_logstash_logback_logstash_logback_encoder",
    ],
    visibility = ["//visibility:public"]
)

java_library(
    name = "library_logging_deps",
    exports = [
        "@logging_deps//:org_slf4j_slf4j_api",
        "@logging_deps//:ch_qos_logback_logback_classic",
        "@logging_deps//:net_logstash_logback_logstash_logback_encoder",
        "@logging_deps//:io_reactiverse_reactiverse_contextual_logging",
    ],
    visibility = ["//visibility:public"]
)


#########################
# Prometheus Metrics Deps
#########################
# This can be re-used by other libraries that depend on this 
# utils library
java_library(
    name = "metrics_dependencies",
    exports = [
        # metrics
        "@metrics_deps//:io_vertx_vertx_micrometer_metrics",
        "@metrics_deps//:io_micrometer_micrometer_registry_prometheus",
        "@metrics_deps//:io_prometheus_simpleclient_vertx",
        "@metrics_deps//:io_prometheus_simpleclient",
        "@metrics_deps//:io_micrometer_micrometer_core"
    ],
    visibility = ["//visibility:public"]
)

##################
# Vertx Base Deps
##################
# This can be re-used by other libraries that depend on this 
# utils library
java_library(
    name = "vertx_core_deps",
    exports = [
        "@vertx_core//:io_vertx_vertx_core",
        "@vertx_core//:io_vertx_vertx_web_openapi",
        "@vertx_core//:io_vertx_vertx_web_validation",
        "@vertx_core//:io_vertx_vertx_web",
        "@vertx_core//:io_vertx_vertx_json_schema",
    ],
    visibility = ["//visibility:public"]
)

#######################
# Utils Lib
#################
java_library(
    name = "vertx_utils",
    srcs = glob(["src/**/*.java"]),
    deps = [
        ":preprocessors",
        ":library_logging_deps",
        ":vertx_core_deps",
        "metrics_dependencies",
        # jackson deps
        "@deps//:com_fasterxml_jackson_core_jackson_core",
        "@deps//:com_fasterxml_jackson_core_jackson_databind",
    ],
    javacopts = [
        # Sets nullaway errors to break build
        "-Xep:NullAway:ERROR",
        # sets packages for nullaway to run on
        "-XepOpt:NullAway:AnnotatedPackages=com.hmellema.vertxutils",
        # Tries to ignore dagger-generated classes
        "-XepOpt:NullAway:UnannotatedSubPackages=com.hmellema.vertxutils.routing",
    ],
    visibility = ["//visibility:public"],
)


#########################################
# Common Deps for reuse in vertx projects 
##########################################
java_library(
    name = "common",
    exports = [
        ":library_logging_deps",
        ":preprocessors",
        ":vertx_core_deps",
        ":vertx_utils",
        ":metrics_dependencies"
    ],
    visibility = ["//visibility:public"]
)