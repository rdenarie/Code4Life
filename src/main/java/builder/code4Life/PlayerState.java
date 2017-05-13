package builder.code4Life;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romain Dénarié (romain.denarie@exoplatform.com) on 12/05/17.
 */
public class PlayerState {
    String target;
    int eta;
    int score;

    int storageA;
    int storageB;
    int storageC;
    int storageD;
    int storageE;

    int expertiseA;
    int expertiseB;
    int expertiseC;
    int expertiseD;
    int expertiseE;

    List<Sample> carriedSamples;

    public PlayerState(String target, int eta, int score, int storageA, int storageB, int storageC, int storageD, int
            storageE, int expertiseA, int expertiseB, int expertiseC, int expertiseD, int expertiseE) {
        this.target = target;
        this.eta = eta;
        this.score = score;
        this.storageA = storageA;
        this.storageB = storageB;
        this.storageC = storageC;
        this.storageD = storageD;
        this.storageE = storageE;
        this.expertiseA = expertiseA;
        this.expertiseB = expertiseB;
        this.expertiseC = expertiseC;
        this.expertiseD = expertiseD;
        this.expertiseE = expertiseE;
        this.carriedSamples=new ArrayList<Sample>();
    }

    public void addSample(Sample sample) {
        this.carriedSamples.add(sample);
    }

    @Override
    public String toString() {
        return "PlayerState{" +
                "target='" + target + '\'' +
                ", eta=" + eta +
                ", score=" + score +
                ", storageA=" + storageA +
                ", storageB=" + storageB +
                ", storageC=" + storageC +
                ", storageD=" + storageD +
                ", storageE=" + storageE +
                ", expertiseA=" + expertiseA +
                ", expertiseB=" + expertiseB +
                ", expertiseC=" + expertiseC +
                ", expertiseD=" + expertiseD +
                ", expertiseE=" + expertiseE +
                ", carriedSamples=" + carriedSamples +
                '}';
    }

    public int getNbDiagnosedSamples() {
        int count=0;
        for (Sample sample :carriedSamples) {
            if (sample.diagnosed) {
                count++;
            }
        }
        return count;    }

    public int getNbUndiagnosedSamples() {
        int count=0;
        for (Sample sample :carriedSamples) {
            if (!sample.diagnosed) {
                count++;
            }
        }
        return count;
    }

    public int getFirstUndiagnosedSampleId() {
        for (Sample sample :carriedSamples) {
            if (!sample.diagnosed) {
                return sample.id;
            }
        }
        return -1;
    }

    public int getFirstDiagnosedSampleId() {
        for (Sample sample :carriedSamples) {
            if (sample.diagnosed) {
                return sample.id;
            }
        }
        return -1;    }

    public int getLastDiagnosedSampleId() {
        int lastDiagnosedId = -1;
        for (Sample sample :carriedSamples) {
            if (sample.diagnosed) {
                lastDiagnosedId=sample.id;
            }

        }
        return lastDiagnosedId;
    }

    public int getNbMoleculeCarried() {
        return storageA+storageB+storageC+storageD+storageE;
    }
}

