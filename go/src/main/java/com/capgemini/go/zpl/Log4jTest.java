package com.capgemini.go.zpl;

import com.capgemini.go.utility.GoLog;

class A {
	public void f1 () {
		GoLog.getLogger(A.class).debug("Debug Message 1 from A");
	}
}

class B {
	public void f1 () {
		GoLog.getLogger(B.class).debug("Debug Message 1 from B");
	}
}

public class Log4jTest {
	public static void main (String [] args) {
		GoLog.getLogger(Log4jTest.class).debug("Debug Message 1");
		A a = new A();
		a.f1();
		
		B b = new B();
		b.f1();
	}
}
