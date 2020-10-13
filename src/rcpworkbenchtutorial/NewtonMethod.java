package rcpworkbenchtutorial;

import org.eclipse.swt.widgets.Composite;

public class NewtonMethod {

	private double tolerance = .000000001;
	private int max_count = 200;
	private double x = 0;
	private NewtonMethodView view;
	
	
	public NewtonMethod(NewtonMethodView parent) {
		view = parent;
	}
	
	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
		
	}
	public void setMaxCount(int max) {
		this.max_count = max;
	}
	
	public void setX(double initialGuess) {
		x = initialGuess;
	}
	static double f(double x) {
		return Math.cos(x)-x;
	}
	static double fprime(double x) {
		return -Math.sin(x)-1;
	}
	
	
	public void calculate() {
		 for( int count=1;
                 (Math.abs(f(x)) > tolerance) && ( count < max_count);
                count ++)  {
           x= x - f(x)/fprime(x);
           
           view.addOutputLine("Step: "+count+" x:"+x+" Value:"+f(x));
//         System.out.println("Step: "+count+" x:"+x+" Value:"+f(x));
	  }            

	  if( Math.abs(f(x)) <= tolerance) {
		  view.addOutputLine("Zero found at x="+x);
//	   System.out.println("Zero found at x="+x);
	  }
	  else {
		  view.addOutputLine("Failed to find a zero");
//	   System.out.println("Failed to find a zero");
	  }
    }
}
	

