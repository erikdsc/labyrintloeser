class Node<T> {
  private T innhold;
  //Hvis man tenker at den forste er til venstre
  //Og den siste er til hoyre, gaar neste mot hoyre
  //og forrige mot venstre
  private Node<T> neste;
  private Node<T> forrige;

  Node(T x) {
    this.innhold = x;
  }
  public void settInnhold(T x) {
    this.innhold = x;
  }

  public T hentInnhold() {
    return this.innhold;
  }

  public Node<T> hentNeste()  {
    //if (this.neste == null) {
    //  throw new UgyldigListeIndeks(1337);
    //}
    //else {
      return this.neste;
    //}
  }
  public Node<T> hentForrige() {
    //if (this.forrige == null) {
    //  throw new UgyldigListeIndeks(1337);
    //}
    //else {
      return this.forrige;
    //}
  }

  public void settNeste(Node<T> neste) {
    this.neste = neste;
  }
  public void settForrige(Node<T> forrige) {
    this.forrige = forrige;
  }
}
