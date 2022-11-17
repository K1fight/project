public class BlackAndWhite {
    private int[][] pt;
    private int[][] result;
    private int temp,r,g,b,parameter,avg;
    public BlackAndWhite(int[][] pt){
        this.pt = pt;
        this.result = new int[pt.length][pt[0].length];
    }
    public int[][] baw(){
        for(int i = 0;i< pt.length;i++){
            for(int j = 0;j<pt[0].length;j++){
                r = (pt[i][j]>>16)&0xff;
                g = (pt[i][j]>>8)&0xff;
                b = pt[i][j]&0xff;
                avg = (r+g+b)/3;
                r = avg;
                g = avg;
                b = avg;
                temp = 0xff<<24|r<<16|g<<8|b;
                result[i][j] = temp;

            }
        }
        return result;
    }

}
