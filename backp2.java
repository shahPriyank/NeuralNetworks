import java.util.*;
import java.io.*;
//import java.text.DecimalFormat;
class preprocessing{
	public static ArrayList<ArrayList<String>> data = new ArrayList<>();
	public static ArrayList<ArrayList<String>> numericData = new ArrayList<>();
	public static ArrayList<Integer> integerFlag = new ArrayList<Integer>();
	public static void readData(String fileName) throws IOException{
		File f = new File(fileName);
		String fileNames = f.getName();
		if (fileNames.equals("cardata.csv")) {
			// CAR DATASET
			integerFlag = new ArrayList<Integer>(Arrays.asList(0,0,0,0,0,0,0));
			data.add(new ArrayList<String> (Arrays.asList("vhigh","high","med","low")));
			data.add(new ArrayList<String> (Arrays.asList("vhigh","high","med","low")));
			data.add(new ArrayList<String> (Arrays.asList("2","3","4","5more")));
			data.add(new ArrayList<String> (Arrays.asList("2","4","more")));
			data.add(new ArrayList<String> (Arrays.asList("small","med","big")));
			data.add(new ArrayList<String> (Arrays.asList("low","med","high")));
			data.add(new ArrayList<String> (Arrays.asList("unacc","acc","good","vgood")));
		}
		else if (fileNames.equals("irisdata.csv")) {
			// IRIS DATASET
			integerFlag = new ArrayList<Integer>(Arrays.asList(1,1,1,1,0));
			data.add(new ArrayList<String> (Arrays.asList("0")));
			data.add(new ArrayList<String> (Arrays.asList("0")));
			data.add(new ArrayList<String> (Arrays.asList("0")));
			data.add(new ArrayList<String> (Arrays.asList("0")));
			data.add(new ArrayList<String> (Arrays.asList("Iris-setosa","Iris-versicolor","Iris-virginica")));
			
		}
		
		else if (fileNames.equals("adultdata.csv")) {
			// ADULT DATASET
			integerFlag = new ArrayList<Integer>(Arrays.asList(1,0,1,0,1,0,0,0,0,0,1,1,1,0,0));
			data.add(new ArrayList<String> (Arrays.asList("0")));
			data.add(new ArrayList<String> (Arrays.asList(" Private", " Self-emp-not-inc", " Self-emp-inc", " Federal-gov", " Local-gov", " State-gov", " Without-pay", " Never-worked")));
			data.add(new ArrayList<String> (Arrays.asList("0")));
			data.add(new ArrayList<String> (Arrays.asList(" Bachelors", " Some-college", " 11th", " HS-grad", " Prof-school", " Assoc-acdm", " Assoc-voc", " 9th", " 7th-8th", " 12th", " Masters", " 1st-4th", " 10th", " Doctorate", " 5th-6th", " Preschool")));
			data.add(new ArrayList<String> (Arrays.asList("0")));
			data.add(new ArrayList<String> (Arrays.asList(" Married-civ-spouse", " Divorced", " Never-married", " Separated", " Widowed", " Married-spouse-absent", " Married-AF-spouse")));
			data.add(new ArrayList<String> (Arrays.asList(" Tech-support", " Craft-repair", " Other-service", " Sales", " Exec-managerial", " Prof-specialty", " Handlers-cleaners", " Machine-op-inspct", " Adm-clerical", " Farming-fishing", " Transport-moving", " Priv-house-serv", " Protective-serv", " Armed-Forces")));
			data.add(new ArrayList<String> (Arrays.asList(" Wife", " Own-child", " Husband", " Not-in-family", " Other-relative", " Unmarried")));
			data.add(new ArrayList<String> (Arrays.asList(" White", " Asian-Pac-Islander", " Amer-Indian-Eskimo", " Other", " Black")));
			data.add(new ArrayList<String> (Arrays.asList(" Female", " Male")));
			data.add(new ArrayList<String> (Arrays.asList("0")));
			data.add(new ArrayList<String> (Arrays.asList("0")));
			data.add(new ArrayList<String> (Arrays.asList("0")));
			data.add(new ArrayList<String> (Arrays.asList(" United-States", " Cambodia", " England", " Puerto-Rico", " Canada", " Germany", " Outlying-US(Guam-USVI-etc)", " India", " Japan", " Greece", " South", " China", " Cuba", " Iran", " Honduras", " Philippines", " Italy", " Poland", " Jamaica", " Vietnam", " Mexico", " Portugal", " Ireland", " France", " Dominican-Republic", " Laos", " Ecuador", " Taiwan", " Haiti", " Columbia", " Hungary", " Guatemala", " Nicaragua", " Scotland", " Thailand", " Yugoslavia", " El-Salvador", " Trinadad&Tobago", " Peru", " Hong", " Holand-Netherlands")));
			data.add(new ArrayList<String> (Arrays.asList(" >50K", " <=50K")));

		}		
		BufferedReader dataBR = new BufferedReader(new FileReader(new File(fileName)));
    	String line = "";
	    while ((line = dataBR.readLine()) != null) { 
	        String[] value = line.split(",");
	        //Ignoring the missing values
	        for (int i=0;i<value.length;i++ ) {
	        	if (value[i].equals(" ?")) {
	        		line=dataBR.readLine();
	        		value=line.split(",");
	        		break;
	        	}
	        }
	    	ArrayList<String> innerNumericData = new ArrayList<String>();
	    	for (int i=0;i<data.size();i++ ) {
	    		if (integerFlag.get(i)==1) {
	    			innerNumericData.add(i,value[i]);
	    		}
	    		else {
	    			int pos = data.get(i).indexOf(value[i]);
	    			innerNumericData.add(i,Integer.toString(pos));
	    		}
	    	}
	    	numericData.add(innerNumericData);
	    }
	}

