import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.LinkedList;
import org.knowm.xchart.*;
import java.awt.Image ; 


public class Palmares extends JFrame {
	//Image de l'écran d'acceuil 
	private ImageIcon icon ; 
	
	//Images des badges 
	private ImageIcon badge5; 
	private ImageIcon badge10; 
	private ImageIcon badge20;
	private ImageIcon badge30; 
	private ImageIcon badge50; 
	private ImageIcon badge100;

		
	//Label 
	private JLabel img ;
	private JLabel lbadge5;
	private JLabel lbadge10;
	private JLabel lbadge20;
	private JLabel lbadge30;
	private JLabel lbadge50;
	private JLabel lbadge100;
	
	//Boutons 
	private JButton graphiqueTemps ; 
	private JButton graphiqueScore ;
	private JButton btnBadges ; 
	private JButton quitter ; 
	
	//Panels 
	private JPanel bandeHaut ;
	private JPanel cadrePrincipal ; 
	private XChartPanel<CategoryChart> xPanel; 
	private XChartPanel<CategoryChart> xPanel2; 
	private JPanel badges ; 
	//private XChartPanel<PieChart> xPanel ; --> Si on veut un autre graphe
	
	//LinkedLists : une comprenant les scores marqués par partie et les scores par millisecondes dans l'autre 
	private static LinkedList<String> lesNoms = new LinkedList<String>(java.util.Arrays.asList("Quentin","Edouard","Julita","Quentin","Edouard","Julita")); 
	private static LinkedList<Integer> lesScores = new LinkedList<Integer>(java.util.Arrays.asList(5,10,20,30,50,100));
    private static LinkedList<Integer> scoreParTemps = new LinkedList<Integer>(java.util.Arrays.asList(10,11,12,13,14,15));
        
	//Constructeur de l'interface : on met un paramètre "visible" exceptionnellement pour gérer l'affichage de cette interface
	public Palmares (boolean visible) {
		super("Palmares");
		setSize(1500,1000);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
		//Instanciation des boutons 
		graphiqueTemps = new JButton("Performances"); //Bouton qui affiche un graphique du score par millisecondes lors d'une partie 
        graphiqueScore = new JButton("Scores"); //Bouton qui affiche un graphique des points marqués par partie 
        btnBadges = new JButton("Mes badges"); //Bouton qui affiche les badges obtenus par le joueur
        quitter = new JButton("Quitter") ; //Bouton qui permet de quitter l'interface palmarès sans fermer le jeu en entier
		
		// ====== Organisation structurelle ======
		
		//Bande du haut contenant les boutons
        bandeHaut = new JPanel();
        bandeHaut.setBackground(Color.gray);
        bandeHaut.add(graphiqueTemps);
        bandeHaut.add(graphiqueScore);
        bandeHaut.add(btnBadges); 
        bandeHaut.add(quitter) ; 

		//Fenêtre principale contenant le fond 
        cadrePrincipal= new JPanel(new BorderLayout());
        cadrePrincipal.add(bandeHaut,BorderLayout.NORTH);
        
        //Instanciation de l'image de la page d'accueil 
        icon = new ImageIcon("Images/podium.jpg");
		img = new JLabel(icon);
		cadrePrincipal.add(img);
		
		add(cadrePrincipal);
		
		//Liaisons boutons/écouteurs
        graphiqueTemps.addActionListener(new GraphiqueTemps(this));
        graphiqueScore.addActionListener(new GraphiqueScore(this));
        btnBadges.addActionListener(new BtnBadges(this)); 
        quitter.addActionListener(new EcouteurQuitter(this)) ; 
        
        setVisible(visible);
        		
 }

	//Méthode qui ajoute à la fin de chaque LinkedList un temps et un score 
	public void addCoupleDeDonnees(int score, int temps) {
		lesNoms.add("User");      //On ajoute à chaque fois un user automatiquement
		double tempsec = temps/1000.0 ; 
		scoreParTemps.add((int)((5*score)/(int)tempsec));   //Le temps étant en millisecondes on divise par 1000
		lesScores.add(score); 
		
	}
		
	//Méthode qui permet d'afficher le graphique de temps en appuyant sur le bouton "Temps"	: deux types de graphes possibles 
	public void afficherGraphTemps() {
		cadrePrincipal.removeAll();
		cadrePrincipal.add(bandeHaut);
		//Pie Chart 
		/*PieChart monChart = new PieChart(0,0) ; 
		
		for(int i=0; i<lesTemps.size(); i++) {
			monChart.addSeries(lesNoms.get(i),lesTemps.get(i)) ; 
		}
		
		monChart.setTitle("Duree en secondes d'une partie") ; 
        xPanel = new XChartPanel<PieChart>(monChart) ; 
        cadrePrincipal.add(xPanel) ; */
        
        //Autre type de graphique 
        CategoryChart monChart = new CategoryChart(0,0) ; 
        monChart.addSeries("Nombre de points marqués par tranches de 5 secondes", lesNoms, scoreParTemps) ; 
        monChart.setYAxisTitle("Nombre de points marqués moyens par tranches de 5 secondes lors d'une partie") ; 
        xPanel = new XChartPanel<CategoryChart>(monChart) ; 
        cadrePrincipal.add(xPanel) ; 
        cadrePrincipal.validate(); 
        cadrePrincipal.repaint(); 
	}
	
