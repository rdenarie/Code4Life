package builder.code4Life;

/**
 * Created by Romain Dénarié (romain.denarie@exoplatform.com) on 12/05/17.
 */
public class Sample {
    int id;
    int owner;
    int rank;
    String gain;
    int health;

    int costA;
    int costB;
    int costC;
    int costD;
    int costE;

    boolean diagnosed;


    @Override
    public String
    toString() {
        return "Sample{" +
                "id=" + id +
                ", owner=" + owner +
                ", rank=" + rank +
                ", gain='" + gain + '\'' +
                ", health=" + health +
                ", costA=" + costA +
                ", costB=" + costB +
                ", costC=" + costC +
                ", costD=" + costD +
                ", costE=" + costE +
                ", diagnosted=" + diagnosed +
                '}';
    }

    public Sample(int id, int owner, int rank, String gain, int health, int costA, int costB, int costC, int costD, int
            costE) {
        this.id = id;
        this.owner = owner;
        this.rank = rank;
        this.gain = gain;
        this.health = health;
        this.costA = costA;
        this.costB = costB;
        this.costC = costC;
        this.costD = costD;
        this.costE = costE;
        this.diagnosed= (costA!=-1);

    }

}
