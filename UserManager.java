package dataApp;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.time.LocalDate;
import java.util.Random;
import java.time.Period;

import java.io.Serializable;

public class UserManager {
    public static List<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
    }

    // Method to add a user to the list
    public void addUser(User user) {
        users.add(user);
    }

    // Method to remove a user from the list
    public void removeUser(User user) {
        users.remove(user);
    }

    // Method to get all users
    public List<User> getUsers() {
        return users;
    }

    public static void start() {
        // Creating an instance of UserManager
        UserManager userManager = new UserManager();

        String name = generateRandomName();
        LocalDate DOB = generateRandomDate();
        int age = getAgeFromDate(DOB);
        boolean maritalStat = randomBoolean();
        boolean isGPS = randomBoolean();
        String Address = randomAddress();
        User user = new User(name, age, DOB, maritalStat, isGPS, Address);
        userManager.addUser(user);

        System.out.println("Name: " + user.getName() + ", Age: " + user.getAge() + " " + user.getDOB() + ", Married: " + user.getMarital() + ", GPS: " + user.getGPS() + ", Address: " + user.getAddress() );
    }

    private static int createRandomIntBetween(int start, int end) {
        return start + (int) Math.round(Math.random() * (end - start));
    }
    // Method to generate a random name
    private static String generateRandomName() {
        String[] firstnames = {"Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace", "Henry", "Ivy", "Jack"};
        String[] lastnames = {"Smith", "Jones", "Clarke", "Obama"};
        Random random = new Random();
        String firstname = firstnames[random.nextInt(firstnames.length)];
        String lastname = lastnames[random.nextInt(lastnames.length)];
        return firstname + " " + lastname;
    }

    // Method to generate a random age between 18 and 60
    private static LocalDate generateRandomDate() {
        int day = createRandomIntBetween(1, 28);
        int month = createRandomIntBetween(1, 12);
        int year = createRandomIntBetween(1970, 2000);
        return LocalDate.of(year, month, day);

    }
    private static int getAgeFromDate(LocalDate date){
        LocalDate curDate = LocalDate.now();
        return Period.between(date, curDate).getYears();

    }

    private static boolean randomBoolean(){
        boolean status = true;
        int num = createRandomIntBetween(1,2);
        if (num == 1){
            status = true;
        } else {
          status = false;
        }
        return status;
    }

    private static String randomAddress(){
        String[] addresses = {"88 Manchester Road, WEST LONDON, W79 5WX", "68 York Road, DUMFRIES, DG18 1UX", "963 Manor Road, EDINBURGH, EH84 1RK"};
        Random random = new Random();
        return addresses[random.nextInt(addresses.length)];
    }

	public static String[] getUser() {
		// turn user into array of strings for agent
		User curUser = users.get(0);
		String[] userArr = new String[5];
		userArr[0] = curUser.getName();
		userArr[1] = Integer.toString(curUser.getAge());
		userArr[2] = curUser.getDOB().toString();
		userArr[3] = String.valueOf(curUser.getMarital());
		userArr[4] = String.valueOf(curUser.getGPS());
		userArr[5] = curUser.getAddress();
		
		return userArr;		
		
	}

}
