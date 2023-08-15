package app;

import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class for Managing the JDBC Connection to a SQLLite Database.
 * Allows SQL queries to be used with the SQLLite Databse in Java.
 *
 * @author Timothy Wiley, 2022. email: timothy.wiley@rmit.edu.au
 * @author Santha Sumanasekara, 2021. email: santha.sumanasekara@rmit.edu.au
 */
public class JDBCConnection {

    // Name of database file (contained in database folder)
    //private static final String DATABASE = "jdbc:sqlite:database/ctg.db";

    // If you are using the Homelessness data set replace this with the below
    private static final String DATABASE = "jdbc:sqlite:database/homelessness.db";

    /**
     * This creates a JDBC Object so we can keep talking to the database
     */
    public JDBCConnection() {
        System.out.println("Created JDBC Connection Object");
    }

    /**
     * Get all of the LGAs in the database.
     * @return
     *    Returns an ArrayList of LGA objects
     */
    public ArrayList<LGA> getLGAs(int year) {
        // Create the ArrayList of LGA objects to return
        ArrayList<LGA> lgas = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT lga_code, lga_name FROM LGA where year =" + year + " order by lga_name;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code16     = results.getInt("lga_code");
                String name16  = results.getString("lga_name");

                // Create a LGA Object
                LGA lga = new LGA(code16, name16);

                // Add the lga object to the array
                lgas.add(lga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga
        return lgas;
    }

    //Get all of the states from the database
    public ArrayList<State> getStates() {
        // Create the ArrayList of State objects to return
        ArrayList<State> state = new ArrayList<State>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM StateName";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code     = results.getInt("state_code");
                String name  = results.getString("name");

                // Create a State Object
                State StateValue = new State(code, name);

                // Add the state object to the array
                state.add(StateValue);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the states
        return state;
    }

    //Get the list of LGAs of a particular state
    public ArrayList<LGA> getLGAbyState(String NameState) {
        // Create the ArrayList of LGAByState objects to return
        ArrayList<LGA> LGAbyState = new ArrayList<LGA>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA JOIN StateName on LGA.state_code = StateName.state_code where StateName.name = '" + NameState + "' and year = 2018";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int state_code     = results.getInt("state_code");
                String name  = results.getString("lga_name");

                // Create a LGA Object
                LGA NameByStateLga = new LGA(state_code, name);

                // Add the lga object to the array
                LGAbyState.add(NameByStateLga);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lgas of a particular state
        return LGAbyState;
    }

    //Count the total number of LGAs 
    public int countLGAs() {
        // Create a variable to return
        int count = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT COUNT(lga_code) AS Count FROM LGA where year = 2018";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                //Lookup the columns we need
                count = results.getInt( "Count");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the count
        return count;
    }

    //Sum of the total population of Australia
    public int totalPopulation() {
        // Create a variable to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT SUM(population) AS Sum FROM LGA where year = 2018";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                sum = results.getInt( "Sum");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the total population
        return sum;
    }

    //Getting the State of a LGA
    public ArrayList<State> getStatebyLGA(String LGAName) {
        // Create the ArrayList of State objects to return
        ArrayList<State> stateName = new ArrayList<State>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM StateName JOIN LGA ON StateName.state_code = LGA.state_code where year = 2018 and lga_name = '" + LGAName + "' and year = 2018";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code     = results.getInt("state_code");
                String name  = results.getString("name");

                // Create a LGA Object
                State StateValue = new State(code, name);

                // Add the lga object to the array
                stateName.add(StateValue);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the state
        return stateName;
    }

    //Get the number of homeless people per selected category
    public ArrayList<HomelessGroup> getNumPeople(String lga_name, int year, String sex, String status, String age_group) {
        // Create the ArrayList of HomelessGroup objects to return
        ArrayList<HomelessGroup> numPeople = new ArrayList<HomelessGroup>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM HomelessGroup JOIN LGA ON HomelessGroup.lga_code = LGA.lga_code WHERE lga_name = '" + lga_name + "' AND HomelessGroup.year = " + year + " AND LGA.year = " + year + " AND status = '" + status + "' AND sex = '" + sex + "' AND age_group = '" + age_group + "'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int code                 = results.getInt("lga_code");
                int year_extracted       = results.getInt("year");
                String status_extracted  = results.getString("status");
                String sex_extracted     = results.getString("sex");
                String age_extracted     = results.getString("age_group");
                int count_extracted      = results.getInt("count");

                // Create a HomelessGroup Object
                HomelessGroup homelessPeople = new HomelessGroup(code, year_extracted, status_extracted, sex_extracted, age_extracted, count_extracted);

                // Add the HomelessGroup object to the array
                numPeople.add(homelessPeople);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        //return the HomelessGroup array
        return numPeople;
    }

    //To get the properties of a LGA
    public ArrayList<LGAProperties> getLGAProperties(String NameLGA) {
        // Create the ArrayList of LGAProperties objects to return
        ArrayList<LGAProperties> LGAProperties = new ArrayList<LGAProperties>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM LGA where lga_name = '" + NameLGA + "' and year = 2018";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String LGAtype  = results.getString("lga_type");
                double LGAarea = results.getDouble("area_sqkm");
                int population    = results.getInt("population");
                int Populationyear = results.getInt("year");

                // Create a LGAProperties Object
                LGAProperties properties = new LGAProperties(LGAtype, LGAarea, population, Populationyear);

                // Add the object to the array
                LGAProperties.add(properties);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the lga properties
        return LGAProperties;
    }

    //To find the sum of homeless & at-risk people by lga
    public int SumOfCountByLGA(String nameLGA, int year) {
        // Create the variable to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT SUM(count) AS Sum FROM HomelessGroup JOIN LGA on HomelessGroup.lga_code = LGA.lga_code WHERE LGA.lga_name= '" + nameLGA + "' AND LGA.year = " + year + " AND HomelessGroup.year = " + year + " ";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                sum = results.getInt( "Sum");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the sum
        return sum;
    }

    //To find the sum of homeless & at-risk people by state
    public int SumOfCountByState(String nameState, int year) {
        // Create the variable to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT SUM(count) AS Sum FROM HomelessGroup JOIN LGA on HomelessGroup.lga_code = LGA.lga_code JOIN StateName on LGA.state_code = StateName.state_code WHERE StateName.name = '" + nameState + "' AND LGA.year = " + year + " AND HomelessGroup.year = " + year + " ";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                sum = results.getInt( "Sum");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the sum
        return sum;
    }
    
    //To find the sum of homeless & at-risk people by country
    public int SumOfCountByCountry(int year) {
        // Create the variable to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT SUM(count) AS Sum FROM HomelessGroup WHERE year = " + year + " ";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                sum = results.getInt( "Sum");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the sum
        return sum;
    }

    //To get the first persona from the database
    public ArrayList<persona> getpersona1() {
        //Create the ArrayList of Persona objects to return
        ArrayList<persona> persona = new ArrayList<persona>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            //The query
            String query = "SELECT * FROM persona where persona_name ='Connor Choi' ";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name  = results.getString("persona_name");
                int Age     = results.getInt("persona_age");
                String Desc    = results.getString("Persona_description");

                //Make a persona object
                persona per = new persona(name,Age,Desc);

                //Add the object to the arraylist
                persona.add(per);

            }

            // Close the statement because we are done with it
            statement.close();
        }
        catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        //return the persona
        return persona;
    }

    //To get the first persona from the database
    public ArrayList<persona> getpersona2() {
        //Create the ArrayList of Persona objects to return
        ArrayList<persona> persona = new ArrayList<persona>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            //The query
            String query = "SELECT * FROM persona where persona_name = 'Andrew Simmons'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name  = results.getString("persona_name");
                int Age     = results.getInt("persona_age");
                String Desc    = results.getString("Persona_description");

                //Make a persona object
                persona per = new persona(name,Age,Desc);

                //Add the object to the arraylist
                persona.add(per);
            }

            // Close the statement because we are done with it
            statement.close();
        }
        catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        //Return the persona
        return persona;
    }
    
    public ArrayList<top5sex> top5_sex(String NameLGA) {
        // Create the ArrayList of LGA objects to return
        ArrayList<top5sex> top5 = new ArrayList<top5sex>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select sex , age_group, count from homelessgroup join LGA on Lga.lga_code = homelessgroup.lga_code where lga.lga_name= '" + NameLGA + "'  and homelessgroup.year = 2018 and lga.year = 2018 order by sex desc limit 5";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);
            //int i = 0;
            while (results.next()) {
                // Lookup the columns we need
                //public HomelessGroup(int lga_code, int year, String status, String sex, String age_group, int count){
                String age  = results.getString("age_group");
                int count    = results.getInt("count");
                String sex    = results.getString("sex");

                top5sex per = new top5sex(age,sex,count);
                top5.add(per);
            }

            statement.close();
        }
        catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        return top5;
    }


    public ArrayList<top5age> top5_age(String NameLGA) {
        // Create the ArrayList of LGA objects to return
        ArrayList<top5age> top5 = new ArrayList<top5age>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select sex , age_group, count from homelessgroup join LGA on Lga.lga_code = homelessgroup.lga_code where lga.lga_name = '" + NameLGA + "'  and homelessgroup.year = 2018 and lga.year = 2018 order by sex desc limit 5";
        
            // Get Result
            ResultSet results = statement.executeQuery(query);
            
            while (results.next()) {
                // Lookup the columns we need
                //public HomelessGroup(int lga_code, int year, String status, String sex, String age_group, int count){
                String age  = results.getString("age_group");
                int count    = results.getInt("count");
                String sex    = results.getString("sex");

                top5age per = new top5age(age, sex, count);
                top5.add(per);
            }
            statement.close();

        }
        catch (SQLException e) {
        // If there is an error, lets just pring the error
        System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                connection.close();
                }
            } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
            }
        }

        return top5;
    }

    //Find the total population of a LGA
    public int totalPopulationByLGA(String NameLGA, int year) {
        // Create the variable to return
        int population = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT population FROM LGA WHERE year = " + year + " AND lga_name = '" + NameLGA + "'";  
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                population = results.getInt( "population");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the population
        return population;
    }

    //Get the Income information per LGA
    public ArrayList<LGAIncome> getLGAIncome(String NameLGA) {
        // Create the ArrayList of Income objects to return
        ArrayList<LGAIncome> Income = new ArrayList<LGAIncome>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT * FROM Income JOIN LGA on Income.lga_code = LGA.lga_code WHERE lga_name = '" + NameLGA + "' AND LGA.year = 2018";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int age                   = results.getInt("median_age");
                int mortgage_repayments   = results.getInt("median_mortgage");
                int rent                  = results.getInt("median_weekly_rent");
                int income                = results.getInt("median_weekly_income");

                // Create a LGAIncome Object
                LGAIncome lgaIncome = new LGAIncome(age, mortgage_repayments, rent, income);

                // Add the object to the array
                Income.add(lgaIncome);
            }

            // Close the statement because we are done with it
            statement.close();
        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return all of the Income information
        return Income;
    }

    //Find the lowest weekly income
    public int lowestWeeklyIncome() {
        // Create the variable to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT sum(count) AS count FROM HomelessGroup JOIN Income ON HomelessGroup.lga_code = Income.lga_code JOIN LGA ON Income.lga_code = LGA.lga_code WHERE LGA.year = 2018 AND HomelessGroup.year = 2018 AND Income.median_weekly_income IN (SELECT MIN(median_weekly_income) FROM Income);";  
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                sum = results.getInt( "count");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the variable
        return sum;
    }

    //Find the highest weekly income
    public int highestWeeklyIncome() {
        // Create the variable to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT sum(count) AS count FROM HomelessGroup JOIN Income ON HomelessGroup.lga_code = Income.lga_code JOIN LGA ON Income.lga_code = LGA.lga_code WHERE LGA.year = 2018 AND HomelessGroup.year = 2018 AND Income.median_weekly_income IN (SELECT MAX(median_weekly_income) FROM Income);";  
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                sum = results.getInt( "count");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the variable
        return sum;
    }

    //Find the lowest median age
    public int lowestMedianAge() {
        // Create the variable to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT sum(count) AS count FROM HomelessGroup JOIN Income ON HomelessGroup.lga_code = Income.lga_code JOIN LGA ON Income.lga_code = LGA.lga_code WHERE LGA.year = 2018 AND HomelessGroup.year = 2018 AND Income.median_age IN (SELECT MIN(median_age) FROM Income);";  
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                sum = results.getInt( "count");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the variable
        return sum;
    }

    //Find the highest median age
    public int highestMedianAge() {
        // Create the variable to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT sum(count) AS count FROM HomelessGroup JOIN Income ON HomelessGroup.lga_code = Income.lga_code JOIN LGA ON Income.lga_code = LGA.lga_code WHERE LGA.year = 2018 AND HomelessGroup.year = 2018 AND Income.median_age IN (SELECT MAX(median_age) FROM Income);";  
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                sum = results.getInt( "count");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the variable
        return sum;
    }

    //Find the lowest median rent
    public int lowestMedianRent() {
        // Create the variable to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT sum(count) AS count FROM HomelessGroup JOIN Income ON HomelessGroup.lga_code = Income.lga_code JOIN LGA ON Income.lga_code = LGA.lga_code WHERE LGA.year = 2018 AND HomelessGroup.year = 2018 AND Income.median_weekly_rent IN (SELECT MIN(median_weekly_rent) FROM Income);";  
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                sum = results.getInt( "count");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the variable
        return sum;
    }

    //Find the highest median rent
    public int highestMedianRent() {
        // Create the variable to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT sum(count) AS count FROM HomelessGroup JOIN Income ON HomelessGroup.lga_code = Income.lga_code JOIN LGA ON Income.lga_code = LGA.lga_code WHERE LGA.year = 2018 AND HomelessGroup.year = 2018 AND Income.median_weekly_rent IN (SELECT MAX(median_weekly_rent) FROM Income);";  
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                sum = results.getInt( "count");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the variable
        return sum;
    }
    
    //Get the student information from the database
    public ArrayList<student> getstudent1(String num) {
        //Create the arraylist Student to store the objects
        ArrayList<student>student = new ArrayList<student>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            //The query
            String query = "SELECT * FROM student where Student_number = '" + num +"'";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name  = results.getString("Student_name");
                String numb     = results.getString("Student_number");
                String Desc    = results.getString("Student_description");

                //Create a Student object
                student per = new student(name,numb,Desc);

                //Add the object to the arraylist
                student.add(per);
            }
            
            //Close the statement because we are done with it
            statement.close();
        }
        catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        //return the student information
        return student;
    }
    
    //To get the table with all the information for all lgas
    public ArrayList<table> gettable(String h){
        //Create the ArrayList 
        ArrayList<table> table = new ArrayList<table>();

        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            //The query
            String query = "select lga_name , homelessgroup.year, status , sex, age_group, count from homelessgroup join LGA on lga.lga_code = homelessgroup.lga_code where homelessgroup.year = 2018 and lga.year = 2018 and homelessgroup.status = '"+ h +"';";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name   = results.getString("lga_name");
                int year      = results.getInt("year");
                String status = results.getString("status");
                String sex    = results.getString("sex");
                String age    = results.getString("age_group");
                int count     = results.getInt("count");

                //Create the table object
                table per = new table(name,year,status,sex,age,count);

                //add the object to the arraylist
                table.add(per);

            }
            
            //Close the statement because we are done with it
            statement.close();
       }
       
       catch (SQLException e) {
        // If there is an error, lets just pring the error
        System.err.println(e.getMessage());
    } finally {
        // Safety code to cleanup
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }

    //Return the table arraylist
    return table;
    }      

    //Find the highest number of homeless & at-risk people by sex
    public ArrayList<LGAByCount> highestCountBySex(String sex) {
        // Create the ArrayList of LGAByCount objects to return
        ArrayList<LGAByCount> lgaByCount = new ArrayList<LGAByCount>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "WITH CountTemp AS (SELECT lga_name, HomelessGroup.lga_code as lga_code, sex, SUM(count) AS count FROM HomelessGroup JOIN LGA on LGA.lga_code = HomelessGroup.lga_code WHERE HomelessGroup.year = 2018 AND LGA.year = 2018 AND sex = '"+ sex +"' GROUP BY HomelessGroup.lga_code, sex) SELECT lga_name, MAX(count) AS max_count FROM CountTemp;";  
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int count       = results.getInt( "max_count");
                String lga_name = results.getString("lga_name");

                LGAByCount lgacount = new LGAByCount(lga_name, count);

                lgaByCount.add(lgacount);
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the arraylist
        return lgaByCount;
    }

    //Find the lowest number of homeless & at-risk people by sex
    public ArrayList<LGAByCount> lowestCountBySex(String sex) {
        // Create the ArrayList of LGAbyCount objects to return
        ArrayList<LGAByCount> lgaByCount = new ArrayList<LGAByCount>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "WITH CountTemp AS (SELECT lga_name, HomelessGroup.lga_code as lga_code, sex, SUM(count) AS count FROM HomelessGroup JOIN LGA on LGA.lga_code = HomelessGroup.lga_code WHERE HomelessGroup.year = 2018 AND LGA.year = 2018 AND sex = '"+ sex +"' GROUP BY HomelessGroup.lga_code, sex) SELECT lga_name, MIN(count) AS min_count FROM CountTemp;";  
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int count       = results.getInt( "min_count");
                String lga_name = results.getString("lga_name");

                LGAByCount lgacount = new LGAByCount(lga_name, count);

                lgaByCount.add(lgacount);
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the arraylist
        return lgaByCount;
    }

    //Find the number of homeless & at-risk people by income and median age
    public int CountByIncomeAndAge(String stateName, String sex, String status, String operationIncome, int income, String operationAge, int age) {
        // Create the variable to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT sum(Count) AS count FROM HomelessGroup JOIN LGA ON HomelessGroup.lga_code = LGA.lga_code JOIN StateName on StateName.state_code = LGA.state_code JOIN Income ON Income.lga_code = LGA.lga_code WHERE StateName.name = '" + stateName + "'  AND HomelessGroup.sex = '" + sex + "' AND HomelessGroup.status = '" + status + "' AND Income.median_weekly_income " + operationIncome + income + " AND Income.median_age " + operationAge + age + " ";  
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                sum = results.getInt( "count");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the variable
        return sum;
    }

    //Find the ranking of lgas by state
    public ArrayList<toplga> top(String sex,String age,int state, String status) {
        // Create the ArrayList of TopLGA objects to return
        ArrayList<toplga> toplga = new ArrayList<toplga>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "select distinct lga_name, count from homelessgroup join LGA on lga.lga_code = homelessgroup.lga_code join StateName on lga.state_code = statename.state_code where lga.state_code =" + state + " and statename.state_code ="+ state +" and homelessgroup.status = '"+ status +"' and homelessgroup.age_group = '" + age +"' and homelessgroup.sex = '" + sex +"' and homelessgroup.year = 2018 and lga.year = 2018 order by count desc limit 5;";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int count       = results.getInt( "count");
                String lga_name = results.getString("lga_name");

                toplga lgacount = new toplga(lga_name, count);

                toplga.add(lgacount);
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the arraylist
        return toplga;
    }

    //To find the information of the homeless or at-risk people in a particular lga
    public ArrayList<table1> gettable1(String h,String t){
        ArrayList<table1> table1 = new ArrayList<table1>();

        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            String query = "select lga_name , homelessgroup.year, status , sex, age_group, count from homelessgroup join LGA on lga.lga_code = homelessgroup.lga_code where homelessgroup.year = 2018 and lga.year = 2018 and homelessgroup.status = '"+ h +"' and lga.lga_name ='"+ t +"';";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                String name   = results.getString("lga_name");
                int year      = results.getInt("year");
                String status = results.getString("status");
                String sex    = results.getString("sex");
                String age    = results.getString("age_group");
                int count     = results.getInt("count");


                table1 per = new table1(name,year,status,sex,age,count);

                table1.add(per);
            }
            
            statement.close();

       }
       
       catch (SQLException e) {
        // If there is an error, lets just pring the error
        System.err.println(e.getMessage());
    } finally {
        // Safety code to cleanup
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            // connection close failed.
            System.err.println(e.getMessage());
        }
    }
        //return that table
        return table1;
    }

    //Finding the total population of a particular year by lga
    public int totalPopulationByYearByLGA(String lga, int year) {
        // Create the variable to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "";

            // The Query
            if(year==2016){
                query = "select * from population2016 where lga_name = '"+lga+"';";  
            }
            else{
                query = "select * from population2018 where lga_name = '"+lga+"';";
            }

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            if(year==2016){
                while (results.next()) {
                    // Lookup the columns we need
                    sum = results.getInt( "population2016");
                }
            }
            else{
                while (results.next()) {
                    // Lookup the columns we need
                    sum = results.getInt( "population2018");
                }
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the variable
        return sum;
    }

    //find the homeless population by lga, year and status
    public int homelessPopulation(String lga, int year, String stat) {
        // Create the ArrayList of LGA objects to return
        int sum = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            String query = "";

            // The Query
            if(year==2016){
                query = "select sum(count) as sum from homelesscount2016  where lga_name = '"+lga+"' and status = '"+ stat +"' ;";  
            }
            else{
                 query = "select sum(count) as sum from homelesscount2018  where lga_name = '"+lga+"' and status = '"+ stat +"' ;";
            }

            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                sum = results.getInt( "sum");
            }
        
            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the variable
        return sum;
    }

    //Find the largest change in the total population from 2016 to 2018
    public ArrayList<LGAPopulation> LargestChangeTotalPopulation() {
        // Create the ArrayList of LGAPopulation objects to return
        ArrayList<LGAPopulation> lgaPopulation = new ArrayList<LGAPopulation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "SELECT Population2018.lga_name, MAX(population2018 - population2016) AS Difference FROM Population2018 JOIN Population2016 ON Population2018.lga_code = Population2016.lga_code";  
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int difference  = results.getInt( "Difference");
                String lga_name = results.getString("lga_name");

                LGAPopulation population = new LGAPopulation(lga_name, difference);

                lgaPopulation.add(population);
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the arraylist
        return lgaPopulation;
    }

    //Finding the change in homelessness from 2016 to 2018 by lga
    public ArrayList<LGAPopulation> ChangeHomelessness(String LGAName) {
        // Create the ArrayList of LGAPopulation objects to return
        ArrayList<LGAPopulation> lgaPopulation = new ArrayList<LGAPopulation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = "WITH temp AS (SELECT HomelessCount2018.lga_name, SUM(HomelessCount2018.count) AS count1, SUM(HomelessCount2016.count) AS count2 FROM HomelessCount2018 JOIN HomelessCount2016 ON HomelessCount2018.lga_code = HomelessCount2016.lga_code AND HomelessCount2018.status = HomelessCount2016.status AND HomelessCount2018.sex = HomelessCount2016.sex AND HomelessCount2018.age_group = HomelessCount2016.age_group WHERE HomelessCount2018.lga_name = '" + LGAName + "') SELECT lga_name, MAX(count1 - count2) AS count FROM temp";
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int count       = results.getInt( "count");
                String lga_name = results.getString("lga_name");

                //LGAByCount lgacount = new LGAByCount(lga_name, count);
                LGAPopulation population = new LGAPopulation(lga_name, count);

                lgaPopulation.add(population);
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the arraylist
        return lgaPopulation;
    }

    //Finding the most increase in homeless women over time
    public ArrayList<LGAPopulation> IncreaseHomelessWomen() {
        // Create the ArrayList of LGAPopulation objects to return
        ArrayList<LGAPopulation> lgaPopulation = new ArrayList<LGAPopulation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = """
                WITH temp AS 
                (SELECT HomelessCount2018.lga_name, SUM(HomelessCount2018.count) AS count1, SUM(HomelessCount2016.count) AS count2 FROM HomelessCount2018 
                JOIN HomelessCount2016 ON HomelessCount2018.lga_code = HomelessCount2016.lga_code AND HomelessCount2018.status = HomelessCount2016.status AND HomelessCount2018.sex = HomelessCount2016.sex AND HomelessCount2018.age_group = HomelessCount2016.age_group 
                WHERE HomelessCount2018.sex = 'f'
                GROUP BY HomelessCount2018.lga_name, HomelessCount2018.sex) 
                SELECT lga_name, MAX(count1 - count2) AS count FROM temp
                    """;
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int count       = results.getInt( "count");
                String lga_name = results.getString("lga_name");

                LGAPopulation population = new LGAPopulation(lga_name, count);

                lgaPopulation.add(population);
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the arraylist
        return lgaPopulation;
    }

    //Finding the most decrease in homeless children over time
    public ArrayList<LGAPopulation> DecreaseHomelessChildren() {
        // Create the ArrayList of LGAPopulation objects to return
        ArrayList<LGAPopulation> lgaPopulation = new ArrayList<LGAPopulation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = """
                WITH temp AS 
                (SELECT HomelessCount2018.lga_name, SUM(HomelessCount2018.count) AS count1, SUM(HomelessCount2016.count) AS count2 FROM HomelessCount2018 
                JOIN HomelessCount2016 ON HomelessCount2018.lga_code = HomelessCount2016.lga_code AND HomelessCount2018.status = HomelessCount2016.status AND HomelessCount2018.sex = HomelessCount2016.sex AND HomelessCount2018.age_group = HomelessCount2016.age_group 
                WHERE HomelessCount2018.age_group IN ('_0_9', '_10_19')
                GROUP BY HomelessCount2018.lga_name) 
                SELECT lga_name, MAX(count2 - count1) AS count FROM temp
                    """;
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int count       = results.getInt( "count");
                String lga_name = results.getString("lga_name");

                LGAPopulation population = new LGAPopulation(lga_name, count);

                lgaPopulation.add(population);
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the arraylist
        return lgaPopulation;
    }

    //Finding the most decrease in the at risk people over time
    public ArrayList<LGAPopulation> DecreaseAtRiskPeople() {
        // Create the ArrayList of LGAPopulation objects to return
        ArrayList<LGAPopulation> lgaPopulation = new ArrayList<LGAPopulation>();

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);

            // The Query
            String query = """
                WITH temp AS 
                (SELECT HomelessCount2018.lga_name, SUM(HomelessCount2018.count) AS count1, SUM(HomelessCount2016.count) AS count2 FROM HomelessCount2018 
                JOIN HomelessCount2016 ON HomelessCount2018.lga_code = HomelessCount2016.lga_code AND HomelessCount2018.status = HomelessCount2016.status AND HomelessCount2018.sex = HomelessCount2016.sex AND HomelessCount2018.age_group = HomelessCount2016.age_group 
                WHERE HomelessCount2018.status = 'at_risk'
                GROUP BY HomelessCount2018.lga_name) 
                SELECT lga_name, MAX(count2 - count1) AS count FROM temp
                    """;
            
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                int count       = results.getInt( "count");
                String lga_name = results.getString("lga_name");

                LGAPopulation population = new LGAPopulation(lga_name, count);

                lgaPopulation.add(population);
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the arraylist
        return lgaPopulation;
    }

    //Finding the homeless people in 2018 by category
    public int HomelessByCategory2018(String state, String operation, String status, String sex) {
        // Create the variable to return
        int count = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            // The Query
             String query = "SELECT SUM(count) AS count FROM Population2018 JOIN Population2016 ON Population2018.lga_code = Population2016.lga_code JOIN HomelessCount2018 ON HomelessCount2018.lga_code = Population2018.lga_code JOIN LGA ON Population2018.lga_code = LGA.lga_code JOIN StateName ON LGA.state_code = StateName.state_code WHERE (population2018 - population2016) " + operation +" AND StateName.name = '"+ state +"' AND status = '" + status + "' AND sex = '" + sex + "'";  
    
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                count = results.getInt( "count");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the count
        return count;
    }

    //Finding the homeless people in 2018 by category
    public int HomelessByCategory2016(String state, String operation, String status, String sex) {
        // Create the variable to return
        int count = 0;

        // Setup the variable for the JDBC connection
        Connection connection = null;

        try {
            // Connect to JDBC data base
            connection = DriverManager.getConnection(DATABASE);

            // Prepare a new SQL Query & Set a timeout
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            
            // The Query
             String query = "SELECT SUM(count) AS count FROM Population2018 JOIN Population2016 ON Population2018.lga_code = Population2016.lga_code JOIN HomelessCount2016 ON HomelessCount2016.lga_code = Population2018.lga_code JOIN LGA ON Population2018.lga_code = LGA.lga_code JOIN StateName ON LGA.state_code = StateName.state_code WHERE (population2018 - population2016) " + operation +" AND StateName.name = '"+ state +"' AND status = '" + status + "' AND sex = '" + sex + "'";  
    
            // Get Result
            ResultSet results = statement.executeQuery(query);

            // Process all of the results
            while (results.next()) {
                // Lookup the columns we need
                count = results.getInt( "count");
            }

            // Close the statement because we are done with it
            statement.close();

        } catch (SQLException e) {
            // If there is an error, lets just pring the error
            System.err.println(e.getMessage());
        } finally {
            // Safety code to cleanup
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                // connection close failed.
                System.err.println(e.getMessage());
            }
        }

        // Finally we return the count
        return count;
    }
    
}


