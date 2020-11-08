import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SocialNetwork {
/**
 *              Overview: gestore di una rete sociale, contenenti i Post degli utenti. la lista delle persone che seguono ogni utente.....
 *              TE: <ListOfPost , ListOfUser> AND f : user -> FollowersList t.c.
 *                  (user ∈ ListOfUser => f(user) = FollowersList ⊂ ListOfUser) AND
 *                  ListOfPost != Null AND ListOfUser != Null AND 
 *                  (ListOfPost = {Post_1, Post_2, Post_3.......Post_n}) AND
 *                  (ListOfUser = {User_1, User_2, User_3.......User_n}) AND
 *                  (forall ps1,ps2 in ListOfPost | ps1 != ps2 => ps1.ID != ps2.ID)
 *                  (forall Us1,Us2 in ListOfUser | Us1 != Us2 => Us1.Name != Us2.Name)
 *                  (forall user  in ListOfUser => f(user) != Null)
 * 
 * 
 */
    //Restituisce una rete sociale derivata dalla lista dei post
    /**
     * 
     * @param ps != Null
     * @return Map <User,FolloerwsList> t.c. (Map.getKeys() = {ps_1.Autore, ps_2.Autore....ps_n.Autore} AND Map.getValue(User) = f(User))
     * @throws NullPointerException if ps == Null 
     *          (Eccezione fornita da Java)
     */
    public Map<String,Set<String>> guessFollowers(List<Post> ps)throws NullPointerException;

    //restituisce una lista di tutti gli influencers in ordire decrescente
    /**
     * @return  newListOfUser = {User_1, User_2....User_n} t.c. (forall User_(i),User_(i+1) => #f(User_(i) >= #f(User_(i+1)) )
     * @throws EmptyNetworkException if ListOfUsers.isEmpty()
     */
    public List<String> influencers()throws EmptyNetworkException;

    //Restituisce un SET di tutti gli utenti menzionati nei post
    /**
     * @return ListOfUserMentioned t.c. (forall user in ListOfUserMentioned => Exists Post in ListOfPost | post.checkWord(user) == True)
     * @throws EmptyNetworkException if ListOfPost.isEmpty() or ListOfUser.isEmpty()
     */
    public Set<String> getMentionedUsers()throws EmptyNetworkException;

    //Resistuire una lista di tutti gli utenti menzionati nei post dati in input
    /**
     * 
     * @param ps != Null
     * @return  ListOfUserMentioned t.c. (forall user in ListOfUserMentioned => Exists Post in ps | post.checkWord(user) == True)
     * @throws EmptyNetworkException if ListOfUser.isEmpty()
     * @throws NullPointerException if ps == Null
     */
    public Set<String> getMentionedUsers(List<Post> ps)throws EmptyNetworkException,NullPointerException;

    //restituire una lista di Post scritti da username
    /**
     * @param username != Null
     * @return  ListOfPostWrittenBy ⊂ ListOfPost t.c. (forall Post ps in ListOfPostWrittenBy => ps.Autore = username)
     * @throws EmptyNetworkException if ListOfPost.isEmpty()
     * @throws NullPointerException if username == null
     */
    public List<Post> writtenBy(String username)throws EmptyNetworkException,NullPointerException;

    //restituisce una lista di post scritti da username all'interno della lista datain input
    /**
     * @param ps != Null
     * @param username != Null
     * @return ListOfPostWrittenBy ⊂ ps t.c. (forall Post p in ps => p.Autore = username)
     * @throws NullPointerException if ps == Null or username == Null
     */
    public List<Post> writtenBy(List<Post> ps, String username)throws NullPointerException;

    //restituisce una lista di post dove contengono almeno una delle parole nella lista words
    /**
     * @param words != Null
     * @return  ListOfPostWithWord ⊂ ListOfPost t.c. (forall Post ps in ListOfPostWithWord => (Exists word in words | ps.contains(word)==True))
     *              //Can be empty if can't found any words in all the posts
     * @throws EmptyNetworkException if ListOfPost.isEmpty()
     * @throws NullPointerException if words == Null
     */
    public List<Post> containing(List<String> words)throws EmptyNetworkException,NullPointerException;

    //restituisce una lsita dei post che sono stati pubblicati fra le 2 date
    /**
     * @param before != Null
     * @param post  != Null
     * @return ListOfPostBetweenDate ⊂ ListOfPost t.c. (forall Post ps in ListOfPostBetweenDate => (before <= ps.dataCreazione <= post ) )
     * @throws EmptyNetworkException if ListOfPost.isEmpty()
     * @throws IllegalArgumentException if before > post
     */
    public List<Post> betweenDate(Date before, Date post)throws EmptyNetworkException,IllegalArgumentException;

    //restituisce una lista dei post che sono stati pubblicati dopo la data in input
    public List<Post> postDate(Date before)throws EmptyNetworkException,NullPointerException;

    //rimuove tutti i post ps all'interno di this se presenti
    /**
     * @param ps != Null
     * @return ListOfPostps ⊂ ps t.c. (forall Post p in ListOfPostps => (ListOfPost.contain(p) == False))
     * @modify this.ListOfPost
     * @effects PRE(this.ListOfPost) - ps = POST(this.ListOfPost)
     * @throws EmptyNetworkException if ListOfPost.isEmpty()
     * @throws NullPointerException if ps == Null
     */
    public List<Post> removePosts(List<Post> ps)throws EmptyNetworkException, NullPointerException;

    /**
     * @param ps != Null
     * @return True if the Post is found in ListOfPost and deleted else False
     * @modify this.ListOfPost
     * @effects POST(ListOfPost) = PRE(ListOfPost) - ps
     * @throws EmptyNetworkException if ListOfPost.isEmpty()
     * @throws NullPointerException if ps == Null
     */
    public boolean removePost(Post ps)throws EmptyNetworkException,NullPointerException;

    //rimuove tutti i post con autore username
    /**
     * @param username != Null
     * @modify this.ListOfPost
     * @effects  PRE(this.ListOfPost) - pst = POST(this.ListOfPost)   {ps in pst => ListOfPost.get(ps).Autore == Username}
     * @throws EmptyNetworkException if ListOfPost.isEmpty()
     * @throws NullPointerException if username == Null
     * @throws IllegalArgumentException if (Forall Post ps in ListOfPost => ps.Autore != username)
     */
    public void removePostsbyAuthor(String username)throws EmptyNetworkException, NullPointerException,IllegalArgumentException;

    //restituisce una lista di tutti gli utenti che seguono username
    /**
     * @param username != Null
     * @return f(username)
     * @throws EmptyNetworkException if ListOfUser.isEmpty()
     * @throws NullPointerException if username == Null
     * @throws IllegalArgumentException if (Forall User us in ListOfUser => us != username)
     */
    public List<String> followers(String username)throws EmptyNetworkException, NullPointerException,IllegalArgumentException;

    //inserisce un utente nei followers di un altro
    /**
     * @param author != Null AND Author != username
     * @param username != NUll AND username != Author
     * @return True if username is a new follower of author else False
     * @modify Followers(author)
     * @effects ( PRE(ListOfUser) ∪ username = POST(ListOfUser) ) AND ( PRE(followers(author) ∪ username = POST(followers(author)) )
     * @throws IllegalArgumentException if authro == username OR (ListOfUser.contain(Author) == False)
     * @throws NullPointerException if author == Null OR username == Null
     */
    public boolean addFollower(String author, String username)throws IllegalArgumentException,NullPointerException;

    /**
     * @param author != Null
     * @param Text .leght() < 141 AND Text != Null
     * @return Post | Post.Text == Text AND Post.Autore == author
     * @throws IllegalArgumentException if Text.leght() > 140
     * @throws NullPointerException if author == Null OR Text == Null
     */
    public Post createPost(String author, String Text)throws IllegalArgumentException, NullPointerException;

    //inserisce il post all'interno di this ed aggiorna gli utenti
    /**
     * @param ps != Null AND ListOfPost.contain(ps) == False
     * @modify this.ListOfPost
     * @effects POST(ListOfPost) = PRE(ListOfPost) ∪ ps
     * @throws NullPointerException if ps == Null
     * @throws IllegalArgumentException if ListOfPost.contain(ps) == True
     */
    public void publicatePost(Post ps)throws NullPointerException,IllegalArgumentException;

    /**
     * @param User != Null AND ListOfUser.contain(User) == False
     * @modify this.ListOfUser
     * @effects POST(ListOfUser) = PRE(ListOfUser) ∪ User
     * @throws NullPointerException if User == Null
     * @throws IllegalArgumentException if ListOfUser.contain(User) == True
     */
    public void createUser(String User)throws NullPointerException, IllegalArgumentException;

}


class EmptyNetworkException extends Exception
{
    public EmptyNetworkException()
    {
        super();
    }
    public EmptyNetworkException(String str)
    {
        super(str);
    }
}