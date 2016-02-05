import java.io.*;

public class WaterFlow {
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        File fin=null;
        FileInputStream fileInputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        
        File fout=null;
        FileOutputStream fileOutputStream = null;
        OutputStreamWriter outputStreamWriter = null;
        BufferedWriter bufferedWriter = null;
        
        try{
            //Read Input File
            if(true){
                fin=new File("inputFile.txt");
                fileInputStream=new FileInputStream(fin);
                inputStreamReader=new InputStreamReader(fileInputStream,"UTF-8");
                bufferedReader=new BufferedReader(inputStreamReader);

                //Create Output File
                fout=new File("output.txt");
                fileOutputStream=new FileOutputStream(fout);
                outputStreamWriter=new OutputStreamWriter(fileOutputStream);
                bufferedWriter=new BufferedWriter(outputStreamWriter);

                int testCases=Integer.parseInt(bufferedReader.readLine().trim());
                //For each testCase
                for(int tc=0;tc<testCases;tc++){
                    String algoName=bufferedReader.readLine().trim(); //Algo Name
                    String source=bufferedReader.readLine().trim(); //Source Node
                    String destinations[]=(bufferedReader.readLine().trim()).split(" "); //destination Node(s)
                    String middle[]=(bufferedReader.readLine().trim()).split(" "); //Middle Nodes

                    int totalNodes=destinations.length+middle.length+1;
                    String[] nodes=new String[totalNodes];
                    nodes[0]=source;

                    for(int s=0;s<middle.length;s++)
                        nodes[1+s]=middle[s].toString();
                    for(int s=0;s<destinations.length;s++)
                        nodes[1+middle.length+s]=destinations[s].toString();

                    //Inputing Pipes
                    int numberOfPipes=Integer.parseInt(bufferedReader.readLine().trim());
                    Pipe[] pipes=new Pipe[numberOfPipes];
                    for(int p=0;p<numberOfPipes;p++){
                        String pipeData[]=(bufferedReader.readLine().trim()).split(" ");
                        String start=pipeData[0];
                        String end=pipeData[1];
                        int length=Integer.parseInt(pipeData[2]);
                        int offPeriod=Integer.parseInt(pipeData[3]);
                        int[][] offPeriodTimes;
                        if(offPeriod!=0){
                            offPeriodTimes=new int[offPeriod][2];
                            for(int i=0;i<offPeriod;i++){
                                String tempStr[]=pipeData[4+i].split("-");
                                offPeriodTimes[i][0]=Integer.parseInt(tempStr[0]);
                                offPeriodTimes[i][1]=Integer.parseInt(tempStr[1]);
                            }
                        }
                        else{
                            offPeriodTimes=null;
                        }
                        pipes[p]=new Pipe(start,end,length,offPeriod,offPeriodTimes);
                    }

                    //Sorting Pipes alphabetically decresing order
                    sortPipes(pipes);

                    int startTime=Integer.parseInt(bufferedReader.readLine().trim());
                    if(algoName.equals("BFS")){
                        //call BFS
                        BFS bfs=new BFS(nodes, destinations,startTime,pipes);
                        String output=bfs.bfs();
                        bufferedWriter.write(output);
                        bufferedWriter.newLine();
                    }
                    else if(algoName.equals("DFS")){
                        //call DFS
                        DFS dfs=new DFS(nodes, destinations, startTime,pipes);
                        String output=dfs.dfs(0);
                        bufferedWriter.write(output);
                        bufferedWriter.newLine();
                    }
                    else{
                        //call UCS
                        UCS ucs=new UCS(nodes, destinations, startTime,pipes);
                        String output=ucs.ucs();
                        bufferedWriter.write(output);
                        bufferedWriter.newLine();
                    }
                    //Rreading empty line before each test case (Except for first test case)
                    String empty=bufferedReader.readLine();
                }
            }
            else{
                System.err.println("Invalid arguments count:" + args.length);
            }    
        }
        catch(Exception ex){
            System.out.println("Exception occured : "+ex.getMessage());
        }
        finally{
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
            
            bufferedWriter.close();
            outputStreamWriter.close();
            fileOutputStream.close();
        }
    }
    
    public static void sortPipes(Pipe[] temp){
        for(int i=0;i<temp.length;i++){
            int max=i;
            for(int j=i+1;j<temp.length;j++){
                if(more(temp[j],temp[max]))
                    max=j;
            }
            exch(temp,i,max);
        }
    }
    
    public static boolean more(Pipe a, Pipe b){
        if(a.to().compareTo(b.to())>0)
            return true;
        return false;
    }
    
    public static void exch(Pipe[] temp, int i, int max){
        Pipe tempPipe=temp[i];
        temp[i]=temp[max];
        temp[max]=tempPipe;
    }
}

