package ptrabalho;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;




public class GUI_Rei extends GUI_Base 
{
	private int id = 0;

	private JPanel contentPane;
	
	private int state = 0;
	private final int gerarRandom = 0;
	private final int escreverMensagem = 1;
	private final int dormir = 2;
	private final int esperarTempoExecucao = 3;
	
	Mensagem msg = null;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() {
				try 
				{
					GUI_Rei frame = new GUI_Rei(bd);
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}*/
	
	public GUI_Rei(BD_Rei bd) 
	{
		super(bd);
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					init_GUI_Rei(bd);
					
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
	public void init_GUI_Rei(BD_Rei bd) 
	{
		setTitle("Trabalho 1 - GUI Rei");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 754, 697);
		
		Border cost = BorderFactory.createLineBorder(new Color(0,0,0),1);
		TitledBorder borda_rei = BorderFactory.createTitledBorder(cost,"Controle do Robot em Modo Automático");
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setName("Controle do Robot em Modo Automático");
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_1.setBounds(10, 34, 719, 66);
		panel_1_1.setBorder(borda_rei);
		getContentPane().add(panel_1_1);
		
		JButton btn8com = new JButton("8 Comandos Aleatórios");
		btn8com.setEnabled(false);
		btn8com.setFont(new Font("Arial", Font.BOLD, 12));
		btn8com.setBounds(10, 26, 300, 25);
		panel_1_1.add(btn8com);
		
		JButton btn16com = new JButton("16 Comandos Aleatórios");
		btn16com.setEnabled(false);
		btn16com.setFont(new Font("Arial", Font.BOLD, 12));
		btn16com.setBounds(385, 26, 300, 25);
		panel_1_1.add(btn16com);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(100, 100,100, 100));
		
		
		
		rdbtnAtivarDesativarComp.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(!rdbtnAtivarDesativarComp.isSelected())
				{
					btn8com.setEnabled(false);
					btn16com.setEnabled(false);
				}
				else {
					btn8com.setEnabled(true);
					btn16com.setEnabled(true);
				}
			}
		});
		
		btnFrt.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Mensagem mensagem = new Mensagem(id,1,bd.getDist(),0);
				bd.addMensagem(mensagem);
			}
		});
		
		btnEsq.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Mensagem mensagem = new Mensagem(id,3,bd.getRaio(),bd.getAng());
				bd.addMensagem(mensagem);
			}
		});
		
		btnDir.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Mensagem mensagem = new Mensagem(id,2,bd.getRaio(),bd.getAng());
				bd.addMensagem(mensagem);
			}
		});
		
		btnTras.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Mensagem mensagem = new Mensagem(id,1,-bd.getDist(),0);
				bd.addMensagem(mensagem);
			}
		});
		
		btnParar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Mensagem mensagem = new Mensagem(id,0,0,0);
				bd.addMensagem(mensagem);
			}
		});
		
		btn8com.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				for (int i = 0; i<8; i++)
				{
					msg = gerarRandomMensagem();
					bd.addMensagem(msg);
					
				}
			}
		});
		
		btn16com.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				for (int i = 0; i<16; i++)
				{
					msg = gerarRandomMensagem();
					bd.addMensagem(msg);
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
					cc.abrirCanalEscritor(bd.getFile());
					bd.setCanal(cc);
					txtLog.append(" Canal de Comunicação " + bd.getFile() + " aberto!! \n");
		        }
				else {
					bd.getCanal().fecharCanal();
					bd.setCanal(null);
					txtLog.append(" Canal de Comunicação " + bd.getFile() + " fechado \n");
				}
			}
		});
		
		
		
		setVisible(true);
	}
	
	
	
	private Mensagem gerarRandomMensagem() {
		Mensagem m = new Mensagem();
		Random rn = new Random();
		int[] variaveis = new int[4]; // array de 4 variaveis
		int tipoMensagem = rn.nextInt(3); // random between 0-2
		if(id==8)
			id=0;
		variaveis = gerarVariaveis(tipoMensagem);
		m.setId(variaveis[0]);
		m.setTipo(variaveis[1]);
		m.setArg1(variaveis[2]);
		m.setArg2(variaveis[3]);
		return m;
	}

	/*
	 * Metodo auxiliar ao gerarRandomMensagem()
	 * 
	 * @param tMsg - tipo de mensagem
	 */
	private int[] gerarVariaveis(int tMsg) {
		Random rn = new Random();
		int[] variaveis = new int[4];
		int variavel;
		if (tMsg == 0) { // para tMsg 0 faz reta
			variaveis[0] = id;
			variaveis[1] = 1; // 1 equivale a reta na minha mensagem
			variavel = rn.nextInt(45) + 5; // random between 5-50 cm reta
			variaveis[2] = variavel;
			variaveis[3] = 0;
		} else if (tMsg == 1) { // para tMsg 1 faz curva direita
			variaveis[0] = id;
			variaveis[1] = 2; // 2 equivale a reta na minha mensagem
			variavel = rn.nextInt(30); // random between 0-30 raio
			variaveis[2] = variavel;
			variavel = rn.nextInt(70) + 20;// random between 20-90 angulo
			variaveis[3] = variavel;
		} else { // para tMsg 0 faz curva esquerda
			variaveis[0] = id;
			variaveis[1] = 3; // 3 equivale a reta na minha mensagem
			variavel = rn.nextInt(30); // random between 0-30 raio
			variaveis[2] = variavel;
			variavel = rn.nextInt(70) + 20;// random between 20-90 angulo
			variaveis[3] = variavel;
		}
		return variaveis;
	}
}
