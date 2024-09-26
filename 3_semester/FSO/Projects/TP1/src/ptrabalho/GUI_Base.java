package ptrabalho;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import java.awt.CardLayout;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JTextPane;
import java.awt.Panel;
import java.awt.Color;
import java.awt.Canvas;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JCheckBox;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.SpinnerNumberModel;

public class GUI_Base extends JFrame 
{

	private JPanel contentPane;
	protected JTextField txtRaio;
	protected JTextField txtAng;
	protected JTextField txtDist;
	protected JTextField txtFile;
	protected JButton btnFrt;
	protected JButton btnEsq;
	protected JButton btnDir;
	protected JButton btnParar;
	protected JButton btnTras;
	protected JTextArea txtLog;
	protected JButton btnFile;
	protected JButton btnLimpaLog;
	protected JCheckBox rdbtnAtivarDesativarComp;
	protected JRadioButton rdbtnAbrirCanal;
	protected JSpinner spinNMensagem;
	

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{
					GUI_Base frame = new GUI_Base();
					frame.setVisible(true);
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 */
	public GUI_Base(BD_Base bd) 
	{
		setTitle("Trabalho 1 - GUI Base");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 754, 691);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(100, 100,100, 100));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		txtFile = new JTextField();
		txtFile.setFont(new Font("Arial", Font.BOLD, 12));
		txtFile.setBounds(143, 156, 494, 25);
		contentPane.add(txtFile);
		txtFile.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Ficheiro do Canal");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel.setBounds(30, 156, 103, 25);
		contentPane.add(lblNewLabel);
		
		btnFile = new JButton("...");
		btnFile.setFont(new Font("Arial", Font.BOLD, 12));
		btnFile.setBounds(647, 157, 61, 25);
		contentPane.add(btnFile);
		
		JLabel lblNMsg = new JLabel("Nº msg");
		lblNMsg.setHorizontalAlignment(SwingConstants.LEFT);
		lblNMsg.setFont(new Font("Arial", Font.BOLD, 12));
		lblNMsg.setBounds(30, 200, 103, 25);
		contentPane.add(lblNMsg);
		
		spinNMensagem = new JSpinner();
		spinNMensagem.setModel(new SpinnerNumberModel(8, 8, 12, 1));
		spinNMensagem.setFont(new Font("Arial", Font.BOLD, 12));
		spinNMensagem.setBounds(143, 200, 50, 25);
		contentPane.add(spinNMensagem);
		
		rdbtnAbrirCanal = new JRadioButton("Abrir/Fechar Canal");
		rdbtnAbrirCanal.setFont(new Font("Arial", Font.BOLD, 12));
		rdbtnAbrirCanal.setBounds(459, 200, 103, 25);
		contentPane.add(rdbtnAbrirCanal);
		