	//Méthode qui permet de repasser à la fenêtre d'accueil en enlevant le graphique de scores par temps 
	public void enleverGraphTemps() {
		cadrePrincipal.removeAll();
		cadrePrincipal.add(bandeHaut);  
		cadrePrincipal.add(img); 
		cadrePrincipal.validate(); 
        cadrePrincipal.repaint(); 
	}
	
	//Méthode qui permet d'afficher le graphique des scores en appuyant sur le bouton score 
	public void afficherGraphScore() {
		cadrePrincipal.removeAll();
		cadrePrincipal.add(bandeHaut); 
		//Pie Chart 
		/*PieChart monChart = new PieChart(0,0) ; 
		
		for(int i=0; i<lesTemps.size(); i++) {
			monChart.addSeries(lesNoms.get(i),lesTemps.get(i)) ; 
		}
		
		monChart.setTitle("Duree en secondes d'une partie") ; 
        xPanel = new XChartPanel<PieChart>(monChart) ; 
        cadrePrincipal.add(xPanel) ; */
        
        //Autre type de graphique 
        CategoryChart monChart = new CategoryChart(0,0) ; 
        monChart.addSeries("Score",lesNoms, lesScores) ; 
        monChart.setYAxisTitle("Points marqués lors d'une partie") ; 
        xPanel2 = new XChartPanel<CategoryChart>(monChart) ; 
        cadrePrincipal.add(xPanel2) ; 
        cadrePrincipal.validate(); 
        cadrePrincipal.repaint(); 
	} 
	
	//Méthode qui permet de repasser à la fenêtre d'accueil ou à un autre "onglet" en enlevant le graphique de scores
	public void enleverGraphScore() {
		cadrePrincipal.removeAll();
		cadrePrincipal.add(bandeHaut); 
		cadrePrincipal.add(img);
		cadrePrincipal.validate();
		cadrePrincipal.repaint(); 
	}
	
	//Méthode qui permet d'afficher la page où l'on voit les badges obtenus par le joueur 
	public void afficherBadge() {
		int align = 800 ; //
		int hgap = 50;  //espacement horizontal entre les éléments du panel FlowLayout
		int vgap = 75 ; //espacement vertical des éléments par rapport à la partie supérieure du panel FlowLayout
		cadrePrincipal.removeAll();
		cadrePrincipal.add(bandeHaut); 
		badges = new JPanel(new FlowLayout(align, hgap, vgap)); 
		 
	//Instanciation des images des badges et redimension 
		badge5 = new ImageIcon(new ImageIcon("Images/cinqq.png").getImage().getScaledInstance(400,400,400)); 
		badge10 = new ImageIcon(new ImageIcon("Images/dixx.png").getImage().getScaledInstance(400,400,400));
		badge20 = new ImageIcon(new ImageIcon("Images/vingtt.png").getImage().getScaledInstance(400,400,400));
		badge30 = new ImageIcon(new ImageIcon("Images/trentee.png").getImage().getScaledInstance(400,400,400)); 
		badge50 = new ImageIcon(new ImageIcon("Images/cinquantee.png").getImage().getScaledInstance(400,400,400)); 
		badge100 = new ImageIcon(new ImageIcon("Images/cent.png").getImage().getScaledInstance(400,400,400));    
		
		//getScaledInstance(int width, int height, int hints) 
		//Crée une version à l'échelle de cette image. Width correspond à la largeur de l'image, height à la hauteur de l'image et hint à l'échelle obtenue 
		
				
	//Instanciation des labels des badges 
		lbadge5 = new JLabel(badge5); 
		lbadge10 = new JLabel(badge10); 
		lbadge20 = new JLabel(badge20); 
		lbadge30 = new JLabel(badge30); 
		lbadge50 = new JLabel(badge50); 
		lbadge100 = new JLabel(badge100); 
		
	//Parcours de la LinkedList des scores pour les attributions des badges 
	//Les if vérifient dans la LinkedList des scores si le joueur a obtenu le nombre de points nécéssaires pour avoir un badge dans ses parties 
		for(int i=0 ; i<lesScores.size() ; i++) {
			if(lesScores.get(i) >= 5){
				badges.add(lbadge5); 
			}
			if(lesScores.get(i) >= 10) {
				badges.add(lbadge10);
			}
			if(lesScores.get(i) >= 20) {
				badges.add(lbadge20);
			}
			if(lesScores.get(i) >= 30) {
				badges.add(lbadge30);
			}
			if(lesScores.get(i) >= 50) {
				badges.add(lbadge50);
			}
			if(lesScores.get(i) >= 100) {
				badges.add(lbadge100);
			}
		}
		cadrePrincipal.add(badges, BorderLayout.CENTER); 
		cadrePrincipal.validate();
		cadrePrincipal.repaint(); 
	}
	
	
	//Méthode qui permet d'enlever la fenêtre des badges pour passer à la page d'accueil ou à un autre onglet 
	public void enleverBadge() {
		cadrePrincipal.removeAll();
		cadrePrincipal.add(bandeHaut); 
		cadrePrincipal.add(img);
		cadrePrincipal.validate();
		cadrePrincipal.repaint(); 
	}
			
		
	
	public static void main (String[] args) {
		Palmares maFenetre = new Palmares (true); 
	
}
}

