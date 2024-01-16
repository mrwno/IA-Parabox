import java.util.*;
public class Map implements MapInterface{
    private RecMap objetRec;
    private Matrix<Boolean> mapBool; 
    private Matrix<TypeObjet> mapType;
    private int[] joueurCoord;
    private int[] blocMondeCoord;
    private int[][] arrive;
    private Stack<Matrix<TypeObjet>> pile;

    public Map(){

    }

    public Map(String niveau){
        if(niveau.length() > 145){
            String chaine1, chaine2;
            chaine1 = niveau.substring(0, 145);
            chaine2 = niveau.substring(145, niveau.length());
            this.objetRec = new RecMap(chaine1, chaine2);
        }else{
            this.objetRec = null;
        }

        this.mapType = new Matrix<TypeObjet> (11);
        this.mapBool = new Matrix<Boolean> (11);
        char[] tab = niveau.toCharArray();
        this.joueurCoord = new int[2];
        this.blocMondeCoord = new int[2];
        this.pile = new Stack<>();

        int i = 0, j = 0;
        int k = 0;
        int tmp = 0;

        while(i <= 10){
            while(j <= 10){
                switch(tab[k]){
                    case '@' :
                        mapType.set(j, i, TypeObjet.ARRIVE);
                        mapBool.set(j, i , true);
                        j++;
                        tmp++;
                        break;
                    case 'A' :
                        mapType.set(j, i, TypeObjet.JOUEUR);
                        mapBool.set(j, i , false);
                        j++;
                        break;
                    case 'B' :
                        mapType.set(j, i, TypeObjet.BLOC);
                        mapBool.set(j, i , false);
                        j++;
                        break;
                    case 'C' :
                        mapType.set(j, i, TypeObjet.BLOC_MONDE);
                        mapBool.set(j, i , false);
                        this.blocMondeCoord[0] = j;
                        this.blocMondeCoord[1] = i;
                        j++;
                        break;
                    case '#' :
                        mapType.set(j, i, TypeObjet.MUR);
                        mapBool.set(j, i , false);
                        j++;
                        break;
                    case ' ' : 
                        mapType.set(j, i, TypeObjet.VIDE);
                        mapBool.set(j, i , true);
                        j++;
                        break;
                    default:
                        break;
                }
                k++;
            }
            j = 0;
            i++;
        }

        this.arrive = new int[tmp][2];
        tmp = 0;
        for(i = 0; i <= 10; i++){
            for(j = 0; j <= 10; j++){
                if(mapType.get(j, i) == TypeObjet.ARRIVE){
                    arrive[tmp][0] = j;
                    arrive[tmp][1] = i;
                    tmp++;
                }else if(mapType.get(j, i) == TypeObjet.JOUEUR){
                    joueurCoord[0] = j;
                    joueurCoord[1] = i;
                }
            }
        }

    }

    public RecMap getObjetRec(){
        return this.objetRec;
    }

    public Matrix<Boolean> getMapBool(){
        return this.mapBool;
    }
    
    public Matrix<TypeObjet> getMapType(){
        return this.mapType;
    }
    
    
    public int[] getJoueurCoord(){
        return this.joueurCoord;
    }

    public int[] getBlocMondeCoord(){
        return this.blocMondeCoord;
    }

    public int[][] getArrive(){
        return this.arrive;
    }

    public Stack<Matrix<TypeObjet>> getPile(){
        return this.pile;
    }

    public void modifierMapType(Matrix<TypeObjet> map){
        mapType = map;
        refreshMapBool();
        refreshCoordJoueur();
    }

    public void modifierPile(Stack<Matrix<TypeObjet>> pile2){
        pile = pile2;
    }


    
    public boolean niveauFini(){
        for(int i = 0; i < arrive.length; i++){
            int x = arrive[i][0];
            int y = arrive[i][1];

            if(mapBool.get(x, y) == true){
                return false;
            }
        }
        if(objetRec != null){
            return objetRec.getMap().niveauFini();
        }
        return true;
    }

