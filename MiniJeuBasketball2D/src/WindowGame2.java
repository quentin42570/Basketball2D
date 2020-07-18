import org.newdawn.slick.* ; 
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

// Interface du jeu de Basketball 2D(MODE 2) : elle permet de gérer tout le rendu graphique du jeu, son initialisation ainsi que son évolution au cours d'une partie. Elle contient également toute la logique du jeu, qui s'appuie sur les classes Partie et Trajectoire.
// Elle contient le mode de jeu alternatif -> le joueur à une minute pour marquer le plus de panier possible. Ensuite la partie se termine et le score est remis à 0.
// Cette classe ne sera pas commenté en grande partie car elle est presque semblable en tout point à la classe WindowGame (mode1) sauf pour une partie de la logique qui sera elle commentée, et l'ajout d'un timer.

public class WindowGame2 extends BasicGameState {
	public static final int ID = 3;
	private StateBasedGame game;
	private boolean escape ; 
	private TrueTypeFont police ; 
	private TiledMap map ; 
	
	// Logique du jeu
	
	private boolean unePartieEnCours ; 
	private int[][] trajectoire ; 
	private int X ; private int Y ; private int itab ; 
	private Partie p ; 
	private Image ballon ; 
	private Image fleche ; 
	
	private float angle ; 
	private float angleav ; 
	private float tourner ; 
	
	private long temps ; 
	private float force ; 
	private boolean tirer ; 	
	private int timer ; // timer

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game ; 
		escape = false ; 
		police = new TrueTypeFont(new java.awt.Font("Serif", 1, 40), false) ; 
		this.map = new TiledMap("map/Basketball2D.tmx");
		
		// Logique du jeu
		
		unePartieEnCours = false ; 
		X = 770 ; Y = 400 ; itab = 0 ; 
		ballon = new Image("ballon2.png");
		fleche = new Image("fleche.png") ; 
		p = new Partie() ; // obliger d'instancier sinon plante
		
		angle = 0 ; 
		angleav = 0 ; 
		tourner = 0 ;	
		
		temps = 0 ; 
		tirer = false ; 	
		timer = 60 ; 
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		this.map.render(0, 0);
		g.setColor(new Color(255,255,255)) ; 
		if(escape == true) {
			/*g.setFont(police) ;*/ 
			g.drawString("Reprendre (R)", 50, 50) ; 
			g.drawString("Menu (A)", 50, 100) ; 
			g.drawString("Quitter (Q)", 50, 150) ; 
			if(escape==false) {
			g.clear() ; 
			}
		}
		
		// Logique du jeu
		
		if(unePartieEnCours == false ) {
			g.drawString(" Appuyez sur la barre d'espace pour jouer ! ", 200, 250) ; 
		}
		ballon.drawCentered(X, Y); 
		fleche.draw( 580, 350, 150, 100) ; 
		fleche.setCenterOfRotation(200,50) ;
		fleche.rotate(tourner) ; 
		g.setFont(police) ;
		g.drawString(" Score : " + p.getScore(), 500, 100) ; 
		g.drawString(" Timer : " + timer, 200, 100) ; // affichage du temps restant, timer
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException  {
		Input input = container.getInput() ; 
		
		if (input.isKeyDown(Input.KEY_ESCAPE)) {
			escape = true ; 
		}
		if(escape == true) {
			if(input.isKeyDown(Input.KEY_R)) {
				escape = false ; 
			}
			if(input.isKeyDown(Input.KEY_A)) {
				game.enterState(MainMenu.ID);
			}
			if(input.isKeyDown(Input.KEY_Q)) {
				System.exit(0) ; 
			}
		}
		
		// Logique du jeu
		
		if(unePartieEnCours == false && input.isKeyDown(Input.KEY_SPACE)) {
			p = new Partie() ; 
			unePartieEnCours = true ; 
		}
		
		if(p.getEtat()%2 == 0) {
			if(tirer == true) {
				trajectoire = p.tirer((float)Math.toRadians((float)angle),force) ; 
				p.setEtat(p.getEtat() + 1) ; 
				itab = 0 ; 
				tirer = false ; 
			}
		}
		
		// Cette fois, si le joueur marque, il se passe toujours la même chose, mais si le joueur ne marque pas, la partie ne se termine pas et le score n'est pas remis à 0, il reste inchangé.
		
		if(p.getEtat()%2 == 1) {
			X = trajectoire[itab][0] ; 
			Y = trajectoire[itab][1] ; 
			if(X != -1 || Y != -1) {
				itab ++ ; 
			}
			
			if(X == -1 || Y == -1 || input.isKeyDown(Input.KEY_E)) {
				if(p.aMarquer(trajectoire) == false) {
					X = 770 ; 
					Y = 400 ; 
					p.setEtat(p.getEtat() + 1) ; 
				}
				else {
					X = 770 ; 
					Y = 400 ; 
					// pour l'instant
					p.setEtat(p.getEtat() + 1) ; 
					p.setScore(p.getScore() + 1) ; 
				}
			}
			
			try {
				Thread.sleep(2) ;  // permet de régler la vitesse de la trajectoire à notre guise
			}catch (InterruptedException a){
				System.out.println (a.getMessage());
			}
		}
		
		// Lorsque le temps de la partie dépasse les 60 secondes, la partie s'arrête, les résultats sont envoyés à l'interface Palmarès, et le jeu est réinitialiser.
		
		if((int)(System.currentTimeMillis()-p.getTemps())>=60000 && unePartieEnCours == true) {
			p.updateTemps() ; 
			// envoyer score et temps à la classe palmarès
			Palmares resultats = new Palmares(false) ; 
			resultats.addCoupleDeDonnees(p.getScore(),(int)p.getTemps()) ; 
			p.setEtat(-1) ; 
			unePartieEnCours = false ; 
			X = 770 ; 
			Y = 400 ; 
			timer = 60 ; // reset du timer
		}
		
		if(unePartieEnCours == true) {  // mise à jour du timer si une partie est en cours
			int t = (int)(System.currentTimeMillis()-p.getTemps()) ; // calcul du temps écoulé
			timer = 60 - (int)(t/1000) ; // mise à jour du timer : 60 secondes - temps écoulé
		}
		
		tourner = angle - angleav ; 
		angleav = angle ; 
    }
    
    @Override
    public void keyReleased(int key, char c) {
		if (Input.KEY_1 == key) {  						
			game.enterState(WindowGame.ID);
		}
	}
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		if(newx < 770 && newy < 400) {
			angle = (float)Math.atan(((double)400.0-newy)/((double)770.0-  newx)) ; 
		} else {
			angle = 0 ; 
			angleav = 0 ; 
		} 
		
		angle = (float)Math.toDegrees((float)angle) ; 
	}
	
	@Override
	public void mousePressed(int button, int x, int y){
		if(p.getEtat()%2 == 0) {
			temps = System.currentTimeMillis() ; 
		}
	}
	
	@Override
	public void mouseReleased(int button, int x, int y)  {
		if(p.getEtat()%2 == 0) {
			temps = System.currentTimeMillis() - temps ; 
			if(temps >= 5000) {
				force = 1000 ; 
			} else {
				force = (float)((temps)/(10.0) + 500.0) ; 
			}
			tirer = true ; 
		}
	}
	
    
    @Override
    public int getID() {
		return WindowGame2.ID;
    }
}
