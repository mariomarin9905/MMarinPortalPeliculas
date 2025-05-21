package com.digis01.MMarinPortalPeliculas.Model;

import java.util.List;


public class ResultMedia<T> {
    public int page;
    public List<T> results;
    public int total_pages;
    public int total_results;
}