    public boolean estDeplacable(Direction d){
        int x = 0, y = 0, m = 0;
        switch(d){
            case HAUT:
                x = joueurCoord[0];
                y = joueurCoord[1];
                m = joueurCoord[1] - 1;
                break;
            case BAS:
                x = joueurCoord[0];
                y = joueurCoord[1];
                m = joueurCoord[1] + 1;
                break;
            case GAUCHE:
                x = joueurCoord[0];
                y = joueurCoord[1];
                m = joueurCoord[0] - 1;
                break;
            case DROITE:
                x = joueurCoord[0];
                y = joueurCoord[1];
                m = joueurCoord[0] + 1;
                break;
            default:
                break;
        }

        if(d == Direction.HAUT || d == Direction.BAS){    
            pile.push(mapType.clone());
            if(objetRec != null) 
                objetRec.getMap().getPile().push(objetRec.getMap().getMapType().clone());
            if(mapType.get(x, m) == TypeObjet.VIDE || mapType.get(x, m) == TypeObjet.ARRIVE ){
                modifierCoord(d);
                return deplacer(d, x, y);
            }else if(mapType.get(x, m) == TypeObjet.BLOC){
                if(estDeplacableBloc(d, x, m)){
                    modifierCoord(d);
                    return deplacer(d, x, y);
                }else{
                    pile.pop();
                    return false;
                }
            }
            else if(mapType.get(x, m) == TypeObjet.BLOC_MONDE){
                if(estDeplacableBlocMonde(d, x, m)){
                    if(mapType.get(joueurCoord[0], joueurCoord[1]) == TypeObjet.BLOC_MONDE){
                        return true;
                    }  
                    else
                        return deplacer(d, x, y);
                }
                pile.pop();
                return false;
            }
        }

        if(d == Direction.GAUCHE || d == Direction.DROITE){
            pile.push(mapType.clone());
            if(objetRec != null) 
                objetRec.getMap().getPile().push(objetRec.getMap().getMapType().clone());
            if(mapType.get(m, y) == TypeObjet.VIDE || mapType.get(m, y) == TypeObjet.ARRIVE){
                modifierCoord(d);
                return deplacer(d, x, y);
            }else if(mapType.get(m, y) == TypeObjet.BLOC){
                if(estDeplacableBloc(d, m, y)){
                    modifierCoord(d);
                    return deplacer(d, x, y);
                }else{
                    pile.pop();
                    return false;
                }
            }
            else if(mapType.get(m, y) == TypeObjet.BLOC_MONDE){
                if(estDeplacableBlocMonde(d, m, y)){
                    if(mapType.get(joueurCoord[0], joueurCoord[1]) == TypeObjet.BLOC_MONDE){
                        return true;
                    }else{
                        return deplacer(d, x, y);
                    }
                }
                pile.pop();
                return false;
            }
        }
        if(d == Direction.BACK){
            retourArriere();
            if(objetRec != null) 
                objetRec.getMap().retourArriere();
            return true;
        }
        return false;
    }

