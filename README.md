# IA_Parabox

Projet de jeu réalisé par Ziad ACHACH, Marwane OUARET, Talubna EL MAOULOUD, Brian ABOU KHALIL et Ali BHATTI.

## Télechargement du jeu

Télécharger les fichier du jeu et déplacez-vous dans le dossier à l'aide des commandes ci-dessous :

```
git clone https://gitlab.sorbonne-paris-nord.fr/ia_parabox/ia_parabox
cd ./ia_parabox
```


## Execution du jeu

Une fois dans le répertoire du jeu, à l'aide du makefile executer le jeu à l'aide de la commande ci-dessous :
```
make
```

## Après le jeu

En cas de problèmes de suppression des classes.
Executez la commande ci-dessous afin de supprimer les classes:
```
make clean
```

## Description du jeu

Pourquoi IA Parabox ?

L'IA Midjourney nous a permis de crée l'univers graphique, nous voulions aussi que ChatGPT3 nous crée le game design des niveau mais malheureusement il a failli à sa tache. 

Il s'agit d'un jeu type sokoban de 11 niveau. Les règles sont simple déplacer le joueur à l'aide des flèches, le joueur peut déplacer 1 ou plusieurs blocs à condition que ce dernier ne soient pas bloquer par un mur.
Vous reussisez le niveau lorsque que toutes les cibles sont recouvertes par le joueur ou un bloc.
Comment se déplacer ? Utilisez les flêches analogiques.
Vous avez fait une manoeuvre qui vous a bloqué ? Utilisez Ctrl+Z pour revenir en arrière.
Vous voulez voir le contenu du bloc-monde ? Cliquez dessus
Vous voulez vous déplacer sur une case précise ? Cliquez sur la case.
Vous voulez recommencer le niveau ? Appuyer sur R
