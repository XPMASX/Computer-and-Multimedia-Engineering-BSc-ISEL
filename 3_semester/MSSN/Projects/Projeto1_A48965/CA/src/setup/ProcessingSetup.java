package setup;
import CA.TestCA;
import processing.core.PApplet;

public class ProcessingSetup extends PApplet {

    public static IProcessingApp app;
    private int lastUpdate;

    @Override
    public void settings()
    {
        size(1000,800);
    }

    @Override
    public void setup()
    {
        app.setup(this);
        lastUpdate = millis();
    }

    @Override
    public void draw()
    {
        int now = millis();
        float dt = (now - lastUpdate)/1000f;
        lastUpdate = now;
        app.draw(this,dt);
    }

    @Override
    public void mousePressed()
    {
        app.mousePressed(this);
    }

    public void keyPressed() {app.keyPressed(this);}

    public static void main(String[]args)
    {
        System.out.println("Controlos: ");
        System.out.println("Pressione 1 para jogar Life ");
        System.out.println("Pressione 2 para jogar HighLife ");
        System.out.println("Pressione 3 para jogar Immigration ");
        System.out.println("Pressione 4 para jogar QuadLife ");
        System.out.println("Pressione 5 para jogar a versão a cores de Life");
        System.out.println("Pressione r para começar o jogo de novo");
        System.out.println("Pressione a tecla espaço para pausar o jogo");
        System.out.println("Pressione c matar todas as células");
        System.out.println("Se o jogo estiver parado clique com o rato na célula em que quiser trocar o estado");
        System.out.println("\nDivirta-se");
        app = new TestCA();
        PApplet.main(ProcessingSetup.class);
    }
}
