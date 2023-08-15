package app;

public class LGAIncome {
    
    private int age;

   private int mortgage_repayments;

   private int weekly_rent;

   private int weekly_income;

   public LGAIncome(int age, int mortgage_repayments, int weekly_rent, int weekly_income){
       this.age = age;
       this.mortgage_repayments = mortgage_repayments;
       this.weekly_rent = weekly_rent;
       this.weekly_income = weekly_income;
   }

   public int getLGAMedianAge(){
       return age;
   }
   
   public int getLGAMortgageRepayments(){
       return mortgage_repayments;
    }
    
    public int getLGAWeeklyRent(){
        return weekly_rent;
    }

    public int getLGAWeeklyIncome(){
        return weekly_income;
    }
}
