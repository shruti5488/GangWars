package FirstPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree {
	private List<Tree> children = new ArrayList<>();
	private final Tree parent;
	private static int matrix_size;
	private ArrayList<ArrayList<String>> id;
	static String player_turn;
	private static int depth;
	private static int count;
	static int level = 0;
	
	public Tree(Tree parent, int matrix_size, String player_turn, int depth){
		this.parent = parent;
		this.matrix_size = matrix_size;
		this.player_turn = player_turn;
		this.depth = depth;
	}

	
	public ArrayList<ArrayList<String>> getId(){
		return id;
	}

	public void setId(ArrayList<ArrayList<String>> id){
		this.id = id;
	}

	public List<Tree> getChildren(){
		return children;
	}

	public Tree getParent(){
		return parent;
	}
	
	public static ArrayList<ArrayList<String>> clone_board_value(ArrayList<ArrayList<String>> board_value){
//		matrix_size = 2;
		ArrayList<ArrayList<String>> board_value_clone = new ArrayList<ArrayList<String>>();

		for (int i=0; i< matrix_size ; i++ ) {
			ArrayList<String> board_value_row = board_value.get(i);
			board_value_clone.add(i, new ArrayList<String>(board_value_row.size()));
			for (int j=0; j< matrix_size ; j++) {
				board_value_clone.get(i).add(j, board_value.get(i).get(j));
			}
		}
		return board_value_clone;
	}
	
	
//	public static HashMap<Integer, String> create_hashmap(ArrayList<ArrayList<String>> board_value){
//		HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
//		
//		ArrayList<String> board_value_row = new ArrayList<String>();
//		int k = 0;
//		for (int i=0; i< matrix_size ; i++ ) {
//			board_value_row = board_value.get(i);
//			for (int j=0; j< matrix_size ; j++) {
//				if (board_value_row.get(j).equals(".")){
//					hashmap.put(k, "Y");
//				} else if (board_value_row.get(j).equals("X")){
//					hashmap.put(k, "X");
//				} else if (board_value_row.get(j).equals("O")){
//					hashmap.put(k, "O");
//				}
//			k ++ ;	
//			}
//		}
//		return hashmap;
//	}
	
	public static String player_value(String check, int level){
		
		if(level%2 == 0){ 
			check = opposite_turn();
		} else{
			check = same_turn();
		}
		return check;
		
		
	}
	
	public static String opposite_turn (){
		String check;
		if (player_turn.equals("O")){
			check = "X";
		} else{
			check = "O";
		}
		return check;
	}
	public static String same_turn (){
		String check;
		if (player_turn.equals("O")){
			check = "O";
		} else{
			check = "X";
		}
		return check;
	}
	public static Tree addChild(Tree parent, ArrayList<ArrayList<String>> id) {
		Tree node = new Tree(parent, matrix_size, player_turn, depth);
		node.setId(id);
		parent.getChildren().add(node);
		return node;
	}
		
	public static Tree create_tree(Tree tree_root, ArrayList<ArrayList<String>> board_value, int count){
		ArrayList<ArrayList<String>> childnodeValue ;
		HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
		Map<String, ArrayList<ArrayList<String>>> map= new HashMap();
		String first_turn = "";
		
		level +=1;
		
		tree_root.setId(board_value);
		Tree childNode = null;
		boolean flag = false;
		
		if (depth == level-1){
			return tree_root;
		}
		else{
		for (int i=0; i<matrix_size; i++){
			for (int j = 0; j<matrix_size; j++){
				if(board_value.get(i).get(j).equals(".")){
					childnodeValue = clone_board_value(board_value);
					first_turn = player_value(first_turn, level);
					childnodeValue.get(i).set(j, first_turn);
					childNode = addChild(tree_root, childnodeValue);
					count = count_space(childnodeValue, matrix_size);
					create_tree(childNode, childnodeValue, count);
					level -=1;
				}
			}
		}
	}
		
		return tree_root;	
}
//	public static ArrayList<ArrayList<String>> update_board_key(ArrayList<ArrayList<String>> board_key){
//		
//		for (int i=0; i< matrix_size ; i++ ) {
//			if (board_key.get(i).contains("Y")){
//				for (int j=0; j< matrix_size ; j++) {
//					if (board_key.get(i).get(j).equals("Y")){
//						board_key.get(i).set(j, "N");
//					}
//				}
//			}
//		}
//		
//		return board_key;
//	}
//	
//	public static ArrayList<ArrayList<String>> board_key_removeN(ArrayList<ArrayList<String>> board_key){	
//		for (int i=0; i< matrix_size ; i++ ) {
//			if (board_key.get(i).contains("N")){
//				for (int j=0; j< matrix_size ; j++) {
//					if (board_key.get(i).get(j).equals("N")){
//						board_key.get(i).set(j, ".");
//					}
//				}
//			} 
//		}
//		return board_key;
//	}

//	public static Map<String,ArrayList<ArrayList<String>>> create_node(ArrayList<ArrayList<String>> board_value, ArrayList<ArrayList<String>> board_key){
//		boolean flag = false;
//		for (int i=0; i< matrix_size ; i++ ) {
//			if (board_key.get(i).contains(".")){
//				for (int j=0; j< matrix_size ; j++) {
//					if (board_key.get(i).get(j).equals(".")){
//						board_value.get(i).set(j, first_turn);
//						board_key.get(i).set(j, "Y");
//						flag = true;
//						break;
//					}
//				}
//			}
//			if (flag == true){
//				break;
//			}
//		}
//		Map<String, ArrayList<ArrayList<String>>> map= new HashMap();
//		map.put("value",board_value);
//		map.put("key",board_key);
//		return map;
//	}
	
	
//	public static int build_tree (Tree root, ArrayList<ArrayList<String>> board_value, ArrayList<ArrayList<String>> board_key){
//		ArrayList<ArrayList<String>> childnodeValue ;
//		
//		
//		
//
//	}
//	
	public static int count_space(ArrayList<ArrayList<String>> board_value, int matrix_size){
		int count = 0;
		for (int i = 0; i<matrix_size;i++){
			for (int j=0;j<matrix_size;j++){
				if (board_value.get(i).get(j).equals(".")) {
					count ++;
				}
			}
		}
		return count;
	}
	

}
