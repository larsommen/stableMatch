
public class GS{



	public static void main(String[] args) {


					String line =StdIn.readLine();

					while(line.charAt(0)=='#'){
						line=StdIn.readLine();
					}

				

					String number =line;

		int n = Integer.valueOf(number.substring(2));

		System.out.println(" n is: "+ n);

		String []names = new String[2*n];

		System.out.println();

		for (int i=0; i<2*n; i++){
			line=StdIn.readLine();
			String[] parts = line.split(" ");
			names[i]=parts[1];
			System.out.println(names[i]+ i);
		}

			StdIn.readLine();

			int [] [] maleprefs= new int[n] [n];
			int [] [] femaleprefs = new int [n][n];

		while(StdIn.hasNextLine()){

			line = StdIn.readLine();

			String[] prefparts = line.split(" ");

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

			int [] match = new int [n];

			for(int i =0; i<n;i++){
				match[i]=-1;
			}

			int [] currentPref =new int [n];


			int[] nextFemale= new int [n]; 

			ResizingArrayQueue freeMales= new ResizingArrayQueue();

			for (int i=0; i<n;i++){
				freeMales.enqueue(i);
			}

			while(!freeMales.isEmpty()){
				int currentMale=(int)freeMales.dequeue();
				System.out.println("Current male is: "+currentMale);

				int currentFemale = maleprefs[currentMale][nextFemale[currentMale]];
				nextFemale[currentMale]++;


				//check if currentFemale is awailable
				//If - update match and currentPreff

					System.out.println("currentFemale: "+currentFemale+" currentMale: "+ currentMale);
					//while currentMale not preffered increment nextFemale and get her Id
					while(match[currentFemale]!=-1 && (currentPref[currentFemale]<femaleprefs[currentFemale][currentMale]&&currentPref[currentFemale]>0)){

						System.out.println("currentFemale: "+currentFemale+" currentMale: "+ currentMale+" nextFemale[currentMale]: "+ nextFemale[currentMale]+" in the while");
						nextFemale[currentMale]++;
;
						currentFemale = maleprefs[currentMale][nextFemale[currentMale]];

						System.out.println("currentFemale "+currentFemale);

					}
					// currentFemale is free - make mathc

					if(match[currentFemale]==-1){
					match[currentFemale]=currentMale;
					currentPref[currentFemale]= femaleprefs[currentFemale][currentMale];
					System.out.println("----- Matching unmatched female");
						}else{


					// currentFemale is in relation but prefers currentMale
					// put currentFemala's mathc on stack and update match and currentPref[currentFemale]

					System.out.println("MatchcurrentFemale: "+ match[currentFemale]);
					freeMales.enqueue(match[currentFemale]);

					System.out.println("++++++ Exchangeing male: "+match[currentFemale]+" with male: "+currentMale);


					match[currentFemale]=currentMale;

					currentPref[currentFemale]= femaleprefs[currentFemale][currentMale];

					System.out.println("currentFemale: "+currentFemale+" is now matched with male: "+currentMale);


				}

				


				


				

			}




			//Do printout

		for (int i=0; i<n;i++){
				System.out.println("Femalel: ");
				System.out.println("Id = "+i+" ");
			for (int j=0; j<n; j++){
				System.out.printf(femaleprefs[i][j]+" ");
			}
			System.out.println();
			System.out.println("Males: ");
			System.out.println("Id = "+i+" ");

			for (int j=0; j<n; j++){

				System.out.printf(maleprefs[i][j]+" ");
			}
			System.out.println();
		}

		for(int i=0; i<n;i++){
			
			int countfemale=(1+2*i);
			
			int countmale=2*match[i];
			//System.out.println("countfem: "+countfem);
			//System.out.println("Female: "+countfem+" male: "+countmal);
			//System.out.println("Female no: "+names[countfem]+" has been matched to male no: "+names[countmal]);

			System.out.println(names[countmale]+" --- "+names[countfemale]);
		}


	}
}