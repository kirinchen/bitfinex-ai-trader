package net.surfm.lambda;

import java.util.function.Consumer;

public class Action  {

	@FunctionalInterface
	public static interface _1<T1> extends Consumer<T1> {	}
	
	@FunctionalInterface
	public static interface _2<T1,T2> {
		void accept(T1 t1,T2 t2);
	}
	
	@FunctionalInterface
	public static interface _3<T1,T2,T3> {
		void accept(T1 t1,T2 t2,T3 t3);
	}	
	
}
