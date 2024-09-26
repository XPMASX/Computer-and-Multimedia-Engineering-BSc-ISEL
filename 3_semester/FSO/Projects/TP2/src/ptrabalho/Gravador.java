package ptrabalho;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Gravador {
		
	
	private boolean gravar;
	protected BD_Gravar bdGravar;
	private final String EOL = System.lineSeparator();
	
	public Gravador(BD_Gravar bd)
	{
		gravar = false;
		bdGravar = bd;
	}
	
	public void setGravar(boolean g)
	{
		gravar = g;
	}
	
	public boolean getGravar()
	{
		return bdGravar.getGravar();
	}
	
	void reta(Mensagem msg)
	{
		// Try-with-resources to automatically close resources (like FileWriter)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bdGravar.getNome(), true))) {
            // Write data to the file
            writer.write(
            		""+ msg.tipo + "," +
            		"" + msg.arg1 + "," +
            		"" + System.currentTimeMillis() + "" + EOL);

            System.out.println("Data has been successfully saved to the file.");
           
        } catch (IOException e) {
            // Handle exceptions
        	System.out.println("No file.");
            e.printStackTrace();
        }
	}
	
	void curvarDireita(Mensagem msg)
	{
		// Try-with-resources to automatically close resources (like FileWriter)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bdGravar.getNome(), true))) {
            // Write data to the file
            writer.write(
            		"" + msg.tipo + "," +
            		"" + msg.arg1 + "," +
            		"" + msg.arg2 + "," +
            		"" + System.currentTimeMillis() + "" + EOL);

            System.out.println("Data has been successfully saved to the file.");
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
	}
	
	void curvarEsquerda(Mensagem msg)
	{
		// Try-with-resources to automatically close resources (like FileWriter)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bdGravar.getNome(), true))) {
            // Write data to the file
            writer.write(
            		"" + msg.tipo + "," +
            		"" + msg.arg1 + "," +
            		"" + msg.arg2 + "," +
            		"" + System.currentTimeMillis() + "" + EOL);

            System.out.println("Data has been successfully saved to the file.");
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
	}
	
	void parar(Mensagem msg)
	{
		// Try-with-resources to automatically close resources (like FileWriter)
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bdGravar.getNome(), true))) {
            // Write data to the file
            writer.write("4" + EOL);
            System.out.println("Data has been successfully saved to the file.");
        } catch (IOException e) {
            // Handle exceptions
            e.printStackTrace();
        }
	}
}
