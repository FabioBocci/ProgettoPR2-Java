import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestTwo {
    //Batteria di test su ProtectedSocialNetwork
    public static void main(String[] args) {
        String users[] = {"marta","fabio","luca","alex","batman","superman"};
        ProtectedSocialNetwork socialNetwork = new SafeSocialNetwork();

        //aggiungo gli utenti al socialnetwork
        for (String user : users) {
            try {
                socialNetwork.createUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        
        String testo="il giorno più bello di tutta la mia gioventù è stato il giorno in cui ti ho incontrata, marta";
        Post pstone = null;

        try{
            pstone=socialNetwork.createPost("batman", testo);
            socialNetwork.publicatePost(pstone);
            //socialNetwork.publicatePost(pstone);  //lancierebbe l'eccezione del post gia presente
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        String testodue ="noi siamo i 4 vendicatori fabio luca alex e beppe";
        Post pstdue = null;

        try {
            pstdue = socialNetwork.createPost("fabio", testodue);
            socialNetwork.publicatePost(pstdue);
        } catch (Exception e) {
            e.printStackTrace();
        }


        Set<String> mentioned =socialNetwork.getMentionedUsers();

        for (String string : mentioned) {
            System.out.println("Utente menzionato:"+string);
        }

        try {
            socialNetwork.addFollower("luca", "marta");
            socialNetwork.addFollower("luca", "batman");
            socialNetwork.addFollower("luca", "superman");
            //socialNetwork.addFollower("luca", "luca");   //lancia un eccezione

            socialNetwork.addFollower("fabio", "luca");
            socialNetwork.addFollower("fabio", "marta");

            socialNetwork.addFollower("batman", "superman");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<String> lista= socialNetwork.influencers();

        for(int i =0; i<lista.size();i++)
        {
            System.out.printf("%d° utente per numero di followers: %s \n",i+1,lista.get(i));
        }
        
        String testotre = "ho bisogno di un altro testo per citare solo una persona quindi ciao batman";
        Post psttre = null;
        
        try {
            psttre= socialNetwork.createPost("fabio", testotre);
            
            socialNetwork.publicatePost(psttre);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Post> scritti= null;
        try {
            scritti = socialNetwork.writtenBy("fabio");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!scritti.isEmpty())
        {
            System.out.println("numero di post scritti da fabio: "+scritti.size());
        }
        for (Post post : scritti) {
            System.out.println("post scritti da fabio: "+post);
        }

        Map<String , Set<String>> mappina = null;
        try {
            mappina = socialNetwork.guessFollowers(scritti);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        if(!mappina.isEmpty())
        {
            System.out.println("mappa di dimensioni:" +mappina.size());
            System.out.println("mappa keys contiene:" +mappina.keySet());
            System.out.println("mappa di fabio contiene:" +mappina.get("fabio"));
        }

        List<String> words = new ArrayList<>();
        words.add("ho");
        words.add("giorno");

        try {
            scritti=socialNetwork.containing(words);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(!scritti.isEmpty())
        {
            System.out.println("numero di post scritti contenenti una delle parole: "+scritti.size());
        }
        for (Post post : scritti) {
            System.out.println("post che contiene una delle parole: "+post);
        }




        System.out.println("-----------------------------------");
        System.out.println("tutti i test superati con successo!");
        System.out.println("-----------------------------------");
        System.out.println("Aggiungiamo le parole bannate!");
        System.out.println("-----------------------------------");
        List<String> wordsbanned = new ArrayList<>();
        wordsbanned.add("4");
        wordsbanned.add("giorno");
        System.out.println("Parole bannate: "+wordsbanned);

        List<Post> pst = socialNetwork.setBannedWords(wordsbanned);

        for (Post post : pst) {
            System.out.println("Post rimosso:"+post);
        }

        System.out.println("--------------------------------------");
        System.out.println("provo a inserire un post con una parola bannata");
        System.out.println("--------------------------------------");
        try {
            socialNetwork.publicatePost(pst.get(0));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("--------------------------------------");
        System.out.println("Controllo se ci sono post con parole bannate");
        System.out.println("--------------------------------------");
        pst = socialNetwork.containing(wordsbanned);
        if(pst.isEmpty())
        {
            System.out.println("non ci sono post con le parole bannate");
        }
    }
}
