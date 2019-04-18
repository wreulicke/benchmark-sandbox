package com.github.wreulicke.jcstress;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.infra.results.L_Result;

import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.impl.TimeBasedGenerator;

@JCStressTest
@Outcome(id = {
	"[0-9A-F]{8}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{4}-[0-9A-F]{12}"
}, expect = Expect.ACCEPTABLE)
public class UuIdV1Test {
	
	private static final TimeBasedGenerator v1Generator = Generators.timeBasedGenerator();
	
	@Actor
	public void actor1(L_Result r) {
		r.r1 = v1Generator.generate().toString();
	}
}
