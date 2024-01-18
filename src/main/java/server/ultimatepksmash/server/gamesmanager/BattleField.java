package server.ultimatepksmash.server.gamesmanager;

import server.ultimatepksmash.server.database.skills.attack.Attack;
import server.ultimatepksmash.server.database.skills.defence.Defence;
import server.ultimatepksmash.server.database.smasher.Smasher;

import java.util.Random;

public class BattleField {
    public static void batlle(Smasher smasher1, Attack attackSmasher1, Defence defenceSmasher1, Smasher smasher2, Attack attackSmasher2, Defence defenceSmasher2)
    {
        Smasher firstSmasher;
        Smasher secondSmasher;
        Attack firstAttack;
        Defence firstDefence;
        Attack secondAttack;
        Defence secondDefence;
        if(smasher1.getEcts() > smasher2.getEcts())
        {
            firstSmasher = smasher1;
            firstAttack = attackSmasher1;
            firstDefence = defenceSmasher1;

            secondSmasher = smasher2;
            secondAttack = attackSmasher2;
            secondDefence = defenceSmasher2;
        }
        else if(smasher1.getEcts() < smasher2.getEcts())
        {
            firstSmasher = smasher2;
            firstAttack = attackSmasher2;
            firstDefence = defenceSmasher2;

            secondSmasher = smasher1;
            secondAttack = attackSmasher1;
            secondDefence = defenceSmasher1;
        }
        else {
            Random rand = new Random();
            int random = rand.nextInt(2);
            if(random == 0)
            {
                firstSmasher = smasher1;
                firstAttack = attackSmasher1;
                firstDefence = defenceSmasher1;

                secondSmasher = smasher2;
                secondAttack = attackSmasher2;
                secondDefence = defenceSmasher2;
            }
            else
            {
                firstSmasher = smasher2;
                firstAttack = attackSmasher2;
                firstDefence = defenceSmasher2;

                secondSmasher = smasher1;
                secondAttack = attackSmasher1;
                secondDefence = defenceSmasher1;
            }
        }


        if(firstAttack.getType() == secondDefence.getType())
        {
            secondSmasher.setHealthPoints(secondSmasher.getHealthPoints() + secondDefence.getDefencePoints() - firstAttack.getAttackPoints());
        }
        else
        {
            secondSmasher.setHealthPoints(secondSmasher.getHealthPoints() - firstAttack.getAttackPoints());
        }

        if(secondSmasher.getHealthPoints() > 0)
        {
            if(secondAttack.getType() == firstDefence.getType())
            {
                firstSmasher.setHealthPoints(firstSmasher.getHealthPoints() + firstDefence.getDefencePoints() - secondAttack.getAttackPoints());
            }
            else
            {
                firstSmasher.setHealthPoints(firstSmasher.getHealthPoints() - secondAttack.getAttackPoints());
            }
        }
    }


}
