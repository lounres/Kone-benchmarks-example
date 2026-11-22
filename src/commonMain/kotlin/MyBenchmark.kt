package common

import dev.lounres.kone.misc.benchmarks.KoneBenchmarkExecutor
import dev.lounres.kone.misc.benchmarks.runners.KoneBenchmarkRunner
import dev.lounres.kone.misc.benchmarks.support.kotlinxBenchmark.KotlinxBenchmarkBenchmark
import dev.lounres.kone.misc.benchmarks.support.kotlinxBenchmark.KotlinxBenchmarkConfiguration
import dev.lounres.kone.misc.benchmarks.support.kotlinxBenchmark.kotlinxBenchmark
import kotlinx.benchmark.Benchmark
import kotlinx.benchmark.BenchmarkTimeUnit
import kotlinx.benchmark.Blackhole
import kotlinx.benchmark.Mode
import kotlinx.benchmark.Param
import kotlinx.benchmark.Scope
import kotlinx.benchmark.State
import kotlin.math.tan


@State(Scope.Benchmark)
open class MyBenchmark {
    companion object {
        var value = 57.179
    }
    
    @Param("57")
    var times = 0
    
    @Benchmark
    fun benchmark(blackhole: Blackhole) {
        repeat(times) { blackhole.consume(tan(value)) }
    }
}


expect val myBenchmarkDescription: KotlinxBenchmarkBenchmark

@KoneBenchmarkRunner
fun customBenchmarkExecutor(
    reportFile: String,
) {
    KoneBenchmarkExecutor.kotlinxBenchmark.execute(
        configuration = KotlinxBenchmarkConfiguration(
            name = "common",
            reportFile = reportFile,
            traceFormat = "text",
            reportFormat = "json",
            params = mapOf(
                "times" to buildList {
                    // Collecting all Fibonacci numbers
                    // from 1 to the positive infinity
                    // that less than a million
                    var a = 1
                    var b = 2
                    while (a < 1_000_000) {
                        add(a.toString())
                        a = b.also { b += a }
                    }
                }
            ),
            include = listOf(),
            exclude = listOf(),
            iterations = 3,
            warmups = 1,
            iterationTime = 500,
            iterationTimeUnit = BenchmarkTimeUnit.MILLISECONDS,
            advanced = mapOf(),
            outputTimeUnit = BenchmarkTimeUnit.NANOSECONDS,
            threads = 1,
            mode = Mode.AverageTime,
            compilationMode = null,
            configurationName = "customBenchmarkExecutor",
        ),
        benchmark = myBenchmarkDescription
    )
}