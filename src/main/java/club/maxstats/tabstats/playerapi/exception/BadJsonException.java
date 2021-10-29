package club.maxstats.tabstats.playerapi.exception;

public class BadJsonException extends Exception {

    public BadJsonException() {
        System.out.println("Hypixel API returned Bad Json. Maybe the API is down?");
    }

}
