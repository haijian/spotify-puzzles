import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Lottery {

	public static void main(String[] args) throws IOException {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String input = stdin.readLine();
		String[] params = input.split(" ");

		int m = Integer.parseInt(params[0]);
		int n = Integer.parseInt(params[1]);
		int t = Integer.parseInt(params[2]);
		int p = Integer.parseInt(params[3]);

		int winnersNeeded = p / t + (p % t > 0 ? 1 : 0);
		
		double win = 0;
		double lose = 0;
		double total = choose(m, n);
		if(winnersNeeded < p - winnersNeeded){
			for(int i=0;i<winnersNeeded;i++){
				lose += choose(p, i)*choose(m-p, n-i);
			}
			System.out.printf("%.10f", 1-lose/total);
		}else{
			for(int i=winnersNeeded;i<=p;i++){
				win += choose(p, i)*choose(m-p, n-i);
			}
			System.out.printf("%.10f", win/total);
		}
	}
	
	private static double choose(int n, int m) {
		if (m<0 || m > n) {
			return 0;
		}
		if(m < n-m){
			m = n-m;
		}
		double result = 1;
		for(int i=n, j=1; i>m; i--,j++){
			result = result * i / j;
		}
		return result;
	}
}
