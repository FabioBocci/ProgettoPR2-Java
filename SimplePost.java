import java.util.Date;

public class SimplePost implements Post 
{
    /**
     *      AF: <ID_Post,Author,Text[140],DataCreazione>
     *      RI: ID_Post != Null AND Author != Null AND Text != Null AND Text.Leght()<=140 AND DataCreazione != Null
     */

    private int ID_Post;
    private String Author;
    private String Text;
    private Date DataCreazione;
    
    public SimplePost(String Author,int ID )
    {
        this(Author, ID, new Date());
    }
    public SimplePost(String Author,int ID, Date date)
    {
        this.ID_Post=ID;
        this.Author=Author;
        this.Text= new String();
        this.DataCreazione=date;
    }

    @Override
    public boolean changeText(String newText) throws NullPointerException, IllegalArgumentException 
    {
        if(newText == null) throw new NullPointerException("NewText = Null");
        if(newText.length()>140) throw new IllegalArgumentException("NewText.leght() > 140");

        this.Text = newText;

        return true;
    }

    @Override
    public String getText() {
        return new String(this.Text);
    }

    @Override
    public boolean checkWord(String WordToBeChecked) throws NullPointerException {
        if(WordToBeChecked == null) throw new NullPointerException("WordToBeChecked is Null");
        return this.Text.contains(WordToBeChecked);
    }

    @Override
    public int getIDPost() {
        return this.ID_Post;
    }

    @Override
    public String getAuthor() {
        return this.Author;
    }

    @Override
    public Date getDate() {
        return this.DataCreazione;
    }
    
}
