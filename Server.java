import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args){
        Object[] data;
        try {
            ServerSocket ss = new ServerSocket(8888);
            System.out.println("Server is listening port: 8888");
            Socket s = ss.accept();
            System.out.println("Have a connection in");
            InputStream input = s.getInputStream();
            ObjectInputStream obj = new ObjectInputStream(input);
            OutputStream out = s.getOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(out);
            while(true){
                data = (Object[]) (obj.readObject());
                String str = (String)(data[1]);
                if(str.equals("blk")){
                    output.writeObject(processBlackAndWhite((int[][])data[0]));
                }
                else if(str.equals("gb")){
                    output.writeObject(processGaussian((int[][])(data[0]),(double)data[2],(int)data[3]));
                }
                else if(str.equals("ed")){
                    output.writeObject(processEdgeDection((int[][])(data[0]),(int)data[2]));
                }
            }
        }catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }


    }
    private static int[][] processBlackAndWhite(int[][] imgData){

        BlackAndWhite blk = new BlackAndWhite(imgData);
        return  blk.baw();
    }
    private static int[][] processGaussian(int[][] imgData,double sigma,int radius){
        GaussianBlur gb = new GaussianBlur(imgData,sigma,radius);
        return gb.GaussianB();
    }
    private  static int[][] processEdgeDection(int[][] imgData,int rate){
        EdgeDection ed = new EdgeDection(imgData,rate);
        return ed.edge();
    }

}

