package com.demo.entity;

import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity  
@Table(name = "user_info")  
public class UserInfo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3615629434187317277L;
	private String id;  
    private String username;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "uuid")    
    @GenericGenerator(name = "uuid", strategy = "uuid") 
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "passsword")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;
}
