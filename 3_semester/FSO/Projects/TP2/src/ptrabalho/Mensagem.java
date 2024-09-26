package ptrabalho;

public class Mensagem implements iMensagem{
    int tipo, id, arg1, arg2;

    public Mensagem(int id, int tipo, int arg1, int arg2) {
        this.tipo = tipo;
        this.id = id;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }
    
    public Mensagem() {
        
    }
    

    @Override
    public String toString() {
        return "Mensagem{" +
                " id= " + id +
                " tipo=" + tipo +
                " arg1=" + arg1 +
                " arg2=" + arg2 +
                '}';
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getArg1() {
        return arg1;
    }

    public void setArg1(int arg) {
        this.arg1 = arg;
    }
    
    public int getArg2() {
        return arg2;
    }

    public void setArg2(int arg) {
        this.arg2 = arg;
    }

    public boolean equals(Mensagem mensagem) {

        if(mensagem==null) return false;

        return (this.getId() == mensagem.getId());
    }
}