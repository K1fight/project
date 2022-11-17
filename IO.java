import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class IO implements ActionListener{
    private JFrame frame;
    private JLabel label,label1,resultLabel,labelR,labelS;
    private JPanel panel,panel1,panel2;
    private Icon icon;
    private JButton button,button1;
    private JTextField textR,textS;
    private BufferedImage bImg,bResultImg;
    private Image img, resultImg;
    private File file;
    public IO(){
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
        textR = new JTextField();
        textS = new JTextField();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setSize(1440,800);

        panel.setBounds(0,0,720,600);
        panel1.setBounds(720,0,720,600);
        panel2.setBounds(0,600,1440,200);

        button.setText("Blur");
        button.setFocusable(true);
        button.addActionListener(this::actionPerformed);
        button.setVerticalAlignment(JButton.CENTER);
        button1.setText("BlackAndWhite");
        button1.setFocusable(true);
        button1.addActionListener(this::actionPerformed);
        button1.setVerticalAlignment(JButton.CENTER);


        textR.setPreferredSize(new Dimension(60,20));
        textS.setPreferredSize(new Dimension(60,20));

//        file = new File("./source/horse.jpg");
//        bImg = ImageIO.read(file);
//        img = bImg.getScaledInstance(1200,750,img.SCALE_DEFAULT);
//        icon = new ImageIcon(img);
//        label.setIcon(icon);
//        frame.add(label);

    }

    public void display(String path) throws IOException {
        file = new File(path);
        bImg = ImageIO.read(file);
        img = bImg.getScaledInstance(720, 450,1);
        icon = new ImageIcon(img);


        label1.setIcon(icon);
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
        panel2.add(button1);

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

            GaussianBlur g = new GaussianBlur(getRGB(),1.5,2);
            Image temp = convert(g.GaussianB());
            after(temp);
        } else if (e.getSource()==button1) {
            BlackAndWhite blk = new BlackAndWhite(getRGB());
            Image temp1 = convert(blk.baw());
            after(temp1);
        }
    }
}
