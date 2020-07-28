class SortRute extends Rute {
  private static char tegn = '#';

  public SortRute(int kolonne, int rad) {
    super(kolonne, rad);
  }

  @Override
  public char tilTegn() {
    return this.tegn;
  }

  @Override
  public String toString() {
    return "#";
  }

  @Override
  public void gaa (Rute origin, String veien) {
    return;
  }

  public void finnUtvei() {
    System.out.println("Kan ikke finne utvei fra sorte ruter!");
    return;
  }
}
