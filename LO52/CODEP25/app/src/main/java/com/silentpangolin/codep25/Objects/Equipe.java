package com.silentpangolin.codep25.Objects;

public class Equipe {
    private int id_equ;
    private String name_equ;

    public Equipe(){}

    public Equipe(int id_equ, String name_equ) {
        this.id_equ = id_equ;
        this.name_equ = name_equ;
    }

    public int getId_equ() { return id_equ; }

    public String getName_equ() { return name_equ; }


    public void setId_equ(int id_equ) { this.id_equ = id_equ; }

    public void setName_equ(String name_equ) { this.name_equ = name_equ; }
}
