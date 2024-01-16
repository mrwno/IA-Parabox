import java.io.*;
import java.util.*;


public class Fichier {
    private String level;
    private Stack<String> pile;
    private File file;
    private File fileUpdate;
    private long taille;

  
    public Fichier(){

    }


    public Fichier(String pathname){
        this.file = new File(pathname);
        try{
            FileInputStream lvl = new FileInputStream(file);
            this.taille = file.length();
            byte[] b = new byte[(int)taille];     
            try{
                lvl.read(b);
                lvl.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.level = new String(b); 
            this.pile = new Stack<String>();
        } catch (FileNotFoundException ex){
        }
    }
  
    public void fileUpdate(){
        fileUpdate = new File("lvl/level_update", file.getName());
        fileUpdate.setReadable(true);
        fileUpdate.setWritable(true);
        try{
            FileOutputStream lvl = new FileOutputStream(fileUpdate, true);
            try{
                byte[] b2 = new byte[(int)taille];
                b2 = level.getBytes();
                lvl.write(b2);
                lvl.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        } catch(FileNotFoundException ex){
            
        }
    }
  
    
    public Stack<String> getPile(){
        return pile;
    }

    public String getNiveau(){
        return level;
    }
  
    public void afficherTerminal(){
        System.out.println(level);
        fileUpdate();
    }

    public void resetTerminal(Direction d, Map map){
        switch(d){
            case BACK:
                if(pile.size() > 1){
                    level = pile.pop();
                    afficherTerminal();
                }
            default:
                level = matrixToString(map);
                pile.push(level);
                afficherTerminal();      
        }
    }

    public String matrixToString(Map map){
        String tmp = new String();
        Map mapTmp = map;
        int v;
        if(map.getObjetRec() == null){
            v = 1;
        }else{
            v = 2;
        }
        boolean test;
        for(int l = 0; l < v; l++){
            int[][] arrive = mapTmp.getArrive();
            tmp += "\n";
            for(int i = 0; i < 11 ; i++){
                for(int j = 0; j < 11 ; j++){
                    test = false;
                    switch(mapTmp.getMapType().get(j, i)){
                        case MUR:
                            tmp += "#";
                            break;
                        case VIDE:
                            tmp += " ";
                            break;
                        case BLOC:
                            for(int k = 0; k < arrive.length; k++){
                                int x = arrive[k][0];
                                int y = arrive[k][1];
                                if(i == y && x == j){
                                    test = true;  
                                }
                            }
                            if(test == true){
                                tmp += "b";   
                            }else{
                                tmp += "B";   
                            }
                            break;
                        case BLOC_MONDE:
                            for(int k = 0; k < arrive.length; k++){
                                int x = arrive[k][0];
                                int y = arrive[k][1];
                                if(i == y && x == j){
                                    test = true;  
                                }
                            }
                            if(test == true){
                                tmp += "c";   
                            }else{
                                tmp += "C";   
                            }
                            break;
                        case ARRIVE:
                            tmp += "@";
                            break;
                        case JOUEUR:
                            for(int k = 0; k < arrive.length; k++){
                                int x = arrive[k][0];
                                int y = arrive[k][1];
                                if(i == y && x == j){
                                    test = true;  
                                }
                            }
                            if(test == true){
                                tmp += "a";   
                            }else{
                                tmp += "A";   
                            }
                            break;
                        default:
                    }
                }
                tmp += "\n";
            }
            tmp += "\n";
            if(v == 2){
                mapTmp = map.getObjetRec().getMap();
            }
        }
        
        return tmp;
    }

    public boolean fileDelete(){
        return fileUpdate.delete();
    }

}
