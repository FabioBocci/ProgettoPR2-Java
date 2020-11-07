import java.util.Date;

public interface Post
{
/**
 *          Overview: Tipo di dato astratto POST, con relative informazioni come: ID, Autore, Testo, DataCreazione
 *          OSS:La struttura è mutable poichè penso che il creatore del post o chi lo gestisce possa modificarlo in un secondo momento
 * 
 *          TE: <ID_Post, Autore, Text, DataCreazione>
 * 
 */

    /**
     * 
     * @param newText != Null AND newText.lenght <= 140
     * @return True if SUCCES else throws an exception
     * @effect post(this.Text) = newText
     * @throws NullPointerException if newText = Null
     *          (Eccezione fornita da Java)
     * @throws IllIllegalArgumentException if newText.leght > 140
     *          (Eccezione fornita da Java)
     */
    public boolean changeText(String newText)throws NullPointerException,IllegalArgumentException;     

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
    public int getIDPost();

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