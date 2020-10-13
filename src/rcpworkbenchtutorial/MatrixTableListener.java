package rcpworkbenchtutorial;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

public class MatrixTableListener implements Listener{
	Table parentTable;
	final TableEditor editor;
	public MatrixTableListener(Table parent) {
		parentTable = parent;
		editor = new TableEditor (parentTable);
		editor.horizontalAlignment = SWT.LEFT;
		editor.grabHorizontal = true;
	}
	@Override
	public void handleEvent(Event event) {
		Rectangle clientArea = parentTable.getClientArea ();
		Point pt = new Point (event.x, event.y);
		int index = parentTable.getTopIndex ();
		while (index < parentTable.getItemCount ()) {
			boolean visible = false;
			final TableItem item = parentTable.getItem (index);
			for (int i=0; i<parentTable.getColumnCount (); i++) {
				Rectangle rect = item.getBounds (i);
				if (rect.contains (pt)) {
					final int column = i;
					final Text text = new Text (parentTable, SWT.NONE);
					Listener textListener = e -> {
						switch (e.type) {
							case SWT.FocusOut:
								item.setText (column, text.getText ());
								text.dispose ();
								break;
							case SWT.Traverse:
								switch (e.detail) {
									case SWT.TRAVERSE_RETURN:
										item.setText (column, text.getText ());
										//FALL THROUGH
									case SWT.TRAVERSE_ESCAPE:
										text.dispose ();
										e.doit = false;
								}
								break;
						}
					};
					text.addListener (SWT.FocusOut, textListener);
					text.addListener (SWT.Traverse, textListener);
					editor.setEditor (text, item, i);
					text.setText (item.getText (i));
					text.selectAll ();
					text.setFocus ();
					return;
				}
				if (!visible && rect.intersects (clientArea)) {
					visible = true;
				}
			}
			if (!visible) return;
			index++;
		}
		
	}
}
