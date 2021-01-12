package com.example.dteamproject.DTO;

public class MemberDTO {
    String id, passwd, name, phonenumber, birth, email;

    public MemberDTO(String id, String name, String phonenumber, String birth, String email) {
        this.id = id;
        this.name = name;
        this.phonenumber = phonenumber;
        this.birth = birth;
        this.email = email;
    }

    public MemberDTO(String id, String passwd, String name, String phonenumber, String birth, String email) {
        this.id = id;
        this.passwd = passwd;
        this.name = name;
        this.phonenumber = phonenumber;
        this.birth = birth;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