    public boolean estDeplacableBloc(Direction d, int x, int y){
        switch(d){
            case HAUT:
                if(mapType.get(x, y - 1) == TypeObjet.VIDE || mapType.get(x, y - 1) == TypeObjet.ARRIVE){
                    return deplacer(d, x, y);
                }
                if(mapType.get(x, y - 1) == TypeObjet.BLOC){
                    if(estDeplacableBloc(d, x, y - 1)){ 
                        return deplacer(d, x, y);
                    }
                }
                if(mapType.get(x, y - 1) == TypeObjet.BLOC_MONDE){
                    if(estDeplacableBlocMonde(d, x, y - 1)){ 
                        if(mapType.get(x, y - 2) == TypeObjet.MUR)
                            return true;
                        else
                            return deplacer(d, x, y);
                    }
                }
                return false;
            case BAS:
                if(mapType.get(x, y + 1) == TypeObjet.VIDE || mapType.get(x, y + 1) == TypeObjet.ARRIVE){
                    return deplacer(d, x, y);
                }
                if(mapType.get(x, y + 1) == TypeObjet.BLOC){
                    if(estDeplacableBloc(d, x, y + 1)){ 
                        return deplacer(d, x, y);
                    }
                }
                if(mapType.get(x, y + 1) == TypeObjet.BLOC_MONDE){
                    if(estDeplacableBlocMonde(d, x, y + 1)){ 
                        if(mapType.get(x, y + 2) == TypeObjet.MUR)
                            return true;
                        else
                            return deplacer(d, x, y);
                    }
                }
                return false;
            case GAUCHE:
                if(mapType.get(x - 1, y) == TypeObjet.VIDE || mapType.get(x - 1, y) == TypeObjet.ARRIVE){
                    return deplacer(d, x, y);
                }
                if(mapType.get(x -1, y) == TypeObjet.BLOC){
                    if(estDeplacableBloc(d, x - 1, y)){ 
                        return deplacer(d, x, y);
                    }
                }
                if(mapType.get(x -1, y) == TypeObjet.BLOC_MONDE){
                    if(estDeplacableBlocMonde(d, x - 1, y)){ 
                        if(mapType.get(x - 2, y) == TypeObjet.MUR)
                            return true;
                        else
                            return deplacer(d, x, y);
                    }
                }
                return false;
            case DROITE:
                if(mapType.get(x + 1, y) == TypeObjet.VIDE || mapType.get(x + 1, y) == TypeObjet.ARRIVE){
                    return deplacer(d, x, y);
                }
                if(mapType.get(x + 1, y) == TypeObjet.BLOC){
                    if(estDeplacableBloc(d, x + 1, y)){ 
                        return deplacer(d, x, y);
                    }
                }
                if(mapType.get(x + 1, y) == TypeObjet.BLOC_MONDE){
                    if(estDeplacableBlocMonde(d, x + 1, y)){ 
                        if(mapType.get(x + 2, y) == TypeObjet.MUR)
                            return true;
                        else
                            return deplacer(d, x, y);
                    }
                }
                return false;
            default:
                return false;

        }
    }

