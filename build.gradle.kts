import kotlin.io.path.createFile
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kone.benchmarksRunners)
    alias(libs.plugins.kone.benchmarksRunnersKotlinxBenchmarkSupport)
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of("17")
        vendor = JvmVendorSpec.of("graalvm")
    }
    
    compilerOptions {
        progressiveMode = true
        freeCompilerArgs.addAll(
            "-Xexpect-actual-classes",
            "-Xconsistent-data-class-copy-visibility",
            "-Xcontext-sensitive-resolution",
            "-Xreturn-value-checker=full",
            "-Xlocal-type-aliases",
            "-Xname-based-destructuring=complete",
            "-Xcollection-literals",
            "-Xcompanion-blocks-and-extensions",
        )
        verbose = true
    }
    
    jvm {
        // FIXME: Disabled until there will be a general patch function that adds JMH support
        // For regular kotlinx-benchmark-like configurations, the support is provided.
//        koneBenchmarksRunners {
//            register("custom") {
//                runnerName = "common.customBenchmarkExecutor"
//                val reportFile = project.layout.buildDirectory.file("customBenchmarkReport").get().asFile
//                argument("reportFile", reportFile.absolutePath)
//                configureRunnersTasks {
//                    doFirst {
//                        reportFile.toPath().let {
//                            if (!it.exists()) {
//                                it.createParentDirectories()
//                                it.createFile()
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
    mingwX64 {
        koneBenchmarksRunners {
            register("custom") {
                runnerName = "common.customBenchmarkExecutor"
                val reportFile = project.layout.buildDirectory.file("customBenchmarkReport").get().asFile
                argument("reportFile", reportFile.absolutePath)
                configureRunnersTasks {
                    doFirst {
                        reportFile.toPath().let {
                            if (!it.exists()) {
                                it.createParentDirectories()
                                it.createFile()
                            }
                        }
                    }
                }
            }
        }
    }
    linuxX64 {
        koneBenchmarksRunners {
            register("custom") {
                runnerName = "common.customBenchmarkExecutor"
                val reportFile = project.layout.buildDirectory.file("customBenchmarkReport").get().asFile
                argument("reportFile", reportFile.absolutePath)
                configureRunnersTasks {
                    doFirst {
                        reportFile.toPath().let {
                            if (!it.exists()) {
                                it.createParentDirectories()
                                it.createFile()
                            }
                        }
                    }
                }
            }
        }
    }
}

benchmark {
    targets {
        register("jvm")
        register("mingwX64")
        register("linuxX64")
    }
    configurations {
        register("main") {
            warmups = 3
            iterations = 3
            iterationTime = 1
            iterationTimeUnit = "SECONDS"
        }
    }
}