import greenfoot.*;  // Nodig voor gebruik van Greenfoot klassen
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class MyDodo extends Dodo {
    // Houdt het aantal gemaakte stappen bij
    private int myNrOfStepsTaken;
    // Telt de behaalde punten via eieren
    private int NrOfEggs;
           
    public MyDodo() {
        super(EAST); // Beginrichting is oost
        myNrOfStepsTaken = 0;
    }

    public void act() {
        // Leeg act()-blokje, logica wordt elders aangeroepen
    }

    /**
     * Verplaatst de Dodo ��n vakje naar voren als dat mogelijk is.
     */
    public void move() {
        if (canMove()) {
            step();
            myNrOfStepsTaken++;
            ScoreBoard(); // Update scoreboard na elke stap
        } else {
            showError("Ik zit vast!");
        }
    }

    /**
     * Controleert of de Dodo vooruit kan bewegen.
     * Retourneert true als er niets in de weg staat.
     */
    public boolean canMove() {
        return !(borderAhead() || fenceAhead());
    }

    /**
     * Laat de Dodo meerdere stappen zetten, zolang hij kan.
     */
    public void jump(int distance) {
        int nrStepsTaken = 0;
        while (nrStepsTaken < distance) {
            move();
            nrStepsTaken++;
        }
    }

    /**
     * Maakt een lijst van alle eieren in de wereld.
     */
    public List<Egg> getListOfEggsInWorld() {
        return getWorld().getObjects(Egg.class);
    }

    /**
     * Simuleert een voorbeeldlijst met getallen.
     */
    public List<Integer> createListOfNumbers() {
        return new ArrayList<>(Arrays.asList(2, 43, 7, -5, 12, 7));
    }

    /**
     * Kleine testfunctie voor lijstmanipulatie.
     */
    public void practiceWithLists() {
        List<Integer> listOfNumbers = createListOfNumbers();
        System.out.println("Tweede getal: " + listOfNumbers.get(1));
    }

    /**
     * Testfunctie om SurpriseEggs te genereren.
     */
    public void practiceWithListsOfSurpriseEgss() {
        List<SurpriseEgg> listOfEgss = SurpriseEgg.generateListOfSurpriseEggs(12, getWorld());
    }

    /**
     * Laat de Dodo in de juiste richting draaien.
     */
    public void faceDirection(int direction) {
        while (getDirection() != direction) {
            if (direction < NORTH || direction > WEST) break;
            turnRight();
        }
    }

    /**
     * Zorgt dat de Dodo stap voor stap naar bepaalde co�rdinaten loopt.
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
     * Zoekt het dichtstbijzijnde ei op.
     */
    public Egg ReturnClosestEgg() {
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

    /**
     * Loopt naar de locatie van een specifiek ei.
     */
    public void GoToEgg(Egg egg) {
        int eggX = egg.getX();
        int eggY = egg.getY();
        gotoLocation(eggX, eggY);
    }

    /**
     * Voert een race uit waarbij Dodo zo veel mogelijk eieren probeert te verzamelen binnen de limiet.
     */
    public void DodoRace() {
        Egg egg = ReturnClosestEgg();

        while (egg != null && myNrOfStepsTaken < Mauritius.MAXSTEPS) {
            GoToEgg(egg);
            if (myNrOfStepsTaken >= Mauritius.MAXSTEPS) break;

            NrOfEggs += egg.getValue(); // Zorg dat getValue() bestaat in Egg!
            pickUpEgg();
            egg = ReturnClosestEgg();
        }
    }

    /**
     * Zorgt ervoor dat de score getoond en bijgewerkt wordt in de wereld.
     */
    public void ScoreBoard() {
        Mauritius world = (Mauritius) getWorld();
        world.updateScore(Mauritius.MAXSTEPS - myNrOfStepsTaken, NrOfEggs);
    }
}
