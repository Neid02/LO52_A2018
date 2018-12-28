package com.utbm.lo52.f1levier.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

@Entity(tableName = "ETAPE",
        indices = {@Index(value = "ID_PARTICIPANT")},
        foreignKeys = @ForeignKey(entity = Participant.class,
        parentColumns = "ID",
        childColumns = "ID_PARTICIPANT",
        onDelete = ForeignKey.CASCADE))
public class Etape {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID_ETAPE")
    private int id;

    @ColumnInfo(name = "ID_PARTICIPANT")
    private int id_participant;

    @ColumnInfo(name = "SPRINT1")
    private float sprint1;

    @ColumnInfo(name = "FRACTION1")
    private float fraction1;

    @ColumnInfo(name = "PIT_STOP")
    private float pitStop;

    @ColumnInfo(name = "SPRINT2")
    private float sprint2;

    @ColumnInfo(name = "FRACTION2")
    private float fraction2;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_participant() {
        return id_participant;
    }

    public void setId_participant(int id_participant) {
        this.id_participant = id_participant;
    }

    public float getSprint1() {
        return sprint1;
    }

    public void setSprint1(float sprint1) {
        this.sprint1 = sprint1;
    }

    public float getFraction1() {
        return fraction1;
    }

    public void setFraction1(float fraction1) {
        this.fraction1 = fraction1;
    }

    public float getPitStop() {
        return pitStop;
    }

    public void setPitStop(float pitStop) {
        this.pitStop = pitStop;
    }

    public float getSprint2() {
        return sprint2;
    }

    public void setSprint2(float sprint2) {
        this.sprint2 = sprint2;
    }

    public float getFraction2() {
        return fraction2;
    }

    public void setFraction2(float fraction2) {
        this.fraction2 = fraction2;
    }

}
