import java.util.*;
public class RecMap extends Map{
	private boolean nord; // nord, sud, est, ouest
	private boolean sud; // nord, sud, est, ouest
	private boolean est; // nord, sud, est, ouest
	private boolean ouest; // nord, sud, est, ouest

	private Map mapParent;
	private Map map;

	public RecMap(String chaine1, String chaine2){
		this.mapParent = new Map(chaine1);
		this.map = new Map(chaine2);

		this.nord = map.rechercheDispoRec(0, 5, Direction.BAS);
		this.sud = map.rechercheDispoRec(10, 5, Direction.HAUT);
		this.est = map.rechercheDispoRec(5, 10, Direction.GAUCHE);
		this.ouest = map.rechercheDispoRec(5, 0, Direction.DROITE);
	}

	public Map getMap(){
		return map;
	}
	public Map getMapParent(){
		return mapParent;
	}

	public boolean getNord(){
		return nord;
	}

	public boolean getSud(){
		return sud;
	}

	public boolean getOuest(){
		return ouest;
	}

	public boolean getEst(){
		return est;
	}

	public void refreshSorties(){
		this.nord = map.rechercheDispoRec(0, 5, Direction.BAS);
		this.sud = map.rechercheDispoRec(10, 5, Direction.HAUT);
		this.est = map.rechercheDispoRec(5, 10, Direction.GAUCHE);
		this.ouest = map.rechercheDispoRec(5, 0, Direction.DROITE);
	}

    public void modifierMapParentType(Matrix<TypeObjet> map){
        mapParent.modifierMapType(map);
    }

	public boolean revenirDansMonde(Direction d, int x, int y){
        int l, c;
		int[] mondeCoord = mapParent.getBlocMondeCoord();
        switch(d){
            case HAUT:
                c = mondeCoord[0]; l = mondeCoord[1] - 1;
                break;
            case BAS:
                c = mondeCoord[0]; l= mondeCoord[1] + 1;
                break;
            case GAUCHE:
                c = mondeCoord[0] - 1; l= mondeCoord[1];
                break;
            case DROITE:
                c = mondeCoord[0] + 1; l = mondeCoord[1];
                break;
            default: 
                return false;
        }
        switch(this.mapParent.getMapType().get(c,l)){
            case MUR:
                return false;
            case BLOC:
                if(!mapParent.estDeplacableBloc(d, c, l)){
                    return false;
                }
            default:
                break;
        }
        this.apparitionParent(this.map.getMapType().get(x,y), c, l);
        this.map.getMapType().set(x,y, TypeObjet.VIDE);
        this.map.refreshMapBool();
        refreshSorties();
        return true;
    }

	public void apparitionParent(TypeObjet t, int c, int l){
        this.mapParent.getMapType().set(c,l,t);
        this.mapParent.refreshMapBool();
        this.mapParent.refreshCoordJoueur();
    }

	@Override
	public boolean estDeplacable(Direction d){
        map.refreshCoordJoueur();
        int x = 0, y = 0, m = 0;
		int[] playerCoord = map.getJoueurCoord();
        switch(d){
            case HAUT:
                x = playerCoord[0];
                y = playerCoord[1];
                m = playerCoord[1] - 1;
                break;
            case BAS:
                x = playerCoord[0];
                y = playerCoord[1];
                m = playerCoord[1] + 1;
                break;
            case GAUCHE:
                x = playerCoord[0];
                y = playerCoord[1];
                m = playerCoord[0] - 1;
                break;
            case DROITE:
                x = playerCoord[0];
                y = playerCoord[1];
                m = playerCoord[0] + 1;
                break;
            default:
                break;
        }

        if(d == Direction.HAUT || d == Direction.BAS){
            map.getPile().push(map.getMapType().clone());
            mapParent.getPile().push(mapParent.getMapType().clone());
            if(m == -1 || m == 11){
                return revenirDansMonde(d, x, y);
            }
            if(map.getMapType().get(x, m) == TypeObjet.VIDE || map.getMapType().get(x, m) == TypeObjet.ARRIVE ){
                return map.deplacer(d, x, y);
            }else if(map.getMapType().get(x, m) == TypeObjet.BLOC){
                if(estDeplacableBloc(d, x, m)){
                    return map.deplacer(d, x, y);
                }else{
                    map.getPile().pop();
                    return false;
                }
            }
        }

        if(d == Direction.GAUCHE || d == Direction.DROITE){
            map.getPile().push(map.getMapType().clone());
            mapParent.getPile().push(mapParent.getMapType().clone());
            if(m == -1 || m == 11){
                return revenirDansMonde(d, x, y);
            }
            if(map.getMapType().get(m, y) == TypeObjet.VIDE || map.getMapType().get(m, y) == TypeObjet.ARRIVE){
                return map.deplacer(d, x, y);
            }else if(map.getMapType().get(m, y) == TypeObjet.BLOC){
                if(estDeplacableBloc(d, m, y)){
                    return map.deplacer(d, x, y);
                }else{
                    map.getPile().pop();
                    return false;
                }
            }
        }
        if(d == Direction.BACK){
            map.retourArriere();
            mapParent.retourArriere();
            return true;
        }
        return false;
    }

	@Override
    public boolean estDeplacableBloc(Direction d, int x, int y){
        switch(d){
            case HAUT:
                if(x == 5 && y == 0)
                    return revenirDansMonde(d, x, y);
                if(map.getMapType().get(x, y - 1) == TypeObjet.VIDE || map.getMapType().get(x, y - 1) == TypeObjet.ARRIVE){
                    return map.deplacer(d, x, y);
                }
                if(map.getMapType().get(x, y - 1) == TypeObjet.BLOC){
                    if(estDeplacableBloc(d, x, y - 1)){ 
                        return map.deplacer(d, x, y);
                    }
                }
                return false;
            case BAS:
                if(x == 5 && y == 10)
                    return revenirDansMonde(d, x, y);
                if(map.getMapType().get(x, y + 1) == TypeObjet.VIDE || map.getMapType().get(x, y + 1) == TypeObjet.ARRIVE){
                    return map.deplacer(d, x, y);
                }
                if(map.getMapType().get(x, y + 1) == TypeObjet.BLOC){
                    if(estDeplacableBloc(d, x, y + 1)){ 
                        return map.deplacer(d, x, y);
                    }
                }
                return false;
            case GAUCHE:
                if(x == 0 && y == 5)
                    return revenirDansMonde(d, x, y);
                if(map.getMapType().get(x - 1, y) == TypeObjet.VIDE || map.getMapType().get(x - 1, y) == TypeObjet.ARRIVE){
                    return map.deplacer(d, x, y);
                }
                if(map.getMapType().get(x -1, y) == TypeObjet.BLOC){
                    if(estDeplacableBloc(d, x - 1, y)){ 
                        return map.deplacer(d, x, y);
                    }
                }
                return false;
            case DROITE:
                if(x == 10 && y == 5)
                    return revenirDansMonde(d, x, y);
                if(map.getMapType().get(x + 1, y) == TypeObjet.VIDE || map.getMapType().get(x + 1, y) == TypeObjet.ARRIVE){
                    return map.deplacer(d, x, y);
                }
                if(map.getMapType().get(x + 1, y) == TypeObjet.BLOC){
                    if(estDeplacableBloc(d, x + 1, y)){ 
                        return map.deplacer(d, x, y);
                    }
                }
                return false;
            default:
                return false;

        }
    }

}