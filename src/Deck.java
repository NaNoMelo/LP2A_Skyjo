import java.io.*;
import java.util.LinkedList;

public class Deck extends LinkedList<Card> {
    public Deck() {
    }

    public void shuffle() {
        //shuffle the deck
        for (int i = 0; i < this.size(); i++) {
            int random = (int) (Math.random() * this.size());
            Card temp = this.get(i);
            this.set(i, this.get(random));
            this.set(random, temp);
        }
    }

    public void load() throws IOException {
        //create a deck by creating a linked list of card
        File file = new File(".\\uv.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        }
        String line = br.readLine();

        while (line != null) {
            String[] parts = line.split("\\|");
            this.add(new Card(new UV(parts[1], parts[2], parts[0], Integer.parseInt(parts[3].trim()))));
            line = br.readLine();
        }
        br.close();
    }

    public Card drawCard() {
        return this.poll();
    }
}
