package rcpworkbenchtutorial;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

public class TableMethods {
	
public static void redrawDefaultMatrixTable(Table matrix, int columns, int rows) {
		
		if((matrix.getColumnCount() != columns)||(matrix.getItemCount() != rows)) {
			matrix.setRedraw(false);
			//clear previous table
			clearTable(matrix);
			
			//set columns
			addColumns(matrix,columns, 70);
			//set rows
			addRows(matrix, columns,rows, null);
			
			matrix.setRedraw(true);
			
		}
	}

public static void clearTable(Table matrix) {
	while(matrix.getColumnCount() > 0) {
		matrix.getColumns()[0].dispose();
	}
	while(matrix.getItemCount() > 0) {
		matrix.getItems()[0].dispose();
	}
}

public static void addColumns(Table matrix, int columns, int colwidth) {
	for(int c = 0; c < columns; c++) {
		TableColumn tc1 = new TableColumn(matrix, SWT.CENTER);
		tc1.setWidth(colwidth);
	}
}

public static void addRows(Table matrix,int columns, int rows, String[][] rowValues) {
	String[] rowText = new String[0];
	for (int i=0; i < rows; i++) {
		TableItem item = new TableItem (matrix, SWT.NONE);
		if(rowValues == null) {
			rowText = new String[columns];
			for(int c = 0; c < columns; c++) {
				rowText[c] = ""+c;
			}
		}
		else {
			rowText = rowValues[i];
		}
		item.setText(rowText);
	}
}

}
