import java.awt.event.*;

public class GraphiqueTemps implements ActionListener {
    private Palmares fen ;
	private static int compteur = 0 ;   //On met en place un compteur pour pouvoir affiche et effacer la fenêtre en appuyant sur le bouton "Temps"
	
    public GraphiqueTemps(Palmares fen){
        this.fen=fen;
    }

    public void actionPerformed(ActionEvent e){
		if(compteur % 2 == 0) {			 //Si le compteur est un nombre pair, la fenêtre s'affiche en appellant la méthode afficherGraphTemps de l'interface Palmarès
			fen.afficherGraphTemps();  
        } 
        if(compteur % 2 == 1) {			 //Si le compteur est un nombre impair, la fenêtre s'efface en appellant la méthode enleverGraphTemps de l'interface Palmarès
			fen.enleverGraphTemps();
		}
		compteur++; 					 //On incrémente le compteur pour afficher/effacer simultanément la fenêtre contenant le graphique du score par millisecondes par partie à chaque clic sur le bouton
	}
}
