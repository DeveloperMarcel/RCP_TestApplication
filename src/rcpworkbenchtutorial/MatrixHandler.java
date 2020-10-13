package rcpworkbenchtutorial;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MatrixHandler {
	static int MAX = 100; 
	static String s=""; 
	
	public static double[][] multiplyMatrices(double[][] aMatrix, double[][] bMatrix){
		System.out.println("A matrix rows = "+aMatrix.length+" A matrix columns = "+aMatrix[0].length);
		System.out.println("B matrix rows = "+bMatrix.length+" A matrix columns = "+bMatrix[0].length);
		if(aMatrix[0].length !=  bMatrix.length) {
			System.out.println("Matrix multiplication can not be performed. Matrix A has to have columns equal to the number of rows in matrix B");
			return new double[0][0];
		}
		double[][] product = new double[bMatrix.length][aMatrix[0].length];
		
		
		
		return product;
	}
	public static void luDecomposition(double[][] mat, int n, double[][] cMatrix) {
		double [][]lower = new double[n][n]; 
	    double [][]upper = new double[n][n];
	 // Decomposing matrix into Upper and Lower 
	    // triangular matrix 
	    for (int i = 0; i < n; i++) 
	    { 
	  
	        // Upper Triangular 
	        for (int k = i; k < n; k++) 
	        { 
	  
	            // Summation of L(i, j) * U(j, k) 
	            int sum = 0; 
	            for (int j = 0; j < i; j++) 
	                sum += (lower[i][j] * upper[j][k]); 
	  
	            // Evaluating U(i, k) 
	            upper[i][k] = round((mat[i][k] - sum),8); 
	        } 
	  
	        // Lower Triangular 
	        for (int k = i; k < n; k++)  
	        { 
	            if (i == k) 
	                lower[i][i] = 1; // Diagonal as 1 
	            else 
	            { 
	  
	                // Summation of L(k, j) * U(j, i) 
	                int sum = 0; 
	                for (int j = 0; j < i; j++) 
	                    sum += (lower[k][j] * upper[j][i]); 
	  
	                // Evaluating L(k, i) 
	                lower[k][i] = round(((mat[k][i] - sum) / upper[i][i]), 8); 
	            } 
	        } 
	    } 
	 // setw is for displaying nicely 
	    System.out.println(setw(2) + "     Lower Triangular"
	        + setw(10) + "Upper Triangular"); 
	  
	    // Displaying the result : 
	    for (int i = 0; i < n; i++)  
	    { 
	        // Lower 
	        for (int j = 0; j < n; j++) 
	            System.out.print(setw(4) + lower[i][j] + "\t");  
	        System.out.print("\t"); 
	  
	        // Upper 
	        for (int j = 0; j < n; j++) 
	            System.out.print(setw(4) + upper[i][j] + "\t"); 
	        System.out.print("\n"); 
	    } 
	    double[][] x = getXMatricByBackwardSubstitution(upper, getZMatrixByForwardSubstitution(lower,cMatrix ));
	    System.out.println("X = ");
	    for(int i = 0; i < x.length; i++) {
	    	System.out.println(x[i][0]+"");	
	    }
	    
	} 
	
	/**
	 * Solves for Z in the equation LZ = C where Z is a single column matrix of n rows
	 * Using LU Decomposition this method obtains the Z matrix to substitute into the equation UX = Z
	 * 
	 * @param lMatrix
	 * @param cMatrix
	 * @return 
	 */
	static public double[][] getZMatrixByForwardSubstitution(double[][] lMatrix, double[][] cMatrix) {
		double[][] Z = new double[lMatrix.length][1];
		double s;
		Z[0][0] = cMatrix[0][0];
		for(int i = 1; i < lMatrix.length; i++) {
			s = 0;
			for(int j = 0; j <= i-1; j++) {
				s = s + lMatrix[i][j] * Z[j][0];
			}
			Z[i][0] = cMatrix[i][0] - s;
			
		}
		System.out.println("Z= ");
		for(int i = 0; i < Z.length; i++) {
			System.out.println(Z[i][0]+"");
		}
		return Z;
		
	}
	
	/**
	 * Solves the equation of the form UX = Z in the LU decomposition method
	 * @param uMatrix - the upper triangular matrix
	 * @param zMatrix - the matrix obtained by solving LZ = C
	 * @return the X matrix
	 */
	static double[][] getXMatricByBackwardSubstitution(double[][]uMatrix, double[][] zMatrix){
		double[][] X = new double[uMatrix.length][1];
		double s;
		for(int i = 0; i < X.length; i++) {
			for(int j = 0; j < X[0].length; j++) {
				X[i][j] = 1;
			}
		}
		int index = uMatrix[uMatrix.length-1].length-1;
		X[uMatrix.length-1][0] = zMatrix[uMatrix.length-1][0] / uMatrix[uMatrix.length-1][uMatrix[uMatrix.length-1].length-1];
		
		for(int i = uMatrix.length -2; i >= 0; i-- ) {
			s = 0;
			for(int j = i+1; j < uMatrix[0].length; j++) {
				s = s + uMatrix[i][j] * X[j][0];
			}
			X[i][0] = (zMatrix[i][0]-s)/uMatrix[i][i]; 
		}
		return X;
	}
	static String setw(int noOfSpace) 
	{ 
	    s=""; 
	    for(int i = 0;i<noOfSpace;i++) 
	        s+=" "; 
	    return s; 
	} 
	
	public static double round(double value, int places) {
		if(places < 0) throw new IllegalArgumentException();
		
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		
		return bd.doubleValue();
	}
}
