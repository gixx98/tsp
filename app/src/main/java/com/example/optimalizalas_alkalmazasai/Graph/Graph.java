package com.example.optimalizalas_alkalmazasai.Graph;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Objects;

public class Graph {

    private Hashtable<String, Integer[]> distancesT;
    private ArrayList<Integer> names;
    private Hashtable<Integer, Node> vertex;
    private ArrayList<Arrow> arrows;
    private Arrow aux;
    private int nodes;

    public Graph() {
        vertex = new Hashtable<Integer, Node>();
        arrows = new ArrayList<Arrow>();
        names = new ArrayList<Integer>();
        distancesT = new Hashtable<String, Integer[]>();
        nodes = 0;
    }

    public Graph(Hashtable<Integer, Node> vertex, ArrayList<Arrow> arrows) {
        this.vertex = vertex;
        this.arrows = arrows;
        distancesT = new Hashtable<String, Integer[]>();
        names = Collections.list(vertex.keys());
        nodes = vertex.size();
    }


    public Hashtable<Integer, Node> getVertex() {
        return vertex;
    }

    public Node getNode(int name) {
        return (Node) vertex.get(name);
    }

    public void setVertex(Hashtable<Integer, Node> vertex) {
        this.vertex = vertex;
    }

    public ArrayList<Integer> getNames() {
        return names;
    }

    public ArrayList<String> getNamesString(){
        ArrayList<String> names = new ArrayList<String>();
        for(int name : this.names){
            names.add(name+"");
        }
        return names;
    }

    public void setNames(ArrayList<Integer> names) {
        this.names = names;
    }

    public void addNode(Node node) {
        names.add(nodes);
        vertex.put(nodes, node);
        nodes++;
    }

    public void addNode(int id) {
        Node node = new Node(0, 0, id, 1);
        vertex.put(id, node);
        nodes++;
    }

    public void addNode(int x, int y, int viewportWidth, int viewportHeight,
                        float density) {
        names.add(nodes);
        Node node = new Node(x, y, nodes,
                density);
        vertex.put(nodes, node);
        nodes++;
    }

    public void addNodeF(int id, float posX, float posY, int viewportWidth, int viewportHeight, float density) {
        names.add(id);
        Node node = new Node(posX, posY, id,
                density);
        vertex.put(id, node);
        if(id>=nodes)nodes=id+1;
    }

    public Node copyNode(int name) {
        Node n = vertex.get(name);
        return n;

    }

    public void colorRestorationNodes() {
        Enumeration<Node> nodes = vertex.elements();
        while (nodes.hasMoreElements())
            ((Node) (nodes.nextElement())).setColor(Color.BLACK);
    }

    public void setColorOfNode(int id, int color) {
        ((Node) vertex.get(id)).setColor(color);
    }

    public void deleteNode(int name) {
        ArrayList<int[]> to_delete = new ArrayList<int[]>();
        Node node = new Node();
        node = vertex.get(name);
        Iterator<Link> links = node.getLinks().iterator();
        while (links.hasNext()) {
            Link link = links.next();
            int idf = link.getIdf();
            Node nodef = vertex.get(idf);
            int[] link_to_delete = new int[2];
            link_to_delete[0] = name;
            link_to_delete[1] = idf;
            to_delete.add(link_to_delete);
            Iterator<Link> links_ = nodef.getLinks().iterator();
            nodef.initNode(idf);
            while (links_.hasNext()) {
                Link link_ = links_.next();
                if (!(link_.getIdf() == name))
                    nodef.addLink(link_.getIdf(),
                            (int) Math.floor(link_.getWeight()));
            }
        }
        Iterator<int[]> remove = to_delete.iterator();
        while (remove.hasNext()) {
            int[] r = new int[2];
            r = remove.next();
            deleteLink(r[0], r[1]);
            deleteLink(r[1], r[0]);
        }
        names.remove((Object)name);
        vertex.get(name).initNode(name);
        Node n = new Node(0, 0, -1, 1);
        vertex.put(name, n);
    }

