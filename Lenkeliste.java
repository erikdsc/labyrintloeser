import java.util.Iterator;

class Lenkeliste<T> implements Liste<T> {
  protected Node<T> forst;
  protected Node<T> sist;

  public void leggTil(T x) {
    Node<T> nyNode = new Node<T>(x);
    //Hvis listen er tom
    if (this.forst == null || this.sist == null) {
      this.forst = nyNode;
      this.sist = nyNode;
    }
    //Hvis listen ikke er tom
    else {
      nyNode.settForrige(this.sist);
      this.sist.settNeste(nyNode);
      this.sist = nyNode;
    }
  }

  public T fjern() throws UgyldigListeIndeks {
    if (this.forst == null || this.sist == null) {
      System.out.println("Listen er tom, ingenting kan fjernes!");
      throw new UgyldigListeIndeks(0);
    }
    //Hvis elementet ikke er alene i listen
    else if (this.forst.hentNeste() != null) {
      Node<T> ut = this.forst;
      this.forst = ut.hentNeste();
      this.forst.settForrige(null);
      return ut.hentInnhold();
    }
    //Hvis listen kun har ett element
    else {
      Node<T> ut = this.forst;
      this.forst = null;
      return ut.hentInnhold();
    }
  }

  public void sett(int pos, T x) throws UgyldigListeIndeks {
    //Hvis listen er tom
    if (this.forst == null || this.sist == null) {
      System.out.println("ERROR: Listen er tom");
      throw new UgyldigListeIndeks(pos);
    }
    //Hvis onsket posisjon er negativ
    if (pos < 0) {
      System.out.println("Kan ikke ha negative indekser!");
      throw new UgyldigListeIndeks(pos);
    }
    //Antar for enkelhetens skyld at man ikke faar en indeks i listen som er tom
    Node<T> denne = this.forst;
    for (int i = 0 ; i < pos ; i++) {
      if (denne.hentNeste() == null) {
        throw new UgyldigListeIndeks(i);
      }
      denne = denne.hentNeste();
    }
    denne.settInnhold(x);
  }

  public void leggTil(int pos, T x) throws UgyldigListeIndeks {
    Node<T> nyNode = new Node<T>(x);
    //Hvis en av de er null har det enten skjedd en feil eller saa er
    //hele listen tom.
    if (this.forst == null || this.sist == null) {
      if (pos != 0) {
        System.out.println("ERROR: Listen har ikke indeksen");
        throw new UgyldigListeIndeks(pos);
      }
      this.forst = nyNode;
      this.sist = nyNode;
    }
    //Hvis onsket posisjon er negativ
    if (pos < 0) {
      System.out.println("Kan ikke ha negative indekser!");
      throw new UgyldigListeIndeks(pos);
    }
    //Hvis listen ikke er tom og man setter inn paa indeks 0
    if (pos == 0) {
      this.forst.settForrige(nyNode);
      nyNode.settNeste(this.forst);
      this.forst = nyNode;
    }
    else {
      Node<T> denne = forst;
      Node<T> neste;
      for (int i = 0 ; i < pos -1; i++) {
        if (denne.hentNeste() == null) {
          throw new UgyldigListeIndeks(i);
        }
        denne = denne.hentNeste();
      }
      //Hvis indeksen ikke er sist i listen
      if (denne.hentNeste() != null) {
        neste = denne.hentNeste();
        nyNode.settNeste(neste);
        neste.settForrige(nyNode);
        denne.settNeste(nyNode);
        nyNode.settForrige(denne);
      }
      //Hvis indeksen er siste elementet i listen
      else {
        this.sist.settNeste(nyNode);
        nyNode.settForrige(this.sist);
        this.sist = nyNode;
      }
    }
  }

  public T fjern(int pos) throws UgyldigListeIndeks {
    //Hvis listen er tom
    if (this.forst == null || this.sist == null) {
      System.out.println("ERROR: Listen er tom");
      throw new UgyldigListeIndeks(pos);
    }
    if (pos < 0) {
      System.out.println("Kan ikke ha negative indekser!");
      throw new UgyldigListeIndeks(pos);
    }
    //Fjerne indeks 0
    if (pos == 0) {
      //Hvis listen kun har ett element
      if (this.forst == this.sist) {
        T ut = this.forst.hentInnhold();
        this.forst = null;
        this.sist = null;
        return ut;
      }
      else {
        T ut = this.forst.hentInnhold();
        this.forst = this.forst.hentNeste();
        this.forst.settForrige(null);
        return ut;
      }
    }
    //Hvis posisjonen ikke er 0
    Node<T> denne = this.forst;
    //Finne frem til riktig indeks
    for (int i = 0; i < pos ; i++) {
      if (denne.hentNeste() == null) {
        throw new UgyldigListeIndeks(i);
      }
      denne = denne.hentNeste();
    }
    //Hvis posisjonen er sist
    if (denne.hentNeste() == null) {
      T ut = this.sist.hentInnhold();
      this.sist = denne.hentForrige();
      this.sist.settNeste(null);
      return ut;
    }
    //Hvis posisjonen verken er forst eller sist
    else {
      denne.hentForrige().settNeste(denne.hentNeste());
      denne.hentNeste().settForrige(denne.hentForrige());
      return denne.hentInnhold();
    }
  }

  public int stoerrelse() {
    if (this.forst == null || this.sist == null) {
      return 0;
    }
    else {
      int antall = 1;
      Node<T> denne = this.forst;
      while (denne.hentNeste() != null) {
        antall += 1;
        denne = denne.hentNeste();
      }
      return antall;
    }
  }
  public T hent(int pos) throws UgyldigListeIndeks {
    //Hvis listen er tom
    if (this.forst == null || this.sist == null) {
      System.out.println("ERROR: Listen er tom");
      throw new UgyldigListeIndeks(pos);
    }
    //Hvis onsket posisjon er negativ
    if (pos < 0) {
      System.out.println("Kan ikke ha negative indekser!");
      throw new UgyldigListeIndeks(pos);
    }
    Node<T> denne = this.forst;
    for (int i = 0 ; i < pos ; i++) {
      if (denne.hentNeste() == null) {
        throw new UgyldigListeIndeks(i);
      }
      denne = denne.hentNeste();
    }
    return denne.hentInnhold();
}

public Iterator<T> iterator() {
  //Siden iteratoren er en egen fil, trenger den haandtak til listen for aa iterere
  return new LenkelisteIterator<T>(this, this.forst);
}

public boolean inneholder(T objekt) {
  for (T i : this) {
    if (i == objekt) {
      return true;
    }
  }
  return false;
}

  /*
  private class LenkelisteIterator<T> implements Iterator<T> {
    private int pos = 0;

    public boolean hasNext() {
      if (this.hent(pos) == null) {
        return false;
      }
      else {
        if (this.hent(0).hentNeste() == null) {
          return false;
        }
        else {
          return true;
        }
      }
    }

    public T next() {
      if (this.hent(0).hasNext() == true) {
        this.pos += 1;
        return this.hent(pos-1).hentInnhold();
      }
      else {
        return null;
      }
    }

    public void remove() {

    }

  }
  */
}
