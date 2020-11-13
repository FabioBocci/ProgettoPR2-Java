import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class MicroBlog implements SocialNetwork {
/**
 *          AF: <MapOfFollowers , MapOfLikes > t.c. MapOfFollowers = Map< User , SetOfUsers > AND MapOfLikes = Map< Post , SetOfUsers >
 *          RI: MapOfFollowers != Null AND MapOfLikes != Null AND
 *              (forall User in MapOfFollowers.getkeyset() => MapOfFollowers.get(User) != Null AND MapOfFollowers.get(User).contains(User) == False )
 *              (forall Post in MapOfLikes.getkeyset() => MapOfLikes.get(Post) != Null AND MapOfLikes.get(Post) == Set of the user that assigned a like to the post Post )
 *              
 */

    private Map<String, Set<String>> MapOfFollowers;
    private Map<Post, Set<String>> MapOfLikes;


    public MicroBlog()
    {
        this.MapOfFollowers = new HashMap<>();
        this.MapOfLikes = new HashMap<>();
    }

    @Override
    public Map<String, Set<String>> guessFollowers(List<Post> ps) throws NullPointerException 
    {
        if(ps == null) throw new NullPointerException("Passato parametro Null a guessFollowers");
        Set<String> authorSet = new HashSet<>();

        for (Post post : ps) 
        {
            authorSet.add(post.getAuthor());    
        }
        Map<String , Set<String>> map = new HashMap<>();

        for(String author : authorSet)
        {
            if(MapOfFollowers.keySet().contains(author))
            {
                map.put(author, MapOfFollowers.get(author));
            }   
        }
        return map;
    }


    @Override
    public List<String> influencers() throws EmptyNetworkException              
    {
        if(MapOfFollowers.isEmpty()) throw new EmptyNetworkException("Network Empty");

        SetValueComparato bvc = new SetValueComparato(MapOfFollowers);
        TreeMap<String,Set<String>> sorted_map_by_value = new TreeMap<String, Set<String>>(bvc);    //la classe TreeMap esegue un ordinamento delle chiavi tramite il Comparator passato alla creazione
        sorted_map_by_value.putAll(MapOfFollowers);         //all'inserimento verranno tutti ordinati

       return new ArrayList<String>(sorted_map_by_value.keySet());
    }

    @Override
    public Set<String> getMentionedUsers() throws EmptyNetworkException 
    {
        if(MapOfFollowers.isEmpty() || MapOfLikes.isEmpty()) throw new EmptyNetworkException("Network Empty");

        Set<String> ListOfUser = MapOfFollowers.keySet();
        Set<String> mentionedUser = new HashSet<>();
        boolean found = false;

        for (String user : ListOfUser) 
        {
            if(!mentionedUser.contains(user))
            {
                found = false;
                for(Post ps : MapOfLikes.keySet())
                {
                    if(ps.checkWord(user)) found = true;
                }
                if(found) mentionedUser.add(user);
            }
        }
        return mentionedUser;
    }

    @Override
    public Set<String> getMentionedUsers(List<Post> ps) throws EmptyNetworkException, NullPointerException 
    {
        if(MapOfFollowers.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(ps == null)throw new NullPointerException("getMentionedUsers got a Null");

        Set<String> mentionedUsers = new HashSet<>();
        int i;
        boolean found;

        for(String User : MapOfFollowers.keySet())
        {
            if(!mentionedUsers.contains(User))
            {
                found = false;
                i=0;
                while(!found && i < ps.size())
                {
                    found=ps.get(i).checkWord(User);
                    i++;
                }
                if(found) mentionedUsers.add(User);
            }
        }

        return mentionedUsers;
    }

    @Override
    public List<Post> writtenBy(String username) throws EmptyNetworkException, NullPointerException 
    {
        if(MapOfLikes.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(username == null) throw new NullPointerException();

        List<Post> ps = new ArrayList<>();

        for (Post p : MapOfLikes.keySet())
        {
            if(p.getAuthor()==username)
                ps.add(p);
        }

        return ps;
    }

    @Override
    public List<Post> writtenBy(List<Post> ps, String username) throws NullPointerException 
    {
        if(ps == null || username == null) throw new NullPointerException();

        List<Post> p = new ArrayList<>();

        for (Post post : ps) {
            if(post.getAuthor() == username)
                p.add(post);
        }
        return p;
    }

    @Override
    public List<Post> containing(List<String> words) throws EmptyNetworkException, NullPointerException 
    {
        if(MapOfLikes.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(words == null) throw new NullPointerException();

        List<Post> tempList = new ArrayList<>(MapOfLikes.keySet());
        List<Post> returnList = new ArrayList<>(MapOfLikes.keySet());
        int i;
        boolean found;

        for (String word : words) 
        {
            i=0;
            found=false;

            while(!found && i<tempList.size())
            {
                if(tempList.get(i).checkWord(word))
                {
                    found=true;
                    tempList.remove(i);
                }
            }
        }
        returnList.removeAll(tempList);

        return returnList;
    }

    @Override
    public List<Post> removePosts(List<Post> ps) throws EmptyNetworkException, NullPointerException 
    {
        if(MapOfLikes.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(ps == null) throw new NullPointerException();
        
        for(Post p : ps)
        {
            if(MapOfLikes.keySet().contains(p))
            {
                MapOfLikes.remove(p);
                ps.remove(p);
            }
        }
        return ps;

    }

    @Override
    public boolean removePost(Post ps) throws EmptyNetworkException, NullPointerException {
        if(MapOfLikes.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(ps == null) throw new NullPointerException();

        if(MapOfLikes.remove(ps) != null)
            return true;
        return false;
    }

    @Override
    public boolean removePostsbyAuthor(String username)throws EmptyNetworkException, NullPointerException, IllegalArgumentException {
        if(MapOfLikes.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(username == null) throw new NullPointerException();
        boolean foundOne = false;

        for(Post pst : MapOfLikes.keySet())
        {
            if(pst.getAuthor()==username)
            {
                foundOne=true;
                MapOfLikes.remove(pst);
            }
        }
        if(foundOne)
            return true;
        
        throw new IllegalArgumentException("User Don't Found");
    }

    @Override
    public Set<String> followers(String username)throws EmptyNetworkException, NullPointerException, IllegalArgumentException {
        if(MapOfLikes.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(username == null) throw new NullPointerException();
        if(!MapOfFollowers.keySet().contains(username)) throw new IllegalArgumentException("user non presente");

        return new HashSet<>(MapOfFollowers.get(username));
    }

    @Override
    public boolean addFollower(String author, String username) throws IllegalArgumentException, NullPointerException {
        if(author == null || username == null) throw new  NullPointerException();
        if(author.equals(username)) throw new IllegalArgumentException("Nomi uguali, un utente non puo auto-seguirsi");
        if(!MapOfFollowers.keySet().contains(author)) throw new IllegalArgumentException("Autore non presente");

        if(!MapOfFollowers.keySet().contains(username)) createUser(username); //creo l'utente se non gia presente

        if(MapOfFollowers.get(author).contains(username))
            return false;
        
        MapOfFollowers.get(author).add(username);
        return true;
    }

    @Override
    public Post createPost(String author, String Text) throws IllegalArgumentException, NullPointerException {
        if(author == null || Text == null) throw new NullPointerException();
        if(Text.length()>140) throw new IllegalArgumentException("Lungezza del testo troppo lunga");

        Set<Integer> setID = new HashSet<>();
        int ID = 1;

        for(Post ps : MapOfLikes.keySet())
        {
            setID.add(ps.getID());
        }
        
        while (setID.contains(ID)) ID = (int) Math.random()*1000;

        Post pst = new SimplePost(author, ID,Text);

        return pst;
    }

    @Override
    public void publicatePost(Post ps) throws NullPointerException, IllegalArgumentException 
    {
        if(ps == null) throw new NullPointerException();
        if(MapOfLikes.keySet().contains(ps)) throw new  IllegalArgumentException("Post gia presente");

        Set<Integer> setID = new HashSet<>();
     
        for(Post pst : MapOfLikes.keySet())
        {
            setID.add(pst.getID());
        }
        
        if(setID.contains(ps.getID())) throw new  IllegalArgumentException("ID del post gia presente");

        MapOfLikes.put(ps,new HashSet<>());

    }

    @Override
    public void createUser(String User) throws NullPointerException, IllegalArgumentException 
    {
        if(User == null) throw new NullPointerException();
        if(MapOfFollowers.keySet().contains(User)) throw new IllegalArgumentException("Utente gia presente");

        MapOfFollowers.put(User, new HashSet<String>());

    }

    @Override
    public boolean addLike(Post ps, String user) throws NullPointerException, IllegalArgumentException 
    {
        if(ps == null ) throw new NullPointerException();
        if(!MapOfLikes.keySet().contains(ps)) throw new IllegalArgumentException("Post non all'interno del SocialNetwork");
        if(MapOfLikes.get(ps).contains(user)) return false;

        MapOfLikes.get(ps).add(user);

        return true;
    }
    
}

 class SetValueComparato implements Comparator<String> {
    Map<String , Set<String>> bs;

    public SetValueComparato(Map<String,Set<String>> base)
    {
        this.bs=base;
    }

    public int compare(String a , String b)
    {
        if (bs.get(a).size() >= bs.get(b).size()) {
            return 1;
        } else {
            return -1;
        }

    }
 }