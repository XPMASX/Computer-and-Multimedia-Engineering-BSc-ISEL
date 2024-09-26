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
import javax.swing.WindowConstants;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

public class GUI_Subdito extends GUI_BaseRS
{

	private JPanel contentPane;
	private JTextField txtNome;
	

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
					init_Subdito(bd);
					
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
	public void init_Subdito(BD_Subdito bd) 
	{
		setTitle("Trabalho 2 - Subdito");
		
		setDefaultCloseOperation(WindowConstants. DO_NOTHING_ON_CLOSE);
	    setBounds(50, 400, 760, 600);
		
		
		JLabel lblNomeDoRobot = new JLabel("Nome do robot");
		lblNomeDoRobot.setHorizontalAlignment(SwingConstants.LEFT);
		lblNomeDoRobot.setFont(new Font("Arial", Font.BOLD, 12));
		lblNomeDoRobot.setBounds(30, 61, 103, 25);
		getContentPane().add(lblNomeDoRobot);
		
		JRadioButton rdbtnAbrirFecharBlt = new JRadioButton("Abrir / Fechar Bluetooth");
		rdbtnAbrirFecharBlt.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				if (bd.isLigado()) 
		        {
					System.out.println("Desligando...");
		            bd.getRobot().getRobot().CloseEV3();
		            bd.setLigado(false);
		            txtLog.append(" O robot foi desligado com sucesso \n");
		            
		        } else 
		        {
		        	System.out.println("Ligando...");
		            bd.setLigado(bd.getRobot().getRobot().OpenEV3(bd.getNome()));
		            System.out.println(bd.isLigado());
		            if (!bd.isLigado()) {
		            	rdbtnAbrirFecharBlt.setSelected(false);
		            }
		            else
		            	txtLog.append(" O robot foi ligado com sucesso \n");
		            
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
		
		
		btnFrt.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.getRobot().reta(new MensagemReta(0,1,bd.getDist()));
				bd.getRobot().parar(new MensagemParar(0,4,false));
		        txtLog.append(" O robot avançou " + bd.getDist() + "\n");
			}
		});
		
		btnEsq.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.getRobot().curvarEsquerda(new MensagemCurvar(0,3,bd.getRaio(),bd.getAng()));
				bd.getRobot().parar(new MensagemParar(0,4,false));
		        txtLog.append(" O robot virou esquerda com raio = " + bd.getRaio() +" e angulo = " + bd.getAng() + "\n");
			}
		});
		
		btnDir.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.getRobot().curvarDireita(new MensagemCurvar(0,2,bd.getRaio(),bd.getAng()));
				bd.getRobot().parar(new MensagemParar(0,4,false));
		        txtLog.append(" O robot virou direita com raio = " + bd.getRaio() +" e angulo = " + bd.getAng() + "\n");
			}
		});
		
		btnTras.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.getRobot().reta(new MensagemReta(0,1,-bd.getDist()));
				bd.getRobot().parar(new MensagemParar(0,4,false));
		        txtLog.append(" O robot recuou " + bd.getDist() + "\n");
			}
		});
		
		btnParar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.getRobot().parar(new MensagemParar(0,4,false));
				//System.out.print(bd.getCanal().GetandSetReadLeitor());
				txtLog.append(" O robot parou \n");
				//txtLog.append("no limpa " + bd.getCanal().GetandSetRead().toString() +"\n");
				
			}
		});
		
		
		rdbtnAbrirFecharBlt.setFont(new Font("Arial", Font.BOLD, 12));
		rdbtnAbrirFecharBlt.setBounds(455, 61, 188, 25);
		getContentPane().add(rdbtnAbrirFecharBlt);
		
		setVisible(true);
	}

}
