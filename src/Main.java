import java.util.*;
import javafx.util.*;

class Graph
{
    int vx;
    int edge;
    boolean mol=true;
    boolean negcyc;
    ArrayList<ArrayList<Integer>> adjMat=new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Pair<Integer,Integer>>> adjList=new ArrayList<ArrayList<Pair<Integer,Integer>>>();
    public Graph(int v,int e)
    {
        vx=v;
        edge=e;
        for(int i=0;i<vx;i++)
        {
            ArrayList<Integer> al=new ArrayList<Integer>();
            for(int j=0;j<vx;j++)
            {
                al.add(0);
            }
            adjMat.add(al);
        }
        for(int i=0;i<vx;i++)
        {
            adjList.add(new ArrayList<Pair<Integer,Integer>>());
        }
    }
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
        else
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
        else
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
        else
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
        else
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
            else
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
            else
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
    public ArrayList<Integer> dijkstra(int src)
    {
        ArrayList<Integer> dist=new ArrayList<>(Collections.nCopies(vx,Integer.MAX_VALUE));
        dist.set(src, 0);
        ArrayList<Boolean> vis=new ArrayList<>(Collections.nCopies(vx, false));

        for(int i=0;i<vx;i++)
        {
            int minDistNode = -1;
            for(int j=0;j<vx;j++)
            {
                if(!vis.get(j) && (minDistNode==-1 || dist.get(j) < dist.get(minDistNode)))
                {
                    minDistNode=j;
                }
            }
            if(dist.get(minDistNode)==Integer.MAX_VALUE){break;}
            vis.set(minDistNode, true);

            if(!mol)
            {
                for(int nbr=0;nbr<vx;nbr++)
                {
                    int weight=adjMat.get(minDistNode).get(nbr);
                    if(weight!=0 && dist.get(minDistNode)+weight < dist.get(nbr))
                    {
                        dist.set(nbr,dist.get(minDistNode)+weight);
                    }
                }
            }
            else
            {
                for(Pair<Integer,Integer> nbr: adjList.get(minDistNode))
                {
                    int nbrNode= nbr.getKey();
                    int weight= nbr.getValue();
                    if(dist.get(minDistNode)+weight < dist.get(nbrNode))
                    {
                        dist.set(nbrNode, dist.get(minDistNode)+weight);
                    }
                }
            }
        }
        return dist;
    }
    //dijkstra with priority queue
    public ArrayList<Integer> dijkstra_pq(int src)
    {
        ArrayList<Integer> dist=new ArrayList<Integer>(Collections.nCopies(vx,Integer.MAX_VALUE));
        dist.set(src,0);
        PriorityQueue<Pair<Integer,Integer>> pq= new PriorityQueue<>((a,b) -> a.getKey() - b.getKey());
        pq.add(new Pair<>(0,src));

        while(!pq.isEmpty())
        {
            Pair<Integer,Integer> cur=pq.poll();
            int curDist=cur.getKey();
            int curNode=cur.getValue();

            if(curDist > dist.get(curNode)){continue;}
            if(!mol)
            {
                for(int i=0;i<vx;i++)
                {
                    int weight=adjMat.get(curNode).get(i);
                    if(weight!=0)
                    {
                        int newDist=curDist+weight;
                        if(newDist < dist.get(i))
                        {
                            dist.set(i,newDist);
                            pq.add(new Pair<>(newDist,i));
                        }
                    }
                }
            }
            else
            {
                for(Pair<Integer,Integer> nbr : adjList.get(curNode))
                {
                    int nxtNode=nbr.getKey();
                    int weight=nbr.getValue();
                    int newDist=curDist+weight;

                    if(newDist<dist.get(nxtNode))
                    {
                        dist.set(nxtNode, newDist);
                        pq.add(new Pair<>(newDist,nxtNode));
                    }
                }
            }
        }
        return dist;
    }
    //bellman ford
    public ArrayList<Integer> bellman_ford(int src)
    {
        ArrayList<Integer> dist= new ArrayList<Integer>(Collections.nCopies(vx,Integer.MAX_VALUE));
        dist.set(src,0);
        ArrayList<Pair<Pair<Integer,Integer>,Integer>> edge_list= new ArrayList<Pair<Pair<Integer,Integer>,Integer>>();
        if(!mol)
        {
            for(int i=0;i<vx;i++)
            {
                for(int j=0;j<vx;j++)
                {
                    if(adjMat.get(i).get(j)!=0)
                    {
                        Pair<Pair<Integer,Integer>,Integer> e=new Pair<Pair<Integer,Integer>,Integer>(new Pair<>(i,j),adjMat.get(i).get(j));

                        edge_list.add(e);
                    }
                }
            }
        }
        else
        {
            for(int i=0;i<vx;i++)
            {
                for(var p:adjList.get(i))
                {
                    Pair<Pair<Integer,Integer>,Integer> e=new Pair<Pair<Integer,Integer>,Integer>(new Pair<>(i,p.getKey()),p.getValue());

                    edge_list.add(e);
                }
            }
        }
        for(int i=0;i<vx-1;i++)
        {
            for(var uvw:edge_list)
            {
                int u=uvw.getKey().getKey();
                int v=uvw.getKey().getValue();
                int w=uvw.getValue();

                if(dist.get(u) != Integer.MAX_VALUE && dist.get(u)+w < dist.get(v))
                {
                    dist.set(v,dist.get(u)+w);
                }
            }
        }
        for(var uvw:edge_list)
        {
            int u=uvw.getKey().getKey();
            int v=uvw.getKey().getValue();
            int w=uvw.getValue();

            if(dist.get(u) != Integer.MAX_VALUE && dist.get(u)+w < dist.get(v))
            {
                negcyc=true;
            }
            else negcyc=false;
        }
        return dist;
    }
    //floydwarshall
    //johnson
    //kruskal
}



public class Main {
    public static void main(String[] args){
        Graph g=new Graph(3,3);
        g.d_addEdge(0,1,10);
        g.d_addEdge(1,2,5);
        g.d_addEdge(2,0,-16);
        g.show_graph();
        ArrayList<Integer> al1=g.bfs(0);
        ArrayList<Integer> al2=g.dfs(0);
        ArrayList<Integer> al3=g.dijkstra_pq(0);
        ArrayList<Integer> al4=g.dijkstra(0);
        ArrayList<Integer> al5=g.bellman_ford(0);
        System.out.print("BFS: ");
        for (Integer integer : al1) {
            System.out.print(integer + " ");
        }
        System.out.println();
        System.out.print("DFS: ");
        for (Integer integer : al2) {
            System.out.print(integer + " ");
        }
        System.out.println();
        System.out.print("Shortest distances (Dijkstra PQ): ");
        for (Integer integer : al3) {
            System.out.print(integer + " ");
        }
        System.out.println();
        System.out.print("Shortest distances (Dijkstra): ");
        for (Integer integer : al4) {
            System.out.print(integer + " ");
        }
        System.out.println();
        System.out.print("Shortest distances (Bellman_Ford): ");
        for (Integer integer : al5) {
            System.out.print(integer + " ");
        }
        System.out.println();
        System.out.println(g.negcyc);
    }
}