    public boolean estDeplacableBlocMonde(Direction d, int x, int y){
        switch(d){
            case HAUT:
                if(this.objetRec.getSud()){
                    if((mapType.get(x, y - 1) == TypeObjet.MUR)){
                        //deplacer joueur dans bloc monde
                        sortirDuMonde(d, x, y+1);
                        return true;
                    }
                    else if((mapType.get(x, y-1) == TypeObjet.BLOC && !(estDeplacableBloc(d, x, y - 1)))){
                        if(this.objetRec.getNord()){
                            //deplacer objet dans maprec puis bouger joueur + obj rec
                            sortirDuMonde(Direction.BAS, x, y - 1);
                            modifierCoordMonde(d);
                            return deplacer(d, x, y);
                        }
                        else{
                            //deplacer joueur dans bloc monde 
                            sortirDuMonde(d, x, y + 1);
                            return true;
                        }
                    }
                    else{
                        // bouger joueur + obj rec
                        modifierCoordMonde(d);
                        return deplacer(d, x, y);
                    }
                }
                else if(!(this.objetRec.getSud())){
                    if((mapType.get(x, y - 1) == TypeObjet.BLOC && !(estDeplacableBloc(d, x, y - 1)))){
                        if(this.objetRec.getNord()){
                            //deplacer objet dans maprec puis bouger joueur + obj rec 
                            sortirDuMonde(Direction.BAS, x, y-1);
                            modifierCoordMonde(d);
                            return deplacer(d, x, y);
                        }
                        else{
                            //rien ne bouge
                            return false;
                        }
                    }
                    else if((mapType.get(x, y - 1) == TypeObjet.MUR)){
                        //rien ne bouge 
                        return false;
                    }
                    else{
                        //bouger joueur + obj rec 
                        modifierCoordMonde(d);
                        return deplacer(d, x, y);
                    }
                }
            case BAS:
                if(this.objetRec.getNord()){
                    if((mapType.get(x, y + 1) == TypeObjet.MUR)){
                        //deplacer joueur dans bloc monde 
                        
                        sortirDuMonde(d, x, y - 1);
                        return true;
                    }
                    else if((mapType.get(x, y + 1) == TypeObjet.BLOC && !(estDeplacableBloc(d, x, y + 1)))){
                        if(this.objetRec.getSud()){
                            //deplacer objet dans maprec puis bouger joueur + obj rec
                            sortirDuMonde(Direction.HAUT, x, y + 1);
                            modifierCoordMonde(d);
                            return deplacer(d, x, y);
                        }
                        else{
                            //deplacer joueur dans bloc monde 
                            sortirDuMonde(d, x, y - 1);
                            return true;
                        }
                    }
                    else{
                        // bouger joueur + obj rec
                        modifierCoordMonde(d);

                        return deplacer(d, x, y);
                    }
                }
                else if(!(this.objetRec.getNord())){
                    if((mapType.get(x, y + 1) == TypeObjet.BLOC && !(estDeplacableBloc(d, x, y + 1)))){
                        if(this.objetRec.getSud()){
                            //deplacer objet dans maprec puis bouger joueur + obj rec 
                            sortirDuMonde(Direction.HAUT, x, y + 1);
                            modifierCoordMonde(d);
                            return deplacer(d, x, y);
                        }
                        else{
                            //rien ne bouge
                            return false;
                        }
                    }
                    else if((mapType.get(x, y + 1) == TypeObjet.MUR)){
                        //rien ne bouge 
                        return false;
                    }
                    else{
                        //bouger joueur + obj rec 
                        modifierCoordMonde(d);
                        return deplacer(d, x, y);
                    }
                }
            case GAUCHE:
                if(this.objetRec.getEst()){
                    if((mapType.get(x - 1, y) == TypeObjet.MUR)){
                        //deplacer joueur dans bloc monde 
                        sortirDuMonde(d, x + 1, y);
                        return true;
                    }
                    else if((mapType.get(x - 1, y) == TypeObjet.BLOC && !(estDeplacableBloc(d, x - 1, y)))){
                        if(this.objetRec.getOuest()){
                            //deplacer objet dans maprec puis bouger joueur + obj rec
                            sortirDuMonde(Direction.DROITE, x - 1, y);
                            modifierCoordMonde(d);
                            return deplacer(d, x, y);
                        }
                        else{
                            //deplacer joueur dans bloc monde 
                            sortirDuMonde(d, x + 1, y);
                            return true;
                        }
                    }
                    else{
                        // bouger joueur + obj rec
                        modifierCoordMonde(d);
                        return deplacer(d, x, y);
                    }
                }
                else if(!(this.objetRec.getEst())){
                    if((mapType.get(x - 1, y) == TypeObjet.BLOC && !(estDeplacableBloc(d, x - 1, y)))){
                        if(this.objetRec.getOuest()){
                            //deplacer objet dans maprec puis bouger joueur + obj rec 
                            sortirDuMonde(Direction.DROITE, x - 1, y);
                            modifierCoordMonde(d);
                            return deplacer(d, x, y);
                        }
                        else{
                            //rien ne bouge
                            return false;
                        }
                    }
                    else if((mapType.get(x - 1, y) == TypeObjet.MUR)){
                        //rien ne bouge 
                        return false;
                    }
                    else{
                        //bouger joueur + obj rec 
                        modifierCoordMonde(d);
                        return deplacer(d, x, y);
                    }
                }
            case DROITE:
                if(this.objetRec.getOuest()){
                    if((mapType.get(x + 1, y) == TypeObjet.MUR)){
                        //deplacer joueur dans bloc monde 
                        sortirDuMonde(d, x - 1, y);
                        return true;
                    }
                    else if((mapType.get(x + 1, y) == TypeObjet.BLOC && !(estDeplacableBloc(d, x + 1, y)))){
                        if(this.objetRec.getEst()){
                            //deplacer objet dans maprec puis bouger joueur + obj rec
                            sortirDuMonde(Direction.GAUCHE, x + 1, y);
                            modifierCoordMonde(d);
                            return deplacer(d, x, y);
                        }
                        else{
                            //deplacer joueur dans bloc monde 
                            sortirDuMonde(d, x - 1, y);
                            return true;
                        }
                    }
                    else{
                        // bouger joueur + obj rec
                        modifierCoordMonde(d);
                        return deplacer(d, x, y);
                    }
                }
                else if(!(this.objetRec.getOuest())){
                    if((mapType.get(x + 1, y) == TypeObjet.BLOC && !(estDeplacableBloc(d, x + 1, y)))){
                        if(this.objetRec.getEst()){
                            //deplacer objet dans maprec puis bouger joueur + obj rec 
                            sortirDuMonde(Direction.GAUCHE, x + 1, y);
                            modifierCoordMonde(d);
                            return deplacer(d, x, y);
                        }
                        else{
                            //rien ne bouge
                            return false;
                        }
                    }
                    else if((mapType.get(x + 1, y) == TypeObjet.MUR)){
                        //rien ne bouge 
                        return false;
                    }
                    else{
                        //bouger joueur + obj rec 
                        modifierCoordMonde(d);
                        return deplacer(d, x, y);
                    }
                }
            default:
                return false;
        }  
    }