	public static void convertData() throws IOException{
		for (int i=0;i<integerFlag.size()-1;i++ ) {
			if (integerFlag.get(i)==1) {
				double mean = calculateMean(i);
				double sd = calculateSd(i,mean);
				if (sd!=0) {
					standardize(i,mean,sd);
				}
			}
		}
	}

	public static double calculateMean(int position){
		double total = 0;
        for ( int i= 0;i < numericData.size(); i++)
        {
            double currentNum = Double.parseDouble(numericData.get(i).get(position));
            total+= currentNum;
        }
        return total/ numericData.size();
	}

	public static double calculateSd(int position, double mean){
		double total = 0;
		for ( int i= 0;i < numericData.size(); i++)
        {
            double currentNum = Double.parseDouble(numericData.get(i).get(position))- mean;
            currentNum = Math.pow(currentNum,2);
            total+= currentNum;
        }
        total = total/numericData.size();
        return Math.sqrt(total);
	}

	public static void standardize(int position, double mean, double sd){
		for (int i=0;i<numericData.size();i++ ) {
			double temp = (Double.parseDouble(numericData.get(i).get(position))- mean)/sd;
			numericData.get(i).set(position, Double.toString(temp));
		}
	}

	public static void writeData(String fileName) throws FileNotFoundException{
		PrintWriter pw = new PrintWriter(new File(fileName));
        StringBuilder sb = new StringBuilder();
        for (int i=0;i<numericData.size();i++ ) {
        	for (int j=0;j<numericData.get(i).size();j++ ) {
        		sb.append(numericData.get(i).get(j));
        		if (j!=numericData.get(i).size()-1) {
        			sb.append(',');
        		}
        	}
        	sb.append('\n');
        }
        pw.write(sb.toString());
        pw.close();
	}
}

