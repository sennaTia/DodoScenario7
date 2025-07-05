import greenfoot.*;
import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;

public class MyDodo extends Dodo {
    private int myNrOfStepsTaken;
    private int NrOfEggs;

    // constructor
    public MyDodo() {
        super(EAST);
        myNrOfStepsTaken = 0;
    }

    // actiemethode 
    public void act() {
    }

    // verplaatst Dodo
    public void move() {
        if (canMove()) {
            step();
            myNrOfStepsTaken++;
            ScoreBoard();
        }
    }

    // controleert of Dodo kan bewegen
    public boolean canMove() {
        return !(borderAhead() || fenceAhead());
    }

    // laat Dodo meerdere stappen zetten
    public void jump(int distance) {
        int nrStepsTaken = 0;
        while (nrStepsTaken < distance) {
            move();
            nrStepsTaken++;
        }
    }

    // geeft lijst van eieren in de wereld
    public List<Egg> getListOfEggsInWorld() {
        return getWorld().getObjects(Egg.class);
    }

    // maakt testlijst van getallen
    public List<Integer> createListOfNumbers() {
        return new ArrayList<>(Arrays.asList(2, 43, 7, -5, 12, 7));
    }

    // test ophalen van waarde uit lijst
    public void practiceWithLists() {
        List<Integer> listOfNumbers = createListOfNumbers();
        System.out.println("Tweede getal: " + listOfNumbers.get(1));
    }

    // test maken van SurpriseEgg lijst
    public void practiceWithListsOfSurpriseEgss() {
        List<SurpriseEgg> listOfEgss = SurpriseEgg.generateListOfSurpriseEggs(12, getWorld());
    }

    // draait Dodo naar opgegeven richting
    public void faceDirection(int direction) {
        while (getDirection() != direction) {
            if (direction < NORTH || direction > WEST) break;
            turnRight();
        }
    }

    // laat Dodo naar coördinaten lopen
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

    // zoekt dichtstbijzijnde ei
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

    // loopt naar opgegeven ei
    public void GoToEgg(Egg egg) {
        int eggX = egg.getX();
        int eggY = egg.getY();
        gotoLocation(eggX, eggY);
    }

    // voert eieren-verzamelrace uit
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

    // update het scoreboard
    public void ScoreBoard() {
        Mauritius world = (Mauritius) getWorld();
        world.updateScore(Mauritius.MAXSTEPS - myNrOfStepsTaken, NrOfEggs);
    }
}
