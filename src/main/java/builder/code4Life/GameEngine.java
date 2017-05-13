package builder.code4Life;

import java.util.Random;

/**
 * Created by Romain Dénarié (romain.denarie@exoplatform.com) on 12/05/17.
 */
public class GameEngine {


    private static int betterSampleId;
    private static int betterRank;

    public static String getMove(GameState gameState, int player) {
        /*

        Si me.eta > 0 => wait
        sinon (me.eta==0)
            Si je ne porte pas de sample
                //je dois aller en chercher
                //soit au sample, soit au diagnotic s'il y en a dans le cloud
                //on va d'abord privilegier ceux que je diagnotic moi
                si je ne suis pas au sample => GOTO SAMPLES
                sinon CONNECT rank
            Si je peux encore porter un SAMPLE supplémentaire ET que je suis déjà au SAMPLE
                => CONNECT rank
            sinon
                //je porte au moins un sample,
                //je ne peux plus en porter, ou je ne suis plus au sample
                Si je possede des sample non diagnostiqué
                    Si je ne suis pas au diag => GOTO Diag
                    Sinon
                        //jai des samples non diag
                        // et je suis au diag
                        //Je diagnot mes diag non diadnostiqués
                sinon
                    //je porte au moins un sample
                    //tous mes samples sont diagnostiqué
                    Si certains samples ne sont pas réalisables et que je suis au DIAG
                        je pose les samples pas réalisables
                    sinon
                        Si il me manque des molecules
                            //je dois aller les chercher
                            Si je ne suis pas MOLECULES => GOTO Molecules
                            Sinon
                                CONNECT type
                        sinon
                            //jai toutes les molecules quil me faut
                            si je ne suis pas au labo => GOTO labo
                            sinon
                                //je suis deja au labo
                                CONNECT id




       idées a rajouter
        - si jai un sample qui demande plus de molecules que disponible, je le pose au diag
        - si dans le cloud, il y a un sample qui rapporte et que je peux faire, je dois le prendre
        - modifier le comportement
            - recuperer 3 sample
            - les diagnostiquer
            - recuperer des molecules tant que possible pour le premier, puis le 2 eme puis le 3
            - valider ceux qui sont possible
            - retourner aux molecules tant quil y a des samples diagnostiqués

         */

        System.err.println(player);
        System.err.println(gameState.getPlayer(player));

        if (gameState.getPlayer(player).eta>0) {
            System.err.println("Je suis en mouvement, eta="+gameState.getPlayer(player).eta);
            return Constant.WAIT;
        } else {
            System.err.println("Je ne suis pas  en mouvement");
            if (gameState.getPlayer(player).carriedSamples.size()==0) {
                System.err.println("Je ne porte pas de sample");
                if (!gameState.getPlayer(player).target.equals(Constant.SAMPLES)) {
                    System.err.println("Je ne suis pas au sample, j'y vais");
                    return Constant.GOTO+" "+Constant.SAMPLES;
                } else {
                    System.err.println("Je suis au sample, j'en prend 1");
                    int rank = chooseRank(gameState,player);
                    return Constant.CONNECT+" "+rank;
                }
            } else if (gameState.canCarryMoreSample(player) && gameState.getPlayer(player).target.equals(Constant.SAMPLES)) {
                System.err.println("Je peux  porter un sample supplémentaire et je suis dejà au SAMPLES, j'en prend un autre");
                int rank = chooseRank(gameState,player);
                return Constant.CONNECT+" "+rank;
            } else {
                System.err.println("Je porte des samples, et je ne peux pas en porter plus ou je ne suis pas au sample");
                if (gameState.getPlayer(player).getNbUndiagnosedSamples()!=0) {
                    System.err.println("Je porte des samples non diagnostiqués");
                    if (!gameState.getPlayer(player).target.equals(Constant.DIAGNOSIS)) {
                        System.err.println("Je ne suis pas au DIAGNOSIS, j'y vais");
                        return Constant.GOTO+" "+Constant.DIAGNOSIS;
                    } else {
                        System.err.println("Je suis au DIAGNOSIS, je diagnostic mes samples non diag");
                        int undiagnosedSampleId = gameState.getPlayer(player).getFirstUndiagnosedSampleId();
                        return Constant.CONNECT+" "+undiagnosedSampleId;
                    }
                } else {
                    System.err.println("Je ne porte que des samples diagnostiqués");
                    if (!gameState.canIRealizeAllMySamples(player)) {
                        if (gameState.getPlayer(player).target.equals(Constant.MOLECULES)) {
                            System.err.println("Je ne peux pas réaliser tous mes samples, mais je suis   deja aux molecules, je vais attendre");
                            return Constant.WAIT;
                        }
                        System.err.println("Je ne peux pas réaliser tous mes samples, j'en pose 1");
                        if (!gameState.getPlayer(player).target.equals(Constant.DIAGNOSIS)) {
                            System.err.println("Je ne suis pas au DIAGNOSIS, j'y vais (on devrait pas passer par la)");
                            return Constant.GOTO+" "+Constant.DIAGNOSIS;
                        } else {
                            System.err.println("Je suis au DIAGNOSIS, je pose un sample diagnostiqué");
                            int diagnosedSampleId = gameState.getPlayer(player).getLastDiagnosedSampleId();
                            return Constant.CONNECT+" "+diagnosedSampleId;
                        }
                    } else {
                        System.err.println("Je peux réaliser tous mes samples");
                        if (!gameState.haveEnoughMolecule(player)) {
                            System.err.println("Je ne porte pas assez de molecules");
                            if (!gameState.getPlayer(player).target.equals(Constant.MOLECULES)) {
                                System.err.println("Je ne suis pas au MOLECULES, j'y vais");
                                return Constant.GOTO+" "+Constant.MOLECULES;
                            } else {
                                System.err.println("Je suis au MOLECULES, je recupere des molecules");
                                if (gameState.getPlayer(player).getNbMoleculeCarried()==Constant.MAX_MOLECULES) {
                                    System.err.println("Jai deja MAX molecules, je n'en prend pas plus");
                                    return Constant.WAIT;
                                }
                                String moleculeTypeToGet = gameState.getNeededMoleculeType(player);
                                return Constant.CONNECT + " " + moleculeTypeToGet;
                            }
                        } else {
                            System.err.println("J'ai assez de molecules");
                            if (!gameState.getPlayer(player).target.equals(Constant.LABORATORY)) {
                                System.err.println("Je ne suis pas au LABORATORY, j'y vais");
                                return Constant.GOTO+" "+Constant.LABORATORY;
                            } else {
                                System.err.println("Je suis au LABORATORY, je valide un sample");
                                int sampleIdToValidate = gameState.getPlayer(player).getFirstDiagnosedSampleId();
                                return Constant.CONNECT + " " + sampleIdToValidate;
                            }
                        }
                    }
                }
            }
        }



    }

