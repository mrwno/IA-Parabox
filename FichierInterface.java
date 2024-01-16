import java.util.*;
public interface FichierInterface{

    /**
     * Crée un nouveau fichier (si ce dernier n'est pas déjà crée) dans le dossier "lvl/level_update" et lui donne le nom
     * du fichier avec laquel on a crée l'instance.
     * Met dans ce fichier la String level
     */
    public abstract void fileUpdate();

    /**
     * Renvoie la string du niveau actuel;
     * @returns level, la string representant le niveau;
     * @pure
     */
    public abstract String getNiveau();

    /**
     * Renvoie une pile de String qui contient les String de la map a chaque deplacements
     * @returns pile, la pile de String;
     * @pure
    */
    public abstract Stack<String> getPile();

    /**
     * Affiche le niveau dans le terminal et fait appelle à la fonction fileUpdate pour mettre à jour le fichier update.
     * @pure
     */
    public abstract void afficherTerminal();

    /**
     * Modifie les éléments de la pile en fonction des déplacements
     * @param map objet de type map
     * @param d direction
     */
    public abstract void resetTerminal(Direction d, Map map);

    /**
     * Supprime un fichier
     * @returns true si fichier supprimé, false sinon
     */
    public abstract boolean fileDelete();

    /**
     * Prend une map et renvoie la String correspondante
     * @param map objet de type map
     * @returns tmp, String correspondant à la map donnée 
     */
    public abstract String matrixToString(Map map);
}