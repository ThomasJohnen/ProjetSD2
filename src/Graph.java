import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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

    List<Troncon> chemin = new ArrayList<>();
    Troncon tronconActuel = sommetPrecedent.get(stationArrive);
    while (tronconActuel != null) {
      chemin.add(tronconActuel);
      tronconActuel = sommetPrecedent.get(tronconActuel.getDepart());
    }

    Collections.reverse(chemin);
    int nbTroncons = 0;
    Ligne ligne = null;
    int dureeTransport = 0;
    int dureeTotale = 0;
    String numeroLigne = "";
    for (Troncon troncon : chemin) {
      System.out.print("Troncon" + " [" + "départ=" + troncon.getDepart() + ", arrivée=" + troncon.getArrivee()
          + ", durée=" + troncon.getDuree() + ", ligne=Ligne " + "[");
      dureeTransport += troncon.getDuree();

      for (int i = 0; i < listeLignes.size(); i++) {
        if (listeLignes.get(i).getId() == troncon.getNumeroLigne()){
          ligne = listeLignes.get(i);
        }
      }
      System.out.println("id=" +ligne.getId() +", nom=" + ligne.getNumero() +
          ", source=" + ligne.getPremiereStation() + ", destination=" + ligne.getDestination() + ", type=" + ligne.getTypeTransport() +
          ", attente moyenne=" + ligne.getTempsAttenteMoyen() + "]]");

      if (!numeroLigne.equals(ligne.getNumero())){
        numeroLigne = ligne.getNumero();
        dureeTotale += ligne.getTempsAttenteMoyen();
      }
      nbTroncons += 1;
    }

    System.out.println("Nombre de troncons=" + nbTroncons);
    dureeTotale += dureeTransport;
    System.out.println("dureeTransport=" + dureeTransport + " dureeTotale=" + dureeTotale );

    /*
    Ligne ligne2 = null;
    String numLigne = "";
    String arrivee = "";
    for (Troncon troncon : chemin) {
      for (int i = 0; i < listeLignes.size(); i++) {
        if (listeLignes.get(i).getId() == troncon.getNumeroLigne()){
          ligne2 = listeLignes.get(i);
        }
      }

      if (!numLigne.equals(ligne2.getNumero())){
        numLigne = ligne2.getNumero();
        System.out.print("Deplacement [" + "ligne=" + ligne2.getNumero() + ", Depart=" + troncon.getDepart());
        for (Troncon troncon1 : chemin) {

          if (troncon1.getDepart().equals(troncon.getArrivee())){
            System.out.println(", arrivee=" + troncon.getArrivee());
          }
        }
      }
    }*/
  }

  public void calculerCheminMinimisantTempsTransport(String stationDepart, String stationArrive) {
    Map<String, Integer> tempsMin = new HashMap<>();
    Map<String, Troncon> sommetPrecedent = new HashMap<>();
    Set<String> sommetsNonVisites = new HashSet<>();

    for (String sommet : mapTroncons.keySet()) {
      tempsMin.put(sommet, Integer.MAX_VALUE);
      sommetPrecedent.put(sommet, null);
      sommetsNonVisites.add(sommet);
    }
    tempsMin.put(stationDepart, 0);

    while (!sommetsNonVisites.isEmpty()) {
      String sommetActuel = null;
      int tempsMinActuel = Integer.MAX_VALUE;

      for (String sommet : sommetsNonVisites) {
        int temps = tempsMin.get(sommet);
        if (temps < tempsMinActuel) {
          sommetActuel = sommet;
          tempsMinActuel = temps;
        }
      }
      if (sommetActuel == null) {
        break;
      }

      sommetsNonVisites.remove(sommetActuel);
      ArrayList<Troncon> troncons = mapTroncons.get(sommetActuel);
      for (int i = 0; i < troncons.size(); i++) {
        Troncon troncon = troncons.get(i);
        String sommetVoisin = troncon.getArrivee();
        int tempsVoisin = tempsMinActuel + troncon.getDuree();

        if (tempsVoisin < tempsMin.get(sommetVoisin)) {
          tempsMin.put(sommetVoisin, tempsVoisin);
          sommetPrecedent.put(sommetVoisin, troncon);
        }
      }
    }

    List<Troncon> chemin = new ArrayList<>();
    Troncon tronconActuel = sommetPrecedent.get(stationArrive);
    while (tronconActuel != null) {
      chemin.add(tronconActuel);
      tronconActuel = sommetPrecedent.get(tronconActuel.getDepart());
    }

    int nbTroncons = 0;
    Ligne ligne = null;
    int dureeTransport = tempsMin.get(stationArrive);
    int dureeTotale = 0;
    int numeroLigne = 0;
    Collections.reverse(chemin);
    for (Troncon troncon : chemin) {
      System.out.print("Troncon" + " [" + "départ=" + troncon.getDepart() + ", arrivée=" + troncon.getArrivee()
          + ", durée=" + troncon.getDuree() + ", ligne=Ligne " + "[");

      for (int i = 0; i < listeLignes.size(); i++) {
        if (listeLignes.get(i).getId() == troncon.getNumeroLigne()){
          ligne = listeLignes.get(i);
        }
      }
      System.out.println("id=" +ligne.getId() +", nom=" + ligne.getNumero() +
          ", source=" + ligne.getPremiereStation() + ", destination=" + ligne.getDestination() + ", type=" + ligne.getTypeTransport() +
          ", attente moyenne=" + ligne.getTempsAttenteMoyen() + "]]");

      if (numeroLigne != ligne.getId()){
        numeroLigne = ligne.getId();
        dureeTotale += ligne.getTempsAttenteMoyen();
      }
      nbTroncons += 1;
    }
    dureeTotale += dureeTransport;
    System.out.println("Nombre de troncons=" + nbTroncons);
    System.out.println("dureeTransport=" + dureeTransport + " dureeTotale=" + dureeTotale);
  }
}
