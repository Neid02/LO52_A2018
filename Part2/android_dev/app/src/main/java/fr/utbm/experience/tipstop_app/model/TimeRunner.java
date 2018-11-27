package fr.utbm.experience.tipstop_app.model;

import java.sql.Time;

public class TimeRunner {

    private  int Id;
    private int t1_Sprint;
    private int t1_Fract;
    private int t1_PitStop;
    private int t2_Sprint;
    private int t2_Fract;
    private float Moy;
    private int Passage;
    private int r_RunnerMat;

    public TimeRunner(int id,int r_RunnerMat, int t1_Sprint, int t1_Fract, int t1_PitStop, int t2_Sprint, int t2_Fract, float moy, int passage) {
        Id = id;
        this.r_RunnerMat = r_RunnerMat;
        this.t1_Sprint = t1_Sprint;
        this.t1_Fract = t1_Fract;
        this.t1_PitStop = t1_PitStop;
        this.t2_Sprint = t2_Sprint;
        this.t2_Fract = t2_Fract;
        Moy = moy;
        Passage = passage;
    }

    public TimeRunner(int r_RunnerMat, int t1_Sprint, int t1_Fract, int t1_PitStop, int t2_Sprint, int t2_Fract, float moy, int passage) {
        this.r_RunnerMat = r_RunnerMat;
        this.t1_Sprint = t1_Sprint;
        this.t1_Fract = t1_Fract;
        this.t1_PitStop = t1_PitStop;
        this.t2_Sprint = t2_Sprint;
        this.t2_Fract = t2_Fract;
        Moy = moy;
        Passage = passage;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getT1_Sprint() {
        return t1_Sprint;
    }

    public void setT1_Sprint(int t1_Sprint) {
        this.t1_Sprint = t1_Sprint;
    }

    public int getT1_Fract() {
        return t1_Fract;
    }

    public void setT1_Fract(int t1_Fract) {
        this.t1_Fract = t1_Fract;
    }

    public int getT1_PitStop() {
        return t1_PitStop;
    }

    public void setT1_PitStop(int t1_PitStop) {
        this.t1_PitStop = t1_PitStop;
    }

    public int getT2_Sprint() {
        return t2_Sprint;
    }

    public void setT2_Sprint(int t2_Sprint) {
        this.t2_Sprint = t2_Sprint;
    }

    public int getT2_Fract() {
        return t2_Fract;
    }

    public void setT2_Fract(int t2_Fract) {
        this.t2_Fract = t2_Fract;
    }

    public float getMoy() {
        return Moy;
    }

    public void setMoy(float moy) {
        Moy = moy;
    }

    public int getPassage() {
        return Passage;
    }

    public void setPassage(int passage) {
        Passage = passage;
    }

    public int getR_RunnerMat() {
        return r_RunnerMat;
    }

    public void setR_RunnerMat(int r_RunnerMat) {
        this.r_RunnerMat = r_RunnerMat;
    }

    @Override
    public String toString() {
        return "TimeRunner{" +
                "Id=" + Id +
                ", t1_Sprint=" + t1_Sprint +
                ", t1_Fract=" + t1_Fract +
                ", t1_PitStop=" + t1_PitStop +
                ", t2_Sprint=" + t2_Sprint +
                ", t2_Fract=" + t2_Fract +
                ", Moy=" + Moy +
                ", Passage=" + Passage +
                '}';
    }
}
