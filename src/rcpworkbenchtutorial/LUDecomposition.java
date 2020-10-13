package rcpworkbenchtutorial;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;

import static org.eclipse.swt.events.SelectionListener.widgetSelectedAdapter;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Spinner;

import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;

public class LUDecomposition {
	private Table aMatrix;
	private Table cMatrix;
	
	private Group aMatrixContainer;
	private Group cMatrixContainer;
	
	private double[][] coeficientMatrix;
	private double[][] cMatrixValues;
	private Table table;
	private CCombo combo;
	private String[] methods = new String[] {"LU Decomposition", "Gauss-Seidel Method"};
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	@PostConstruct
	public void createComposite(Composite parent) {
		
		parent.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		initializeAMatrix(parent);
		createAMatrixTable(parent, 4,4);
		initializeCMatrix(parent);
		createCMatrixTable(parent, 4);
		
		Group grpResult = new Group(parent, SWT.NONE);
		grpResult.setLayoutData(new RowData(165, 480));
		grpResult.setText("Result");
		
		Group grpCalculationSettings = new Group(grpResult, SWT.NONE);
		grpCalculationSettings.setText("Calculation settings");
		grpCalculationSettings.setBounds(10, 409, 151, 78);
		
		Button btnCalculate = new Button(grpCalculationSettings, SWT.NONE);
		btnCalculate.setBounds(80, 43, 61, 25);
		btnCalculate.setText("Calculate");
		
		combo = new CCombo(grpCalculationSettings, SWT.BORDER);
		combo.setText("Guass-Seidel Method");
		combo.setItems(methods);
		combo.setBounds(10, 16, 131, 21);
		
		table = new Table(grpResult, SWT.BORDER | SWT.FULL_SELECTION);
		table.setBounds(10, 24, 151, 379);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		btnCalculate.addSelectionListener(new CalculateListener());
		
		

	}
	
