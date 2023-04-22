package data_retrieval;

import java.util.ArrayList;
import game_info.Collection;
import game_info.Game;
import game_info.Review;
import org.w3c.dom.*;
import program_users.User;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;

public class Database {

    private ArrayList<Game> gameList;   //List of games + data pulled from database
    private ArrayList<User> userList;   //List of Users + collections pulled from database

    /**
     * readGameList method parses through an XML document containing all information regarding the games, their
     * information and reviews, and stores the data as a list of Game objects
     * @return the list of Game objects
     */
    public void readGameList() {
        ArrayList<Game> returnList = new ArrayList<>(); //instantiate final list of games
        //use try-catch statement to see if file can be found
        try {
            //read from file and create documentbuilder element
            File inputFile = new File("data_files/gamelist.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            //pull list of item nodes (each item is a separate game)
            NodeList nList = doc.getElementsByTagName("item");
            //for each item node (game node)
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    //pull list of child nodes under each game node
                    NodeList childNodes = nNode.getChildNodes();
                    ArrayList<Review> reviews = new ArrayList<>();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node item = childNodes.item(j);
                        if (item.getNodeType() == Node.ELEMENT_NODE) {
                            //for each game node, retrieve nodes labeled as reviews:
                            if ("review".equalsIgnoreCase(item.getNodeName())) {
                                Element rElement = (Element) item;
                                //instantiate information as a new Review object
                                Review r = new Review(
                                        Double.parseDouble(((Element) rElement.getElementsByTagName("rating").item(0)).getAttribute("value")),
                                        rElement.getElementsByTagName("content").item(0).getTextContent(),
                                        rElement.getElementsByTagName("author").item(0).getTextContent()
                                );
                                //add new review object to a list
                                reviews.add(r);
                            }
                        }
                    }
                    Element eElement = (Element) nNode;
                    double rating;
                    if ((eElement.getElementsByTagName("avgrating").item(0)) == null) {
                        rating = 0;
                    } else {
                        rating = Double.parseDouble(((Element) eElement.getElementsByTagName("avgrating").item(0)).getAttribute("value"));
                    } //if no listed rating, list as 0. otherwise pull review
                    NodeList categoryList = ((Element) nNode).getElementsByTagName("link");
                    ArrayList<String> genreList = new ArrayList<>();
                    for (int k = 0; k < categoryList.getLength(); k++)
                    {
                        Node item2 = categoryList.item(k);
                        if (item2.getNodeType() == Node.ELEMENT_NODE)
                        {
                            if (((Element) item2).getAttribute("type").equals("boardgamecategory"))
                            {
                                genreList.add(((Element) item2).getAttribute("value"));
                            }
                        }
                    }   //retrieve all category tags and store as list of strings
                    Game e = new Game(Integer.parseInt(eElement.getAttribute("id")),
                            Integer.parseInt(((Element) eElement.getElementsByTagName("minplayers").item(0)).getAttribute("value")),
                            Integer.parseInt(((Element) eElement.getElementsByTagName("maxplayers").item(0)).getAttribute("value")),
                            Integer.parseInt(((Element) eElement.getElementsByTagName("minplaytime").item(0)).getAttribute("value")),
                            Integer.parseInt(((Element) eElement.getElementsByTagName("maxplaytime").item(0)).getAttribute("value")),
                            Integer.parseInt(((Element) eElement.getElementsByTagName("minage").item(0)).getAttribute("value")),
                            rating,
                            ((Element) eElement.getElementsByTagName("name").item(0)).getAttribute("value"),
                            genreList,
                            eElement.getElementsByTagName("description").item(0).getTextContent(),
                            eElement.getElementsByTagName("thumbnail").item(0).getTextContent(),
                            reviews
                    );  //instantiate all given information as new Game object, add to final list of games
                    returnList.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.gameList = returnList;
    }   //parses xml document to create database of Game objects

    /**
     * retrieveByName method takes a string corresponding to a game title and retrieves the Game object
     * @param name is the string corresponding to the title of the game
     * @return the Game object based on the title match
     */
    public Game retrieveByName(String name) {
        for (Game game : gameList) {
            if (game.getTitle().equalsIgnoreCase(name)) {
                return game;
            }
        }
        return null;
    }   //retrieves Game object based on game title

    /**
     * readUserList method parses through an XML document containing all information regarding the users, their
     * information and collections, and stores the data as a list of User objects
     * @return the list of User objects
     */
    public void readUserList() {
        ArrayList<User> returnList = new ArrayList<>(); //instantiate final list of User objects
        //use try-catch statement to see if file can be found
        try {
            //read from file and create documentbuilder element
            File inputFile = new File("data_files/userlist.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            //retrieve list of nodes corresponding to users
            NodeList nList = doc.getElementsByTagName("user");
            //for each user node
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    User u = new User(
                            ((Element) eElement.getElementsByTagName("username").item(0)).getAttribute("value"),
                            ((Element) eElement.getElementsByTagName("password").item(0)).getAttribute("value")
                    ); //instantiate new user object with username and password
                    //retrieve list of collections under a specific user node
                    NodeList collectionList = ((Element) nNode).getElementsByTagName("collection");
                    for (int j = 0; j < collectionList.getLength(); j++) {
                        Node cNode = collectionList.item(j);
                        if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                            ArrayList<Game> games = new ArrayList<>();
                            //retrieve list of games under a specific collection node
                            NodeList cGames = cNode.getChildNodes();
                            for (int k = 0; k < cGames.getLength(); k++) {
                                Node gNode = cGames.item(k);
                                if (gNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element gElement = (Element) gNode;
                                    games.add(retrieveByName(gElement.getAttribute("name")));
                                } //for each game under the collection, retrieve the corresponding Game object from the
                                  //gameList and add it to a list of Game objects
                            }
                            Element cElement = (Element) cNode;
                            Collection c = new Collection(
                                    Integer.parseInt(cElement.getAttribute("sortType")),
                                    Integer.parseInt(cElement.getAttribute("filterType")),
                                    cElement.getAttribute("name"),
                                    games); //instantiate collection with created list of games
                            u.addCollection(c); //add collections to corresponding user
                        }
                    }
                    returnList.add(u); //add user to list of User objects
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.userList = returnList; //return final list of User objects
    }

    /**
     * addUserScratch method instantiates a new User object based on a username and password and adds it to userList
     * @param username is the string for the username of the new User
     * @param password is the string for the password of the new User
     */
    public void addUserScratch(String username, String password) {
        userList.add(new User(username, password));
    } //add new user from username and password strings

    /**
     * getGameList method returns the arraylist of Game objects
     * @return arraylist of Game objects
     */
    public ArrayList<Game> getGameList() {
        return gameList;
    } //observer method for the game list

    /**
     * getUserList method returns the arraylist of User objects
     * @return arraylist of User objects
     */
    public ArrayList<User> getUserList() {
        return userList;
    } //observer method for the user list

    /**
     * saveDatabase method takes current gameList and userList information and writes/"saves" it back to the two
     * XML documents that hold all the data
     */
    public void saveDatabase() {
        //use try-catch statement to see if file can be found
        try {
            //read from file and create documentbuilder element
            File inputFile = new File("data_files/gamelist.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            //pull list of item nodes (each item is a separate game)
            NodeList nList = doc.getElementsByTagName("item");
            //for each item node (game node)
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    ArrayList<Node> removalList = new ArrayList<>(); //instantiate list of Node objects to remove from game node
                    NodeList childNodes = nNode.getChildNodes(); //pull list of child nodes under each game node
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node item = childNodes.item(j);
                        if (item.getNodeType() == Node.ELEMENT_NODE) {
                            if ("avgrating".equalsIgnoreCase(item.getNodeName()) || "review".equalsIgnoreCase(item.getNodeName())) {
                                removalList.add(item); //if child node is labeled as avgrating or a review, add to the removal list
                            }
                        }
                    }
                    for (Node n : removalList) {
                        nNode.removeChild(n); //remove listed child node from game node
                    }

                    //create new avgrating node, set value attribute and append it to the game node
                    Element avgrating = doc.createElement("avgrating");
                    avgrating.setAttribute("value", String.valueOf(gameList.get(i).getAvgRating()));
                    nNode.appendChild(avgrating);

                    //for each review under a game, create new review node, set childnode contents and append to game node
                    for (Review r : gameList.get(i).getReviews()) {
                        Element review = doc.createElement("review");

                        Element rating = doc.createElement("rating");
                        rating.setAttribute("value", String.valueOf(r.getRating()));

                        Element content = doc.createElement("content");
                        content.setTextContent(r.getContent());

                        Element author = doc.createElement("author");
                        author.setTextContent(r.getAuthor());

                        review.appendChild(rating);
                        review.appendChild(content);
                        review.appendChild(author);

                        nNode.appendChild(review);
                    }
                }
            }

            //creates transformer factory element and writes information from new xml document back to original document
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            FileWriter fileWriter = new FileWriter("data_files/gamelist.xml");
            fileWriter.write(output);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //user try-catch statement to see if file can be found
        try {
            //read from file and create documentbuilder element
            File inputFile = new File("data_files/userlist.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            //retrieves root node of document and deletes it + all child nodes
            Node root = doc.getFirstChild();
            doc.removeChild(root);

            Element users = doc.createElement("users"); //recreates root node

            for (User u : userList) //for every User object in the userList
            {
                Element user = doc.createElement("user"); //create new user node

                Element username = doc.createElement("username");
                username.setAttribute("value", u.getUser());
                user.appendChild(username); //create and add username child node to user node

                Element password = doc.createElement("password");
                password.setAttribute("value", u.getPassword());
                user.appendChild(password); //create and add password child node to user node

                for (Collection c : u.getCollections()) //for every Collection object under the User
                {
                    Element collection = doc.createElement("collection"); //create new collection node
                    collection.setAttribute("sortType", String.valueOf(c.getCollectionSortType()));
                    collection.setAttribute("filterType", String.valueOf(c.getCollectionFilterType()));
                    collection.setAttribute("name", c.getTitle()); //set all attributes
                    for (Game g : c.getGames()) //for every Game object in the collection
                    {
                        Element game = doc.createElement("game");
                        game.setAttribute("name", g.getTitle());
                        collection.appendChild(game); //create and add game child node to collection node
                    }
                    user.appendChild(collection); //add collection child node to user node
                }
                users.appendChild(user); //add user child node to root node
            }
            doc.appendChild(users); //add root node to document

            //creates transformer factory element and writes information from new xml document back to original document
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer();
            StringWriter writer = new StringWriter();
            transformer.transform(new DOMSource(doc), new StreamResult(writer));
            String output = writer.getBuffer().toString();
            FileWriter fileWriter = new FileWriter("data_files/userlist.xml");
            fileWriter.write(output);
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}