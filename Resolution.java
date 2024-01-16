import java.util.*;

public class Resolution{
    private Stack<int[]> pileCoord;
    private Set<int[]> chemin;
    private Map map;
    private int[] depart; //Coordonées de départ pour la classe.
    private int[] parcours; //Parcours du chemin (intermédiaire entre depart et arrivee).
    private int[] arrivee; //Coordoonées d'arrivée pour la classe.
    private int[] copy;
    private int[] joueur;
    private ArrayList liste;

    public Resolution (int[] joueurCoord, int[] tab,Map map){ 
        this.depart = new int[2];
        this.arrivee = new int[2];
        this.parcours = new int[2];
        this.joueur = new int[2];
        this.copy = new int[2];
        Fichier file = new Fichier("./lvl/level_start/niveau1.txt");
        this.map = new Map(file.getNiveau());
        this.pileCoord = new Stack<int[]>();
        this.chemin = new HashSet<int[]>(); 
        this.liste = new ArrayList<int[]>();
        
        for(int i = 0; i < 2; i++){
            this.parcours[i] = joueurCoord[i];
            this.arrivee[i] = tab[i];
            this.joueur[i] = joueurCoord[i];
            this.depart[i] = joueurCoord[i];
        }
    }

    public Set<int[]> getChemin(){
        return this.chemin;
    }
    public int[] getDepart() {
        return this.depart;
    }

    public int[] getParcours() {    
        return this.parcours;
    }
    
    public Stack<int[]> getPileCoord() {
        return this.pileCoord;
    }

    public int[] getJoueurCoord(){
        return this.joueur;
    }

    public int[] getArrivee(){
        return this.arrivee;
    }

    public ArrayList<int[]> getListe() {
        return this.liste;
    } 
     
    public boolean continuer(Matrix<TypeObjet> map) {

        if (pileCoord.empty() == true){
            return false;
        }
        
        if (chemin.contains(pileCoord.peek())) {
            return false;
        }

        if ((parcours[0] < 0 || parcours[0] >= 11) || (parcours[1] < 0 || parcours[1] >= 11)){
            return false;
        }

        if ((map.get(parcours[0], parcours[1]) != TypeObjet.VIDE) && (map.get(parcours[0], parcours[1]) != TypeObjet.ARRIVE) && (map.get(parcours[0], parcours[1]) != TypeObjet.JOUEUR)) {
            return false;
        }

        return true;
    }

    public boolean possible(int[] essai){
        if ((copy[0] < 0 || copy[0] >= 11) || (copy[1] < 0 || copy[1] >= 11)){
            return false;
        }

        if (chemin.contains(copy)) { // Problème dans l'ensemble chemin.
            return false;
        }
        
        /*if ((map.get(copy[0], copy[1]) != TypeObjet.VIDE) && (map.get(copy[0], copy[1]) != TypeObjet.ARRIVE) && (map.get(copy[0], copy[1]) != TypeObjet.JOUEUR)) {
            System.out.println("Impossible C");
            return false;
        }*/

        return true;
    }
  
    public void copie(int[] base) {
        copy = base;
        getListe().add(copy);

        copy[0] += 1;
        if (copy == arrivee) {
            fini();
        }
        if (possible(copy) == true){
            getListe().add(copy);
            pileCoord.push(clone(copy)); // (x + 1 , y)
        }

        copy[0] -= 2;
        if (copy == arrivee) {
            fini();
        }
        if (possible(copy) == true) {
            getListe().add(copy);
            pileCoord.push(clone(copy)); // (x - 1 , y)
        }

        copy[0] += 1;
        copy[1] += 1;
        if (copy == arrivee) {
            fini();
        }
        if (possible(copy) == true) {
            getListe().add(copy);
            pileCoord.push(clone(copy)); // (x , y + 1)
        }
        copy[1] -= 2;
        if (copy == arrivee) {
            fini();
        }
        if (possible(copy) == true) {
            getListe().add(copy);
            pileCoord.push(clone(copy)); // (x , y - 1)
        }

        /*copie = base;
        copie[0] += 1;
        pileCoord.push(clone(copie)); // (x + 1 , y)
        copie[0] -= 2;
        pileCoord.push(clone(copie)); // (x - 1 , y)
        copie[0] += 1;
        copie[1] += 1;
        pileCoord.push(clone(copie)); // (x , y + 1)
        copie[1] -= 2;
        pileCoord.push(clone(copie)); // (x , y - 1)*/
    }

    public int[] teleport(Matrix<TypeObjet> map){
        chemin.add(getJoueurCoord());
        
        if (map.get(arrivee[0], arrivee[1]) != TypeObjet.VIDE && map.get(arrivee[0], arrivee[1]) != TypeObjet.ARRIVE && map.get(arrivee[0], arrivee[1]) != TypeObjet.JOUEUR ) {
            return getDepart();
        }

        pileCoord.push(clone(joueur));
        while (pileCoord.empty() != true){
            parcours = getPileCoord().peek();
            if (parcours[0] == arrivee[0] && parcours[1] == arrivee[1]){
                return this.getArrivee();
            }
            
            while (continuer(map) != true) {              
                pileCoord.pop();             
                parcours = getPileCoord().peek();
                if(pileCoord.empty() == true) {
                    return getDepart();
                }
            }
            chemin.add(parcours);
            pileCoord.pop();
            copie(parcours);
            if (fini() == true) {
                return getArrivee();
            }
            parcours = pileCoord.peek();
        }
        return getDepart();
    }

    public int[] clone(int[] tab){
        int[] clone = new int[2];
        for (int i = 0; i < 2; i++) {
                clone[i] = tab[i];
        }
        return clone;
    }

    public boolean fini() {
        return true;
    }
}