import java.awt.event.*;

public class GraphiqueScore implements ActionListener {
    private Palmares fen ;
    private static int compteur = 0 ; 	//On met en place un compteur pour pouvoir affiche et effacer la fenêtre en appuyant sur le bouton "Scores"

    public GraphiqueScore(Palmares fen){
        this.fen=fen;
        
    }

    public void actionPerformed(ActionEvent e){
		if(compteur % 2 == 0) {			 //Si le compteur est un nombre pair, la fenêtre s'affiche en appellant la méthode afficherGraphScore de l'interface Palmarès
			fen.afficherGraphScore();
        }
        if(compteur % 2 == 1) {			 //Si le compteur est un nombre impair, la fenêtre s'efface en appellant la méthode enleverGraphScore de l'interface Palmarès
			fen.enleverGraphScore();
		}
		compteur++ ; 					 //On incrémente le compteur pour afficher/effacer simultanément la fenêtre contenant le graphe des scores par partie à chaque clic sur le bouton
	}
}
