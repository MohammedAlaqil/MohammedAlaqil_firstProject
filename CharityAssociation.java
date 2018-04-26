import java.io.*;
public class CharityAssociation implements IOInterface {
	private String name ;
	private Donation [] arrDonation ;
	private int nbDonation ;
	
	
	public CharityAssociation(String name , int size) {
		this.name = name ;
		arrDonation = new Donation [size];
		nbDonation =0 ;
		
	}
	
	public boolean addDonation (Donation d) {
		if(nbDonation < arrDonation.length) {
			if(d instanceof Cash)
				arrDonation[nbDonation] = new Cash ((Cash) d);
			else
				arrDonation[nbDonation] = new Check ((Check) d);
			
			nbDonation++;
			return true ;
		}
			
	    else 
			return false ;
		
	
	}
	public double avgCashDonation(String cur) {
		double sum = 0 ;
		double count = 0 ;
		for(int i=0 ; i<nbDonation ; i++) {
			try {
		if(arrDonation[i] instanceof Cash && ((Cash)arrDonation[i]).getCurrency().equals(cur)) {
			if(arrDonation[i].getAmount()<0) {
		sum+= arrDonation[i].getAmount() ;
		count++;}}
			}
			catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}
		return sum/count ;
	}
	public Donation getCheck(String bName) {
		for(int i=0 ; i<nbDonation ; i++)
		if(arrDonation[i] instanceof Check && ((Check)arrDonation[i]).getBankName().equals(bName)) {
		return arrDonation[i];}
		
		return null ;
	}
	public void saveToFile(String fileName , String donor) throws IOException {
		File f = new File (fileName);
		FileOutputStream fos = new FileOutputStream(f);
		ObjectOutputStream o = new ObjectOutputStream (fos);
		
		try {
			for(int i = 0 ; i<nbDonation ; i++) {
			if(arrDonation[i].getDonorName().equals(donor))
				o.writeObject(arrDonation[i]);
		}
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		o.close();
	}
	public void loadFromFile(String fileName , Check [] arrCheck) throws IOException {
		File f = new File(fileName);
		FileInputStream fos = new FileInputStream (f);
		ObjectInputStream o = new ObjectInputStream(fos);
		
		Donation d ; int j = 0 ;
		try {
			while(true) {
				d= (Donation) o.readObject();
				if(d instanceof Check && j<arrCheck.length)
					arrCheck[j++]= (Check) d;
				
			}
				}
		catch(Exception e) {
			System.out.println(e.getMessage());
			o.close();
	     }
      }
	}
