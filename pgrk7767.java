//JAITLY AKSHAY cs610 7767 prp

import java.io.*;
import java.util.Iterator;
import java.text.DecimalFormat;
import java.util.List;

public class pgrk7767 {

    public static void main(String[] args) throws NumberFormatException, IOException {
        
        int Numiter;
        final double d = 0.85;
        final double errorrate = 0.00001;
        boolean isLess = true;
        DecimalFormat df = new DecimalFormat("0.0000000");//Setting the values
        
        int iterations = Integer.parseInt(args[0]);
        double initialValue = Integer.parseInt(args[1]);
        String filename = args[2];//Reading initial arguments

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String ln = br.readLine();//Buffer reading the file

        int N = Integer.parseInt(ln.split(" ")[0]);
        int E = Integer.parseInt(ln.split(" ")[1]);//Reading Vertices and Edges

        AdjSet7767 NodesNum = new AdjSet7767(N);//Adjacency Set is created

        int Node1, Node2;
        for(int i = 0; i < E; i++){
            ln = br.readLine();
            Node1 = Integer.parseInt(ln.split(" ")[0]);
            Node2 = Integer.parseInt(ln.split(" ")[1]);
            NodesNum.addEdge(Node1,Node2);//Adding nodes from file
        }

        if(iterations <1){
            Numiter = 1000000000;
        }
        else{
            Numiter = iterations;//We assign number of iterations
        }

        double [] pageRank = new double[N];
        double [] PageRank_sum = new double[N];
        double [] PageRank_tmp = new double[N];
        double [] errorrate_diff = new double[N];//Array Creation step for PageRank

        if(initialValue == 0){
            initialValue = 0.0;
        }
        else if(initialValue == 1){
            initialValue = 1.0;
        }
        else if(initialValue == -1){
            initialValue = 1.0/N;
        }
        else if(initialValue == -2){
            initialValue = 1.0/(Math.sqrt(N));//Initializing initialValue
        }

    
        for (int i = 0; i <N; i++)
        {
            pageRank[i] = initialValue;
            PageRank_tmp[i] = initialValue;
            errorrate_diff[i] = 0;//We initialize Arrays here
        }

        
        System.out.print("Vectors must be initialized to "+df.format(initialValue)+"\n");

        if(N < 10){
            System.out.print("Base : 0 :");
            for(int i = 0; i < N; i++){
                System.out.print("PageRank["+i+"] "+df.format(pageRank[i])+" ");//Base case Value
            }
        }
        else{
            System.out.print("Base : 0 "+"\n");
            for(int i = 0; i < N; i++){
                System.out.print("PageRank["+i+"] "+df.format(pageRank[i])+" \n");//Base case Value
            }
        }
        
        
        for(int y = 0; y < Numiter; y++)
        {
            for(int i = 0; i < pageRank.length; i++)
            {
                PageRank_sum[i] = 0;
                for(int x = 0;x< pageRank.length; x++)
                {
                 List<Integer> str = NodesNum.getVertices_Adj(x);
                    Iterator<Integer> itr = str.iterator();
                    while(itr.hasNext())
                    {
                        if(i == itr.next())
                        {
                            PageRank_sum[i] = PageRank_sum[i] + pageRank[x]/NodesNum.getVertices_Adj(x).size();//Steps of SumPageRank
                        }
                    }
                }

            }

            
            for(int i = 0; i<pageRank.length; i++)
            {
                pageRank[i] = ((1 - d)/N) + d*(PageRank_sum[i]);//Pagerank formula
            }
            
            
            if(N < 10){

                System.out.print("\nItr : "+(y+1)+" :");
                for(int i = 0; i < N; i++){
                    System.out.print("PageRank["+i+"] "+df.format(pageRank[i])+" ");//Generate output if vertices are less than 10

                }

            }
            else{
                System.out.print("\nItr : "+(y+1)+"\n");
                for(int i = 0; i < N; i++){
                    System.out.print("PageRank["+i+"] "+df.format(pageRank[i])+"\n");
                }

            }

            
            for(int i = 0; i < errorrate_diff.length; i++)
            {
                errorrate_diff[i] = PageRank_tmp[i] - pageRank[i];//Calculating difference of the iterations
                
                if(errorrate_diff[i] < errorrate){
                    isLess = true;
                }
                else{
                    isLess = false;
                    break;//Verifying if the difference is true or not
                }

            }

            for(int i = 0; i<pageRank.length; i++)
            {
                PageRank_tmp[i] = pageRank[i];//Assign TempPagerank Value for next iteration calculation

            }

            
            if(isLess == true){
                System.out.print("\n");
                break;//If previous PageRank value is less than current value then break out of loop
            }
        }

      }
}
