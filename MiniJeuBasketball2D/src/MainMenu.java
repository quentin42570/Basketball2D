import org.newdawn.slick.* ; 
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

// Premier état, interface du jeu. Le menu d'accueil. 
// Elle a comme attribut son identifiant qui est public pour que les autres états puissent y accèder, l'image de fond, une police d'écriture et une instance du jeu (StateBasedGame) qui permet de passer d'un état à l'autre

public class MainMenu extends BasicGameState {
	 public static final int ID = 0;
	 private Image background;
	 private StateBasedGame game;
	 private TrueTypeFont police ; 
	
// La méthode init est équivalente au constructeur d'une classe
// Chaque attribut est instancier / L'image de fond est chargé depuis le répertoire du projet / La police est en Arial par défaut mais elle peut ainsi être changé facilement

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game ; 
		background = new Image("fond.jpg");
		police = new TrueTypeFont(new java.awt.Font("Arial", 1, 20), false) ; 
		
    }

// Méthode qui s'occupe de tout ce qui est affichage graphique


    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		background.draw(0, 0, container.getWidth(), container.getHeight());  // Dessine l'image à la taille du conteneur pour que ce soit une image d'arrière plan. l'image est repéré dans le repère par son coin haut gauche. Or l'origine du repère se trouve en haut à gauche du conteneur. Donc on les fait coïncider (0,0)
		g.setFont(police) ; 												 // Permet de dire que la police d'écriture sera maintenant celle initialisé dans la méthode init et non plus celle par défaut.
		g.setColor(new Color(255,255,255)) ; 								 // Permet de changer la couleur d'écriture : 255 255 255 correspond au code couleur du blanc en RVB
		g.drawString("Jouer (J)", 50, 450) ;     							 // Méthode qui permet d'afficher un texte à des coordonnées précises.
		g.drawString("Regles (R)", 250, 450) ; 
		g.drawString("Palmares (P)", 450, 450) ; 
		g.drawString("Quitter (Q)", 650, 450) ;
    }
    
    // Méthode qui gère la logique du jeu et permet de la mettre à jour. Ici elle ne fait rien car l'interface Menu n'a besoin d'aucune logique. Elle est cependant nécessaire pour la structure du code imposé par la bibliothèque.

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
    }
    
    // Méthode qui s'actionne quand un bouton du clavier est relaché et permet d'accèder précisement à la touche relacher
    
    @Override
    public void keyReleased(int key, char c) {
		if (Input.KEY_J == key) {  						// Si la touche J est relaché, on passe à l'état du jeu qui permet de joueur, on change d'interface
			game.enterState(WindowGame.ID);
		}
		if (Input.KEY_Q == key) {						// Si la touche Q est relaché sur le clavier, le jeu s'éteind.
			System.exit(0) ; 
		}
		if (Input.KEY_R == key) {						// Si la touche R est relaché, on change d'état, c'est l'interface contenant les règles qui s'affiche. 
			game.enterState(Regles.ID);
		}
		if (Input.KEY_P == key) {						// Si la touche P est relaché, l'interface palmares avec les resultats s'ouvre. 
			Palmares resultats = new Palmares(true) ;
		}
	}
    
    @Override
    public int getID() {
		return MainMenu.ID;
	}
	
}
