Part 1

Part 2

P2_001.png

Une équipe est possédée par un et un seul entraineur. Un entraineur n'entraine qu'une seule équipe. Chaque équipe peut posséder de 2 à 3 coureurs. 
Les équipes sont formées automatiquement par l'application car il faut respecter la différence maximale de 5 points entre la somme des échelons de chaque membre de l'équipe. L'ordre de passage, contenu dans Coureur, est attribué en même temps que la création des équipes (attribué de manière aléatoire par l'application).
Chaque coureur possède 0 ou N temps. Chaque temps est enregistrer avec un type de tour. Le type de tour correspond à : sprint, pit-stop, ateliers confondus... Il est plus simple de faire comme ceci car si un type de tour vient à être modifié, cela permet simplement de ne modifier qu'un seul enregistrement. De plus il est donc simple de pouvoir ajouter un nouveau type de tour. Les temps moyens et meilleurs temps de chaques joueurs / équipes ne sont pas stockés mais seront calculés automatiquement lors de "l'affichage". Ce calcul "au dernier moment" permet de gagner de la place niveau stockage et évite un nombre de requêtes beaucoup trop important pour un simple ajout de temps. 



Part 3

D'abord il faut clone le dépot contenant les sources
git clone https://android.googlesource.com/kernel/hikey-linaro

puis checkout la branche qui nous intéresse
git checkout -b android-hikey-linaro-4.1 origin/android-hikey-linaro-4.1

Les exports
export ARCH=arm64
export CROSS_COMPILE=/usr/bin/aarch64-linux-gnu-

charger la config
make hikey_defconfig

puis compiler
make -j8

La première compilation prend plusieurs minutes

- Modification de la configuration
À l'aide de kconfig
recherche versatile
CONFIG_ARCH_VERSATILE=y


(screen)
ncf

NFC subsystem support
CONFIG_NFC=y

NFC hardware simulator driver (NFC_SIM)
CONFIG_NFC_SIM=y

Bootup logo (LOGO)
CONFIG_LOGO=y

pour pouvoir désactiver MTP GADGET:
USB functions configurable through configfs (USB_CONFIGFS)
CONFIG_USB_CONFIGFS=y

MTP gadget (USB_CONFIGFS_F_MTP)
CONFIG_USB_CONFIGFS_F_MTP=n

USB LED driver support (USB_LED)
CONFIG_USB_LED=y

TI SOC drivers support (SOC_TI)
CONFIG_SOC_TI=y

Le diff entre les deux config fait 3400 lignes pour seulement 7 changements.
Les images font le même poids à 4ko près

make savedefconfig


PART 4

Les fichiers à inclure lors de la compilation sont : les fichiers source (core.c ...), le header libusbi.h ainsi que les fichiers os/linux_usbfs.c et os/linux_usbfs.h. En effet android est basé sur linux et les fichiers dont le nom commence par darwin correspondent à MacOS.
Ensuite on écrit le fichier Android.mk comme on a appris en TD.
Il faut changer LOCAL_MODULE avec le nom que l'on veut donner à notre lib, ici *libusb*.
On complète les C flags et LD flags avec le contenu du makefile original (-pthread, -lstdc++ et -lc)

Pour corriger l'erreur 1 il faut ajouter aux C flags *-DTIMESPEC_TO_TIMEVAL* cela crée la macro dans le code C.
Pour corriger l'erreur 2 il faut ajouter la ligne *LOCAL_PRELINK_MODULE:= false*

