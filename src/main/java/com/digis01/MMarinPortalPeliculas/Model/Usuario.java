
package com.digis01.MMarinPortalPeliculas.Model;


public class Usuario {
    private boolean success;
    private String username;
    private String password;    
    private String request_token;
    private String session_id;

    public void setSuccess(boolean success){
        this.success = success;
    }
    public boolean getSuccess(){
        return this.success;
    }
    
    public void setSession_id(String session_id){
        this.session_id = session_id;
    }
    public String getSession_id(){
        return this.session_id;
    }
    public String getRequest_token() {
        return request_token;
    }

    public void setRequest_token(String request_token) {
        this.request_token = request_token;
    }

    public void setPassword(String Password){
        this.password = Password;
    }
    public String getPassword(){
        return this.password;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

  
  
    
    
}
