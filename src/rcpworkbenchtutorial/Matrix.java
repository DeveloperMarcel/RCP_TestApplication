package rcpworkbenchtutorial;

import javax.annotation.PostConstruct;
import org.eclipse.swt.widgets.Composite;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Label;



public class Matrix {
	private Table table;

	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new BorderLayout(0, 0));
		
		table = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(BorderLayout.CENTER);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		TableColumn tc1 = new TableColumn(table,SWT.NONE);
		TableColumn tc2 = new TableColumn(table,SWT.NONE);
		TableColumn tc3 = new TableColumn(table,SWT.NONE);
		
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayoutData(BorderLayout.SOUTH);
		
		Spinner spinner = new Spinner(composite, SWT.BORDER);
		spinner.setBounds(88, 7, 47, 22);
		
		Spinner spinner_1 = new Spinner(composite, SWT.BORDER);
		spinner_1.setBounds(195, 7, 47, 22);
		
		Label lblNewLabel = new Label(composite, SWT.NONE);
		lblNewLabel.setBounds(27, 10, 55, 15);
		lblNewLabel.setText("Rows");
		
		Label lblNewLabel_1 = new Label(composite, SWT.NONE);
		lblNewLabel_1.setBounds(141, 10, 55, 15);
		lblNewLabel_1.setText("Columns");
		for(int i =0; i < 5; i++) {
			TableItem item = new TableItem(table, SWT.NONE);
			String[] rowText = new String[3];
			for(int j= 0; j < 3;j++) {
				rowText[j] = ""+j;
			}
			item.setText(rowText);
		}
		for(int i =0; i < 3;i++) {
			table.getColumn(i).pack();
		}
	
	}
}
