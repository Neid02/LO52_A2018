package com.utbm.lo52.f1levier.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;

@Entity(tableName = "GROUPE_PARTICIPANT",
        indices = {@Index(value = {"ID_GROUPE", "ID_PARTICIPANT"})},
        primaryKeys={"ID_PARTICIPANT", "ID_GROUPE"},
        foreignKeys = {
        @ForeignKey(entity = Participant.class,
                parentColumns = "ID",
                childColumns = "ID_PARTICIPANT"),
        @ForeignKey(entity = Groupe.class,
                parentColumns = "ID",
                childColumns = "ID_GROUPE")
        }
)
public class GroupeParticipant {

    @ColumnInfo(name = "ID_PARTICIPANT")
    private int id_participant;

    @ColumnInfo(name = "ID_GROUPE")
    private int id_groupe;

    public int getId_participant() {
        return id_participant;
    }

    public void setId_participant(int id_participant) {
        this.id_participant = id_participant;
    }

    public int getId_groupe() {
        return id_groupe;
    }

    public void setId_groupe(int id_groupe) {
        this.id_groupe = id_groupe;
    }

    @Override
    public String toString() {
        return "GroupeParticipant{" +
                "id_participant=" + id_participant +
                ", id_groupe=" + id_groupe +
                '}';
    }
}
