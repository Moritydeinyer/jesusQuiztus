import java.sql.*;
import greenfoot.*;
import java.util.*;

public class main_game extends Screen
{
   button button1; 
   
   String[] SFX = {"hit1.mp3", "hit2.mp3", "hit3.mp3", "hit4.mp3", "hit5.mp3"};
   
   GreenfootSound right;
   GreenfootSound wrong;
   
   long regeneration;
   
   boolean wh1;
   boolean wh2;
   int numberQuestions;
   question Question;
   int answer;
   boolean sync;
   boolean chooseSpawn = true;
   int acPhase = 0;
   boolean resetHealth;
   int rightQuestions;
   
   databaseGateway database;
   
   button ovlButton;

   time timer;
   label time;
   
   long startTime;
   long lastClick;
   
   boolean creator;
   
   label[] userEmail;
   label[] userPoints;
   label[] userHealths;
   
   game game;
   
   user user;
   user[] tempUsers;
   mapdesign[] tempMapDesign;
   
   MouseInfo mi;
    
   int fail_connect;
   
   main_menu main_menu;
   
   int actualRound = 0;
   int dimmerRoundOVL = 300;
   
   button q1;
   button q2;
   button q3;
   button q4;
   
   label cS;
   label ping;
   
   label question;
   
   button mc1;
   button mc2;
   button mc3;
   button mc4;
    
   label mctxt1;
   label mctxt2;
   label mctxt3;
   label mctxt4;
   
   label joinnrLabel;
   label roundLabel;
   label yourPointsLabel;
   label yourHealthLabel;
   label yourDamageLabel;
   label effectLabelLabel;
   label effectDesc;
   label effectLast;
   
   boolean active = true;
   boolean qa = false;
   int gameTime;
       
   public main_game(main_menu tempMainMenu, databaseGateway tempDatabase, game tempGame, user tempUser) {
        super(720, 1280, 1);
        setBackground("main_game.png");
        database = tempDatabase;
        main_menu = tempMainMenu;
        game = tempGame;
        user = tempUser;
        creator = tempGame.creator;
        right = new GreenfootSound("correctQA.mp3");
        wrong = new GreenfootSound("wrongQA.mp3");
        prepare();
    }
    
