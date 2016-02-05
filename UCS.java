import java.util.Arrays;
import java.util.TreeMap;

class UCS {
    
    private boolean marked[];
    private int[] distTo;
    private int[] edgeTo;
    private int goal=-1;
    
    int source=0;
    String[] destinations;
    String[] nodes;
    int startTime;
    Bag<Pipe>[] adj;
    
    public UCS(String[] nodes, String[] destinations, int startTime, Pipe[] pipes){
        marked=new boolean[nodes.length];
        edgeTo=new int[nodes.length];
        distTo=new int[nodes.length];
        
        this.nodes=new String[nodes.length];
        for(int i=0;i<nodes.length;i++){
            this.nodes[i]=nodes[i];
        }
        
        this.destinations=new String[destinations.length];
        for(int i=0;i<destinations.length;i++){
            this.destinations[i]=destinations[i];
        }
        
        this.startTime=startTime;
        
        this.adj=(Bag<Pipe>[])new Bag[nodes.length];
        for(int i=0;i<nodes.length;i++)
            this.adj[i]=new Bag<Pipe>();
        
        for(int p=0;p<pipes.length;p++){
            adj[(int)Arrays.asList(nodes).indexOf(pipes[p].from().toString())].add(pipes[p]);
        }
        
        for(int i=0;i<nodes.length;i++){
            distTo[i]=Integer.MAX_VALUE;
        }
    }
    
    public String ucs(){
        marked[source]=true;
        distTo[source]=startTime;
        edgeTo[source]=-1;
        goal=-1;
        boolean validNode=false;
        
        TreeMap<String,Integer> treemap=new TreeMap<String,Integer>();
        
        treemap.put(nodes[source],distTo[source]);
        TreeMap<String,Integer> sortedTreemap=SortByValue(treemap);
        sortedTreemap=SortByValue(treemap);
        while(sortedTreemap.size()>0){

            String key=sortedTreemap.pollFirstEntry().getKey();
            int current=(int)Arrays.asList(nodes).indexOf(key);
            treemap.remove(key);
            
            //Check if goal reached
            if(Arrays.asList(destinations).contains(nodes[current])){
                goal=current;
                break;
            }
            
            for(Pipe p: adj[current]){
                validNode=false;
                int[] availability=new int[24];
                int to=(int)Arrays.asList(nodes).indexOf(p.to().toString());
                if(p.offPeriod>0){
                    for(int i=0;i<p.offPeriodTimes.length;i++){
                        for(int j=(p.offPeriodTimes[i][0]%24);j<=(p.offPeriodTimes[i][1]%24);j++)
                            availability[j]=-1;
                    }
                    if(availability[distTo[current]%24]==0){
                        validNode=true;
                    }
                }
                else{
                    validNode=true;
                }
                
                if(validNode){
                    if(!marked[to]){
                        edgeTo[to]=current;
                        distTo[to]=distTo[current]+p.length();
                        marked[to]=true;

                        treemap.put(nodes[to],distTo[to]);
                        sortedTreemap=SortByValue(treemap);
                    }
                    else{
                        if(distTo[to]>(distTo[current]+p.length())){
                            edgeTo[to]=current;
                            distTo[to]=distTo[current]+p.length();
                            marked[to]=true;

                            treemap.remove(nodes[to]);
                            treemap.put(nodes[to],distTo[to]);
                            sortedTreemap=SortByValue(treemap);
                        }
                    }
                }
            }
            
        }
        
        if(goal==-1){
            return "None";
        }
        else{
            return nodes[goal].toString()+" "+(distTo[goal]%24);
        } 
    }
    
    public static TreeMap<String, Integer> SortByValue 
			(TreeMap<String, Integer> map) {
		ValueComparator vc =  new ValueComparator(map);
		TreeMap<String,Integer> sortedMap = new TreeMap<String,Integer>(vc);
		sortedMap.putAll(map);
		return sortedMap;
	}
}