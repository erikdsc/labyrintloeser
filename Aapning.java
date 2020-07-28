class Aapning extends HvitRute {

  public Aapning(int kolonne, int rad) {
    super(kolonne, rad);
  }

  @Override
  public void gaa (Rute origin, String veien) {
    veien += "(" + kolonne + "," + rad + ").";
    origin.leggTilLoesning(veien);
  }
}