class backp{
	public static ArrayList<ArrayList<Double>> finalData = new ArrayList<ArrayList<Double>>();
	public static ArrayList<ArrayList<Double>> newData = new ArrayList<ArrayList<Double>>();
	public static int xValueCount = 0;
	public static ArrayList<Double> xValues;
	public static ArrayList<Double> deltaValues;
	public static double classValue;
	public static double rate = 0.5;
	public static int xp;
	public static ArrayList<Integer> sumValues;
	public static ArrayList<Integer> newSumValues;
	public static ArrayList<Integer> biasFlag = new ArrayList<Integer>();
	public static ArrayList<ArrayList<Double>> storage = new ArrayList<ArrayList<Double>>();
	public static void readData(String fileName, ArrayList<Integer> a, int train_size, int attributes, int max_iterations) throws IOException{
		sumValues = new ArrayList<Integer>();
		xp = attributes;
		BufferedReader dataBR = new BufferedReader(new FileReader(new File(fileName)));
    	String line = "";
    	while ((line = dataBR.readLine()) != null) {
    		String[] value = line.split(",");
    		ArrayList<Double> innerStorage = new ArrayList<Double>();
    		for (int i = 0; i < value.length; i++) {
				innerStorage.add(Double.parseDouble(value[i]));
			}
    		storage.add(innerStorage);
		}
    	Collections.shuffle(storage);
	    int counter = 0;
	    double randomValue;
	    ArrayList<Double> line2 = new ArrayList<Double>();
	    while (counter<train_size) {
	    	line2 = storage.get(counter);
	    	classValue = line2.get(line2.size()-1);
	    	xValues = new ArrayList<Double>(); 
    		for (int i=0;i<line2.size();i++) {
    			xValues.add(line2.get(i));
    		}
    		xValues.remove(xValues.size()-1);
	    	if (counter==0) {
	    		// INITIALIZE RANDOM VALUES
		    	for (int i=1;i<a.size()-1;i++) {
		    		int start = a.get(i);
		    		int end = a.get(i+1);
		    		for (int j=0;j<end;j++) {
		    			ArrayList<Double> innerFinalData = new ArrayList<Double>();
		    			for (int k=0;k<start;k++) {
		    				randomValue = calculateRandom(); 
		    				innerFinalData.add(randomValue);
		    			}
		    			randomValue = calculateRandom(); //random value for bias
		    			innerFinalData.add(randomValue);
		    			finalData.add(innerFinalData);
		    		}
		    	}
		    	a.set(1,0);
		    	// INITIALIZED DONE
		    	sumValues.add(0);
				for (int i=1;i<a.size()-1;i++ ) {
					sumValues.add(sumValues.get(sumValues.size()-1)+a.get(i));
				}
				sumValues.add(sumValues.get(sumValues.size()-1)+1);
				for (int i=1;i<sumValues.size()-1;i++ ) {
	    			transpose(sumValues.get(i),sumValues.get(i+1));	
	    		}
				newSumValues = calculateArray(sumValues);
	    	}
    		// COMPUTE X VALUES FOR EVERY NODE
	    	xValues.add(1.0);
	    	for (int i=1;i<a.size()-1;i++) {
	    		computeX(sumValues.get(i),sumValues.get(i+1),0,attributes);
	    	}
	    	xValueCount=0;
	    	xValues.remove(xValues.size()-1); // REMOVE THE LAST 1.0
	    	if(counter == 0) {
		    	for (int i=0;i<xValues.size();i++) {
		    		if ((i>attributes-1) && (xValues.get(i) == 1.0)) {
		    			biasFlag.add(1);
		    		}
		    		else{
		    			biasFlag.add(0);
		    		}
		    	}
	    	}
			computeDelta(attributes);
			reverseDelta();
			computeDeltaW(attributes);
			while (deltaValues.size()!=0) {
				deltaValues.remove(0);
			}
			for (int i=0;i<newSumValues.size()-1;i++) {
				transposeValues(newSumValues.get(i),newSumValues.get(i+1), sumValues.get(i+1));
			}
			for (int iter=0;iter<max_iterations-1;iter++) {
				for (int i=1;i<a.size()-1;i++) {
	    			computeX(sumValues.get(i),sumValues.get(i+1),1,attributes);
	    		}
	    		xValueCount=0;
	    		xp=attributes;
	    		computeDelta(attributes);
	    		reverseDelta();
	    		if (deltaValues.get(0)<0.000001) {
	    			break;
	    		}
	    		computeDeltaW(attributes);
	    		while(deltaValues.size()!=0) {
	    			deltaValues.remove(0);
	    		}
	    		for (int i=0;i<newSumValues.size()-1;i++) {
					transposeValues(newSumValues.get(i),newSumValues.get(i+1), sumValues.get(i+1));
				}
			}
			counter++; //don't change
	    }
	}

	public static void transposeValues(int start, int end, int c){
		int temp = newData.get(start).size();
		for (int i=0;i<temp;i++ ) {
			ArrayList<Double> innerFinal = new ArrayList<Double>();
			for (int j=start;j<end;j++) {	
				innerFinal.add(newData.get(j).get(i));
			}
			finalData.set(c++,innerFinal);
		}
	}

