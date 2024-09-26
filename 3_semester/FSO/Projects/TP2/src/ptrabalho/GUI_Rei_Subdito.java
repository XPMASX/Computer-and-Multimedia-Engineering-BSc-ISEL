package ptrabalho;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JCheckBox;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.Semaphore;

import javax.swing.SwingConstants;

public class GUI_Rei_Subdito extends GUI_Base {

    private JPanel contentPane;
    private App_Rei appRei;
    private App_Subdito appSub;
    private App_Gravar appGravar;
    private GUI_Subdito gui_Subdito;
    private GUI_Gravar gui_Gravar;
    
    private BD_Rei bdRei = new BD_Rei();
    private BD_Gravar bdGravar = new BD_Gravar();
    private Gravador gravador = new Gravador(bdGravar);
    private BD_Subdito bdSub = new BD_Subdito(gravador);
    private Semaphore subAvailable = new Semaphore(0);
    private Semaphore reiAvailable = new Semaphore(0);
    private Semaphore gravarAvailable = new Semaphore(0);
    
    private BufferCircular bcG = new BufferCircular();
    private Semaphore haTrabalhoG = new Semaphore(1);
   

    public GUI_Rei_Subdito(BD_Base bd, BufferCircular bc, Semaphore ht) 
	{
		super(bd);
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					init_Rei_Subdito(bc, ht);
					
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}


  public void init_Rei_Subdito(BufferCircular bc, Semaphore ht) {
      
	  setTitle("Trabalho 2 - Rei_Subdito");
	  //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	  setBounds(0, 0, 754, 600);
	  
	  
		
      //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      getContentPane().setLayout(null);
      JCheckBox chckbxRei = new JCheckBox("REI");
      chckbxRei.setHorizontalAlignment(SwingConstants.LEFT);
      chckbxRei.setFont(new Font("Tahoma", Font.PLAIN, 15));
      chckbxRei.setBounds(16, 56, 97, 59);
      getContentPane().add(chckbxRei);
      JCheckBox chckbxSubdito = new JCheckBox("SUBDITO");
      chckbxSubdito.setHorizontalAlignment(SwingConstants.LEFT);
      chckbxSubdito.setFont(new Font("Tahoma", Font.PLAIN, 15));
      chckbxSubdito.setBounds(16, 100, 195, 59);
      getContentPane().add(chckbxSubdito);
      JCheckBox chckbxGravar = new JCheckBox("GRAVAR");
      chckbxGravar.setHorizontalAlignment(SwingConstants.LEFT);
      chckbxGravar.setFont(new Font("Tahoma", Font.PLAIN, 15));
      chckbxGravar.setBounds(16, 142, 108, 59);
      getContentPane().add(chckbxGravar);
      appRei = new App_Rei(bdRei, bc, ht, reiAvailable);
  	  Thread tRei = new Thread(appRei);
  	  tRei.start();
  	  
      //appRei.run();
      //gui_Subdito = new GUI_Subdito(bdSub);
      appSub = new App_Subdito(bdSub, bc, ht, subAvailable, bcG, haTrabalhoG);
  	  Thread tSub = new Thread(appSub);
  	  tSub.start();
      appGravar = new App_Gravar(bdGravar, gravador, gravarAvailable, bcG, haTrabalhoG);
      Thread tGravar = new Thread(appGravar);
      tGravar.start();
      
      chckbxRei.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(chckbxRei.isSelected()) {
					if (!appRei.gui.isVisible())
						appRei.gui = new GUI_Rei(bdRei);
					appRei.gui.start();
					System.out.println(bdRei.getMensagens().size());
					reiAvailable.release();
					reiAvailable.release();
					//appRei.run();
					//t.run();
					write("Ativei a GUI_REI \n");
				}
				else {
					appRei.gui.off();
					try {
						reiAvailable.acquire();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					write("Desativei a GUI_REI \n");
				}

			}
		});
      
      chckbxSubdito.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(chckbxSubdito.isSelected()) {
					if (!appSub.gui.isVisible())
						appSub.gui = new GUI_Subdito(bdSub);
					appSub.gui.start();
					System.out.println(bdRei.getMensagens().size());
					subAvailable.release();
					subAvailable.release();
					write("Ativei a GUI_SUBDITO \n");
				}
				else {
					appSub.gui.off();
					try {
						subAvailable.acquire();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					write("Desativei a GUI_SUBDITO \n");
				}

		        
			}
		});
      
      chckbxGravar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(chckbxGravar.isSelected()) {
					if (!appGravar.gui.isVisible())
						appGravar.gui = new GUI_Gravar(bdGravar);
					appGravar.gui.start();
					gravarAvailable.release();
					gravarAvailable.release();
					bdGravar.setGravarOff(true);
					write("Ativei a GUI_GRAVAR \n");
				}
				else {
					appGravar.gui.off();
					try {
						bdGravar.setGravarOff(false);
						gravarAvailable.acquire();
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					write("Desativei a GUI_GRAVAR \n");
				}

		        
			}
		});
      
      addWindowListener(new WindowAdapter(){
          public void windowClosing(WindowEvent e){
              if (appSub.getBD().isLigado())
              {
            	  appSub.gui.write(" Desconectando o robot ... \n");
            	  write(" Desconectando o robot ... \n");
            	  appSub.getBD().getRobot().getRobot().CloseEV3();
            	  appSub.getBD().setLigado(false);
            	  System.exit(0);
              }
              else {
                  System.out.println("Closing program");
                  System.exit(0);
              }
          }
      });
      
      setVisible(true);
  }
  
  
}