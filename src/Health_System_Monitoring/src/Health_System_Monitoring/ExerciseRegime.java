package Health_System_Monitoring;

import java.sql.Date;

public class ExerciseRegime {
    public int regimeId;
    public int patientId;
    public int gpId;
    public Date startDate;
    public Date endDate;
    public int frequency;

    public ExerciseTrial trial; // only one trial per regime in this version
}
