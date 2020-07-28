class HvitRute extends Rute {
  private static char tegn = '.';

  public HvitRute(int kolonne, int rad) {
    super(kolonne, rad);
  }

  @Override
  public char tilTegn() {
    return this.tegn;
  }

  @Override
  public String toString() {
    return ".";
  }

  @Override
  public void gaa (Rute origin, String veien) {
    this.toemLoesninger();
    veien += "(" + kolonne + "," + rad + ")-->";
    //System.out.println("kol: " + kolonne + "  rad:" + rad);
    Rute[] naboer = {this.nord, this.vest, this.soer, this.oest};
    for (Rute r : naboer) {
      if (!r.equals(origin)) {
        r.gaa(this, veien);
      }
    }
    if (origin != this) {
      for (String loesning : this.loesninger) {
        origin.leggTilLoesning(loesning);
        /*
        System.out.println("legger til loesning: " + loesning);
        System.out.println("kol: " + kolonne + "  rad:" + rad);
        */
      }
    }
  }

  @Override
  public void finnUtvei() {
    gaa(this, "");
  }
}
