package app;

public class LGAProperties {

    private int year;

   private String type;

   private double area;

   private int population;

   public LGAProperties(String type, double area, int population, int year){
       this.type = type;
       this.area = area;
       this.population = population;
       this.year = year;
   }

   public String getLGAType(){
       return type;
   }
   
   public double getLGAArea(){
       return area;
    }
    
    public int getLGAPopulation(){
        return population;
    }

    public int getLGAYear(){
        return year;
    }
    
}
