package com.lo52.codep25.models;

import io.realm.RealmObject;

public class ParticipantCourse extends RealmObject {
    private Participant participant;
    private Course course;

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
