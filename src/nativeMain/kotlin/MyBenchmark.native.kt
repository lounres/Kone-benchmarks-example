package common

import dev.lounres.kone.misc.benchmarks.support.kotlinxBenchmark.KotlinxBenchmarkBenchmark
import kotlinx.benchmark.BenchmarkDescriptorWithBlackholeParameter
import kotlinx.benchmark.Blackhole
import kotlinx.benchmark.SuiteDescriptor
import kotlinx.benchmark.internal.KotlinxBenchmarkRuntimeInternalApi


@OptIn(KotlinxBenchmarkRuntimeInternalApi::class)
actual val myBenchmarkDescription: KotlinxBenchmarkBenchmark
    get() {
        val suite = SuiteDescriptor(
            name = "MyBenchmark",
            factory = ::MyBenchmark,
            parametrize = { myBenchmark, parameters ->
                myBenchmark.times = parameters["times"]!!.toInt()
            },
            setup = { },
            teardown = { },
            parameters = listOf(
                "times",
            ),
            defaultParameters = mapOf(
                "times" to listOf(
                    "57",
                ),
            )
        )
        suite.add(
            BenchmarkDescriptorWithBlackholeParameter(
                name = "benchmark",
                suite = suite,
                blackhole = Blackhole(),
                function = MyBenchmark::benchmark,
            )
        )
        return KotlinxBenchmarkBenchmark(
            listOf(
                suite,
            )
        )
    }