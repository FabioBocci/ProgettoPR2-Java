import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;


public class MicroBlog implements SocialNetwork {
/**
 *          AF: <ListOfUser, ListOfPost , MapOfFollowers , NumOfFollowers >
 *          RI: ListOfUser != Null AND ListOfPost != Null AND MapOfFollowers != Null AND NumOfFollowers != Null AND
 *              (forall Post in ListOfPost => Post != Null) AND
 *              (forall User in ListOfUser => User != Null) AND
 *              (forall pst_1, pst_2 in ListOfPost | pst_1 != pst_2 => pst_1.getID() !=pst_2.getID()) AND
 *              (forall usr_1,usr_2 in ListOfUser | usr_1 != usr_2 => usr_1.Name != usr_2.Name)
 *              (forall User in ListOfUser => MapOfFollowers.get(User) != Null AND NumOfFollowers.get(User)>=0 AND MapOfFollowers.get(User).size() == NumOfFollowers.get(User)) AND
 */

    private List<String> ListOfUser;
    private List<Post> ListOfPost;
    private Map<String, Set<String>> MapOfFollowers;
    private Map<String , Integer> NumOfFollowers;


    public MicroBlog()
    {
        this.ListOfPost = new ArrayList<>();
        this.ListOfUser = new ArrayList<>();
        this.MapOfFollowers = new HashMap<>();
        this.NumOfFollowers = new HashMap<>();
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

        for( String author : authorSet)
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
        if(ListOfUser.isEmpty()) throw new EmptyNetworkException("Network Empty");

        ValueComparator bvc = new ValueComparator(NumOfFollowers);
        TreeMap<String,Integer> sorted_map_by_value = new TreeMap<String, Integer>(bvc);    //la classe TreeMap esegue un ordinamento delle chiavi tramite il Comparator passato alla creazione
        sorted_map_by_value.putAll(NumOfFollowers);         //all'inserimento verranno tutti ordinati

       return new ArrayList<String>(sorted_map_by_value.keySet());
    }

    @Override
    public Set<String> getMentionedUsers() throws EmptyNetworkException 
    {
        if(ListOfPost.isEmpty()) throw new EmptyNetworkException("Network vuoto ");

        Set<String> mentionedUser = new HashSet<>();
        boolean found = false;
        int i=0;
        for (String user : ListOfUser) 
        {
            if(!mentionedUser.contains(user))
            {
                found= false;
                i=0;
                while(!found && i < ListOfPost.size())
                {
                    found=ListOfPost.get(i).checkWord(user);
                    i++;
                }
                if(found) mentionedUser.add(user);
            }
        }
        return mentionedUser;
    }

