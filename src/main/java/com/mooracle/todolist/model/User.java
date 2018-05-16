package com.mooracle.todolist.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/** ENTRY LIST:
 * ENTRY 4: Users and Roles
 * */

@Entity //4-2
public class User implements UserDetails {//4-6: UserDetails is Spring security in house interface just implement it!
    //4-1: field declaration
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true) //4-3: this username will be a column name and the value must be unique
    @Size(min = 8, max = 20)
    private String username;

    @Column(length = 100) //4-4: column with this size?
    private String password;

    @Column(nullable = false) //4-5: this column field must not be empty in value
    private boolean enabled;

    @OneToOne //4-14: since one role has only one name (username)
    @JoinColumn(name = "role_id") //4-15: join column from other entity
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //4-13: one role one user
        List<GrantedAuthority> authorities = new ArrayList<>();//4-14 creating the list for the granted one
        authorities.add(new SimpleGrantedAuthority(role.getName()));
        return authorities;
    }

    @Override
    public String getPassword() {
        return password; //4-12: we got password field
    }

    @Override
    public String getUsername() {
        return username; //4-11: we got username field
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //4-10: yes it is non expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //4-9: the account is non locked
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;//4-8: the credential is non-expired, once it created it will be valid forever until destroyed
    }

    @Override
    public boolean isEnabled() {
        return enabled; //4-7: we got field for this so just return the field
    }
}