	public static ArrayList<Integer> calculateArray(ArrayList<Integer> a){
		ArrayList<Integer> newSum = new ArrayList<Integer>();
		newSum.add(0);
		for (int i=1;i<a.size()-1;i++) {
			int temp = a.get(i);
			newSum.add(newSum.get(newSum.size()-1) + finalData.get(temp).size());
		}
		return newSum;
	}

	public static void computeDeltaW(int attributes){
		double temp;
		for (int i=0;i<newData.size();i++) {
			if (i>attributes) {
				if (biasFlag.get(i-1)==1) {
					for (int j=0;j<newData.get(i-1).size();j++) {
						if (deltaValues.size()!=0) {
							deltaValues.remove(0);
						}
					}
				}
			}
			for (int j=0;j<newData.get(i).size();j++) {
				temp = rate * xValues.get(i) * deltaValues.get(j);
				temp += newData.get(i).get(j);
				newData.get(i).set(j,temp);
				temp=0;
			}
		}
	}

	public static void reverseDelta(){
		Collections.reverse(deltaValues);
	}

	public static void computeDelta(int attributes){
		deltaValues = new ArrayList<Double>();
		int xposition = xValues.size()-1;
		double x = xValues.get(xposition);
		classValue = 1/(1+Math.exp(-classValue));
		deltaValues.add(x*(1-x)*(classValue-x));
		xposition--;
		int start=0;
		for (int i=newData.size()-1;i>attributes;i--) {
			int cc = 1;
			xposition--;
			while(biasFlag.get(xposition)!=1.0){
				cc++;
				xposition--;
			}
			xposition += cc;
			if (biasFlag.get(xposition)==1.0) {
				xposition--;
			}
			for (int j = 0 ; j < cc-1 ; j++) {
		 		double sum = 0;
		 		for ( int l=start;l<(start+newData.get(xposition).size());l++) {
		 			sum += newData.get(xposition).get(l-start) * deltaValues.get(l); 
		 		}
		 		double temp = xValues.get(xposition) * (1-xValues.get(xposition)) * sum;
		 		deltaValues.add(temp);
				xposition--;
				i--;
			}
			start+=newData.get(xposition+1).size();
		}
	}

	public static void transpose(int start, int end){
		int temp = finalData.get(start).size();
		for (int i=0;i<temp;i++ ) {
			ArrayList<Double> innerNewData = new ArrayList<Double>();
			for (int j=start;j<end;j++) {	
				innerNewData.add(finalData.get(j).get(i));
			}
			newData.add(innerNewData);
		}
	}

	public static void computeX(int start, int end, int flag, int attributes){
		if (flag==0) {
			int size = xValues.size();
			for (int i=start;i<end;i++) {
				double total = 0;
				ArrayList<Double> temp = finalData.get(i);
				for (int j=xValueCount;j<size;j++ ) {
					total+=temp.get(j-xValueCount)*xValues.get(j);
				}
				total = 1/(1+Math.exp(-total));
				xValues.add(total);
			}

			xValueCount=size;
			xValues.add(1.0);
		}
		else {
			double total = 0;
			for (int i=start;i<end;i++) {
				ArrayList<Double> temp = finalData.get(i);
				for (int j=xValueCount;j<xValueCount+finalData.get(i).size();j++ ) {
					total+=temp.get(j-xValueCount)*xValues.get(j);
				}
				total = 1/(1+Math.exp(-total));
				if (biasFlag.get(xp)==1.0) {
					xp++;
				}
				xValues.set(xp++,total);
			}
			xValueCount += finalData.get(start).size();
		}	
	}

	public static double calculateRandom(){
		//DecimalFormat numberFormat = new DecimalFormat("#.##");
		// return Double.parseDouble(numberFormat.format(Math.random()*2-1));
		return Math.random()*2-1;
	}

	public static void printData(){
		for (int i=0;i<newSumValues.size()-1;i++ ) {
			System.out.println("Layer "+i);
			int temp = newSumValues.get(i);
			int temp2 = newSumValues.get(i+1);
			int position = 1;
			for (int j=temp;j<temp2;j++) {
				if (j == temp2-1) {
					System.out.print("Bias    weight: ");
				}
				else {
					System.out.print("Neuron "+position+" weights: ");
				}
				for (int k=0;k<newData.get(j).size();k++) {
					System.out.print((k<newData.get(j).size()-1)? newData.get(j).get(k)+",":newData.get(j).get(k));
				}
				position++;
				System.out.println();
			}
		}
	}
}

