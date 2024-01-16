JC = javac
JVM= java
RM= rm
.SUFFIXES: .java .class
.java.class:
		$(JC) -g $*.java
CLASSES = \
		InterfaceGraphique.java \
		Fichier.java \
		FichierInterface.java \
		Map.java\
		MapInterface.java\
		Matrix.java\
		Direction.java\
		RecFichier.java\
		RecMap.java\
		TypeObjet.java
MAIN = InterfaceGraphique

run: $(MAIN).class
		$(JVM) $(MAIN)
		$(RM) -f *.class
		$(RM) -f ./lvl/level_update/niveau*

clean:
		$(RM) -f *.class
		$(RM) -f ./lvl/level_update/niveau*

