package com.github.wreulicke.jmh;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.util.ConcurrencyThrottleSupport;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import com.github.wreulicke.bricks.concurrent.ConcurrencyLimitsExecutorService;
import com.github.wreulicke.bricks.concurrent.ConcurrentContext;
import com.netflix.concurrency.limits.limit.VegasLimit;
import com.netflix.concurrency.limits.limiter.AbstractPartitionedLimiter;

public class ExecutorTest {
	
	@State(Scope.Benchmark)
	public static class Executor {
		
		public ExecutorService fixedExecutor;
		
		public ExecutorService cachedExecutor;
		
		public ExecutorService concurrencyLimitsExecutorService;
		
		@Setup(Level.Trial)
		public void doSetup() {
			fixedExecutor = Executors.newFixedThreadPool(10);
			cachedExecutor = Executors.newCachedThreadPool();
			concurrencyLimitsExecutorService = new ConcurrencyLimitsExecutorService(
				new LimiterBuilder().build(), Executors.newFixedThreadPool(10));
		}
		
		@TearDown(Level.Trial)
		public void doTearDown() {
			fixedExecutor.shutdownNow();
			concurrencyLimitsExecutorService.shutdownNow();
			cachedExecutor.shutdownNow();
		}
	}
	
	@Benchmark
	public void fixed(Executor executor) throws ExecutionException, InterruptedException {
		executor.fixedExecutor.submit(task).get();
	}
	
	@Benchmark
	public void cached(Executor executor) throws ExecutionException, InterruptedException {
		executor.cachedExecutor.submit(task).get();
	}
	
	@Benchmark
	public void concurrencyLimit(Executor executor) throws ExecutionException, InterruptedException {
		try {
			executor.concurrencyLimitsExecutorService.submit(task).get();
		} catch (RejectedExecutionException e) {
		}
		
	}
	
	private static final Callable<String> task = () -> "";
	
	static class LimiterBuilder extends AbstractPartitionedLimiter.Builder<LimiterBuilder, ConcurrentContext> {
		
		LimiterBuilder() {
			super();
			limit(VegasLimit.newBuilder()
				.initialLimit(1000000)
				.maxConcurrency(Integer.MAX_VALUE)
				.build());
		}
		
		@Override
		protected LimiterBuilder self() {
			return this;
		}
		
	}
}
