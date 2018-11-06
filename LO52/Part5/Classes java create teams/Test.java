/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;

/**
 *
 * @author Emilien
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        // initialisation de toutes les variables
        ArrayList<Coureur> all = createList();
        int nbMinTeam = 2, gap = 1, count;
        int average = averageFor3(all);
        Coureur[][] teams = new Coureur[3][all.size() / nbMinTeam];
        boolean[] isInTeam = new boolean[all.size()];
        boolean valid = true;;
        long startTime, stopTime, elapsedTime;

        // depart du "timer"
        startTime = System.currentTimeMillis();

        while (valid) {
            // reinitialise toutes les variables afin de pouvoir tester avec un 
            // nouvel ecart
            count = 0;
            for (int i = 0; i < all.size() / nbMinTeam; ++i) {
                teams[0][i] = null;
                teams[1][i] = null;
                teams[2][i] = null;
            }
            for (int i = 0; i < all.size(); ++i) {
                isInTeam[i] = false;
            }

            // essaie de creer une equipe de 2 puis 3 coureurs dont la somme 
            // des echelons est comprise entre moyenne - gap et moyenne + gap
            for (Coureur c : new ArrayList<>(all)) {
                for (Coureur b : new ArrayList<>(all)) {
                    for (Coureur a : new ArrayList<>(all)) {
                        if ((c.getId_crr() != b.getId_crr()) && (b.getId_crr() != a.getId_crr()) && (c.getId_crr() != a.getId_crr())
                                && !(isInTeam[all.indexOf(c)]) && !(isInTeam[all.indexOf(b)]) && !(isInTeam[all.indexOf(a)])) {
                            if (((c.getEchelon_crr() + b.getEchelon_crr() + a.getEchelon_crr()) < (average + gap))
                                    && ((c.getEchelon_crr() + b.getEchelon_crr() + a.getEchelon_crr()) > (average - gap))) {
                                teams[0][count] = c;
                                teams[1][count] = b;
                                teams[2][count] = a;
                                isInTeam[all.indexOf(c)] = true;
                                isInTeam[all.indexOf(b)] = true;
                                isInTeam[all.indexOf(a)] = true;
                                ++count;
                            }
                        }
                    }
                    if ((c.getId_crr() != b.getId_crr()) && !(isInTeam[all.indexOf(c)]) && !(isInTeam[all.indexOf(b)])) {
                        if (((c.getEchelon_crr() + b.getEchelon_crr()) < (average + gap)) && ((c.getEchelon_crr() + b.getEchelon_crr()) > (average - gap))) {
                            teams[0][count] = c;
                            teams[1][count] = b;
                            isInTeam[all.indexOf(c)] = true;
                            isInTeam[all.indexOf(b)] = true;
                            ++count;
                        }
                    }
                }
            }

            // verifie si tous les coureurs ont bien une equipe
            valid = false;
            for (int i = 0; i < all.size(); ++i) {
                if (!(isInTeam[i])) {
                    valid = true;
                }
            }
            ++gap;
        }

        // affichage de l'ecart min / max
        System.out.println("Real gap : " + getGap(teams, all.size() / nbMinTeam));

        // affichage du temps d'execution
        stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("Time : " + elapsedTime);

    }

    /*
        creer juste la liste avec les coureurs donnés par le prof
     */
    public static ArrayList<Coureur> createList() {
        ArrayList<Coureur> crrs = new ArrayList<>();

        crrs.add(new Coureur(1, "Personne", "1", 0, 0, 0));
        crrs.add(new Coureur(2, "Personne", "2", 1, 0, 0));
        crrs.add(new Coureur(3, "Personne", "3", 2, 0, 0));
        crrs.add(new Coureur(4, "Personne", "4", 6, 0, 0));
        crrs.add(new Coureur(5, "Personne", "5", 9, 0, 0));
        crrs.add(new Coureur(6, "Personne", "6", 11, 0, 0));
        crrs.add(new Coureur(7, "Personne", "7", 12, 0, 0));
        crrs.add(new Coureur(8, "Personne", "8", 12, 0, 0));
        crrs.add(new Coureur(9, "Personne", "9", 19, 0, 0));
        crrs.add(new Coureur(10, "Personne", "10", 20, 0, 0));
        crrs.add(new Coureur(11, "Personne", "11", 22, 0, 0));
        crrs.add(new Coureur(12, "Personne", "12", 22, 0, 0));
        crrs.add(new Coureur(13, "Personne", "13", 23, 0, 0));
        crrs.add(new Coureur(14, "Personne", "14", 23, 0, 0));
        crrs.add(new Coureur(15, "Personne", "15", 25, 0, 0));
        crrs.add(new Coureur(16, "Personne", "16", 30, 0, 0));
        crrs.add(new Coureur(17, "Personne", "17", 31, 0, 0));
        crrs.add(new Coureur(18, "Personne", "18", 32, 0, 0));
        crrs.add(new Coureur(19, "Personne", "19", 33, 0, 0));
        crrs.add(new Coureur(20, "Personne", "20", 34, 0, 0));
        crrs.add(new Coureur(21, "Personne", "21", 34, 0, 0));
        crrs.add(new Coureur(22, "Personne", "22", 48, 0, 0));
        crrs.add(new Coureur(23, "Personne", "23", 54, 0, 0));
        crrs.add(new Coureur(24, "Personne", "24", 56, 0, 0));
        crrs.add(new Coureur(25, "Personne", "25", 65, 0, 0));
        crrs.add(new Coureur(26, "Personne", "26", 75, 0, 0));
        crrs.add(new Coureur(27, "Personne", "27", 76, 0, 0));
        crrs.add(new Coureur(28, "Personne", "28", 87, 0, 0));
        crrs.add(new Coureur(29, "Personne", "29", 87, 0, 0));
        crrs.add(new Coureur(30, "Personne", "30", 89, 0, 0));
        crrs.add(new Coureur(31, "Personne", "31", 90, 0, 0));
        crrs.add(new Coureur(32, "Personne", "32", 100, 0, 0));

        System.out.println("List Created");

        return crrs;
    }

    /*
        permet d'obtenir la moyenne des echelons
        multiplie par 3 car une equipe peut avoir 3 coureurs
     */
    public static int averageFor3(ArrayList<Coureur> crrs) {
        int total = 0;

        for (Coureur c : crrs) {
            total += c.getEchelon_crr();
        }
        total /= crrs.size();
        total *= 3;
        System.out.println("Average : " + total);
        return total;
    }

    /* 
        determine juste le max et le min de la somme des echelons de chaque team
        afin de pouvoir avoir la différence
     */
    public static int getGap(Coureur teams[][], int size) {
        int min = 0, max = 0;
        if ((teams[0][0] != null) && (teams[1][0] != null)) {
            if (teams[2][0] != null) {
                min = teams[0][0].getEchelon_crr() + teams[1][0].getEchelon_crr() + teams[2][0].getEchelon_crr();
                max = min;
            } else {
                min = teams[0][0].getEchelon_crr() + teams[1][0].getEchelon_crr();
                max = min;
            }
        }
        for (int i = 0; i < size; ++i) {
            if ((teams[0][i] != null) && (teams[1][i] != null)) {
                if (teams[2][i] != null) {
                    if (min > (teams[0][i].getEchelon_crr() + teams[1][i].getEchelon_crr() + teams[2][i].getEchelon_crr())) {
                        min = teams[0][i].getEchelon_crr() + teams[1][i].getEchelon_crr() + teams[2][i].getEchelon_crr();
                    }
                    if (max < (teams[0][i].getEchelon_crr() + teams[1][i].getEchelon_crr() + teams[2][i].getEchelon_crr())) {
                        max = teams[0][i].getEchelon_crr() + teams[1][i].getEchelon_crr() + teams[2][i].getEchelon_crr();
                    }
                    System.out.println(i + " : " + teams[0][i].getEchelon_crr()
                            + " + " + teams[1][i].getEchelon_crr()
                            + " + " + teams[2][i].getEchelon_crr()
                            + " = " + (teams[2][i].getEchelon_crr() + teams[1][i].getEchelon_crr() + teams[0][i].getEchelon_crr()));
                } else {
                    if (min > (teams[0][i].getEchelon_crr() + teams[1][i].getEchelon_crr())) {
                        min = teams[0][i].getEchelon_crr() + teams[1][i].getEchelon_crr();
                    }
                    if (max < (teams[0][i].getEchelon_crr() + teams[1][i].getEchelon_crr())) {
                        max = teams[0][i].getEchelon_crr() + teams[1][i].getEchelon_crr();
                    }
                    System.out.println(i + " : " + teams[0][i].getEchelon_crr()
                            + " + " + teams[1][i].getEchelon_crr()
                            + " + x"
                            + " = " + (teams[1][i].getEchelon_crr() + teams[0][i].getEchelon_crr()));
                }
            }
        }
        return max - min;
    }
}
