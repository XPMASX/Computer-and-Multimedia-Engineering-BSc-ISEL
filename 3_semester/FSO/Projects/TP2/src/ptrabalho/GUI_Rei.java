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
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GUI_Rei extends GUI_BaseRS 
{
	private int id = 0;
	protected JButton btn8com;
	protected JButton btn16com;
	protected JButton btn1com;
	Mensagem msg = null;

	
	public GUI_Rei(BD_Rei bd) 
	{
		super(bd);
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					init_Rei(bd);
					
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
	public void init_Rei(BD_Rei bd) 
	{
		
		setTitle("Trabalho 1 - GUI Rei");
		setDefaultCloseOperation(WindowConstants. DO_NOTHING_ON_CLOSE);
		setBounds(1000, 0, 760, 600);
		
		Border cost = BorderFactory.createLineBorder(new Color(0,0,0),1);
		TitledBorder borda_rei = BorderFactory.createTitledBorder(cost,"Controle do Robot em Modo Automático");
		JPanel panel_1_1 = new JPanel();
		panel_1_1.setLayout(null);
		panel_1_1.setName("Controle do Robot em Modo Automático");
		panel_1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1_1.setBounds(10, 34, 719, 113);
		panel_1_1.setBorder(borda_rei);
		getContentPane().add(panel_1_1);
		
		btn8com = new JButton("8 Comandos Aleatórios");
		btn8com.setEnabled(false);
		btn8com.setFont(new Font("Arial", Font.BOLD, 12));
		btn8com.setBounds(10, 78, 300, 25);
		panel_1_1.add(btn8com);
		
		btn16com = new JButton("16 Comandos Aleatórios");
		btn16com.setEnabled(false);
		btn16com.setFont(new Font("Arial", Font.BOLD, 12));
		btn16com.setBounds(390, 78, 300, 25);
		panel_1_1.add(btn16com);
		
		btn1com = new JButton("1 Comando Aleatório");
		btn1com.setFont(new Font("Arial", Font.BOLD, 12));
		btn1com.setEnabled(false);
		btn1com.setBounds(10, 25, 680, 25);
		panel_1_1.add(btn1com);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(100, 100,100, 100));
		
		btn1com.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				msg = gerarRandomMensagem();
				bd.addMensagem(msg);
				write(" Criei a mensagem " + msg + "\n");
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
					write(" Criei a mensagem " + msg + "\n");
					
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
					write(" Criei a mensagem " + msg + "\n");
				}
				
			}
		});
		
		
		btnFrt.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Mensagem mensagem = new Mensagem(id,1,bd.getDist(),0);
				bd.addMensagem(mensagem);
				write(" Criei a mensagem " + mensagem + "\n");
			}
		});
		
		btnEsq.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Mensagem mensagem = new Mensagem(id,3,bd.getRaio(),bd.getAng());
				bd.addMensagem(mensagem);
				write(" Criei a mensagem " + mensagem + "\n");
			}
		});
		
		btnDir.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Mensagem mensagem = new Mensagem(id,2,bd.getRaio(),bd.getAng());
				bd.addMensagem(mensagem);
				write(" Criei a mensagem " + mensagem + "\n");
			}
		});
		
		btnTras.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Mensagem mensagem = new Mensagem(id,1,-bd.getDist(),0);
				bd.addMensagem(mensagem);
				write(" Criei a mensagem " + mensagem + "\n");
			}
		});
		
		btnParar.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Mensagem mensagem = new Mensagem(id,0,0,0);
				bd.addMensagem(mensagem);
				write(" Criei a mensagem " + mensagem + "\n");
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
			int sinal = rn.nextInt(2);
			if (sinal == 1)
				variavel*=-1;
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
	
	protected void start()
	{
		super.start();
		btn1com.setEnabled(true);
		btn16com.setEnabled(true);
		btn8com.setEnabled(true);
	}
	
	protected void off()
	{
		super.off();
		btn1com.setEnabled(false);
		btn16com.setEnabled(false);
		btn8com.setEnabled(false);
	}
}