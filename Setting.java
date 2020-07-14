public class Setting {
    private int ROW;
    private int COL;
    private int MineCount;
    private boolean easyStart;
    public Setting(int ROW,int COL,int MineCount,boolean easyStart){
        this.ROW=ROW;
        this.COL=COL;
        this.MineCount=MineCount;
    }

    public Setting(String difficulty){
        if(difficulty.equals("easy")){
            this.ROW=9;
            this.COL=9;
            this.MineCount=10;
        }else if(difficulty.equals("medium")){
            this.ROW=16;
            this.COL=16;
            this.MineCount=40;
        }else{
            this.ROW=16;
            this.COL=30;
            this.MineCount=99;
        }
    }

    public int getROW() {
        return ROW;
    }

    public int getCOL() {
        return COL;
    }

    public int getMineCount() {
        return MineCount;
    }
}
