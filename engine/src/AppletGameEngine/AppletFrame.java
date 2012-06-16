package AppletGameEngine;

import java.awt.*;
import java.awt.event.*;

/**
 * @author Joshua Taylor
 */
public class AppletFrame extends Frame implements ActionListener
{
	public AppletMain app;
	public MenuBar menuBar;
	public Menu fileMenu;

	/**
	 * Override this to add more menus or menu items.
	 */
	public void BuildMenu()
	{
	}

	/**
	 * Override this to react to new menu items.
	 * @param label		The label of the menu item selected.
	 */
	public void HandleMenu(String label)
	{
	}

	/**
	 * Create a new frame to run the applet as a standalone application.
	 * @param name			The name to display on the title bar
	 * @param w				The width of the application
	 * @param h				The height of the application
	 * @param app			The applet to be framed
	 */
	public AppletFrame(String name, int w, int h, AppletMain app)
	{
		super();

		// Create the menu bar
		menuBar = new MenuBar();
		setMenuBar(menuBar);

		// Create the file menu
		fileMenu = new Menu("File");
		menuBar.add(fileMenu);

		// Call BuildMenu to add additional menus
		BuildMenu();

		// Add the Exit option at the bottom of the file menu
		MenuItem exit = new MenuItem("Exit");
		fileMenu.add(exit);
		exit.addActionListener(this);

		// Add the applet
		add(app, BorderLayout.CENTER);
		pack();
		setVisible(true);
		setSize(w, h);

		// Resize to account for the borders
		Insets in = getInsets();
		setSize(w + in.left + in.right, h + in.top + in.bottom);

		// Set the applet to the same size
		app.setSize(w, h);

		// Start the applet
		app.init();
		app.start();
		this.app = app;

		// Handle the X button
		addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				shutdown();
			}
		});
	}

	/**
	 * Close everything down safely
	 */
	public void shutdown()
	{
		app.destroy();
		while(app.isRunning())
		{
			try
			{
				Thread.sleep(10);
			}
			catch(InterruptedException i)
			{
			}
		}
		dispose();
		System.exit(0);
	}

	/**
	 * A menu item was selected.
	 * @param e		The event generated
	 */
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource() instanceof MenuItem)
		{
			MenuItem item = (MenuItem)e.getSource();
			String label = item.getLabel();

			if(label.equals("Exit"))
				shutdown();
			else
				HandleMenu(label);
		}
	}
}
