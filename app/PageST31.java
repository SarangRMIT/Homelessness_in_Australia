package app;

import java.util.ArrayList;

import io.javalin.http.Context;
import io.javalin.http.Handler;

/**
 * Example Index HTML class using Javalin
 * <p>
 * Generate a static HTML page using Javalin
 * by writing the raw HTML into a Java String object
 *
 * @author Timothy Wiley, 2021. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */

public class PageST31 implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page5.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Comparing Homelessness</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='pageST31.css' />";
        html = html + "</head>";

        // Add the body
        html = html + "<body>";

        // Add the topnav
        // This uses a Java v15+ Text Block
        html = html + """
            <div class='topnav'>
                <a href='page6.html'>Change in <br> Homelessness</a>
                <a href='page5.html'>Comparing <br> Homelessness</a>
                <a href='page4.html'>Number of <br> Homeless People</a>
                <a href='page3.html'>Ranking & List <br> of LGAs</a>
                <a href='mission.html'>Our Mission</a>
                <a href='/'>Home</a>
                <a class='split' href='/'><img src='website logo.png' alt='Website logo' height='40'></a>
                <a class='split' href='/'>Australian <br> Homelessness</a>
            </div>
        """;

        // Add header content block
        html = html + """
            <div class='header'>
                <h1>Comparing Homelessness <br> With Other Factors</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Look up some information from JDBC
        // First we need to use your JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        //PART 1

        //RATIO OF HOMELESS PEOPLE IN DIFFERENT CATEGORIES TO THE TOTAL NUMBER OF PEOPLE IN EACH LGA
        html = html + "<h2 id = 'ratios'>Ratio of Homeless People in <br> Different Categories to the Total <br> Number of People &amp; Income <br> Information by LGA</h2>";

        html = html + "   <br>";

        html = html + "<form action='#ratios' method='post'>";
        
        html = html + "   <div class='form-group1'>";
        html = html + "      <label for='statetype_drop' class = 'labels'><b>CHOOSE A STATE OF THE LGA YOU WISH TO SEE:</b></label>";
        html = html + "      <select id='statetype_drop' name='statetype_drop'>";
        html = html + "         <option>Choose a State*</option>";

        // Gettting the Name of the States from the Database
        ArrayList<State> states = jdbc.getStates();
        for (State stateName : states){
            html = html + "     <option>" + stateName.getStateName() + "</option>";
        }

        //Continuing the HTML
        html = html + "      </select>";
        html = html + "   </div>";

        //Search Button
        html = html + "<div class = 'button1'>";
        html = html + "<br>";
        html = html + "   <button type='submit' class='button1'>Click here to obtain <br> LGA options & to <br> select your categories</button>";
        html = html + "</div>";

        html = html + "</form>";

        //Obtain the data recieved by this form
        String statetype_drop = context.formParam("statetype_drop");

        //Tell the user which state they have chosen, as it does not get stored in the dropdown itself
        if (statetype_drop != null){
            if (statetype_drop.equals("Choose a State*")){
                html = html + "<div class = 'stateChosen' id = 'stateChosen'><h4>Please choose a valid dropdown option to obtain LGA options.</h4></div>";
            }
            else{
                html = html + "<div class = 'stateChosen' id = 'stateChosen'><h4>State Chosen: " + statetype_drop + "</h4></div>";
            }
        }

        String[] sex_options = {"Male","Female"};
        String[] age_options = {"0-9 years","10-19 years","20-29 years","30-39 years", "40-49 years","50-59 years","60+ years"};
        String[] status_options = {"Homeless","At-risk"};
        String[] year_options = {"2016", "2018"};

        html = html + "<form action='#ratios' method='post'>";

        if (statetype_drop != null){
                html = html + "   <div class='form-group2'>";
            html = html + "      <label for='lgabystatetype_drop'><b>CHOOSE A LOCAL GOVERNMENT AREA (LGA):</b></label>";
            html = html + "      <select id='lgabystatetype_drop' name='lgabystatetype_drop'>";
            html = html + "         <option>Choose a LGA*</option>";

            // Gettting the Name of the LGAs according to the State Chosen from the Database
            ArrayList<LGA> lgaByState = jdbc.getLGAbyState(statetype_drop);
            for (LGA lgaName : lgaByState){
                html = html + "     <option>" + lgaName.getName16() + "</option>";
            }

            //Continuing the HTML
            html = html + "      </select>";
            html = html + "   </div>";

            //DROPDOWN FOR SEX CATEGORIES
            html = html + "   <br>";
            html = html + "   <div class='form-group3'>";
            html = html + "      <select id='choosesex_drop' name='choosesex_drop'>";
            html = html + "         <option>Choose a Sex*</option>";

            for (String i : sex_options){
                html = html + "     <option>" + i + "</option>";
            }

            html = html + "      </select>";
            html = html + "   </div>";

            //DROPDOWN FOR AGE CATEGORIES
            html = html + "   <br>";
            html = html + "   <div class='form-group4'>";
            html = html + "      <select id='chooseage_drop' name='chooseage_drop'>";
            html = html + "         <option>Choose an Age Group*</option>";

            for (String i : age_options){
                html = html + "     <option>" + i + "</option>";
            }

            html = html + "      </select>";
            html = html + "   </div>";

            //DROPDOWN FOR STATUS CATEGORIES
            html = html + "   <br>";
            html = html + "   <div class='form-group5'>";
            html = html + "      <select id='choosestatus_drop' name='choosestatus_drop'>";
            html = html + "         <option>Choose a Status*</option>";

            for (String i : status_options){
                html = html + "     <option>" + i + "</option>";
            }

            html = html + "      </select>";
            html = html + "   </div>";

            //DROPDOWN FOR YEAR CATEGORIES
            html = html + "   <br>";
            html = html + "   <div class='form-group6'>";
            html = html + "      <select id='chooseyear_drop' name='chooseyear_drop'>";
            html = html + "         <option >Choose a Year*</option>";

            for (String i : year_options){
                html = html + "     <option>" + i + "</option>";
            }

            html = html + "      </select>";
            html = html + "   </div>";

            html = html + "   <br>";

            //Search Button
            html = html + "<div class = 'button1'>";
            html = html + "<br>";
            html = html + "   <button type='submit' class='button1'>Search</button>";
            html = html + "</div>";
        }

        //Closing the Form
        html = html + "</form>";

        String lgabystatetype_drop = context.formParam("lgabystatetype_drop");
        String choosesex_drop = context.formParam("choosesex_drop");
        String chooseage_drop = context.formParam("chooseage_drop");
        String choosestatus_drop = context.formParam("choosestatus_drop");
        String chooseyear_drop = context.formParam("chooseyear_drop");  

        String LGAChosen = lgabystatetype_drop;

        int year = 0;

        if (chooseyear_drop != null){
            switch (chooseyear_drop){
                case "2016":
                     year = 2016;
                     break;
                 case "2018":
                     year = 2018;
                     break;
             }
         }

        html = html + "<div class = 'result'>";
        //html = html + "<div id = 'ratios'>";

        if (LGAChosen != null && chooseyear_drop != null && choosesex_drop != null && choosestatus_drop != null & chooseage_drop != null){
            if (LGAChosen.equals("Choose a LGA*") || chooseyear_drop.equals("Choose a Year*")  || choosesex_drop.equals("Choose a Sex*") || choosestatus_drop.equals("Choose a Status*") || chooseage_drop.equals("Choose an Age Group*")){
                html = html + "<hr class = 'hr3' color='#1B2355'>";
                html = html + "<p> To view the ratios of homeless / at-risk people, <br> please choose a value for all the dropdowns.</p>"; 
            }
            else{
                html = html + "<hr class = 'hr3' color='#1B2355'>";

                ArrayList<State> state = jdbc.getStatebyLGA(LGAChosen);

                for (State s : state){
                    html = html + "<h3>" + LGAChosen + ", " + s.getStateName() + "</h3>";
                }

                html = html + "<p>Ratio of Homeless & At-risk People to the <b>Total Population of " + LGAChosen + "</b>:</p>";
            
                int countByLGA = jdbc.SumOfCountByLGA(LGAChosen, year);
                int populationByLGA = jdbc.totalPopulationByLGA(LGAChosen, year);

                //String percentage = String.format("%.2f", ((float)(countNum) / sum) * 100);
                String ratioTotal = String.format("%.2f", ((float)(countByLGA) / populationByLGA) * 100);

                html = html + "<h3>" + ratioTotal + "%</h3>";

                html = html + "<p> The ratio of <b>" + choosestatus_drop + " " + choosesex_drop + "s</b> in <b>" + LGAChosen + " </b>between <b>" + chooseage_drop + ",</b> in the year <b>" + year + ",</b> against the <b>Total Population of " + LGAChosen + " </b>is:</p>";
            
                String count = outputCount(LGAChosen, chooseyear_drop, choosesex_drop, choosestatus_drop, chooseage_drop);
            
                //Convert the obtained count of homeless/at-risk people to an integer
                int countNum = Integer.parseInt(count);

                String percentage = String.format("%.2f", ((float)(countNum) / populationByLGA) * 100);
                html = html + "<h3>" + percentage + "%</h3>";

                //Table
                html = html + """
                
                    <table>
                        <tr>
                            <th> Median Age </th>
                            <th> Median <br> Mortgage <br> Repayments </th>
                            <th> Median Weekly Rent </th>
                            <th> Median Weekly Income </th>
                        </tr>
                        <tr>
                """;

                ArrayList<LGAIncome> lgaIncome = jdbc.getLGAIncome(LGAChosen);

                for (LGAIncome i : lgaIncome){
                    html = html + "<td>" + i.getLGAMedianAge() + "</td>";
                    html = html + "<td>" + i.getLGAMortgageRepayments() + "</td>";
                    html = html + "<td>" + i.getLGAWeeklyRent() + "</td>";
                    html = html + "<td>" + i.getLGAWeeklyIncome() + "</td>";
                }

                html = html + """
                        </tr>
                    </table>
                """;

                html = html + "   <br>";
                //html = html + "   <hr class='hr1' color='black'>";
            } 
        }

        //html = html + "</div>";

        //PART 2
        html = html + "   <hr class='hr1' color='black'>";

        html = html + "<h2 id = 'specificRatios'>Filter Information For <br> Ratios Based on Median Age <br> and Weekly Income by LGA</h2>";

        html = html + "<form action='#specificRatios' method='post'>";
        
        html = html + "   <div class='form-group1'>";
        html = html + "      <label for='statetype_drop_2' class = 'labels'><b>CHOOSE A STATE FOR THE RATIOS YOU WISH TO SEE:</b></label>";
        html = html + "      <select id='statetype_drop_2' name='statetype_drop_2'>";
        html = html + "         <option>Choose a State*</option>";

        // Gettting the Name of the States from the Database
        //ArrayList<State> states = jdbc.getStates();
        for (State stateName : states){
            html = html + "     <option>" + stateName.getStateName() + "</option>";
        }

        //Continuing the HTML
        html = html + "      </select>";
        html = html + "   </div>";

        //html = html + "<h3 class = 'labels'> Select your Categories </h3>";

        //STATUS DROPDOWNS 
        html = html + "   <br>";
        html = html + "   <div class='form-group5'>";
        html = html + "      <select id='choosestatus_drop_2' name='choosestatus_drop_2'>";
        html = html + "         <option>Choose a Status*</option>";

        for (String i : status_options){
            html = html + "     <option>" + i + "</option>";
        }

        html = html + "      </select>";
        html = html + "   </div>";

        //SEX DROPDOWNS
        html = html + "   <br>";
        html = html + "   <div class='form-group3'>";
        html = html + "      <select id='choosesex_drop_2' name='choosesex_drop_2'>";
        html = html + "         <option>Choose a Sex*</option>";

        for (String i : sex_options){
            html = html + "     <option>" + i + "</option>";
        }

        html = html + "      </select>";
        html = html + "   </div>";

        //AGE OPTIONS DROPDOWNS
        String[] age_options_dropdown2 = {"under 10", "over 10", "under 20", "over 20", "under 30", "over 30", "under 40", "over 40", "under 50", "over 50", "under 60", "over 60"};

        html = html + "   <br>";
        html = html + "   <div class='form-group7'>";
        html = html + "      <select id='chooseagegroup_drop_2' name='chooseagegroup_drop_2'>";
        html = html + "         <option>Choose an Age Group*</option>";

        for (String i : age_options_dropdown2){
            html = html + "     <option>" + i + "</option>";
        }

        html = html + "      </select>";
        html = html + "   </div>";

        //WEEKLY INCOME DROPDWONS
        String[] weekly_income_options = {"over 500", "under 1000", "over 1000", "under 1500", "over 1500", "under 2000", "over 2000", "under 2500", "over 2500", "under 3000", "over 3000", "under 3500"};

        html = html + "   <br>";
        html = html + "   <div class='form-group8'>";
        html = html + "      <select id='chooseincome_drop' name='chooseincome_drop'>";
        html = html + "         <option>Choose a Weekly Income Group*</option>";

        for (String i : weekly_income_options){
            html = html + "     <option>" + i + "</option>";
        }

        html = html + "      </select>";
        html = html + "   </div>";

        html = html + "<div class = 'button1'>";
        html = html + "<br>";
        html = html + "   <button type='submit' class='button1'>Search</button>";
        html = html + "</div>";

        //Closing the Form
        html = html + "</form>";

        String statetype_drop_2 = context.formParam("statetype_drop_2");
        String choosestatus_drop_2 = context.formParam("choosestatus_drop_2");
        String choosesex_drop_2 = context.formParam("choosesex_drop_2");
        String chooseagegroup_drop_2 = context.formParam("chooseagegroup_drop_2");
        String chooseincome_drop = context.formParam("chooseincome_drop");

        int age = 0;
        int income = 0;
        String underOverAge = "";
        String underOverIncome = "";
        String sex = "";
        String status = "";

        if (chooseagegroup_drop_2 != null){
            switch(chooseagegroup_drop_2){
                case "under 10":
                case "over 10":
                    age = 10;
                    break;
                case "under 20":
                case "over 20":
                    age = 20;
                    break;
                case "under 30":
                case "over 30":
                    age = 30;
                    break;
                case "under 40":
                case "over 40":
                    age = 40;
                    break;
                case "under 50":
                case "over 50":
                    age = 50;
                    break;
                case "under 60":
                case "over 60":
                    age = 60;
                    break;
            }
        }

        if (chooseincome_drop != null){
            switch (chooseincome_drop){
                case "over 500":
                    income = 500;
                    break;

                case "under 1000":
                case "over 1000":
                    income = 1000;
                    break;

                case "under 1500":
                case "over 1500":
                    income = 1500;
                    break;
                
                case "under 2000":
                case "over 2000":
                    income = 2000;
                    break;

                case "under 2500":
                case "over 2500":
                    income = 2500;
                    break;
                
                case "under 3000":
                case "over 3000":
                    income = 3000;
                    break;

                case "under 3500":
                    income = 3500;
                    break;
            }
        }

        if (chooseagegroup_drop_2 != null && chooseincome_drop != null){
            switch (chooseagegroup_drop_2){
                case "under 10":
                case "under 20":
                case "under 30":
                case "under 40":
                case "under 50":
                case "under 60":
                    underOverAge = "under";
                    break;
                case "over 10":
                case "over 20":
                case "over 30":
                case "over 40":
                case "over 50":
                case "over 60":
                    underOverAge = "over";
                    break;
            }

            switch (chooseincome_drop){
                case "under 1000":
                case "under 1500":
                case "under 2000":
                case "under 2500":
                case "under 3000":
                case "under 3500":
                    underOverIncome = "under";
                    break;
                case "over 500":
                case "over 1000":
                case "over 1500":
                case "over 2000":
                case "over 2500":
                case "over 3000":
                    underOverIncome = "over";
                    break;
            }
        }

        if (choosesex_drop_2 != null){
            switch(choosesex_drop_2){
                case "Male":
                    sex = "m";
                    break;
                case "Female":
                    sex = "f";
                    break;
            }
        }

        if (choosestatus_drop_2 != null){
            switch(choosestatus_drop_2){
                case "Homeless":
                    status = "homeless";
                    break;
                case "At-risk":
                    status = "at_risk";
                    break;
            }
        }

        int Homelesspopulation = jdbc.SumOfCountByCountry(2018);

        //html = html + "<div id = 'specificRatios'>";

        if (statetype_drop_2 != null && choosestatus_drop_2 != null && choosesex_drop_2 != null && chooseagegroup_drop_2 != null && chooseincome_drop != null){
            if (statetype_drop_2.equals("Choose a State*") || choosestatus_drop_2.equals("Choose a Status*")  || choosesex_drop_2.equals("Choose a Sex*") || chooseincome_drop.equals("Choose a Weekly Income Group*") || chooseagegroup_drop_2.equals("Choose an Age Group*")){
                html = html + "<hr class = 'hr3' color='#1B2355'>";
                html = html + "<p> To view the ratios of homeless / at-risk people, <br> please choose a valid value for all the dropdowns.</p>"; 
            }
            else{
                html = html + "<hr class = 'hr3' color='#1B2355'>";

                String rate = "";

                html = html + "<div class = 'stateChosen'><h4>State Chosen: " + statetype_drop_2 + "</h4></div>";

                html = html + "The <b>" + choosestatus_drop_2 + " </b>ratio for <b>" + choosesex_drop_2 + "s</b> in <b>" + statetype_drop_2 + " </b>with an average weekly income <b>" + chooseincome_drop + " </b>and a median age of <b>" + chooseagegroup_drop_2 + " </b>is:";
            
                if (underOverAge == "over" && underOverIncome == "over"){
                    int count = jdbc.CountByIncomeAndAge(statetype_drop_2, sex, status, ">", income, ">", age);
                    rate = String.format("%.3f", ((float)(count) / Homelesspopulation) * 100);
                }
                else if(underOverAge == "over" && underOverIncome == "under"){
                    int count = jdbc.CountByIncomeAndAge(statetype_drop_2, sex, status, "<", income, ">", age);
                    rate = String.format("%.3f", ((float)(count) / Homelesspopulation) * 100);
                }
                else if(underOverAge == "under" && underOverIncome == "under"){
                    int count = jdbc.CountByIncomeAndAge(statetype_drop_2, sex, status, "<", income, "<", age);
                    rate = String.format("%.3f", ((float)(count) / Homelesspopulation) * 100);
                }
                else if(underOverAge == "under" && underOverIncome == "over"){
                    int count = jdbc.CountByIncomeAndAge(statetype_drop_2, sex, status, ">", income, "<", age);
                    rate = String.format("%.3f", ((float)(count) / Homelesspopulation) * 100);
                }

                html = html + "<h3>" + rate + "%</h3>";
            }
        }

        //html = html + "</div>";

        //______________________________________________________________________________________________________________________________________________________________________________________________

        //PART 3 - GRAPH
        html = html + "   <hr class='hr1' color='black'>";
        html = html + "<h2 id = 'graph'>Trends for Different LGAs Based on <br> the Number of Homeless / At-risk People <br> & The Average Weekly Income</h2>";

        //Form Starts
        html = html + "<form action='#graph' method='post'>";
        
        html = html + "   <div class='form-group1'>";
        html = html + "      <label for='statetype_drop_3' class = 'labels'><b>CHOOSE A STATE FOR THE TRENDS YOU WISH TO SEE:</b></label>";
        html = html + "      <select id='statetype_drop_3' name='statetype_drop_3'>";
        html = html + "         <option>Choose a State*</option>";

        // Gettting the Name of the States from the Database
        //ArrayList<State> states = jdbc.getStates();
        for (State stateName : states){
            html = html + "     <option>" + stateName.getStateName() + "</option>";
        }

        //Continuing the HTML
        html = html + "      </select>";
        html = html + "   </div>";

        html = html + "<div class = 'button1'>";
        html = html + "<br>";
        html = html + "   <button type='submit' class='button1'>Click here to obtain <br> LGA options</button>";
        html = html + "</div>";

        html = html + "</form>";

        //Obtain the data recieved by this form
        String statetype_drop_3 = context.formParam("statetype_drop_3");

        //Tell the user which state they have chosen, as it does not get stored in the dropdown itself
        if (statetype_drop_3 != null){
            if (statetype_drop_3.equals("Choose a State*")){
                html = html + "<p class = 'stateChosen' id = 'stateChosenTrends'><b>Please choose a valid dropdown option to obtain LGA options.</b></p>";    
            }
            else{
                html = html + "<div class = 'stateChosen' id = 'stateChosenTrends'><h4>State Chosen: " + statetype_drop_3 + "</h4></div>";
            }
        }

        ArrayList<LGA> lgaByState2 = jdbc.getLGAbyState(statetype_drop_3);

        //Second Form 
        html = html + "<form action='#graph' method='post'>";

        if (statetype_drop_3 != null){
            html = html + "<div class = 'part3Dropdowns'>";

            html = html + "   <div class='form-group9'>";
            html = html + "      <select id='lgabystatetype_drop_2' name='lgabystatetype_drop_2'>";
            html = html + "         <option>Choose the first LGA*</option>";

            // Gettting the Name of the LGAs according to the State Chosen from the Database
            for (LGA lgaName : lgaByState2){
                html = html + "     <option>" + lgaName.getName16() + "</option>";
            }

            //Continuing the HTML
            html = html + "      </select>";
            html = html + "   </div>";

            html = html + "<br>";

            html = html + "   <div class='form-group9'>";
            //html = html + "      <label for='lgabystatetype_drop_2'><b>CHOOSE A LOCAL GOVERNMENT AREA (LGA):</b></label>";
            html = html + "      <select id='lgabystatetype_drop_3' name='lgabystatetype_drop_3'>";
            html = html + "         <option>Choose the second LGA*</option>";

            // Gettting the Name of the LGAs according to the State Chosen from the Database
            for (LGA lgaName : lgaByState2){
                html = html + "     <option>" + lgaName.getName16() + "</option>";
            }

            //Continuing the HTML
            html = html + "      </select>";
            html = html + "   </div>";

            html = html + "<br>";

            html = html + "   <div class='form-group9'>";
            //html = html + "      <label for='lgabystatetype_drop_2'><b>CHOOSE A LOCAL GOVERNMENT AREA (LGA):</b></label>";
            html = html + "      <select id='lgabystatetype_drop_4' name='lgabystatetype_drop_4'>";
            html = html + "         <option>Choose the third LGA*</option>";

            // Gettting the Name of the LGAs according to the State Chosen from the Database
            for (LGA lgaName : lgaByState2){
                html = html + "     <option>" + lgaName.getName16() + "</option>";
            }

            //Continuing the HTML
            html = html + "      </select>";
            html = html + "   </div>";

            html = html + "<br>";

            html = html + "   <div class='form-group9'>";
            //html = html + "      <label for='lgabystatetype_drop_2'><b>CHOOSE A LOCAL GOVERNMENT AREA (LGA):</b></label>";
            html = html + "      <select id='lgabystatetype_drop_5' name='lgabystatetype_drop_5'>";
            html = html + "         <option>Choose the fourth LGA*</option>";

            // Gettting the Name of the LGAs according to the State Chosen from the Database
            for (LGA lgaName : lgaByState2){
                html = html + "     <option>" + lgaName.getName16() + "</option>";
            }

            //Continuing the HTML
            html = html + "      </select>";
            html = html + "   </div>";

            html = html + "<br>";

            html = html + "   <div class='form-group9'>";
            //html = html + "      <label for='lgabystatetype_drop_2'><b>CHOOSE A LOCAL GOVERNMENT AREA (LGA):</b></label>";
            html = html + "      <select id='lgabystatetype_drop_6' name='lgabystatetype_drop_6'>";
            html = html + "         <option>Choose the fifth LGA*</option>";

            // Gettting the Name of the LGAs according to the State Chosen from the Database
            for (LGA lgaName : lgaByState2){
                html = html + "     <option>" + lgaName.getName16() + "</option>";
            }

            //Continuing the HTML
            html = html + "      </select>";
            html = html + "   </div>";

            html = html + "</div>";

            html = html + "<div class = 'button1'>";
            html = html + "<br>";
            html = html + "   <button type='submit' class='button1'>Search</button>";
            html = html + "</div>";
        }

        //Closing the Form
        html = html + "</form>";
        
        String lgabystatetype_drop_2 = context.formParam("lgabystatetype_drop_2");
        String lgabystatetype_drop_3 = context.formParam("lgabystatetype_drop_3");
        String lgabystatetype_drop_4 = context.formParam("lgabystatetype_drop_4");
        String lgabystatetype_drop_5 = context.formParam("lgabystatetype_drop_5");
        String lgabystatetype_drop_6 = context.formParam("lgabystatetype_drop_6");

        int LGA1Count = jdbc.SumOfCountByLGA(lgabystatetype_drop_2, 2018);
        int LGA2Count = jdbc.SumOfCountByLGA(lgabystatetype_drop_3, 2018);
        int LGA3Count = jdbc.SumOfCountByLGA(lgabystatetype_drop_4, 2018);
        int LGA4Count = jdbc.SumOfCountByLGA(lgabystatetype_drop_5, 2018);
        int LGA5Count = jdbc.SumOfCountByLGA(lgabystatetype_drop_6, 2018);

        int LGA1Income = 0;
        int LGA2Income = 0;
        int LGA3Income = 0;
        int LGA4Income = 0;
        int LGA5Income = 0;

        ArrayList<LGAIncome> lgaIncome2 = jdbc.getLGAIncome(lgabystatetype_drop_2);
        for (LGAIncome i : lgaIncome2){
            LGA1Income = i.getLGAWeeklyIncome();
        }

        ArrayList<LGAIncome> lgaIncome3 = jdbc.getLGAIncome(lgabystatetype_drop_3);
        for (LGAIncome i : lgaIncome3){
            LGA2Income = i.getLGAWeeklyIncome();
        }

        ArrayList<LGAIncome> lgaIncome4 = jdbc.getLGAIncome(lgabystatetype_drop_4);
        for (LGAIncome i : lgaIncome4){
            LGA3Income = i.getLGAWeeklyIncome();
        }

        ArrayList<LGAIncome> lgaIncome5 = jdbc.getLGAIncome(lgabystatetype_drop_5);
        for (LGAIncome i : lgaIncome5){
            LGA4Income = i.getLGAWeeklyIncome();
        }

        ArrayList<LGAIncome> lgaIncome6 = jdbc.getLGAIncome(lgabystatetype_drop_6);
        for (LGAIncome i : lgaIncome6){
            LGA5Income = i.getLGAWeeklyIncome();
        }

        html = html + "<div class = 'graph'>";

        if (lgabystatetype_drop_2 != null && lgabystatetype_drop_3 != null && lgabystatetype_drop_4 != null && lgabystatetype_drop_5 != null && lgabystatetype_drop_6 != null){
            if(lgabystatetype_drop_2.equals("Choose the first LGA*") || lgabystatetype_drop_3.equals("Choose the second LGA*")|| lgabystatetype_drop_4.equals("Choose the third LGA*") || lgabystatetype_drop_5.equals("Choose the fourth LGA*")|| lgabystatetype_drop_6.equals("Choose the fifth LGA*")){
                html = html + "<hr class = 'hr3' color='#1B2355'>";
                html = html + "<p><b>Please choose valid LGAs in all the dropdowns to obtain the result.</b></p>";
            }
            else{
                html = html + "<hr class = 'hr3' color='#1B2355'>";
                ArrayList<State> StateName = jdbc.getStatebyLGA(lgabystatetype_drop_2);
                html = html + "<p>The LGAs chosen are: " + lgabystatetype_drop_2 + ", " + lgabystatetype_drop_3 + ", " + lgabystatetype_drop_4 + ", " + lgabystatetype_drop_5 + " and " + lgabystatetype_drop_6 + "</p>";
                for (State i : StateName){
                    html = html + "<p>The State chosen is: " + i.getStateName();
                }

                html = html + "<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js'> </script>";

                html = html + """
                    <canvas id='myChart' style='width:100%; max-width:8`00px'></canvas>
  
                    <script>
                """;

                    html = html + "var xValues = ['%s','%s','%s','%s','%s'];".formatted(lgabystatetype_drop_2, lgabystatetype_drop_3, lgabystatetype_drop_4, lgabystatetype_drop_5, lgabystatetype_drop_6);
            
                html = html + """
                    new Chart('myChart', {
                    type: 'line',
                    data: {
                        labels: xValues,
                        datasets: [{ 
                """;
  
                html = html + "data: [%d, %d, %d, %d, %d],".formatted(LGA1Count, LGA2Count, LGA3Count, LGA4Count, LGA5Count);
  
                html = html + """          
                        borderColor: '#1B2355',
                        label: 'Homeless & At-Risk People',
                        fill: false
                    }, { 
                """;

                html = html + "data: [%d, %d, %d, %d, %d],".formatted(LGA1Income, LGA2Income, LGA3Income, LGA4Income, LGA5Income);

                html = html + """          
                        borderColor: 'red',
                        label: 'Weekly Income',
                        fill: false
                    }]
                    },
                    options: {
                        display: true
                    }
                    });
                    </script>
                """;
            }
        }

        html = html + "</div>";

        //PART 4 
        int population = jdbc.SumOfCountByCountry(2018);

        html = html + "   <hr class='hr1' color='black'>";

        html = html + """
            <h2>Other Data You Might Want <br> To Have A Look At</h2>

            <button class='accordion'>The homeless rate in the LGA with the highest / lowest weekly income?</button>

                <div class='panel'>
            """;
            
            html = html + "<p> The LGA with the <b>lowest weekly income</b> is: Peterborough, South Australia </p>";
            int sumLGAlowestIncome = jdbc.lowestWeeklyIncome();
            String ratelowestIncome = String.format("%.4f", ((float)(sumLGAlowestIncome) / population) * 100);
            html = html + "  <p>The homeless rate of Peterborough is: " + ratelowestIncome + "%</p>";

            html = html + "<hr class = 'hr3' color='#1B2355'>";

            html = html + "<p> The LGA with the <b>highest weekly income</b> is: Peppermint Grove, Western Australia </p>";
            int sumLGAhighestIncome = jdbc.highestWeeklyIncome();
            String ratehighestIncome = String.format("%.4f", ((float)(sumLGAhighestIncome) / population) * 100);
            html = html + "<p> The homeless rate of Peppermint Grove is: " + ratehighestIncome + "%</p>";

            html = html + """
                </div>
            
            <button class='accordion'>The homeless rate in the LGA with the highest / lowest median age?</button>
            <div class='panel'>
            """;

            html = html + "<p> The LGAs with the <b>lowest median age</b> are: Cherbourg, QLD and Doomadgee, QLD </p>";
            int sumLGAlowestAge = jdbc.lowestMedianAge();
            String ratelowestAge = String.format("%.4f", ((float)(sumLGAlowestAge) / population) * 100);
            html = html + "<p> The homeless rate of Cherbourg and Doomadgee combined are: " + ratelowestAge + "%</p>";

            html = html + "<hr class = 'hr3' color='#1B2355'>";
            
            html = html + "<p> The LGA with the <b>highest median age</b> is: Queenscliffe, Victoria </p>";
            int sumLGAhighestAge = jdbc.highestMedianAge();
            String ratehighestAge = String.format("%.4f", ((float)(sumLGAhighestAge) / population) * 100);
            html = html + "  <p>The homeless rate of Queenscliffe is: " + ratehighestAge + "%</p>";
            
            html = html + """
                </div>

            <button class='accordion'>The homeless rate in the LGA with the highest / lowest rent?</button>
            <div class='panel'>
            """;

            html = html + "<p> The LGAs with the <b>lowest weekly rent</b> are: Maralinga Tjarutja, South Australia; Leonora, Western Australia and Sandstone, Western Australia</p>";
            int sumLGAlowestRent = jdbc.lowestMedianRent();
            String ratelowestRent = String.format("%.4f", ((float)(sumLGAlowestRent) / population) * 100);
            html = html + "<p> The homeless rate of Maralinga Tjarutja, Leonora and Sandstone combined are: " + ratelowestRent + "%</p>";

            html = html + "<hr class = 'hr3' color='#1B2355'>";
            
            //Highest
            html = html + "<p> The LGAs with the <b>highest weekly rent</b> are: Ku-ring-gai, NSW and Woollahra, NSW </p>";
            int sumLGAhighestRent = jdbc.highestMedianRent();
            String ratehighestRent = String.format("%.4f", ((float)(sumLGAhighestRent) / population) * 100);
            html = html + "<p> The homeless rate of Ku-ring-gai and Woollahra combined are: " + ratehighestRent + "%</p>";

            html = html + """
            </div>
            <button class='accordion'>The LGA with the highest/lowest ratio of homelessness by sex?</button>
            <div class='panel'>
            """;

            ArrayList<LGAByCount> lgaHighestCountMale = jdbc.highestCountBySex("m");
            for (LGAByCount i : lgaHighestCountMale){
                html = html + "<p> The LGA with the <b>highest ratio of homeless/at-risk males</b> is: " + i.getLGAName() + "</p>";
                html = html + "<p> The homeless rate of males in " + i.getLGAName() + " is: " + String.format("%.4f", ((float)(i.getCount()) / population) * 100) + "%</p>";
            }

            ArrayList<LGAByCount> lgaHighestCountFemale = jdbc.highestCountBySex("f");
            for (LGAByCount i : lgaHighestCountFemale){
                html = html + "<p> The LGA with the <b>highest ratio of homeless/at-risk females</b> is: " + i.getLGAName() + "</p>";
                html = html + "<p> The homeless rate of females in " + i.getLGAName() + " is: " + String.format("%.4f", ((float)(i.getCount()) / population) * 100) + "%</p>";
            }

            ArrayList<LGAByCount> lgalowestCountMale = jdbc.lowestCountBySex("m");
            for (LGAByCount i : lgalowestCountMale){
                html = html + "<p> The LGA with the <b>lowest ratio of homeless/at-risk males</b> is: " + i.getLGAName() + "</p>";
                html = html + "<p> The homeless rate of males in " + i.getLGAName() + " is: " + String.format("%.4f", ((float)(i.getCount()) / population) * 100) + "%</p>";
            }

            ArrayList<LGAByCount> lgalowestCountFemale = jdbc.lowestCountBySex("f");
            for (LGAByCount i : lgalowestCountFemale){
                html = html + "<p> The LGA with the <b>lowest ratio of homeless/at-risk females</b> is: " + i.getLGAName() + "</p>";
                html = html + "<p> The homeless rate of females in " + i.getLGAName() + " is: " + String.format("%.4f", ((float)(i.getCount()) / population) * 100) + "%</p>";
            }

            html = html + """
                </div>
            
            <script>
            var acc = document.getElementsByClassName('accordion');
            var i;
            
            for (i = 0; i < acc.length; i++) {
              acc[i].addEventListener('click', function() {
                this.classList.toggle('active');
                var panel = this.nextElementSibling;
                if (panel.style.maxHeight) {
                  panel.style.maxHeight = null;
                } else {
                  panel.style.maxHeight = panel.scrollHeight + 'px';
                } 
              });   
            }
            </script>
            
                """;

        html = html + "<br>";

        //Close the result div
        html = html + "</div>";

        // Close Content div
        html = html + "</div>";

        html = html + """
                <div class = 'findOutMore'>
                <br>
                <h2> FIND OUT MORE </h2>
                <table>
                <tr>
                <td colspan = '2'><a class='page1' href='page3.html'><b>Ranking and List<br> of LGAs</b></a></td>
                </tr>
                <tr>
                <td class='blankrow' colspan = '2'></td>
                </tr>
                <tr>
                <td><a class='page3' href='page4.html'><b>Number of Homeless <br> People in Particular Areas</b></a></td>
                <td><a class='page4' href='page6.html'><b>Change in Homelessness <br> over time</b></a></td>
                </tr>
                </table>
                <br>
                </div>
        """;

        // Footer
        html = html + """
            <div class='footer'>
                
                <br>
                <img src = 'flag1.png' class = 'column1' alt='flag'>
                <img src = 'flag2.png' class = 'column1' alt='flag'>
                <img src = 'flag3.png' class = 'column1' alt='flag'>
                <br>

                <p> Australian Homelessness acknowledges the Traditional <br>
                Custodians of lands upon which we meet and work on. We <br>
                recognize their continuing connection to land, waters and <br>
                culture. We pay our respects to their people and elders past, <br>
                present and future. </p>
                <br>

            </div>
        """;

        // Finish the HTML webpage
        html = html + "</body>" + "</html>";
        

        // DO NOT MODIFY THIS
        // Makes Javalin render the webpage
        context.html(html);
    }

    public String outputCount(String lga_name, String year, String sex, String status, String age_group){

        String html = "";

        JDBCConnection jdbc = new JDBCConnection();

        //Converting the categories chosen by the users into the format in which the information is stored in the database
        int year_final = 0;
        String sex_final = "";
        String status_final = "";
        String age_group_final = "";

        switch (year){
            case "2016":
                year_final = 2016;
                break;
            case "2018":
                year_final = 2018;
                break;
        }

        switch(sex){
            case "Male":
                sex_final = "m";
                break;
            case "Female":
                sex_final = "f";
                break;
        }

        switch(status){
            case "Homeless":
                status_final = "homeless";
                break;
            case "At-risk":
                status_final = "at_risk";
        }

        switch(age_group){
            case "0-9 years":
                age_group_final = "_0_9";
                break;
            case "10-19 years":
                age_group_final = "_10_19";
                break;
            case "20-29 years":
                age_group_final = "_20_29";
                break;
            case "30-39 years":
                age_group_final = "_30_39";
                break;
            case "40-49 years":
                age_group_final = "_40_49";
                break;
            case "50-59 years":
                age_group_final = "_50_59";
                break;
            case "60+ years":
                age_group_final = "_60_plus";
                break;
        }

        ArrayList<HomelessGroup> count = jdbc.getNumPeople(lga_name, year_final, sex_final, status_final, age_group_final);

       for (HomelessGroup num : count){
            html = html + num.getCount();
        } 

        //Return the count as a string
        return html;
    }

}