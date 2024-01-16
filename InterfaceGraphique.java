import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class InterfaceGraphique extends JFrame implements ActionListener, KeyListener,MouseListener{
        private Fichier niveauFile;
        private Map niveauMap;
        private JPanel jeu;
        /* Dans positionClique[0] il y a l'indice i de la matrice de la case cliquer et dans positionClique[1] il y a l'index j de la matrice*/
        private int[] positionClique;
        private final  static int blockSize = 90;
        private int niveauActuel;
        /*Image icone qui permet d'avoir toujour l'icone peut importe la fentêre ou on est*/
        private final ImageIcon logo = new ImageIcon("./image/logo.png");

        /*Permet de crée une Fenêtre graphique qui a certaint composant pour le jeu selon le label passer en argument
         * @requires label!= null
         * 
        */
        public InterfaceGraphique(String label){
            super("IA Parabox");
            this.niveauFile = new Fichier();
            this.niveauMap = new Map();
            this.jeu = new JPanel();
            this.positionClique = new int[2];
            this.setIconImage(logo.getImage());
            for(int i = 1 ; i<= 11 ; i++){
                if(label.equals("lvl" + i)){
                    lvl(i);
                }
            }
            if(label.equals("menu")){
                menu();
            }else if(label.equals("start")){
                start();
            }
            if(label.equals("")){
                JFrame zoom = new JFrame();
            }

        }

        
        public static void main(String[] args){
            InterfaceGraphique myInterface = new InterfaceGraphique("menu");
            myInterface.setVisible(true);

        }
        /*Permet d'afficher le jeu avec la map passer en arguement 
         * @param map la map a afficher en la parcourant
         * @param x permet de savoir si on doit afficher la version zoomé des image
         * @throw NullPointerException si map est null
         * @throw IllegaleArgumentExeption si x > 1 &&  x < 0
          */
        public void afficherGraphique(Matrix<TypeObjet> map,int x){
            if(map == null){
                throw new NullPointerException("Map est null");
            }if(x > 1 &&  x < 0){
                throw new IllegalArgumentException("x est égale a autre chose que 1 ou 0 pas normale");
            }
            jeu.setSize(1100,1100);
            jeu.removeAll();
            jeu.validate();
            JPanel dedans = new JPanel(new GridLayout(12,12));

        
            dedans.removeAll();
            dedans.validate();
            for(int i = 0; i < 11;i++){
                for(int j = 0; j < 11; j++){
                    TypeObjet tmp = map.get(j,i);
                    if(tmp == TypeObjet.BLOC){
                        if(x == 1){
                            ImageIcon image = new ImageIcon("./image/petit/bloc.png");
                            JLabel bloc = new JLabel(image);
                            dedans.add(bloc);

                        }else{
                            ImageIcon image = new ImageIcon("./image/bloc.png");
                            JLabel bloc = new JLabel(image);
                            dedans.add(bloc);

                        }
                    }
                    if(tmp == TypeObjet.JOUEUR){
                        if(x == 1){
                            ImageIcon image = new ImageIcon("./image/petit/player.png");
                            JLabel player = new JLabel(image);
                            dedans.add(player);

                        }else{
                            ImageIcon image = new ImageIcon("./image/player.png");
                            JLabel player = new JLabel(image);
                            dedans.add(player);

                        } 
                    }
                    if(tmp == TypeObjet.MUR){
                        if(x == 1){
                            ImageIcon image = new ImageIcon("./image/petit/wall.png");
                            JLabel mur = new JLabel(image);
                            dedans.add(mur);

                        }else{
                            ImageIcon image = new ImageIcon("./image/wall.png");
                            JLabel mur = new JLabel(image);
                            dedans.add(mur);

                        }
                    }
                    if(tmp == TypeObjet.ARRIVE){if(x == 1){
                        ImageIcon image = new ImageIcon("./image/petit/finish.png");
                        JLabel arriver = new JLabel(image);
                        dedans.add(arriver);

                    }else{
                        ImageIcon image = new ImageIcon("./image/finish.png");
                        JLabel arriver = new JLabel(image);
                        dedans.add(arriver);

                    }
                    }if(tmp == TypeObjet.VIDE){
                        if(x == 1){
                            ImageIcon image = new ImageIcon("./image/petit/ground.png");
                            JLabel vide = new JLabel(image);
                            dedans.add(vide);

                        }else{
                            ImageIcon image = new ImageIcon("./image/ground.png");
                            JLabel vide = new JLabel(image);
                            dedans.add(vide);

                        }
                    }
                    if(tmp == TypeObjet.BLOC_MONDE){
                        if(x == 1){
                            ImageIcon image = new ImageIcon("./image/petit/bloc.png");
                            JLabel blocmonde = new JLabel(image);
                            dedans.add(blocmonde);

                        }else{
                            ImageIcon image = new ImageIcon("./image/blocmonde.png");
                            JLabel blocmonde = new JLabel(image);
                            dedans.add(blocmonde);

                        } 
                    }
                }
        }
        dedans.addMouseListener(this);
        jeu.add(dedans);
        jeu.setVisible(true);
        jeu.revalidate();
        jeu.repaint();
        this.add(jeu);

        }
        /*Affiche le menu du jeu premier ecrans du jeu avec des boutton qui sont ajouter a actionPerformed
         * @pure
         */
        public void menu(){
            
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
           
            ImageIcon background = new ImageIcon("./image/menu_background.png");
            JLabel back = new JLabel(background);
            back.setLayout(new BorderLayout());
            
            JButton regle = new JButton("Règles");       
            regle.setFont(new Font("Arial",Font.PLAIN,35));  
            regle.addActionListener(this);
        
            JButton start = new JButton("Jouer");
            start.setFont(new Font("Arial",Font.PLAIN,35));
            start.addActionListener(this);
            JButton quitter = new JButton("Quitter jeu");
            quitter.setFont(new Font("Arial",Font.PLAIN,35));
            quitter.addActionListener(this);

            JPanel buttonPanel = new JPanel();
            buttonPanel.setOpaque(false);
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(start);
            buttonPanel.add(regle);
            buttonPanel.add(quitter);
            back.add(buttonPanel, BorderLayout.SOUTH);
            this.add(back);

            this.setVisible(true);

        }
        /*Affiche la selection des niveaux du jeu avec des boutton qui sont ajouter a actionPerformed 
         * @pure
        */
        public void start(){
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            this.setLayout(new FlowLayout());
            for(int i = 1 ; i <= 11 ; i++){
                JButton lvlButton = new JButton("Niveau " + i);
                lvlButton.setFont(new Font("Arial",Font.PLAIN,35));
                lvlButton.addActionListener(this);
                this.add(lvlButton);
            }
            JButton retour = new JButton("Retour");
            retour.setFont(new Font("Arial",Font.PLAIN,35));
            retour.addActionListener(this);
            this.add(retour);
            this.setLayout(new GridLayout(3,4));

        }
        /*Met une nouvelle fenetre graphique avec le niveau choisie de chargée et l'affiche graphquement  
        *@throw IllegaleArgumentExeption si i > 1 &&  i < 12
        *@pure
        */ 
        public void lvl(int i){
            if(i < 1 || i > 12){
                throw new IllegalArgumentException("le niveau a chargé est pas bon");
            }
            
            this.setExtendedState(JFrame.MAXIMIZED_BOTH);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            niveauFile = new Fichier("./lvl/level_start/niveau"+ i +".txt");
            niveauMap = new Map(niveauFile.getNiveau());
            niveauActuel = i;
            afficherGraphique(niveauMap.getMapType(),0);
        } 
        /*Permet de savoir si le niveau est finie c'est a dire que tous les arrivé on des boîte dessus
         * @pure
        */
        public void endLevel(){
            jeu.removeAll();
            jeu.validate();
            niveauFile.fileDelete();
            JPanel bouttonpanel = new JPanel(new FlowLayout());
            JButton fin = new JButton("Niveau Suivant");
            fin.setFont(new Font("Arial",Font.PLAIN,35));
            fin.addActionListener(this);
            bouttonpanel.add(fin,BorderLayout.LINE_START);
            JButton retour = new JButton("Retour a la selection des niveaux");
            retour.addActionListener(this);
            retour.setFont(new Font("Arial",Font.PLAIN,35));
            bouttonpanel.setLayout(new GridLayout(1,2));
            bouttonpanel.add(retour,BorderLayout.CENTER);
            this.add(bouttonpanel);
            this.repaint();
            this.revalidate();
            jeu.revalidate();
            jeu.repaint();
        }

        @Override
        public void keyPressed(KeyEvent e){
            Direction d = lireInput(e);
            if(d == null){
                throw new NullPointerException("Direction est null");
            }
            if(d == Direction.RESTART){
                restart();
            }else{
                int[] x = niveauMap.getJoueurCoord();
                if(!(niveauMap.niveauFini())){
                    if(niveauMap.getMapType().get(x[0], x[1]) != TypeObjet.BLOC_MONDE){
                        if(niveauMap.estDeplacable(d)){
                            niveauFile.resetTerminal(d, niveauMap);
                            x = niveauMap.getJoueurCoord();
                            if(niveauMap.getObjetRec() != null){
                                niveauMap.getObjetRec().modifierMapParentType(niveauMap.getMapType());
                                niveauMap.modifierMapType(niveauMap.getObjetRec().getMapParent().getMapType());
                                niveauMap.getObjetRec().getMapParent().modifierPile(niveauMap.getPile());
                            }
                            if(niveauMap.getMapType().get(x[0], x[1]) == TypeObjet.BLOC_MONDE){
                                afficherGraphique(niveauMap.getObjetRec().getMap().getMapType(),0);
                            }else{
                                afficherGraphique(niveauMap.getMapType(),0);
                            }
                            this.repaint();
                            if(niveauMap.niveauFini()){
                                this.endLevel();
                            }
                        }
                    }else{
                        if(niveauMap.getObjetRec().estDeplacable(d)){
                            niveauMap.modifierMapType(niveauMap.getObjetRec().getMapParent().getMapType());
                            niveauMap.modifierPile(niveauMap.getObjetRec().getMapParent().getPile());
                            niveauFile.resetTerminal(d, niveauMap);
                            if(niveauMap.getMapType().get(x[0], x[1]) == TypeObjet.BLOC_MONDE){
                                afficherGraphique(niveauMap.getObjetRec().getMap().getMapType(),0);
                            }else{
                                afficherGraphique(niveauMap.getMapType(),0);
                            }
                            this.repaint();
                            if(niveauMap.niveauFini()){
                                this.endLevel();
                            }
                        } 
                    }
                }else{
                    this.endLevel();
                }
            }
        }

        
        @Override
        public void keyTyped(KeyEvent e){
        }
        @Override
        public void keyReleased(KeyEvent e){       
        }
        /*Permert de lire l'input entré par le joueur 
         * @param e la KeyEvent fais par le joueur 
         * @return La direction entré par le joueur 
         * @throw NullPointerException si e est null
         */
        public Direction lireInput(KeyEvent e){
            if(e == null){
                throw new NullPointerException("e est null");
            }
            switch(e.getKeyCode()){
                case 38:
                    return Direction.HAUT;
                case 40:
                    return Direction.BAS;
                case 37:
                    return Direction.GAUCHE;
                case 39:
                    return Direction.DROITE;
                case 82: return Direction.RESTART;
                default :   
                    if(e.getKeyCode() == 90 && e.isControlDown()){
                        return Direction.BACK;
                    }       
                    return null; 

            }
        }
    public void restart(){
        lvl(niveauActuel);
    }
        /*A chaque fois que l'utilisateur clique sur un boutton la methode s'active 
         * @param e ActionEvent du boutton activé 
         * @throw NullPointerException si e est null
           */
        public void actionPerformed(ActionEvent e){
            if(e == null)
                throw new NullPointerException("e est null");
            if(e.getActionCommand().equals("Règles")){
                InterfaceGraphique regle = new InterfaceGraphique("");
                regle.setSize(1000,1000);
                regle.setTitle("Règles");
                ImageIcon image = new ImageIcon("./image/Règles.png");
                JLabel tmp = new JLabel(image);
                regle.add(tmp);
                regle.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                regle.setLocationRelativeTo(null);
                regle.setVisible(true);
            }else if(e.getActionCommand().equals("Jouer") || e.getActionCommand().equals("Retour a la selection des niveaux")){
                InterfaceGraphique start = new InterfaceGraphique("start");
                this.dispose();
                start.setSize(500, 500);
                start.setVisible(true);
            }
            if(e.getActionCommand().equals("Retour")){
                InterfaceGraphique retour = new InterfaceGraphique("menu");
                this.dispose();
                retour.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        
            for(int i = 0; i <= 11;i++){
                if(e.getActionCommand().equals("Niveau "+i)){
                    
                    InterfaceGraphique lvl = new InterfaceGraphique("lvl"+i);
                    this.dispose();
                    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    lvl.setSize(500, 500);
                    lvl.addKeyListener(lvl);
                    lvl.setVisible(true);
                }
            }
            if(e.getActionCommand().equals("Niveau Suivant")){
                niveauActuel++;
                InterfaceGraphique retour = new InterfaceGraphique("lvl"+ niveauActuel);
                this.dispose();
                retour.addKeyListener(retour);
                retour.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                retour.setVisible(true);
            }
            if(e.getActionCommand().equals("Quitter jeu")){
                this.dispose();
            }
        }
      

        @Override
        public void mouseEntered(MouseEvent e) {
        }
        
        @Override
        public void mouseExited(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            int x = e.getX();
            int y = e.getY();
            int i = (int) Math.floor(x / blockSize);
            int j = (int) Math.floor(y / blockSize);
            TypeObjet type = niveauMap.getMapType().get(i, j);
            positionClique[0] = i;
            positionClique[1] = j;
            if(type == TypeObjet.BLOC_MONDE){
                InterfaceGraphique zoom = new InterfaceGraphique("");
                zoom.afficherGraphique(niveauMap.getObjetRec().getMap().getMapType(),1);
                zoom.setLocationRelativeTo(null);
                zoom.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                zoom.setSize(600,600);
                zoom.setVisible(true);
            }
            int[] tmp = niveauMap.getJoueurCoord();
            Resolution res = new Resolution(tmp, positionClique, niveauMap);
            tmp = res.teleport(niveauMap.getMapType());
            niveauMap.modifierCoordRes(tmp);
            afficherGraphique(niveauMap.getMapType(),0);
            if(niveauMap.niveauFini()){
                this.endLevel();
            }
        }


       
        

    }