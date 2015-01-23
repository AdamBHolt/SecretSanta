/**
* This class is used with the SantaChooser program to provide information for
* each person participating in a Secret Santa gift exchange
*/
public class FamilyMember {

    //Name of the family member
    private String name;
    //Email address of the family member
    private String email;
    //Spouse of the family member
    private String spouse;
    //First secret santa assignment
    private String santa1;
    //Second secret santa assignment
    private String santa2;
    //Password
    private String password;
    //Number of times this family member has been assigned to another
    private int picked;

    public FamilyMember()
    {
		setName("");
		setEmail("");
		setSpouse("");
		setPassword("");
		santa1 = "";
		santa2 = "";
		picked = 0;
    }

    public FamilyMember(String n, String e, String s, String p)
    {
		setName(n);
		setEmail(e);
		setSpouse(s);
		setPassword(p);
		santa1 = "";
		santa2 = "";
		picked = 0;
    }
    
    public FamilyMember(String n, String e, String s, String p, String s1, String s2)
    {
		setName(n);
		setEmail(e);
		setSpouse(s);
		setPassword(p);
		santa1 = s1;
		santa2 = s2;
		picked = 0;
    }

    public String toString()
    {
		String newName=name;
		String newEmail=email;
		String newSpouse=spouse;
		String newSanta1=santa1;
		String newSanta2=santa2;
		int pad = 10 - name.length();
		for(int i=0; i<pad; i++)
		    newName+=" ";
		
		pad = 25 - email.length();
		for(int i=0; i<pad; i++)
		    newEmail+=" ";
		
		pad = 10 - spouse.length();
		for(int i=0; i<pad; i++)
		    newSpouse+=" ";
		
		pad = 10 - santa1.length();
		for(int i=0; i<pad; i++)
		    newSanta1+=" ";
		
		pad = 10 - santa2.length();
		for(int i=0; i<pad; i++)
		    newSanta2+=" ";
		
		return "Name: " + newName + "Email: " + newEmail + "Spouse: " + newSpouse + "Santa 1: " + newSanta1 + "Santa 2: " + newSanta2; 
    }

    public void setName(String n)
    {
    	name = n;
    }

    public String getName()
    {
    	return name;
    }

    public void setEmail(String e)
    {
    	email = e;
    }

    public String getEmail()
    {
    	return email;
    }

    public void setSpouse(String s)
    {
    	spouse = s;
    }

    public String getSpouse()
    {
    	return spouse;
    }

    public boolean marriedTo(FamilyMember f)
    {
    	return spouse.equals(f.getName());
    }

    public void setPassword(String p)
    {
    	password = p;
    }
    
    public String getPassword()
    {
    	return password;
    }
    
    public void setSanta1(FamilyMember f)
    {
    	santa1 = f.getName();
    }

   	public String getSanta1()
    {
   		return santa1;
    }

    public void setSanta2(FamilyMember f)
    {
		santa2 = f.getName();
    }

    public String getSanta2()
    {
    	return santa2;
    }

    public void pick()
    {
    	picked++;
    }

    public int picked()
    {
    	return picked;
    }

    public void resetPicked()
    {
    	picked = 0;
    }
    
    public boolean equals(FamilyMember otherMember)
    {
    	return getName().equals(otherMember.getName());
    }
    
    public String outputString()
    {
    	return getName() + " " + getEmail() + " " + getSpouse() + " " + getPassword() + " " + getSanta1() + " " + getSanta2();
    }
}