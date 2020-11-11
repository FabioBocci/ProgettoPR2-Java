import java.util.List;

public interface ProtectedSocialNetwork extends SocialNetwork
{
/**
 *          OVERVIEW:Estensione della classe SocialNetwork per la gestione di una rete sociale, con l'aggiunta di un controllo per segnalare i contenuti offensivi.
 * 
 *          TE: <ListOfPost , ListOfUser, ListOfBannedWord> AND f : user -> FollowersList t.c.
 *                  (user ∈ ListOfUser => f(user) = FollowersList ⊂ ListOfUser) AND
 *                  ListOfPost != Null AND ListOfUser != Null AND 
 *                  (ListOfPost = {Post_1, Post_2, Post_3.......Post_n}) AND
 *                  (ListOfUser = {User_1, User_2, User_3.......User_n}) AND
 *                  (forall ps1,ps2 in ListOfPost | ps1 != ps2 => ps1.ID != ps2.ID) AND
 *                  (forall Us1,Us2 in ListOfUser | Us1 != Us2 => Us1.Name != Us2.Name) AND
 *                  (forall user  in ListOfUser => f(user) != Null) AND
 * 
 * 
 *                  (froall word in ListOfBannedWord => forall post in ListOfPost | post.getText().contains(word) == False )
 */
    //Controlla che il post dato in input rispetti i vincoli sul contenuto (non abbia parole bannate)
    public boolean checkPost(Post ps)throws NullPointerException;
    //controlla che i posts dati in input rispettino i voncoli e restituisce una lista tale che rispettino i vincoli del Social network
    public List<Post> checkPosts(List<Post> pst)throws NullPointerException;

    //restiuisce una copia della lista delle parole bannate
    public List<String> getBannedWords()throws EmptyNetworkException;
    //aggiorna la lista delle parole bannate con quella data in input
    public boolean setBannedWords(List<String> pst)throws NullPointerException,IllegalArgumentException;

    //aggiunge la parola Word alla lista delle parole bannate
    public boolean addBannedWord(String Word)throws NullPointerException;

    //rimuove una parola dalla listad delle parole bannate
    public boolean removeBannedWord(String Word)throws NullPointerException;

    //overide del metodo publicate post di SocialNetwork
    public void publicatePost(Post ps) throws NullPointerException, IllegalArgumentException ,IllegalWordsException;
    //overide del metodo creare post di SocialNetwork
    public Post createPost(String author, String Text) throws IllegalArgumentException, NullPointerException,IllegalWordsException;


}



class IllegalWordsException extends RuntimeException{
    public IllegalWordsException(String word)
    {
        super(word);
    }

    public IllegalWordsException()
    {
        super();
    }
}