package com.demo.clinton.drsnappy.database;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

@Table(database = AppDatabase.class)
public class User extends BaseModel {
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Boolean getAuthenticatedFully() {
        return authenticatedFully;
    }

    public void setAuthenticatedFully(Boolean authenticatedFully) {
        this.authenticatedFully = authenticatedFully;
    }

    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String username;

    @Column
    String emailId;

    @Column
    String access_token;

    @Column
    Boolean authenticatedFully;


}
