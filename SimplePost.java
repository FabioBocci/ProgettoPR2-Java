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
    
    public SimplePost(String Author,int ID, String Text ) throws IllegalArgumentException
    {
        this(Author, ID, new Date(),Text);
    }
    public SimplePost(String Author,int ID, Date date,String txt) throws IllegalArgumentException
    {
        if(txt.length() > 140)throw new IllegalArgumentException();
        this.ID_Post=ID;
        this.Author=Author;
        this.Text= new String(txt);
        this.DataCreazione=date;
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
    public int getID() {
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

    @Override
    public Post createNewPost(String Author, String Text, int ID)throws NullPointerException, IllegalArgumentException {
            if (Author.equals(null)) throw new NullPointerException();
            if (Text.length() > 140) throw new IllegalArgumentException("Testo troppo lungo");
        return new SimplePost(Author, ID,Text);
    }

    @Override
    public Post cloneThis() {
        return new SimplePost(this.Author, this.ID_Post,this.DataCreazione, this.Text);
    }

    @Override
    public boolean isEqual(Post p) throws NullPointerException {
        if(p == null)throw new NullPointerException();


        if(!p.getAuthor().equals(this.Author)) return false;
        if(!p.getDate().equals(this.DataCreazione))return false;
        if(!p.getText().equals(this.Text))return false;
        if(p.getID()!=this.ID_Post)return false;


        return true;
    }

}