    private static int chooseRank(GameState gameState, int player) {
        int playerTotalExpertise = gameState.getPlayer(player).expertiseA +
                gameState.getPlayer(player).expertiseB +
                gameState.getPlayer(player).expertiseC +
                gameState.getPlayer(player).expertiseD +
                gameState.getPlayer(player).expertiseE;

        if (gameState.getPlayer(player).score>=140) return 2;
        else if (playerTotalExpertise>=5) return 3;
        else if (gameState.getPlayer(player).carriedSamples.size()==0) {
            return 2;
        } else {
            return 1;
        }
        //next : choisir le meilleur rank en fonction de letat du jeu
    }


//    public static int getBetterSampleId(GameState gameState, int player) {
//        System.err.println("Le joueur "+player+" a encore "+gameState.getNbMoleculePlaceLeft(player)+"  places molecules disponibles");
//        for (Sample sample : gameState.samples) {
//            if (sample.owner == Constant.NOBODY) {
//                int sampleGlobalCost = sample.costA+sample.costB+sample.costC+sample.costD+sample.costE;
//                System.err.println("Le sample "+sample.id+" nessecite "+sampleGlobalCost+" molecules");
//                if (sampleGlobalCost<=gameState.getNbMoleculePlaceLeft(player)) {
//                    //ce sample a un count en molecule plus faible que le nombre de place qu'il reste au joueur.
//                    //il peut prendre ce sample
//                    return sample.id;
//                }
//            }
//        }
//        return -1;
//    }
//
//    public static int getBetterRank() {
//        Random rand = new Random();
//        return rand.nextInt(3)+1;
//    }
}
