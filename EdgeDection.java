public class EdgeDection {
    private int[][] sobelx = {{-1,0,1},{-2,0,2},{-1,0,1}};
    private int[][] sobely = {{-1,-2,-1},{0,0,0},{1,2,1}};
    private int[][] result,pt;
    private int rate,r,G,Gx,Gy;
    public EdgeDection(int[][] img,int rate){
        BlackAndWhite blk = new BlackAndWhite(img);
        pt = blk.baw();
        result = new int[pt.length][pt[0].length];

        this.rate = rate;

    }
    public int[][] edge(){
        for(int i = 0;i<result.length;i++){
            for(int j = 0;j< result[0].length;j++){
                int temp = 0;
                Gx = 0;
                Gy = 0;
                for(int n = -1;n<2;n++) {
                    for (int m = -1; m < 2; m++) {
                        if (i + n < 0 && j + m < 0) {
                            Gx +=(pt[i + 1][j + 1]) & 0x00ff* sobelx[n + 1][m + 1];
                            Gy +=(pt[i + 1][j + 1]) & 0x00ff* sobely[n + 1][m + 1];
                        } else if (i + n < 0 && j + m >= pt[0].length) {
                            Gx += (pt[i + 1][j - 1]& 0x00ff) * sobelx[n + 1][m + 1];
                            Gy += (pt[i + 1][j - 1]& 0x00ff) * sobely[n + 1][m + 1];
                        } else if (i + n < 0) {
                            Gx += (pt[i + 1][j + m]& 0x00ff) * sobelx[n + 1][m + 1];
                            Gy += (pt[i + 1][j + m]& 0x00ff) * sobely[n + 1][m + 1];
                        } else if (j + m < 0 && i + n >= pt.length) {
                            if (i + n >= pt.length) {
                                Gx += (pt[i - 1][j + 1] & 0x00ff) * sobelx[n + 1][m + 1];
                                Gy += (pt[i - 1][j + 1] & 0x00ff) * sobely[n + 1][m + 1];
                            } else {
                                Gx += (pt[i + n][j + 1] & 0x00ff) * sobelx[n + 1][m + 1];
                                Gy += (pt[i + n][j + 1] & 0x00ff) * sobely[n + 1][m + 1];
                            }
                        } else if (j + m < 0) {
                            Gx += (pt[i + n][j + 1]& 0x00ff) * sobelx[n + 1][m + 1];
                            Gy += (pt[i + n][j + 1]& 0x00ff) * sobely[n + 1][m + 1];
                        } else if (j + m >= pt[0].length && i + n >= pt.length) {
                            Gx +=(pt[i - 1][j - 1]& 0x00ff) * sobelx[n + 1][m + 1];
                            Gy +=(pt[i - 1][j - 1]& 0x00ff) * sobely[n + 1][m + 1];
                        } else if (j + m >= pt[0].length) {
                            Gx += (pt[i + n][j - 1]& 0x00ff) * sobelx[n + 1][m + 1];
                            Gy += (pt[i + n][j - 1]& 0x00ff) * sobely[n + 1][m + 1];
                        } else if (i + n >= pt.length) {
                            Gx += (pt[i - 1][j + m]& 0x00ff) * sobelx[n + 1][m + 1];
                            Gy += (pt[i - 1][j + m]& 0x00ff) * sobely[n + 1][m + 1];
                        } else {
                            Gx += (pt[i + n][j + m] & 0xff) * sobelx[n + 1][m + 1];
                            Gy += (pt[i + n][j + m] & 0xff) * sobely[n + 1][m + 1];
                        }
                    }
                }
                Gx = Math.abs(Gx);
                Gy = Math.abs(Gy);
                G = Gx+Gy;
                if(G>=rate){
                    temp = 0xffffffff;
                }
                else{
                    temp = 0xff000000;
                }
                result[i][j] = temp;
            }
        }
        return result;
    }


}
