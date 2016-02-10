package spell;

public class DemoTrie implements ITrie {

	Node root;
	int nodecount;
	int wordcount;
	StringBuilder word, wordList;
	
	public DemoTrie(){
		root=null;
		nodecount=0;
		wordcount=0;
	}
	
	@Override
	public void add(String word) {
		if(root==null){
			root = new Node();
		}
		root.add(word.toLowerCase());
	}
	
	@Override
	public INode find(String word) {
		if(word=="")
			return null;
		if(root==null)
			return null;
		else
			return root.find(word);
	}
	
	@Override
	public int getWordCount() {
		return wordcount;
	}
	
	@Override
	public int getNodeCount() {
		return nodecount;
	}
	
	@Override
	public String toString(){
		if(root!=null) {
			
			word = new StringBuilder();
			wordList = new StringBuilder();
			wordList.append(root.toString());
			
			return wordList.toString();
		}
		
		return "";
	}
	
	@Override
	public int hashCode() {
		final int prime = 13;
		int result = 3;
		result = prime * result + nodecount*5;
		result = prime * result;
		for(int x=0; x<26; x++){
			if(root.children[x]!=null)
				result = x*5 + result;
		}
		result = prime * result + wordcount - 7;
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DemoTrie other = (DemoTrie) obj;
		if (nodecount != other.nodecount)
			return false;
		if (wordcount != other.wordcount)
			return false;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		
		
		//traverse recursively through all nodes, return false if not a match
		
		return true;
		
	}

	public class Node implements INode {

		public Node[] children;
		int frequency;
		
		public Node(){
			nodecount++;
			children = new Node[26];
			frequency = 0;
		}
		
		public void add(String s){
			int l = s.length();	
			
			if(l>1){	
				int index = s.charAt(0) - 'a';
				if(children[index]==null)
					children[index] = new Node();
				String substr = s.substring(1);	
				children[index].add(substr);
			}
			else if(l==1){
				int index = s.charAt(0) - 'a';
				if(children[index]==null)
					children[index] = new Node();
				children[index].add("");
			}
			else {
				if(frequency==0)
					wordcount++;
				frequency++;
			}
		}

		@Override
		public int getValue() {
			return frequency;
		}
		
		public INode find(String word) {
			if(word.length()==1){
				if(children[word.charAt(0)-'a']==null)
					return null;
				if(children[word.charAt(0)-'a'].getValue()>0)
					return children[word.charAt(0)-'a'];
				else
					return null;
			}
			else if(word.length()>1) {
				if(children[word.charAt(0)-'a']==null) 
					return null;
				return children[word.charAt(0)-'a'].find(word.substring(1));
			}
			else
				return null;
		}
		
		@Override
		public String toString(){

			
			for(int x = 0; x < 26; x++) {
				
				if(children[x]!=null){
					word.append(Character.toString((char) (97+x)));	
					if(children[x].getValue()>0){
						wordList.append(word);
						wordList.append('\n');
					}
					word.append(children[x].toString());
					word.deleteCharAt(word.length()-1);
				}
					
			}
			
			return "";
		}
		
		public boolean equals(Node other){
			if(frequency!=other.frequency)
				return false;
			for(int x=0; x<26; x++){
				if(children[x]==null && other.children[x]!=null)
					return false;
				if(children[x]!=null && other.children[x]==null)
					return false;	
			}
			for(int x=0; x<26; x++)
				if(children[x]!=null)
					if(!children[x].equals(other.children[x]))
						return false;
			
			
			
			return true;
		}
	}
		
			
}
	


