import java.util.ArrayList;
import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;

public class Database {

    private ArrayList<Game> gameList;
    private ArrayList<User> userList;
    private int gameCounter;

    public void readGameList() {
        ArrayList<Game> returnList = new ArrayList<>();
        try {
            File inputFile = new File("C:/temp/gamelist.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("item");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList childNodes = nNode.getChildNodes();
                    ArrayList<Review> reviews = new ArrayList<>();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node item = childNodes.item(j);
                        if (item.getNodeType() == Node.ELEMENT_NODE) {
                            if ("review".equalsIgnoreCase(item.getNodeName())) {
                                Element rElement = (Element) item;
                                Review r = new Review(
                                        Double.parseDouble(((Element) rElement.getElementsByTagName("rating").item(0)).getAttribute("value")),
                                        rElement.getElementsByTagName("content").item(0).getTextContent(),
                                        rElement.getElementsByTagName("author").item(0).getTextContent()
                                );
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
                    }
                    Game e = new Game(Integer.parseInt(eElement.getAttribute("id")),
                            Integer.parseInt(((Element) eElement.getElementsByTagName("minplayers").item(0)).getAttribute("value")),
                            Integer.parseInt(((Element) eElement.getElementsByTagName("maxplayers").item(0)).getAttribute("value")),
                            Integer.parseInt(((Element) eElement.getElementsByTagName("minplaytime").item(0)).getAttribute("value")),
                            Integer.parseInt(((Element) eElement.getElementsByTagName("maxplaytime").item(0)).getAttribute("value")),
                            Integer.parseInt(((Element) eElement.getElementsByTagName("minage").item(0)).getAttribute("value")),
                            rating,
                            ((Element) eElement.getElementsByTagName("name").item(0)).getAttribute("value"),
                            " ",
                            eElement.getElementsByTagName("description").item(0).getTextContent(),
                            eElement.getElementsByTagName("image").item(0).getTextContent(),
                            reviews
                    );
                    returnList.add(e);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.gameList = returnList;
    }

    public Game retrieveByName(String name) {
        for (Game game : gameList) {
            if (game.getTitle().equalsIgnoreCase(name)) {
                return game;
            }
        }
        return null;
    }

    public void readUserList() {
        ArrayList<User> returnList = new ArrayList<>();
        try {
            File inputFile = new File("C:/temp/userlist.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("user");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    User u = new User(
                            ((Element) eElement.getElementsByTagName("username").item(0)).getAttribute("value"),
                            ((Element) eElement.getElementsByTagName("password").item(0)).getAttribute("value")
                    );
                    NodeList collectionList = ((Element) nNode).getElementsByTagName("collection");
                    for (int j = 0; j < collectionList.getLength(); j++) {
                        Node cNode = collectionList.item(j);
                        if (cNode.getNodeType() == Node.ELEMENT_NODE) {
                            ArrayList<Game> games = new ArrayList<>();
                            NodeList cGames = cNode.getChildNodes();
                            for (int k = 0; k < cGames.getLength(); k++) {
                                Node gNode = cGames.item(k);
                                if (gNode.getNodeType() == Node.ELEMENT_NODE) {
                                    Element gElement = (Element) gNode;
                                    games.add(retrieveByName(gElement.getAttribute("name")));
                                }
                            }
                            Element cElement = (Element) cNode;
                            Collection c = new Collection(
                                    Integer.parseInt(cElement.getAttribute("sortType")),
                                    Integer.parseInt(cElement.getAttribute("filterType")),
                                    cElement.getAttribute("name"),
                                    games);
                            u.addCollection(c);
                        }
                    }
                    returnList.add(u);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.userList = returnList;
    }

    public User retrieveByUser(String username) {
        for (User user : userList) {
            if (user.getUser().equalsIgnoreCase(username)) {
                return user;
            }
        }
        return null;
    }

    public void addUserScratch(String username, String password) {
        userList.add(new User(username, password));
    }

    public void addUserObject(User user) {
        userList.add(user);
    }

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void saveDatabase() {
        try {
            File inputFile = new File("C:/temp/gamelist.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("item");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    NodeList childNodes = nNode.getChildNodes();
                    for (int j = 0; j < childNodes.getLength(); j++) {
                        Node item = childNodes.item(j);
                        if (item.getNodeType() == Node.ELEMENT_NODE) {
                            if ("avgrating".equalsIgnoreCase(item.getNodeName())) {
                                nNode.removeChild(item);
                            }
                            if ("review".equalsIgnoreCase(item.getNodeName())) {
                                nNode.removeChild(item);
                            }
                        }
                    }

                    Element avgrating = doc.createElement("avgrating");
                    avgrating.setAttribute("value", String.valueOf(gameList.get(i).getAvgRating()));
                    nNode.appendChild(avgrating);

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
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            File inputFile = new File("C:/temp/userlist.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            Node users = (doc.getElementsByTagName("users")).item(0);
            NodeList nList = users.getChildNodes();
            ArrayList<User> userListTemp = new ArrayList<>();
            for (User u : userList) {
                userListTemp.add(new User(u));
            }
            for (User u : userListTemp) {
                for (int i = 0; i < nList.getLength(); i++) {
                    Node nNode = nList.item(i);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element eElement = (Element) nNode;
                        if (((Element) eElement.getElementsByTagName("username").item(0)).getAttribute("value").equals(u.getUser())) {
                            if (((Element) eElement.getElementsByTagName("password").item(0)).getAttribute("value").equals(u.getPassword())) {
                                NodeList childNodes = nNode.getChildNodes();
                                for (int j = 0; j < childNodes.getLength(); j++) {
                                    Node item = childNodes.item(j);
                                    if (item.getNodeType() == Node.ELEMENT_NODE) {
                                        if ("collection".equalsIgnoreCase(item.getNodeName())) {
                                            nNode.removeChild(item);
                                        }
                                    }
                                }
                                // RENEW/ADD COLLECTIONS HERE
                                for (Collection c : u.getCollections()) {
                                    Element collection = doc.createElement("collection");
                                    collection.setAttribute("sortType", String.valueOf(c.getCollectionSortType()));
                                    collection.setAttribute("filterType", String.valueOf(c.getCollectionFilterType()));
                                    collection.setAttribute("name", c.getTitle());
                                    for (Game g : c.getGames()) {
                                        Element game = doc.createElement("game");
                                        game.setAttribute("name", g.getTitle());
                                        collection.appendChild(game);
                                    }
                                    nNode.appendChild(collection);
                                }
                                userListTemp.remove(u);
                            }
                        }
                    }
                }
            }
            for (User u : userListTemp) {
                Element user = doc.createElement("user");

                Element username = doc.createElement("username");
                username.setAttribute("value", u.getUser());
                user.appendChild(username);

                Element password = doc.createElement("password");
                password.setAttribute("value", u.getPassword());
                user.appendChild(password);

                for (Collection c : u.getCollections()) {
                    Element collection = doc.createElement("collection");
                    collection.setAttribute("sortType", String.valueOf(c.getCollectionSortType()));
                    collection.setAttribute("filterType", String.valueOf(c.getCollectionFilterType()));
                    collection.setAttribute("name", c.getTitle());
                    for (Game g : c.getGames()) {
                        Element game = doc.createElement("game");
                        game.setAttribute("name", g.getTitle());
                        collection.appendChild(game);
                    }
                    user.appendChild(collection);
                }
                users.appendChild(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
} //Push