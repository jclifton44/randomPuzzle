import java.lang.annotation.*;
import java.io.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;
import java.lang.StringBuffer;
enum Sex {M,F}
class Bear {
// TODOBEGIN(EE422C)
	private String name;
	private Sex sex;
	private String mother;
	private String father;
	private double age;
	public Bear(String name, Sex sex, String mother, String father, double age) {
		this.name = name;
		this.sex = sex;
		this.mother = mother;
		this.father = father;
		this.age = age;
	}
	public Bear() {
		this.name = null;
		this.sex = null;
		this.mother = null;
		this.father = null;
		this.age = 0;
	}
	public void setName(String s) {
		this.name = s;
	}
	public String Name() {
		return this.name;
	}
	public void setSex(Sex s) {
		if(s == Sex.M) {
			this.sex = Sex.M;
		} else {
			this.sex = Sex.F;
		}
	
	}
	public Sex Sex() {
		return this.sex;
	}
	public void setMother(String s) {
		this.mother = new String(s);
	}
	public String Mother() {
		return this.mother;
	}
	public void setFather(String s) {
		this.father = new String(s);
	}
	public String Father() {
		return this.father;
	}
	public void setAge(double d) {
		this.age = d;
	}
	public double Age() {
		return this.age;
	}
	public boolean compareBear(Bear b) {
	if(	b.name == this.name &&
		b.mother == this.mother &&
		b.father == this.father &&
		b.age == this.age &&
		b.sex == this.sex) {
		return true;
	} else {
		return false;
	}
			
	}
// TODOEND(EE422C)
}
@Target(TYPE)

@Retention(RUNTIME)
@interface Author {
public String name() default "Jeremy Clifton" ;
public String uteid() default "jdc3887" ;
}
//TODO(EE422C): update these to your name and eid
@Author(name="Jeremy Clifton", uteid="jdc3887")
public class PairingConstraints {
	



	
	
//TODO(EE422C): implement your helper functions here
// TODOBEGIN(EE422C)
// TODOEND(EE422C)
	public static String[] concatStrings(String[] A, String[] B) {
		String[] returnString = new String[A.length+B.length];
		System.arraycopy(A, 0, returnString, 0, A.length);
		System.arraycopy(B, 0, returnString, A.length, B.length);
		return returnString;
		
		
	}
	public static String [] solve( String filename ) {
		// TODOBEGIN(EE422C)
		String [] stringOfBears = new String[0];
		try {
			FileInputStream fstream = new FileInputStream(filename);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			while((strLine = br.readLine()) != null) {
				//System.out.println(strLine);
				stringOfBears = concatStrings(stringOfBears,new String[]{strLine});
			}
				
		}
		catch(Exception e) {
			
		}
		
		return solve(stringOfBears);
		// TODOEND(EE422C)
	}
	
