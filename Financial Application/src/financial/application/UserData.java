/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package financial.application;

/**
 *
 * @author yduong
 */
class UserData {
    String username;
    String password;
    private String email;
    private String firstName;
    private String lastName;
    private int income;
    private int bill;
    private int saving;
    
    UserData (String user, String pass){
        this.username = user;
        this.password = pass;
    }
    
    public String getUsername(){
        return username;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getFirst() {
        return firstName;
    }
    
    public String getLast() {
        return lastName;
    }
    
    public int getIncome() {
        return income;
    }
    
    public int getBill() {
        return bill;
    }
    
    public int getSaving() {
        return saving;
    }
    
    public void setUsername (String username) {
        this.username = username;
    }
    
    public void setPassword (String password) {
        this.password = password;
    }
    
    public void setEmail (String email) {
        this.email = email;
    }
    
    public void setFirst (String firstName) {
        this.firstName = firstName;
    }
    
    public void setLast (String lastName) {
        this.lastName = lastName;
    }
    
    public void setIncome (int income){
        this.income = income;
    }
    
    public void setBill (int bill) {
        this.bill = bill;
    }
    
    public void setSaving (int saving) {
        this.saving = saving;
    }
    
    public String toString() {
        return String.format("%s, %s, %s, %s, %s, %s\n", email, firstName, lastName, income, bill, saving);
    }
}
