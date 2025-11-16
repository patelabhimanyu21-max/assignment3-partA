/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedulingsystem;

public class User {
    protected String userId;
    protected String name;
    protected String email;
    protected String phone;
    protected String role;
    protected String passwordHash;
    protected boolean isActive;

    public void login(){ System.out.println(name + " logged in."); }
    public void logout(){ System.out.println(name + " logged out."); }
    public void updateProfile(){ System.out.println("Profile updated."); }
    public void viewNotifications(){ System.out.println("Viewing notifications..."); }
}
