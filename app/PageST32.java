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
public class PageST32 implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/page6.html";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Head information
        html = html + "<head>" + 
               "<title>Change in Homelessness</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='pageST32.css' />";
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
                <h1>Investigate the Change in<br>
                Homelessness Over Time</h1>
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        // Look up some information from JDBC
        // First we need to use your JDBCConnection class
        JDBCConnection jdbc = new JDBCConnection();

        //----------------------------------------------------------------------------------------------------------------------
        //Part 1 - Total Change in Population
        html = html + "<h2 id = 'result1'>Total Change in the Number of <br>Homeless/At-risk People By LGA</h2>";

        html = html + "<br>";

        //First Form Starts 
        html = html + "<form action='#result1' method='post'>";
        
        html = html + "   <div class='form-group1'>";
        html = html + "      <label for='statetype_drop'><b>CHOOSE A STATE OF THE LGA YOU WISH TO SEE:</b></label>";
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

        html = html + "<div class = 'button1'>";
        html = html + "<br>";
        html = html + "   <button type='submit' class='button1'>Click here to obtain <br> LGA options</button>";
        html = html + "</div>";

        html = html + "</form>";

        //html = html + "</form>";
        html = html + "<div class = 'result'>";

        //Obtain the data recieved by this form
        String statetype_drop = context.formParam("statetype_drop");

        if (statetype_drop != null){
            if (statetype_drop.equals("Choose a State*")){
                html = html + "<p class = 'stateChosen' id = 'stateChosen'><b>Please choose a valid dropdown option to obtain LGA options.</b></p>";    
            }
            else{
                //html = html + "<br>";
                html = html + "<div class = 'stateChosen' id = 'stateChosen'><h4>State Chosen: " + statetype_drop + "</h4></div>";
            }
        }
        else{
            html = html + "<h4 class = 'stateChosenStatement'> Please submit the 'Click Here to Obtain LGA options' button to choose a LGA. </h4>"; 
        }

        html = html + "</div>";
        //Second Form Starts

        html = html + "<form action='#result1' method='post'>";
        
        if (statetype_drop != null){
            html = html + "   <div class='form-group2'>";
            html = html + "      <label for='lgabystatetype_drop'><b>CHOOSE A LOCAL GOVERNMENT AREA (LGA):</b></label>";
            html = html + "      <select id='lgabystatetype_drop' name='lgabystatetype_drop'>";
            html = html + "         <option>Choose a LGA*</option>";

            ArrayList<LGA> lgaByState = jdbc.getLGAbyState(statetype_drop);
            for (LGA lgaName : lgaByState){
                html = html + "     <option>" + lgaName.getName16() + "</option>";
            }

            //Continuing the HTML
            html = html + "      </select>";
            html = html + "   </div>";

            //Search Button
            html = html + "<div class = 'button1'>";
            html = html + "<br>";
            html = html + "   <button type='submit' class='button1' onclick = 'ButtonClick()'>Search</button>";
            html = html + "</div>";

        }

        //Closing the Form
        html = html + "</form>";
        

        String lga = context.formParam("lgabystatetype_drop");

        html = html + "<div class = 'result'>";
        html = html + "<div class = 'result1'>";

        if(lga!=null){
            if (lga.equals("Choose a LGA*")){
                html = html + "<hr class = 'hr3' color='#1B2355'>";
                html = html + "<p> To view the total change of population of homeless & at-risk people, <br> please choose a valid value for the LGA.</p>"; 
            }
            else{
                html = html + "<hr class = 'hr3' color='#1B2355'>";
                ArrayList<State> nameState = jdbc.getStatebyLGA(lga);
                String name = "";

                for (State s : nameState){
                    name = s.getStateName();
                }

                html = html + "<h4>" + lga + ", " + name + " </h4>";

                html = html + """
                        <table>
                            <tr>
                    """;
                                    
                html = html +    "<th><b> Categories </th>";
                html = html +    "<th><b> 2016 </th>";
                html = html +    "<th><b> 2018 </th>";
                html = html +    "<th><b> Total Change </th>";
                html = html +    "<th><b> Total Percentage Change </th>";
                html = html + "</tr>";


                int totpop2016 = jdbc.totalPopulationByYearByLGA(lga, 2016);
                int totpop2018 = jdbc.totalPopulationByYearByLGA(lga, 2018);

                int tothom2016 = jdbc.homelessPopulation(lga, 2016,"homeless");
                int tothom2018 = jdbc.homelessPopulation(lga, 2018,"homeless");

                int totrisk2016 = jdbc.homelessPopulation(lga, 2016,"at_risk");
                int totrisk2018 = jdbc.homelessPopulation(lga, 2018,"at_risk");

                html = html + "<tr>";
                html = html + "<td><b>" + "Total<br>Population" + "</b></td>";
                html = html + "<td>" + totpop2016 + "</td>";
                html = html + "<td>" + totpop2018 + "</td>";
                html = html + "<td>" + (totpop2018-totpop2016) + "</td>";
                if (totpop2016 == 0){
                    if (totpop2016 == 0 && totpop2018 == 0){
                        html = html + "<td>0</td>";    
                    }
                    else{
                        html = html + "<td>Not Applicable</td>";    
                    }
                }
                else{
                    html = html + "<td>" + (((float)totpop2018-totpop2016)*100/totpop2016) + "%</td>";    
                }
                //html = html + "<td>" + (((float)totpop2018-totpop2016)*100/totpop2016) + "%</td>";
                html = html + "</tr>";
        
                html = html + "<tr>";
                html = html + "<td><b>" + "Homeless<br>Population" + "</b></td>";
                html = html + "<td>" + tothom2016 + "</td>";
                html = html + "<td>" + tothom2018 + "</td>";
                html = html + "<td>" + (tothom2018-tothom2016) + "</td>";
                if (tothom2016 == 0){
                    if (tothom2016 == 0 && tothom2018 == 0){
                        html = html + "<td>0</td>";    
                    }
                    else{
                        html = html + "<td>Not Applicable</td>";    
                    }
                }
                else{
                    html = html + "<td>" + (((float)tothom2018-tothom2016)*100/tothom2016) + "%</td>";
                }
                //html = html + "<td>" + (((float)tothom2018-tothom2016)*100/tothom2016) + "%</td>";
                html = html + "</tr>";
        
                html = html + "<tr>";
                html = html + "<td><b>" + "At-Risk<br>Population" + "</b></td>";
                html = html + "<td>" + totrisk2016 + "</td>";
                html = html + "<td>" + totrisk2018 + "</td>";
                html = html + "<td>" + (totrisk2018-totrisk2016) + "</td>";
                if (totrisk2016 == 0){
                    if (totrisk2016 == 0 && totrisk2018 == 0){
                        html = html + "<td>0</td>";    
                    }
                    else{
                        html = html + "<td>Not Applicable</td>";    
                    }
                }
                else{
                    html = html + "<td>" + (((float)totrisk2018-totrisk2016)*100/totrisk2016) + "%</td>";
                }
                //html = html + "<td>" + (((float)totrisk2018-totrisk2016)*100/totrisk2016) + "%</td>";
                html = html + "</tr>";

                html = html +"</table>";

                html = html + "<br>";
            }
        }
        html = html + "</div>";
        
        //----------------------------------------------------------------------------------------------------------------------
        //Part 2 - Percentage Increase / Decrease of Selected Categories by LGA

        String[] sex_options = {"Male","Female"};
        String[] status_options = {"Homeless","At-risk"};
        String[] populationChange_options = {"Increase","Decrease", "None"};

        html = html + "<hr class='hr1' color='black'>";

        html = html + "<h2 id = 'result2'>Percentage Increase / Decrease <br> of Selected Categories by LGA</h2>";

        //Form Starts
        html = html + "<form action='#result2' method='post'>";
        
        //STATE DROPDOWN
        html = html + "   <div class='form-group1'>";
        html = html + "      <label for='statetype_drop_2' class = 'labels'><b>CHOOSE A STATE FOR THE RATIOS YOU WISH TO SEE:</b></label>";
        html = html + "      <select id='statetype_drop_2' name='statetype_drop_2'>";
        html = html + "         <option>Choose a State*</option>";

        // Gettting the Name of the States from the Database
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

        //POPULATION CHANGE DROPDOWNS 
        html = html + "   <br>";
        html = html + "   <div class='form-group5'>";
        html = html + "      <select id='choosePopulationChange_drop' name='choosePopulationChange_drop'>";
        html = html + "         <option>Choose a Population Change*</option>";

        for (String i : populationChange_options){
            html = html + "     <option>" + i + "</option>";
        }

        html = html + "      </select>";
        html = html + "   </div>";

        //SEARCH BUTTON
        html = html + "<div class = 'button1'>";
        html = html + "<br>";
        html = html + "   <button type='submit' class='button1'>Search</button>";
        html = html + "</div>";

        //Closing the Form
        html = html + "</form>";

        String statetype_drop_2 = context.formParam("statetype_drop_2");
        String choosestatus_drop_2 = context.formParam("choosestatus_drop_2");
        String choosesex_drop_2 = context.formParam("choosesex_drop_2");
        String choosePopulationChange_drop = context.formParam("choosePopulationChange_drop");

        String sex = "";
        String status = "";
        String operation = "";

        html = html + "<div class = 'result2'>";

        //Converting the categories chosen by the users into the format in which the information is stored in the database
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

        if (choosePopulationChange_drop != null){
            switch (choosePopulationChange_drop){
                case "Increase":
                    operation = ">0";
                    break;
                case "Decrease":
                    operation = "<0";
                    break;
                case "None":
                    operation = "=0";
                    break;
            }
        }
        
        if (statetype_drop_2 != null && choosestatus_drop_2 != null && choosesex_drop_2 != null && choosePopulationChange_drop != null){
            if (statetype_drop_2.equals("Choose a State*") || choosestatus_drop_2.equals("Choose a Status*") || choosesex_drop_2.equals("Choose a Sex*") || choosePopulationChange_drop.equals("Choose a Population Change*") ){
                html = html + "<hr class = 'hr3' color='#1B2355'>";
                html = html + "<p> To view the percentages increase / decrease of selected categories, <br> please choose a valid value for all the dropdowns.</p>"; 
            }

            else{
                html = html + "<hr class = 'hr3' color='#1B2355'>";
                int totalHomeless2016 = jdbc.HomelessByCategory2016(statetype_drop_2, operation, status, sex);
                int totalHomeless2018 = jdbc.HomelessByCategory2018(statetype_drop_2, operation, status, sex);

                int change = totalHomeless2018 - totalHomeless2016;

                float percentageChange = ((float)(change) / totalHomeless2016) * 100;

                if (percentageChange < 0){
                    percentageChange = Math.abs(percentageChange);

                    if (choosePopulationChange_drop.equals("Increase")){
                        html = html + "<b>" + percentageChange + "%</b><br> was the <b>decrease</b> in <b>" + choosestatus_drop_2.toLowerCase() + " " + choosesex_drop_2.toLowerCase() + "s</b> in LGAs in <b>" + statetype_drop_2  + "</b> where <b>population increased</b>." ;      
                    }
                    else if (choosePopulationChange_drop.equals("Decrease")){
                        html = html + "<b>" + percentageChange + "%</b><br> was the <b>decrease</b> in <b>" + choosestatus_drop_2.toLowerCase() + " " + choosesex_drop_2.toLowerCase() + "s</b> in LGAs in <b>" + statetype_drop_2  + "</b> where <b>population decreased</b>." ;   
                    }
                    else{
                        html = html + "<b>" + percentageChange + "%</b><br> was the <b>decrease</b> in <b>" + choosestatus_drop_2.toLowerCase() + " " + choosesex_drop_2.toLowerCase() + "s</b> in LGAs in <b>" + statetype_drop_2  + "</b> where <b>population remained the same</b>." ;
                    }
                }
                else if (percentageChange > 0){
                    
                    if (choosePopulationChange_drop.equals("Increase")){
                        html = html + "<b>" + percentageChange + "%</b><br> was the <b>increase</b> in <b>" + choosestatus_drop_2.toLowerCase() + " " + choosesex_drop_2.toLowerCase() + "s</b> in LGAs in <b>" + statetype_drop_2  + "</b> where <b>population increased</b>." ;      
                    }
                    else if (choosePopulationChange_drop.equals("Decrease")){
                        html = html + "<b>" + percentageChange + "%</b><br> was the <b>increase</b> in <b>" + choosestatus_drop_2.toLowerCase() + " " + choosesex_drop_2.toLowerCase() + "s</b> in LGAs in <b>" + statetype_drop_2  + "</b> where <b>population decreased</b>." ;      
                    }
                    else{
                        html = html + "<b>" + percentageChange + "%</b><br> was the <b>increase</b> in <b>" + choosestatus_drop_2.toLowerCase() + " " + choosesex_drop_2.toLowerCase() + "s</b> in LGAs in <b>" + statetype_drop_2  + "</b> where <b>population remained the same</b>." ;
                    }
                }
                else{
                    html = html + "There are no LGAs in " + statetype_drop_2 + " where the population has not changed." ;      
                }

                html = html + "<br><br>";

            }   
        }

        html = html +"</div>";

        //----------------------------------------------------------------------------------------------------------------------
        //Part 3 - Graph

        html = html + "   <hr class='hr1' color='black'>";

        html = html + "<h2 id = 'result3'>Trends for Different LGAs Based on how <br> the Number of Homeless / At-risk People Change <br> As The Total Population Changes</h2>";

        //Form for the State
        html = html + "<form action='#result3' method='post'>";

        //State Dropdown
        html = html + "   <div class='form-group1'>";
        html = html + "      <label for='statetype_drop_3' class = 'labels'><b>CHOOSE A STATE FOR THE TRENDS YOU WISH TO SEE:</b></label>";
        html = html + "      <select id='statetype_drop_3' name='statetype_drop_3'>";
        html = html + "         <option>Choose a State*</option>";

        // Gettting the Name of the States from the Database
        for (State stateName : states){
            html = html + "     <option>" + stateName.getStateName() + "</option>";
        }
        html = html + "      </select>";
        html = html + "   </div>";

        //Search Button
        html = html + "<div class = 'button1'>";
        html = html + "<br>";
        html = html + "   <button type='submit' class='button1'>Click here to obtain <br> LGA options</button>";
        html = html + "</div>";

        html = html + "</form>";

        String state = context.formParam("statetype_drop_3");

        html = html + "<div class = 'result3'>";

        if (state != null){
            if (state.equals("Choose a State*")){
                html = html + "<p class = 'stateChosen' id = 'stateChosen'><b>Please choose a valid dropdown option to obtain LGA options.</b></p>";    
            }
            else{
                //html = html + "<br>";
                html = html + "<div class = 'stateChosen' id = 'stateChosen'><h4>State Chosen: " + state + "</h4></div>";
            }
        }
        else{
            html = html + "<h3 class = 'stateChosenStatement'> Please submit the 'Click Here to Obtain LGA options' button to choose a LGA. </h3>"; 
        }

        html = html +"</div>";

        //Form for the LGA option starts
        html = html + "<form action='#result3' method='post'>";
        
        //LGA Dropdown
        if (state != null){
            html = html + "   <div class='form-group2'>";
            html = html + "      <label for='lgabystatetype_drop_2' class = 'labels'><b>CHOOSE A LOCAL GOVERNMENT AREA (LGA):</b></label>";
            html = html + "      <select id='lgabystatetype_drop_2' name='lgabystatetype_drop_2'>";
            html = html + "         <option>Choose a LGA*</option>";

            ArrayList<LGA> lgaByState = jdbc.getLGAbyState(state);
            for (LGA lgaName : lgaByState){
                html = html + "     <option>" + lgaName.getName16() + "</option>";
            }

            //Continuing the HTML
            html = html + "      </select>";
            html = html + "   </div>";

            html = html + "      </select>";
            html = html + "</div>";
            html = html + "<div class = 'button1'>";
            html = html + "<br>";
            html = html + "   <button type='submit' class='button1'>Search</button>";
            html = html + "</div>";

        }
    
        String lga3 = context.formParam("lgabystatetype_drop_2");

        html = html + "</form>";

        html = html + "<div class = 'result4'>";

        if (lga3 != null ){
            if(lga3.equals("Choose a LGA*") ){
                html = html + "<hr class = 'hr3' color='#1B2355'>";

                html = html + "<p><b>Please choose a valid LGA in the dropdown to obtain the result.</b></p>";
            }
            else{
                html = html + "<hr class = 'hr3' color='#1B2355'>";

                int hompop2016 = jdbc.homelessPopulation(lga3,2016, "homeless");
                int hompop2018 = jdbc.homelessPopulation(lga3,2018, "homeless");

                int topop2016 = jdbc.totalPopulationByYearByLGA(lga3, 2016);
                int topop2018 = jdbc.totalPopulationByYearByLGA(lga3, 2018);

                ArrayList<State> StateName = jdbc.getStatebyLGA(lga3);

                html = html + "<p>The LGAs chosen are: " + lga3 + "</p>";
                
                for (State i : StateName){
                    html = html + "<p>The State chosen is: " + i.getStateName();
                }

                html = html + "<div class = 'graph'>";

                html = html + "<script src='https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.5.0/Chart.min.js'> </script>";

                html = html + """
                    <canvas id='myChart' style='width:100%; max-width:8`00px'></canvas>
  
                    <script>
                """;

                html = html + "var xValues = ['%s','%s'];".formatted("2016","2018");
            
                html = html + """
                    new Chart('myChart', {
                    type: 'line',
                    data: {
                        labels: xValues,
                        datasets: [{ 
                """;
  
                html = html + "data: [%d, %d],".formatted(hompop2016,hompop2018);
  
                html = html + """          
                        borderColor: 'green',
                        label: 'Homeless Population',
                        yAxisID: 'Homeless Population',
                        fill: false
                    }, { 
                """;

                html = html + "data: [%d, %d],".formatted(topop2016,topop2018);

                html = html + """          
                        borderColor: 'red',
                        label: 'Total Population',
                        yAxisID: 'Total Population',
                        fill: false
                    }]
                    },
                    options: {
                        display: true,
                        scales: {
                            yAxes: [{
                                scaleLabel: {
                                    display: true,
                                    labelString: 'Homeless Population'
                                },
                                id: 'Homeless Population',
                                type: 'linear',
                                position: 'left',
                            }, {
                                scaleLabel: {
                                    display: true,
                                    labelString: 'Total Population'
                                },
                                id: 'Total Population',
                                type: 'linear',
                                position: 'right',
                            }]
                        }
                    }
                    });
                    </script>
                """;

                html = html + "</div>";
            }
        }
        html = html + "</div>";

        //----------------------------------------------------------------------------------------------------------------------
        //Part 4 - Other Data

        html = html + "   <hr class='hr1' color='black'>";

        //html = html + "<div class = 'result'>";

        html = html + """
            <h2>Other Data You Might Want <br> To Have A Look At</h2>

            <button class='accordion'>The change in homelessness in the LGA with the largest change in total population?</button>

                <div class='panel'>
            """;

            ArrayList<LGAPopulation> TotalPopulation = jdbc.LargestChangeTotalPopulation();
            String lgaName = "";
            int ChangeTotalPopulation = 0;

            for (LGAPopulation p : TotalPopulation){
                lgaName = p.getName();
                ChangeTotalPopulation = p.getPopulation();
            }

            int HomelessnessChange = 0;

            ArrayList<LGAPopulation> ChangeHomelessness = jdbc.ChangeHomelessness(lgaName);
            for (LGAPopulation p : ChangeHomelessness){
                HomelessnessChange = p.getPopulation();
            }

            html = html + "<p> The LGA with the <b>largest change in population</b> from 2016 to 2018 is: " + lgaName +  "<br> with a total change of <b>" + ChangeTotalPopulation + "</b>. </p>";
            html = html + "<p> The <b>change in homelessness</b> from 2016 to 2018 in: " + lgaName +  "<br> is: <b>" + HomelessnessChange + "</b>. </p>";

            html = html + """
                </div>
            
            <button class='accordion'>The LGA with the largest increase of homeless women?</button>

                <div class='panel'>
            """;

            ArrayList<LGAPopulation> IncreaseHomelessFemale = jdbc.IncreaseHomelessWomen();
            String lgaName2 = "";
            int IncreaseFemales = 0;

            for (LGAPopulation p : IncreaseHomelessFemale){
                lgaName2 = p.getName();
                IncreaseFemales = p.getPopulation();
            }

            html = html + "<p> The LGA with the <b>largest increase of Homeless Women</b> from 2016 to 2018 is: " + lgaName2 +  "<br> with a total increase of <b>" + IncreaseFemales + "</b> homeless females. </p>";

            html = html + """
                </div>

            <button class='accordion'>The LGA with the largest decrease of homeless children?</button>
                
                <div class='panel'>
            """;

            ArrayList<LGAPopulation> DecreaseHomelessChildren = jdbc.DecreaseHomelessChildren();
            String lgaName3 = "";
            int DecreaseChildren = 0;

            for (LGAPopulation p : DecreaseHomelessChildren){
                lgaName3 = p.getName();
                DecreaseChildren = p.getPopulation();
            }

            html = html + "<p> The LGA with the <b>largest decrease of Homeless Children</b> from 2016 to 2018 is: " + lgaName3 +  "<br> with a total decrease of <b>" + DecreaseChildren + "</b> homeless children. </p>";

            html = html + """
                </div>

            <button class='accordion'>The LGA with the largest shift of people from being 'at risk' to being homeless?</button>
            
                <div class='panel'>
            """;

            ArrayList<LGAPopulation> DecreaseAtRiskPeople = jdbc.DecreaseAtRiskPeople();
            String lgaName4 = "";
            int DecreasePeople = 0;

            for (LGAPopulation p : DecreaseAtRiskPeople){
                lgaName4 = p.getName();
                DecreasePeople = p.getPopulation();
            }

            html = html + "<p> The LGA with the <b>largest decrease of At-Risk people</b> from 2016 to 2018 is: " + lgaName4 +  "<br> with a total decrease of <b>" + DecreasePeople + "</b> at-risk people. </p>";

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
                <td><a class='page4' href='page5.html'><b>Comparing Homelessness <br> with Other Factors</b></a></td>
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
}
