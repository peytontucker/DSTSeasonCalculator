import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.Scanner;

public class Main {
    private EnumMap<SeasonLengthSetting, EnumMap<Season,Integer>> seasonLengths;
    private EnumMap<SeasonLengthSetting, Integer> yearLengths;
    private EnumMap<Season, Integer> seasonFirstDays;

    public static void main(String[] args) {
        Main main = new Main();
        main.run();
    }

    public void init() {
        //create enums for each setting of season length setting and their distribution of days for each season

        //very short season length distribution
        EnumMap<Season,Integer> veryShortSeasonLengths = new EnumMap<Season,Integer>(Season.class);
        veryShortSeasonLengths.put(Season.SPRING, 5);
        veryShortSeasonLengths.put(Season.SUMMER, 5);
        veryShortSeasonLengths.put(Season.AUTUMN, 5);
        veryShortSeasonLengths.put(Season.WINTER, 5);

        //short season length distribution
        EnumMap<Season,Integer> shortSeasonLengths = new EnumMap<Season,Integer>(Season.class);
        shortSeasonLengths.put(Season.SPRING, 12);
        shortSeasonLengths.put(Season.SUMMER, 10);
        shortSeasonLengths.put(Season.AUTUMN, 12);
        shortSeasonLengths.put(Season.WINTER, 10);

        ////default season length distribution
        EnumMap<Season,Integer> defaultSeasonLengths = new EnumMap<Season,Integer>(Season.class);
        defaultSeasonLengths.put(Season.SPRING, 20);
        defaultSeasonLengths.put(Season.SUMMER, 15);
        defaultSeasonLengths.put(Season.AUTUMN, 20);
        defaultSeasonLengths.put(Season.WINTER, 15);

        //long season length distribution
        EnumMap<Season,Integer> longSeasonLengths = new EnumMap<Season,Integer>(Season.class);
        longSeasonLengths.put(Season.SPRING, 31);
        longSeasonLengths.put(Season.SUMMER, 22);
        longSeasonLengths.put(Season.AUTUMN, 30);
        longSeasonLengths.put(Season.WINTER, 22);

        //very long season length distribution
        EnumMap<Season,Integer> veryLongSeasonLengths = new EnumMap<Season,Integer>(Season.class);
        veryLongSeasonLengths.put(Season.SPRING, 50);
        veryLongSeasonLengths.put(Season.SUMMER, 40);
        veryLongSeasonLengths.put(Season.AUTUMN, 50);
        veryLongSeasonLengths.put(Season.WINTER, 40);

        seasonLengths = new EnumMap<SeasonLengthSetting, EnumMap<Season,Integer>>(SeasonLengthSetting.class);

        seasonLengths.put(SeasonLengthSetting.VERY_SHORT, veryShortSeasonLengths);
        seasonLengths.put(SeasonLengthSetting.SHORT, shortSeasonLengths);
        seasonLengths.put(SeasonLengthSetting.DEFAULT, defaultSeasonLengths);
        seasonLengths.put(SeasonLengthSetting.LONG, longSeasonLengths);
        seasonLengths.put(SeasonLengthSetting.VERY_LONG, veryLongSeasonLengths);

        yearLengths = new EnumMap<SeasonLengthSetting, Integer>(SeasonLengthSetting.class);

        yearLengths.put(SeasonLengthSetting.VERY_SHORT, sumOfSeasonDays(veryShortSeasonLengths));
        yearLengths.put(SeasonLengthSetting.SHORT, sumOfSeasonDays(shortSeasonLengths));
        yearLengths.put(SeasonLengthSetting.DEFAULT, sumOfSeasonDays(defaultSeasonLengths));
        yearLengths.put(SeasonLengthSetting.LONG, sumOfSeasonDays(longSeasonLengths));
        yearLengths.put(SeasonLengthSetting.VERY_LONG, sumOfSeasonDays(veryLongSeasonLengths));

        seasonFirstDays = new EnumMap<Season, Integer>(Season.class);
    }

