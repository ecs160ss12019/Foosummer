package com.gamecodeschool.asteroidsfs;

import static com.gamecodeschool.asteroidsfs.GameConfig.ASTEROID_DROP_PROB;
import static com.gamecodeschool.asteroidsfs.GameConfig.OPPONENT_DROP_PROB;

import android.graphics.PointF;

import java.util.ArrayList;


/* 
 * The CollisionEngine's main role is detecting the collision between objects.
 *  Brute force algorithm is used for collicsion detection. We break on first collision detected.
 * The CollisionEngine's second role is to faciliate collision action between two objects.
 *  For example. Asteroids can split when destroyed by colliding with laser and player.
 */
public class CollisionEngine {
    int totalHits = 0;
    boolean asteroidsEliminated = false;
    boolean oppsEliminated = false;
    boolean dropPowerUp = false;
    private PointF powerUpPos;

    /*
        We run through all object pairs that can be collided.
        player laser - asteroid
        player laser - opponent
        enemy laser - player
        asteroid - player
        powerup - player 
        These should cover the basic cases of collision within the game.
    */
    public void checkCollision(SObjectsCollection collection, GameProgress gProg, ParticleSystem ps) {
        dropPowerUp = false;

        // player vs asteroid.
        if(collection.mPlayer.getShieldState() || collection.mPlayer.getRespawnState()){
            PLaserEnemyCollision(collection.mPlayerLasers, collection.mOpponents, gProg, ps);
            PLaserAsteroidCollision(collection.mPlayerLasers, collection.mAsteroids, gProg, ps);
            playerPowerUpCollision(collection.mPlayer, collection.mMineralPowerUps, gProg);
        }
        else {
            playerAsteroidCollision(collection.mPlayer, collection.mAsteroids, gProg);
            playerEnemyCollision(collection.mPlayer, collection.mOpponents, gProg);
            PLaserEnemyCollision(collection.mPlayerLasers, collection.mOpponents, gProg, ps);
            PLaserAsteroidCollision(collection.mPlayerLasers, collection.mAsteroids, gProg, ps);
            oLaserPlayerCollision(collection.mOpponentLasers, collection.mPlayer, gProg);
            playerPowerUpCollision(collection.mPlayer, collection.mMineralPowerUps, gProg);
        }

    }

    // When player collides with asteroid, trigger asteroid collision and reset player.
    // Also, life is decremented since player died
    private void playerAsteroidCollision(Player P, ArrayList<Asteroid> aList, GameProgress gp) {
        for(int i = 0; i < aList.size(); i++) {
            Asteroid temp = aList.get(i);
            if(SpaceObject.collisionCheck(P, temp)) {
                P.resetPos();
                gp.decLife(); // You pay for the mistake with your life

                // add invincibility for respawn
                aList.addAll(temp.collisionAction());
                aList.remove(i);
                i--;
                if(aList.size() == 0){
                    asteroidsEliminated = true;
                }
                break;
            }
        }
    }

    private void playerEnemyCollision(Player P, ArrayList<Opponent> oList, GameProgress gp){
        for(int i = 0; i < oList.size(); i++) {
            Opponent temp = oList.get(i);
            if(SpaceObject.collisionCheck(P, temp)) {
                P.resetPos();
            // add subtract life logic here and possible start grace period count down.
            // should the enemy ship be destroyed on collision with Player ship?
            gp.decLife();

            oList.remove(i);
            if(oList.size() == 0){
                oppsEliminated = true;
            }
            break;
        }
        }
    }

    private void playerPowerUpCollision(Player P, ArrayList<PowerUps> puList, GameProgress gp){
        for(int i = 0; i < puList.size(); i++) {
            PowerUps temp = puList.get(i);
            if(SpaceObject.collisionCheck(P, temp)) {
                // add power up feature on collision
                switch(temp.getPowerUpType()){
                    case FIRE_RATE:
                        P.receivePowerUp(PowerUp.FIRE_RATE);
                        break;

                    case SHIELD:
                        P.receivePowerUp(PowerUp.SHIELD);
                        break;
                }
                puList.remove(i);
                break;
            }
        }
    }


    private void PLaserEnemyCollision(ArrayList<Laser> pList, ArrayList<Opponent> oList, GameProgress gp, ParticleSystem ps) {
        for(int i = 0; i < pList.size(); i++) {
            for(int k = 0; k < oList.size(); k++) {
                Opponent temp = oList.get(k);
                if(SpaceObject.collisionCheck(pList.get(i), temp)) {
                    ps.emitParticles(
                            new PointF(
                                    temp.getPosition().x,
                                    temp.getPosition().y

                            )
                    );

                    pList.remove(i);
                    // For now enemy dies in 1 hit.
                    gp.updateScore(gp.OppMultiplier);

                    didPowerUpDrop(OPPONENT_DROP_PROB,
                            new PointF(temp.getBitmapX(), temp.getBitmapY()));

                    oList.remove(k);
                    i--;
                    if(oList.size() == 0){
                        oppsEliminated = true;
                    }
                    break;
                }
            }
        }
    }

    private void PLaserAsteroidCollision(ArrayList<Laser> pList, ArrayList<Asteroid> aList, GameProgress gp, ParticleSystem ps) {
        for(int i = 0; i < pList.size(); i++) {
            for(int k = 0; k < aList.size(); k++) {
                Asteroid temp = aList.get(k);
                if(SpaceObject.collisionCheck(pList.get(i), temp)) {
                    ps.emitParticles(
                            new PointF(
                                    temp.getPosition().x,
                                    temp.getPosition().y

                            )
                    );


                    gp.updateScore(temp.getSize());

                    didPowerUpDrop(ASTEROID_DROP_PROB,
                            new PointF(temp.getBitmapX(), temp.getBitmapY()));

                    aList.addAll(temp.collisionAction());
                    aList.remove(k);
                    pList.remove(i);
                    i--;
                    if(aList.size() == 0){
                        asteroidsEliminated = true;
                    }
                    break;
                }
            }
        }
    }

    private void oLaserPlayerCollision(ArrayList<Laser>oLaserList, Player P, GameProgress gp) {
        for(int i = 0; i < oLaserList.size(); i++) {
            if(SpaceObject.collisionCheck(oLaserList.get(i), P)) {
                P.resetPos();
                // add subtract life logic here and possible grace period count down.
                gp.decLife();

                oLaserList.remove(i);
                break;
            }
        }
    }

    public boolean checkEnemiesRemaining(){
        if(oppsEliminated == true && asteroidsEliminated == true) { return true; }
        else{ return false; }
    }

    public void resetEnemies(){
        oppsEliminated = false;
        asteroidsEliminated = false;
    }

    private void didPowerUpDrop(double prob, PointF pos){
        if(prob >= Math.random()){
            dropPowerUp = true;
            setDropPos(pos);
        }
    }

    private void setDropPos(PointF pos){
        powerUpPos = pos;
    }

    public PointF getDropPos() {return this.powerUpPos;}
}