    public boolean deplacer(Direction d, int x, int y){
        for(int i = 0; i < arrive.length; i++){
            if(arrive[i][0] == x && arrive[i][1] == y){
                switch(d){
                    case HAUT:
                        mapType.set(x, y - 1, mapType.get(x, y));
                        mapType.set(x, y, TypeObjet.ARRIVE);
                        mapBool.set(x, y - 1, mapBool.get(x, y));
                        mapBool.set(x, y, true);
                        return true;
                    case BAS:
                        mapType.set(x, y + 1, mapType.get(x, y));
                        mapType.set(x, y, TypeObjet.ARRIVE);
                        mapBool.set(x, y + 1, mapBool.get(x, y));
                        mapBool.set(x, y, true);
                        return true;
                    case GAUCHE:
                        mapType.set(x - 1, y, mapType.get(x, y));
                        mapType.set(x, y, TypeObjet.ARRIVE);
                        mapBool.set(x - 1, y, mapBool.get(x, y));
                        mapBool.set(x, y, true);
                        return true;
                    case DROITE:
                        mapType.set(x + 1, y, mapType.get(x, y));
                        mapType.set(x, y, TypeObjet.ARRIVE);
                        mapBool.set(x + 1, y, mapBool.get(x, y));
                        mapBool.set(x, y, true);
                        return true;
                    default:
                        break;
                }
            }
        }
        switch(d){
            case HAUT:
                mapType.set(x, y - 1, mapType.get(x, y));
                mapType.set(x, y, TypeObjet.VIDE);
                mapBool.set(x, y - 1, mapBool.get(x, y));
                mapBool.set(x, y, true);
                return true;
            case BAS:
                mapType.set(x, y + 1, mapType.get(x, y));
                mapType.set(x, y, TypeObjet.VIDE);
                mapBool.set(x, y + 1, mapBool.get(x, y));
                mapBool.set(x, y, true);
                return true;
            case GAUCHE:
                mapType.set(x - 1, y, mapType.get(x, y));
                mapType.set(x, y, TypeObjet.VIDE);
                mapBool.set(x - 1, y, mapBool.get(x, y));
                mapBool.set(x, y, true);
                return true;
            case DROITE:
                mapType.set(x + 1, y, mapType.get(x, y));
                mapType.set(x, y, TypeObjet.VIDE);
                mapBool.set(x + 1, y, mapBool.get(x, y));
                mapBool.set(x, y, true);
                return true;
            default:
                return false;
        }
    }

    public void modifierCoord(Direction d){
        switch(d){
            case HAUT:
                joueurCoord[1]--;
                break;
                
            case BAS:
                joueurCoord[1]++;
                break;

            case GAUCHE:
                joueurCoord[0]--;  
                break;

            case DROITE:
                joueurCoord[0]++;    
                break;

            default:
                break;
        }
    }

    public void modifierCoordMonde(Direction d){
        switch(d){
            case HAUT:
                blocMondeCoord[1]--;
                break;
                
            case BAS:
                blocMondeCoord[1]++;
                break;

            case GAUCHE:
                blocMondeCoord[0]--;  
                break;

            case DROITE:
                blocMondeCoord[0]++;    
                break;

            default:
                break;
        }
    }


    
    
    public void afficherMatEnum(){
        for(int i = 0; i <= 10; i++){
            for(int j = 0; j <= 10; j++){
                    switch(mapType.get(j, i)) {
                    case BLOC_MONDE :
                        System.out.print('C');
                        break;
                    case BLOC :
                        System.out.print('B');
                        break;
                    case JOUEUR :
                        System.out.print('A');
                        break;
                    case MUR :
                        System.out.print('#');
                        break;
                    case ARRIVE :
                        System.out.print('@');
                        break;
                    default :
                        System.out.print(' ');
                    }
            }
            System.out.print("\n");
        }
    } 

    public void retourArriere(){
        if(this.pile.peek() != null){
            mapType = this.pile.pop();
            refreshMapBool();
            refreshCoordJoueur();
        }
    }


