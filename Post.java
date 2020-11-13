import java.util.Date;

public interface Post
{
/**
 *          Overview: Tipo di dato astratto POST, con relative informazioni come: ID, Autore, Testo, DataCreazione
 *          i Post sono Immutable dopo la loro creazione
 * 
 *          TE: <ID_Post, Autore, Text, DataCreazione>
 * 
 */

    /**
     * 
     * @param Author != Null
     * @param Text  Text.leght() < 141 AND Text != Null
     * @param ID 
     * @return a new Post with Post.getAuthor() == Author AND Post.getText() == Text AND Post.getID() == ID
     * @throws NullPointerException if Author == Null OR Text == Null
     * @throws IllegalArgumentException if Text.leght() > 140
     */
    public Post createNewPost(String Author,String Text,int ID)throws NullPointerException,IllegalArgumentException;

    /**
     * 
     * @return clone(this.Post)
     */
    public Post cloneThis();

    /**
     * 
     * @param p != Null
     * @return  True if this.Post contain the same information of p else False
     * @throws NullPointerException if p == Null
     */
    public boolean isEqual(Post p)throws NullPointerException;

    /**
     * 
     * @return  Clone(this.Text)
     */
    public String getText();

    /**
     * 
     * @param WordToBeChecked != Null
     * @return True if Text.contain(WordToBeChecked) else False
     * @throws NullPointerException if WordToBeChecked = Null
     *          (Eccezione fornita da Java)
     */
    public boolean checkWord(String WordToBeChecked)throws NullPointerException;

    /**
     * 
     * @return this.ID_Post
     */
    public int getID();

    /**
     * 
     * @return this.Autore
     */
    public String getAuthor();

    /**
     * 
     * @return this.Data
     */
    public Date getDate();

}