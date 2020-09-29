package com.github.vjuranek.code_snippets;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

/**
 * Based on https://shipilev.net/jvm/anatomy-quarks/1-lock-coarsening-for-loops/
 */

@Warmup(iterations = 10, time = 1)
@Measurement(iterations = 5, time = 1)
@Fork(value = 1, jvmArgsPrepend = {"-XX:-UseBiasedLocking", "-XX:+UnlockDiagnosticVMOptions", "-XX:PrintAssemblyOptions=intel", "-XX:CompileCommand=print,*Locks.*"})
//@Fork(value = 1, jvmArgsPrepend = {"-XX:-UseBiasedLocking", "-XX:LoopUnrollLimit=1"})
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class Locks {

    int x = 0;
    int inc = 1;

    @Benchmark
    @CompilerControl(CompilerControl.Mode.DONT_INLINE)
    public void forLock() {
        for (int c = 0; c < 1000; c++) {
            synchronized (this) {
                x += inc;
            }
        }
    }

//    @Benchmark
//    public void forLockInlined() {
//        for (int c = 0; c < 1000; c++) {
//            synchronized (this) {
//                x += inc;
//            }
//        }
//    }
//
//    @Benchmark
//    public void forLockGlob() {
//        synchronized (this) {
//            for (int c = 0; c < 1000; c++) {
//                x += inc;
//            }
//        }
//    }
//
//    @Benchmark
//    public void forNoLocks() {
//        for (int c = 0; c < 1000; c++) {
//            x += inc;
//        }
//    }

}
