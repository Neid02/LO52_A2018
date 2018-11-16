package com.silentpangolin.codep25.Objects;

public class TypeTour {
    private int id_typetour;
    private String name_typetour;

    public TypeTour(){}

    public TypeTour(int id_typetour, String name_typetour) {
        this.id_typetour = id_typetour;
        this.name_typetour = name_typetour;
    }

    public int getId_typetour() { return id_typetour; }

    public String getName_typetour() { return name_typetour; }


    public void setId_typetour(int id_typetour) { this.id_typetour = id_typetour; }

    public void setName_typetour(String name_typetour) { this.name_typetour = name_typetour; }
}
