import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

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
        //Possibile soluzione con gli Stream ------------------------Da Controllare il funzionamento------------------//
        //Stream<Map.Entry<String,Integer>> sorted = map.entrySet().stream().sorted(Collections.reverseOrder(Map.Entry.comparingByValue()));
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
        // TODO Auto-generated method stub

    }

    @Override
    public List<String> followers(String username)throws EmptyNetworkException, NullPointerException, IllegalArgumentException {
        if(ListOfPost.isEmpty()) throw new EmptyNetworkException("Network Empty");
        if(username == null) throw new NullPointerException();

        

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


class sortListBySize implements Comparator<List<?>>
{
    @Override
    public int compare(List<?> o1, List<?> o2) {

        return o1.size()-o2.size();
    }
    
}