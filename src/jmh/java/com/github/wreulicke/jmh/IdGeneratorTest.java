package com.github.wreulicke.jmh;

import java.util.UUID;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

import de.huxhorn.sulky.ulid.ULID;

public class IdGeneratorTest {
	
	private static final TimeBasedGenerator v1Generator = Generators.timeBasedGenerator();
	
	private static final ULID ulid = new ULID();
	
	/**
	 * Initialize this utility.
	 *
	 */
	public static void initialize() {
	}
	
	@Benchmark
	public void uuidV1(Blackhole blackhole) {
		blackhole.consume(v1Generator.generate().toString());
	}
	
	@Benchmark
	public void uuidV4(Blackhole blackhole) {
		blackhole.consume(UUID.randomUUID().toString());
	}
	
	@Benchmark
	public void ulid(Blackhole blackhole) {
		blackhole.consume(ulid.nextULID());
	}
	
	@Benchmark
	public void timestampPrefixedUUIDV1(Blackhole blackhole) {
		blackhole.consume(System.currentTimeMillis() + "_" + v1Generator.generate().toString());
	}
}
