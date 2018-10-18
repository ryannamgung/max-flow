import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.io.*;

public class FordFulkerson
{
  /* Returns true if there is a path from source 's' to sink
    't' in residual graph. Also fills parent[] to store the
    path */
  public static boolean bfs(int rGraph[][], int s, int t, int parent[], int V)
  {
      // Create a visited array and mark all vertices as not
      // visited
      boolean visited[] = new boolean[V];
      //boolean pathFound = false;
      for(int i=0; i<V; i++)
      {
          visited[i]=false;
      }

      // Create a queue, enqueue source vertex and mark
      // source vertex as visited
      LinkedList<Integer> queue = new LinkedList<Integer>();
      queue.add(s);
      visited[s] = true;
      parent[s]=-1;

      // Standard BFS Loop
      while (queue.size()!=0)
      {
          int u = queue.poll();

          for (int v=0; v<V; v++)
          {
              if (visited[v]==false && rGraph[u][v] > 0)
              {
                  queue.add(v);
                  parent[v] = u;
                  visited[v] = true;
              }
          }
      }

      // If we reached sink in BFS starting from source, then
      // return true, else false
      return (visited[t] == true);
  }

  // Returns tne maximum flow from s to t in the given graph
  public static int fordFulkerson(int graph[][], int s, int t, int V)
  {
      int u, v;

      // Create a residual graph and fill the residual graph
      // with given capacities in the original graph as
      // residual capacities in residual graph

      // Residual graph where rGraph[i][j] indicates
      // residual capacity of edge from i to j (if there
      // is an edge. If rGraph[i][j] is 0, then there is
      // not)
      int rGraph[][] = new int[V][V];

      for (u = 0; u < V; u++)
      {
        for (v = 0; v < V; v++)
        {
          rGraph[u][v] = graph[u][v];

        }
      }

      // This array is filled by BFS and to store path
      int parent[] = new int[V];

      int max_flow = 0;  // There is no flow initially

      // Augment the flow while tere is path from source
      // to sink
      while (bfs(rGraph, s, t, parent, V))
      {
          // Find minimum residual capacity of the edhes
          // along the path filled by BFS. Or we can say
          // find the maximum flow through the path found.
          int path_flow = Integer.MAX_VALUE;
          for (v=t; v!=s; v=parent[v])
          {
              u = parent[v];
              path_flow = Math.min(path_flow, rGraph[u][v]);
          }

          // update residual capacities of the edges and
          // reverse edges along the path
          for (v=t; v != s; v=parent[v])
          {
              u = parent[v];
              rGraph[u][v] -= path_flow;
              rGraph[v][u] += path_flow;
          }

          // Add path flow to overall flow
          max_flow += path_flow;
      }
      // Return the overall flow
      return max_flow;
  }

    public static void main(String args[])
    {
        int[][] graph;
        int numberOfNodes;
        int source;
        int sink;
        int maxFlow;
        int L = 0;
        int M = 0;
        int C = 0;
        int G = 0;

        Scanner input = new Scanner(System.in);

        L = input.nextInt();
        M = input.nextInt();
        C = input.nextInt();
        G = input.nextInt();

        numberOfNodes = L + M + C + G + 2;
        graph = new int[numberOfNodes][numberOfNodes];

        System.out.println(numberOfNodes);

        for(int i = 0; i < L - 1; i++)
        {
          graph[0][i+1] = 1;
        }

        // Reads in laptop shells
        for(int i = 0; i < L - 1; i++)
        {
          int laptopShellNum = input.nextInt();
          //System.out.println("laptopShellNum: " + laptopShellNum);
          int numberOfConnections = input.nextInt();
          //System.out.println("numberOfConnections: " + numberOfConnections);
          for(int j = 0; j < numberOfConnections; j++)
          {
            int motherBoardNum = input.nextInt();
            graph[laptopShellNum][motherBoardNum+L] = 1;
          }
        }

        // Reads in Motherboards
        for(int i = 0; i < M - 1; i++)
        {
          int currentMB = input.nextInt();
          //System.out.println("motherboard number: " + currentMB);
          int numbConnect = input.nextInt();
          //System.out.println("numconnect:"  + numbConnect);
          for(int j = 0; j < numbConnect - 1; j++)
          {
            int cpuNum = input.nextInt();
            graph[currentMB+L][cpuNum+L+M] = 1;
          }
        }

        // Reads in CPUs
        for(int i = 0; i < C - 1; i++)
        {
          int currentCPU = input.nextInt();
          //System.out.println("cpu number: " + currentCPU);
          int numConnect = input.nextInt();
          //System.out.println("numconnect:"  + numConnect);
          for(int j = 0; j < numConnect - 1; j++)
          {
            int gpuNum = input.nextInt();
            graph[currentCPU+L+M][gpuNum+L+M+C] = 1;
          }
        }

        for(int i = L+M+C+1; i < L+M+C+G+1; i++)
        {
          graph[i][numberOfNodes-1] = 1;
        }

        for(int i = 0; i < numberOfNodes - 1; i++)
        {
          for(int j = 0; j < numberOfNodes - 1; j++)
          {
            System.out.print(graph[i][j]);
          }
          System.out.println("");
        }

        source = 0; //graph[0][0]

        sink = numberOfNodes-1; //graph[numberOfNodes+1][numberOfNodes+1]

        maxFlow = fordFulkerson(graph, source, sink, numberOfNodes);
        System.out.println("The Max Flow is " + maxFlow);
   }
}
