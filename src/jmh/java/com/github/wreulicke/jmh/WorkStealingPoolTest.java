package com.github.wreulicke.jmh;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

public class WorkStealingPoolTest {
	
	@State(Scope.Benchmark)
	public static class WorkStealingPoolState {
		
		ExecutorService workStealingPool;
		
		@Setup
		public void setUp() {
			workStealingPool = Executors.newWorkStealingPool();
		}
		
		@TearDown
		public void teadDown() {
			workStealingPool.shutdown();
		}
	}
	
	@State(Scope.Benchmark)
	public static class FixedPoolState {
		ExecutorService fixedThreadPool;
		
		@Setup
		public void setUp() {
			fixedThreadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
		}
		
		@TearDown
		public void teadDown() {
			fixedThreadPool.shutdown();
		}
	}
	
	
	@State(Scope.Benchmark)
	public static class FixedPoolState2 {
		ExecutorService fixedThreadPool;
		
		@Setup
		public void setUp() {
			fixedThreadPool = Executors.newFixedThreadPool(10);
		}
		
		@TearDown
		public void teadDown() {
			fixedThreadPool.shutdown();
		}
	}
	
	@Benchmark
	public void case1(FixedPoolState2 state) throws Exception {
		state.fixedThreadPool.submit(() -> {
			
		IntStream.range(0, 9)
			.mapToObj(n -> state.fixedThreadPool.submit(() -> {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}))
			.parallel()
			.forEach(f-> {
				try {
					f.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			});
		}).get();
	}
	
	@Benchmark
	public void case2(FixedPoolState2 state) throws Exception {
		state.fixedThreadPool.submit(() -> {
		IntStream.range(0, 9)
			.mapToObj(n -> state.fixedThreadPool.submit(() -> {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}))
			.forEach(f-> {
				try {
					f.get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			});
		}).get();
	}
	
	@Benchmark
	public void case3(WorkStealingPoolState state) throws Exception {
		state.workStealingPool.submit(() -> {
		IntStream.range(0, 9)
			.mapToObj(n -> n)
			.parallel()
			.forEach(n -> {
				try {
					state.workStealingPool.submit(() -> {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}).get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			});
		}).get();
	}
	
	@Benchmark
	public void case4(FixedPoolState state) throws Exception {
		state.fixedThreadPool.submit(() -> {
		IntStream.range(0, 9)
			.mapToObj(n -> n)
			.parallel()
			.forEach(n -> {
				try {
					state.fixedThreadPool.submit(() -> {
						try {
							Thread.sleep(10);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}).get();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			});
		}).get();
	}
	
}
