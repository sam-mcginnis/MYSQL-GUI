import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;


import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.Box;


public class App_Display extends JFrame 
{
   // default query retrieves all data from bikes table
   static final String DEFAULT_QUERY = "SHOW TABLES";
   private boolean connect;
   private DBconnector tableModel;
   private JTextArea queryArea;
   
   // create ResultSetTableModel and GUI
   public App_Display() throws ClassNotFoundException, SQLException 
   {   
      super( "Displaying Query Results" );
        
      GridBagLayout gridWindow = new GridBagLayout();
	  GridBagConstraints LY= new GridBagConstraints();
	  
	  JPanel container = new JPanel();
	  
	  JPanel ConnectOpt = new JPanel();
	  JPanel inputCode = new JPanel();
	  JPanel buttonPanel = new JPanel();
	  JPanel ResultWindow = new JPanel();	  
	  
	  // create TableModel for results of query SELECT * FROM bikes
	  tableModel = new DBconnector();

	  
	  ConnectOpt.setLayout(gridWindow);
	  buttonPanel.setLayout(gridWindow);
	  inputCode.setLayout(gridWindow);
	  ResultWindow.setLayout(gridWindow);
	  
	  JLabel Label0 = new JLabel("JDBC Driver");
	  String s1[] = {"com.mysql.cj.jdbc.Driver"};
	  JComboBox Text0 = new JComboBox(s1);
	  Text0.setPreferredSize(new Dimension(225,25));
	  
	  LY.gridx = 0;
	  LY.gridy = 0;
	  ConnectOpt.add(Label0, LY);
	  
	  LY.gridx = 1;
	  LY.gridy = 0;
	  ConnectOpt.add(Text0, LY);

	  
	  JLabel Label1 = new JLabel("Database URL");
	  String s2[] = {"jdbc:mysql://localhost:3306/project3?useTimezone=true&serverTimeZone=UTC"};
	  JComboBox Text1 = new JComboBox(s2);
	  Text1.setPreferredSize(new Dimension(225,25));
	  
	  LY.gridx = 0;
	  LY.gridy = 1;
	  ConnectOpt.add(Label1, LY);

	  LY.gridx = 1; 
	  LY.gridy = 1;       
	  ConnectOpt.add(Text1, LY);
	  
	  JLabel Label2 = new JLabel("Username");
	  JTextField Text2 = new JTextField(20);
	  LY.gridy = 2;
	  LY.gridx = 0;
	  ConnectOpt.add(Label2, LY);

	  LY.gridx = 1; 
	  LY.gridy = 2;       
	  ConnectOpt.add(Text2, LY);

	  JLabel Label3 = new JLabel("Password");
	  JPasswordField Text3 = new JPasswordField(20);
	  Text3.setEchoChar('*');
	  LY.gridy = 3;
	  LY.gridx = 0;
	  ConnectOpt.add(Label3, LY);

	  LY.gridx = 1; 
	  LY.gridy = 3;       
	  ConnectOpt.add(Text3, LY);
	  
	  JLabel CL= new JLabel("Enter An Sql Command");
	  LY.gridx = 0;
      LY.gridy = 0;
      inputCode.add(CL, LY);

	  JTextArea EC= new JTextArea(5,25);
      LY.gridx = 0; 
      LY.gridy = 1;       
      inputCode.add(EC, LY);
      
      
      JButton connectBtnStatus = new JButton("No Connection Now");
      LY.gridx = 0;
      LY.gridy = 0;     
      connectBtnStatus.setForeground(Color.BLACK);
      connectBtnStatus.setBackground(Color.RED);
      buttonPanel.add(connectBtnStatus, LY);
      
      JButton execButtonConnection = new JButton("Connect to Database");
      LY.gridx = 1;
      LY.gridy = 0;      
      buttonPanel.add(execButtonConnection, LY);
      
      
      JButton clearTerminalBtn = new JButton("Clear SQL Command");
      LY.gridx = 2;
      LY.gridy = 0;  
      buttonPanel.add(clearTerminalBtn, LY);

      JButton QueryBtn = new JButton("Execute SQL Command");
      LY.gridx = 3;
      LY.gridy = 0;    
      buttonPanel.add(QueryBtn, LY);
      
      JLabel ResultLabel = new JLabel("SQL Execution Result Window");
      LY.gridx = 0;
      LY.gridy = 0;
      ResultWindow.add(ResultLabel, LY);
      
      JTable resultsTable= new JTable();
      LY.gridx = 0; 
      LY.gridy = 1;       
      ResultWindow.add(new JScrollPane(resultsTable), LY);
     
      JButton ClearResultWindowBtn= new JButton("Clear Result Window");
      LY.gridx = 0; 
      LY.gridy = 2;       
      ResultWindow.add(ClearResultWindowBtn, LY);

	
	  container.setLayout(gridWindow);
	  LY.gridx = 0;
	  LY.gridy = 0;        
	  container.add(ConnectOpt, LY);
	  
	  LY.gridx = 1;
	  LY.gridy = 0;        
	  container.add(inputCode, LY);

	  LY.gridx = 0;
	  LY.gridwidth = 2;
	  LY.gridy = 1;        
	  container.add(buttonPanel, LY);
	 
	  LY.gridx = 0;
	  LY.gridy = 2;        
	  container.add(ResultWindow, LY);

	 
	 // place GUI components on content pane
	  execButtonConnection.addActionListener(
			  new ActionListener() {
			       public void actionPerformed( ActionEvent event ) {
			    	   
				       
					 if(connect == false) {
						 
						 String pass = new String(Text3.getPassword());
						 try {
							tableModel.login("SHOW TABLES",Text2.getText(), pass);

						 	resultsTable.setModel(tableModel);
						 	connectBtnStatus.setText("You are now Connected");
						 	connectBtnStatus.setBackground(Color.GREEN);
						 	connect = true;
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					 }
					 else
						 JOptionPane.showMessageDialog(App_Display.this, String.format("You are already connected to a database!"));
			       }
				  
   });
	  clearTerminalBtn.addActionListener(
	  	new ActionListener() {
	  		@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
	  				EC.setText("");
			}
	  	});
	 // create event listener for submitButton
	 QueryBtn.addActionListener( 
	 
	    new ActionListener() 
	    {
	       // pass query to table model
	       public void actionPerformed( ActionEvent event )
	       {
	          // perform a new query
	          try 
	          {
	        	  if(connect == true) {
	        	  String mystring = EC.getText();
					 String arr[] = mystring.split(" ", 2);

					 String firstWord = arr[0];   //the
					 String theRest = arr[1];
					 if(firstWord.equalsIgnoreCase("select")) {
						 tableModel.setQuery(EC.getText());
					 }
						 
					 else 
						tableModel.setUpdate(EC.getText());
	        	  }
	        	  else
						 JOptionPane.showMessageDialog(App_Display.this, String.format("You must be connected to a DataBase to execute commands"));
	          } // end try
	          catch ( SQLException sqlException ) 
	          {
	        	  JOptionPane.showMessageDialog( null, 
	                   sqlException.getMessage(), "Database error", 
	                   JOptionPane.ERROR_MESSAGE );
	 
	                // ensure database connection is closed
	 
	             } // end inner catch // end outer catch
	       } // end actionPerformed
	    }  // end ActionListener inner class          
	 ); // end call to addActionListener
	 
	 ClearResultWindowBtn.addActionListener(
			 new ActionListener(){
				 public void actionPerformed( ActionEvent event ) {
					 tableModel.clear();
				 }
			 });
	 
	 setSize( 1000, 750 ); // set window size
	 setVisible( true ); // display window  
	 this.add(container);
      
      // dispose of window when user quits application (this overrides
      // the default of HIDE_ON_CLOSE)
      setDefaultCloseOperation( DISPOSE_ON_CLOSE );
      
      // ensure database connection is closed when user quits application
      addWindowListener(new WindowAdapter() 
         {
            // disconnect from database and exit when window has closed
            public void windowClosed( WindowEvent event )
            {
               tableModel.disconnectFromDatabase();
               System.exit( 0 );
            } // end method windowClosed
         } // end WindowAdapter inner class
      ); // end call to addWindowListener
   } // end DisplayQueryResults constructor
   
   // execute application
   public static void main( String args[] ) throws ClassNotFoundException, SQLException 
   {
      new App_Display();     
   } // end main
} // end class DisplayQueryResults



