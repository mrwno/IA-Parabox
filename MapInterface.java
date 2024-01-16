import java.util.*;
public interface MapInterface {

    /*
     * Regarde si tous les coordonées d'arriver sont occuper revoi true sinon false
     * 
     * @ensures /for all map[arrive[x][1]][arrive[x][2]] == false;
     * @returns true si le niveau est fini sinon false; 
     */
    public abstract  boolean niveauFini();

    /* Déplace si le deplacement dans la direction spécifié est possible pour le joueur dans la map,sinon false
     * @param d la direction ou on doit verifier  
     * @returns true si il est deplacable ,false sinon
     * @pure
     */
    public abstract boolean estDeplacable(Direction d);

    /* Deplace le bloc si le deplacement dans la direction spécifié est possible pour le bloc de coordonnées x et y dans la map,sinon false
     * @param d la direction ou on doit verifier  
     * @param y ligne de la matrice
     * @param x colonne de la matrice
     * @returns true si il est deplacable ,false sinon
     * @pure
     */
    public abstract boolean estDeplacableBloc(Direction d, int x, int y);

    /* Deplace le bloc monde si le deplacement dans la direction spécifié est possible pour le bloc de coordonnées x et y dans la map,, fait appel a sortirDuMonde() si le joueur peut entrer dans le monde et renvoie true, sinon false
     * @param d la direction ou on doit verifier  
     * @param y ligne de la matrice
     * @param x colonne de la matrice
     * @returns true si le joueur peut entrer dans le bloc monde,, deplace le bloc si possible, false sinon
     * @pure
     */
    public abstract boolean estDeplacableBlocMonde(Direction d, int x, int y);

    /* Déplace l'Objet de coordonées x et y dans la direction spécifié, dans les map booléen et TypeObjet
     * @requires estDeplacable() == true 
     * @param d la direction ou on doit a aller 
     * @param y ligne de la matrice
     * @param x colonne de la matrice
     */
    public abstract boolean deplacer(Direction d, int x, int y);

    /**
     * Renvoie la matrice de booléens
     * @returns la matrice à booléens
     * @pure 
     */
    public abstract Matrix<Boolean> getMapBool();

    /**
     * Renvoie la matrice de TypeObjet
     * @returns la matrice à TypeObjet
     * @pure 
     */
    public abstract Matrix<TypeObjet> getMapType();

    /**
     * Renvoie les coordonnées du joueur
     * @returns les coordonnées du joueur
     * @pure 
     */
    public abstract int[] getJoueurCoord();
    

    /**
     * Renvoie les coordonnées des points d'arrivé
     * @returns les coordonnées des points d'arrivé
     * @pure 
     */
    public abstract int[][] getArrive();

    /**
     * Renvoie la Map récursive, si elle existe, associé au bloc monde de la map
     * @returns objetRec, la map recursive
     * @pure
     */
    public abstract RecMap getObjetRec();

    /**
     * Renvoie la pile contenant la map à chacuns de ces déplacements
     * @returns pile, la pile de matrice d'enum typeObjet
     * @pure
     */
    public abstract Stack<Matrix<TypeObjet>>getPile();

    /**
     * Modifie mapType en se servant d'une mapType donnée en parametre comme modele
     * fait appel a refreshMapBool et refreshCoordJoueur;
     * @param map matrice de typeObjet
     */
    public abstract void modifierMapType(Matrix<TypeObjet> map);

    /**
     * Modifie pile en se servant d'une pile donnée en parametre comme modele;
     * @param pile pile de matrice de type objet
     */
    public abstract void modifierPile(Stack<Matrix<TypeObjet>> pile);


    /**
     * Affiche la matrice de typeObjet
     * @pure
     */
    public abstract void afficherMatEnum();

    /**
     * Modifie les coordonnées du joueur en fonction d'une direction
     * @param d direction
     */
    public abstract void modifierCoord(Direction d);

    /**
     * Modifie les coordonnées du bloc monde en fonction d'une direction
     * @param d direction
     */
    public abstract void modifierCoordMonde(Direction d);
    
    /**
     * Depile pour annuler un déplacement effectué
     */
    public abstract void retourArriere();

    /**
     * Réactualise la map de booleens en fonction de la map de typeObjet
     */
    public abstract void refreshMapBool();

    /**
     * Réactualise les coordonnées du joueur en fonction de la map de typeObjet
     */
    public abstract void refreshCoordJoueur();

    /**
     * Permet de faire sortir le joueur du monde via un bloc monde
     * @param d direction
     * @param y ligne de la matrice
     * @param x colonne de la matrice
     * @returns true si le joueur a pu etre sortie, false si la direction n'est pas haut, bas, gauche ou droite
     */
    public abstract boolean sortirDuMonde(Direction d, int x, int y);

    /**
     * Permet de faire apparaitre le joueur dans le monde recursif
     * @param d direction
     * @param l ligne de la matrice
     * @param c colonne de la matrice
     */
    public abstract void apparition(TypeObjet t, int c, int l);

    /**
     * Permet de ramener le joueur à une case de coordonnée (tab[0],tab[1]), utilisée pour la résolution de chemin
     * @param tab coordonées de la case ou l'on doit se rendre
     */
    public abstract void modifierCoordRes(int[] tab);

    /**
     * Permet de déterminer si un côté de la map est disponible
     * afin de savoir si dans le cas ou la map est recursive, le joueur peut
     * entrer de ce côté ou non, utilisée dans le constructeur de RecMap
     * Si la direction est droit, on cherche à savoir si on peut entrer par l'est, 
     * gauche l'ouest, haut le sud et bas le nord
     * @param c colonne de la matrice
     * @param r ligne de la matrice
     * @param d direction
     * @returns true si le un joueur peut entrer par ce côté, false sinon
     * @pure
     */
    public abstract boolean rechercheDispoRec(int r, int c, Direction d);
    
}
