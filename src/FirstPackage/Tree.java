package FirstPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Tree {
	private List<Tree> children = new ArrayList<>();
	private List<Tree> parent_child = new ArrayList<>();
	private final Tree parent;
	private static int matrix_size;
	private ArrayList<ArrayList<String>> id;
	private int score;
	static String player_turn;
	private static int depth;
	private static int count;
	static int level = 0;
	private static  ArrayList<ArrayList<String>> board_points = new  ArrayList<ArrayList<String>>(); 


	public Tree(Tree parent, int matrix_size, String player_turn, int depth, ArrayList<ArrayList<String>> board_points){
		this.parent = parent;
		this.matrix_size = matrix_size;
		this.player_turn = player_turn;
		this.depth = depth;
		this.board_points = clone_board_value(board_points);
	}


	public ArrayList<ArrayList<String>> getId(){
		return id;
	}

	public int getScores(){
		return score;
	}

	public void setId(ArrayList<ArrayList<String>> id){
		this.id = id;
	}

	public void setScores(int score){
		this.score = score;
	}

	public List<Tree> getChildren(){
		return children;
	}

	public Tree getParent(){
		return parent;
	}

	public void setParent(Tree parent_child){
		this.parent_child = (List<Tree>) parent_child;
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
		Tree node = new Tree(parent, matrix_size, player_turn, depth, board_points);
		node.setId(id);
		parent.getChildren().add(node);
		return node;
	}

	public static Tree create_tree(Tree tree_root, ArrayList<ArrayList<String>> board_value, int count){
		ArrayList<ArrayList<String>> childnodeValue ;
		//		HashMap<Integer, String> hashmap = new HashMap<Integer, String>();
		//		Map<String, ArrayList<ArrayList<String>>> map= new HashMap();
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
	public static Tree addChild_points(Tree parent, Integer score) {
		Tree node = new Tree(parent, matrix_size, player_turn, depth, board_points);
		node.setScores(score);
		parent.getChildren().add(node);
		return node;
	}

	public static String minimax(String check, int level){
		if(level%2 == 0){ 
			check = opposite_turn();
		} else{
			check = same_turn();
		}
		return check;
	}
	public static Tree create_points(Tree tree_score, Tree tree_root, ArrayList<ArrayList<String>> board_value,  int util_value){
		int childnodeValue = 0 ;
		ArrayList<Integer> utility_value = new ArrayList<>();
		int temp=0, max = 0;
		Tree child;
		boolean flag = false ;
		int i= 0;

		level +=1;
		Tree childNode = null;

		for ( Tree each : tree_root.getChildren() ){
			childnodeValue = calculate_utility(board_points, each.getId());
			childNode = addChild_points(tree_score, childnodeValue);
			create_points(childNode, each, board_value,childnodeValue);
			level -=1;
			if (childNode.getChildren().size() == each.getChildren().size() ){
				for (Tree each_score: tree_score.getChildren()){
					utility_value.add(i,each_score.getScores());
					i++;
					flag = true;
				}
				if (flag == true){
					Collections.sort(utility_value);
					if(level%2 == 0){ 
						childnodeValue = utility_value.get(utility_value.size()-1);
					} else{
						childnodeValue = utility_value.get(0);
					}

				}
			}
			tree_score.setScores(childnodeValue);
		}
		return tree_score;	
	}
	//	
	public static void play_board(Tree tree_root, Tree tree_root_points){
		int i = 0;
		List<Tree> temp_child = tree_root_points.getChildren();

		for(Tree each : tree_root_points.getChildren()){
			if (each.getScores() == tree_root_points.getScores()){
				print_move(tree_root.getChildren().get(i).getId());
			}
			i++;
		}
	}	
	
	public static void print_move(ArrayList<ArrayList<String>> next_move){
		System.out.println("You next move: ");
		for(int i=0;i<matrix_size;i++){
			for(int j=0;j<matrix_size;j++){
				System.out.print(next_move.get(i).get(j));
			}
			System.out.println();
		}
		
	}
	
	public static int calculate_utility(ArrayList<ArrayList<String>> board_points, ArrayList<ArrayList<String>> board_turn ){
		int utility_value = 0;
		String a = " "; 
		int value_x = 0, value_o = 0 ;
		for(int i =0;i<matrix_size ; i++){
			for(int j =0;j<matrix_size ; j++){
				if (board_turn.get(i).get(j).equals("X")){
					value_x += Integer.parseInt(board_points.get(i).get(j));
				}
				if (board_turn.get(i).get(j).equals("O")){
					value_o += Integer.parseInt(board_points.get(i).get(j));
				}
				if (player_turn.equals("O")){
					utility_value = value_o - value_x;
				}else{
					utility_value = value_x - value_o ;
				}
			}
		}
		return utility_value;

	}
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
