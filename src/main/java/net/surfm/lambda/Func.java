package net.surfm.lambda;

import java.util.function.Function;

public class Func {

	@FunctionalInterface
	public interface _0< R> {
		R apply();
	}
	
	@FunctionalInterface
	public interface _1<T, R> extends Function<T, R>{
		
	}
	
	@FunctionalInterface
	public interface _2<T1,T2, R> {
	    R apply(T1 t1,T2 t2);
	}
	
	@FunctionalInterface
	public interface _3<T1,T2,T3, R> {
	    R apply(T1 t1,T2 t2,T3 t3);
	}	

}
