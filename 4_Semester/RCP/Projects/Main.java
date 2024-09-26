// package Redes.src;

import java.io.*;
import java.net.Socket;


public class Main {

    public static String IP = "127.0.0.1";
    public static int PORT = 80;


    public static void main(String[] args) throws IOException {
        BufferedReader inSock = socketInit(IP,PORT,"/");
        String line = inSock.readLine();
        // System.out.println("line-->"+line);
        httpRead(line,inSock);
    }

    /**
     * Criámos a inputSocket utilizando um BufferedReader para fazer print pelo servidor
     * e a outputSocket para enviar para o servidor,
     * acabamos por enviar o pedido HTTP com este output utilizando o método GET.
     * @param host String com o endereço IP
     * @param Port Int com o num da porta
     * @param URI "/"
     * @return
     */
    public static BufferedReader socketInit(String host,int Port, String URI) throws IOException {

        Socket socket = new Socket(host,Port);
        InputStreamReader input = new InputStreamReader(socket.getInputStream());
        BufferedReader inputSocket = new BufferedReader(input);
        PrintStream outputSocket = new PrintStream(socket.getOutputStream());
        outputSocket.println("GET " + URI + " HTTP/1.0\r\n");
        // socket.close(); Não se pode fechar o socket para funcionar
        return inputSocket;
    }

    /**
     * lê a mensagem de resposta enviada pelo servidor e faz print na consola com base
     * na resposta dada. No fim redirecionamos para a função output() com a primeira linha da
     * resposta, a socket e a informação sobre a resposta do servidor.
     * @param code primeira linha da resposta
     * @param socket BufferedReader com os pacotes de entrada
     */
    public static void httpRead(String code,BufferedReader socket) throws IOException {
        System.out.println();
        //O código esta após o primeiro espaço exemplo: HTTP/1.1 302 Found
        String info = code.split(" ")[1];

        switch (info) {
            case "200" -> {
                System.out.println("200 OK");
            }
            case "301" -> {
                System.out.println("301 MOVED");
            }
            case "302" -> {
                System.out.println("302 FOUND");
            }
            case "400" -> {
                System.out.println("400 BAD REQUEST");
            }
            case "401" -> {
                System.out.println("401 UNAUTHORIZED");
            }
            case "403" -> {
                System.out.println("403 FORBIDDEN");
            }
            case "404" -> {
                System.out.println("404 ERROR");
            }
            default -> System.out.println("UNRECOGNIZED CODE");
        }
        output(code,socket,info);
    }

    /**
     *se a informação enviada for “301” ou “302”, redirecionamos para a
     * função getURL() para podermos criar uma nova socket com a nova informação e chamamos outra vez httpRead.
     * Se não fazemos print da mensagem de resposta
     * inteira, o que vai também mostrar o código HTML da página
     * @param code primeira linha da resposta
     * @param socket BufferedReader com os pacotes de entrada
     * @param info contém a informação do pedido http
     */
    public static void output(String code,BufferedReader socket,String info) throws IOException {
        String msg = code;
        System.out.println();
        //Se a resposta for 301 ou 302
        if(info.equals("301") || info.equals("302")){
            System.out.println("\nRedirecting...\n");
            //Encontramos o Location Header
            String URL = getURL(code,socket);
            //Criamos a nova Socket
            BufferedReader inSock=socketInit(IP,PORT,URL);
            String line = inSock.readLine();
            //Ler a resposta
            httpRead(line,inSock);
        //Se não imprimimos a mensagem da resposta inteira
        }else {
            System.out.println();
            System.out.println("Reply message: ");
            System.out.println();
            while (msg!=null){
                msg=socket.readLine();
                System.out.println(msg);
            }
        }
    }


    /**
     * Encontra a Location Header, ou seja, o endereço de reencaminhamento.
     * Escreve a mensagem de resposta na consola e retorna o novo URL que
     * vai ser usado para criar a nova socket
     * @param code primeira linha da resposta
     * @param socket BufferedReader com os pacotes de entrada
     * @return String com o novo URL
     */
    private static String getURL(String code, BufferedReader socket) throws IOException {

        String msg = code;
        // System.out.println("Code-->"+code);
        String URL = "";

        //Percorremos os pacotes de entrada
        while (msg!=null){
            System.out.println(msg);
            //Se encontrarmos o campo "Location:" guardamos esse endereço
            if(msg.contains("Location:")){
                URL = msg.substring(10);
                // System.out.println("URL--> "+URL);
                break;
            }
            msg = socket.readLine();
        }
        return URL;
    }


}