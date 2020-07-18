import org.newdawn.slick.* ; 
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

// Interface du jeu de Basketball 2D(MODE 1) : elle permet de gérer tout le rendu graphique du jeu, son initialisation ainsi que son évolution au cours d'une partie. Elle contient également toute la logique du jeu, qui s'appuie sur les classes Partie et Trajectoire.
// UPDATE : Elle contient le mode de jeu principal -> tant que le joueur marque la partie continue. S'il rate le panier, le joueur à perdu, la partie est terminé et le score est remis à 0.

// Elle a comme attributs principaux son identifiant, une instance du jeu, un boolean escape qui stocke si le joueur a appuyer sur le bouton escape ou non, une police, et une image de fond de type TiledMap, c'est à dire une carte de jeu 2D qui a été créé avec un éditeur de carte(Tiled)


public class WindowGame extends BasicGameState {
	public static final int ID = 1;
	private StateBasedGame game;
	private boolean escape ; 
	private TrueTypeFont police ; 
	private TiledMap map ; 
	
	// Les attributs liées à la Logique du jeu
	
	private boolean unePartieEnCours ; // Stocke si une partie est déjà en cours ou non 
	private int[][] trajectoire ; // Stocke une trajectoire
	private int X ; private int Y ; private int itab ; // X et Y stockent les coordonnées du ballon, itab est la grandeur qui permet de parcourir le tableau de coordonnées
	private Partie p ; // stocke une instance de la classe partie
	private Image ballon ; // stocke l'image du ballon
	private Image fleche ; // stocke l'image de la flèche de direction
	
	private float angle ; // stocke l'angle entre la droite passant par le curseur de la souris et le centre du ballon, et la droite horizontale passant par le centre du ballon, nécessaire pour orienter la flèche directionnelle 
	private float angleav ; // stocke l'angle précédent i-1
	private float tourner ; // stocke la valeur en degré dont la flèche directionnelle doit tourner
	
	private long temps ; // stocke la durée durant laquelle le joueur a laisser enfoncé le bouton de la souris
	private float force ; // stocke un float qui représente la force donner au ballon
	private boolean tirer ; // stocke si le joueur a envoyé des données ou non (clic de souris)

	// Méthode d'initialisation de tous les attributs

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
		this.game = game ; 
		escape = false ; // escape est par défaut initialiser en false 
		police = new TrueTypeFont(new java.awt.Font("Serif", 1, 40), false) ; // on importe une police qui peut ensuite être facilement changé en modifiant les 3 paramètres
		this.map = new TiledMap("map/Basketball2D.tmx"); // on charge le background
		
		// Logique du jeu
		
		unePartieEnCours = false ; // Au début, aucune partie est en cours donc false
		X = 770 ; Y = 400 ; itab = 0 ; // Les coordonnées sont initialisés à la position initial du ballon avant un lancer. La grandeur qui permet de suivre l'avancer dans le tableau trajectoire est initialiser à 0
		ballon = new Image("ballon2.png"); // On importe l'image du ballon
		fleche = new Image("fleche.png") ; // On importe l'image de la flèche
		p = new Partie() ; // on est obliger de créer une instance de la classe partie même si aucune partie est en cours sinon la classe ne veut pas compiler
		
		angle = 0 ; // ces grandeurs sont par défaut initialiser à 0 car elles n'ont pas encore de valeur
		angleav = 0 ; 
		tourner = 0 ;	
		temps = 0 ; 
		
