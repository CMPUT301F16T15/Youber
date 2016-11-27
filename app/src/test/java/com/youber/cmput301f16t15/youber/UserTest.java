package com.youber.cmput301f16t15.youber;

import com.youber.cmput301f16t15.youber.users.User;

import org.junit.Test;

/**
 * Created by Reem on 2016-10-13.
 */
import java.util.UUID;

import static org.junit.Assert.*;
public class UserTest
{
    @Test
    public void testGetFirstName()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        assertEquals("First Name", user1.getFirstName());
    }

    @Test
    public void testSetFirstName()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setFirstName("Another name Lol");
        assertEquals("Another name Lol", user1.getFirstName());
    }

    @Test
    public void testSetEmail()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setEmail("email");
        assertEquals("email", user1.getEmail());

    }

    @Test
    public void testGetEmail()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        assertEquals("email",user1.getEmail());
    }

    @Test
    public void testGetLastName()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        assertEquals("Last Name", user1.getLastName());
    }

    @Test
    public void testSetLastName()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setLastName("Another name Lol");
        assertEquals("Another name Lol", user1.getLastName());
    }


    @Test
    public void testGetDateOfBirth()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        assertEquals("Date of birth", user1.getDateOfBirth());
    }

    @Test
    public void testSetDateOfBirthday()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setDateOfBirth("Another date Lol");
        assertEquals("Another date Lol", user1.getDateOfBirth());
    }


    @Test
    public void testGetPhoneNumber()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        assertEquals("phone number", user1.getPhoneNumber());
    }

    @Test
    public void testSetPhoneNumber()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setPhoneNumber("another number Lol");
        assertEquals("another number Lol", user1.getPhoneNumber());
    }

    @Test
    public void testGetVechicleMake()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setMake("Ford");
        assertEquals("Ford",user1.getMake());
    }



    @Test
    public void testSetVechicleMake()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setMake("Ford");
        assertEquals("Ford",user1.getMake());
    }

    @Test
    public void testGetVechicleModel()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setModel("Taurus");
        assertEquals("Taurus",user1.getModel());

    }

    @Test
    public void testSetVechicleModel()
    {
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setModel("Taurus");
        assertEquals("Taurus",user1.getModel());

    }

    @Test
    public void testGetVechicleYear(){
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setYear("2003");
        assertEquals("2003",user1.getYear());
    }

    @Test
    public void testSetVechicleYear(){
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setYear("2003");
        assertEquals("2003",user1.getYear());
    }

    @Test
    public void testGetVechicleColor(){
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setColour("Beige");
        assertEquals("Beige",user1.getColour());
    }

    @Test
    public void testSetVechicleColor(){
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setColour("Beige");
        assertEquals("Beige",user1.getColour());
    }


    @Test
    public void testAddRequesttUUID(){

        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        UUID uuid = UUID.randomUUID();
        user1.addRequestUUID(uuid);
        assertTrue(user1.getRequestUUIDs().contains(uuid));
    }

    @Test
    public void testGetRequestUUID(){
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        UUID uuid = UUID.randomUUID();
        user1.addRequestUUID(uuid);
        assertEquals(user1.getRequestUUIDs().size(),1);

    }

    @Test
    public void testGetRiderUUIDs(){
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        UUID uuid = UUID.randomUUID();
        user1.addRequestUUID(uuid);
        assertEquals(user1.getRiderUUIDs().size(),1);
    }

    @Test
    public void testGetAcceptedDriverUUIDs(){
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setCurrentUserType(User.UserType.driver);
        UUID uuid = UUID.randomUUID();
        user1.addRequestUUID(uuid);
        assertTrue(user1.getAcceptedDriverUUIDs().contains(uuid));
    }

    @Test
    public void testGetConfirmedDriverUUIDs(){
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setCurrentUserType(User.UserType.driver);
        UUID uuid = UUID.randomUUID();
        user1.addToDriverConfirmed(uuid);
        assertEquals(1, user1.getConfirmedDriverUUIDs().size());
    }

    @Test
    public void testAddToDriverConfirmed(){
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setCurrentUserType(User.UserType.driver);
        UUID uuid = UUID.randomUUID();
        user1.addToDriverConfirmed(uuid);
        assertTrue(user1.getConfirmedDriverUUIDs().contains(uuid));
    }


    @Test
    public void testDeleteUUIDFromAccepted(){
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setCurrentUserType(User.UserType.driver);
        UUID uuid = UUID.randomUUID();
        user1.addRequestUUID(uuid);
        assertTrue(user1.getAcceptedDriverUUIDs().contains(uuid));
        user1.deleteUUIDFromAccepted(uuid);
        assertFalse(user1.getAcceptedDriverUUIDs().contains(uuid));


    }


    @Test
    public void testRemoveRequestUUID(){
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        UUID uuid = UUID.randomUUID();
        user1.addRequestUUID(uuid);
        assertTrue(user1.getRequestUUIDs().contains(uuid));
        user1.removeRequestUUID(uuid);
        assertFalse(user1.getRequestUUIDs().contains(uuid));
    }


    @Test
    public void testGetCurrentUserType(){
        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        assertEquals(user1.getCurrentUserType(), User.UserType.rider);
    }


    @Test
    public void testSetCurrentUserType(){

        User user1 = new User("username","First Name", "Last Name", "Date of birth","phone number", "email");
        user1.setCurrentUserType(User.UserType.driver);
        assertEquals(user1.getCurrentUserType(), User.UserType.driver);
    }

}
