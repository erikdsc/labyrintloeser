import java.util.Iterator;

class LenkelisteIterator<T> implements Iterator<T> {
  private Lenkeliste<T> liste;
  private Node<T> denne;

  public LenkelisteIterator(Lenkeliste<T> liste, Node<T> forst) {
    this.liste = liste;
    this.denne = forst;
  }
  public boolean hasNext() {
    if (this.denne == null) {
      return false;
    }
    else {
      return true;
    }
  }

  public T next() {
      T ut = this.denne.hentInnhold();
      this.denne = this.denne.hentNeste();
      return ut;
  }

  public void remove() {
    //Ikke implementert enda
  }

}
