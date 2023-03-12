public class Troncon {
  private String numeroLigne;
  private String depart;
  private String arrivee;
  private int duree;

  public Troncon(String depart, String arrivee, int duree) {
    this.depart = depart;
    this.arrivee = arrivee;
    this.duree = duree;
  }

  public String getNumeroLigne() {
    return numeroLigne;
  }

  public String getDepart() {
    return depart;
  }

  public String getArrivee() {
    return arrivee;
  }

  public int getDuree() {
    return duree;
  }
}