   private void prepare()
   {        
        try {
            game = database.gameSerializerJava(game.join_nr);
        } catch (Exception e) {fail_connect++;}
        tempUsers = new user[game.users.length - 1];
        int i = 0;
        for (user usr : game.users) {
            if (usr.id != user.id) {
                tempUsers[i] = usr;
                addObject(usr, (int) usr.xx, (int) usr.yy); //DEV x,y
                i++;
            }
        }  
        tempMapDesign = new mapdesign[game.current_map.mapdesigns.length];
        i = 0;
        for (mapdesign md : game.current_map.mapdesigns) {
            md.getImage().setTransparency(0);
            tempMapDesign[i] = md;
            addObject(md, (int) md.xx, (int) md.yy);
            i++;
        }
        addObject(user, (int) user.xx, (int) user.yy); //DEV x,y
        timer = new time();
        addObject(timer, 0, 0);
        if (creator) {
            game.time = 1;
            try {
                database.gameSerializerDB(game, 2);
            } catch (Exception e) {fail_connect++;}
            timer.timerStart();
        }
        ping = new label("", 20);
        ping.setTextColor(Color.WHITE);
        addObject(ping, 1026, 21);
        
        actualRound = game.round - 1;
        label e1 = new label("", 20);
        e1.setTextColor(Color.WHITE);
        addObject(e1, 1180, 300);
        label e2 = new label("", 20);
        e2.setTextColor(Color.WHITE);
        addObject(e2, 1180, 362);
        label e3 = new label("", 20);
        e3.setTextColor(Color.WHITE);
        addObject(e3, 1180, 424);
        label e4 = new label("", 20);
        e4.setTextColor(Color.WHITE);
        addObject(e4, 1180, 486);
        
        label u1 = new label("", 20);
        u1.setTextColor(Color.WHITE);
        addObject(u1, 1180, 317);
        label u2 = new label("", 20);
        u2.setTextColor(Color.WHITE);
        addObject(u2, 1180, 379);
        label u3 = new label("", 20);
        u3.setTextColor(Color.WHITE);
        addObject(u3, 1180, 441);
        label u4 = new label("", 20);
        u4.setTextColor(Color.WHITE);
        addObject(u4, 1180, 503);
        
        label h1 = new label("", 20);
        h1.setTextColor(Color.WHITE);
        addObject(h1, 1180, 333);
        label h2 = new label("", 20);
        h2.setTextColor(Color.WHITE);
        addObject(h2, 1180, 395);
        label h3 = new label("", 20);
        h3.setTextColor(Color.WHITE);
        addObject(h3, 1180, 457);
        label h4 = new label("", 20);
        h4.setTextColor(Color.WHITE);
        addObject(h4, 1180, 509);
       
        userEmail = new label[] {e1, e2, e3, e4};
        userPoints = new label[] {u1, u2, u3, u4};
        userHealths = new label[] {h1, h2, h3, h4};
       
       
        ovlButton = new button("empty_btn.png");    // Layer 1 !
        addObject(ovlButton, 650, 370);             // Layer 1 !
        button1 = new button("quiet_button.png");
        addObject(button1,1176,685);
        time = new label("0:00", 25);
        time.setTextColor(Color.WHITE);
        addObject(time,1180,77);
        
        effectDesc = new label("", 20);
        effectDesc.setTextColor(Color.WHITE);
        addObject(effectDesc,1180,211);
        effectLast = new label("00:00", 20);
        effectLast.setTextColor(Color.WHITE);
        addObject(effectLast,1180,234);
        joinnrLabel = new label(""+game.join_nr, 30);
        joinnrLabel.setTextColor(Color.WHITE);
        addObject(joinnrLabel,1180,24);
        effectLabelLabel = new label("Effect", 25);
        effectLabelLabel.setTextColor(Color.WHITE);
        addObject(effectLabelLabel,1180,188);
        roundLabel = new label("", 25);
        roundLabel.setTextColor(Color.WHITE);
        addObject(roundLabel,1180,50);
        yourPointsLabel = new label("", 20);
        yourPointsLabel.setTextColor(Color.WHITE);
        addObject(yourPointsLabel,1238,126);
        yourHealthLabel = new label("", 20);
        yourHealthLabel.setTextColor(Color.WHITE);
        addObject(yourHealthLabel,1238,103);
        yourDamageLabel = new label("", 20);
        yourDamageLabel.setTextColor(Color.WHITE);
        addObject(yourDamageLabel,1238,149);
        label yourPointsLabelLabel = new label("Points", 20);
        yourPointsLabelLabel.setTextColor(Color.WHITE);
        addObject(yourPointsLabelLabel,1128,126);
        label yourHealthLabelLabel = new label("Health", 20);
        yourHealthLabelLabel.setTextColor(Color.WHITE);
        addObject(yourHealthLabelLabel,1128,103);
        label yourDamageLabelLabel = new label("Damage", 20);
        yourDamageLabelLabel.setTextColor(Color.WHITE);
        addObject(yourDamageLabelLabel,1128,149);
        
        q1 = new button("choose_quarter.png");
        q2 = new button("choose_quarter.png");
        q3 = new button("choose_quarter.png");
        q4 = new button("choose_quarter.png");
        cS = new label("Choose Spawn", 85);
        lastClick = System.currentTimeMillis();
        regeneration = System.currentTimeMillis();
        mctxt1 = new label("", 35);
        mctxt1.setTextColor(Color.WHITE);
        mctxt2 = new label("", 35);
        mctxt2.setTextColor(Color.WHITE);
        mctxt3 = new label("", 35);
        mctxt3.setTextColor(Color.WHITE);
        mctxt4 = new label("", 35);
        mctxt4.setTextColor(Color.WHITE);
        question = new label("", 35);
        question.setTextColor(Color.WHITE);
        mc1 = new button("multiple_choice.png");
        mc1.getImage().setTransparency(100);
        mc2 = new button("multiple_choice.png");
        mc2.getImage().setTransparency(100);
        mc3 = new button("multiple_choice.png");
        mc3.getImage().setTransparency(100);
        mc4 = new button("multiple_choice.png");
        mc4.getImage().setTransparency(100);
        button1 = new button("quiet_button.png");
        addObject(button1,1176,685); //last layer
   }
   
