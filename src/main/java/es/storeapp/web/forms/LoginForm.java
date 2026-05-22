package es.storeapp.web.forms;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LoginForm {
    
    @NotNull
    @Size(min=1)
    private String email;
    
    @NotNull
    @Size(min=1)
    private String password;

    private Boolean rememberMe;
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }
    
    
    
}
