package app;

public class student {

    private String name;
    private String numb;
    private String desc;
    
    public student(String name,String numb,String desc){
        this.name = name;
        this.numb = numb;
        this.desc = desc ;
    }
    
    public String getname(){
        return name;
    } 

    public String getnum(){
        return numb;
    } 
    
    public String getdesc(){
        return desc;
    } 

}
