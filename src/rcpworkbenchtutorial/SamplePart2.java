package rcpworkbenchtutorial;
import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.*;
import org.eclipse.swt.widgets.*;

import static org.eclipse.swt.events.SelectionListener.*;


public class SamplePart2 {
	private Table aMatrix; //the matrix A
	private Composite aMatrixContainer;
	private Table cMatrix; // the C matrix in a matrix equation [A][X] = [C]
	private Composite cMatrixContainer;
	
	private double[][] coeficientMatrix;
	private double[][] cMatrixValues;
	
	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new GridLayout(1,false));
		createAMatrixTable(parent, 4,4); // create initial 4x4 matrix
		createAMatrixSettingsGroup(parent);
		createCMatrixTable(parent, 4);
		createCMatrixSettingsGroup(parent);
		
		Button btnCalculate = new Button(parent, SWT.PUSH);
		btnCalculate.setText("Calculate");
		
		
		btnCalculate.addSelectionListener(widgetSelectedAdapter(e -> {
			
			coeficientMatrix = new double[aMatrix.getItemCount()][aMatrix.getColumnCount()]; 
			cMatrixValues = new double[cMatrix.getItemCount()][1];
			
			for(int i = 0; i < aMatrix.getColumnCount(); i++) {
				for(int j = 0; j < aMatrix.getItemCount(); j++) {
					TableItem tblItem = aMatrix.getColumn(i).getParent().getItems()[j];
					coeficientMatrix[j][i] = Double.valueOf(tblItem.getText(i));
					
				}
				
				
			}
			
			for(int row = 0; row < cMatrix.getItemCount(); row++) {
				TableItem tblItem = cMatrix.getColumn(0).getParent().getItems()[row];
				cMatrixValues[row][0] = Double.valueOf(tblItem.getText(0));
			}
			MatrixHandler.multiplyMatrices(coeficientMatrix, cMatrixValues);
			MatrixHandler.luDecomposition(coeficientMatrix, aMatrix.getColumnCount(),cMatrixValues);
		}));
		
		
			
	}
	
	public Composite createCMatrixTable(Composite parent, int rows) {
		cMatrixContainer = new Composite(parent, SWT.NONE);
		cMatrixContainer.setLayout(new FillLayout());
		cMatrix = new Table(cMatrixContainer, SWT.BORDER | SWT.MULTI);
		cMatrix.setLinesVisible(true);
		for(int c = 0; c < 1; c++) {
			TableColumn tc1 = new TableColumn(cMatrix, SWT.CENTER);
			tc1.setWidth(70);
		}
		
		cMatrix.setHeaderVisible(false);
		for (int i=0; i< rows; i++) {
			TableItem item = new TableItem (cMatrix, SWT.NONE);
			String[] rowText = new String[1];
			for(int c = 0; c < 1; c++) {
				rowText[c] = ""+c;
			}
			item.setText(rowText);
		}
		
		cMatrix.addListener (SWT.MouseDown, new MatrixTableListener(cMatrix));
		return cMatrixContainer;
	}
	
	public void createCMatrixSettingsGroup(Composite parent) {
		Composite cMatrixGroupContainer = new Composite(parent, SWT.NONE);
		cMatrixGroupContainer.setLayout(new FillLayout());
		
		Spinner rSpinner = new Spinner(cMatrixGroupContainer, SWT.BORDER);
		rSpinner.setMinimum(2);
		rSpinner.setMaximum(12);
		rSpinner.setSelection(4);
		rSpinner.setIncrement(1);
		
		rSpinner.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				TableMethods.redrawDefaultMatrixTable(cMatrix, 1, Integer.parseInt(rSpinner.getText()));
				
			}
			
		});
		
		
		rSpinner.pack();
	}
	
	
	public void createAMatrixSettingsGroup(Composite parent) {
		Composite aMatrixGroupContainer = new Composite(parent, SWT.NONE);
		aMatrixGroupContainer.setLayout(new FillLayout());
		Spinner cSpinner = new Spinner(aMatrixGroupContainer, SWT.BORDER);
		cSpinner.setMinimum(2);
		cSpinner.setMaximum(12);
		cSpinner.setSelection(4);
		cSpinner.setIncrement(1);
		
		Spinner rSpinner = new Spinner(aMatrixGroupContainer, SWT.BORDER);
		rSpinner.setMinimum(2);
		rSpinner.setMaximum(12);
		rSpinner.setSelection(4);
		rSpinner.setIncrement(1);
		
		rSpinner.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				TableMethods.redrawDefaultMatrixTable(aMatrix, Integer.parseInt(cSpinner.getText()), Integer.parseInt(rSpinner.getText()));
				
			}
			
		});
		
		cSpinner.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				
				TableMethods.redrawDefaultMatrixTable(aMatrix, Integer.parseInt(cSpinner.getText()), Integer.parseInt(rSpinner.getText()));
				
				
			}
			
		});
		
		cSpinner.pack();
		rSpinner.pack();
		
	}
	
	
	
	public Composite createAMatrixTable(Composite parent, int rows, int columns) {
		aMatrixContainer = new Composite(parent, SWT.NONE);
		aMatrixContainer.setLayout(new FillLayout());
		
		aMatrix = new Table(aMatrixContainer, SWT.BORDER | SWT.MULTI);
		aMatrix.setLinesVisible(true);
		for(int c = 0; c < columns; c++) {
			TableColumn tc1 = new TableColumn(aMatrix, SWT.CENTER);
			tc1.setWidth(70);
		}
		
		aMatrix.setHeaderVisible(false);
		for (int i=0; i< rows; i++) {
			TableItem item = new TableItem (aMatrix, SWT.NONE);
			String[] rowText = new String[columns];
			for(int c = 0; c < columns; c++) {
				rowText[c] = ""+c;
			}
			item.setText(rowText);
		}

		aMatrix.addListener (SWT.MouseDown, new MatrixTableListener(aMatrix));

		aMatrixContainer.pack();
		aMatrixContainer.layout();
		return aMatrixContainer;
	}
	
	
	
		
	

}
