// CET 350
// Main.java
// Created: 2/15/2017
// Last Edited: 2/22/2017

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.*;
import java.nio.file.*;

public class Main extends Frame implements ActionListener, WindowListener, KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	// Variable declarations
	int colWidth[] = {20,70,20,450,20,40,20};
	int rowHeight[] = {40,310,20,20,10,20,10, 20, 30};
	double colWeight[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	double rowWeight[] = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
	boolean sourceBool, targetBool, subDirsBool, subDirsBool1;
	
	// Button and text field declarations
	Button OKButton = new Button("OK");
	Button SOURCEButton = new Button("SOURCE");
	Button TARGETButton = new Button("TARGET");
	Label FILENAMELabel = new Label ("FILE NAME: ");
	TextField directoryText = new TextField();
	TextField sourceText = new TextField();
	TextField targetText = new TextField();
	TextField filenameText = new TextField();
	
	// These variables are outside of main so they can be edited by multiple methods
	File folder = new File(System.getProperty("user.dir"));
	File[] curDir = folder.listFiles(); 
	
	Main(){		
		
		// Frame parameters
		this.setTitle(System.getProperty("user.dir"));
		setLayout(null);
		this.setSize(640, 480);
		this.setLocation(50, 50);
		this.setResizable(true);
		this.setVisible(true);
		this.addWindowListener(this);
	
		
		/*Directory text layout
			If a directory is detected then it will loop thru 
			an array of the sub-directory. If another directory is found
			in the sub-directory then it's flagged. IF statements are used to 
			decipher the various flags when printing i.e. "+" or ". ."	
		*/
		List dirList = new List(20, false);
		File buffer = folder.getParentFile();
		if(buffer.exists()){
			dirList.add(". .");
		}
		for(int i=0; i<curDir.length; i++){
			if(curDir[i].isDirectory()){
				subDirsBool=false;
				File folder1 = curDir[i];
				File[] subDirs = folder1.listFiles();
					for(int j = 0; j<subDirs.length; j++){
						if(subDirs[j].isDirectory()){
							subDirsBool = true;
						} }
						 if(subDirsBool == true){
							dirList.add(curDir[i].toString() +"+");
						} else{
							dirList.add(curDir[i].toString());
						}
						
					} else if(curDir[i].isFile()){
						dirList.add(curDir[i].toString());
			} 
			}
		
		
	    // Directory list layout
		this.add(dirList);
		dirList.setSize(600, 310);
		dirList.setLocation(20, 40);
		dirList.setVisible(true);
		dirList.addActionListener(this); 

		// SOURCE button layout
		this.add(SOURCEButton);		
		SOURCEButton.setSize(70, 20);
		SOURCEButton.setLocation(20, 370);
		SOURCEButton.addActionListener(this);

		//Source text area
		this.add(sourceText);
		sourceText.setSize(450, 20);
		sourceText.setLocation(110, 370);
		sourceText.addKeyListener(this);
		
		// TARGET button layout
		this.add(TARGETButton);
		TARGETButton.setSize(70, 20);
		TARGETButton.setLocation(20, 400);
		TARGETButton.addActionListener(this);

		//Target text area
		this.add(targetText);
		targetText.setSize(450, 20);
		targetText.setLocation(110, 400);
		targetText.addKeyListener(this);
		
		// File name label
		this.add(FILENAMELabel);
		FILENAMELabel.setSize(70, 20);
		FILENAMELabel.setLocation(20, 430);
		
		//File name text area
		this.add(filenameText);
		filenameText.setSize(450, 20);
		filenameText.setLocation(110, 430);
		filenameText.addKeyListener(this);
		
		// OK button layout
		this.add(OKButton);
		OKButton.setSize(40, 80);
		OKButton.setLocation(580, 370);
		OKButton.addActionListener(this);

		}

	public static void main(String[] args){
		Main m = new Main();
	} // End of public static void main
	
	
	// this method is waiting to hear something from the various buttons & fields
	public void actionPerformed(ActionEvent ae) {
		Object src = ae.getSource();
		
		if(src == SOURCEButton){  // puts program into SOURCE MODE
			sourceBool = true;
			targetBool = false;
		}  
		if(src == TARGETButton){ // puts Program into TARGET MODE
			sourceBool = false;
			targetBool = true;
		} 
		if((src != SOURCEButton) && (src != TARGETButton) && (src != OKButton)){  // if listener doesn't hear anything from the buttons then it
			if(sourceBool == true && targetBool == false){						  // will fill the text field depending on the current MODE
				sourceText.setText(ae.getActionCommand());
			} else if(sourceBool == false && targetBool == true){
				targetText.setText(ae.getActionCommand());
			}
		} 
		
		if(src == OKButton && filenameText.getText().equals("")){
			// copies files that user selects from the directory list
			File sourceFile, targetFile;
			sourceFile = new File(sourceText.getText());
			targetFile = new File(targetText.getText());
			try{
			Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}catch(Exception e){
				System.out.print("ERROR");
			}
			}
		
		if(src == OKButton && !filenameText.getText().equals("")){ // copies files using the directory list & user defined selection
			File sourceFile, targetFile;
			sourceFile = new File(sourceText.getText());
			targetFile = new File(filenameText.getText());
			try{
			Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}catch(Exception e){
				System.out.print("ERROR");
			}
			}  
		
	}
	
	// This method is responsible to responding to the ENTER key 
	// The IF statements are setup in the same manner as the Button IF statements
	public void keyPressed(KeyEvent e) {
		
		Object src1 = e.getSource();
		
		if((src1 == filenameText || src1 == sourceText || src1 == targetText) && filenameText.getText().equals("")){
			// copies files that user selects from the directory list
			File sourceFile, targetFile;
			sourceFile = new File(sourceText.getText());
			targetFile = new File(targetText.getText());
			try{
			Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}catch(Exception f){
				System.out.print("ERROR");
			}
			}
		if((src1 == filenameText || src1 == sourceText || src1 == targetText) && !filenameText.getText().equals("")){ // copies files using the directory list & user defined selection
			File sourceFile, targetFile;
			sourceFile = new File(sourceText.getText());
			targetFile = new File(filenameText.getText());
			try{
			Files.copy(sourceFile.toPath(), targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}catch(Exception k){
				System.out.print("ERROR");
			}
			} 
	}
	
	// This method removes all the ActionListeners so the window can close properly	
	public void WindowClosing(WindowEvent e)
	{
		OKButton.removeActionListener(this);
		SOURCEButton.removeActionListener(this);
		TARGETButton.removeActionListener(this);
		this.removeWindowListener(this);
		System.out.println("Closing program");
		this.dispose();
	}
	
	
	public void WindowActivated(WindowEvent e){
		System.out.println("Activate");
	}
	
	
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	

} // End of public class Main