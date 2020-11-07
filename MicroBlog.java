import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MicroBlog implements SocialNetwork {
/**
 *          AF: <ListOfUser, ListOfPost , Map <User , Set<Followers> >
 *          RI: limitazioni
 */

    private List<String> ListOfUser;
    private List<Post> ListOfPost;
    //private Map<String, Set<String>> MapOfFollowers;
    private List<List<String>> FollowersList;

    public MicroBlog()
    {
        this.ListOfPost = new ArrayList<>();
        this.ListOfUser = new ArrayList<>();
        //this.MapOfFollowers = new LinkedHashMap<>();
        this.FollowersList = new ArrayList<>();
    }

    @Override
    public Map<String, Set<String>> guessFollowers(List<Post> ps) throws NullPointerException 
    {
        if(ps == null) throw new NullPointerException("Passato parametro Null a guessFollowers");

        ArrayList<String> authors = new ArrayList<String> ();
        for (Post post : ps) 
        {
            authors.add(post.getAuthor());    
        }
        Map<String , Set<String>> map = new HashMap<>();

        for (String author : authors) {
            Set<String> setstr = new HashSet<>(FollowersList.get(ListOfUser.indexOf(author)));
            map.put(author, setstr);
        }
        return map;
    }

    //---------------------Da controllare----------------------------// 
    @Override
    public List<String> influencers() throws EmptyNetworkException              
    {
        if(ListOfUser.isEmpty()) throw new EmptyNetworkException("Network Empty");
        List<Integer> mp= new ArrayList<Integer>();
        List<String> influencers = new ArrayList<>();
        //= new ArrayList<String>(this.ListOfUser);
        //influencers.sort();

        int max=0;
        int indexofmax;

        for (List<String> followers : FollowersList) 
        {
            mp.add(followers.size());
            
        }
        for(int i=0; i< ListOfUser.size() ;i++ )
        {
            max=0;
            for (int maxi : mp) 
            {
                if(maxi>max)
                {
                    max=maxi;
                }
            }
            indexofmax = mp.indexOf(max);
            mp.set(indexofmax,-1);

            influencers.add(ListOfUser.get(indexofmax));
            
        }
        return influencers;
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
                }
                if(found) mentionedUser.add(user);
            }
        }
        return mentionedUser;
    }

    @Override
    public Set<String> getMentionedUsers(List<Post> ps) throws EmptyNetworkException, NullPointerException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> writtenBy(String username) throws EmptyNetworkException, NullPointerException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> writtenBy(List<Post> ps, String username) throws NullPointerException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> containing(List<String> words) throws EmptyNetworkException, NullPointerException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> betweenDate(Date before, Date post) throws EmptyNetworkException, IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> postDate(Date before) throws EmptyNetworkException, NullPointerException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> removePosts(List<Post> ps) throws EmptyNetworkException, NullPointerException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removePost(Post ps) throws EmptyNetworkException, NullPointerException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void removePostsbyAuthor(String username)
            throws EmptyNetworkException, NullPointerException, IllegalArgumentException {
        // TODO Auto-generated method stub

    }

    @Override
    public List<String> followers(String username)
            throws EmptyNetworkException, NullPointerException, IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addFollower(String author, String username) throws IllegalArgumentException, NullPointerException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Post createPost(String author, String Text) throws IllegalArgumentException, NullPointerException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void publicatePost(Post ps) throws NullPointerException, IllegalArgumentException {
        // TODO Auto-generated method stub

    }

    @Override
    public void createUser(String User) throws NullPointerException, IllegalArgumentException {
        // TODO Auto-generated method stub

    }
    
}
