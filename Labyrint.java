import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.File;

class Labyrint {
  private int kolonner;
  private int rader;
  private Lenkeliste<Lenkeliste<Rute>> kart;

  private Labyrint(Lenkeliste<Lenkeliste<Rute>> ruter, int antRad, int antKol) {
    this.kolonner = antKol;
    this.rader = antRad;
    this.kart = ruter;
  }

  public String toString() {
    String ut = "";
    for (Lenkeliste<Rute> rad : this.kart) {
      for (Rute rute : rad) {
        if (rute instanceof HvitRute) {
          ut += "  ";
        }
        else {
         ut += "# ";
        }
      }
      ut += "\n";
    }
    return ut;
  }

  public Liste<String> finnUtveiFra(int kol, int rad) {
    System.out.println(this);
    Rute start = kart.hent(rad-1).hent(kol-1);
    return start.hentLoesning();
  }


  public static Labyrint lesFraFil(File fil) throws FileNotFoundException {
    //Tolker "du kan anta at inputfilen er gyldig" som at man kan se bort ifra
    //FileNotFoundException og feil formatering av filen.

    //Les inn linjene
    Scanner reader = new Scanner(fil);
    //Henter rader og kolonner, som er oeverst i filen
    int antRader = reader.nextInt();
    int antKolonner = reader.nextInt();
    reader.nextLine();
    //Leser labyrint rad etter rad
    int radNummer = 1;
    Lenkeliste<Lenkeliste<Rute>> ruter = new Lenkeliste<Lenkeliste<Rute>>();
    while (reader.hasNextLine()) {
      Lenkeliste<Rute> nyRad = new Lenkeliste<Rute>();
      //NB kan kanskje fjerne arrayet
      String[] rad = reader.nextLine().split("");
      int kolonneNummer = 1;
      for (String tegn : rad) {
        if (tegn.equals("#")) {
          SortRute nyRute = new SortRute(kolonneNummer, radNummer);
          nyRad.leggTil(nyRute);
        } else if (tegn.equals(".")) {
          if (kolonneNummer == 1 || kolonneNummer == antKolonner) {
            Aapning nyRute = new Aapning(kolonneNummer, radNummer);
            nyRad.leggTil(nyRute);
          } else if (radNummer == 1 || radNummer == antRader) {
            Aapning nyRute = new Aapning(kolonneNummer, radNummer);
            nyRad.leggTil(nyRute);
          } else {
            HvitRute nyRute = new HvitRute(kolonneNummer, radNummer);
            nyRad.leggTil(nyRute);
          }
        }
        else {
          System.out.println(tegn);
          System.out.println("FEIL");
          //HER ER DET NOE GALT, FEIL FILFORMAT
        }
        kolonneNummer += 1;
      }
      ruter.leggTil(nyRad);
      radNummer += 1;
    }
    reader.close();
    //Naa skal kartet vaere klart, men mangler naboreferansene i rutene


    //Loope igjennom for aa sette naboreferanser og koordinater
    int radIndeks = 0;
    for (Lenkeliste<Rute> rad : ruter) {
      int kolonneIndeks = 0;
      for (Rute rute : rad) {
        //Sjekk hver rute
        if (radIndeks == 0) {
          //Kutt nord
          if (kolonneIndeks == 0) {
            //kutt nord og vest
            rute.settSoer(ruter.hent(radIndeks+1).hent(kolonneIndeks));
            rute.settOest(rad.hent(kolonneIndeks+1));
          } else if (kolonneIndeks == antKolonner-1) {
            //kutt nord og oest
            rute.settVest(rad.hent(kolonneIndeks-1));
            rute.settSoer(ruter.hent(radIndeks+1).hent(kolonneIndeks));
          } else {
            //kutt nord
            rute.settVest(rad.hent(kolonneIndeks-1));
            rute.settSoer(ruter.hent(radIndeks+1).hent(kolonneIndeks));
            rute.settOest(rad.hent(kolonneIndeks+1));
          }
        }
        else if (radIndeks == antRader-1) {
          //Kutt soer
          if (kolonneIndeks == 0) {
            //kutt soer og vest
            rute.settNord(ruter.hent(radIndeks-1).hent(kolonneIndeks));
            rute.settOest(rad.hent(kolonneIndeks+1));
          } else if (kolonneIndeks == antKolonner-1) {
            //kutt soer og oest
            rute.settNord(ruter.hent(radIndeks-1).hent(kolonneIndeks));
            rute.settVest(rad.hent(kolonneIndeks-1));
          } else {
            //kutt soer
            rute.settNord(ruter.hent(radIndeks-1).hent(kolonneIndeks));
            rute.settVest(rad.hent(kolonneIndeks-1));
            rute.settOest(rad.hent(kolonneIndeks+1));
          }
        }
        else {
          //Ikke kutt nord eller soer
          if (kolonneIndeks == 0) {
            //kutt vest
            rute.settNord(ruter.hent(radIndeks-1).hent(kolonneIndeks));
            rute.settSoer(ruter.hent(radIndeks+1).hent(kolonneIndeks));
            rute.settOest(rad.hent(kolonneIndeks+1));
          } else if (kolonneIndeks == antKolonner-1) {
            //kutt oest
            rute.settNord(ruter.hent(radIndeks-1).hent(kolonneIndeks));
            rute.settVest(rad.hent(kolonneIndeks-1));
            rute.settSoer(ruter.hent(radIndeks+1).hent(kolonneIndeks));
          } else {
            //ikke Kutt
            rute.settNord(ruter.hent(radIndeks-1).hent(kolonneIndeks));
            rute.settVest(rad.hent(kolonneIndeks-1));
            rute.settSoer(ruter.hent(radIndeks+1).hent(kolonneIndeks));
            rute.settOest(rad.hent(kolonneIndeks+1));
          }
        }
        kolonneIndeks += 1;
      }
      radIndeks += 1;
    }

    //Opprett labyrintobjekt
    Labyrint nyLabyrint = new Labyrint(ruter, antRader, antKolonner);

    //Returner labyrint med riktig datastruktur
    return nyLabyrint;
  }
}
