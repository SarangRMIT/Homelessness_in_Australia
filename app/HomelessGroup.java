package app;

public class HomelessGroup {
    
    private int lga_code;
    private int year;
    private String status;
    private String sex;
    private String age_group;
    private int count;

    public HomelessGroup(int lga_code, int year, String status, String sex, String age_group, int count){
        this.lga_code = lga_code;
        this.year = year;
        this.status = status;
        this.sex = sex;
        this.age_group = age_group;
        this.count = count;
    }

    public int getLGACode(){
        return lga_code;
    }

    public int getYear(){
        return year;
    }

    public String getStatus(){
        return status;
    }

    public String getSex(){
        return sex;
    }

    public String getAgeGroup(){
        return age_group;
    }

    public int getCount(){
        return count;
    }

}
