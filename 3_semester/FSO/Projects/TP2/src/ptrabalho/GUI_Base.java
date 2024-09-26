package ptrabalho;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class GUI_Base extends JFrame {

	protected JPanel contentPane;
	protected JTextArea txtLog;
	protected JScrollPane scrollPane;

  public GUI_Base(BD_Base bd) {
      //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(0, 0, 800, 500);
      contentPane = new JPanel();
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        txtLog = new JTextArea();
        txtLog.setBounds(1, 1, 719, 203);
        contentPane.add(txtLog);

        scrollPane = new JScrollPane(txtLog);
        scrollPane.setBounds(10, 292, 719, 205);

        JButton btnLimpaLog = new JButton("Limpar Log");
        btnLimpaLog.setBounds(10, 521, 719, 21);
		btnLimpaLog.setFont(new Font("Arial", Font.BOLD, 12));
		contentPane.add(scrollPane);
		contentPane.add(btnLimpaLog);
        
        btnLimpaLog.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				txtLog.setText("");
			}
		});
    }
  
  public void write(String txt)
  {
	  EventQueue.invokeLater(new Runnable() 
		{

			@Override
			public void run() {
				txtLog.append(txt);
				
				// Set the caret position to the end of the text
	            txtLog.setCaretPosition(txtLog.getDocument().getLength());

	            // Set the caret position to the end of the text
	            txtLog.setCaretPosition(txtLog.getDocument().getLength());

	            // Adjust the scrollbar to show the latest content
	            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
	            verticalScrollBar.setValue(verticalScrollBar.getMaximum());

	            // Repaint the JTextArea and its parent
	            txtLog.revalidate();
	            txtLog.repaint();
				
			}});
  }
}
