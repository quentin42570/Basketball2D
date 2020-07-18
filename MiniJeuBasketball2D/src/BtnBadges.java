import java.awt.event.*;

public class BtnBadges implements ActionListener {
    private Palmares fen ;
    private static int compteur = 0 ; //On met en place un compteur pour pouvoir affiche et effacer la fenêtre en appuyant sur le bouton "Mes badges"

    public BtnBadges (Palmares fen){
        this.fen=fen;
    }

	
    public void actionPerformed(ActionEvent e){
		if(compteur % 2 == 0){      //Si le compteur est un nombre pair, la fenêtre s'affiche en appellant la méthode afficherBadge de l'interface Palmarès
			fen.afficherBadge();
		}
		if(compteur % 2 == 1) {		//Si le compteur est un nombre impair, la fenêtre s'efface en appellant la méthode enleverBadge de l'interface Palmarès
			fen.enleverBadge(); 
        }
        compteur ++ ;              	//On incrémente le compteur pour afficher/effacer simultanément la fenêtre des badges à chaque clic sur le bouton
	}
}
