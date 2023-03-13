public class Troncon {
  private int numeroLigne;
  private String depart;
  private String arrivee;
  private int duree;

  public Troncon(int numeroLigne, String depart, String arrivee, int duree) {
    this.numeroLigne = numeroLigne;
    this.depart = depart;
    this.arrivee = arrivee;
    this.duree = duree;
  }

  public int getNumeroLigne() {
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
