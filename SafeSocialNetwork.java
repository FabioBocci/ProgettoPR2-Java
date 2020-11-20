import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SafeSocialNetwork implements ProtectedSocialNetwork {
/**
 *              AF: <MapOfFollowers , MapOfLikes, BannedWords > t.c. MapOfFollowers = Map< User , SetOfUsers > AND MapOfLikes = Map< Post , SetOfUsers >
 *              RI: MapOfFollowers != Null AND MapOfLikes != Null AND BannedWords != Null AND
 *              (forall User in MapOfFollowers.getkeyset() => MapOfFollowers.get(User) != Null AND MapOfFollowers.get(User).contains(User) == False )
 *              (forall Post in MapOfLikes.getkeyset() => MapOfLikes.get(Post) != Null AND MapOfLikes.get(Post) == Set of the user that assigned a like to the post Post )
 *              
 *              (forall Post in MapOfLikes.getKeyset() => (not EXISTS word in BannedWords | Post.getText().contains(word) == True))
 */

    private Map<String, Set<String>> MapOfFollowers;
    private Map<Post, Set<String>> MapOfLikes;
    //private Map<String , Set<Post>> BannedWordsMap;       //nel caso volessimo salvare i Post rimossi per una colpa di una parola
    private Set<String> BannedWords;


    public SafeSocialNetwork()
    {
        this.MapOfFollowers= new HashMap<>();
        this.MapOfLikes = new HashMap<>();
        this.BannedWords = new HashSet<>();
    }

    @Override
    public Map<String, Set<String>> guessFollowers(List<Post> ps) throws NullPointerException {
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
    public List<String> influencers() throws EmptyNetworkException {
        if(MapOfFollowers.isEmpty()) throw new EmptyNetworkException("Network Empty");

        SetValueComparato bvc = new SetValueComparato(MapOfFollowers);
        TreeMap<String,Set<String>> sorted_map_by_value = new TreeMap<String, Set<String>>(bvc);    //la classe TreeMap esegue un ordinamento delle chiavi tramite il Comparator passato alla creazione
        sorted_map_by_value.putAll(MapOfFollowers);         //all'inserimento verranno tutti ordinati

       return new ArrayList<String>(sorted_map_by_value.keySet());
    }

    @Override
    public Set<String> getMentionedUsers() throws EmptyNetworkException {
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
    public Set<String> getMentionedUsers(List<Post> ps) throws EmptyNetworkException, NullPointerException {
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
    public List<Post> writtenBy(String username) throws EmptyNetworkException, NullPointerException {
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
    public List<Post> writtenBy(List<Post> ps, String username) throws NullPointerException {
        if(ps == null || username == null) throw new NullPointerException();

        List<Post> p = new ArrayList<>();

        for (Post post : ps) {
            if(post.getAuthor() == username)
                p.add(post);
        }
        return p;
    }

    @Override
    public List<Post> containing(List<String> words) throws EmptyNetworkException, NullPointerException {
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
                i++;
            }
        }
        returnList.removeAll(tempList);

        return returnList;
    }

    @Override
    public List<Post> removePosts(List<Post> ps) throws EmptyNetworkException, NullPointerException {
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
    public boolean removePostsbyAuthor(String username)
            throws EmptyNetworkException, NullPointerException, IllegalArgumentException {
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
    public void createUser(String User) throws NullPointerException, IllegalArgumentException {
        if(User == null) throw new NullPointerException();
        if(MapOfFollowers.keySet().contains(User)) throw new IllegalArgumentException("Utente gia presente");

        MapOfFollowers.put(User, new HashSet<String>());

    }

    @Override
    public boolean addLike(Post ps, String user) throws NullPointerException, IllegalArgumentException {
        if(ps == null ) throw new NullPointerException();
        if(!MapOfLikes.keySet().contains(ps)) throw new IllegalArgumentException("Post non all'interno del SocialNetwork");
        if(MapOfLikes.get(ps).contains(user)) return false;

        MapOfLikes.get(ps).add(user);

        return true;
    }

    @Override
    public boolean checkPost(Post ps) throws NullPointerException {
        if(ps == null) throw new NullPointerException("post = Null in Check Post");
        String text = ps.getText();
        
        for(String word : BannedWords)
        {
            if(text.contains(word)) return false;
        }
        return true;
    }

    @Override
    public List<Post> checkPosts(List<Post> pst) throws NullPointerException {
        if(pst == null) throw new NullPointerException();
        List<Post> newPostList = new ArrayList<>();

        for(Post p : pst){
            if(checkPost(p)) newPostList.add(p);
        }
        return newPostList;
    }

    @Override
    public List<String> getBannedWords() throws EmptyNetworkException {
        if(BannedWords.isEmpty()) throw new EmptyNetworkException("Network emtpy");
        return new ArrayList<>(BannedWords);
    }

    @Override
    public List<Post> setBannedWords(List<String> words) throws NullPointerException {
        if(words == null) throw new NullPointerException("words = Null in setBannedWords");
        BannedWords.clear();
        BannedWords.addAll(words);
        return checkRI();
    }

    @Override
    public List<Post> addBannedWord(String Word) throws NullPointerException, DoubleWordException {
        if(Word == null) throw new NullPointerException();
        if(BannedWords.contains(Word))throw new DoubleWordException(Word);
        BannedWords.add(Word);
        return checkRI();
    }

    @Override
    public boolean removeBannedWord(String Word) throws NullPointerException, WordDontFoundException {
        if(Word == null) throw new NullPointerException();
        if(!BannedWords.contains(Word)) throw new WordDontFoundException(Word);
        return BannedWords.remove(Word);
    }

    @Override
    public void publicatePost(Post ps) throws NullPointerException, IllegalArgumentException, IllegalWordsException {
        if(ps == null) throw new NullPointerException();
        if(MapOfLikes.keySet().contains(ps)) throw new  IllegalArgumentException("Post gia presente");

        Set<Integer> setID = new HashSet<>();
     
        for(Post pst : MapOfLikes.keySet())
        {
            setID.add(pst.getID());
        }
        
        if(setID.contains(ps.getID())) throw new  IllegalArgumentException("ID del post gia presente");

        String testo = ps.getText();
        boolean found=false;
        int i=0;
        ArrayList<String> tmp = new ArrayList<>(BannedWords);
        while(i<BannedWords.size() && !found)
        {
            if(testo.contains(tmp.get(i)))
                found=true;
        }

        if(found)
        {
            throw new IllegalWordsException(tmp.get(i));
        }

        MapOfLikes.put(ps,new HashSet<>());

    }

    @Override
    public Post createPost(String author, String Text) throws IllegalArgumentException, NullPointerException, IllegalWordsException {
        if(author == null || Text == null) throw new NullPointerException();
        if(Text.length()>140) throw new IllegalArgumentException("Lungezza del testo troppo lunga");
        for(String word : BannedWords)
        {
            if(Text.contains(word)) throw new IllegalWordsException(word);
        }
        Set<Integer> setID = new HashSet<>();
        int ID = 1;

        for(Post ps : MapOfLikes.keySet())
        {
            setID.add(ps.getID());
        }
        
        while (setID.contains(ID)) ID = (int) (Math.random()*1000);

        Post pst = new SimplePost(author, ID,Text);

        return pst;
    }

    //controlla il Rep. Invariant
    //restituisce una lista di Post che non soddisfa più il RI, rimuovendoli da This.MapOfLikes.keySet()
    List<Post> checkRI()
    {
        List<Post> PostBanned = new ArrayList<>();

        for(String word : BannedWords)
        {
            for(Post p : MapOfLikes.keySet())
            {
                if(p.getText().contains(word))
                {
                    PostBanned.add(p);
                }
            }
        }
        for (Post post : PostBanned) {
            MapOfLikes.remove(post);
        }
        return PostBanned;
    }
    
}
