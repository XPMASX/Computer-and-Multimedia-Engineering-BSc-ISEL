package ptrabalho;

import java.awt.EventQueue;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class GUI_Subdito extends GUI_Base 
{

	private JPanel contentPane;
	private JTextField txtNome;
	private App_Subdito app;
	private BD_Subdito bd;

	/**
	 * Launch the application.
	 */
	public GUI_Subdito(BD_Subdito bd) 
	{
		super(bd);
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					init_GUI_Subdito(bd);
					
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void init_GUI_Subdito(BD_Subdito bd) 
	{
		setTitle("Trabalho 1 - GUI Subdito");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 754, 695);
		
		
		JLabel lblNomeDoRobot = new JLabel("Nome do robot");
		lblNomeDoRobot.setHorizontalAlignment(SwingConstants.LEFT);
		lblNomeDoRobot.setFont(new Font("Arial", Font.BOLD, 12));
		lblNomeDoRobot.setBounds(30, 61, 103, 25);
		getContentPane().add(lblNomeDoRobot);
		
		
		btnFrt.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.getRobot().Reta(bd.getDist());
		        bd.getRobot().Parar(false);
		        txtLog.append(" O robot avançou " + bd.getDist() + "\n");
			}
		});
		
		btnEsq.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.getRobot().CurvarEsquerda(bd.getRaio(),bd.getAng());
		        bd.getRobot().Parar(false);
		        txtLog.append(" O robot virou esquerda com raio = " + bd.getRaio() +" e angulo = " + bd.getAng() + "\n");
			}
		});
		
		btnDir.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.getRobot().CurvarDireita(bd.getRaio(),bd.getAng());
		        bd.getRobot().Parar(false);
		        txtLog.append(" O robot virou direita com raio = " + bd.getRaio() +" e angulo = " + bd.getAng() + "\n");
			}
		});
		
		btnTras.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.getRobot().Reta(-(bd.getDist()));
		        bd.getRobot().Parar(false);
		        txtLog.append(" O robot recuou " + bd.getDist() + "\n");
			}
		});
		
		btnParar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.getRobot().Parar(true);
				System.out.print(bd.getCanal().GetandSetReadLeitor());
				txtLog.append(" O robot parou \n");
				//txtLog.append("no limpa " + bd.getCanal().GetandSetRead().toString() +"\n");
				
			}
		});
		
		
		JRadioButton rdbtnAbrirFecharBlt = new JRadioButton("Abrir / Fechar Bluetooth");
		rdbtnAbrirFecharBlt.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				if (bd.isLigado()) 
		        {
					System.out.println("Desligando...");
		            bd.getRobot().CloseEV3();
		            bd.setLigado(false);
		            
		        } else 
		        {
		        	System.out.println("Ligando...");
		            bd.setLigado(bd.getRobot().OpenEV3(bd.getNome()));
		            System.out.println(bd.isLigado());
		            if (!bd.isLigado())
		            	rdbtnAbrirFecharBlt.setSelected(false);
		            	
		            //bd.setLigado(true);
		        }
			}
		});
		
		rdbtnAbrirCanal.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if (rdbtnAbrirCanal.isSelected()) 
				{
					CanalComunicacaoConsistente cc = new CanalComunicacaoConsistente(bd.getNMensagens());
					cc.abrirCanalLeitor(bd.getFile());
					bd.setCanal(cc);
					txtLog.append(" Canal de Comunicação aberto!! \n");
					
					//while(rdbtnAbrirCanal.isSelected())
						
		        }
				else {
					bd.getCanal().fecharCanal();
					bd.setCanal(null);
					txtLog.append(" Canal de Comunicação fechado \n");
				}
			}
		});
		
		
		txtNome = new JTextField();
		txtNome.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.setNome(txtNome.getText());
				txtLog.append(" O nome do Robot é " + bd.getNome() + "\n");
			}
		});
		
		
		txtNome.setFont(new Font("Arial", Font.BOLD, 12));
		txtNome.setColumns(10);
		txtNome.setBounds(143, 61, 279, 25);
		getContentPane().add(txtNome);
		
		
		
		rdbtnAbrirFecharBlt.setFont(new Font("Arial", Font.BOLD, 12));
		rdbtnAbrirFecharBlt.setBounds(455, 61, 158, 25);
		getContentPane().add(rdbtnAbrirFecharBlt);
		
		
		Border bords_robot= BorderFactory.createLineBorder(new Color(0,0,0),1);
		TitledBorder borda_robot = BorderFactory.createTitledBorder(bords_robot, "Robot");
		JPanel panel = new JPanel();
		panel.setName("Canal de Comunicação");
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 34, 719, 66);
		panel.setBorder(borda_robot);
		getContentPane().add(panel);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(100, 100,100, 100));
		
		addWindowListener(new WindowAdapter(){
            public void windowClosing(WindowEvent e){
                if (bd.isLigado())
                {
                    txtLog.append(" Desconectando o robot ... \n");
                    System.out.println("desconect");
                    bd.getRobot().CloseEV3();
                    rdbtnAbrirFecharBlt.setSelected(false);
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