    public void run() {
        init();

        askSeasonLength();

        Scanner input = new Scanner(System.in);
        int userDay;
        int userSelection;

        userSelection = Integer.parseInt(input.nextLine());

        SeasonLengthSetting userLengthSetting = parseUserSeasonLengthSetting(userSelection);

        askCurrentDay();
        userDay = Integer.parseInt(input.nextLine());

        askStartingSeason();

        userSelection = Integer.parseInt(input.nextLine());
        Season userStartingSeason = parseUserStartingSeason(userSelection);

        populateSeasonFirstDays(userStartingSeason, userLengthSetting);
        
        int userYearLength = yearLengths.get(userLengthSetting);
        int userCurrentYear = userDay/userYearLength;

        int userNextSpring = (userCurrentYear * userYearLength)+ seasonFirstDays.get(Season.SPRING);
        int userNextSummer = (userCurrentYear * userYearLength) + seasonFirstDays.get(Season.SUMMER);
        int userNextAutumn = (userCurrentYear * userYearLength) + seasonFirstDays.get(Season.AUTUMN);
        int userNextWinter = (userCurrentYear * userYearLength) + seasonFirstDays.get(Season.WINTER);

        if (userNextSpring <= userDay) userNextSpring += userYearLength;
        if (userNextSummer <= userDay) userNextSummer += userYearLength;
        if (userNextAutumn <= userDay) userNextAutumn += userYearLength;
        if (userNextWinter <= userDay) userNextWinter += userYearLength;

        printNextSeasonDays(userNextSpring, userNextSummer, userNextAutumn, userNextWinter);
    }

    public void askSeasonLength() {
        System.out.println("\nWhat is your season length setting?\n\n" 
            + "Please choose from the following options:\n"
            + "1) Very Short\n"
            + "2) Short\n"
            + "3) Default\n"
            + "4) Long\n"
            + "5) Very Long");
    }

    public void askCurrentDay() {
        System.out.println("What is the current day?");
    }

    public void askStartingSeason() {
        System.out.println("\nWhat was your starting season?\n\n" 
            + "Please choose from the following options:\n"
            + "1) Autumn\n"
            + "2) Winter\n"
            + "3) Spring\n"
            + "4) Summer");
    }

    public void printNextSeasonDays(int userNextSpring, int userNextSummer, int userNextAutumn, int userNextWinter) {
        System.out.println("Next Spring is on Day " + userNextSpring + ".\n"
            + "Next Summer is on Day " + userNextSummer + ".\n"
            + "Next Autumn is on Day " + userNextAutumn + ".\n"
            + "Next Winter is on Day " + userNextWinter + ".");

    }

    public SeasonLengthSetting parseUserSeasonLengthSetting(int userSelection) {
        switch (userSelection) {
            case 1: return SeasonLengthSetting.VERY_SHORT;
            case 2: return SeasonLengthSetting.SHORT;
            case 3: return SeasonLengthSetting.DEFAULT;
            case 4: return SeasonLengthSetting.LONG;
            case 5: return SeasonLengthSetting.VERY_LONG;
            default: return null;
        }
    }

    public Season parseUserStartingSeason(int userSelection) {
        switch (userSelection) {
            case 1: return Season.AUTUMN;
            case 2: return Season.WINTER;
            case 3: return Season.SPRING;
            case 4: return Season.SUMMER;
            default: return null;
        }
    }

    public int sumOfSeasonDays(EnumMap<Season,Integer> seasonDays) {
        int sum = 0;
        Collection<Integer> seasonLengths = seasonDays.values();

        for (Integer length : seasonLengths) {
            sum += length;
        }

        return sum;
    }

    public void populateSeasonFirstDays(Season startingSeason, SeasonLengthSetting userLengthSetting) {
        int numberOfSeasons = Season.values().length;

        ArrayList<Season> correctSeasonOrder = new ArrayList<Season>(Arrays.asList(Season.values()));
        
        Season[] seasonOrder = new Season[numberOfSeasons];
        int iterationStart = correctSeasonOrder.indexOf(startingSeason);

        int j = 0;
        for (int i = iterationStart; i < iterationStart + numberOfSeasons; i++) {
            seasonOrder[j] = correctSeasonOrder.get(i % numberOfSeasons);
            j++;
        }

        seasonFirstDays.put(seasonOrder[0], 1);
        seasonFirstDays.put(seasonOrder[1], seasonFirstDays.get(seasonOrder[0]) + seasonLengths.get(userLengthSetting).get(seasonOrder[0]));
        seasonFirstDays.put(seasonOrder[2], seasonFirstDays.get(seasonOrder[1]) + seasonLengths.get(userLengthSetting).get(seasonOrder[1]));
        seasonFirstDays.put(seasonOrder[3], seasonFirstDays.get(seasonOrder[2]) + seasonLengths.get(userLengthSetting).get(seasonOrder[2]));
    }
}
