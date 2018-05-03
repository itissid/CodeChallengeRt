We need a program to simulate a relay race.


There are 6 teams and each team has 4 runners. The teams should all start the race at exactly the same time and each runner must wait for the previous runner in the team to finish before they can start. Each runner should randomly take between 9.0 and 10.5 seconds to complete their part of the race.

 

1) You must accurately record the finishing time and position of each team and print out the results as soon as each team completes their race to simulate a live event.


i.e.


1st Team 3 - 42.332 seconds

2nd Team 1 - 42.380 seconds

```
public interface IRelayRace {          

                public abstract void startRace();   

}
```

 

2) We need a program to record bytes read from a file or network stream.


Every time a byte is read the recordByte() function is called passing the byte read, this could happen many millions of times for large files or network transfers.

 

At any given time someone might call the printLastBytes() method which requires the method to return the last 1024 bytes read in the correct order as a string for display.

```

public interface ILastBytesRead {

                public abstract void recordByte(byte b);

                public abstract String printLastBytes();

}
```


#End coding challenge