    public void refreshMapBool(){
        for(int i = 0; i <= 10; i++){
            for(int j = 0; j <= 10; j++){
                if(this.mapType.get(j,i) == TypeObjet.VIDE || this.mapType.get(j,i) == TypeObjet.ARRIVE){
                    this.mapBool.set(j, i, true);
                }
                else{
                    this.mapBool.set(j, i, false);
                }
            }
        }
    }

    public void refreshCoordJoueur(){
        int i, j;
        boolean test = false;
        for(i = 0; i<= 10; i++){
            for(j = 0; j <= 10; j++){
                if(mapType.get(j,i) == TypeObjet.JOUEUR){
                    joueurCoord[0] = j;
                    joueurCoord[1] = i;
                    test = true;
                }
                if(mapType.get(j,i) == TypeObjet.BLOC_MONDE){
                    blocMondeCoord[0] = j;
                    blocMondeCoord[1] = i;
                }
            }
        }
        if(!(test)){
            joueurCoord[0] = blocMondeCoord[0];
            joueurCoord[1] = blocMondeCoord[1];
        }
    }


    public boolean sortirDuMonde(Direction d, int x, int y){
        int l, c;
        switch(d){
            case HAUT:
                l=10; c=5;
                break;
            case BAS:
                l=0; c=5;
                break;
            case GAUCHE:
                l=5; c=10;
                break;
            case DROITE:
                l=5; c=0;
                break;
            default:
                return false;
        }
        if(this.objetRec.getMap().getMapType().get(c,l) == TypeObjet.BLOC){
            if(!(this.objetRec.getMap().estDeplacableBloc(d,c,l)))
                return false;
        }
        this.objetRec.getMap().apparition(this.mapType.get(x,y), c, l);
        this.mapType.set(x, y, TypeObjet.VIDE);
        refreshMapBool();
        this.objetRec.refreshSorties();
        objetRec.getMap().refreshCoordJoueur();
        modifierCoord(d);
        return true;
    }

    public void apparition(TypeObjet t, int c, int l){
        this.mapType.set(c,l,t);
        this.refreshMapBool();
        this.refreshCoordJoueur();
    }


    public void modifierCoordRes(int[] tab){
        boolean tmp = false;
        int i, j;
        for(i = 0; i<= 10; i++){
            for(j = 0; j <= 10; j++){
                if(mapType.get(j,i) == TypeObjet.JOUEUR){
                    for(int k = 0; k < arrive.length; k++){
                        if(arrive[k][0] == j && arrive[k][1] == i){
                            mapType.set(j,i,TypeObjet.ARRIVE);
                            tmp = true;
                        }
                    }
                    if(!(tmp))
                        mapType.set(j,i,TypeObjet.VIDE);
                }
                if(j == tab[0] && i == tab[1]){
                    mapType.set(j,i,TypeObjet.JOUEUR);
                }
            }
        }
        pile.push(mapType.clone());
        refreshCoordJoueur();
        refreshMapBool();

    }

    public boolean rechercheDispoRec(int r, int c, Direction d){
        if(d == Direction.BAS){
            switch(this.mapType.get(c, r)){
                case MUR:
                    return false;
                case BLOC:
                    return rechercheDispoRec(r + 1, c, d);
                default:
                    return true;
            }
        }
        if(d == Direction.HAUT){
            switch(this.mapType.get(c, r)){
                case MUR:
                    return false;
                case BLOC:
                    return rechercheDispoRec(r - 1, c, d);
                default:
                    return true;
            }
        }

        if(d == Direction.DROITE){
            switch(this.mapType.get(c, r)){
                case MUR:
                    return false;
                case BLOC:
                    return rechercheDispoRec(r, c + 1, d);
                default:
                    return true;
            }
        }

        if(d == Direction.GAUCHE){
            switch(this.mapType.get(c, r)){
            case MUR:
                return false;
            case BLOC:
                return rechercheDispoRec(r, c - 1, d);
            default:
                return true;
            }
        }
        return false;
    }

/*Map -> objRec
this.sortirDuMonde
 * this.objRec.sortirDuMonde
 */


 /*ObjRec -> Map
 * this.sortirDuMonde
  *this.objRecsortirDuMonde
  */

}