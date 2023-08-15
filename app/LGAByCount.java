package app;

public class LGAByCount {

    private String lga_name;
    private int count;

    public LGAByCount(String lga_name, int count){
        this.lga_name = lga_name;
        this.count = count;
    }

    public String getLGAName(){
        return lga_name;
    }

    public int getCount(){
        return count;
    }
    
}
