public class Ligne {
  private int id;
  private String numero;
  private String premiereStation;
  private String destination;
  private String typeTransport;
  private int tempsAttenteMoyen;

  public Ligne(int id, String numero, String premiereStation, String destination, String typeTransport, int tempsAttenteMoyen) {
    this.id = id;
    this.numero = numero;
    this.premiereStation = premiereStation;
    this.destination = destination;
    this.typeTransport = typeTransport;
    this.tempsAttenteMoyen = tempsAttenteMoyen;
  }

  public int getId() {
    return id;
  }

  public String getNumero() {
    return numero;
  }

  public String getPremiereStation() {
    return premiereStation;
  }

  public String getDestination() {
    return destination;
  }

  public String getTypeTransport() {
    return typeTransport;
  }

  public int getTempsAttenteMoyen() {
    return tempsAttenteMoyen;
  }

}
