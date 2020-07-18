import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurQuitter implements ActionListener {
	private Palmares fen;

	public EcouteurQuitter(Palmares fen) {
		this.fen = fen;

	}

	// Permet de "fermer" la fenêtre palmarès sans que tout le jeu se ferme
	public void actionPerformed(ActionEvent e) {
		fen.hide();
	}
}
