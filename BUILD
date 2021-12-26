#######################
# Lombok
#######################
java_library(
    name = "lombok",
    exported_plugins = [
        ":lombok_plugin",
    ],
    exports = [
        "@maven//:org_projectlombok_lombok",
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
        "@maven//:v1/https/repo1.maven.org/maven2/org/projectlombok/lombok/1.18.22/lombok-1.18.22.jar",
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
        "@maven//:com_google_dagger_dagger_compiler",
    ],
)

java_library(
    name = "dagger_lib",
    exported_plugins = ["dagger_plugin"],
    exports = [
        "@maven//:com_google_dagger_dagger",
        "@maven//:javax_inject_javax_inject",
    ],
)

#######################
# Nullaway
#######################
java_plugin(
    name = "nullaway",
    deps = [
        "@maven//:com_uber_nullaway_nullaway",
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
        ":dagger_lib",
        ":nullaway",
    ],
    visibility = ["//visibility:public"],
)

#######################
# Utils Lib
#################
java_library(
    name = "vertx_utils",
    srcs = glob(["src/**/*.java"]),
    deps = [
        ":preprocessors",
        ":util_deps"
    ],
    javacopts = [
        # Sets nullaway errors to break build
        "-Xep:NullAway:ERROR",
        # sets packages for nullaway to run on
        "-XepOpt:NullAway:AnnotatedPackages=com.hmellema",
        # Tries to ignore dagger-generated classes
        "-XepOpt:NullAway:UnannotatedSubPackages=com.hmellema.vertxutils.routing",
    ],
    visibility = ["//visibility:public"],
)

java_library(
    name = "util_deps",
    exports = [
        # jackson deps
        "@maven//:com_fasterxml_jackson_core_jackson_core",
        "@maven//:com_fasterxml_jackson_core_jackson_databind",
        # vertx
        "@maven//:io_vertx_vertx_core",
        "@maven//:io_vertx_vertx_web_openapi",
        "@maven//:io_vertx_vertx_web_validation",
        "@maven//:io_vertx_vertx_web",
        "@maven//:io_vertx_vertx_json_schema",
        #contextual logging
        "@maven//:io_reactiverse_reactiverse_contextual_logging",
        # base logging
        "@maven//:org_slf4j_slf4j_api",
    ],
)
######