import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

// Classe nécessaire pour faire un jeu avec différents états, différentes "interface". Elle n'est en revanche d'aucune utilité si le jeu ne possède qu'une interface graĥique. 
// Elle permet d'initialiser les différents états du jeu : ici 3 états, interfaces graphiques avec la méthode initStatesList() : Un menu, l'interface de jeu, et une interface pour les règles. Le première état ajouté dans la méthode est l'état qui sera visible à l'ouverture du jeu.
// Chaque classe correspondant à un état du jeu est identifié par un identifiant unique. C'est grace à cet identifiant qu'on peut ensuite passer d'un état à un autre
// Cette classe contient également le main. Pour éxécuter le jeu, on le place dans un conteneur de jeu que l'on affiche avec la méthode start() ; 

public class ControleInterfaces extends StateBasedGame {

	public ControleInterfaces() {
		super("Basketball 2D");
	}

	/**
	 * Ici il suffit d'ajouter nos deux boucles de jeux. La première ajoutèe sera
	 * celle qui sera utilisée au début
	 */
	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		this.addState(new MainMenu());
		this.addState(new WindowGame());
		this.addState(new WindowGame2());
		this.addState(new Regles());
	}

	public static void main(String[] args) throws SlickException {
		AppGameContainer app = new AppGameContainer(new ControleInterfaces(), 800, 500, false);
		app.setShowFPS(false);
		app.start();
	}
}
