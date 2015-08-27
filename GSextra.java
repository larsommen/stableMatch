
public class GSextra{
	int [][] maleprefs;

	public int[][] getMaleprefs() {
		return this.maleprefs;
	}

	int [][] femaleprefs;

	public int[][] getFemaleprefs() {
		return this.femaleprefs;
	}

	String [] names;
	String[] prefparts;
	int [] match;
	int [] currentPref;
	int[] nextFemale;
	ResizingArrayQueue freeMales;


	public GSextra() {
		String line =StdIn.readLine();

		while(line.charAt(0)=='#'){
			line=StdIn.readLine();
		}			

		String number =line;

		int n = Integer.valueOf(number.substring(2));

		System.out.println(" n is: "+ n);

		names = new String[2*n];

		System.out.println();

		for (int i=0; i<2*n; i++){
			line=StdIn.readLine();
			String[] parts = line.split(" ");
			names[i]=parts[1];
			System.out.println(names[i]+ i);
		}

			StdIn.readLine();

			maleprefs= new int[n] [n];
			femaleprefs = new int [n][n];

		while(StdIn.hasNextLine()){

			line = StdIn.readLine();

			prefparts = line.split(" ");

			int id = Integer.valueOf(prefparts[0].substring(0, prefparts[0].length()-1));

			for(int i=0; i<prefparts.length-1;i++){
				if(id%2==0){
					//female
					
					femaleprefs[(id/2)-1] [(Integer.valueOf(prefparts[i+1])-1)/2]= i;
				//	System.out.println("Ran female "+ id);

				}else{
					//male
					
					maleprefs[(id-1)/2][i]=((Integer.valueOf(prefparts[i+1]))/2)-1;
				//	System.out.println("Updated maleprefs: "+maleprefs[(id-1)/2][i]);
				//	System.out.println("Ran male "+id);

				}
			}
		
		}


		//Done passing



			//Do maching

		match = new int [n];

		for(int i =0; i<n;i++){
			match[i]=-1;
		}

		currentPref =new int [n];
		nextFemale= new int [n]; 
		freeMales = new ResizingArrayQueue();

		for (int i=0; i<n;i++){
			freeMales.enqueue(i);
			StdOut.println("enqued:" + i);
		}

	}


	public  int getFreeMan (){
		StdOut.println("getFreeMan ran");
		int freeMan = (int)freeMales.dequeue();
		return freeMan;
	}

	public  int getNextWoman (int manToPropose) {
		StdOut.println("getNextWoman ran");
		int womanNumber = nextFemale[manToPropose];
		int nextWoman = maleprefs[manToPropose][womanNumber];
		return nextWoman;
	}

	public boolean isMarried (int wantedWoman) {
		StdOut.println("isMarried ran");
		if (match[wantedWoman] == -1) {
			return false;
		}
		else return true;	
	}

	public boolean isBetter (int newMan, int woman) {
		int oldManPref = currentPref[woman];
		int newManPref = femaleprefs[woman][newMan];
		StdOut.println("isBetter ran");
		if (newManPref < oldManPref) {
			return true;
		} else return false;
	}

	public void getDivorceAndGetMarried (int woman, int newMan) {
		int oldMan = match[woman];
		match[woman] = newMan;
		freeMales.enqueue(oldMan);
		StdOut.println("Enqueued old man:" + oldMan);
	}

	public void getMarried (int woman, int newMan) {
		match[woman] = newMan;
		StdOut.println("getMarried ran");
	}

	public static void main(String[] args) {
		GSextra gs = new GSextra();
		while (!gs.freeMales.isEmpty()) {
			int freeMan = gs.getFreeMan();
			int womanToMarry = gs.getNextWoman(freeMan);
			boolean isMarried = gs.isMarried(womanToMarry);

			if (isMarried == true) {
				if (gs.isBetter(freeMan, womanToMarry) == true) {
					gs.getDivorceAndGetMarried(freeMan, womanToMarry);
				}else {
					gs.getNextWoman(freeMan);
				}
			} else {
				gs.getMarried(freeMan, womanToMarry);
			}
		}

		for (int i = 0; i<gs.match.length; i++){
			StdOut.println(i + " -- " + gs.match[i]);
		}
		
	}

}


