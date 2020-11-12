import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SafeSocialNetwork implements ProtectedSocialNetwork {

    @Override
    public Map<String, Set<String>> guessFollowers(List<Post> ps) throws NullPointerException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> influencers() throws EmptyNetworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<String> getMentionedUsers() throws EmptyNetworkException {
        // TODO Auto-generated method stub
        return null;
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
    public List<Post> betweenDate(Date before, Date post)
            throws EmptyNetworkException, IllegalArgumentException, NullPointerException {
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
    public boolean removePostsbyAuthor(String username)
            throws EmptyNetworkException, NullPointerException, IllegalArgumentException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public Set<String> followers(String username)
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
    public void createUser(String User) throws NullPointerException, IllegalArgumentException {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean checkPost(Post ps) throws NullPointerException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public List<Post> checkPosts(List<Post> pst) throws NullPointerException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<String> getBannedWords() throws EmptyNetworkException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Post> setBannedWords(List<String> pst) throws NullPointerException, IllegalArgumentException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addBannedWord(String Word) throws NullPointerException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean removeBannedWord(String Word) throws NullPointerException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void publicatePost(Post ps) throws NullPointerException, IllegalArgumentException, IllegalWordsException {
        // TODO Auto-generated method stub

    }

    @Override
    public Post createPost(String author, String Text)
            throws IllegalArgumentException, NullPointerException, IllegalWordsException {
        // TODO Auto-generated method stub
        return null;
    }
    
}
