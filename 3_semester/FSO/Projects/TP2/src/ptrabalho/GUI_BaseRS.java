package ptrabalho;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

public class GUI_BaseRS extends GUI_Base{
	
	protected JPanel contentPane;
	protected JTextField txtRaio;
	protected JTextField txtAng;
	protected JTextField txtDist;
	protected JTextField txtFile;
	protected JButton btnFrt;
	protected JButton btnEsq;
	protected JButton btnDir;
	protected JButton btnParar;
	protected JButton btnTras;
	
	public GUI_BaseRS(BD_Base bd) {
		
		super(bd);
		Border borda_cont_robot = BorderFactory.createLineBorder(new Color(0,0,0),1);
		TitledBorder borda1 = BorderFactory.createTitledBorder(borda_cont_robot,"Controle do Robot");
		
		JLabel lblRaio = new JLabel("Raio");
		lblRaio.setBounds(30, 179, 103, 25);
		getContentPane().add(lblRaio);
		lblRaio.setHorizontalAlignment(SwingConstants.LEFT);
		lblRaio.setFont(new Font("Arial", Font.BOLD, 12));
		
		txtRaio = new JTextField();
		txtRaio.setBounds(143, 179, 100, 23);
		getContentPane().add(txtRaio);
		txtRaio.setText("20");
		
		txtRaio.setFont(new Font("Arial", Font.BOLD, 12));
		txtRaio.setColumns(10);
		
		JLabel lblngulo = new JLabel("Ângulo");
		lblngulo.setBounds(30, 209, 103, 25);
		getContentPane().add(lblngulo);
		lblngulo.setHorizontalAlignment(SwingConstants.LEFT);
		lblngulo.setFont(new Font("Arial", Font.BOLD, 12));
		
		JLabel lblDistncia = new JLabel("Distância");
		lblDistncia.setBounds(30, 239, 103, 25);
		getContentPane().add(lblDistncia);
		lblDistncia.setHorizontalAlignment(SwingConstants.LEFT);
		lblDistncia.setFont(new Font("Arial", Font.BOLD, 12));
		
		txtAng = new JTextField();
		txtAng.setBounds(143, 209, 100, 23);
		getContentPane().add(txtAng);
		txtAng.setText("90");
		txtAng.setFont(new Font("Arial", Font.BOLD, 12));
		txtAng.setColumns(10);
		
		txtDist = new JTextField();
		txtDist.setBounds(143, 239, 100, 23);
		getContentPane().add(txtDist);
		txtDist.setText("30");
		txtDist.setFont(new Font("Arial", Font.BOLD, 12));
		txtDist.setColumns(10);
		
		btnParar = new JButton("Parar");
		btnParar.setBounds(470, 206, 100, 25);
		getContentPane().add(btnParar);
		btnParar.setEnabled(false);
		btnParar.setFont(new Font("Arial", Font.BOLD, 12));
		
		btnFrt = new JButton("Frente");
		btnFrt.setBounds(470, 178, 100, 25);
		getContentPane().add(btnFrt);
		btnFrt.setEnabled(false);
		btnFrt.setFont(new Font("Arial", Font.BOLD, 12));
		
		btnDir = new JButton("Direita");
		btnDir.setBounds(575, 207, 100, 25);
		getContentPane().add(btnDir);
		btnDir.setEnabled(false);
		btnDir.setFont(new Font("Arial", Font.BOLD, 12));
		
		btnEsq = new JButton("Esquerda");
		btnEsq.setBounds(365, 206, 100, 25);
		getContentPane().add(btnEsq);
		btnEsq.setEnabled(false);
		btnEsq.setFont(new Font("Arial", Font.BOLD, 12));
		
		btnTras = new JButton("Tras");
		btnTras.setBounds(470, 234, 100, 25);
		getContentPane().add(btnTras);
		btnTras.setEnabled(false);
		btnTras.setFont(new Font("Arial", Font.BOLD, 12));
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 160, 719, 113);
		getContentPane().add(panel_1);
		panel_1.setFont(new Font("Arial", Font.BOLD, 12));
		panel_1.setName("Controle do Robot");
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBorder(borda1);
		
		txtDist.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.setDist(Integer.parseInt(txtDist.getText()));
				write(" A distancia é " + bd.getDist() + "\n");
				
			}
		});
		
		txtAng.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.setAng(Integer.parseInt(txtAng.getText()));
				write(" O angulo é " + bd.getAng() + "\n");
			}
		});
		
		txtRaio.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.setRaio(Integer.parseInt(txtRaio.getText()));
				write(" A raio é " + bd.getRaio() + "\n");
			}
		});
		
		
	}
	
	protected void start()
	{
		btnFrt.setEnabled(true);
		btnEsq.setEnabled(true);
		btnParar.setEnabled(true);
		btnDir.setEnabled(true);
		btnTras.setEnabled(true);
	}
	
	protected void off()
	{
		btnFrt.setEnabled(false);
		btnEsq.setEnabled(false);
		btnParar.setEnabled(false);
		btnDir.setEnabled(false);
		btnTras.setEnabled(false);
	}

}
