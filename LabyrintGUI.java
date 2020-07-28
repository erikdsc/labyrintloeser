import javafx.stage.FileChooser;
import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.event.*;
import java.util.Scanner;
import java.io.FileNotFoundException;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import java.lang.Math;
import javafx.scene.Node;

public class LabyrintGUI extends Application {
  private Text statusinfo;
  private GridPane rutenett;
  private Labyrint labyrint;
  private int ruteLengde = 0;
  private int antRad;
  private int antKol;
  private int xMellomrom;
  private Pane kulisser;
  private Circle utgangspunkt;

  class Klikkbehandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      int rad = rutenett.getRowIndex((Node)e.getSource());
      int kol = rutenett.getColumnIndex((Node)e.getSource());
      finnUtveiFra(kol+1, rad+1, 0);
    }
  }

  class Stoppbehandler implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent e) {
      Platform.exit();
    }
  }

  void finnUtveiFra(int kol, int rad, int utveiNr) {
    System.out.println("utKol:" + kol);
    System.out.println("utRad:" + rad);
    Liste<String> loesningsvei = labyrint.finnUtveiFra(kol, rad);
    if (loesningsvei.stoerrelse() < 1) return;
    if (kulisser.getChildren().size() > 3) {
      kulisser.getChildren().remove(3, kulisser.getChildren().size()-1);
      kulisser.getChildren().remove(utgangspunkt);
    }
    statusinfo.setText("Viser utvei fra: (" + kol + ", " + rad + ").");
    Text antallUtveier = new Text(loesningsvei.stoerrelse() + " antall utveier.");
    antallUtveier.setFont(new Font(20));
    antallUtveier.setX(690);  antallUtveier.setY(860);
    this.kulisser.getChildren().add(antallUtveier);

    utgangspunkt = new Circle(ruteLengde / 4, Color.rgb(255,0,0));
    utgangspunkt.setCenterX(xMellomrom + ruteLengde*(kol-0.5));
    utgangspunkt.setCenterY(10 +ruteLengde*(rad-0.5));
    //boolean[][] loesningFelt = losningStringTilTabell(loesningsvei.hent(0), antKol, antRad);
    System.out.println(loesningsvei.hent(0));
    double forrigeX = kol; double denneX = 0.0;
    double forrigeY = rad; double denneY = 0.0;
    String ut = loesningsvei.hent(utveiNr);
    //Finner koordinatene ved aa lete frem komma og ta tallene rundt.
    for (int i = 0; i<ut.length()-1; i++) {
      char c  = ut.charAt(i);
      if (c == '.') break;
      if (c == ',') {
        denneX = Character.getNumericValue(ut.charAt(i-1));
        denneY = Character.getNumericValue(ut.charAt(i+1));
        for (int j = 2; j < 4 && Character.isDigit(ut.charAt(i-j)); j++) {
          if (! Character.isDigit(ut.charAt(i-j))) break;
          denneX = denneX + Character.getNumericValue(ut.charAt(i-j))*Math.pow(10,j-1);
        }
        for (int j = 2; j < 4 && Character.isDigit(ut.charAt(i+j)); j++) {
          if (! Character.isDigit(ut.charAt(i+j))) break;
          denneY = 10* denneY + Character.getNumericValue(ut.charAt(i+j));
        }
        System.out.println("fra (" + forrigeX + "," + forrigeY + " til " + denneX + "," + denneY);
        Line nyLinje = new Line(xMellomrom+(forrigeX-0.5)*ruteLengde,
         10+(forrigeY-0.5)*ruteLengde, xMellomrom+(denneX-0.5)*ruteLengde,
          10+(denneY-0.5)*ruteLengde);
        nyLinje.setStrokeWidth(ruteLengde/6);
        nyLinje.setStroke(Color.rgb(255, 0, 0));
        this.kulisser.getChildren().add(nyLinje);
        if (i < ut.length()-11) {
          forrigeX = denneX; forrigeY = denneY;
        }
      }
    }
    //Legger til den siste halve ruten som mangler for aa komme ut av labyrinten
    Line nyLinje = new Line(xMellomrom+(denneX-0.5)*ruteLengde,
     10+(denneY-0.5)*ruteLengde, xMellomrom+(denneX+(denneX-forrigeX)/2-0.5)*ruteLengde,
     10+(denneY+(denneY-forrigeY)/2-0.5)*ruteLengde);

    nyLinje.setStrokeWidth(ruteLengde/6); nyLinje.setStroke(Color.rgb(255, 0, 0));
    this.kulisser.getChildren().add(nyLinje);
    this.kulisser.getChildren().add(utgangspunkt);
  }

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage teater) {
    statusinfo = new Text("Velg en rute");
    statusinfo.setFont(new Font(20));
    statusinfo.setX(390);  statusinfo.setY(860);
    GridPane rutenett = new GridPane();
    rutenett.setPrefSize(810, 810);

    File fil = new FileChooser().showOpenDialog(teater);
    Button stoppknapp = new Button("Stopp");
  	stoppknapp.setLayoutX(50);  stoppknapp.setLayoutY(840);
  	Stoppbehandler stopp = new Stoppbehandler();
  	stoppknapp.setOnAction(stopp);

    try {
      labyrint = Labyrint.lesFraFil(fil);
	    Scanner f = new Scanner(fil);
      antRad = f.nextInt(); antKol = f.nextInt();
      if (antRad>antKol) ruteLengde = 810 / antRad;
      else ruteLengde = 810 / antKol;
      f.nextLine();
      Klikkbehandler klikk = new Klikkbehandler();
      rutenett.setGridLinesVisible(true);
	    for (int i = 0; i < antRad && f.hasNext(); i++) {
        String line = f.nextLine();
        for (int j = 0; j <antKol; j++) {
          char c = line.charAt(j);
          //NB NOE ER TULLETE HER
          if (c != '#') {
            Button nyVei = new Button();
            nyVei.setPrefSize(ruteLengde, ruteLengde);
            nyVei.setMinSize(0,0);
            nyVei.setOnAction(klikk);
            rutenett.add(nyVei, j, i);
          } else {
            Rectangle vegg = new Rectangle(Double.valueOf(ruteLengde),
             Double.valueOf(ruteLengde), Color.rgb(0, 0, 0));
             rutenett.add(vegg, j, i);
          }
          this.xMellomrom = (900-antKol*ruteLengde)/2;
          rutenett.setLayoutX(xMellomrom);  rutenett.setLayoutY(10);
        }
      } f.close();
  	} catch (FileNotFoundException e) {System.out.println("Feil ved filinnlesing");}

    kulisser = new Pane();
  	kulisser.setPrefSize(900, 900);
  	kulisser.getChildren().add(0, rutenett);
  	kulisser.getChildren().add(1, statusinfo);
  	kulisser.getChildren().add(2, stoppknapp);

    Scene scene = new Scene(kulisser);

    teater.setTitle("Labyrint");
    teater.setScene(scene);
    teater.show();
  }
}
