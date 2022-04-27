package com.example.optimalizalas_alkalmazasai.Graph;

public class Link {
    private int idf;
    private int weight;

    public Link(int idf, int weight) {
        this.idf = idf;
        this.weight = weight;
    }

    public int getIdf() {
        return idf;
    }

    public double getWeight() {
        return weight;
    }

}
