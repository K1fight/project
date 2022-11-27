import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class IO implements ActionListener,Serializable{
    private static Socket s;  //create Socket
    private static OutputStream os;  // create Output stream
    private static InputStream is;
    private static ObjectInputStream obji;
    private static ObjectOutputStream objo;
    private JFrame frame;  //create a Jframe
    private JLabel label,label1,resultLabel,labelR,labelS;  //create some labels
    private JPanel panel,panel1,panel2;  //create some panels
    private Icon icon;  // create Icons
    private JButton button,button1,button2; // create some buttons
    private JTextField textR,textS,textPath;
    private BufferedImage bImg,bResultImg;
    private Image img, resultImg;
    private File file;
    public IO(String path) throws IOException, ClassNotFoundException {
        this.display(path);
        client();
    }

    public void display(String path) throws IOException {
        frame = new JFrame();
        label = new JLabel();
        label1 = new JLabel();
        labelR = new JLabel();
        labelS = new JLabel();
        panel = new JPanel();
        panel1 = new JPanel();
        panel2 = new JPanel();
        button = new JButton();
        button1 = new JButton();
        button2 = new JButton();
        textR = new JTextField();
        textS = new JTextField();
        textPath = new JTextField();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(1440,800);

        panel.setBounds(0,0,720,600);
        panel1.setBounds(720,0,720,600);
        panel2.setBounds(0,600,1440,200);
        panel2.setLayout(new FlowLayout());

        button.setText("Blur");
        button.setFocusable(true);
        button.addActionListener(this::actionPerformed);
        button.setVerticalAlignment(JButton.CENTER);
        button1.setText("BlackAndWhite");
        button1.setFocusable(true);
        button1.addActionListener(this::actionPerformed);
        button1.setVerticalAlignment(JButton.CENTER);
        button2.setText("edgeDection");
        button2.setFocusable(true);
        button2.addActionListener(this::actionPerformed);
        button2.setVerticalAlignment(JButton.CENTER);

        textR.setPreferredSize(new Dimension(60,20));
        textS.setPreferredSize(new Dimension(60,20));
        textPath.setPreferredSize(new Dimension(60,20));

        file = new File(path);
        bImg = ImageIO.read(file);
        img = bImg.getScaledInstance(720, 450,1);
        icon = new ImageIcon(img);


        label1.setIcon(icon);
        label1.setOpaque(true);
        label1.setHorizontalAlignment(JLabel.RIGHT);
        label.setIcon(icon);
        labelR.setText("Radius");
        labelR.setIconTextGap(20);
        labelR.setVerticalAlignment(JLabel.TOP);
        labelS.setText("Sigma");
        labelR.setIconTextGap(20);

        panel.add(label);
        panel1.add(label1);
        panel2.add(button);
        panel2.add(labelR);
        panel2.add(textR);
        panel2.add(labelS);
        panel2.add(textS);
        panel2.add(textPath);
        panel2.add(button1);
        panel2.add(button2);

        frame.add(panel);
        frame.add(panel1);
        frame.add(panel2);
        frame.setVisible(true);
    }
    private  int[][] getRGB(){
        int width = bImg.getWidth();
        int height = bImg.getHeight();
        int[][] result = new int[height][width];
        for(int i = 0;i<height;i++){
            for(int j = 0;j<width;j++){
                int c = bImg.getRGB(j,i);
                result[i][j] = c;
            }
        }
        System.out.printf("%h\n",result[0][0]);
        return result;
    }
    private void after(Image img){
        panel1.remove(label1);
        if(resultLabel!=null){
            panel1.remove(resultLabel);
        }
        resultImg = img.getScaledInstance(720, 450,1);
        resultLabel = new JLabel(new ImageIcon(resultImg));
        panel1.add(resultLabel);
        frame.setVisible(true);
    }
    private Image convert(int[][] p){
        int temp[] = new int[p.length*p[0].length];
        int count = 0;
        for(int i = 0;i<p.length;i++){
            for(int j = 0;j<p[0].length;j++){
                temp[count] = p[i][j];
                count++;
            }
        }
        System.out.printf("%h\n",temp[0]);
        System.out.printf("%h\n",temp[1]);

        BufferedImage image1 = new BufferedImage(p[0].length,p.length,BufferedImage.TYPE_INT_ARGB);
        image1.setRGB(0,0,p[0].length,p.length,temp,0,p[0].length);
        return image1;

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==button){
            double sigma = 0;
            int radius = 0;

            sigma = Double.parseDouble(textS.getText());
            radius = Integer.parseInt(textR.getText());
            Object[] gb = {getRGB(),"gb",sigma,radius};
            try {
                objo.writeObject(gb);
                Image temp = convert((int[][])(obji.readObject()));
                after(temp);
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource()==button1) {
            Object[] blk = {getRGB(),"blk"};
            try {
                objo.writeObject(blk);
                Image temp1 = convert((int[][])(obji.readObject()));
                after(temp1);
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
        else if(e.getSource()==button2){
            Object[] ed = {getRGB(),"ed",110};

            try{
                objo.writeObject(ed);
                Image temp2  =  convert((int[][])(obji.readObject()));
                after(temp2);
            }catch (IOException | ClassNotFoundException ex){
                throw new RuntimeException();
            }
        }
    }
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        IO io = new IO("./source/flower.png");
    }
    private void client() throws IOException{
        s = new Socket("127.0.0.1",8888);
        os = s.getOutputStream();
        objo = new ObjectOutputStream(os);
        is = s.getInputStream();
        obji = new ObjectInputStream(is);

    }
}
