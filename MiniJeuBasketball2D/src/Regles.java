import org.newdawn.slick.* ; 
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

// Etat 2 du jeu : l'interface des règles
// Elle a comme attribut son identifiant qui est public pour que les autres états puissent y accèder, l'image de fond, une police d'écriture et une instance du jeu (StateBasedGame) qui permet de passer d'un état à l'autre

public class Regles extends BasicGameState {
	public static final int ID = 2;
	private StateBasedGame game;
	private TrueTypeFont police ;
	private Image background; 
	
// Méthode d'initialisation des attributs
// Chaque attribut est instancier / L'image de fond est chargé depuis le répertoire du projet / Aucune police n'est chargé, l'attribut est là simplement si plus tard on décide de changer la police

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game ;
		background = new Image("joueur.png");
    }
    
// Méthode qui s'occupe de tout ce qui est affichage graphique

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setColor(new Color(255,255,255)) ;        													// Permet de changer la couleur d'écriture
		background.draw(300, 0, 400, 600); 																// Dessine l'image dans le container suivant une taille et des coordonnées précises
		g.drawString("Une fois sur l’interface de jeu, après avoir cliqué sur la touche J dans le menu, ", 40, 130) ; 		// Méthode qui permet d'afficher du texte
		g.drawString("vous pouvez passer d’un mode de jeu à l’autre en cliquant sur la touche 1 ou  2.", 40, 150) ; 
		g.drawString("Pour lancer une partie, il suffit d’appuyer sur la barre d’espace.", 40, 170) ; 
		g.drawString("Ensuite, pour tirer, déplacer le curseur de la souris : ", 40, 190) ; 
		g.drawString("la flèche d’aide à la trajectoire vous indiquera l’angle de lancer.", 40, 210) ; 
		g.drawString("Appuyez plus ou moins longtemps pour tirer plus ou moins fort.", 40, 230) ; 
		g.drawString("Si vous bloquez le ballon, appuyez sur la touche E.", 40, 250) ; 
		g.drawString("Si vous souhaitez revenir au menu pour voir vos résultats, ou quitter le jeu,", 40, 270) ; 
		g.drawString("appuyez sur la touche esc à n’importe quel moment dans l’interface jeu.", 40, 290) ; 
		g.drawString("Un menu vous donnant des indications s affichera.", 40, 310) ; 
		g.drawString("Let s play !!!", 40, 330) ; 
		g.drawString("< Appuyez sur n'importe quelle touche pour revenir au menu ! >", 40, 370) ; 
		
		
    }
    
    // Méthode qui gère la logique du jeu et permet de la mettre à jour. Ici elle ne fait rien car l'interface Menu n'a besoin d'aucune logique. Elle est cependant nécessaire pour la structure du code imposé par la bibliothèque.

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {		
    }
    
    // Méthode qui s'actionne quand un bouton du clavier est relaché et permet d'accèder précisement à la touche relacher
    
    @Override
    public void keyReleased(int key, char c) {
		game.enterState(MainMenu.ID);				// si n'importe qu'elle bouton est relaché, revient à l'interface du Menu
	}
	 
    @Override
    public int getID() {
		return Regles.ID;
    }
}
