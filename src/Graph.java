import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Graph {

  private File lignes;
  private File troncons;
  private ArrayList<Ligne> listeLignes;
  private HashMap<Integer, ArrayList<Troncon>> mapTroncons;

  public Graph(File lignes, File troncons) {
    this.lignes = lignes;
    this.troncons = troncons;
    this.listeLignes = new ArrayList<>();
    this.mapTroncons = new HashMap<>();
    chargerLignes();
    chargerTroncons();
  }

  private void chargerLignes() {
    try (Scanner scanner = new Scanner(lignes)) {
      while (scanner.hasNextLine()) {
        String ligne = scanner.nextLine();
        String[] elements = ligne.split(",");
        int id = Integer.parseInt(elements[0]);
        String numero = elements[1];
        String premiereStation = elements[2];
        String destination = elements[3];
        String typeTransport = elements[4];
        int tempsAttenteMoyen = Integer.parseInt(elements[5]);
        Ligne l = new Ligne(id, numero, premiereStation, destination, typeTransport, tempsAttenteMoyen);
        listeLignes.add(l);
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void chargerTroncons() {
    try (Scanner scanner = new Scanner(troncons)) {
      while (scanner.hasNextLine()) {
        String ligne = scanner.nextLine();
        String[] elements = ligne.split(",");
        int numeroLigne = Integer.parseInt(elements[0]);
        String depart = elements[1];
        String arrivee = elements[2];
        int duree = Integer.parseInt(elements[3]);
        Troncon t = new Troncon(depart, arrivee, duree);
        if (mapTroncons.containsKey(numeroLigne)) {
          mapTroncons.get(numeroLigne).add(t);
        } else {
          ArrayList<Troncon> troncons = new ArrayList<>();
          troncons.add(t);
          mapTroncons.put(numeroLigne, troncons);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void calculerCheminMinimisantNombreTroncons(String stationDepart, String stationArrive) {
    // à implémenter
  }

  public void calculerCheminMinimisantTempsTransport(String stationDepart, String stationArrive) {
    // à implémenter
  }
}
