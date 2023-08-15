package app;

public class table1 {
    private String name;
    private int year;
    private String status;
    private String sex;
    private String age;
    private int count;

    public table1(String name,int year,String status,String sex,String age,int count){
        this.name = name;
        this.year = year;
        this.status = status;
        this.sex = sex;
        this.age = age;
        this.count = count; ;
    }
    
    public String getname(){
        return name;
    } 

    public int getyear(){
        return year;
    } 

    public String getstatus(){
        return status;
    } 

    public String getsex(){
        return sex;
    } 

    public String getage(){
        return age;
    } 
    
    public int getcount(){
        return count;
    } 

}
