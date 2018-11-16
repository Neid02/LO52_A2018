package com.example.root.codep25;

import java.util.Date;

public class Stage {



    private String pseudo;
    private String text;

    public Stage( String pseudo, String text) {
        this.pseudo = pseudo;
        this.text = text;
    }


    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
