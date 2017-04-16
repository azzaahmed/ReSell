package com.example;

import java.util.Random;

public class JokesJava {
    String [] jokesArray={"Can a kangaroo jump higher than a house? \n  Of course, a house doesn't jump at all.",
                          "Anton, do you think I’m a bad mother? \n My name is Paul.",
                          "My dog used to chase people on a bike a lot. It got so bad, finally I had to take his bike away.",
                          " What's the difference between a car salesman and a computer salesman? \n The car salesman can probably drive! ",
                           "Teacher: If you spend all your time sitting round playing on the Internet, you'll be fat and useless when you grow up. \n pupil: Wow! You must have spent hours surfing when you were a kid!" ,
             " Q:How many computer scientists does it take to screw in a light bulb? \n" +
            "A: \"Five. Two write the specifications, one to prove their validity and two to implement it.\"\n" +
            "Q: \"Well, how many hackers does it take?\"\n" +
            "A: \"One. But, hackers don't turn on the lights.\"",
            "Q: How does Bill Gates get fresh air into his mansion?\n" +
                    "A: One clicks on an icon and a window opens!",
            " What's O. J. Simpson's Internet address? \n" +
                    "Slash, slash, backslash, slash, slash, escape.",
            "Can you show me how to use the Internet? \n" +
                    "I d better – otherwise you will just go round and round in circles.",
           " What do you get when you cross a midget with a computer?\n" +
                   "A short circuit." ,
            "Q: What did the gangster's son tell his dad\n" +
                    "when he failed his examination?\n" +
                    "A: Dad they questioned me for 3 hours\n" +
                    "but I never told them anything.",
            "A client comes to a bank:\n" +
                    "– My cheque was returned with a remark: 'Insufficient funds'. I'd like to know whether it refers to mine or the Bank?",
            " Son: Daddy, have youever been to Egypt?\n" +
                    "FATHER : No. Why do\n" +
                    "you ask that?\n" +
                    "SinWell, where did you get THIS mummy??"
    };
    public String getJoke(){
        Random generator = new Random();
        //generate from 1 to 10 generator.nextInt(10) + 1;
        int i = generator.nextInt(jokesArray.length-1);

        return jokesArray[i];
    }
}
