package builder.code4Life;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Romain Dénarié (romain.denarie@exoplatform.com) on 12/05/17.
 */
public class GameState {
    PlayerState me;

    public PlayerState getMe() {
        return me;
    }

    public void setMe(PlayerState me) {
        this.me = me;
    }

    public PlayerState getEnnemy() {
        return ennemy;
    }

    public void setEnnemy(PlayerState ennemy) {
        this.ennemy = ennemy;
    }

    PlayerState ennemy;

    List<Sample> samplesInCloud;

    public int getAvailableA() {
        return availableA;
    }

    public void setAvailableA(int availableA) {
        this.availableA = availableA;
    }

    public int getAvailableB() {
        return availableB;
    }

    public void setAvailableB(int availableB) {
        this.availableB = availableB;
    }

    public int getAvailableC() {
        return availableC;
    }

    public void setAvailableC(int availableC) {
        this.availableC = availableC;
    }

    public int getAvailableD() {
        return availableD;
    }

    public void setAvailableD(int availableD) {
        this.availableD = availableD;
    }

    public int getAvailableE() {
        return availableE;
    }

    public void setAvailableE(int availableE) {
        this.availableE = availableE;
    }

    int availableA;
    int availableB;
    int availableC;
    int availableD;
    int availableE;

    public List<ScienceProject> getScienceProjects() {
        return scienceProjects;
    }

    public void setScienceProjects(List<ScienceProject> scienceProjects) {
        this.scienceProjects = scienceProjects;
    }

    List<ScienceProject> scienceProjects;

    public GameState(PlayerState me, PlayerState ennemy, List<Sample> samples) {
        this.me = me;
        this.ennemy = ennemy;
        this.samplesInCloud = samples;
    }

    public GameState() {
        this.samplesInCloud=new ArrayList<Sample>();
        this.scienceProjects=new ArrayList<ScienceProject>();
    }



    public void addSampleInCloud(Sample sample) {
        this.samplesInCloud.add(sample);
    }

    public String toString() {
        String result="";
        result+="GameState : \n"+
                "\tMe : "+me.toString()+"\n"+
                "\tEnnemy : "+ennemy.toString()+"\n"+
                "\tSamples : "+samplesInCloud.toString();
        return result;
    }


    public PlayerState getPlayer(int player) {
        if (player==Constant.ME) {
            return me;
        } else {
            return ennemy;
        }
    }

    public boolean canCarryMoreSample(int player) {
        if (getPlayer(player).carriedSamples.size()>=1) {
            //le joueur a déjà  3 samples, on peut pas en porter plus
            return false;
        } else {
            int nbMoleculePlaceLeft = getNbMoleculePlaceLeft(player);
            if (nbMoleculePlaceLeft>=5) return true;
            else return false;

            //pour le moment, on recupere que des rangs 1,
            //le nb de molecule max est 5
            //donc si il reste moins de 5 places, on veux plus porter de sample

        }

    }
//    public int getNbSampleDiagnosedHoldedBy(int player) {
//        return (int) samples.stream().filter(sample -> sample.owner==player && sample.diagnosted).count();
//    }
//
//    public int getNbSampleUndiagnosedHoldedBy(int player) {
//        return (int) samples.stream().filter(sample -> sample.owner==player && !sample.diagnosted).count();
//    }
//
//    public boolean canTakeAnotherDiagnosedSample(int player) {
//        if (getNbSampleDiagnosedHoldedBy(player)>=Constant.MAX_SAMPLES) {
//            //on a deja 3 samples diiagnostiqués, on peut pas en porter plus
//            return false;
//        } else {
//            int nbMoleculePlaceLeft = getNbMoleculePlaceLeft(player);
//
//            for(Sample sample : samples) {
//                if (sample.costA+sample.costB+sample.costC+sample.costD+sample.costE<=nbMoleculePlaceLeft) {
//                    //il y a un sample dont le nombre de molecules necessaire
//                    //est inférieur au nombre de place de molecule restante
//                    return true;
//                }
//            }
//            return false;
//
//        }
//    }
//
//
    public int getNbMoleculePlaceLeft(int player) {
        int nbMoleculePlaceLeft = Constant.MAX_MOLECULES;
        for(Sample sample : getPlayer(player).carriedSamples) {
           nbMoleculePlaceLeft=nbMoleculePlaceLeft-sample.costA-sample.costB-sample.costC-sample.costD-sample.costE;
           if (nbMoleculePlaceLeft<=0) return 0;
        }
        return nbMoleculePlaceLeft;
    }

