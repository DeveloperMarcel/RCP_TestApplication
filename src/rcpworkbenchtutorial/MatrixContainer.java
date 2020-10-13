package rcpworkbenchtutorial;

import javax.annotation.PostConstruct;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import swing2swt.layout.BorderLayout;


public class MatrixContainer {
	private Table table;

	

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new BorderLayout(0, 0));
		
		table = new Table(parent, SWT.BORDER | SWT.FULL_SELECTION);
		table.setLayoutData(BorderLayout.CENTER);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		
		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true,true,1,1);
		
		table.setLayoutData(gd_table);
		table.setHeaderVisible(false);
		table.setLinesVisible(true);
		
	}
	

}