		Border simpb = BorderFactory.createLineBorder(new Color(0,0,0),1);
		TitledBorder border_simp = BorderFactory.createTitledBorder(simpb,"Canal de Comunicação");
		JPanel panel = new JPanel();
		panel.setName("Canal de Comunicação");
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 128, 719, 113);
		panel.setBorder(border_simp);
		contentPane.add(panel);
		
		JLabel lblRaio = new JLabel("Raio");
		lblRaio.setHorizontalAlignment(SwingConstants.LEFT);
		lblRaio.setFont(new Font("Arial", Font.BOLD, 12));
		lblRaio.setBounds(30, 270, 103, 25);
		contentPane.add(lblRaio);
		
		txtRaio = new JTextField();
		txtRaio.setText("20");
		
		txtRaio.setFont(new Font("Arial", Font.BOLD, 12));
		txtRaio.setColumns(10);
		txtRaio.setBounds(143, 270, 100, 23);
		contentPane.add(txtRaio);
		
		JLabel lblngulo = new JLabel("Ângulo");
		lblngulo.setHorizontalAlignment(SwingConstants.LEFT);
		lblngulo.setFont(new Font("Arial", Font.BOLD, 12));
		lblngulo.setBounds(30, 300, 103, 25);
		contentPane.add(lblngulo);
		
		JLabel lblDistncia = new JLabel("Distância");
		lblDistncia.setHorizontalAlignment(SwingConstants.LEFT);
		lblDistncia.setFont(new Font("Arial", Font.BOLD, 12));
		lblDistncia.setBounds(30, 330, 103, 25);
		contentPane.add(lblDistncia);
		
		txtAng = new JTextField();
		txtAng.setText("90");
		txtAng.setFont(new Font("Arial", Font.BOLD, 12));
		txtAng.setColumns(10);
		txtAng.setBounds(143, 300, 100, 23);
		contentPane.add(txtAng);
		
		txtDist = new JTextField();
		txtDist.setText("30");
		txtDist.setFont(new Font("Arial", Font.BOLD, 12));
		txtDist.setColumns(10);
		txtDist.setBounds(143, 330, 100, 23);
		contentPane.add(txtDist);
		
		btnParar = new JButton("Parar");
		btnParar.setEnabled(false);
		btnParar.setFont(new Font("Arial", Font.BOLD, 12));
		btnParar.setBounds(470, 297, 100, 25);
		contentPane.add(btnParar);
		
		btnFrt = new JButton("Frente");
		btnFrt.setEnabled(false);
		btnFrt.setFont(new Font("Arial", Font.BOLD, 12));
		btnFrt.setBounds(470, 269, 100, 25);
		contentPane.add(btnFrt);
		
		btnDir = new JButton("Direita");
		btnDir.setEnabled(false);
		btnDir.setFont(new Font("Arial", Font.BOLD, 12));
		btnDir.setBounds(575, 298, 100, 25);
		contentPane.add(btnDir);
		
		btnEsq = new JButton("Esquerda");
		btnEsq.setEnabled(false);
		btnEsq.setFont(new Font("Arial", Font.BOLD, 12));
		btnEsq.setBounds(365, 297, 100, 25);
		contentPane.add(btnEsq);
		
		btnTras = new JButton("Tras");
		btnTras.setEnabled(false);
		btnTras.setFont(new Font("Arial", Font.BOLD, 12));
		btnTras.setBounds(470, 325, 100, 25);
		contentPane.add(btnTras);
		
		Border borda_cont_robot = BorderFactory.createLineBorder(new Color(0,0,0),1);
		TitledBorder borda1 = BorderFactory.createTitledBorder(borda_cont_robot,"Controle do Robot");
		JPanel panel_1 = new JPanel();
		panel_1.setFont(new Font("Arial", Font.BOLD, 12));
		panel_1.setName("Controle do Robot");
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(10, 251, 719, 113);
		panel_1.setBorder(borda1);
		contentPane.add(panel_1);
		
		rdbtnAtivarDesativarComp = new JCheckBox("Ativar / Desativar Comportamento");
		rdbtnAtivarDesativarComp.setFont(new Font("Arial", Font.BOLD, 12));
		rdbtnAtivarDesativarComp.setBounds(10, 365, 262, 45);
		contentPane.add(rdbtnAtivarDesativarComp);
		
		JLabel lblLog = new JLabel("Log");
		lblLog.setHorizontalAlignment(SwingConstants.LEFT);
		lblLog.setFont(new Font("Arial", Font.BOLD, 12));
		lblLog.setBounds(10, 429, 103, 25);
		contentPane.add(lblLog);
		
		btnLimpaLog = new JButton("Limpar Log");
		btnLimpaLog.setFont(new Font("Arial", Font.BOLD, 12));
		btnLimpaLog.setBounds(10, 630, 719, 25);
		contentPane.add(btnLimpaLog);
		
		
		txtLog = new JTextArea();
		txtLog.setBounds(45, 464, 659, 156);
		contentPane.add(txtLog);
		
		JScrollPane scrollPane = new JScrollPane(txtLog);
		scrollPane.setBounds(45, 464, 663, 156);
		contentPane.add(scrollPane);
		
		btnLimpaLog.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				txtLog.setText("");
			}
		});
		
		spinNMensagem.addChangeListener(new ChangeListener() {   
			@Override
			public void stateChanged(ChangeEvent e) {
				int nMensagens = (int) spinNMensagem.getValue();
				if (bd.getCanal()!= null) {
					
					bd.getCanal().nMensagens = nMensagens;
					bd.getCanal().BUFFER_MAX = 16 * nMensagens;
					
				}
				bd.setNMensagens(nMensagens);
				txtLog.append(" O num de mensagens é " + nMensagens + "\n");
			}
		});
		
		btnFile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				txtLog.append(" Escolher Ficheiro \n");
				JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir"));
				
				if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
					String file = fileChooser.getSelectedFile().getAbsolutePath();
					txtFile.setText(file);
					bd.setFile(file);
					txtLog.append(" O nome do ficheiro é " + bd.getFile() + "\n");
				}
			}
		});
		
		txtFile.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.setFile(txtFile.getText());
				txtLog.append(" O nome do ficheiro é " + bd.getFile() + "\n");
			}
		});
		
		
		rdbtnAtivarDesativarComp.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				if(!rdbtnAtivarDesativarComp.isSelected())
				{
					btnFrt.setEnabled(false);
					btnEsq.setEnabled(false);
					btnParar.setEnabled(false);
					btnDir.setEnabled(false);
					btnTras.setEnabled(false);
				}
				else {
					btnFrt.setEnabled(true);
					btnEsq.setEnabled(true);
					btnParar.setEnabled(true);
					btnDir.setEnabled(true);
					btnTras.setEnabled(true);
				}
			}
		});
		
		
		spinNMensagem.setValue(bd.getNMensagens());
		txtFile.setText(bd.getFile());
		
		txtDist.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.setDist(Integer.parseInt(txtDist.getText()));
				txtLog.append(" A distancia é " + bd.getDist() + "\n");
			}
		});
		
		txtAng.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.setAng(Integer.parseInt(txtAng.getText()));
				txtLog.append(" O angulo é " + bd.getAng() + "\n");
			}
		});
		
		txtRaio.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				bd.setRaio(Integer.parseInt(txtRaio.getText()));
				txtLog.append(" A raio é " + bd.getRaio() + "\n");
			}
		});
		
	}
}