   public void act() {
       //ping.setText("Ping "+database.ping);
       if (button1.clicked()) {
           Greenfoot.setWorld(main_menu);
           main_menu.delete_game(this);
       }  
       if (active) {
           try {
               game = database.gameSerializerJava(game.join_nr);
               fail_connect = 0;
               // manage map design
               for (mapdesign mpd : game.current_map.mapdesigns) {
                   boolean added = false;
                   for (mapdesign md : tempMapDesign) {
                       if (md.id == mpd.id) {                                                              
                           md.data = mpd.data;
                           md.function = mpd.function;
                           md.source = mpd.source;
                           if (md.function == 5 && md.getImage().getTransparency() != 0) {                                                                              // DEBUG
                               int remainingTime = Integer.valueOf(mpd.data);
                               if (System.currentTimeMillis() >= md.startMillisFunction + 1000) {
                                   int tempV = md.getImage().getTransparency()-10;
                                   if (tempV >= 0) {md.getImage().setTransparency(tempV);}
                               }
                           }
                           added = true; 
                       } 
                   } 
                   if (!added) {                                                                             
                        mapdesign tMD = new mapdesign(mpd.id, mpd.xx, mpd.yy, mpd.function, mpd.source, mpd.data);
                        if (tMD.function == 5) {tMD.startMillisFunction = System.currentTimeMillis();}
                        addObject(tMD, (int)tMD.xx, (int)tMD.yy);
                        // add tempMapDesign new entry
                        mapdesign[] tempMD = new mapdesign[tempMapDesign.length+1];
                        int i = 0;
                        for (mapdesign tempmd : tempMapDesign) {
                            tempMD[i] = tempmd;
                            i++;
                        }
                        tempMD[i] = tMD;
                        tempMapDesign = tempMD;
                   }
               }
               for (user usr : tempUsers) {
                    for (user ussr : game.users) {
                        if (ussr.id == usr.id) {
                            usr.setLocation((int) ussr.xx, (int) ussr.yy);
                            usr.speed = ussr.speed;
                            usr.health = ussr.health;
                            usr.damage = ussr.damage;
                            usr.points = ussr.points;
                            usr.email = ussr.email;
                            usr.effects = ussr.effects;
                            usr.visibility = ussr.visibility;
                            usr.setImage(ussr.img);
                            
                            for (effect e : ussr.effects) {
                                if ((System.currentTimeMillis()/1000) >= e.activated+1 && e.lasting_time > 0 && e.activated > 0) { 
                                    e.activated = (int) (System.currentTimeMillis()/1000); //seconds
                                    e.lasting_time--;
                                    database.effectSerializerDB(e, 2);
                                } 
                            }
                            
                            if (resetHealth) {
                                if (usr.health <= 0) {
                                    ussr.points = ussr.points + gameTime / 4 * (game.users.length);
                                } else {
                                    int tempX = (gameTime/10);
                                    if (tempX == 0) {tempX=1;}
                                    ussr.points = ussr.points + ussr.health*(game.users.length)*30/tempX;
                                }
                                ussr.health = 10;
                                database.userSerializerDB(ussr, 2);
                                ussr = database.userSerializerJava(ussr.id, "", "");
                            }
                            if (usr.visibility == true) {usr.getImage().setTransparency(255);} else {usr.getImage().setTransparency(0);} //DEV TEST
                            if (user.isTouchingActor(usr)) {
                                //System.out.println("touching user INSTANCE");
                                mi = Greenfoot.getMouseInfo();
                                if (mi != null) {
                                    int buttonNumber = mi.getButton();
                                    if (buttonNumber == 1 && (System.currentTimeMillis() - lastClick) >= 600) {// 1 left; 3 right //10 = 1/6 Sekunde each click
                                       //DAMAGE player
                                       lastClick = System.currentTimeMillis();
                                       ussr.health = ussr.health - user.damage;
                                       database.userSerializerDB(ussr, 2);
                                       System.out.println(System.currentTimeMillis() + "" + lastClick);
                                       //create blood
                                       mapdesign blood = new mapdesign(1, usr.getY(), usr.getX(), 5, "blood.png", "5");
                                       database.mapdesignSerializerDB(blood, 3);
                                       
                                       
                                       mapdesign[] tempMD = new mapdesign[tempMapDesign.length+1];
                                       int i = 0;
                                       for (mapdesign tempmd : tempMapDesign) {
                                            tempMD[i] = tempmd;
                                            i++;
                                       }
                                       tempMD[i] = blood;
                                       game.current_map.mapdesigns = tempMD;
                                       database.mapSerializerDB(game.current_map, 2);
                                       
                                       
                                       //playsound hit1-5
                                       int random = Greenfoot.getRandomNumber(SFX.length);
                                       GreenfootSound sound = new GreenfootSound(SFX[random]);
                                       sound.play();
                                    } 
                                }
                            }
                        }
                    }
                }  
               user u = database.userSerializerJava(user.id, "", "");
               user.speed = u.speed;
               if (u.health < user.health) {
                   //playsound hit1-5
                   int random = Greenfoot.getRandomNumber(SFX.length);
                   GreenfootSound sound = new GreenfootSound(SFX[random]);
                   sound.play();
               }
               user.health = u.health;
               user.points = u.points;
               if (creator && resetHealth) {
                   resetHealth = false; 
                   if (user.health <= 0) {
                        user.points = user.points + gameTime/4*(tempUsers.length+1);
                   } else {
                       int tempX = (gameTime/10);
                       if (tempX == 0) {tempX=1;}
                       user.points = user.points + user.health*(tempUsers.length+1)*30/tempX;
                       System.out.println(user.points);
                   }
                   user.health = 10;
                   gameTime = 0;
               }
               user.email = u.email;
               user.effects = u.effects;
               user.damage = u.damage;
               user.visibility = u.visibility;
               
               //reset to initial values
               user.speed = 5;
               user.damage = 1;
               user.visibility = true; 
               
               effectDesc.setText("");
               for (effect e : user.effects) {
                   if ((System.currentTimeMillis()/1000) >= e.activated+1 && e.lasting_time > 0 && e.activated > 0) {
                        System.out.println("d4");
                        e.activated = (int) (System.currentTimeMillis()/1000); //seconds
                        e.lasting_time--;
                        System.out.println("d6");
                        database.effectSerializerDB(e, 2);
                   } 
                   if (e.id >=5 && e.function != -10) {
                       effectLast.setText(timer.formatSeconds(e.lasting_time));
                       effectDesc.setText(e.description+"");
                       if (Greenfoot.isKeyDown("e") && e.activated == 0) {
                           e.activated = 1;
                           database.effectSerializerDB(e, 2);
                       }
                       if (e.activated > 0 && e.lasting_time > 0) {
                           //apply effect
                           switch (e.function) {
                               case 15:
                                   user.speed = 2*user.speed;
                               case 16:
                                   user.visibility = false;
                               case 17:
                                   user.damage = user.damage*2;
                               case 18:
                                   if (System.currentTimeMillis() - regeneration >= 1000) {
                                       if (user.health < 10) {user.health++;}
                                       regeneration = System.currentTimeMillis(); 
                                   }
                           }
                       }
                       break;
                   } 
               }
               
                    
               boolean tempJesus = false;
               if (user.id == user.sortUsers(game.users)[0].id) {tempJesus = true;}
               user.walk(tempMapDesign, tempJesus);
               user.setImage(user.img);
               
               user.yy = user.getY();
               user.xx = user.getX();
               database.userSerializerDB(user, 2);   
           } catch (Exception e) {
               System.out.println(e);
               fail_connect++; 
           }
           if (creator) {
               try {
                   game = database.gameSerializerJava(game.join_nr);
               } catch (Exception e) {fail_connect++;}
               time.setText(timer.formatSeconds(timer.timer));
               game.time = timer.timer;
               boolean gO = false;
               for (user u : game.users) {
                   if (u.health <= 0) {
                       gO = true;
                   } 
               }
               if ((game.time>=300 || gO) && game.round <= 5) {                                        //hard limit 5 minutes [300] || 1th player death 
                   game.round++;   
                   resetHealth = true;
               }
               try {
                   database.gameSerializerDB(game, 2);
               } catch (Exception e) {fail_connect++;}
           } else {
               time.setText(timer.formatSeconds(game.time));
           }                                                                                
           boolean reset = false;
           String ovl = "";
           if (actualRound == 0 && game.round == 1) {
               reset = true;
               ovl = "round1ovl.png";
               actualRound++;
           }
           if (actualRound == 1 && game.round == 2) {
               qa = true;
               reset = true;
               System.out.println("test");
               ovl = "round2ovl.png";
               actualRound++; 
           }
           if (actualRound == 2 && game.round == 3) {
               qa = true;
               reset = true;
               ovl = "round3ovl.png";
               actualRound++;
           }
           if (actualRound == 3 && game.round == 4) {
               qa = true;
               reset = true;
               ovl = "round4ovl.png";
               actualRound++;
           }
           if (actualRound == 4 && game.round == 5) {
               qa = true;
               reset = true;
               ovl = "round5ovl.png";
               actualRound++;
           }
           if (ovl != "") {
                ovlButton.setBackgroundImage(ovl);
                ovl = "";
                dimmerRoundOVL = 300;
           } else {}
           if (game.time <= 5 && game.time >= 1 && dimmerRoundOVL>=1) {
               dimmerRoundOVL--;
               ovlButton.getImage().setTransparency((int)(dimmerRoundOVL*0.85)); 
           }
           if (game.time >= 5 && game.time <= 25) {
               ovlButton.getImage().setTransparency(0); 
           }
           if (game.round == 6) {
               gameTime = game.time;
               qa = true;
           }
           if (reset) {
               reset = false;
               if (creator) {
                   gameTime = game.time;
                   timer.timerStart();
               }
           }
        }
       try {
           game = database.gameSerializerJava(game.join_nr);
        } catch (Exception e) {fail_connect++;}
        
       if (fail_connect >= 5) {
               fail_connect = 0;
               Greenfoot.setWorld(main_menu);
               main_menu.delete_game(this); 
           }
        
       if (!creator) {
           if (game.phase == 1) { //question
               if (acPhase != game.phase) {
                   acPhase = game.phase;
               }
           }
           if (game.phase == 2) { //choose spawn
               if (acPhase != game.phase) {
                   System.out.println("d1");
                   wh1 = false;
                   removeObject(question);
                   removeObject(mc1);
                   removeObject(mc2);
                   removeObject(mc3);
                   removeObject(mc4);
                   removeObject(mctxt1);
                   removeObject(mctxt2);
                   removeObject(mctxt3);
                   removeObject(mctxt4);
                   //build choose spawn screen 5sec
                   addObject(q1,960,180);   
                   q1.getImage().setTransparency(100);
                   q2.getImage().setTransparency(100);
                   addObject(q2,320,180);   
                   q3.getImage().setTransparency(100);
                   addObject(q3,320,540);   
                   q4.getImage().setTransparency(100);
                   addObject(q4,960,540);   
                   cS.setTextColor(Color.WHITE);
                   addObject(cS,537,275); 
                   game.time = 0;
                   startTime = System.currentTimeMillis();
                   try {
                       user.xx = (float) 0;
                       user.yy = (float) 0;
                       database.userSerializerDB(user, 2);
                    } catch (Exception e) {}
                   wh2 = true; 
                   acPhase = game.phase;
               }
           }
           if (game.phase == 0) { //normal game
               if (acPhase != game.phase && game.time >= 2) {
                   removeObject(question);
                   removeObject(mc1);
                   removeObject(mc2);
                   removeObject(mc3);
                   removeObject(mc4);
                   removeObject(mctxt1);
                   removeObject(mctxt2);
                   removeObject(mctxt3);
                   removeObject(mctxt4);
                   removeObject(q1);
                   removeObject(q2);
                   removeObject(q3);
                   removeObject(q4);
                   removeObject(cS);
                   chooseSpawn = false;
                   active = true; 
                   wh2 = false;
                   wh1 = false;
                   acPhase = game.phase;
               }
           }
        }       
       
       
       
       if (qa) {
           rightQuestions = 0;
           qa = false;
           active = false;
           //build question screen 5sec
           addObject(mctxt1,268,425);
           addObject(mctxt2,268,605);
           addObject(mctxt3,805,605);
           addObject(mctxt4,805,425);
           addObject(question,562,165);
           addObject(mc1, 268, 425);
           addObject(mc2, 268, 605);
           addObject(mc3, 805, 605);
           addObject(mc4, 805, 425);
           game.time = 0;
           numberQuestions = 0;
           startTime = System.currentTimeMillis();
           wh1 = true;
       }

       
       
       if (wh1) {
           if (numberQuestions <= 5) {
               if (mc1.clicked()) {
                    answer = 1;
                    mc1.getImage().setTransparency(150);
                    mc2.getImage().setTransparency(100);
                    mc3.getImage().setTransparency(100);
                    mc4.getImage().setTransparency(100);
                }
               if (mc2.clicked()) {
                    answer = 2;
                    mc1.getImage().setTransparency(100);
                    mc2.getImage().setTransparency(150);
                    mc3.getImage().setTransparency(100);
                    mc4.getImage().setTransparency(100);
                }
               if (mc3.clicked()) {
                    answer = 3;
                    mc1.getImage().setTransparency(100);
                    mc2.getImage().setTransparency(100);
                    mc3.getImage().setTransparency(150);
                    mc4.getImage().setTransparency(100);
                }
               if (mc4.clicked()) {
                    answer = 4;
                    mc1.getImage().setTransparency(100);
                    mc2.getImage().setTransparency(100);
                    mc3.getImage().setTransparency(100);
                    mc4.getImage().setTransparency(150);
                }
                if (creator) {
                   long elapsedTime = System.currentTimeMillis() - startTime;
                   int elapsedSeconds = (int) (elapsedTime / 1000);
                   game.time = elapsedSeconds;
                   game.phase = 1;
                   try {
                       database.gameSerializerDB(game, 2);
                   } catch (Exception e) {fail_connect++;}
               }
               try {
                   game = database.gameSerializerJava(game.join_nr);
               } catch (Exception e) {fail_connect++;}
               int minutes = game.time / 60;
               int remainingSeconds = game.time % 60;
               time.setText(String.format("%02d:%02d", minutes, remainingSeconds)); 
               if (question.getText() == "") {
                   try {
                       Random r = new Random();
                       question[] Q = database.questionSerializerJavaAll();
                       Question = Q[r.nextInt(Q.length)];
                       question.setText(Question.data);
                       mctxt1.setText(Question.answer_a);
                       mctxt2.setText(Question.answer_b);
                       mctxt3.setText(Question.answer_c);
                       mctxt4.setText(Question.answer_d);
                   } catch (Exception e) {fail_connect++;}
               }
               if (game.time <5) {sync=true;}
               if (game.time >= 6) {
                   if (creator) {
                       startTime = System.currentTimeMillis();
                       numberQuestions++; 
                       question.setText("");
                       System.out.println(numberQuestions);
                       System.out.println(game.time);
                   } else {
                       if (sync) {
                           numberQuestions++; 
                           question.setText("");
                           System.out.println(numberQuestions); 
                           System.out.println(game.time);
                           sync = false;
                       }
                   }
                   int tempRightQuestions = rightQuestions;
                   if (Question.answer_a.equals(Question.answer) && answer == 1) {
                       rightQuestions++;
                   }
                   if (Question.answer_b.equals(Question.answer) && answer == 2) {
                       rightQuestions++;
                   }
                   if (Question.answer_c.equals(Question.answer) && answer == 3) {
                       rightQuestions++;
                   }
                   if (Question.answer_d.equals(Question.answer) && answer == 4) {
                       rightQuestions++;
                   }
                   if (tempRightQuestions < rightQuestions) {
                       //right 
                       right.setVolume(100);
                       right.play();
                   } else {
                       //wrong
                       wrong.setVolume(100);
                       wrong.play();
                   }
                   System.out.println(rightQuestions);     
                       }
               }
           if (game.time >= 6 && numberQuestions >=5) { // ACHTUNG DEV kann 2 mal ausgefuehrt werden...
               System.out.println("d1");
               wh1 = false;
               removeObject(question);
               removeObject(mc1);
               removeObject(mc2);
               removeObject(mc3);
               removeObject(mc4);
               removeObject(mctxt1);
               removeObject(mctxt2);
               removeObject(mctxt3);
               removeObject(mctxt4);
               if (game.round == 6) {
                   if (creator) {
                        for (user u : game.users) {
                            try {
                               if (u.health <= 0) {
                                    u.points = u.points + gameTime/4*(game.users.length);
                               } else {
                                   int tempX = (gameTime/10);
                                   if (tempX == 0) {tempX=1;}
                                   u.points = u.points + u.health*(game.users.length)*30/tempX;
                               }
                               System.out.println(rightQuestions);
                               database.userSerializerDB(u, 2);
                            } catch (Exception e) {fail_connect++;}
                        }
                   }
                   try {
                       user = database.userSerializerJava(user.id, "", "");
                       double factor = (double) rightQuestions / (double) 3.00;
                       user.points = (int) ((double) user.points * (double) factor);
                       database.userSerializerDB(user, 2);
                    } catch (Exception e) {}
                   game_over gameOverScreen = new game_over(main_menu, database , user, game, creator);
                   Greenfoot.setWorld(gameOverScreen); 
               } else {
                   addObject(q1,960,180);   
                   q1.getImage().setTransparency(100);
                   q2.getImage().setTransparency(100);
                   addObject(q2,320,180);   
                   q3.getImage().setTransparency(100);
                   addObject(q3,320,540);   
                   q4.getImage().setTransparency(100);
                   addObject(q4,960,540);   
                   cS.setTextColor(Color.WHITE);
                   addObject(cS,537,275); 
                   game.time = 0;
                   startTime = System.currentTimeMillis();
                   try {
                       // give random effect
                       for (effect eF : user.effects) {
                           if (eF.function != -10) {
                               database.effectSerializerDB(eF, 1); 
                           } 
                       }
                       if (rightQuestions >= 2) {
                           Random r = new Random();
                           int randomEffectID = r.nextInt(4) + 5; //0-3 --> 5-8
                           effect tempEffectNew = database.effectSerializerJava(randomEffectID);
                           tempEffectNew.lasting_time = tempEffectNew.lasting_time * (rightQuestions/2);
                           database.effectSerializerDB(tempEffectNew, 3);
                           effect[] tempEffect = { database.effectSerializerJava(34), tempEffectNew };
                           user.effects = tempEffect;
                       } else { 
                           effect[] tempEffect = { database.effectSerializerJava(34)}; 
                           user.effects = tempEffect;
                       }
                       user.xx = (float) 0;
                       user.yy = (float) 0;
                       database.userSerializerDB(user, 2);
                    } catch (Exception e) {fail_connect++;}
                   wh2 = true; 
                   rightQuestions = 0;
               }
           }
       }
       
       
       
       if (wh2) {
           if (creator) {
               long elapsedTime = System.currentTimeMillis() - startTime;
               int elapsedSeconds = (int) (elapsedTime / 1000);
               game.time = elapsedSeconds;
               game.phase = 2;
               try {
                   database.gameSerializerDB(game, 2);
               } catch (Exception e) {fail_connect++;}
           } 
           try {
                   game = database.gameSerializerJava(game.join_nr);
           } catch (Exception e) {fail_connect++;}
           int minutes = game.time / 60;
           int remainingSeconds = game.time % 60;
           time.setText(String.format("%02d:%02d", minutes, remainingSeconds)); 
           if (q1.clicked()) {
                try {
                    user.xx = (float) 960;
                    user.yy = (float) 180;
                    database.userSerializerDB(user, 2);
                    q1.getImage().setTransparency(150);
                    q2.getImage().setTransparency(100);
                    q3.getImage().setTransparency(100);
                    q4.getImage().setTransparency(100);
                } catch (Exception e) {fail_connect++;}
            }
           if (q2.clicked()) { 
                try {
                    user.xx = (float) 320;
                    user.yy = (float) 180;
                    database.userSerializerDB(user, 2);
                    q1.getImage().setTransparency(100);
                    q2.getImage().setTransparency(150);
                    q3.getImage().setTransparency(100);
                    q4.getImage().setTransparency(100);
                } catch (Exception e) {fail_connect++;}
            }
           if (q3.clicked()) { 
                try {
                    user.xx = (float) 320;
                    user.yy = (float) 540;
                    database.userSerializerDB(user, 2);
                    q1.getImage().setTransparency(100);
                    q2.getImage().setTransparency(100);
                    q3.getImage().setTransparency(150);
                    q4.getImage().setTransparency(100);
                } catch (Exception e) {fail_connect++;}
            }
           if (q4.clicked()) { 
                try {
                    user.xx = (float) 960;
                    user.yy = (float) 540;
                    database.userSerializerDB(user, 2);
                    q1.getImage().setTransparency(100);
                    q2.getImage().setTransparency(100);
                    q3.getImage().setTransparency(100);
                    q4.getImage().setTransparency(150);
                } catch (Exception e) {fail_connect++;}
            }
           if (game.time <= 2) {chooseSpawn = true;}
           if (game.time >= 5 && chooseSpawn) {
               if (user.xx == 0 && user.yy == 0) {
                    try {
                       user.xx = (float) 960;
                       user.yy = (float) 540;
                       database.userSerializerDB(user, 2);
                       if (creator) {
                           game = database.gameSerializerJava(game.join_nr);
                           game.phase = 0;
                           database.gameSerializerDB(game, 2);
                       }
                    } catch (Exception e) {fail_connect++;}
                }
               user.setLocation((int) user.xx, (int) user.yy);
               for (mapdesign md : tempMapDesign) {
                   md.getImage().setTransparency(0); 
               }
               removeObject(q1);
               removeObject(q2);
               removeObject(q3);
               removeObject(q4);
               removeObject(cS);
               chooseSpawn = false;
               active = true; 
               wh2 = false;
               if (creator) {
                   timer.timerStart(); 
               }
           }
       }
       
       try {
           user tempUser = database.userSerializerJava(user.id, "", "");
           roundLabel.setText(game.round+"/5");
           yourPointsLabel.setText(""+tempUser.points);
           yourHealthLabel.setText(""+tempUser.health);
           yourDamageLabel.setText(""+tempUser.damage);
           // trigger lasting time by e ^
           for (int i=0; i<=3; i++) {
               if (tempUsers.length >= (i+1)) {
                   user uu = tempUsers[i];
                   userEmail[i].setText(uu.email);
                   userHealths[i].setText(""+uu.health);
                   userPoints[i].setText(""+uu.points);
               }
           }
       } catch (Exception e) {fail_connect++;}
       
       Greenfoot.setSpeed(game.fps);
   }
   
}