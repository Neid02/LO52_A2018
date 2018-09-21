Part 1

Part 2

P2_001.png

Une équipe est possédée par un et un seul entraineur. Un entraineur n'entraine qu'une seule équipe. Chaque équipe peut posséder de 2 à 3 coureurs. 
Les équipes sont formées automatiquement par l'application car il faut respecter la différence maximale de 5 points entre la somme des échelons de chaque membre de l'équipe. L'ordre de passage, contenu dans Coureur, est attribué en même temps que la création des équipes (attribué de manière aléatoire par l'application).
Chaque coureur possède 0 ou N temps. Chaque temps est enregistrer avec un type de tour. Le type de tour correspond à : sprint, pit-stop, ateliers confondus... Il est plus simple de faire comme ceci car si un type de tour vient à être modifié, cela permet simplement de ne modifier qu'un seul enregistrement. De plus il est donc simple de pouvoir ajouter un nouveau type de tour. Les temps moyens et meilleurs temps de chaques joueurs / équipes ne sont pas stockés mais seront calculés automatiquement lors de "l'affichage". Ce calcul "au dernier moment" permet de gagner de la place niveau stockage et évite un nombre de requêtes beaucoup trop important pour un simple ajout de temps. 