    public boolean canIRealizeAllMySamples(int player) {
        PlayerState playerState = getPlayer(player);
        int neededA=0;
        int neededB=0;
        int neededC=0;
        int neededD=0;
        int neededE=0;
        for (Sample sample : playerState.carriedSamples) {
            neededA = neededA + sample.costA;
            neededB = neededB + sample.costB;
            neededC = neededC + sample.costC;
            neededD = neededD + sample.costD;
            neededE = neededE + sample.costE;
        }

        //il faut compter ceux que j'ai déjà en stock+l'expertise;
        neededA=neededA-playerState.storageA-playerState.expertiseA;
        neededB=neededB-playerState.storageB-playerState.expertiseB;
        neededC=neededC-playerState.storageC-playerState.expertiseC;
        neededD=neededD-playerState.storageD-playerState.expertiseD;
        neededE=neededE-playerState.storageE-playerState.expertiseE;
        if (neededA+neededB+neededC+neededD+neededE>Constant.MAX_MOLECULES) return false;

        return (neededA <= availableA && neededB <= availableB && neededC <= availableC && neededD <= availableD && neededE <= availableE);

    }



    public boolean haveEnoughMolecule(int player) {
        int neededA = 0;
        int neededB = 0;
        int neededC = 0;
        int neededD = 0;
        int neededE = 0;

        for (Sample sample : getPlayer(player).carriedSamples) {
            neededA = neededA + sample.costA;
            neededB = neededB + sample.costB;
            neededC = neededC + sample.costC;
            neededD = neededD + sample.costD;
            neededE = neededE + sample.costE;
        }
        neededA = neededA - getPlayer(player).expertiseA;
        neededB = neededB - getPlayer(player).expertiseB;
        neededC = neededC - getPlayer(player).expertiseC;
        neededD = neededD - getPlayer(player).expertiseD;
        neededE = neededE - getPlayer(player).expertiseE;
        return (getPlayer(player).storageA >= neededA && getPlayer(player).storageB >= neededB && getPlayer(player).storageC >= neededC && getPlayer(player).storageD >= neededD && getPlayer(player).storageE >= neededE);

    }
//
//
    public String getNeededMoleculeType(int player) {
        int neededA=0;
        int neededB=0;
        int neededC=0;
        int neededD=0;
        int neededE=0;

        for(Sample sample : getPlayer(player).carriedSamples) {
                neededA=neededA+sample.costA;
                neededB=neededB+sample.costB;
                neededC=neededC+sample.costC;
                neededD=neededD+sample.costD;
                neededE=neededE+sample.costE;
        }
        neededA = neededA - getPlayer(player).expertiseA;
        neededB = neededB - getPlayer(player).expertiseB;
        neededC = neededC - getPlayer(player).expertiseC;
        neededD = neededD - getPlayer(player).expertiseD;
        neededE = neededE - getPlayer(player).expertiseE;
        if (getPlayer(player).storageA<neededA) {
            return "A";
        }
        if (getPlayer(player).storageB<neededB) {
            return "B";
        }
        if (getPlayer(player).storageC<neededC) {
            return "C";
        }
        if (getPlayer(player).storageD<neededD) {
            return "D";
        }
        if (getPlayer(player).storageE<neededE) {
            return "E";
        }
        return "";
    }
//
//    public int getSampleToValidate(int player) {
//
//        int playerStorageA = player==Constant.ME ? me.storageA : ennemy.storageA;
//        int playerStorageB = player==Constant.ME ? me.storageB : ennemy.storageB;
//        int playerStorageC = player==Constant.ME ? me.storageC : ennemy.storageC;
//        int playerStorageD = player==Constant.ME ? me.storageD : ennemy.storageD;
//        int playerStorageE = player==Constant.ME ? me.storageE : ennemy.storageE;
//        for(Sample sample : samples) {
//            if (sample.owner == player) {
//                //est ce que le player veut valider ce sample ?
//                if  (sample.costA<=playerStorageA && sample.costB<=playerStorageB
//                        && sample.costC<=playerStorageC && sample.costD<=playerStorageD && sample.costE<=playerStorageE) {
//                    return sample.id;
//                }
//            }
//        }
//        return -1;
//    }
//
//    public int getNbAvailableDiagnosedSample(int player) {
//        int count=0;
//        for (Sample sample : samples) {
//            if (sample.owner==player || sample.owner==Constant.NOBODY) {
//                //ce sample est porté par moi
//                //ou present dans le cloud
//                if (sample.diagnosted) {
//                    count++;
//                }
//            }
//        }
//        return count;
//    }
}
