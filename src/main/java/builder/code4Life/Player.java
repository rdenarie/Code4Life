package builder.code4Life;

import java.util.*;


/**
 * Bring data on patient samples from the diagnosis machine to the laboratory with enough molecules to produce medicine!
 **/
class Player {


    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int projectCount = in.nextInt();
        List<ScienceProject> scienceProjects=new ArrayList<ScienceProject>();

        for (int i = 0; i < projectCount; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            int c = in.nextInt();
            int d = in.nextInt();
            int e = in.nextInt();
            ScienceProject sp=new ScienceProject(a,b,c,d,e);
            scienceProjects.add(sp);
        }

        // game loop
        while (true) {
            GameState gameState=new GameState();
            gameState.setScienceProjects(scienceProjects);
            for (int i = 0; i < 2; i++) {
                String target = in.next();
                int eta = in.nextInt();
                int score = in.nextInt();
                int storageA = in.nextInt();
                int storageB = in.nextInt();
                int storageC = in.nextInt();
                int storageD = in.nextInt();
                int storageE = in.nextInt();
                int expertiseA = in.nextInt();
                int expertiseB = in.nextInt();
                int expertiseC = in.nextInt();
                int expertiseD = in.nextInt();
                int expertiseE = in.nextInt();

                PlayerState playerState=new PlayerState(target,eta,score,storageA,storageB,storageC,storageD,storageE,expertiseA,expertiseB,expertiseC,expertiseD,expertiseE);
                if (i==0) {
                    gameState.setMe(playerState);
                    System.err.println("ME : "+playerState);
                } else {
                    gameState.setEnnemy(playerState);
                    System.err.println("ennemy : "+playerState);

                }


            }
            int availableA = in.nextInt();
            int availableB = in.nextInt();
            int availableC = in.nextInt();
            int availableD = in.nextInt();
            int availableE = in.nextInt();
            gameState.setAvailableA(availableA);
            gameState.setAvailableB(availableB);
            gameState.setAvailableC(availableC);
            gameState.setAvailableD(availableD);
            gameState.setAvailableE(availableE);
            int sampleCount = in.nextInt();
            for (int i = 0; i < sampleCount; i++) {
                int sampleId = in.nextInt();
                int carriedBy = in.nextInt();
                int rank = in.nextInt();
                String expertiseGain = in.next();
                int health = in.nextInt();
                int costA = in.nextInt();
                int costB = in.nextInt();
                int costC = in.nextInt();
                int costD = in.nextInt();
                int costE = in.nextInt();
                Sample sample = new Sample(sampleId,carriedBy,rank,expertiseGain,health,costA,costB,costC,costD,costE);
                if (carriedBy==Constant.ME) {
                    gameState.me.addSample(sample);
                } else if (carriedBy==Constant.ENNEMY) {
                    gameState.ennemy.addSample(sample);
                } else {
                    gameState.addSampleInCloud(sample);
                }
            }

            //System.err.println(gameState.toString());
            String move = GameEngine.getMove(gameState,Constant.ME);

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println(move);
        }
    }
}