import java.sql.SQLOutput;
import java.util.*;
import javafx.util.*;

class Graph
{
    int vx;
    int edge;
    boolean mol;
    ArrayList<ArrayList<Integer>> adjMat=new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Pair<Integer,Integer>>> adjList=new ArrayList<ArrayList<Pair<Integer,Integer>>>();

    public Graph(int v,int e, boolean ml)
    {
        vx=v;
        edge=e;
        mol=ml;
        if(!mol)
        {
            for(int i=0;i<vx;i++)
            {
                ArrayList<Integer> al=new ArrayList<Integer>();
                for(int j=0;j<vx;j++)
                {
                    al.add(0);
                }
                adjMat.add(al);
            }
        }
        if(mol)
        {
            for(int i=0;i<vx;i++)
            {
                adjList.add(new ArrayList<Pair<Integer,Integer>>());
            }
        }
    }

    public void ud_addEdge(int s, int d, int w)
    {
        if(!mol)
        {
            adjMat.get(s).set(d,w);
            adjMat.get(d).set(s,w);
        }
        if(mol)
        {
            adjList.get(s).add(new Pair<>(d,w));
            adjList.get(d).add(new Pair<>(s,w));
        }
    }
    public void d_addEdge(int s,int d, int w)
    {
        if(!mol)
        {
            adjMat.get(s).set(d,w);
        }
        if(mol)
        {
            adjList.get(s).add(new Pair<>(d,w));
        }
    }
    public void show_graph()
    {
        if(!mol)
        {
            for (ArrayList<Integer> al : adjMat)
            {
                for(Integer val: al)
                {
                    System.out.print(val + " ");
                }
                System.out.println();
            }
        }
        if(mol)
        {
            int i=0;
            for (ArrayList<Pair<Integer,Integer>> ap: adjList)
            {

                System.out.print(i + ":" + " ");
                for(Pair<Integer,Integer> p: ap)
                {
                    System.out.print("(" + p.getKey() + "," + p.getValue() + ") ");
                }
                System.out.println();
                i++;
            }
        }
    }
    //bfs
    public ArrayList<Integer> bfs(int src)
    {
        ArrayList<Integer> node= new ArrayList<Integer>();
        ArrayList<Boolean> visited=new ArrayList<Boolean>(Collections.nCopies(vx,false));//initializes all the elements to false
        Queue<Integer> q=new LinkedList<Integer>();
        visited.set(src,true);
        q.add(src);
        while(!q.isEmpty())
        {
            int cur=q.remove();
            node.add(cur);
            if(!mol)
            {
                for(int i=0;i<vx;i++)
                {
                    int nbr=adjMat.get(cur).get(i);
                    if(nbr>0 && !visited.get(i))
                    {
                        visited.set(i,true);
                        q.add(i);
                    }
                }
            }
            if(mol)
            {
                for(int i=0;i<adjList.get(cur).size();i++)
                {
                    Pair<Integer,Integer> nbr=adjList.get(cur).get(i);
                    if(nbr.getValue()>0 && !visited.get(nbr.getKey()))
                    {
                        visited.set(nbr.getKey(),true);
                        q.add(nbr.getKey());
                    }
                }
            }
        }
        return node;
    }
    //dfs
    public ArrayList<Integer> dfs(int src)
    {
        ArrayList<Integer> node= new ArrayList<Integer>();
        ArrayList<Boolean> visited=new ArrayList<Boolean>(Collections.nCopies(vx,false));//initializes all the elements to false
        Stack<Integer> st=new Stack<Integer>();
        visited.set(src,true);
        st.add(src);
        while(!st.isEmpty())
        {
            int cur=st.pop();
            node.add(cur);
            if(!mol)
            {
                for(int i=0;i<vx;i++)
                {
                    int nbr=adjMat.get(cur).get(i);
                    if(nbr>0 && !visited.get(i))
                    {
                        visited.set(i,true);
                        st.push(i);
                    }
                }
            }
            if(mol)
            {
                for(int i=0;i<adjList.get(cur).size();i++)
                {
                    Pair<Integer,Integer> nbr=adjList.get(cur).get(i);
                    if(nbr.getValue()>0 && !visited.get(nbr.getKey()))
                    {
                        visited.set(nbr.getKey(),true);
                        st.push(nbr.getKey());
                    }
                }
            }
        }
        return node;
    }
    //dijkstra
    //bellmanford
    //floydwarshall
    //johnson
    //kruskal

}



public class Main {
    public static void main(String[] args) {
        Graph g=new Graph(5,6,false);
        g.d_addEdge(0,1,1);
        g.d_addEdge(1,2,3);
        g.d_addEdge(2,3,5);
        g.d_addEdge(0,3,7);
        g.d_addEdge(0,4,8);
        g.d_addEdge(4,2,3);
        g.show_graph();
        ArrayList<Integer> al1=g.bfs(0);
        ArrayList<Integer> al2=g.dfs(0);
        System.out.print("BFS: ");
        for (Integer integer : al1) {
            System.out.print(integer + " ");
        }
        System.out.println();
        System.out.print("DFS: ");
        for (Integer integer : al2) {
            System.out.print(integer + " ");
        }
    }
}