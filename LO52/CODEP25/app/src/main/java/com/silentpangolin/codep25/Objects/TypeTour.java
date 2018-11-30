package com.silentpangolin.codep25.Objects;

public class TypeTour {
    private int id_typetour;
    private String initials_typetour;
    private String name_typetour;


    public TypeTour(){}

    public TypeTour(int id_typetour, String initials_typetour, String name_typetour) {
        this.id_typetour = id_typetour;
        this.initials_typetour = initials_typetour;
        this.name_typetour = name_typetour;
    }

    public int getId_typetour() { return id_typetour; }

    public String getName_typetour() { return name_typetour; }

    public String getInitials_typetour() { return initials_typetour; }

    public void setId_typetour(int id_typetour) { this.id_typetour = id_typetour; }

    public void setName_typetour(String name_typetour) { this.name_typetour = name_typetour; }

    public void setInitials_typetour(String initials_typetour) { this.initials_typetour = initials_typetour; }
}