		tirer = false ; // initialiser à false
    }

	// Méthode qui gère tout l'affichage graphique de type boucle while infinie

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		this.map.render(0, 0); // On affiche maintenant le background à l'écran
		g.setColor(new Color(255,255,255)) ; // On modifie la couleur de police en blanc
		
		// Si le joueur appuye sur escape, un menu s'ouvre lui proposant de revenir au jeu, retourner au menu, ou de quitter définitivement le jeu. Si la variable escape repasse en false, le menu disparaît. 
		
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
		// Si aucune partie n'est en cours, un message s'affiche à l'écran pour proposer au joueur de lancer une partie
		
		if(unePartieEnCours == false ) {
			g.drawString(" Appuyez sur la barre d'espace pour jouer ! ", 200, 250) ; 
		}
		
		// On dessine le ballon aux coordonnées X et Y qui peuvent évoluer. Le ballon est représenté dans le repère par son centre
		
		ballon.drawCentered(X, Y); 
		
		// On dessine la flèche et on place son centre de rotation sur le centre du ballon, ainsi la flèche tourne autour du ballon lorsqu'il n'est pas en l'air
		
		fleche.draw( 580, 350, 150, 100) ; 
		fleche.setCenterOfRotation(200,50) ;
		
		// La flèche tourne lorsque la valeur tourner n'est pas nulle
		
		fleche.rotate(tourner) ; 
		
		// Affichage du score
		
		g.setFont(police) ;
		g.drawString(" Score : " + p.getScore(), 400, 100) ; 
    }
    
    // Méthode qui contient la logique du jeu de type boucle while infinie. Elle permet de mettre à jour les attributs en traitant les données extérieurs (cliques de souris, appuye sur une touche de clavier, ...)

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException  {
		
		Input input = container.getInput() ; // On créé une instance de la classe input pour pouvoir écouter
		
		// Logique du menu escape : Si le joueur appuye sur escape, la valeur true est stocké dans la variable escape. Si cette variable stocke la valeur true, alors si le joueur appuye sur R, escape redevient false, le menu se ferme. Si le joueur appuye sur A (Q en QWERTY), le jeu revient au menu principal. Si le joueur appuye sur Q (A en QWERTY), le jeu se ferme définitivement.
		
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
		// Si aucune partie est en cours et que le joueur appuye sur la barre d'espace, une partie est créé et on donne logiquement la valeur true à la variable unePartieEnCours 
		
		if(unePartieEnCours == false && input.isKeyDown(Input.KEY_SPACE)) {
			p = new Partie() ; 
			unePartieEnCours = true ; 
		}
		
		// Si l'état de la partie est pair, aucun tir est en cours, le jeu attend l'entré de données venant du joueur (clic de souris)
		// Si l'état de la partie est pair, et qu'on acquiert des données en entrée (tirer = true), alors ces données sont traités pour créer une trajectoire stockée dans la variable trajectoire
		// L'état du jeu devient impair (un tir est en cours), et tirer redevient false pour pouvoir recevoir de nouvelles données du joueur lors du prochain lancer
		
		if(p.getEtat()%2 == 0) {
			if(tirer == true) {
				trajectoire = p.tirer((float)Math.toRadians((float)angle),force) ; // l'angle et la force de lancé sont calculés par des méthodes plus bas
				p.setEtat(p.getEtat() + 1) ; 
				itab = 0 ; 
				tirer = false ; 
			}
		}
		
		// Si l'état de la partie est impair, c'est qu'un lancer est en cours, le ballon parcourt le tableau de trajectoire jusqu'à atteindre la fin de la trajectoire repéré par les coordonnées (-1,-1)
		// Lorsqu'ons arrive à la fin du tableau, on test si le ballon est passer dans le panier ou non à l'aide d'une méthode de la classe partie : 
		// Si le joueur a marqué, le ballon revient à sa position initial, le score augmente de 1 et l'état de la partie redevient pair (prête pour un nouveau lancer)
		// Si le joueur n'a pas marqué, le ballon revient à sa position initial, le temps de la partie s'arrête, une instance de l'interface Palmarès est créé( en visible = false ) pour pouvoir lui envoyer les résultats (score + temps) qui seront stockés dans des LinkedList static pour être conservés par toutes les instances, l'état de la partie devient -1 -> la partie est fini, unePartieEnCours repasse à false -> le joueur peut relancer une nouvelle partie en appuyant sur la barre d'espace
		
		if(p.getEtat()%2 == 1) {
			X = trajectoire[itab][0] ; 
			Y = trajectoire[itab][1] ; 
			if(X != -1 || Y != -1) {
				itab ++ ; // Si on est pas à la fin du tableau, itab s'implemente pour passer aux prochaines coordonnées
			}
			if(X == -1 || Y == -1 || input.isKeyDown(Input.KEY_E)) { // Si on est à la fin du tableau, ou si la touche E est pressé -> sécurité pour pouvoir réinitialisé la partie si le ballon se coince
				if(p.aMarquer(trajectoire) == false) {
					X = 770 ; 
					Y = 400 ; 
					p.updateTemps() ; 
					// envoyer score et temps à la classe palmarès
					Palmares resultats = new Palmares(false) ; 
					resultats.addCoupleDeDonnees(p.getScore(),(int)p.getTemps()) ; 
					p.setEtat(-1) ; 
					unePartieEnCours = false ; 
				}
				else {
					X = 770 ; 
					Y = 400 ; 
					// pour l'instant
					p.setEtat(p.getEtat() + 1) ; 
					p.setScore(p.getScore() + 1) ; 
				}
			}
			
			// Dans la boucle if, permet de régler la vitesse à laquel est parcouru le tableau de trajectoire et donc de rentre la trajectoire plus ou moins fluide et plus ou moins vraie
			// On est obliger de placer cette instruction dans un try catch pour compiler
			
			try {
				Thread.sleep(2) ;  // permet de régler la vitesse de la trajectoire à notre guise
			}catch (InterruptedException a){ 
				System.out.println (a.getMessage());
			}
		}
		
		// On est obliger de réaliser ce petit algorithme car comme l'on se trouve dans des boucles infinis, si l'on s'assure pas que tourner les 0 lorsque la souris ne bouge plus, la flèche va continuer de tourner en continue d'un angle x toutes les x ms
		
		tourner = angle - angleav ; // L'angle dont la flèche doit tourner à chaque mouvement de flèche est le nouveau angle moins l'angle précédent
		angleav = angle ; // angleav prend la valeur de l'ancien angle
    }
    
    // Méthode qui s'active à chaque fois qu'une touche du clavier est relaché. Ici on rentre juste dans le MODE 2 du jeu, mode alternatif, en appuyant sur le chiffre 2 du clavier
    
    @Override
    public void keyReleased(int key, char c) {
		if (Input.KEY_2 == key) {  						
			game.enterState(WindowGame2.ID);
		}
	}
	
	// Méthode qui s'active lorsque la souris bouge. Si la souris se situe à gauche et au dessus de la position initial du ballon, on récupère sa position, on calcule l'angle par rapport à l'horizontal passant par le ballon à l'aide d'un artan. Cette angle servira à orienter la flèche directionnelle, mais aussi à créer une trajectoire fonction de cet angle de lancé
	// Boucle while infinie
	
	@Override
	public void mouseMoved(int oldx, int oldy, int newx, int newy) {
		if(newx < 770 && newy < 400) {
			angle = (float)Math.atan(((double)400.0-newy)/((double)770.0-  newx)) ; 
		} else { // Si l'on est pas dans le champ de lancer, la flèche directionnel ne tourne plus car 0-0 = 0
			angle = 0 ; 
			angleav = 0 ; 
		} 
		
		angle = (float)Math.toDegrees((float)angle) ; 
	}
	
	// Méthode qui s'actuve lorsuqu'un bouton de la souris est pressé
	// ici pour jauger la forcer donner au ballon, on se sert du temps où le bouton est resté pressé. Plus le joueur va presser le bouton de la souris longtemps, plus le ballon aura de la force au lancé
	// Ici lorsque la souris est pressé le timer se lance
	
	@Override
	public void mousePressed(int button, int x, int y){
		if(p.getEtat()%2 == 0) {
			temps = System.currentTimeMillis() ; 
		}
	}
	
	// Lorsque la souris est relaché, le timer se fige. Le temps est converti en une force comprise entre 5 et 10, on a réussi l'acquisition de données -> tirer devient true, on récupère cet force et l'angle calculer plus haut pour générer une trajectoire
	
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
		return WindowGame.ID;
    }
}
