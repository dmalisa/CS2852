/*
 * Course: CS2852
 * Term Spring 2021-2022
 * File header contains class Waiting list
 * Name: malisad
 * Created 3/15/2021
 */
package malisad;


import java.util.List;
/**
 * Course: CS2852
 * Term Spring 2021-2022
 * WaitingList: creates a waiting list
 * @author malisad
 * @version created on 3/15/2021 at 5:29 PM
 */
public class WaitingList {

    List<ItemRequest> requests;

    /**
     * constructor for waiting list and
     * creates a requests list
     * @param list list being taken in
     */
    public WaitingList(List<ItemRequest> list) {
        requests = list;
    }

    /**
     * method searches the list of requests to find the first
     * request with an stock item
     * @param isFulfillable whether the request is fulfillable or not
     * @return the requestItem of found otherwise null id not
     */
    public ItemRequest nextFulfillableRequest(boolean isFulfillable) {
                for (ItemRequest request : requests) {
                    if (isFulfillable){
                    return request;
                }
            }
       return null;
    }

    /**
     *adds an item to the requests list
     * @param request being added to the list
     */
    public void requestItem(ItemRequest request) {
        requests.add(request);

    }

    /**
     * method searches the list to find all requests with the given
     * userID and makes a userRequests list with those requests
     * @param userId the id of the user being searched for
     * @param userRequests the list that will be created containing the user requests
     */
    public void getAllRequestsFromUser(int userId, List<ItemRequest> userRequests) {
        for (ItemRequest request : requests) {
            if (request.getUserId() == userId) {
                userRequests.add(request);
            }
        }
    }

    /**
     * method searches the list for a given request and removes it from the list
     * if it is found true is returned
     * @param request the request being looked for
     * @return true if the the request was found and removed
     */
    public boolean cancelRequest(ItemRequest request) {
        for (ItemRequest request1 : requests) {
            if (request1.equals(request)) {
                requests.remove(request);
                return true;
            }
        }
        return false;
    }

    /**
     * clears the list of requests
     */
    public void clear() {
        requests.clear();
    }

    /**
     * checks if the list is empty
     * @return true is it is empty
     */
    public boolean isEmpty() {
        return requests.isEmpty();
    }
}