
// A chaque fois que le jeu démarre ou que le joueur à perdu, une nouvelle instance de Partie est créé. Elle garde en mémoire le score et le temps lié à la partie;
// Chaque partie à également différents états. les états pairs signifient qu'aucun tir est en cours et que le joueur peut tirer. Les états pairs signifient qu'un lancer est en cours donc le joueur ne peut pas tirer lorsqu'un lancer est déjà en cours. L'état -1 signifie que le joueur à perdu, la partie est terminé.
 
public class Partie {
	private int etat ; 
	private int score ; 
	private long temps ; 
	
	private int test ; 
	
	// Constructeur de la classe partie, initialise les attributs : score à 0, temps = temps réel, et l'état est égal à 0 car aucun lancer est en cours, le joueur peut tirer.

	public Partie() {
		etat = 0 ; 
		score = 0 ; 
		temps = System.currentTimeMillis() ; 
		
		test = 0 ; 
	}
	
	// Méthode qui recoit un angle et une vitesse de la part du jeu, qui créé une trajectoire en appelant le générateur de trajectoire, et qui renvoie la trajectoire au jeu.
	
	public int[][] tirer(float angle, float vitesse) {
		Trajectoire t = new Trajectoire((double)angle, (double)vitesse) ; 
		return t.getTrajectoire() ; 
	}
	
	// Méthode qui permet de tester si le ballon rentre dans le panier : si la trajectoire du ballon passe par le panier défini comme un rectangle, la méthode renvoie true. Si ce n'est pas le cas, elle renvoie false, le joueur n'a pas marqué.
	
	public boolean aMarquer(int [][] trajectoire) {
		boolean retour = false ; 
		for(int i = 0 ; i< trajectoire.length ; i++) {
			if(trajectoire[i][0] <= 170 && trajectoire[i][0] >= 150 && trajectoire[i][1] <= 220 && trajectoire[i][1] >= 203) {
				retour = true ; 
			}
		}
		
		
		/*if(test >= 3) {
			retour = false ; 
		}
		test ++ ; */
		
		return retour ; 
	}
	
	// permet de connaître l'état de la partie à tout moment
	
	public int getEtat() {
		return etat ; 
	}
	
	// permet de mettre à jour l'état de la partie au fur et à mesure que le jeu avance
	
	public void setEtat(int i) {
		etat = i ; 
	}
	
	// permet d'obtenir le score en fin de partie pour l'envoier à l'interface palmarès qui l'affichera
	
	public int getScore() {
		return score ; 
	}
	
	// permet de modifier le score au fur et à mesure de l'avancement de la partie
	
	public void setScore(int i) {
		score = i ; 
	}
	
	// permet à la fin de la partie d'obtenir le temps total mis par le joueur avant de perdre
	
	public void updateTemps() {
		temps = System.currentTimeMillis() - temps ; 
	}
	
	// permet d'obtenir la durée de la partie en fin de partie pour l'envoier à l'interface palmarès qui la traitera pour afficher des données
	
	public int getTemps() {
		return (int)temps ; 
	}

}
