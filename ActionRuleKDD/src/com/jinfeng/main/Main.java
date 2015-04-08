
package com.jinfeng.main;

import java.io.*;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.text.DefaultCaret;




/**
 * This is my Main class.
 * It creates the User Interface. Also it causes the response in the other classes to implement the algorithm
 * @author Jinfeng Du  Shanzhen Gao
 * 
 */
public class Main extends JPanel implements ActionListener {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static private final String newline = "\n";
    JButton openButton, saveButton, about;
    public static JTextArea log;
    JFileChooser fc;
    
    
    JButton actionButton;
    public static JTextArea dTableText;
    JTextArea actionRuleText;
    public static JTextArea textarea0;
    
    
    File file = null;
    
    ArrayList<ArrayList<String>> dataset;
    
    /**
     * This method initializes the UI platform
     */
    public Main() {
        super(new BorderLayout());

        //Create the log first, because the action listeners
        //need to refer to it.
        log = new JTextArea(5,30);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        Font font1 = log.getFont();
        float size1 = font1.getSize() + 8.0f;
        log.setFont( font1.deriveFont(size1) );

        
        
        
        
        dTableText = new JTextArea(5,30);
        dTableText.setMargin(new Insets(5,5,5,5));
        dTableText.setEditable(false);
        Font font2 = dTableText.getFont();
        float size2 = font2.getSize() + 8.0f;
        dTableText.setFont( font2.deriveFont(size2) );
        
        
        JScrollPane logScrollPane = new JScrollPane(log);
        
        JScrollPane logScrollPane2 = new JScrollPane(dTableText);
        
        //Create a file chooser
        fc = new JFileChooser();

      
        openButton = new JButton("Open a File...");
        openButton.addActionListener(this);

        //Create the save button.  We use the image from the JLF
        //Graphics Repository (but we extracted it from the jar).
        saveButton = new JButton("Action.....");
        saveButton.addActionListener(this);

        about = new JButton("About...");
        about.addActionListener(this);
        
        //about.addActionListener(this);
        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(about);

        
        this.setPreferredSize(new Dimension(1500, 600));//test
        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
     
        add(logScrollPane, BorderLayout.WEST);
        
        add(logScrollPane2, BorderLayout.CENTER);
       
        
        
    }

    
    public void actionPerformed(ActionEvent e) {

        //Handle open button action.
        if (e.getSource() == openButton) {
        	log.setText(null);//test
        	dTableText.setText(null);//test
            int returnVal = fc.showOpenDialog(Main.this);

            if (returnVal == JFileChooser.APPROVE_OPTION) {
                file = fc.getSelectedFile();
                //This is where a real application would open the file.
                log.append("Opening: " + file.getName() + "." + newline);
                dataset = ActionRuleKDD.parseCSV(file);
                ActionRuleKDD.displayDataset(dataset);
                
            } else {
                log.append("Open command cancelled by user." + newline);
            }
            //log.setCaretPosition(log.getDocument().getLength());
            log.setCaretPosition(0);
            
            
            
            
        //Handle save button action.
        } else if (e.getSource() == saveButton) {
        	if (file == null){
        		log.setText(null);
        		log.append("Please Open a file first");
        	}
        	else{
        		dTableText.setText(null);
        		System.out.println("Size of dataset is: " + dataset.size());
        		dataset = ActionRuleKDD.parseCSV(file);//test
                ActionRuleKDD.actionRule(dataset);
                dTableText.setCaretPosition(0);
        	}
        	
            
        }
        else if (e.getSource() == about){
        	createFrame();
        	
        }
    }

   

   
    /**
     * Create the GUI and show it. For thread safety,
     * This method should be invoked from the 
     * event dispatch thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("ActionRule");
        frame.setSize(200, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Add content to the window.
        frame.add(new Main());

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }
    
    public static void createFrame(){
        EventQueue.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                JFrame frame = new JFrame("ActionRule");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                try 
                {
                   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                   e.printStackTrace();
                }
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setOpaque(true);
                JTextArea textArea0 = new JTextArea(15, 50);
                
                Font boldFont = new Font("serif", Font.BOLD, 20);
                textArea0.setFont(boldFont);
                
                textArea0.setText("Team Members: Jinfeng Du ID:800815618\n" + 
                                  "                            Shanzhen Gao ID:800781807" +
                		          "\nUsage:\n"+
                                  "Step1: open a .csv file contains the data. (The last column should be the decision.)\n"+
                		          "Step2: Click action button to implement the ActionRule algorithm");
                
                textArea0.setWrapStyleWord(true);
                textArea0.setEditable(false);
                //textArea0.setFont(Font.getFont(Font.SANS_SERIF));
                JScrollPane scroller = new JScrollPane(textArea0);
                scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
                scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
                
                DefaultCaret caret = (DefaultCaret) textArea0.getCaret();
                caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
                panel.add(scroller);
                frame.getContentPane().add(BorderLayout.CENTER, panel);
                frame.pack();
                frame.setLocationByPlatform(true);
                frame.setVisible(true);
                frame.setResizable(false);
                
            }
        });
    }    
    
    /**
     * This is the main method is the project
     * @author JinfengDu
     */
    public static void main(String[] args) {
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //Turn off metal's use of bold fonts
                UIManager.put("swing.boldMetal", Boolean.FALSE); 
                createAndShowGUI();
            }
        });
    }
}