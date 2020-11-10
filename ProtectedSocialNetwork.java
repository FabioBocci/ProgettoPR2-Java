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
}