    /*
     * ARROWS
     */
    public void setArrows(ArrayList<Arrow> arrows) {
        this.arrows = arrows;
    }

    public ArrayList<Arrow> getArrows() {
        return arrows;
    }

    public void addLink(int idi, int idf, int x1, int y1, int x2, int y2, int weight) {
        System.out.println("Add link starts");
        System.out.println("idi: " + idi + " idf: "+ idf + " x1:" + x1 + " y1:" + y1 + " x2:" + x2 + " y2:" + y2);
        Arrow a = new Arrow(idi, idf, weight, x1, y1, x2,y2);
        System.out.println("size:" + arrows.size());

        for (int i = 0; i < arrows.size(); i++) {
            System.out.println(arrows.get(i));
            Arrow aa = arrows.get(i);
            if (aa.getIdi()==idi) {
                if (aa.getIdf()==idf) {
                    a = null;
                }
            }
            if (aa.getIdf()==idi) {
                if (aa.getIdi()==idf) {
                    a = null;
                }
            }
        }

        if (a != null) {
            int i = searchIndex(a.getWeight());
            if (i == -1)
                arrows.add(a);
            else
                arrows.add(i, a);

            System.out.println("Vertex: " + vertex.get(idi) + " found");
            Objects.requireNonNull(vertex.get(idi)).addLink(idf, weight);
            System.out.println("Link for " + idf +" added");

            System.out.println("Vertex: " + vertex.get(idf) + " found");
            Objects.requireNonNull(vertex.get(idf)).addLink(idi, weight);
            System.out.println("Link for " + idi +" added");

        }
    }

    public void deleteLink(int idi, int idf) {
        Node ni = vertex.get(idi);
        Iterator<Link> lni = ni.getLinks().iterator();
        ni.initNode(idi);
        Node nf = vertex.get(idf);
        Iterator<Link> lnf = nf.getLinks().iterator();
        nf.initNode(idf);
        while (lni.hasNext()) {
            Link li = lni.next();
            if (!(li.getIdf() == idf))
                ni.addLink(li.getIdf(), (int) Math.floor(li.getWeight()));
        }
        while (lnf.hasNext()) {
            Link lf = lnf.next();
            if (!(lf.getIdf() == idi))
                nf.addLink(lf.getIdf(), (int) Math.floor(lf.getWeight()));
        }
        for (int i = 0; i < arrows.size(); i++) {
            Arrow a = arrows.get(i);
            if (a.getIdi() == idi && (a.getIdf() == idf)) {
                vertex.get(idf).removeLink(idi);
                vertex.get(idi).removeLink(idf);
                arrows.remove(i);
            }
            if (a.getIdf() == idi && a.getIdi() == idf) {
                vertex.get(idf).removeLink(idi);
                vertex.get(idi).removeLink(idf);
                arrows.remove(i);
            }
        }
    }



    public Arrow searchEdge(int idi, int idf) {
        Arrow a = new Arrow(idi, idf, 0);
        return a;
    }

    public int searchIndex(float weight) {
        for (int i = 0; i < arrows.size(); i++) {
            if (weight < arrows.get(i).getWeight())
                return i;
        }
        return -1;
    }

    public void update() {
        for (int i = 0; i < arrows.size(); i++) {
            int idi = arrows.get(i).getIdi();
            int idf = arrows.get(i).getIdf();
            Node iN = vertex.get(idi);
            Node iF = vertex.get(idf);
            arrows.get(i).start = new float[] { iN.getCenterX(),
                    iN.getCenterY() };
            arrows.get(i).stop = new float[] { iF.getCenterX(), iF.getCenterY() };

        }
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean sameEdge(Arrow a1, Arrow a2) {
        if (a1.getIdi()==(a2.getIdi()) && a1.getIdf()==(a2.getIdf())) {
            return true;
        } else if (a1.getIdf()==(a2.getIdi())
                && a1.getIdi()==(a2.getIdf())) {
            return true;
        }
        return false;
    }

}
