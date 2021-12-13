import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class ProblemaA {

	public static void main(String args[]) throws IOException
	{		
		try ( 
				InputStreamReader is= new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(is);
				) {

			String line = br.readLine();	
			ArrayList<Double> resultados = new ArrayList<Double>();			
			while(line!=null && line.length()>0) 
			{

				String [] dataStr = line.split("\\s+");
				if(dataStr.length == 1 && dataStr[0].equals("0") ) {
					break;					
				}
				resultados= cp(dataStr,resultados);

				line=br.readLine();
			}

			for(int i=0; i<resultados.size(); i++) {

				System.out.println(resultados.get(i));

			}			

		}          




	}


	public static ArrayList<Double> cp(String[] division,ArrayList<Double> resultados) {

		int n = Integer.parseInt(division[0]);	

		double a = Double.parseDouble(division[1]);	

		double b = Double.parseDouble(division[2]);	

		double c = Double.parseDouble(division[3]);	

		double d = Double.parseDouble(division[4]);	

		double[] r = new double[n+1];

		Double cp = 0.0000;

		r[0] = a;
		if(n>0) {
			r[1] = b;

			for(int i=0; i<(r.length-2);i++) {			
				r[i+2] = c*r[i] + d*r[i+1];			
			}	
		}

		for(int i=0; i<r.length ; i++) {			

			cp = cp + r[i]*r[n-i];

		}
		cp = Math.ceil(cp*10000.0)/10000.0;


		resultados.add(cp);		

		return resultados;
	}
}