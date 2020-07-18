


public class Trajectoire {
	
	public double angleInit;
	private double vitInit;
	public double bxInit;
	public double byInit;
	private int ySol;
	private int yPanier;
	private int xAnneau1;
	private int xAnneau2;
	private int xPanneau;
	private int yBasPanneau;
	private int yHautPanneau;
	private int rBallon;
	private int xLimGauche;
	private int xLimDroite;
	private int yLimHaut;
	private double g ;
	private int rAnneau;
	
	public Trajectoire (double angle, double vitInit) {
		// Dans le constructeur de la trajectoire, on intègre d'abord les valeurs d'angle et de vitesse fournies par la classe Partie, puis 
		//on introduit tous les paramètres du jeu (position initiale et rayon du ballon, valeur de la gravité, positions du sol, de l'anneau, du panneau, des 
		// limites de la fenêtre de jeu,...)
		angleInit = 3.14159-angle;
		this.vitInit=vitInit; 
		bxInit = 770; // position initiale du ballon
		byInit=400;
		ySol=425; // altitude du sol
		yPanier=210; // altitude du panier
		xAnneau1=130; // positions des centres des anneaux selon x
		xAnneau2=190;
		xPanneau=125; // position du panneau selon x
		yBasPanneau=220; // altitude du bas du panneau
		yHautPanneau=100; // altitude du haut du panneau
		rBallon=18; // rayon du ballon
		xLimGauche=20; // limite du terrain à gauche
		xLimDroite=800; // limite du terrain à droite
		yLimHaut=0; // limite du terrain en haut
		g =691.5; // 691,5 pixels dans le jeu correspondent à 4,905 m dans la réalité, soit la moitié de 9,81 (car dans les calculs on utilise uniquement g/2)
		rAnneau=2;

	}

	public int[][] getTrajectoire(){ // méthode appelée lors du lancer pour obtenir l'ensemble des positions à afficher
		int[][] positions = new int [50000][2];// on crée le tableau qui stockera toutes les valeurs prises par les coordonnées du ballon. Sa taille excessive (50000) nous assure de ne pas dépasser.
		double bx = bxInit;// le ballon prend sa position initiale
		double by = byInit;
		double bxPrecedent = bxInit;
		double byPrecedent = byInit;
		double angle=angleInit; //les paramètres de la première portion de parabole sont simplement ceux admis lors du lancer
		double v=vitInit;
		double t=0; // initialisation de la variable temporelle
		int i = 0; // variable nous servant à parcourir le tableau
		
		while (bx< xLimDroite && bx> xLimGauche && by< ySol + rBallon && i <= 49995){ // tant que le ballon est dans l'air de jeu 
				if((bx-xAnneau1)*(bx-xAnneau1)+(by-yPanier)*(by-yPanier)<=(rBallon+rAnneau)*(rBallon+rAnneau)){// le ballon touche-t_il l'anneau 1?
					// établissement des nouvelles données de la trajectoire après le rebond
					double tetaN = Math.atan(Math.abs(yPanier-byPrecedent)/Math.abs(xAnneau1-bxPrecedent));// calcul de l'angle entre la normale au contact et l'horizontale
					double tetaI = Math.atan(Math.abs(by-byPrecedent)/Math.abs(bx-bxPrecedent))- tetaN;// calcul de l'angle d'incidence du ballon 
					angle=3.14159-tetaN-tetaI; // calcul de l'angle avec lequel le ballon repart
					v=v*0.6; // diminution de la vitesse pour simuler la perte d'énergie lors du rebond
					t=0; // réinitialisation de la varable temporelle
					bxInit=bxPrecedent; // réinitialisation des coordonées du ballon à sa position actuelle
					byInit=byPrecedent;		
				}
				if((bx-xAnneau2)*(bx-xAnneau2)+(by-yPanier)*(by-yPanier)<=(rBallon+rAnneau)*(rBallon+rAnneau)){// le ballon touche-t_il l'anneau 2?
					// établissement des nouvelles données de la trajectoire après le rebond suivant la même technique
					double tetaN = Math.atan(Math.abs(yPanier-byPrecedent)/Math.abs(xAnneau2-bxPrecedent));
					double tetaI = Math.atan(Math.abs(by-byPrecedent)/Math.abs(bx-bxPrecedent))- tetaN;
					angle=3.14159-tetaN-tetaI;
					v=v*0.6;
					t=0;
					bxInit=bxPrecedent;
					byInit=byPrecedent;
				}
				if (by< yPanier + 7 && by > yPanier - 7 && xAnneau1+rAnneau+rBallon<bx && bx<xAnneau2-rBallon-rAnneau){// le ballon passe t'il dans l'anneau?
					// le ballon part en ligne droite vers le bas
					v = 0; // en annulant la vitesse initiale du ballon, celui-ci part aussitôt en chute libre (ligne droite)
					t=0;
					bxInit=bxPrecedent;
					byInit= yPanier + 8; // On ajoute 8 pour s'assurer que le ballon ne "réapparait" pas dans la zone qui valide la condition, on évite ainsi de rentrer dans une boucle infinie			
				}
				if (bx<=xPanneau+rBallon && yBasPanneau>=by && by>=yHautPanneau){// le ballon touche-t_il le panneau?
					// établissement des nouvelles données de la trajectoire après le rebond
					double tetaI=Math.atan(Math.abs(by-byPrecedent)/Math.abs(bx-bxPrecedent)); // calcul de l'angle d'incidence du ballon
					angle=-tetaI; // calcul de l'angle avec lequel le ballon repart
					v=0.6*v;
					t=0;
					bxInit=bxPrecedent;
					byInit=byPrecedent;
				}
				bxPrecedent=bx; // on stocke la position précédente
				byPrecedent=by;
				bx=v*Math.cos(angle)*t+bxInit; // calcul de la nouvelle position du ballon
				by=t*t*g/2 - v*Math.sin(angle)*t+byInit;
				t=t+0.005; // incrémentation du temps de 5 millièmes de secondes, pour une trajectoire fluide
				positions[i][0] = (int)bx; // remplissage du tableau avec la position actuelle
				positions [i][1] = (int)by;
				i++;	
			
			}
	 
		positions[i][0] = -1; // pour signaler la fin du tableau
		positions[i][1] = -1;

		return positions ;  
	}
			
		

}

