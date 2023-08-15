package app;

//import java.util.ArrayList;

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
public class PageIndex implements Handler {

    // URL of this page relative to http://localhost:7001/
    public static final String URL = "/";

    @Override
    public void handle(Context context) throws Exception {
        // Create a simple HTML webpage in a String
        String html = "<html>";

        // Add some Header information
        html = html + "<head>" + 
               "<title>Homepage</title>";

        // Add some CSS (external file)
        html = html + "<link rel='stylesheet' type='text/css' href='homepage.css' />";
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

        // The 'Do you know' section - the three facts
        html = html + """
            <div class='header'>
                <h1>
                    DO YOU KNOW?
                </h1>
                <ul>
                    <li>About <em>254,855</em> people are experiencing homelessness <br> or are at-risk of being homeless in Australia, as of 2018. 
                    </li>
                    <li>The <em> most </em> number of homeless and at-risk people in <br> Australia, are <em>between ages 20 - 29</em>.
                    </li>
                    <li><em>Victoria</em> has the <em>highest</em> number of homeless and <br> at-risk people, despite having the <em>third highest median <br> household weekly income</em> (2018).
                    </li>
                </ul>
                <br>
                <br> 
            </div>
        """;

        // Add Div for page Content
        html = html + "<div class='content'>";

        //The mission statement and link to the mission page
        html = html + """
                <h1><b>OUR MISSION STATEMENT</b></h1>
                <p>'Australian Homelessness' aims to create awareness 
                and provide data about the situation of homelessness in Australia. </p>
                <a href='mission.html'>Read Our Full Mission Statement Here</a>
                <br>
                <br>
        """;

        //Calculating the Number of LGAs and the Total Population from the database
        JDBCConnection jdbc = new JDBCConnection();
        int countOfLGA = jdbc.countLGAs();
        int sumPopulation = jdbc.totalPopulation();

        //Adding the Total Number of LGAs and Total Population
        html = html + "<div class = 'totalLGA'> <h2> As of 2018, there are a total <br> of <em>" + countOfLGA + "</em> LGAs within <br> Australia. </h2> </div>";
        html = html + "<div class = 'totalPopulation'> <h2> The total population of <br> Australia in 2018 was <br> <em>" + sumPopulation + "</em>. </h2> </div>";

        // Close Content div
        html = html + "</div>";
        
        //Find out More section
        html = html + """
            <div class = 'findOutMore'>
                <br>
                <h2> FIND OUT MORE </h2>
                <table>
                <tr>
                <td><a class='page1' href='page3.html'><b>   Ranking and List <br> of LGAs  </b></a></td>
                <td><a class='page2' href='page4.html'><b>Number of Homeless <br> People in Particular Areas</b></a></td>
                </tr>
                <tr>
                <td class='blankrow' colspan = '2'></td>
                </tr>
                <tr>
                <td><a class='page3' href='page5.html'><b>Comparing Homelessness <br> with Other Factors</b></a></td>
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
}
