package org.docksidestage.bizfw.basic.objanimal.barking.extended;

import org.docksidestage.bizfw.basic.objanimal.Zombie;
import org.docksidestage.bizfw.basic.objanimal.barking.BarkingProcess;

/**
 * @author jflute
 */
public class ZombieBarkingProcess extends BarkingProcess {

    private final Zombie zombie;

    public ZombieBarkingProcess(Zombie zombie) {
        super(zombie);
        this.zombie = zombie;
    }

    @Override
    public void breatheIn() {
        super.breatheIn();
        zombie.getZombieDiary().countBreatheIn();
    }
}