class testing{
	public static ArrayList<ArrayList<Double>> finalData = new ArrayList<ArrayList<Double>>();
	public static ArrayList<Double> xValues;
	public static int xValueCount = 0;
	public static void traverse(ArrayList<ArrayList<Double>> storage, ArrayList<ArrayList<Double>> testFinalData, int attributes, ArrayList<Integer> a, ArrayList<Integer> sumValues, int train_size) throws IOException{
		finalData = testFinalData;
		double classValue;
    	double error = 0;
    	int counter = 0;
    	ArrayList<Double> line = new ArrayList<Double>();
    	while (counter<train_size) {
    		line = storage.get(counter);
    		classValue = line.get(line.size()-1);
	    	xValues = new ArrayList<Double>(); 
    		for (int i=0;i<line.size();i++) {
    			xValues.add(line.get(i));
    		}
    		xValues.remove(xValues.size()-1);
    		xValues.add(1.0);
    		a.set(1,0);
    		for (int i=1;i<a.size()-1;i++) {
	    		computeX(sumValues.get(i),sumValues.get(i+1));
	    	}
	    	xValues.remove(xValues.size()-1);
	    	xValueCount=0;
	    	classValue = 1/(1+Math.exp(-classValue));
	    	double actual = classValue;
	    	double predicted = xValues.get(xValues.size()-1);
	    	double meanSquare = Math.pow((actual-predicted),2);
	    	error+=meanSquare;
	    	counter++;
    	}
    	error /= (2*counter);
    	System.out.println("Total training error: "+error);
    	error = 0;
    	int train_stop = counter;
    	while(counter<storage.size()){
    		line = storage.get(counter);
    		classValue = line.get(line.size()-1);
    		xValues = new ArrayList<Double>(); 
    		for (int i=0;i<line.size();i++) {
    			xValues.add(line.get(i));
    		}
    		xValues.remove(xValues.size()-1);
    		xValues.add(1.0);
    		a.set(1,0);
    		for (int i=1;i<a.size()-1;i++) {
	    		computeX(sumValues.get(i),sumValues.get(i+1));
	    	}
	    	xValues.remove(xValues.size()-1);
	    	xValueCount=0;
	    	classValue = 1/(1+Math.exp(-classValue));
	    	double actual = classValue;
	    	double predicted = xValues.get(xValues.size()-1);
	    	double meanSquare = Math.pow((actual-predicted),2);
	    	error+=meanSquare;
	    	counter++;
    	}
    	counter = counter - train_stop;
    	error /= (2*counter);
    	System.out.println("Total test error: "+error);
	}
	public static void computeX(int start, int end){
		int size = xValues.size();
		for (int i=start;i<end;i++) {
			double total = 0;
			ArrayList<Double> temp = finalData.get(i);
			for (int j=xValueCount;j<size;j++ ) {
				total+=temp.get(j-xValueCount)*xValues.get(j);
			}
			total = 1/(1+Math.exp(-total));
			xValues.add(total);
		}
		xValueCount=size;
		xValues.add(1.0);
	}
}

class backp2{
	public static void main(String[] args) throws IOException {
		Scanner sc = new Scanner(System.in);
		preprocessing pp = new preprocessing();
		pp.readData(args[0]);
		pp.convertData();
		pp.writeData(args[1]);
		System.out.println("Input Dataset, Training percentage, max iterations, number of hidden layers, Neurons in each layers");
		String fileName = sc.nextLine();
		int tp = sc.nextInt();
		int mi = sc.nextInt();
		int n = sc.nextInt();
		ArrayList<Integer> hiddenLayerCount = new ArrayList<Integer>();
		hiddenLayerCount.add(0);
		hiddenLayerCount.add(pp.integerFlag.size()-1);
		for (int i=0;i<n;i++) {
			hiddenLayerCount.add(sc.nextInt());
		}
		hiddenLayerCount.add(1);
		int tcount = tp*pp.numericData.size()/100;
		backp bp = new backp();
		bp.readData(fileName, hiddenLayerCount, tcount, pp.integerFlag.size()-1,mi);
		bp.printData();
		testing t = new testing();
		t.traverse(bp.storage, bp.finalData, pp.integerFlag.size()-1, hiddenLayerCount, bp.sumValues, tcount);
	}
}