  	public static String [] solve( String[] data ) {
  		// TODOBEGIN(EE422C)
  		int index;
  		Bear [] bearList = parseData(data);
  		Bear tempBear;
  		
  		BinaryTree<String,Bear> fullTreeOfBears = new BinaryTree();  		
  		BinaryTree<String,Bear> offspringlessBears = new BinaryTree();  		
  		BinaryTree<String,Bear> femaleBear = new BinaryTree();  		
  		BinaryTree<String,Bear> maleBear = new BinaryTree();  		
  		BinaryTree<String,String> mateableBears = new BinaryTree();  		
  		for(Bear mybear: bearList) { 
  			fullTreeOfBears.insertion(mybear.Name(), mybear);
  		}
  		for(int j = 0; j < fullTreeOfBears.numOfNodes(fullTreeOfBears.root); j++) {
  			tempBear = fullTreeOfBears.lookupNode(j);
  			offspringlessBears.insertion(tempBear.Name(),tempBear);
  		}
  		for(int j = 0; j < fullTreeOfBears.numOfNodes(fullTreeOfBears.root); j++) {
  			offspringlessBears.deleteNode(fullTreeOfBears.lookupNode(j).Mother());
  			offspringlessBears.deleteNode(fullTreeOfBears.lookupNode(j).Father());
  			tempBear = fullTreeOfBears.lookupNode(j);
  			if(tempBear != null) {
  				if(tempBear.Age() > 6 || tempBear.Age() < 2) {
  					offspringlessBears.deleteNode(tempBear.Name());
  				}
  			}
  		}
  		for(int j = 0; j < offspringlessBears.numOfNodes(offspringlessBears.root); j++) {
  			tempBear = offspringlessBears.lookupNode(j);
  			if(tempBear.Sex() == Sex.M) {
  				maleBear.insertion(tempBear.Name(),tempBear);
  			}
  			if(tempBear.Sex() == Sex.F) {
  				femaleBear.insertion(tempBear.Name(),tempBear);
  			}
  		}	
  		
  		for(int j = 0; j < maleBear.numOfNodes(maleBear.root); j++) {
  			for(int k = 0; k < femaleBear.numOfNodes(femaleBear.root); k++) {
  					boolean matable = true;
  					Bear tempMale = maleBear.lookupNode(j);
  					Bear tempFemale = femaleBear.lookupNode(k);
  					double d = tempMale.Age() - tempFemale.Age();
  					if( ! ( d <= 1 && d >= -1) ) {
  						matable = false;
  					}
  					BinaryTree<String,Integer> femaleFamilyTree = new BinaryTree();
  					String mBearFather = tempMale.Father();
  					String mBearMother = tempMale.Mother();
  					String fBearFather = tempFemale.Father();
  					String fBearMother = tempFemale.Mother();
  					String mBearGM1 = null;
  					String mBearGP1 = null; 
  					String mBearGM2 = null;
  					String mBearGP2 = null; 
  					String fBearGM1 = null;
  					String fBearGP1 = null; 
  					String fBearGM2 = null;
  					String fBearGP2 = null; 
  					
  					if(fullTreeOfBears.getValue(mBearMother) != null) { 
  						mBearGM1 = fullTreeOfBears.getValue(mBearMother).Mother();
  						mBearGP1 = fullTreeOfBears.getValue(mBearMother).Father();
  					}
  					if(fullTreeOfBears.getValue(mBearFather) != null) { 
  						mBearGM2 = fullTreeOfBears.getValue(mBearFather).Mother();
  						mBearGP2 = fullTreeOfBears.getValue(mBearFather).Father();
  					}
  					if(fullTreeOfBears.getValue(fBearMother) != null) { 
  						fBearGM1 = fullTreeOfBears.getValue(fBearMother).Mother();
  						fBearGP1 = fullTreeOfBears.getValue(fBearMother).Father();
  					}
  					if(fullTreeOfBears.getValue(fBearFather) != null) { 
  						fBearGM2 = fullTreeOfBears.getValue(fBearFather).Mother();
  						fBearGP2 = fullTreeOfBears.getValue(fBearFather).Father();
  					}
  					
  					
  					femaleFamilyTree.insertion(fBearFather, 1);
  					femaleFamilyTree.insertion(fBearMother, 1);
  					femaleFamilyTree.insertion(fBearGM1, 1);
  					femaleFamilyTree.insertion(fBearGP1, 1);
  					femaleFamilyTree.insertion(fBearGM2, 1);
  					femaleFamilyTree.insertion(fBearGP2, 1);
  					
  					if(
  						(femaleFamilyTree.lookup(mBearMother) != null) ||
  						(femaleFamilyTree.lookup(mBearFather) != null) ||
  						(femaleFamilyTree.lookup(mBearGM1) != null) ||
  						(femaleFamilyTree.lookup(mBearGM2) != null) ||
  						(femaleFamilyTree.lookup(mBearGP1) != null) ||
  						(femaleFamilyTree.lookup(mBearGP2) != null) 
  						) 
  					{ matable = false; }
  					
  					if(matable) {
  						mateableBears.insertion(tempMale.Name() + " + " + tempFemale.Name(), tempMale.Name() + " + " + tempFemale.Name());
  					}

  			}  			
  		}
  		String[] mateableArrayBears = new String[0];
  		for(int j = 0; j < mateableBears.numOfNodes(mateableBears.root); j++) {
				mateableArrayBears = concatStrings(mateableArrayBears,new String[]{ mateableBears.lookupNode(j)});
  		}
  		return mateableArrayBears;
  		// TODOEND(EE422C)
  	}	
  	
  	public static String parseName(String s) {
		if (s.equals("_nil"))
			return s;
		return (unescapeString(s).trim()).toLowerCase();
	}
	public static Bear [] parseData (String [] s) {
		Bear [] returnArray = new Bear[s.length];
		int index = 0;
		for (index = 0; index < s.length; index++) {
			String[] bearInfo = s[index].split(":");
			Bear newBear = new Bear();
			newBear.setName(parseName(bearInfo[0]));
			if(bearInfo[1].trim().equals("M") || bearInfo[1].trim().equals("m")) {
				newBear.setSex(Sex.M);
			} else {
				newBear.setSex(Sex.F);
			}
			newBear.setMother(parseName(bearInfo[2]));
			newBear.setFather(parseName(bearInfo[3]));
			newBear.setAge(Double.parseDouble(bearInfo[4]));
			returnArray[index] = newBear;
		}
		return returnArray;
	}		
	
	
	
	public static String unescapeString(String s) {
		StringBuffer tempString = new StringBuffer(4*s.length());
		for(int i = 0; i < s.length(); i++) {
			//System.out.println(s.charAt(i));
			//System.out.println((int)s.charAt(i));
			
			if(s.charAt(i) == 10){
				// \n
				tempString.append("\\n");
			} else if (s.charAt(i) == 9) {
				// \t 
				tempString.append("\\t");
				
			} else if (s.charAt(i) == 13) {
				// \r
				tempString.append("\\r");
			} else if (s.charAt(i) == 12) {
				// \f
				tempString.append("\\f");
			} else if (s.charAt(i) == 8) {
				// \b
				tempString.append("\\b");
			} else if (s.charAt(i) == 39) {
				// \"
				tempString.append("\\\"");
			} else if (s.charAt(i) == 34) {
				// \'
				tempString.append("\\\'");
			} else if (s.charAt(i) == 92) {
				// \\
				tempString.append("\\\\");
			} else {
				tempString.append(s.charAt(i));
			}
		}
		return tempString.toString();	
					
	}

}