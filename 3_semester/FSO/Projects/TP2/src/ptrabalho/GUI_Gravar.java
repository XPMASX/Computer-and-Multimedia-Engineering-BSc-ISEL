package ptrabalho;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JToggleButton;
import javax.swing.WindowConstants;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;

public class GUI_Gravar extends GUI_Base {

    private JPanel contentPane;
    private JTextField txtFile;
    protected JToggleButton tglGravar;
    protected JToggleButton tglReproduzir;
    protected JButton btnFile;


    public GUI_Gravar(BD_Gravar bd) 
	{
		super(bd);
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					init_Gravar(bd);
					
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

  public void init_Gravar(BD_Gravar bd) {
      
	  setTitle("Trabalho 2 - Gravar");
	  setDefaultCloseOperation(WindowConstants. DO_NOTHING_ON_CLOSE);
      setBounds(1000, 400, 760, 600);
      txtFile = new JTextField();
      txtFile.setBounds(206, 192, 520, 31);
      getContentPane().add(txtFile);
      txtFile.setColumns(10);
      
      tglGravar = new JToggleButton("Gravar/Parar");
      tglGravar.setEnabled(false);
      tglGravar.setFont(new Font("Tahoma", Font.PLAIN, 15));
      tglGravar.setBounds(10, 34, 186, 31);
      getContentPane().add(tglGravar);
      
      tglReproduzir = new JToggleButton("Reproduzir");
      tglReproduzir.setEnabled(false);
      tglReproduzir.setFont(new Font("Tahoma", Font.PLAIN, 15));
      tglReproduzir.setBounds(10, 118, 186, 31);
      getContentPane().add(tglReproduzir);
      
      btnFile = new JButton("Selecionar Ficheiro");
      btnFile.setEnabled(false);
      btnFile.setFont(new Font("Tahoma", Font.PLAIN, 15));
      btnFile.setBounds(10, 192, 186, 31);
      getContentPane().add(btnFile);
      btnFile.addActionListener(new ActionListener() 
      {
    	  public void actionPerformed(ActionEvent e) 
    	  {
    		  JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
                	if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) 
                	{
                		String file = fileChooser.getSelectedFile().getAbsolutePath();
                		bd.setNome(file);
                		write(" O nome do ficheiro é " + file + "\n");
                		txtFile.setText(file);
                	}
           }
       });
      
      txtFile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.setNome(txtFile.getText());
				write(" O nome do ficheiro é " + bd.getNome() + "\n");
			}
		});
      
      tglGravar.addActionListener(new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        // Check if the toggle button is selected (on)
    	        boolean isOn = tglGravar.isSelected();

    	        // Set the 'gravar' boolean based on the toggle button state
    	        try {
					bd.setGravar(isOn);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
    	        if(isOn) {
    	        	// Create an empty byte array
    	            byte[] emptyBytes = new byte[0];

    	            // Open the file in write mode and overwrite it with an empty byte array
    	            
    	            Path path = Paths.get(bd.getNome());
    	            try {
						Files.write(path, emptyBytes, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    	        }
    	        else
    	        {
    	        	// Try-with-resources to automatically close resources (like FileWriter)
    	            try (BufferedWriter writer = new BufferedWriter(new FileWriter(bd.getNome(), true))) {
    	                // Write data to the file
    	                writer.write("5" + System.lineSeparator());
    	                System.out.println("Data has been successfully saved to the file.");
    	            } catch (IOException e1) {
    	                // Handle exceptions
    	                e1.printStackTrace();
    	            }
    	        }
    	    }
    	});
      
      tglReproduzir.addActionListener(new ActionListener() {
  	    public void actionPerformed(ActionEvent e) {
  	        // Check if the toggle button is selected (on)
  	        boolean isOn = tglReproduzir.isSelected();

  	        // Set the 'gravar' boolean based on the toggle button state
  	        bd.setReproduzir(isOn);

  	        // Append a message to the 'txtLog' text component
  	        if(isOn)
  	        	write(" Começou a reproduzir \n");
  	        else
  	        	write(" Parou de reproduzir \n");
  	    }
  	});


      setVisible(true);
    }
  
  protected void start()
	{
		btnFile.setEnabled(true);
		tglReproduzir.setEnabled(true);
		tglGravar.setEnabled(true);
	}
  
  protected void off()
	{
		btnFile.setEnabled(false);
		tglReproduzir.setEnabled(false);
		tglGravar.setEnabled(false);
	}
}