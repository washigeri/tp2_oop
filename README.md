# Pigeon Square Java - SDL
### El Ouerghi Malek

## Pré-requis :
* Windows
* Java 8 en 32 Bits (ne fonctionne pas en 64 bits à cause de SDL)
* Il faut ajouter les librairies contenues dans le dossier lib/
    1. Pour SDLJava : sdljava.jar
    2. Pour Junit: junit-jupiter-api-5.0.0.jar et opentest4j-1.0.0.jar
* Il suffit ensuite de lancer le main (dans la classe Main)
    
## Fonctionnement :
* Clic gauche ou clic droit dans la zone de simulation : ajout d'une nourriture
* La nourriture devient périmée au bout d'un certain temps
* La nourriture périmée disparait de la simulation après un moment
* Pour quitter la simulation : touche échap ou alors fermer la fenêtre
* Il est possible de changer la résolution de la fenêtre dans le main ainsi que le nombre de pigeons 

## Code couleur:
* Rouge : Pigeon
* Blanc : Marcheur (qui vient perturber les pigeons de temps en temps)
* Jaune : Nourriture bonne à manger
* Bleu : Nourriture périmée
       