package fr.utbm.lo52.domain;

public class Participant {

    private long IdRace;
    private long IdRunner;
    private long TimeFirstLapSprint;
    private long TimeSecondLapSprint;
    private long TimeFirstLapSplit;
    private long TimeSecondLapSplit;
    private long TimePitStop;

    public Participant(long idRace, long idRunner, long timeFirstLapSprint, long timeSecondLapSprint, long timeFirstLapSplit, long timeSecondLapSplit, long timePitStop) {
        IdRace = idRace;
        IdRunner = idRunner;
        TimeFirstLapSprint = timeFirstLapSprint;
        TimeSecondLapSprint = timeSecondLapSprint;
        TimeFirstLapSplit = timeFirstLapSplit;
        TimeSecondLapSplit = timeSecondLapSplit;
        TimePitStop = timePitStop;
    }

    public Participant() {

    }

    public long getIdRace() {
        return IdRace;
    }

    public void setIdRace(long idRace) {
        IdRace = idRace;
    }

    public long getIdRunner() {
        return IdRunner;
    }

    public void setIdRunner(long idRunner) {
        IdRunner = idRunner;
    }

    public long getTimeFirstLapSprint() {
        return TimeFirstLapSprint;
    }

    public void setTimeFirstLapSprint(long timeFirstLapSprint) {
        TimeFirstLapSprint = timeFirstLapSprint;
    }

    public long getTimeSecondLapSprint() {
        return TimeSecondLapSprint;
    }

    public void setTimeSecondLapSprint(long timeSecondLapSprint) {
        TimeSecondLapSprint = timeSecondLapSprint;
    }

    public long getTimeFirstLapSplit() {
        return TimeFirstLapSplit;
    }

    public void setTimeFirstLapSplit(long timeFirstLapSplit) {
        TimeFirstLapSplit = timeFirstLapSplit;
    }

    public long getTimeSecondLapSplit() {
        return TimeSecondLapSplit;
    }

    public void setTimeSecondLapSplit(long timeSecondLapSplit) {
        TimeSecondLapSplit = timeSecondLapSplit;
    }

    public long getTimePitStop() {
        return TimePitStop;
    }

    public void setTimePitStop(long timePitStop) {
        TimePitStop = timePitStop;
    }
}
