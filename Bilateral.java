import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Bilateral {
	public static void main(String[] args) throws IOException{
		Graph graph = new Graph();
		BufferedReader stdin = new BufferedReader(new InputStreamReader(
				System.in));
		String input = stdin.readLine();
		int num = Integer.parseInt(input);
		for(int i=0; i<num; i++){
			String[] strIds = stdin.readLine().split(" ");
			graph.add(Integer.parseInt(strIds[0]), Integer.parseInt(strIds[1]));
		}
		graph.hungrian();
		//graph.printMaxMatch();
		graph.konig();
		graph.printResult();
	}
}

class Graph {
	private HashMap<Integer, ArrayList<Integer>> hashMap;
	private int[] matchedLeftPartner;
	private boolean[] checked;
	private boolean[] matchedVertexes;
	private ArrayList<Integer> alternatePath;
	private static Integer BOUND = 2000;
	private static Integer FRIEND = 1009;
	private Set<Integer> left;
	private Set<Integer> right;

	public Graph() {
		this.checked = new boolean[2000];
		this.matchedLeftPartner = new int[1000];
		this.matchedVertexes = new boolean[2000];
		this.hashMap = new HashMap<Integer, ArrayList<Integer>>();
		for(int i=0; i<this.matchedLeftPartner.length; i++){
			this.matchedLeftPartner[i]=0;
		}
		for (int i=0; i<2000; i++){
			this.checked[i] = false;
			this.matchedVertexes[i] = false;
		}
		this.alternatePath = new ArrayList<Integer>();
		this.left = new HashSet<Integer>();
		this.right = new HashSet<Integer>();
	}
	
	private boolean isChecked(int id){
		return this.checked[id-1000];
	}
	
	private void setChecked(int id){
		this.checked[id-1000] = true;
	}
	
	private void resetChecked(){
		for(int i = 0;i<this.checked.length;i++){
			this.checked[i] = false;
		}
	}
	
	private void setMatched(int lid, int rid){
		this.matchedLeftPartner[rid-BOUND] = lid;
		this.matchedVertexes[lid-1000] = true;
		this.matchedVertexes[rid-1000] = true;
	}
	
	private int getMatchedLeftPartner(int id){
		return this.matchedLeftPartner[id-BOUND];
	}

	public boolean isMatched(int id) {
		return this.matchedVertexes[id-1000];
	}
	
	public boolean isMatched(int lid, int rid) {
		return this.matchedLeftPartner[rid - BOUND] == lid;
	}

	public void add(int lid, int rid) {
		this.left.add(lid);
		this.right.add(rid);
		if(this.hashMap.get(lid)==null){
			this.hashMap.put(lid, new ArrayList<Integer>());
		}
		this.hashMap.get(lid).add(rid);
	}
	
	public void printMaxMatch(){
		for(int rid : this.right){
			if(this.isMatched(rid)){
				System.out.println(this.getMatchedLeftPartner(rid)+", "+rid);
			}
		}
	}

	public void hungrian() {
		if (this.hashMap.get(FRIEND)!=null) {
			this.resetChecked();
			hasAugmentPath(FRIEND);
		}
		for (int lid : this.left) {
			if (!this.isMatched(lid)) {
				this.resetChecked();
				this.hasAugmentPath(lid);
			}
		}
	}
	
	public boolean hasAugmentPath(int id) {
		this.setChecked(id);
		if (id < BOUND) {
			for (int rid : this.hashMap.get(id)) {
				if (!this.isChecked(rid) && !this.isMatched(id, rid)
						&& (!this.isMatched(rid) || this.hasAugmentPath(rid))) {
					this.setMatched(id, rid);
					return true;
				}
			}
			return false;
		} else {
			int lid = this.getMatchedLeftPartner(id);
			return this.isMatched(id) && !this.isChecked(lid) && this.hasAugmentPath(lid);
		}
	}
	
	public void konig(){ 
		this.resetChecked();
		for(int lid : this.left){
			if(!this.isMatched(lid)){
				findAlternatePath(lid);
			}
		}
		this.left.removeAll(this.alternatePath);
		this.right.retainAll(this.alternatePath);
		this.left.addAll(this.right);
	}
	
	private void findAlternatePath(int id) {
		this.alternatePath.add(id);
		this.setChecked(id);
		if (id < BOUND) {
			for (int rid : this.hashMap.get(id)) {
				if (!this.isChecked(rid) && !this.isMatched(id, rid)) {
					findAlternatePath(rid);
				}
			}
		} else {
			int lid = this.getMatchedLeftPartner(id);
			if (!this.isChecked(lid)) {
				findAlternatePath(lid);
			}
		}
	}
	
	public void printResult(){
		System.out.println(this.left.size());
		for(int re : this.left){
			System.out.println(re);
		}
	}
}
