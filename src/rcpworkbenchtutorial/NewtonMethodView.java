package rcpworkbenchtutorial;

import javax.annotation.PostConstruct;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Text;



import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.layout.RowData;

public class NewtonMethodView {
	private Text txtP0;
	private Text txtTol;
	private Text txtMaxIter;
	private Text txtOutput;
	
	private NewtonMethod newton = new NewtonMethod(this);

	/**
	 * Create the composite.
	 * @param parent
	 */
	@PostConstruct
	public void createComposite(Composite parent) {
		parent.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		Group grpNewtonMethodInputs = new Group(parent, SWT.NONE);
		grpNewtonMethodInputs.setLayoutData(new RowData(SWT.DEFAULT, 290));
		grpNewtonMethodInputs.setText("Newton Method inputs");
		grpNewtonMethodInputs.setLayout(new FormLayout());
		
		Label lblInitialP = new Label(grpNewtonMethodInputs, SWT.NONE);
		FormData fd_lblInitialP = new FormData();
		fd_lblInitialP.left = new FormAttachment(0, 15);
		lblInitialP.setLayoutData(fd_lblInitialP);
		lblInitialP.setText("Initial P0");
		
		Label lblTolerance = new Label(grpNewtonMethodInputs, SWT.NONE);
		FormData fd_lblTolerance = new FormData();
		fd_lblTolerance.top = new FormAttachment(lblInitialP, 18);
		fd_lblTolerance.right = new FormAttachment(lblInitialP, 0, SWT.RIGHT);
		lblTolerance.setLayoutData(fd_lblTolerance);
		lblTolerance.setText("Tolerance");
		
		Label lblMaximumIterations = new Label(grpNewtonMethodInputs, SWT.NONE);
		FormData fd_lblMaximumIterations = new FormData();
		fd_lblMaximumIterations.left = new FormAttachment(0, 10);
		lblMaximumIterations.setLayoutData(fd_lblMaximumIterations);
		lblMaximumIterations.setText("Maximum iterations");
		
		txtP0 = new Text(grpNewtonMethodInputs, SWT.BORDER);
		fd_lblInitialP.top = new FormAttachment(txtP0, 3, SWT.TOP);
		FormData fd_text = new FormData();
		fd_text.left = new FormAttachment(lblInitialP, 28);
		fd_text.top = new FormAttachment(0, 7);
		txtP0.setLayoutData(fd_text);
		
		txtTol = new Text(grpNewtonMethodInputs, SWT.BORDER);
		fd_lblMaximumIterations.top = new FormAttachment(txtTol, 19);
		txtTol.setText("0.000000001");
		FormData fd_text_1 = new FormData();
		fd_text_1.top = new FormAttachment(lblTolerance, -3, SWT.TOP);
		fd_text_1.right = new FormAttachment(txtP0, 0, SWT.RIGHT);
		txtTol.setLayoutData(fd_text_1);
		
		txtMaxIter = new Text(grpNewtonMethodInputs, SWT.BORDER);
		txtMaxIter.setText("200");
		FormData fd_text_2 = new FormData();
		fd_text_2.top = new FormAttachment(lblMaximumIterations, -3, SWT.TOP);
		fd_text_2.right = new FormAttachment(txtP0, 0, SWT.RIGHT);
		txtMaxIter.setLayoutData(fd_text_2);
		
		Button btnCalculate = new Button(grpNewtonMethodInputs, SWT.NONE);
		btnCalculate.addListener(SWT.Selection, new CalculateListener());
		FormData fd_btnCalculate = new FormData();
		fd_btnCalculate.bottom = new FormAttachment(100, -29);
		fd_btnCalculate.left = new FormAttachment(lblInitialP, 0, SWT.LEFT);
		btnCalculate.setLayoutData(fd_btnCalculate);
		btnCalculate.setText("Calculate");
		
		Group grpOutput = new Group(parent, SWT.NONE);
		grpOutput.setText("Output");
		
		txtOutput = new Text(grpOutput, SWT.BORDER | SWT.WRAP | SWT.MULTI);
		txtOutput.setBounds(10, 21, 439, 279);
		
		
	}
	
	public void addOutputLine(String updateLine) {
		txtOutput.append(updateLine+"\n");
	
		txtOutput.getParent().layout();
	}
	
	private class CalculateListener implements Listener{

		@Override
		public void handleEvent(Event event) {
			newton.setX(Double.valueOf(txtP0.getText()));
			newton.setTolerance(Double.valueOf(txtTol.getText()));
			newton.setMaxCount(Integer.valueOf(txtMaxIter.getText()));
			newton.calculate();
		}
		
	}
}