	private int getIndexOfMethod(String selectedMethod) {
		for(int i =0; i < methods.length; i++) {
			if(methods[i].contentEquals(selectedMethod)) {
				return i;
			}
		}
		return -1;
	}
	private void initializeCMatrix(Composite parent) {
		cMatrixContainer = new Group(parent, SWT.NONE);
		cMatrixContainer.setLayoutData(new RowData(150, 459));
		cMatrixContainer.setText("C Matrix");
		cMatrixContainer.setLayout(new GridLayout(1, false));
		
		cMatrix = new Table(cMatrixContainer, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table_1 = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table_1.widthHint = 135;
		cMatrix.setLayoutData(gd_table_1);
		cMatrix.setHeaderVisible(true);
		cMatrix.setLinesVisible(true);
		
		Group grpSettings_1 = new Group(cMatrixContainer, SWT.NONE);
		GridData gd_grpSettings_1 = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_grpSettings_1.widthHint = 132;
		grpSettings_1.setLayoutData(gd_grpSettings_1);
		grpSettings_1.setText("Settings");
		grpSettings_1.setLayout(new FormLayout());
		
		Label lblRows_1 = new Label(grpSettings_1, SWT.NONE);
		FormData fd_lblRows_1 = new FormData();
		lblRows_1.setLayoutData(fd_lblRows_1);
		lblRows_1.setText("Rows");
		
		Spinner rSpinner = new Spinner(grpSettings_1, SWT.BORDER);
		fd_lblRows_1.top = new FormAttachment(rSpinner, 3, SWT.TOP);
		fd_lblRows_1.right = new FormAttachment(rSpinner, -6);
		rSpinner.setMaximum(16);
		rSpinner.setMinimum(2);
		FormData fd_spinner_2 = new FormData();
		fd_spinner_2.left = new FormAttachment(0, 34);
		rSpinner.setLayoutData(fd_spinner_2);
		rSpinner.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				TableMethods.redrawDefaultMatrixTable(cMatrix, 1, Integer.parseInt(rSpinner.getText()));
				
			}
			
		});
	}
	private void createCMatrixTable(Composite parent, int rows) {
		for(int c = 0; c < 1; c++) {
			TableColumn tc1 = new TableColumn(cMatrix, SWT.CENTER);
			tc1.setWidth(70);
		}
		for (int i=0; i< rows; i++) {
			TableItem item = new TableItem (cMatrix, SWT.NONE);
			String[] rowText = new String[1];
			for(int c = 0; c < 1; c++) {
				rowText[c] = ""+c;
			}
			item.setText(rowText);
		}
		cMatrix.addListener(SWT.MouseDown, new MatrixTableListener(cMatrix));
	}
	private void initializeAMatrix(Composite parent) {
		aMatrixContainer = new Group(parent, SWT.NONE);
		aMatrixContainer.setLayoutData(new RowData(354, 457));
		aMatrixContainer.setText("A Matrix");
		aMatrixContainer.setLayout(new GridLayout(1, false));
		
		aMatrix = new Table(aMatrixContainer, SWT.BORDER | SWT.FULL_SELECTION);
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table.heightHint = 343;
		aMatrix.setLayoutData(gd_table);
		aMatrix.setHeaderVisible(true);
		aMatrix.setLinesVisible(true);
		
		Group grpSettings = new Group(aMatrixContainer, SWT.NONE);
		GridData gd_grpSettings = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_grpSettings.widthHint = 263;
		grpSettings.setLayoutData(gd_grpSettings);
		grpSettings.setText("Settings");
		grpSettings.setLayout(new FormLayout());
		
		Label lblRows = new Label(grpSettings, SWT.NONE);
		FormData fd_lblRows = new FormData();
		fd_lblRows.top = new FormAttachment(0, 3);
		fd_lblRows.left = new FormAttachment(0, 10);
		lblRows.setLayoutData(fd_lblRows);
		lblRows.setText("Rows");
		
		Spinner rSpinner = new Spinner(grpSettings, SWT.BORDER);
		rSpinner.setMaximum(16);
		rSpinner.setMinimum(2);
		rSpinner.setSelection(4);
		FormData fd_spinner = new FormData();
		fd_spinner.top = new FormAttachment(lblRows, -3, SWT.TOP);
		fd_spinner.left = new FormAttachment(lblRows, 6);
		rSpinner.setLayoutData(fd_spinner);
		
		Label lblNewLabel = new Label(grpSettings, SWT.NONE);
		FormData fd_lblNewLabel = new FormData();
		fd_lblNewLabel.top = new FormAttachment(lblRows, 0, SWT.TOP);
		fd_lblNewLabel.left = new FormAttachment(rSpinner, 6);
		lblNewLabel.setLayoutData(fd_lblNewLabel);
		lblNewLabel.setText("Columns");
		
		Spinner cSpinner = new Spinner(grpSettings, SWT.BORDER);
		cSpinner.setMaximum(16);
		cSpinner.setMinimum(2);
		cSpinner.setSelection(4);
		FormData fd_spinner_1 = new FormData();
		fd_spinner_1.top = new FormAttachment(lblRows, -3, SWT.TOP);
		fd_spinner_1.left = new FormAttachment(lblNewLabel, 6);
		cSpinner.setLayoutData(fd_spinner_1);
		
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
	}
	private void createAMatrixTable(Composite parent, int rows, int columns) {
		for(int c = 0; c < columns; c++) {
			TableColumn tc1 = new TableColumn(aMatrix, SWT.CENTER);
			tc1.setWidth(70);
		}
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
	}
	
	class CalculateListener implements SelectionListener{
		private int method_type = 0;
		
		@Override
		public void widgetSelected(SelectionEvent e) {
			method_type = getIndexOfMethod(combo.getText());
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
			if(method_type == 0) {
				MatrixHandler.luDecomposition(coeficientMatrix, aMatrix.getColumnCount(),cMatrixValues);
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}
}

