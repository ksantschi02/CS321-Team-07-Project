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

    private ArrayList<Game> gameList;
    private ArrayList<User> userList;

    public void readGameList() {
        ArrayList<Game> returnList = new ArrayList<>();
        try {
            File inputFile = new File("data_files/gamelist.xml");
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
                    }
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
            File inputFile = new File("data_files/userlist.xml");
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

    public void addUserScratch(String username, String password) {
        userList.add(new User(username, password));
    }

    public ArrayList<Game> getGameList() {
        return gameList;
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    public void saveDatabase() {
        try {
            File inputFile = new File("data_files/gamelist.xml");
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

        try {
            File inputFile = new File("data_files/userlist.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();
            Node root = doc.getFirstChild();
            doc.removeChild(root);
            Element users = doc.createElement("users");
            for (User u : userList)
            {
                System.out.printf(u.getUser() + "\n");
                Element user = doc.createElement("user");

                Element username = doc.createElement("username");
                username.setAttribute("value", u.getUser());
                user.appendChild(username);

                Element password = doc.createElement("password");
                password.setAttribute("value", u.getPassword());
                user.appendChild(password);

                for (Collection c : u.getCollections()) {
                    System.out.printf(c.getTitle() + "\n");
                    Element collection = doc.createElement("collection");
                    collection.setAttribute("sortType", String.valueOf(c.getCollectionSortType()));
                    collection.setAttribute("filterType", String.valueOf(c.getCollectionFilterType()));
                    collection.setAttribute("name", c.getTitle());
                    for (Game g : c.getGames()) {
                        System.out.printf(g.getTitle() + "\n");
                        Element game = doc.createElement("game");
                        game.setAttribute("name", g.getTitle());
                        collection.appendChild(game);
                    }
                    user.appendChild(collection);
                }
                users.appendChild(user);
            }
            doc.appendChild(users);
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
} //Push class