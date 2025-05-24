
package com.digis01.MMarinPortalPeliculas.Model;


public class Reaccion {
    private int id;
    private boolean favorite; 
    private Object rated;
    private boolean  watchlist;
   private String media_type;
   private int media_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public Object getRated() {
        return rated;
    }

    public void setRated(Object rated) {
        this.rated = rated;
    }

    public boolean isWatchlist() {
        return watchlist;
    }

    public void setWatchlist(boolean watchlist) {
        this.watchlist = watchlist;
    }

    public String getMedia_type() {
        return media_type;
    }

    public void setMedia_type(String media_type) {
        this.media_type = media_type;
    }

    public int getMedia_id() {
        return media_id;
    }

    public void setMedia_id(int media_id) {
        this.media_id = media_id;
    }
   
   
    
}
