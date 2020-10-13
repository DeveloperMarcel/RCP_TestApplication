package rcpworkbenchtutorial;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import swing2swt.layout.BoxLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class NewtonsDividedDifference {
	private Table table;
	private TableColumn xPoints;
	private TableColumn yPoints;
	private Group grpSettings;
	private CLabel lblNumberOfX;
	private Spinner spinner;
	private Button btnSolve;
	
	double value; // value to interpolate
	
	double[] xPointValues;
	double[] yPointValues;
	private CLabel lblValueToInterpolate;
	private Text txtValue;
	private Group grpResult;
	private Label lblResult;


	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Group grpNetwonsDividedDifferences = new Group(parent, SWT.NONE);
		grpNetwonsDividedDifferences.setText("Netwons Divided Differences");
		grpNetwonsDividedDifferences.setLayout(new GridLayout(2, false));
		initializeTable(grpNetwonsDividedDifferences);
		
		grpSettings = new Group(grpNetwonsDividedDifferences, SWT.NONE);
		grpSettings.setText("Settings");
		grpSettings.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		lblNumberOfX = new CLabel(grpSettings, SWT.NONE);
		lblNumberOfX.setText("Number of x points");
		
		spinner = new Spinner(grpSettings, SWT.BORDER);
		spinner.setMaximum(20);
		spinner.setMinimum(3);
		spinner.setSelection(5);
		
		lblValueToInterpolate = new CLabel(grpSettings, SWT.NONE);
		lblValueToInterpolate.setText("Value to interpolate");
		
		txtValue = new Text(grpSettings, SWT.BORDER);
		txtValue.setText("1.2");
		
		grpResult = new Group(grpNetwonsDividedDifferences, SWT.NONE);
		grpResult.setText("Result");
		grpResult.setLayout(new GridLayout(1, false));
		
		lblResult = new Label(grpResult, SWT.NONE);
		lblResult.setText("x = ");
		
		btnSolve = new Button(grpNetwonsDividedDifferences, SWT.NONE);
		btnSolve.addListener(SWT.Selection, new SolveListener());
		btnSolve.setText("Solve");
		new Label(grpNetwonsDividedDifferences, SWT.NONE);
		
		
		
	}
	
	
	private void initializeTable(Composite group) {
		table = new Table(group, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		xPoints = new TableColumn(table, SWT.NONE);
		yPoints = new TableColumn(table, SWT.NONE);
		
		xPoints.setText("Xi");
		yPoints.setText("f(Xi)");
		
		for(int i = 0 ; i < 5; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			String[] rowText = new String[2];
			for(int j = 0; j < 2; j++) {
				rowText[j] = ""+0;
				
			}
			item.setText(rowText);
		}
		for(int i =0; i < 2; i++) {
			table.getColumn(i).pack();
		}
		initializeListeners();
	}
	
	private void initializeListeners() {
		table.addListener(SWT.MouseDown, new MatrixTableListener(table));
	}

	private double[] getXPoints() {
		xPointValues = getColumnInTable(0);
		return xPointValues;
	}
	private double[] getYPoints() {
		yPointValues = getColumnInTable(1);
		return yPointValues;
	}
	
	private double[] getColumnInTable(int colIndex) {
		double[] column = new double[table.getItemCount()];
		for(int i = 0; i < table.getItemCount(); i++) {
			TableItem item = table.getColumn(colIndex).getParent().getItems()[i];
			column[i] = Double.valueOf(item.getText(colIndex));
		}
		
		return column;
	}
	
	private double[][] calculateDifferences() {
		double[][] divMatrix = new double[xPointValues.length][xPointValues.length+1];
//		for(int i = 0; i < xPointValues.length; i++) {
//			divMatrix[i][0] = xPointValues[i];
//		}
		for(int i = 0; i < yPointValues.length; i++) {
			divMatrix[i][0] = yPointValues[i];
		}
		
		for(int i = 1; i < xPointValues.length; i++) {
			for(int j = 0; j < xPointValues.length-i; j++) {
//				System.out.println(""+divMatrix[j][i]+" - "+divMatrix[j-1][i]+" / "+divMatrix[i][0]+" - "+  divMatrix[i-1][0]);
				double f = (divMatrix[j][i-1] - divMatrix[j+1][i-1])/(xPointValues[j]-xPointValues[i+j]);
				divMatrix[j][i] =  f;
			}
		}
		
		return divMatrix;
		
	}
	
	public class SolveListener implements Listener{
		
		@Override
		public void handleEvent(Event event) {
		
			getXPoints();
			getYPoints();
			
			double[][] solution = calculateDifferences();
			table.setRedraw(false);
			TableMethods.clearTable(table);
			TableMethods.addColumns(table,solution[0].length, 90);
			TableMethods.addRows(table,solution[0].length, solution.length, convertDoubleMatrixToStringMatrix(solution));
			table.setRedraw(true);
			value = Double.valueOf(txtValue.getText());
			lblResult.setText("x = "+applyFormula(value,xPointValues, solution, 5));
			lblResult.getParent().layout();

		}

		
	}
	
	private String[][] convertDoubleMatrixToStringMatrix(double[][] doubleMatrix){
		int cols = doubleMatrix[0].length;
		int rows = doubleMatrix.length;
		String[][] stringMatrix = new String[rows][cols];
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < cols; col++ ) {
				stringMatrix[row][col] = String.valueOf(doubleMatrix[row][col]);
			}
		}
		
		return stringMatrix;
	}
	
	private double productTerm(int i, double value, double x[]) {
		double product = 1;
		for(int j =0; j < i; j++) {
			product = product * (value - x[j]);
		}
		return product;
	}
	
	private double applyFormula(double value, double x[], double y[][], int n) {
		double sum = y[0][0];
		
		for(int i = 1; i < n; i++) {
			sum = sum+(productTerm(i, value, x)* y[0][i]);
		}
		
		return sum;
	}
	
	
	
	
}
