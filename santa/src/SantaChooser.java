import java.util.*;
import java.io.*;

public class SantaChooser
{
	//File to read data to create FamilyMember objects
	private final String DATA_FILE = "Family.txt";
	//File to write Secret Santa choices
	private final String OUT_FILE = "Santas.txt";
	private Scanner inputFile;
	private ArrayList<FamilyMember> family;
	private Random rand;
	File outFile;
	PrintWriter outputFile;

	/** Main method - needed when not using the GUI */
	public static void main(String [] args)
	{
		new SantaChooser();
	}

	/** Default constructor */
	public SantaChooser()
	{
		family = new ArrayList<>();
		buildList();
		pickSanta();
	}

	/** Use the arrays to build an ArrayList of FamilyMember objects with the relevant data */
	private void buildList()
	{
		File inFile = new File(DATA_FILE);

		try
		{
			inputFile = new Scanner(inFile);
		} 
		catch (FileNotFoundException e)
		{e.printStackTrace();}

		//Populate family with FamilyMember objects made from input file
		while(inputFile.hasNext())
		{
			family.add(new FamilyMember(inputFile.next(),inputFile.next(),inputFile.next(),inputFile.next()));
		}
	}

	/**
	 * Scans through each FamilyMember in the family ArrayList
	 * and randomly assigns two other FamilyMembers from family
	 * to the santa1 and santa2 members
	 */
	private void pickSanta()
	{
		//Initialize output file
		outFile = new File(OUT_FILE);
		try
		{
			outputFile = new PrintWriter(OUT_FILE);
		}
		catch (FileNotFoundException e)
		{e.printStackTrace();}

		rand = new Random(new Date().getTime());
	
		int i, j; //Indexes of the FamilyMembers being picked
		boolean failed; //Flag to indicate failure of a pick

		//Pick at least once - failures will result in multiple picks
		do
		{
			failed = false; //Initial assumption that the pick will not fail

			//Scan through each element of family
			for(int x = 0; x < family.size(); x++)
			{
				int k = 0; //Counter to keep track of the number of failed attempts to pick santas
				
				do{
					//Pick a FamilyMember at random until one is found that hasn't been picked twice
					do
						i = newRand();
					while(family.get(i).picked() >= 2);

					do 
						j = newRand();
					while(family.get(j).picked() >= 2);

					//If invalid picks were made for this FamilyMember 50 times
					//It is likely that the pool is exhausted
					if(k++ > 50)
					{
						failed = true; //Indicate that the pick has failed
						x = 0; //Reset to the first FamilyMember
						
						//Reset the output file
						outputFile.close();
						try
						{
							outputFile = new PrintWriter(OUT_FILE);
						} 
						catch (FileNotFoundException e)
						{e.printStackTrace();}
						
						//Reset the picked member for each FamilyMember
						reset();
						//System.out.println("Failed, repicking...");
						//Exit the picking
						break;
					}
					
					//Repick for this FamilyMember if the picked FamilyMembs arere the same
					//The FamilyMember was picked for themself
					//or the FamilyMember is married to either of its picked FamilyMembers
				} while(i==j || i==x || j==x || family.get(x).marriedTo(family.get(i)) || family.get(x).marriedTo(family.get(j)));

				//If the last pick failed skip the assignment of santas and restart the loop
				if(failed)
					continue;

				FamilyMember member = family.get(x);
			    FamilyMember santa1 = family.get(i);
			    FamilyMember santa2 = family.get(j);
				
				//Assign the picked FamilyMembers to the appropriate santa member
				family.get(x).setSanta1(santa1);
				family.get(x).setSanta2(santa2);

				//Indicate that the picked FamilyMembers have been picked
				santa1.pick();
				santa2.pick();
				
				//Output the current family member's data members to the output file
				outputFile.println(member.outputString());
			}

			//Check whether any two family members both have the same secret Santas
			for(FamilyMember member1: family)
			{
				for(FamilyMember member2: family)
					if(!member1.equals(member2))
					{
						//If bothe of th
						if(member1.getSanta1().equals(member2.getSanta1()) && member1.getSanta2().equals(member2.getSanta2()))
							failed = true;
						if(member1.getSanta1().equals(member2.getSanta2()) && member1.getSanta2().equals(member2.getSanta1()))
							failed = true;
					}
				if(failed)
					System.out.println("Failed - duplicates");
			}

		//Repeat this loop if any pick attempt fails
		} while (failed);

		System.out.println("Santas picked....");


		//Semd emails to each FamilyMember in family
		for(FamilyMember member: family)
		{
			System.out.println(member.outputString());
			sendEmail(member.getName(),member.getEmail(),member.getSanta1(),member.getSanta2());
		}

		outputFile.close();
	}

	/**
	 * Set the picked field for each element in family to 0
	 * Allows for re-picking after a failed attempt
	 */
	private void reset()
	{
		//Reset the picked field for each FamilyMember in family
		for(FamilyMember member: family)
		{
			member.resetPicked();
		}
	}

	/**
	 * Generates a random number from 1 to the number of members in the family
	 * @return A random integer from 0 to 1 less than the size of family
	 */
	private int newRand()
	{
		return rand.nextInt(family.size());
	}

	/**
	 * Creates an instance of SendEmail with information needed to send an email to each
	 * FamilyMember in the family ArrayList
	 * @param to The name of the email recipient
	 * @param email The email address of the recipient
	 * @param santa1 The first santa pick for the recipient
	 * @param santa2 The second santa pick for the recipient
	 */
	private void sendEmail(String to, String email, String santa1, String santa2)
	{

		//The body of the email message to be sent
		String bod = to + ",\nThis year, you will be secret Santa for:\n\n" +
				santa1 + "\n&\n" + santa2;
		//The subject line of the email to be sent
		String subject = "Your Secret Santa Picks";
		//Create an instance of SendEmail with the necessary email information
		new SendEmail().send(email, subject, bod);
	}
}