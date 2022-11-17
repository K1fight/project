import java.util.Arrays;

public class GaussianBlur {
    private final double PI;
    private double sigma;
    private double[][] weight;
    private int[][] tp;
    private int[][] red;
    private int[][] blue;
    private int[][] green;
    private int[][] alpha;
    private int[][] result;
    private int x,y,p,a,r,g,b,radius;
    private double z,sum;
    public GaussianBlur(int[][] c,double sigma,int radius){
        PI = Math.PI;
        this.sigma = sigma;
        this.radius = radius;
        sum = 0;
        weight = new double[2*radius+1][2*radius+1];
        for(int i = -radius;i<radius+1;i++){
            for(int j = -radius;j<radius+1;j++){
                z = Math.exp((0-(i*i+j*j))/(2*sigma*sigma));
                weight[i+radius][j+radius] = z/(2*PI*sigma*sigma);
            }
        }
        for(int i = -radius;i<radius+1;i++){
            for(int j = -radius;j<radius+1;j++){
                sum += weight[i+radius][j+radius];
            }
        }
        for(int i = 0;i<2*radius+1;i++){
            for(int j = 0;j<2*radius+1;j++){
                weight[i][j] /= sum;
            }
        }
        System.out.println(Arrays.toString(weight[0]));
        System.out.println(Arrays.toString(weight[1]));
        System.out.println(Arrays.toString(weight[2]));




        this.tp = c;
        this.result = new int[c.length][c[0].length];
        alpha = new int[c.length][c[0].length];
        red = new int[c.length][c[0].length];
        green = new int[c.length][c[0].length];
        blue = new int[c.length][c[0].length];
        for(int i = 0;i<c.length;i++){
            for(int j = 0;j<c[0].length;j++){
                p = c[i][j];
                a = (p>>24)&0xff;
                r = (p>>16)&0xff;
                g = (p>>8)&0xff;
                b = p&0xff;
                alpha[i][j] = a;
                red[i][j] = r;
                green[i][j] = g;
                blue[i][j] = b;
            }
        }
    }

    public int[][] GaussianB() {
        for (int i = 0; i < tp.length; i++) {
            for (int j = 0; j < tp[0].length; j++) {
                int temp = 0;
                int tempr = 0;
                int tempg = 0;
                int tempb = 0;
                for (int m = -radius; m < radius+1; m++) {
                    for (int n = -radius; n < radius+1; n++) {
                        if(i+n<0&&j+m<0){
                            tempr += tp[i+1][j+1] * weight[n+radius][m+radius];
                            tempg += tp[i+1][j+1] * weight[n+radius][m+radius];
                            tempb += tp[i+1][j+1] * weight[n+radius][m+radius];
                        } else if (i+n<0) {
                            if(j+m>=tp[0].length){
                                tempr += red[i+1][j-1] * weight[n+radius][m+radius];
                                tempg += green[i+1][j-1] * weight[n+radius][m+radius];
                                tempb += blue[i+1][j-1] * weight[n+radius][m+radius];
                            }
                            else{
                                tempr += red[i+1][j+m] * weight[n+radius][m+radius];
                                tempg += green[i+1][j+m] * weight[n+radius][m+radius];
                                tempb += blue[i+1][j+m] * weight[n+radius][m+radius];
                            }
                        }
                        else if (j+m<0) {
                            if(i+n>=tp.length){
                                tempr += red[i-1][j+1] * weight[n+radius][m+radius];
                                tempg += green[i-1][j+1] * weight[n+radius][m+radius];
                                tempb += blue[i-1][j+1] * weight[n+radius][m+radius];
                            }
                            else{
                                tempr += red[i+n][j+1] * weight[n+radius][m+radius];
                                tempg += green[i+n][j+1] * weight[n+radius][m+radius];
                                tempb += blue[i+n][j+1] * weight[n+radius][m+radius];
                            }

                        }
                        else if(i+n>=tp.length&&j+m>=tp[0].length){
                            tempr += red[i-1][j-1] * weight[n+radius][m+radius];
                            tempg += green[i-1][j-1] * weight[n+radius][m+radius];
                            tempb += blue[i-1][j-1] * weight[n+radius][m+radius];
                        }
                        else if(j+m>=tp[0].length){
                            tempr += red[i+n][j-1] * weight[n+radius][m+radius];
                            tempg += green[i+n][j-1] * weight[n+radius][m+radius];
                            tempb += blue[i+n][j-1] * weight[n+radius][m+radius];
                        }
                        else if(i+n>=tp.length){
                            tempr += red[i-1][j+m] * weight[n+radius][m+radius];
                            tempg += green[i-1][j+m] * weight[n+radius][m+radius];
                            tempb += blue[i-1][j+m] * weight[n+radius][m+radius];
                        }
                        else{
                            tempr += red[i+n][j+m] * weight[n+radius][m+radius];
                            tempg += green[i+n][j+m] * weight[n+radius][m+radius];
                            tempb += blue[i+n][j+m] * weight[n+radius][m+radius];
                        }
                    }
                }
                temp = alpha[i][j]<<24|tempr<<16|tempg<<8|tempb;
                result[i][j] = temp;
            }
        }
        return result;
    }
}
