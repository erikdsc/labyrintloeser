abstract class Rute {
  private char tegn;
  protected int kolonne;
  protected int rad;
  protected Rute nord;
  protected Rute oest;
  protected Rute soer;
  protected Rute vest;
  protected Lenkeliste<String> loesninger = new Lenkeliste<String>();

  //NB fjern konstruktoeren her og i subklassene
  public Rute(int kolonne, int rad) {
    this.kolonne = kolonne;
    this.rad = rad;
  }

  abstract char tilTegn();

  abstract void gaa(Rute origin, String veien);

  abstract void finnUtvei();

  public Liste<String> hentLoesning() {
    this.finnUtvei();
    return loesninger;
  }

  public void toemLoesninger() {
    this.loesninger = new Lenkeliste<String>();
  }

  public void leggTilLoesning(String loesning) {
    boolean finnesFraFoer = false;
    for (String l : loesninger) {
      if (l.equals(loesning)) {
        finnesFraFoer = true;
      }
    }
    if (!finnesFraFoer) {
      this.loesninger.leggTil(loesning);
    }
  }

  //Ettersom referansene til rutene er skjult inni labyrintobjektet,
  //tror jeg det gaar fint aa ha et litt aapent grensesnitt for rutene
  public void settRad(int rad) {
    this.rad = rad;
  }
  public void settKol(int kolonne) {
    this.kolonne = kolonne;
  }
  public void settNord(Rute nord) {
    this.nord = nord;
  }
  public void settVest(Rute vest) {
    this.vest = vest;
  }
  public void settSoer(Rute soer) {
    this.soer = soer;
  }
  public void settOest(Rute oest) {
    this.oest = oest;
  }
  public Rute hentNord() {
    return this.nord;
  }
  public Rute hentVest() {
    return this.vest;
  }
  public Rute hentSoer() {
    return this.soer;
  }
  public Rute hentOest() {
    return this.oest;
  }
  public int hentKolonne() {
    return this.kolonne;
  }
  public int hentRad() {
    return this.rad;
  }

}
