
public class GS{
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

	int n;


	public GS() {
		
		
		this.parsefile();

		match = new int [n];

		for(int i =0; i<n;i++){
			match[i]=-1;
		}

		currentPref =new int [n];
		nextFemale= new int [n]; 
		freeMales = new ResizingArrayQueue();

		for (int i=0; i<n;i++){
			freeMales.enqueue(i);
		}

	}

	public void parsefile(){

		String line =StdIn.readLine();

		while(line.charAt(0)=='#'){
			line=StdIn.readLine();
		}			

		String number =line;

		n = Integer.valueOf(number.substring(2));


		names = new String[2*n];


		for (int i=0; i<2*n; i++){
			line=StdIn.readLine();
			String[] parts = line.split(" ");
			names[i]=parts[1];
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
					
					femaleprefs[(id/2)-1] [(Integer.valueOf(prefparts[i+1])-1)/2]= i;

				}else{
					
					maleprefs[(id-1)/2][i]=((Integer.valueOf(prefparts[i+1]))/2)-1;

				}
			}
		}
	}


	public  int getFreeMan (){
		int freeMan = (int)freeMales.dequeue();
		return freeMan;
	}

	public  int getNextWoman (int manToPropose) {
		int womanNumber = nextFemale[manToPropose];
		int nextWoman = maleprefs[manToPropose][womanNumber];
		nextFemale[manToPropose]++;
		return nextWoman;
	}

	public boolean isMarried (int wantedWoman) {
		if (match[wantedWoman] == -1) {
			return false;
		}
		else return true;	
	}

	public boolean isBetter (int newMan, int woman) {
		int oldManPref = currentPref[woman];
		int newManPref = femaleprefs[woman][newMan];
		if (newManPref < oldManPref) {
			return true;
		} else return false;
	}

	public void getDivorceAndGetMarried (int newMan, int woman) {
		int oldMan = match[woman];
		match[woman] = newMan;
		currentPref[woman] = femaleprefs[woman][newMan];
		freeMales.enqueue(oldMan);
	}

	public void getMarried (int newMan, int woman) {
		match[woman] = newMan;
		currentPref[woman] = femaleprefs[woman][newMan];
	}

	public static void main(String[] args) {
		GS gs = new GS();
		while (!gs.freeMales.isEmpty()) {
			int freeMan = gs.getFreeMan();

			boolean gotMarried = false;

			while (gotMarried == false) {
				int womanToMarry = gs.getNextWoman(freeMan);
				boolean isMarried = gs.isMarried(womanToMarry);

				if (isMarried == true) {
					if (gs.isBetter(freeMan, womanToMarry) == true) {
						gs.getDivorceAndGetMarried(freeMan, womanToMarry);
						gotMarried = true;

					}else {
						continue;
					}
				} else {
					gs.getMarried(freeMan, womanToMarry);
					gotMarried = true;
				}
			}
		}


		for(int i=0; i<gs.match.length;i++){
			int countfemale=(1+2*i);
			int countmale=2*gs.match[i];
			System.out.println(gs.names[countmale]+" -- "+gs.names[countfemale]);
		}
		
	}

}


