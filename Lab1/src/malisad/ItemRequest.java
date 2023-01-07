/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class Graphs
 * Name: malisad
 * Created 3/15/2021
 */
package malisad;
/**
 * Course: CS2852
 * Term Spring 2021-2022
 * ItemRequest purpose: to initialize the user and item Id
 * create getters so that the Ids are accessible
 * @author malisad
 * @version created on 3/15/2021 at 5:29 PM
 */
public class ItemRequest{

    private final int userId;
    private final int itemId;

    /**
     * constructor initializing the userId and itemId
     * @param userId id of the user
     * @param itemId id of the item
     */
    public ItemRequest(int userId, int itemId){
        this.userId = userId;
        this.itemId = itemId;
    }

    /**
     * method getter method for userId
     * @return userID
     */
    public int getUserId(){
        return  this.userId;
    }

    /**
     *getter method for itemId
     * @return the item Id
     */
    public int getItemId(){
        return this.itemId;
    }
}