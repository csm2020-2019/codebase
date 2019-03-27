package Health_System_Monitoring;

import java.util.List;

public interface SportsCenterDaoInterface {

    /*
        set sports center (s.c) availability
        @param sports center instance
     */
    void setSportsCenterAvailability(SportsCenter sportsCenter);


    /*
        get sports center (s.c) availability using the ID
        @return boolean - true or false for availability
     */
    boolean getSportsCenterAvailability(int sportsCenterId);


    /*
        get all sports center info
        @return List containing all sports center info
     */
    List<SportsCenter> getAllSportsCenterRecord();

    /*
       add sports center to database
       @param sports center instance of SportsCenter class
       @return true or false
     */
    boolean addSportsCenter(SportsCenter sportsCenter);

}