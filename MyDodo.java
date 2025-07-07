import greenfoot.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class MyDodo extends Dodo {
    private int myNrOfStepsTaken;
    private int NrOfEggs;

    /**
     * Hier roep ik een constructor aan
     */
    public MyDodo() {
        super(EAST);
        myNrOfStepsTaken = 0;
    }

    /** 
     * Hier roep ik de public void act aan die een programma laat lopen
     */
    public void act() {
    }

    /**
     * Deze functie beweegd de dodo 1 stap naar voren
     */
    public void move() {
        if (canMove()) {
            step();
            myNrOfStepsTaken++;
            ScoreBoard();
        }
    }

    /** 
     * Deze functie kijkt of er niks voor de dodo staat als die wilt gaan bewegen
     */
    public boolean canMove() {
        return !(borderAhead() || fenceAhead());
    }

     /**
      * Deze functie laat de dodo een grotere stap zetten in de richting waar die kijkt
      */
    public void jump(int distance) {
        int nrStepsTaken = 0;
        while (nrStepsTaken < distance) {
            move();
            nrStepsTaken++;
        }
    }

   
    /**
     * Deze functie geeft een lijst van alle eieren in de wereld
     */
    public List<Egg> getListOfEggsInWorld() {
        return getWorld().getObjects(Egg.class);
    }

    /**
     * Deze functie maakt een lijst met de getallen 2, 43, 7, -5, 12 en 7, en geeft die lijst terug als resultaat.
     */
    public List<Integer> createListOfNumbers() {
        return new ArrayList<>(Arrays.asList(2, 43, 7, -5, 12, 7));
    }

    /**
     * Deze functie maakt een lijst met getallen en laat daarna het tweede getal uit die lijst zien in de console.
     */
    public void practiceWithLists() {
        List<Integer> listOfNumbers = createListOfNumbers();
        System.out.println("Tweede getal: " + listOfNumbers.get(1));
    }

    /**
     * Deze functie maakt een lijst met 12 SurpriseEgg-objecten door generateListOfSurpriseEggs aan te roepen en geeft de wereld (getWorld()) mee als parameter.
     */
    public void practiceWithListsOfSurpriseEgss() {
        List<SurpriseEgg> listOfEgss = SurpriseEgg.generateListOfSurpriseEggs(12, getWorld());
    }

    /**
     * Deze functie laat de dodo draaien naar de opgegeven directie
     */
    public void faceDirection(int direction) {
        
        while (getDirection() != direction) {
            if (direction < NORTH || direction > WEST) break;
            turnRight();
        }
    }

    /**
     * Deze functie laat de dodo naar een opgegeven plek lopen, zolang het nog niet te veel stappen heeft gezet, en in een directie die daar voor klopt
     */
    public void gotoLocation(int coordX, int coordY) {
        while (getX() < coordX && myNrOfStepsTaken < Mauritius.MAXSTEPS) {
            faceDirection(EAST);
            move();
        }
        while (getX() > coordX && myNrOfStepsTaken < Mauritius.MAXSTEPS) {
            faceDirection(WEST);
            move();
        }
        while (getY() < coordY && myNrOfStepsTaken < Mauritius.MAXSTEPS) {
            faceDirection(SOUTH);
            move();
        }
        while (getY() > coordY && myNrOfStepsTaken < Mauritius.MAXSTEPS) {
            faceDirection(NORTH);
            move();
        }
    }

    /**
     * Deze functie berekent waar het dichtstbijzijnde ei is en berekent de plek waar die zit.
       */
    public Egg ClosestEgg() {
        List<Egg> eggs = getWorld().getObjects(Egg.class);
        Egg closestEgg = null;
        int closestDistance = Integer.MAX_VALUE;

        for (Egg egg : eggs) {
            int dx = Math.abs(egg.getX() - getX());
            int dy = Math.abs(egg.getY() - getY());
            int distance = dx + dy;

            if (distance < closestDistance) {
                closestEgg = egg;
                closestDistance = distance;
            }
        }
        return closestEgg;
    }

    
    /**(
     * Loopt naar het gegeven ei waar net de locatie van berekent is
     */
    public void GoToEgg(Egg egg) {
        int eggX = egg.getX();
        int eggY = egg.getY();
        gotoLocation(eggX, eggY);
    }

    /**
     * Deze functie speelt de dodo race af en laat hem steeds lopen naar het dichtstbijzijnde ei en pakt hem op waarvan die 1 punt krijgt per blauw ei 
     * en 5 per goud ei
     */
    
    public void DodoRace() {
        Egg egg = ClosestEgg();

        while (egg != null && myNrOfStepsTaken < Mauritius.MAXSTEPS) {
            GoToEgg(egg);
            if (myNrOfStepsTaken >= Mauritius.MAXSTEPS) break;

            NrOfEggs += egg.getValue(); // getValue() moet bestaan in Egg
            pickUpEgg();
            egg = ClosestEgg();
        }
    }

    /**
     * Deze functie update het scoreboard zodat die altijd gelijk is aan de stappen en de punten van de dodo
     */
    public void ScoreBoard() {
        Mauritius world = (Mauritius) getWorld();
        world.updateScore(Mauritius.MAXSTEPS - myNrOfStepsTaken, NrOfEggs);
    }
}
