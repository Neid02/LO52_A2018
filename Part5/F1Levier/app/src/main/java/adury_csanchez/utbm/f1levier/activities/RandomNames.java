package adury_csanchez.utbm.f1levier.activities;

import java.util.Random;

public class RandomNames {

    private static final String[] FIRSTNAMES ={
            "Gabriel",
            "Louis",
            "Raphaël",
            "Jules",
            "Adam",
            "Lucas",
            "Léo",
            "Hugo",
            "Arthur",
            "Nathan",
            "Olivia",
            "Amelia",
            "Emily",
            "Isla",
            "Ava",
            "Jessica",
            "Isabella",
            "Lily",
            "Ella",
            "Mia",
            "Sofia",
            "Aino",
            "Eevi",
            "Venla",
            "Emma",
            "Aada",
            "Pihla",
            "Ella",
            "Helmi",
            "Emilia",
            "Liam",
            "Noah",
            "William",
            "James",
            "Logan",
            "Benjamin",
            "Mason",
            "Elijah",
            "Oliver",
            "Jacob",
            "James",
            "John",
            "Robert",
            "Michael",
            "William",
            "David",
            "Richard",
            "Charles",
            "Joseph",
            "Thomas",
            "Santiago",
            "Mateo",
            "Juan",
            "Matías",
            "Nicolás",
            "Benjamín",
            "Pedro",
            "Tomás",
            "Thiago",
            "Santino"
    };

    private static final String[] LASTNAMES = {
            "Smith",
            "Johnson",
            "Williams",
            "Brown",
            "Jones",
            "Miller",
            "Davis",
            "Garcia",
            "Rodriguez",
            "Wilson",
            "Martinez",
            "Anderson",
            "Taylor",
            "Thomas",
            "Hernandez",
            "Moore",
            "Martin",
            "Jackson",
            "Thompson",
            "White",
            "Lopez",
            "Lee",
            "Gonzalez",
            "Harris",
            "Clark ",
            "Lewis ",
            "Robinson",
            "Walker",
            "Perez",
            "Hall"
    };

    private static final String[] COUNTRIES = {
            "États-Unis",
            "Chine",
            "Japon",
            "Allemagne",
            "Royaume-Unis",
            "France",
            "Inde",
            "Italie",
            "Brésil",
            "Canada",
            "Corée",
            "Australie",
            "Russie",
            "Espagne",
            "Mexique",
            "Indonésie",
            "Turquie",
            "Pays-Bas",
            "Suisse",
            "Arabie saoudite",
            "Argentine",
            "Suède",
            "Pologne",
            "Belgique",
            "Iran",
            "Thaïlande",
            "Nigeria",
            "Autriche",
            "Norvège",
            "Hong Kong",
            "Danemark",
            "Philippines",
            "Irlande",
            "Singapour",
            "Malaisie",
            "Venezuela",
            "Colombie",
            "Egype",
            "Chili",
            "Finlande",
            "Portugal"
    };

    public static String getRandomFirstName()
    {
        int idx = new Random().nextInt(FIRSTNAMES.length);
        return (FIRSTNAMES[idx]);
    }

    public static String getRandomLastName()
    {
        int idx = new Random().nextInt(LASTNAMES.length);
        return (LASTNAMES[idx]);
    }

    public static String getRandomCountry()
    {
        int idx = new Random().nextInt(COUNTRIES.length);
        return (COUNTRIES[idx]);
    }
}