    @Override
    public Set<String> getMentionedUsers(List<Post> ps) throws EmptyNetworkException, NullPointerException 
    {
        if(this.ListOfUser.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(ps== null)throw new NullPointerException("getMentionedUsers got a Null");
        Set<String> mentionedUsers = new HashSet<>();
        int i;
        boolean found;
        for(String User : ListOfUser)
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
        if(ListOfPost.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(username == null) throw new NullPointerException();
        List<Post> ps = new ArrayList<>();
        for (Post p : ListOfPost)
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
            if(post.getAuthor()==username)
                p.add(post);
        }
        return p;
    }

    @Override
    public List<Post> containing(List<String> words) throws EmptyNetworkException, NullPointerException 
    {
        if(ListOfPost.isEmpty()) throw new EmptyNetworkException("Network empty");
        if(words == null) throw new NullPointerException();
        List<Post> tempList = new ArrayList<>(ListOfPost);
        List<Post> returnList = new ArrayList<>(ListOfPost);
        int i;
        boolean found;

        for (String word : words) {
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
    public List<Post> betweenDate(Date before, Date post) throws EmptyNetworkException, IllegalArgumentException,NullPointerException {
        if(ListOfPost.isEmpty()) throw new EmptyNetworkException("Empty Network");
        if(before == null || post == null)throw new NullPointerException();
        if(before.getTime() > post.getTime()) throw new IllegalArgumentException("Date sbagliate");

        List<Post> lstPostDate = new ArrayList<>();
        for(Post pst : ListOfPost)
        {
            if(before.getTime()<pst.getDate().getTime() && pst.getDate().getTime()< post.getTime())
                lstPostDate.add(pst);
        }
        return lstPostDate;
    }

    @Override
    public List<Post> postDate(Date before) throws EmptyNetworkException, NullPointerException {
        if(ListOfPost.isEmpty()) throw new EmptyNetworkException("Empty Network");
        if(before == null) throw new NullPointerException();

        List<Post> lstPostDate = new ArrayList<>();
        for(Post pst : ListOfPost)
        {
            if(before.getTime()<pst.getDate().getTime())
                lstPostDate.add(pst);
        }
        return lstPostDate;

    }

    @Override
    public List<Post> removePosts(List<Post> ps) throws EmptyNetworkException, NullPointerException 
    {
        if(ListOfPost.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(ps== null) throw new NullPointerException();
        boolean allfound = false;
        if(ListOfPost.containsAll(ps)) allfound= true;

        if(allfound)
        {
            this.ListOfPost.removeAll(ps);
            return new ArrayList<>();
        }
        else{

            for(Post p : ps)
            {
                if(ListOfPost.contains(p))
                {
                    ListOfPost.remove(p);
                    ps.remove(p);
                }
            }
            return ps;
        }

    }

    @Override
    public boolean removePost(Post ps) throws EmptyNetworkException, NullPointerException {
        if(ListOfPost.isEmpty()) throw new EmptyNetworkException();
        if(ps == null) throw new NullPointerException();

        if(ListOfPost.remove(ps))
            return true;
        return false;
    }

    @Override
    public boolean removePostsbyAuthor(String username)throws EmptyNetworkException, NullPointerException, IllegalArgumentException {
        if(ListOfPost.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(username == null) throw new NullPointerException();
        boolean foundOne = false;

        for(Post pst : ListOfPost)
        {
            if(pst.getAuthor()==username)
            {
                foundOne=true;
                ListOfPost.remove(pst);
            }
        }
        if(foundOne)
            return true;
        
        throw new IllegalArgumentException("User Don't Found");
    }

    @Override
    public Set<String> followers(String username)throws EmptyNetworkException, NullPointerException, IllegalArgumentException {
        if(ListOfPost.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(username == null) throw new NullPointerException();
        if(!MapOfFollowers.keySet().contains(username)) throw new IllegalArgumentException("user non presente");

        return new HashSet<>(MapOfFollowers.get(username));
    }

    @Override
    public boolean addFollower(String author, String username) throws IllegalArgumentException, NullPointerException {
        if(author == null || username == null) throw new  NullPointerException();
        if(author.equals(username)) throw new IllegalArgumentException("Nomi uguali, un utente non puo auto-seguirsi");
        if(!ListOfUser.contains(author)) throw new IllegalArgumentException("Autore non presente");

        if(!ListOfUser.contains(username)) createUser(username); //creo l'utente se non gia presente

        if(MapOfFollowers.get(author).contains(username))
            return false;
        
        MapOfFollowers.get(author).add(username);
        NumOfFollowers.put(author, NumOfFollowers.get(author)+1);
        return true;
    }

    @Override
    public Post createPost(String author, String Text) throws IllegalArgumentException, NullPointerException {
        if(author == null || Text == null) throw new NullPointerException();
        if(Text.length()>140) throw new IllegalArgumentException("Lungezza del testo troppo lunga");
        Set<Integer> setID = new HashSet<>();
        int ID = 1;
        for(Post ps : ListOfPost)
        {
            setID.add(ps.getIDPost());
        }
        
        while (setID.contains(ID)) ID = (int) Math.random();

        Post pst = new SimplePost(author, ID);
        pst.changeText(Text);

        return pst;
    }

    @Override
    public void publicatePost(Post ps) throws NullPointerException, IllegalArgumentException 
    {
        if(ps == null) throw new NullPointerException();
        if(ListOfPost.contains(ps)) throw new  IllegalArgumentException("Post gia presente");
        Set<Integer> setID = new HashSet<>();
     
        for(Post pst : ListOfPost)
        {
            setID.add(pst.getIDPost());
        }
        
        if(setID.contains(ps.getIDPost())) throw new  IllegalArgumentException("ID del post gia presente");

        ListOfPost.add(ps);

    }

    @Override
    public void createUser(String User) throws NullPointerException, IllegalArgumentException 
    {
        if(User == null) throw new NullPointerException();
        if(ListOfUser.contains(User)) throw new IllegalArgumentException("Utente gia presente");

        ListOfUser.add(User);
        MapOfFollowers.put(User, new HashSet<String>());
        NumOfFollowers.put(User,0);


    }
    
}

class ValueComparator implements Comparator<String> {
    Map<String, Integer> base;

    public ValueComparator(Map<String, Integer> base) {
        this.base = base;
    }

    public int compare(String a, String b) {
        if (base.get(a) >= base.get(b)) {
            return 1;
        } else {
            return -1;
        }
    }
}
//era possibile farlo anche con MapOfFollowers <String , Set<String>> nello stesso modo solo che nella compare doveva essere modificata cosi:
/**
 * public int compare(String a, String b) {
        if (base.get(a).size() >= base.get(b).size()) {
            return 1;
        } else {
            return -1;
        }
 */