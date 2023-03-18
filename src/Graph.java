import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Graph {

  private File lignes;
  private File troncons;
  private ArrayList<Ligne> listeLignes;
  private HashMap<String, ArrayList<Troncon>> mapTroncons;

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
        Ligne l = new Ligne(id, numero, premiereStation, destination, typeTransport,
            tempsAttenteMoyen);
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
        Troncon t = new Troncon(numeroLigne, depart, arrivee, duree);
        if (mapTroncons.containsKey(depart)) {
          mapTroncons.get(depart).add(t);
        } else {
          ArrayList<Troncon> troncons = new ArrayList<>();
          troncons.add(t);
          mapTroncons.put(depart, troncons);
        }
      }
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }

  public void calculerCheminMinimisantNombreTroncons(String stationDepart, String stationArrive) {
    String stationActuelle = stationDepart;
    // liste des sommets
    ArrayDeque<String> file = new ArrayDeque<>();
    // liste des sommets visités
    Set<String> stationsVisitees = new HashSet<>();
    // retien le sommet et garde sa station précedente
    Map<String, Troncon> sommetPrecedent = new HashMap<String, Troncon>();

    stationsVisitees.add(stationActuelle);
    file.add(stationActuelle);

    while (!file.contains(stationArrive)) {
      stationActuelle = file.poll();
      for (Troncon troncon : mapTroncons.get(stationActuelle)) {
        if (!stationsVisitees.contains(troncon.getArrivee())) {
          file.add(troncon.getArrivee());
          stationsVisitees.add(troncon.getArrivee());
          sommetPrecedent.put(troncon.getArrivee(), troncon);
        }
      }
    }

    Troncon test;
    String stationSearch = stationArrive;
    Ligne ligne = null;
    while (!stationSearch.equals(stationDepart)){
      test = sommetPrecedent.get(stationSearch);
      System.out.print("Troncon" + " [" + "départ=" + test.getDepart() + ", arrivée=" + test.getArrivee()
          + ", durée=" + test.getDuree() + ", ligne=Ligne " + "[");

      for (int i = 0; i < listeLignes.size(); i++) {
        if (listeLignes.get(i).getId() == test.getNumeroLigne()){
          ligne = listeLignes.get(i);
        }
      }
      System.out.println("id=" +ligne.getId() +", nom=" + ligne.getNumero() +
          ", source=" + ligne.getPremiereStation() + ", destination=" + ligne.getDestination() + ", type=" + ligne.getTypeTransport() +
          ", attente moyenne=" + ligne.getTempsAttenteMoyen() + "]]");
      stationSearch = test.getDepart();
    }
  }

  public void calculerCheminMinimisantTempsTransport(String stationDepart, String stationArrive) {
    // à implémenter
  }
}
